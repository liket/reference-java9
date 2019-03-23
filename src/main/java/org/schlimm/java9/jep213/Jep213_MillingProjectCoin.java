package org.schlimm.java9.jep213;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://openjdk.java.net/jeps/213">JEP 213: Milling Project Coin</a>
 * <br>
 * <a href="https://openjdk.java.net/jeps/213">API Description</a>
 * <br>
 * Refer to {@link Jep213_MillingProjectCoin} class to see an example for every API change by this JEP.
 */
public class Jep213_MillingProjectCoin {
    public static void main(String[] args) {
    }

    /**
     * Interfaces support private methods now.
     *
     */
    public interface PrivateMethod {
        @SuppressWarnings("unused")
        private void someMethod() {
            // NOP
        }
    }

    @SuppressWarnings({"unused", "serial"})
    public static class SomeClass {
        
        /**
         * {@link SafeVarargs} on private method now.
         */
        @SafeVarargs
        private void name(Object... objects) {
            // NOP
        }

        /**
         * Try with resource on final variable. (no fresh variable declaration required)
         * @throws FileNotFoundException exception
         */
        public void tryWithResourceOnFinal() throws FileNotFoundException {
            final InputStream fileInput = new FileInputStream("test");
            try (fileInput) {
                // NOP
            } catch (IOException e) {
                // NOP
            } finally {
                // NOP
            }
        }
        
        /**
         * Allow diamond with anonymous classes if the argument type of the inferred type is denotable.
         */
        List<String> list = new ArrayList<>() { };
        
        /**
         * Underscore as identifier is not allowed.
         */
        // String _ = new String(); // does not compile
        
    }
}
