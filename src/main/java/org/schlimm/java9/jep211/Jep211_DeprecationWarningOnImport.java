package org.schlimm.java9.jep211;

// no warning, since in import statement
import static org.schlimm.java9.jep211.Jep211_DeprecatedHelper.deprecatedMethod;

/**
 * <a href="https://openjdk.java.net/jeps/211">JEP 211: Elide Deprecation
 * Warnings on Import Statements</a> <br>
 * In the javac implementation, there is a simple check now to skip
 * over import statements when looking for deprecation warnings.
 */
public class Jep211_DeprecationWarningOnImport {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        deprecatedMethod();
    }
}
