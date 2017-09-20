/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statemachine;

import statemachine.objects.Event;

/**
 * To be notified if a certain Event has been triggered, you need to 
 * implement this Interface and bind your object to the event.
 * 
 *
 * @author domenik
 */
public interface EventObserver {
    
    /**
     * Is called when a event has been triggered.
     * 
     * @param event The event that has been triggered.
     */
    void notifyEvent(Event event);
}
