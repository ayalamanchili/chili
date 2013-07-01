/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.beans;

import info.chili.jpa.AbstractEntity;

/**
 *
 * @author anuyalamanchili
 */
public class EntityType extends AbstractEntity {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
