/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.service;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import info.chili.analytics.model.Event;
import info.chili.analytics.model.Event.EventsTable;
import info.chili.analytics.utils.CachedUserAgentStringParser;
import java.util.List;
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
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "evenTimeStamp"));
        res.setEntities(mongoTemplate.find(query.limit(limit).skip(start), Event.class));
        res.setSize(mongoTemplate.count(query, Event.class));
        return res;
    }

    public List<Event> searchEvents(SearchEventDto search) {
        Query query = new Query();
        if (!Strings.isNullOrEmpty(search.getUser())) {
            query.addCriteria(Criteria.where("user").is(search.getUser()));
        }
        if (!Strings.isNullOrEmpty(search.getName())) {
            query.addCriteria(Criteria.where("name").regex(search.getName().trim()));
        }
        if (!Strings.isNullOrEmpty(search.getInput())) {
            query.addCriteria(Criteria.where("input").regex(search.getInput().trim()));
        }
        if (!Strings.isNullOrEmpty(search.getOutput())) {
            query.addCriteria(Criteria.where("output").regex(search.getOutput().trim()));
        }
        query.with(new Sort(Sort.Direction.DESC, "evenTimeStamp"));
        return mongoTemplate.find(query.limit(100), Event.class);
    }
}
