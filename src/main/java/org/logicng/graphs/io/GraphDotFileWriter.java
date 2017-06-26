package org.logicng.graphs.io;

import org.logicng.graphs.datastructures.Graph;
import org.logicng.graphs.datastructures.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A dot file writer for a graph.  Writes the internal data structure of the formula to a dot file.
 * @version 1.2
 * @since 1.2
 */
public class GraphDotFileWriter {

  /**
   * Private constructor.
   */
  private GraphDotFileWriter() {
    // Intentionally left empty.
  }

  /**
   * Writes a given formula's internal data structure as a dimacs file.
   * @param fileName the file name of the dimacs file to write
   * @param graph    the graph
   * @throws IOException if there was a problem writing the file
   */
  public static void write(final String fileName, final Graph graph) throws IOException {
    write(new File(fileName.endsWith(".dot") ? fileName : fileName + ".dot"), graph);
  }

  /**
   * Writes a given graph's internal data structure as a dot file.
   * @param file  the file of the dot file to write
   * @param graph the graph
   * @throws IOException if there was a problem writing the file
   */
  public static <T> void write(final File file, final Graph<T> graph) throws IOException {
    final StringBuilder sb = new StringBuilder("strict graph {\n");

    Set<Node<T>> doneNodes = new LinkedHashSet<>();
    for (Node<T> d : graph.nodes()) {
      for (Node<T> n : d.neighbours()) {
        if (!doneNodes.contains(n)) {
          sb.append("  ").append(d.id()).append(" -- ").append(n.id()).append("\n");
        }
      }
      doneNodes.add(d);
    }
    for (Node<T> d : graph.nodes()) {
      if (d.neighbours().isEmpty()) {
        sb.append("  ").append(d.id()).append("\n");
      }
    }
    sb.append("}");

    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")))) {
      writer.append(sb);
      writer.flush();
    }
  }
}
