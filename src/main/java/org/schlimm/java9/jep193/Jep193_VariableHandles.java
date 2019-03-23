package org.schlimm.java9.jep193;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * <a href="https://openjdk.java.net/jeps/193">JEP 193: Variable Handles</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/docs/api/java/lang/invoke/VarHandle.html">API Description</a>
 * <br>
 * <a href="https://www.baeldung.com/java-variable-handles">Examples</a>
 */
public class Jep193_VariableHandles {

    @SuppressWarnings("unused")
    private String someString = "variable";
    @SuppressWarnings("unused")
    private String nullString = null;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        
        Jep193_VariableHandles handlesExampleClass = new Jep193_VariableHandles();

        VarHandle someStringHandle = MethodHandles.privateLookupIn(Jep193_VariableHandles.class, MethodHandles.lookup())
                .findVarHandle(Jep193_VariableHandles.class, "someString", String.class);
        someStringHandle.set(handlesExampleClass, "new");
        System.out.println(someStringHandle.get(handlesExampleClass));

        VarHandle someNullStringHandle = MethodHandles.privateLookupIn(Jep193_VariableHandles.class, MethodHandles.lookup())
                .findVarHandle(Jep193_VariableHandles.class, "nullString", String.class);
        if (someNullStringHandle.compareAndSet(handlesExampleClass, null, "set"))
            System.out.println(someNullStringHandle.get(handlesExampleClass));

    }
}
