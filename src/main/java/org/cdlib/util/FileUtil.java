package org.cdlib.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

/**
 * Convenience for reading and writing files.
 * 
 * Throws IllegalArgumentException in lieu of IOException, assuming that the problem is almost
 * always a bad path.
 *
 */
public class FileUtil {
  
  private FileUtil() {}

  public static String read(String pathOnClasspath) {
    try {
      return readFromClasspath(pathOnClasspath, false);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read file on classpath" + pathOnClasspath, e);
    }
  }

  public static String readStripEOL(String pathOnClasspath) {
    try {
      return readFromClasspath(pathOnClasspath, true);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read file on classpath" + pathOnClasspath, e);
    }
  }

  public static void write(Path filePath, String content) {
    if (!Files.isWritable(filePath)) {
      throw new IllegalArgumentException("File path " + filePath + " is not writable.");
    }
    try {
      Files.write(filePath, content.getBytes());
    } catch (IOException e) {
      throw new IllegalArgumentException("File path " + filePath + " is not writable.", e);
    }
  }

  public static void write(String pathOnClasspath, String content) {
    try {
      File file = new ClassPathResource(pathOnClasspath).getFile();
      write(file.toPath(), content);
    } catch (IOException e) {
      throw new IllegalArgumentException("Class path " + pathOnClasspath + " is not writable.", e);
    }
  }

  private static String buildString(List<String> inputList) {
    StringBuilder builder = new StringBuilder();
    for (String s : inputList) {
      builder.append(s + "\n");
    }
    return builder.toString();
  }

  private static String buildStringStripEOL(List<String> inputList) {
    StringBuilder builder = new StringBuilder();
    for (String s : inputList) {
      builder.append(s);
    }
    return builder.toString();
  }

  private static String read(Path path, boolean stripEOL) {
    if (!(Files.isReadable(path) && Files.isRegularFile(path))) {
      throw new IllegalArgumentException("Unable to read file " + path);
    }
    List<String> inputList = new ArrayList<>();
    try {
      inputList = Files.readAllLines(path);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unexpected error reading file " + path, e);
    }
    if (stripEOL) {
      return buildStringStripEOL(inputList);
    } else {
      return buildString(inputList);
    }
  }

  private static String readFromClasspath(String pathOnClasspath, boolean stripEOL) throws IOException {
    File file = new ClassPathResource(pathOnClasspath).getFile();
    return read(file.toPath(), stripEOL);
  }

}
