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

package org.logicng.formulas;

import org.logicng.datastructures.Assignment;

import static org.logicng.formulas.cache.TransformationCacheEntry.NNF;

/**
 * Boolean equivalence.
 * @version 1.0
 * @since 1.0
 */
public final class Equivalence extends BinaryOperator {

  /**
   * Private constructor (initialize with {@code createNAryOperator()})
   * @param left  the left-hand side operand
   * @param right the right-hand side operand
   * @param f     the factory which created this instance
   */
  Equivalence(final Formula left, final Formula right, final FormulaFactory f) {
    super(FType.EQUIV, left, right, f);
  }

  @Override
  public boolean evaluate(final Assignment assignment) {
    return left.evaluate(assignment) == right.evaluate(assignment);
  }

  @Override
  public Formula restrict(final Assignment assignment) {
    return f.equivalence(left.restrict(assignment), right.restrict(assignment));
  }

  @Override
  public Formula nnf() {
    Formula nnf = this.transformationCache.get(NNF);
    if (nnf == null) {
      nnf = f.or(f.and(left.nnf(), right.nnf()), f.and(f.not(left).nnf(), f.not(right).nnf()));
      this.transformationCache.put(NNF, nnf);
    }
    return nnf;
  }

  @Override
  public int hashCode() {
    if (this.hashCode == 0)
      this.hashCode = 41 * (left.hashCode() + right.hashCode());
    return this.hashCode;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this)
      return true;
    if (other instanceof Formula && this.f == ((Formula) other).f)
      return false; // the same formula factory would have produced a == object
    if (other instanceof Equivalence) {
      Equivalence otherEq = (Equivalence) other;
      return this.left.equals(otherEq.left) && this.right.equals(otherEq.right) ||
              this.left.equals(otherEq.right) && this.right.equals(otherEq.left);
    }
    return false;
  }
}
