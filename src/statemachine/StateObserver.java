/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statemachine;

import statemachine.objects.State;

/**
 * To be notified if a state is entered or left, you need to implement this 
 * Interface and bind your object to the state.
 *
 * @author domenik
 */
public interface StateObserver {
    
    /**
     * This method is called when a state is entered or left.
     * 
     * @param state The state that was entered/left.
     * @param event State entered/left.
     */
    void notifyState(State state, StateEvent event);
}
