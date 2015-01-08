/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.service;

import com.google.common.collect.ImmutableList;
import info.chili.analytics.model.Event;
import info.chili.analytics.model.Event.EventsTable;
import info.chili.analytics.utils.CachedUserAgentStringParser;
import net.sf.uadetector.ReadableUserAgent;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ayalamanchili
 */
@Component
@Transactional
public class EventsService {

    @Autowired
    protected MongoOperations mongoTemplate;
    @Autowired
    protected CachedUserAgentStringParser cachedUserAgentStringParser;

    @Async
    @Transactional
    public void saveEvents(Event... events) {
        for (Event event : events) {
            ReadableUserAgent agent = cachedUserAgentStringParser.parse(event.getUserAgentInfo());
            event.setUserAgentInfo(ReflectionToStringBuilder.toString(agent));
        }
        mongoTemplate.insertAll(ImmutableList.copyOf(events));
    }

    public EventsTable getEvents(int start, int limit) {
        EventsTable res = new EventsTable();
        res.setEntities(mongoTemplate.find(new Query().limit(limit).skip(start), Event.class));
        res.setSize(mongoTemplate.count(new Query(), Event.class));
        return res;
    }

    public EventsTable searchByName(String name) {
        EventsTable res = new EventsTable();
        String tagName = "apple";
        Query query = new Query();
        query.limit(10);
        query.addCriteria(Criteria.where("tagName").regex(tagName));
        mongoTemplate.find(query, Event.class);
        res.setEntities(mongoTemplate.findAll(Event.class, "type"));
        return res;
    }

    public EventsTable getEventTimeStamp(int start, int limit) {
        EventsTable res = new EventsTable();
        Query query = new Query();
        query.skip(start).limit(limit).with(new Sort(Sort.Direction.ASC, "pdate"));
        res.setEntities(mongoTemplate.find(query, Event.class));
        res.setSize(mongoTemplate.count(query, Event.class));
        return res;
    }
}
