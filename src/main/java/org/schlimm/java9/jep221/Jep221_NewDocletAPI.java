package org.schlimm.java9.jep221;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

/**
 * <a href="https://openjdk.java.net/jeps/221">JEP 221: New Doclet API</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/docs/api/jdk/javadoc/doclet/package-summary.html">Doclet API Javadoc</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/javadoc/javadoc-command.htm#JSJAV-GUID-C27CE557-E5C6-4688-9FA5-9E9DE886A569">API Documentation</a><br>
 * Example included in {@link Jep221_NewDocletAPI} class.<br>
 * <a href="https://hg.openjdk.java.net/jdk9/jdk9/langtools/file/65bfdabaab9c/test/jdk/javadoc/doclet">Jdk Doclet Set Suite</a>
 * <br>
 * 
 */
public class Jep221_NewDocletAPI implements Doclet {

    /**
     * Test comment
     * @param args main arguments
     * @throws IOException exception
     */
    public static void main(String[] args) throws IOException {
        Process process = Runtime.getRuntime().exec(
                "javadoc -doclet org.schlimm.java9.jep221.NewDocletAPI -classpath target/classes -docletpath target/classes -overviewfile overview.html -sourcepath src/main/java org.schlimm.java9.jep221");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        output.lines().forEach(l->System.out.println(l));
        error.lines().forEach(l->System.out.println(l));
    }

    Reporter reporter;

    @Override
    public void init(Locale locale, Reporter reporter) {
        reporter.print(Kind.NOTE, "Doclet using locale: " + locale);
        this.reporter = reporter;
    }

    public void printElement(DocTrees trees, Element e) {
        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            System.out.println("Element (" + e.getKind() + ": " + e + ") has the following comments:");
            System.out.println("Entire body: " + docCommentTree.getFullBody());
            System.out.println("Block tags: " + docCommentTree.getBlockTags());
        }
    }

    @Override
    public boolean run(DocletEnvironment docEnv) {
        reporter.print(Kind.NOTE, "overviewfile: " + overviewfile);
        // get the DocTrees utility class to access document comments
        DocTrees docTrees = docEnv.getDocTrees();

        for (TypeElement t : ElementFilter.typesIn(docEnv.getIncludedElements())) {
            System.out.println(t.getKind() + ":" + t);
            for (Element e : t.getEnclosedElements()) {
                printElement(docTrees, e);
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Example";
    }

    private String overviewfile;

    @Override
    public Set<? extends Option> getSupportedOptions() {
        Option[] options = { new Option() {
            private final List<String> someOption = Arrays.asList("-overviewfile", "--overview-file", "-o");

            @Override
            public int getArgumentCount() {
                return 1;
            }

            @Override
            public String getDescription() {
                return "an option with aliases";
            }

            @Override
            public Option.Kind getKind() {
                return Option.Kind.STANDARD;
            }

            @Override
            public List<String> getNames() {
                return someOption;
            }

            @Override
            public String getParameters() {
                return "file";
            }

            @Override
            public boolean process(String opt, List<String> arguments) {
                overviewfile = arguments.get(0);
                return true;
            }
        } };
        return new HashSet<>(Arrays.asList(options));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // support the latest release
        return SourceVersion.latest();
    }
}
