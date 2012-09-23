package info.chili.beans;

public class EntityChildTwo {
	protected Long id;
	protected String childTwoName;
	protected EntityChildTwo childTwo;

	public EntityChildTwo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChildTwoName() {
		return childTwoName;
	}

	public void setChildTwoName(String childTwoName) {
		this.childTwoName = childTwoName;
	}

	public EntityChildTwo getChildTwo() {
		return childTwo;
	}

	public void setChildTwo(EntityChildTwo childTwo) {
		this.childTwo = childTwo;
	}

}
