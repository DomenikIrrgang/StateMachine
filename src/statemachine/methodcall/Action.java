/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statemachine.methodcall;

import java.lang.reflect.InvocationTargetException;

/**
 * An Action can invoke a method.
 * 
 * @author domenik
 */
public class Action extends MethodCall {
    
    /**
     * Creates an Action Object, which creates a Method object with the
     * given methodname and parameters, which can be invoked.
     * 
     * @param object Object the method will be called on.
     * @param methodName Method name.
     * @param params Method parameters.
     */
    public Action(Object object, String methodName, Object ... params) {
        super(object, methodName, params); 
    }
    
    /**
     * Invokes the method.
     * 
     * @throws MethodCallException Thrown if: object,  methodName 
     * or params are wrong.
     */
    public void invoke() throws MethodCallException {
        try {
            method.invoke(object, params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException ex) {
            throw new MethodCallException(ex);
        }
    }
    
}
