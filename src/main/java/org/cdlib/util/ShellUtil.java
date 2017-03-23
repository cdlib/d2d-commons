package org.cdlib.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jferrie
 */
public class ShellUtil {

  public static void scp(String source, String target, String options) throws IOException {
    String command = String.format("scp %s %s %s", options, source, target);
    try {
      executeCommand(command);
    } catch (ShellCommandException e) {
      throw new IOException("File copy failed: ", e);
    }
  }

  public static void rsync(String source, String target, String options) throws IOException {
    String command = String.format("rsync %s %s %s", options, source, target);
    try {
      executeCommand(command);
    } catch (ShellCommandException e) {
      throw new IOException("rsync failed: ", e);
    }
  }

  public static void mv(String source, String target, String options) throws IOException {
    String command = String.format("mv %s %s %s", options, source, target);
    try {
      executeCommand(command);
    } catch (ShellCommandException e) {
      throw new IOException("mv failed: ", e);
    }
  }

  private static String executeCommand(String command) {
    StringBuilder output = new StringBuilder();
    Process p;
    try {
      p = Runtime.getRuntime().exec(command);
      p.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
      if (p.exitValue() != 0) {
        throw new ShellCommandException("Shell command failed");
      }
    } catch (IOException | InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return output.toString();
  }

  public static class ShellCommandException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of <code>RequestException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ShellCommandException(String msg) {
      super(msg);
    }

    public ShellCommandException(Exception e) {
      super(e);
    }

    public ShellCommandException() {
    }

  }

}
