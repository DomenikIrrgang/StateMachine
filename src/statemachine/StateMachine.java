/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine;

import XMLParser.XMLObject;
import XMLParser.XMLParser;
import XMLParser.XMLParserImp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import statemachine.methodcall.Action;
import statemachine.validation.StateMachineValidator;
import statemachine.objects.State;
import statemachine.objects.Event;
import statemachine.methodcall.MethodCallException;
import statemachine.objects.Transition;

/**
 * A StateMachine that can be in ONE state.
 *
 * @author domenik
 */
public class StateMachine implements EventObserver, StateObserver {

    private State currentState;
    private static StateMachineValidator validator = new StateMachineValidator();
    private XMLParser xmlInput;
    private boolean debug = false;

    private HashMap<String, State> states = new HashMap<String, State>();
    private HashMap<String, Event> events = new HashMap<String, Event>();

    /**
     * Creates an empty StateMachine.
     */
    public StateMachine() {
    }

    /**
     * Create a StateMachine out of a .xml file.
     *
     * @param path
     */
    public StateMachine(String path) {
        try {
            xmlInput = new XMLParserImp(path);
            createStateMachine();
        } catch (IOException ex) {
            Logger.getLogger(StateMachine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createStateMachine() {
        createEvents();
        createStates();
        createTransitions();
        currentState = states.get(xmlInput.getAttribute(xmlInput.getObject("statemachine"), "currentstate").getValue());
    }

    private void createStates() {
        for (XMLObject o : xmlInput.getObjects("state")) {
            String name = xmlInput.getAttribute(o, "name").getValue();
            State state = new State(name);
            addState(state);
        }

    }

    private void createTransitions() {
        for (XMLObject o : xmlInput.getObjects("state")) {
            List<Transition> transitions = new ArrayList<Transition>();
            String name = xmlInput.getAttribute(o, "name").getValue();
            for (XMLObject ob : xmlInput.getSubObjects(o, "transition")) {
                String eventName = xmlInput.getAttribute(ob, "eventname").getValue();
                String targetState = xmlInput.getAttribute(ob, "statename").getValue();
                Transition t = new Transition(events.get(eventName), states.get(targetState), null);
                transitions.add(t);
            }
            for (Transition t : transitions) {
                states.get(name).addTransition(t);
            }
        }

    }

    private void createEvents() {
        for (XMLObject o : xmlInput.getObjects("event")) {
            String name = xmlInput.getAttribute(o, "name").getValue();
            Event event = new Event(name);
            addEvent(event);
        }
    }

    @Override
    public void notifyEvent(Event event) {
        if (debug) {
            System.out.println("Event: " + event.getName() + " triggered!");
        }
        try {
            currentState.eventTriggered(event);
        } catch (MethodCallException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void notifyState(State state, StateEvent event) {
        if (debug) {
            System.out.println("State: " + state.getName() + " StateEvent: " + event.toString());
        }

        if (event.equals(StateEvent.ENTRY)) {
            setCurrentState(state);
        }
    }

    /**
     * Sets the StateMachine to DebugMode On/Off. If it's on, it will print
     * triggered Events and Enter/Left States.
     *
     * @param debug True = On, False = Off.
     */
    public void setDebugMode(boolean debug) {
        this.debug = debug;
    }

    /**
     * Sets the state of the machine to the new state.
     *
     * @param state The new State.
     */
    public void setCurrentState(State state) {
        currentState = state;
    }

    /**
     * Returns the current state of the machine.
     *
     * @return Currentstate.
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Checks if statemachine is valid. (each State has at least one incoming
     * and outgoing transition)
     *
     * @return true = valid
     */
    public boolean isValid() {
        return validator.isValid(this);
    }

    /**
     * Adds a new State to the machine.
     *
     * @param state The new State.
     */
    public void addState(State state) {
        states.put(state.getName(), state);
        state.addObserver(this);
    }

    /**
     * Registers an Event at the machine.
     *
     * @param event Event that is to be registered.
     */
    public void addEvent(Event event) {
        events.put(event.getName(), event);
        event.addObserver(this);
    }

    /**
     * Returns all Events that has been registered in the StateMachine.
     *
     * @return All Events.
     */
    public HashMap<String, Event> getEvents() {
        return events;
    }

    /**
     * Returns all States that has been registered in the StateMachine.
     *
     * @return All States.
     */
    public HashMap<String, State> getStates() {
        return states;
    }

    /**
     * Returns the State with the name "name". Null if State does not exist.
     *
     * @param name
     * @return State with the name "name".
     */
    public State getState(String name) {
        return states.get(name);
    }

    /**
     * Returns the Event with the name "name". Null if Event does not exist.
     *
     * @param name
     * @return Event with the name "name".
     */
    public Event getEvent(String name) {
        return events.get(name);
    }

    public void test() {
        System.out.println("test");
    }

    public static void main(String... args) {
        Lol lol = new Lol();
        lol.lol();
    }

    public static class Lol {

        public void test() {
            System.out.println("test");
        }

        public void lol() {
            StateMachine blubb = new StateMachine();
            State testState1 = new State("state1");
            State testState2 = new State("state2");
            Event e1 = new Event("e1");
            testState1.addTransition(new Transition(e1, testState2, null));
            Action action1 = new Action(this, "test");
            
            blubb.addState(testState2);
            blubb.addState(testState1);
            blubb.setCurrentState(testState1);
            blubb.addEvent(e1);
            blubb.getState("state1").getTransitions().get(0).addAction(action1);
            blubb.setDebugMode(true);
            e1.trigger();
        }
    }
}
