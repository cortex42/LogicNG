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

package org.logicng.graphs.datastructures;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A generic node of a graph.
 * @version 1.2
 * @since 1.2
 */
public class Node<T> {

  private Graph<T> g;
  private T id;
  private Set<Node<T>> neighbours;

  /**
   * Constructor.
   * @param id the id of the node
   * @param g  the graph the node will be a part of
   */
  Node(T id, Graph g) {
    this.id = id;
    this.g = g;
    this.neighbours = new LinkedHashSet<>();
  }

  /**
   * Adds the given node to the neighbours of this node.
   * @param o the given node
   */
  void connectTo(Node<T> o) {
    if (o.equals(this)) {
      return;
    }
    neighbours.add(o);
  }

  /**
   * Removes the given node from the neighbours of this node.
   * @param o the given node
   */
  void disconnectFrom(Node<T> o) {
    neighbours.remove(o);
  }

  /**
   * Returns the id of the node.
   * @return the id of the node
   */
  public T id() {
    return id;
  }

  /**
   * Returns the neighbours of the node.
   * @return the neighbours of the node
   */
  public Set<Node<T>> neighbours() {
    return new LinkedHashSet<>(neighbours);
  }

  /**
   * Returns the graph to which the node belongs.
   * @return the node's graph
   */
  public Graph<T> graph() {
    return g;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Node{id=").append(id).append(", neighbours:");
    for (Node neighbour : neighbours) {
      sb.append(neighbour.id()).append(",");
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append("}");
    return sb.toString();
  }
}
