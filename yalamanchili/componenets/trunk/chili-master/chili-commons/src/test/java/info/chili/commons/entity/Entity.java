package info.chili.commons.entity;


import java.util.Date;
import java.util.Set;

public class Entity extends EntitySuper {
	protected Long longField;
	protected String stringField;
	protected Date dateField;
	protected Integer integerField;
	protected Float floatField;
	protected Boolean booleanField;
	protected Set<EntityChildTwo> childrenTwo;
	protected DummyEnum testenum;

	public Long getLongField() {
		return longField;
	}

	public void setLongField(Long longField) {
		this.longField = longField;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public Float getFloatField() {
		return floatField;
	}

	public void setFloatField(Float floatField) {
		this.floatField = floatField;
	}

	public Boolean getBooleanField() {
		return booleanField;
	}

	public void setBooleanField(Boolean booleanField) {
		this.booleanField = booleanField;
	}

	public Set<EntityChildTwo> getChildrenTwo() {
		return childrenTwo;
	}

	public void setChildrenTwo(Set<EntityChildTwo> childrenTwo) {
		this.childrenTwo = childrenTwo;
	}

	public DummyEnum getTestenum() {
		return testenum;
	}

	public void setTestenum(DummyEnum testenum) {
		this.testenum = testenum;
	}

}
