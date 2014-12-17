/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.model;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author anuyalamanchili
 */
@Document(collection = "events")
public class Event {

    /**
     *
     */
    protected String user;
    /**
     *
     */
    protected Date evenTimeStamp;
    /**
     *
     */
    protected String name;
    /**
     *
     */
    protected String type;
    /**
     *
     */
    protected String input;
    /**
     *
     */
    protected String output;
    /**
     *
     */
    protected String userAgentInfo;

    public Event() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getEvenTimeStamp() {
        return evenTimeStamp;
    }

    public void setEvenTimeStamp(Date evenTimeStamp) {
        this.evenTimeStamp = evenTimeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getUserAgentInfo() {
        return userAgentInfo;
    }

    public void setUserAgentInfo(String userAgentInfo) {
        this.userAgentInfo = userAgentInfo;
    }
}
