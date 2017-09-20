/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statemachine.validation;

import java.util.HashMap;
import java.util.List;
import statemachine.StateMachine;
import statemachine.objects.State;
import statemachine.objects.Transition;

/**
 *  Can check if a statemachine is valid.
 * 
 * @author domenik
 */
public class StateMachineValidator {
    
    private HashMap<State, Integer> incomingTransitions = new HashMap<State, Integer>();
    private HashMap<State, Integer> outgoingTransitions = new HashMap<State, Integer>();
    
    /**
     * Checks if statemachine is valid. (each State has at least one
     * incoming and outgoing transition)
     * 
     * @param stateMachine statemachine to check
     * @return true = valid
     */
    public boolean isValid(StateMachine stateMachine) {
        State currentState = stateMachine.getCurrentState();
        List<State> states = DeepthFirstSearch.getAllStates(currentState);
        
        for (State state : states) {
            for (Transition t : state.getTransitions()) {
                add(incomingTransitions, t.getState());
                add(outgoingTransitions, state);
            }
        }
        
        for (State state : states) {
            if (incomingTransitions.get(state) == null || outgoingTransitions.get(state) == null)
                return false;
        }
        return true;
    }
    
    private void add(HashMap<State, Integer> map, State state) {
        if (map.get(state) == null) {
            map.put(state, 1);
        } else {
            map.put(state, map.get(state));
        }
    }
    
}
