/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine.objects;

import statemachine.StateEvent;
import java.util.ArrayList;
import java.util.List;
import statemachine.StateObserver;
import statemachine.methodcall.MethodCallException;

/**
 * A state the machine can be in.
 *
 * @author domenik
 */
public final class State {

    private String name = "";
    private List<StateObserver> observer = new ArrayList<StateObserver>();
    private List<Transition> transitions = new ArrayList<Transition>();

    /**
     * Creates a state the machine can be in.
     *
     * @param name The name of the state.
     * @param observer Observer for this state.
     */
    public State(String name, StateObserver... observer) {
        this.name = name;
        if (observer != null) {
            for (StateObserver so : observer) {
                addObserver(so);
            }
        }
    }

    /**
     * Adds a observer to this state.
     *
     * @param observer Observer that will be added.
     */
    public void addObserver(StateObserver observer) {
        this.observer.add(observer);
    }

    /**
     * Adds a transition to this state.
     *
     * @param transition Transition that will be added.
     */
    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    /**
     * Returns all outgoing transitions of this state.
     *
     * @return
     */
    public List<Transition> getTransitions() {
        return transitions;
    }

    /**
     * This method is called if a event is called and checks if it should react.
     * Possibly the state of the machine will change.
     *
     * @param event Event that has been triggered.
     * @throws MethodCallException Is thrown, if the Guard or Action method of
     * the outgoing transition has been initiated the wrong way.
     */
    public void eventTriggered(Event event) throws MethodCallException {
        for (Transition t : transitions) {
            if (t.getEvent().equals(event)) {               
                if (t.getGuard() == null || t.getGuard().isTrue()) {
                    exitState();
                    t.invokeActions();
                    t.getState().enterState();
                    break;
                }
            }
        }
    }

    /**
     * Notifies all observers that this state has been entered.
     *
     */
    public void enterState() {
        for (StateObserver so : observer) {
            so.notifyState(this, StateEvent.ENTRY);
        }
    }

    /**
     * Notifies all observers that this state has been left.
     *
     */
    public void exitState() {
        for (StateObserver so : observer) {
            so.notifyState(this, StateEvent.EXIT);
        }
    }

    /**
     * Returns the name of this state.
     *
     * @return Name of the state.
     */
    public String getName() {
        return name;
    }
}
