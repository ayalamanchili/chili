/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.analytics.service;

import com.google.common.collect.ImmutableList;
import info.chili.analytics.model.Event;
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

    @Async
    @Transactional
    public void saveEvents(Event... events) {
        mongoTemplate.insertAll(ImmutableList.copyOf(events));
    }
}
