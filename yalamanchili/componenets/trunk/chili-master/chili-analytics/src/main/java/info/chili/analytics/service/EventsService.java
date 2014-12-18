/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.service;

import com.google.common.collect.ImmutableList;
import info.chili.analytics.model.Event;
import info.chili.analytics.utils.CachedUserAgentStringParser;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
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
}
