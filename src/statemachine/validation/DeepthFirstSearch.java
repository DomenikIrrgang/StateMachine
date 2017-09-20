/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import statemachine.objects.State;
import statemachine.objects.Transition;

/**
 *
 * @author domenik
 */
public class DeepthFirstSearch {

    private static HashMap<State, Boolean> stateVisited = new HashMap<State, Boolean>();

    private static List<State> findAllStates(State state) {
        List<State> result = new ArrayList<State>();

        if (stateVisited.get(state) == null) {
            stateVisited.put(state, Boolean.TRUE);
            result.add(state);
            for (Transition t : state.getTransitions()) {
                result.addAll(findAllStates(t.getState()));
            }

        }

        return result;
    }

    public static List<State> getAllStates(State state) {
        List<State> result = findAllStates(state);
        stateVisited.clear();
        return result;
    }

}
