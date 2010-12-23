package info.yalamanchili.server;

import info.yalamanchili.commons.ReflectionUtils;
import info.yalamanchili.commons.SearchUtils;
import info.yalamanchili.commons.ValidatorUtils;
import info.yalamanchili.gwt.beans.TableObj;
import info.yalamanchili.gwt.fields.DataType;
import info.yalamanchili.gwt.rpc.GWTService;
import info.yalamanchili.gwt.ui.DisplayType;
import info.yalamanchili.gwt.ui.UIElement;
import info.yalamanchili.security.jpa.YUser;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.search.FullTextQuery;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.log.Log;

/** this class acts as service for all server calls from y-gwt project widgets */
@Transactional
@Scope(ScopeType.SESSION)
@Name("info.yalamanchili.gwt.rpc.GWTService")
public class GWTServiceImpl extends GileadService implements GWTService {

	public GWTServiceImpl() {
		super("java:/yalamanchili");
	}

	private static final long serialVersionUID = 1L;
	@Logger
	private Log log;

	@In(create = true)
	protected EntityManager yem;

	private static HashMap<String, LinkedHashMap<String, DataType>> entity_AttributeData = new HashMap<String, LinkedHashMap<String, DataType>>();
	private static HashMap<String, LinkedHashMap<String, DataType>> entity_AttributeData_CAPS = new HashMap<String, LinkedHashMap<String, DataType>>();

	protected Class<?> getEntityClass(String className) {
		try {
			Class<?> entity = (Class<?>) Class.forName(className);
			return entity;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("class specified is not found", e);
		}
	}

	protected DisplayType getDisplayType(Field field) {
		for (Annotation annotation : field.getAnnotations()) {
			if (annotation instanceof UIElement) {
				UIElement element = ((UIElement) annotation);
				if (element.displayType() != null) {
					log.debug("display type" + element.displayType().toString());
					return element.displayType();
				}
			}
		}
		return DisplayType.DEFAULT;
	}

	@WebRemote
	public LinkedHashMap<String, DataType> getAttributes(String className) {

		if (entity_AttributeData.containsKey(className)) {
			log.debug("class:" + className
					+ " already exits in map... returning....");
			return entity_AttributeData.get(className);
		} else {
			log.debug("class:" + className
					+ " is a new class info adding info to map");
			LinkedHashMap<String, DataType> dataFields = new LinkedHashMap<String, DataType>();
			Class<?> entity = getEntityClass(className);
			for (Field field : GWTServletUtils.getEntityFieldsInOrder(entity)) {
				dataFields.put(field.getName(),
						GWTServletUtils.getDataType(field));
			}
			entity_AttributeData.put(className, dataFields);
			return dataFields;
		}
	}

	public LinkedHashMap<String, DataType> getAttributesCaps(String className) {
		LinkedHashMap<String, DataType> dataFields = new LinkedHashMap<String, DataType>();
		if (entity_AttributeData_CAPS.containsKey(className)) {
			log.debug("class:" + className
					+ " already exits in map_CAPS... returning....");
			return entity_AttributeData_CAPS.get(className);
		} else {
			Class<?> entity = getEntityClass(className);
			for (Field field : GWTServletUtils.getEntityFieldsInOrder(entity)) {
				dataFields.put(field.getName().toUpperCase(),
						GWTServletUtils.getDataType(field));
			}
			log.debug("class:" + className
					+ " is a new class info adding info to map_CAPS");
			entity_AttributeData_CAPS.put(className, dataFields);
			return dataFields;
		}
	}

	@WebRemote
	public <T extends LightEntity> List<String> validateField(T entity,
			String attributeName) {
		return ValidatorUtils.validateField(entity, attributeName);
	}

	@WebRemote
	public <T extends LightEntity> Map<String, List<String>> validateEntity(
			T entity) {
		return ValidatorUtils.validateEntity(entity);
	}

	public <T extends Serializable> T createEntityFromFields(String className,
			LinkedHashMap<String, Object> fields) {
		LinkedHashMap<String, DataType> attributes = getAttributesCaps(className);
		Class<?> entity = getEntityClass(className);
		Object newObject = null;
		try {
			newObject = entity.newInstance();
			for (String fieldName : fields.keySet()) {
				if (fieldName.compareToIgnoreCase("Id") != 0)
					for (Method method : entity.getMethods()) {
						if (method.getName().compareToIgnoreCase(
								"set" + fieldName) == 0) {
							log.debug("calling method:" + method.getName()
									+ ":with:" + fields.get(fieldName));
							if (DataType.ENUM_FIELD.equals(attributes
									.get(fieldName))) {
								Object e = getEnumValue(className, fieldName,
										(String) fields.get(fieldName));
								method.invoke(newObject, e);
							} else {
								method.invoke(newObject, fields.get(fieldName));
							}
						}
					}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) newObject;
	}

	public <T extends Serializable> LinkedHashMap<String, Object> getFieldsDataFromEntity(
			T t) {
		LinkedHashMap<String, Object> flds = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, DataType> fields = getAttributes(t.getClass()
				.getCanonicalName());
		try {
			for (String fieldName : getAttributes(
					t.getClass().getCanonicalName()).keySet()) {
				for (Method method : t.getClass().getMethods()) {
					if (method.getName().compareToIgnoreCase("get" + fieldName) == 0) {
						if (fields.get(fieldName).equals(DataType.STRING_FIELD)) {
							String result = (String) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName).equals(
								DataType.TEXT_AREA_FIELD)) {
							String result = (String) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName)
								.equals(DataType.INTEGER_FIELD)) {
							Integer result = (Integer) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName).equals(DataType.LONG_FIELD)) {
							Long result = (Long) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName).equals(DataType.FLOAT_FIELD)) {
							Float result = (Float) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName).equals(DataType.DATE_FIELD)) {
							Date result = (Date) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName)
								.equals(DataType.BOOLEAN_FIELD)) {
							Boolean result = (Boolean) method.invoke(t, null);
							flds.put(fieldName, result);
						}
						if (fields.get(fieldName).equals(DataType.ENUM_FIELD)) {
							Object value = method.invoke(t, null);
							if (value != null) {
								String result = method.invoke(t, null)
										.toString();
								flds.put(fieldName, result);
							}
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return flds;
	}

	public <T extends Serializable> T updateEntityFromFields(T entity,
			LinkedHashMap<String, Object> fields) {
		String className = entity.getClass().getCanonicalName();
		LinkedHashMap<String, DataType> attributes = getAttributesCaps(className);
		try {
			for (String fieldName : fields.keySet()) {
				if (fieldName.compareToIgnoreCase("Id") != 0)
					for (Method method : entity.getClass().getMethods()) {
						if (method.getName().compareToIgnoreCase(
								"set" + fieldName) == 0) {
							log.debug("calling method:" + method.getName()
									+ ":with:" + fields.get(fieldName));
							if (DataType.ENUM_FIELD.equals(attributes
									.get(fieldName))) {
								Object e = getEnumValue(className, fieldName,
										(String) fields.get(fieldName));
								method.invoke(entity, e);
							} else {
								method.invoke(entity, fields.get(fieldName));
							}
						}
					}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	@WebRemote
	public Enum<?>[] getEnumValues(String className, String attributeName) {
		Enum<?>[] var = null;
		Field field = GWTServletUtils.getField(getEntityClass(className),
				attributeName);
		Class<?> entity = getEntityClass(className);
		for (Method m : field.getType().getDeclaredMethods()) {
			if (m.getName().equals("values")) {
				try {
					var = (Enum<?>[]) m.invoke(entity, new Object[] {});
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Enum<?> e : var) {
					log.debug(e.toString());
				}
			}
		}

		return var;
	}

	public Object getEnumValue(String className, String attributeName,
			String value) {
		Object var = null;
		Field field = GWTServletUtils.getField(getEntityClass(className),
				attributeName);
		Class<?> entity = getEntityClass(className);
		for (Method m : field.getType().getDeclaredMethods()) {
			log.debug(m.getName());
			if (m.getName().equals("valueOf")) {
				try {
					var = (Enum<?>) m.invoke(entity, value);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		log.debug(var.toString());
		return var;
	}

	public <T extends Serializable> T createEntityFromFieldsWithID(
			String className, LinkedHashMap<String, Object> fields) {

		LinkedHashMap<String, DataType> attributes = getAttributesCaps(className);
		Class<?> entity = getEntityClass(className);
		Object newObject = null;
		try {
			newObject = entity.newInstance();
			for (String fieldName : fields.keySet()) {
				for (Method method : entity.getMethods()) {
					if (method.getName().compareToIgnoreCase("set" + fieldName) == 0) {
						log.debug("calling method:" + method.getName()
								+ ":with:" + fields.get(fieldName));
						if (DataType.ENUM_FIELD.equals(attributes
								.get(fieldName))) {
							Object e = getEnumValue(className, fieldName,
									(String) fields.get(fieldName));
							method.invoke(newObject, e);
						} else {
							method.invoke(newObject, fields.get(fieldName));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) newObject;

	}

	@Override
	@WebRemote
	public List<String> getClassRelations(String className) {
		Class clazz = getEntityClass(className);
		List<String> classRelations = new ArrayList<String>();
		do {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getType().equals(java.util.List.class)) {
					ParameterizedType type = (ParameterizedType) field
							.getGenericType();
					Type[] typeArguments = type.getActualTypeArguments();
					for (Type typeArgument : typeArguments) {
						classRelations.add(typeArgument.toString());
					}

				}
			}
			clazz = clazz.getSuperclass();
		} while (!clazz.equals(LightEntity.class));
		return classRelations;
	}

	/* security */

	@Override
	@WebRemote
	public YUser createUser(YUser entity) {
		return (YUser) getBeanManager().clone(yem.merge(entity));
	}

	@Override
	@WebRemote
	public YUser readUser(Long id) {
		return (YUser) getBeanManager().clone(yem.find(YUser.class, id));
	}

	@Override
	@WebRemote
	public YUser updateUser(YUser entity) {
		return (YUser) getBeanManager().merge(yem.merge(entity));
	}

	@Override
	@WebRemote
	public void deleteUser(YUser entity) {
		yem.remove(entity);
	}

	@Override
	@WebRemote
	public TableObj<YUser> getTableObjUser(int start) {
		List<YUser> users = new ArrayList<YUser>();
		TableObj<YUser> tableObj = new TableObj<YUser>();
		TypedQuery<YUser> getUsers = yem.createQuery(
				"from " + YUser.class.getCanonicalName(), YUser.class);
		getUsers.setFirstResult(start);
		getUsers.setMaxResults(10);
		for (YUser u : getUsers.getResultList()) {
			users.add((YUser) getBeanManager().clone(u));
		}
		tableObj.setRecords(users);
		tableObj.setNumberOfRecords(GWTServiceImpl.getEntitySize(yem,
				YUser.class));
		return tableObj;
	}

	@Override
	@WebRemote
	public List<String> getSuggestionsForNameUser(String name, YUser entity) {
		Query query = yem.createQuery(GWTServletUtils
				.getSuggestionsQueryForName(name, new YUser()));
		return query.getResultList();
	}

	@Override
	@WebRemote
	public List<YUser> getEntitiesUser(YUser entity) {
		List<YUser> entities = new ArrayList<YUser>();
		Query getEntities = yem.createQuery(GWTServletUtils
				.getSearchQueryString(entity));
		for (Object obj : getEntities.getResultList()) {
			entities.add((YUser) getBeanManager().clone(obj));
		}
		return entities;
	}

	@Override
	@WebRemote
	public Map<Long, String> getListBoxValues(String[] columns) {
		String query = GWTServletUtils.getListBoxResultsQueryString(
				YUser.class.getCanonicalName(), columns);
		Map<Long, String> values = new HashMap<Long, String>();
		Query getListBoxValues = yem.createQuery(query);
		for (Object obj : getListBoxValues.getResultList()) {
			Object[] obs = (Object[]) obj;
			values.put((Long) obs[0], (String) obs[1]);
		}
		return values;
	}

	@Override
	@WebRemote
	public List<YUser> searchUser(String searchText) {
		List<YUser> results = new ArrayList<YUser>();
		org.apache.lucene.search.Query luceneQuery = SearchUtils
				.getLuceneQuery(searchText, "id", new StandardAnalyzer(),
						ReflectionUtils.getBeanProperties(YUser.class,
								info.yalamanchili.commons.DataType.STRING));
		FullTextQuery query = SearchUtils.getFullTextSession(yem)
				.createFullTextQuery(luceneQuery, YUser.class);
		for (Object obj : query.list()) {
			results.add((YUser) getBeanManager().clone((obj)));
		}
		return results;
	}

	public static <T extends Serializable> Long getEntitySize(EntityManager em,
			Class<?> clazz) {
		String query = "select count(entity) from " + clazz.getCanonicalName()
				+ " entity";
		Query getEntitiesSize = em.createQuery(query);
		return (Long) getEntitiesSize.getSingleResult();
	}
}