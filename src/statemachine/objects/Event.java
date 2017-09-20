/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine.objects;

import java.util.ArrayList;
import java.util.List;
import statemachine.EventObserver;

/**
 * An Event that can be triggered.
 *
 * @author domenik
 */
public final class Event {

    private String name = "";
    private List<EventObserver> observer = new ArrayList<EventObserver>();

    /**
     * Creates a Event with a name and observers.
     *
     * @param name The name of the event.
     * @param observer List of observers.
     */
    public Event(String name, EventObserver... observer) {
        this.name = name;
        if (observer != null) {
            for (EventObserver eo : observer) {
                addObserver(eo);
            }
        }
    }

    /**
     * Adds an observer to this event.
     *
     * @param observer The observer that will be added.
     */
    public void addObserver(EventObserver observer) {
        this.observer.add(observer);
    }

    /**
     * Triggers the event.
     *
     */
    public void trigger() {
        for (EventObserver eo : observer) {
            eo.notifyEvent(this);
        }
    }

    /**
     * Return the name of the event.
     *
     * @return Name of the event.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Event) obj).name);
    }

}
