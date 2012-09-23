package info.chili.beans;

import java.util.List;

public class EntitySuper {
	protected String superString;
	protected Integer superint;
	protected List<EntityChildOne> childrenOne;
	
	public String getSuperString() {
		return superString;
	}
	public void setSuperString(String superString) {
		this.superString = superString;
	}
	public Integer getSuperint() {
		return superint;
	}
	public void setSuperint(Integer superint) {
		this.superint = superint;
	}
	public List<EntityChildOne> getChildrenOne() {
		return childrenOne;
	}
	public void setChildrenOne(List<EntityChildOne> childrenOne) {
		this.childrenOne = childrenOne;
	}

}
