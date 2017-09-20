/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statemachine.methodcall;

import java.lang.reflect.InvocationTargetException;

/**
 * Can check if a certain condition is met.
 *
 * @author domenik
 */
public class Guard extends MethodCall {

    /**
     * Creates a guard which is depended on the given Method. (Return type must
     * be boolean!!!)
     *
     * @param object Object the method is called on.
     * @param methodName The methodname.
     * @param params Params for the method.
     */
    public Guard(Object object, String methodName, Object... params) {
        super(object, methodName, params);
    }

    /**
     * Returns true if condition is met.
     * 
     * @return  condition = met
     * @throws MethodCallException Thrown if: Returntype of method is not
     * boolean; methodname, object or parameters are wrong.
     */
    public boolean isTrue() throws MethodCallException {
        try {
            return (boolean) method.invoke(object);
        } catch (IllegalAccessException ex) {
            throw new MethodCallException(ex);
        } catch (IllegalArgumentException ex) {
            throw new MethodCallException(ex);
        } catch (InvocationTargetException ex) {
            throw new MethodCallException(ex);
        } catch (NullPointerException ex) {
            throw new MethodCallException(ex);
        } catch (ClassCastException ex) {
            throw new MethodCallException(ex);
        }
    }

}
