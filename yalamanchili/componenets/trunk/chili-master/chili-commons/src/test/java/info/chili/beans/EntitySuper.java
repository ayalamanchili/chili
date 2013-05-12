package info.chili.beans;

import java.util.ArrayList;
import java.util.List;

public class EntitySuper {

    protected Long id;
    protected String superString;
    protected Integer superint;
    protected List<EntityChildOne> childrenOne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        if (this.childrenOne == null) {
            this.childrenOne = new ArrayList<EntityChildOne>();
        }
        return childrenOne;
    }

    public void setChildrenOne(List<EntityChildOne> childrenOne) {
        this.childrenOne = childrenOne;
    }
}
