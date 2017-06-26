package org.logicng.graphs.algorithms;

import org.logicng.graphs.datastructures.Graph;
import org.logicng.graphs.datastructures.Node;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class implements an algorithm to compute the connected components of a graph.
 * @version 1.2
 * @since 1.2
 */
public class ConnectedComponentsComputer<T> {

  private Graph<T> g;
  private Set<Set<Node<T>>> connectedComponents;
  private Set<Node<T>> unmarkedNodes;

  /**
   * Constructor.
   * @param g the graph for which components shall be computed.
   */
  public ConnectedComponentsComputer(Graph<T> g) {
    this.g = g;
    connectedComponents = new LinkedHashSet<>();
  }

  /**
   * Computes the Set of connected components of the graph, where each component is represented by a set of nodes.
   * @return the set of sets of nodes representing the connected components
   */
  public Set<Set<Node<T>>> compute() {
    connectedComponents.clear();
    unmarkedNodes = new LinkedHashSet<>(g.nodes());

    while (!unmarkedNodes.isEmpty()) {
      Set<Node<T>> connectedComp = new LinkedHashSet<>();
      deepFirstSearch(unmarkedNodes.iterator().next(), connectedComp);
      connectedComponents.add(connectedComp);
    }

    return connectedComponents;
  }

  private void deepFirstSearch(Node<T> v, Set<Node<T>> component) {
    component.add(v);
    this.unmarkedNodes.remove(v);
    for (Node<T> neigh : v.neighbours()) {
      if (unmarkedNodes.contains(neigh)) {
        deepFirstSearch(neigh, component);
      }
    }
  }

}
