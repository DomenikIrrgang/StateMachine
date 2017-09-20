/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine.methodcall;

import java.lang.reflect.Method;

/**
 * This class can create a Method from an Object.
 * 
 * @author domenik
 */
public abstract class MethodCall {

    protected Method method;
    protected Object object;
    protected Object[] params;

    /**
     * Creates a methodcall Object, which creates a Method object with the
     * given methodname and parameters.
     * 
     * @param object Object the method is called on.
     * @param methodName Name of the method.
     * @param params Params for the method.
     */
    public MethodCall(Object object, String methodName, Object ... params) {
        this.object = object;
        this.params = params;
        method = getMethod(object, methodName);
    }

    private Method getMethod(Object object, String methodName) {
        for (Method method : object.getClass().getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
