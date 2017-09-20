/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine.objects;

import statemachine.methodcall.Action;
import statemachine.methodcall.Guard;
import java.util.ArrayList;
import java.util.List;
import statemachine.methodcall.MethodCallException;

/**
 * Presents a transition to a state which depends on a event. If that event
 * has been triggered several actions can be invoked. The whole transition only
 * will happen if the requirement of the guard is fulfilled.
 *
 * @author domenik
 */
public class Transition {

    private final Event event;
    private final State state;
    private Guard guard;
    private final List<Action> actions = new ArrayList<Action>();

    /**
     * Creates a Transition.
     *
     * @param event Event that the transition is depending on.
     * @param state The state that will be entered.
     * @param guard Requirement for the transition to happen.
     * @param actions Actions that will be taken in case of transition.
     */
    public Transition(Event event, State state, Guard guard, Action... actions) {
        this.event = event;
        this.state = state;
        this.guard = guard;

        if (actions != null) {
            for (Action action : actions) {
                this.actions.add(action);
            }
        }
    }

    /**
     * Returns the event the transitions is depending on.
     *
     * @return The event the transition is depending on.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Return the state the transition is going to.
     *
     * @return The state the transition is going to.
     */
    public State getState() {
        return state;
    }

    /**
     * Returns the guard of the transition.
     *
     * @return The guard of the transition.
     */
    public Guard getGuard() {
        return guard;
    }

    /**
     * Invokes all actions of the transition.
     *
     * @throws MethodCallException Is thrown when a method definition of an
     * action has been initiated the wrong way.
     */
    public void invokeActions() throws MethodCallException {
        for (Action action : actions) {
            action.invoke();
        }
    }
    
    /**
     * Changes the guard of the transition to the new one.
     * 
     * @param guard The new Guard.
     */
    public void setGuard(Guard guard) {
        this.guard = guard;
    }
    
    /**
     * Adds a Action to the transition.
     * 
     * @param action Action that should be added.
     */
    public void addAction(Action action) {
        actions.add(action);
    }

}
