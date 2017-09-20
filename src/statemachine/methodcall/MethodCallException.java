/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statemachine.methodcall;

/**
 *
 * @author domenik
 */
public class MethodCallException extends Exception {

    public MethodCallException(Exception ex) {
        super(ex);
    }
    
}
