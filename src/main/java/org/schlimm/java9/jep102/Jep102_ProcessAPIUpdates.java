package org.schlimm.java9.jep102;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <a href="https://openjdk.java.net/jeps/102">JEP102: Process API</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/core/process-api1.htm#JSCOR-GUID-6FAB2491-FD4E-42B4-A883-DCD181A1CE3E">API Description</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/core/process-api1.htm#JSCOR-GUID-A6A4B56A-7F3F-4ACD-930A-4E4AA3A930E7">Examples</a>
 */
public class Jep102_ProcessAPIUpdates {

    /**
     * Run example that starts some grep processes.
     * @param args command line arguments not required
     */
    public static void main(String[] args) {
        try {
            startSomeGrepProcesses();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public void startSomeGrepProcesses() throws IOException, InterruptedException {
        List<ProcessBuilder> greps = new ArrayList<>();
        greps.add(new ProcessBuilder("/bin/sh", "-c", "grep -c \"java\" *"));
        greps.add(new ProcessBuilder("/bin/sh", "-c", "grep -c \"Process\" *"));
        greps.add(new ProcessBuilder("/bin/sh", "-c", "grep -c \"onExit\" *"));
        startSeveralProcesses (greps, Jep102_ProcessAPIUpdates::printGrepResults);      
        System.out.println("\nPress enter to continue ...\n");
        System.in.read();  
      }

      static void startSeveralProcesses (
        List<ProcessBuilder> pBList,
        Consumer<Process> onExitMethod)
        throws InterruptedException {
        System.out.println("Number of processes: " + pBList.size());
        pBList.stream().forEach(
          pb -> {
            try {
              Process p = pb.start();
              System.out.printf("Start %d, %s%n",
                p.pid(), p.info().commandLine().orElse("<na>"));
              p.onExit().thenAccept(onExitMethod);
            } catch (IOException e) {
              System.err.println("Exception caught");
              e.printStackTrace();
            }
          }
        );
      }
      
      static void printGrepResults(Process p) {
        System.out.printf("Exit %d, status %d%n%s%n%n",
          p.pid(), p.exitValue(), output(p.getInputStream()));
      }

      private static String output(InputStream inputStream) {
        String s = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
          s = br.lines().collect(Collectors.joining(System.getProperty("line.separator")));
        } catch (IOException e) {
          System.err.println("Caught IOException");
          e.printStackTrace();
        }
        return s;
      }


}
