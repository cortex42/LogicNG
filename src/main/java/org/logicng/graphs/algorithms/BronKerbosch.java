///////////////////////////////////////////////////////////////////////////
//                   __                _      _   ________               //
//                  / /   ____  ____ _(_)____/ | / / ____/               //
//                 / /   / __ \/ __ `/ / ___/  |/ / / __                 //
//                / /___/ /_/ / /_/ / / /__/ /|  / /_/ /                 //
//               /_____/\____/\__, /_/\___/_/ |_/\____/                  //
//                           /____/                                      //
//                                                                       //
//               The Next Generation Logic Library                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  Copyright 2015-2016 Christoph Zengler                                //
//                                                                       //
//  Licensed under the Apache License, Version 2.0 (the "License");      //
//  you may not use this file except in compliance with the License.     //
//  You may obtain a copy of the License at                              //
//                                                                       //
//  http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                       //
//  Unless required by applicable law or agreed to in writing, software  //
//  distributed under the License is distributed on an "AS IS" BASIS,    //
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      //
//  implied.  See the License for the specific language governing        //
//  permissions and limitations under the License.                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

package org.logicng.graphs.algorithms;

import org.logicng.graphs.datastructures.Graph;
import org.logicng.graphs.datastructures.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class implements the Bron-Kerbosch-Algorithm, used to compute all maximal Cliques of a Graph. Requires that the
 * ids of the nodes are comparable.
 * @version 1.2
 * @since 1.2
 */
public class BronKerbosch<T extends Comparable<T>> {

  private Graph<T> g;
  private Comparator<Node<T>> nodeComparator;
  private Set<SortedSet<Node<T>>> cliques;

  /**
   * Constructor.
   * @param g the graph whose maximal cliques are to be computed
   */
  public BronKerbosch(Graph<T> g) {
    this.g = g;
    this.nodeComparator = new Comparator<Node<T>>() {
      @Override
      public int compare(Node<T> o1, Node<T> o2) {
        return o1.id().compareTo(o2.id());
      }
    };
    this.cliques = new HashSet<>();
  }

  /**
   * Computes the maximal cliques and returns them as a Set of SortedSets of Nodes.
   * @return the maximal cliques.
   */
  public Set<SortedSet<Node<T>>> compute() {
    cliques.clear();
    SortedSet<Node<T>> p = new TreeSet<>(this.nodeComparator);
    p.addAll(g.nodes());
    new TreeSet<Node<T>>();
    bk(new TreeSet<Node<T>>(this.nodeComparator), p, new TreeSet<Node<T>>(this.nodeComparator));

    return cliques;
  }

  private void bk(SortedSet<Node<T>> r, SortedSet<Node<T>> p, SortedSet<Node<T>> x) {
    if (p.isEmpty() && x.isEmpty()) {
      cliques.add(r);
      return;
    }

    SortedSet<Node<T>> pvx = new TreeSet<>(new NodeNeighbourComparator());
    pvx.addAll(p);
    pvx.addAll(x);
    Node<T> u = pvx.last();
    SortedSet<Node<T>> pwnu = new TreeSet<>(this.nodeComparator);
    pwnu.addAll(p);
    pwnu.removeAll(u.neighbours());
    for (Node<T> v : pwnu) {
      SortedSet<Node<T>> nr = new TreeSet<>(this.nodeComparator);
      nr.addAll(r);
      nr.add(v);
      SortedSet<Node<T>> np = new TreeSet<>(this.nodeComparator);
      SortedSet<Node<T>> nx = new TreeSet<>(this.nodeComparator);
      for (Node<T> neigh : v.neighbours()) {
        if (p.contains(neigh)) {
          np.add(neigh);
        }
        if (x.contains(neigh)) {
          nx.add(neigh);
        }
      }
      bk(nr, np, nx);
      p.remove(v);
      x.add(v);
    }
  }

  /**
   * Returns the maximal cliques computed with the last call to compute() as a List of Lists of T.
   * @return the maximal cliques
   */
  public List<List<T>> getCliquesAsTLists() {
    List<List<T>> result = new ArrayList<>();
    for (Set<Node<T>> clique : cliques) {
      List<T> curList = new ArrayList<>();
      for (Node<T> node : clique) {
        curList.add(node.id());
      }
      result.add(curList);
    }
    return result;
  }

  /**
   * A comparator between nodes, that compares them by number of neighbours.
   * @version 1.2
   * @since 1.2
   */
  private class NodeNeighbourComparator implements Comparator<Node<T>> {

    @Override
    public int compare(Node<T> n1, Node<T> n2) {
      if (n1.neighbours().size() > n2.neighbours().size()) return 1;
      else if (n1.neighbours().size() < n2.neighbours().size()) return -1;
      else return nodeComparator.compare(n1, n2);
    }
  }
}
