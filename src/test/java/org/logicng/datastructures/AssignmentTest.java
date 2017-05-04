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

package org.logicng.datastructures;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.logicng.formulas.F;
import org.logicng.formulas.Formula;
import org.logicng.formulas.Literal;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit tests for the class {@link Assignment}.
 * @version 1.2
 * @since 1.0
 */
public class AssignmentTest {

  @Rule
  public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

  @Test
  public void testCreators() {
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.X, F.Y))).isNotNull();
  }

  @Test
  public void testSize() {
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.X, F.Y), true).size()).isEqualTo(4);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), false).size()).isEqualTo(4);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.NB)).size()).isEqualTo(2);
  }

  @Test
  public void testPositiveLiterals() {
    Literal[] a = {F.A, F.B, F.X, F.Y};
    Assignment ass1 = new Assignment(Arrays.asList(a), false);
    softly.assertThat(ass1.positiveLiterals()).isEqualTo(Arrays.asList(a));
    ass1 = new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY));
    softly.assertThat(ass1.positiveLiterals()).isEqualTo(Arrays.asList(F.A, F.B));
    ass1 = new Assignment(Arrays.asList(F.NA, F.NB, F.NX, F.NY));
    softly.assertThat(ass1.positiveLiterals().size()).isEqualTo(0);
  }

  @Test
  public void testNegativeLiterals() {
    Literal[] a = {F.NA, F.NB, F.NX, F.NY};
    Assignment ass = new Assignment(Arrays.asList(a));
    softly.assertThat(ass.negativeLiterals()).isEqualTo(Arrays.asList(a));
    ass = new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY));
    softly.assertThat(ass.negativeLiterals()).isEqualTo(Arrays.asList(F.NX, F.NY));
    ass = new Assignment(Arrays.asList(F.A, F.B, F.X, F.Y));
    softly.assertThat(ass.negativeLiterals().size()).isEqualTo(0);
  }

  @Test
  public void testNegativeVariables() {
    Literal[] a = {F.A, F.B, F.X, F.Y};
    Literal[] na = {F.NA, F.NB, F.NX, F.NY};
    Assignment ass = new Assignment(Arrays.asList(na));
    softly.assertThat(ass.negativeVariables()).isEqualTo(Arrays.asList(a));
    ass = new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY));
    softly.assertThat(ass.negativeVariables()).isEqualTo(Arrays.asList(F.X, F.Y));
    ass = new Assignment(Arrays.asList(F.A, F.B, F.X, F.Y));
    softly.assertThat(ass.negativeVariables().size()).isEqualTo(0);
  }

  @Test
  public void testAddLiteral() {
    Assignment ass = new Assignment();
    ass.addLiteral(F.A);
    ass.addLiteral(F.B);
    ass.addLiteral(F.NX);
    ass.addLiteral(F.NY);
    softly.assertThat(ass.positiveLiterals()).isEqualTo(Arrays.asList(F.A, F.B));
    softly.assertThat(ass.negativeLiterals()).isEqualTo(Arrays.asList(F.NX, F.NY));
  }

  @Test
  public void testEvaluateLit() {
    Assignment ass = new Assignment(Arrays.asList(F.A, F.NX));
    softly.assertThat(ass.evaluateLit(F.A)).isTrue();
    softly.assertThat(ass.evaluateLit(F.NX)).isTrue();
    softly.assertThat(ass.evaluateLit(F.NB)).isTrue();
    softly.assertThat(ass.evaluateLit(F.NA)).isFalse();
    softly.assertThat(ass.evaluateLit(F.X)).isFalse();
    softly.assertThat(ass.evaluateLit(F.B)).isFalse();
  }

  @Test
  public void testRestrictLit() {
    Assignment ass = new Assignment(Arrays.asList(F.A, F.NX));
    softly.assertThat(ass.restrictLit(F.A)).isEqualTo(F.TRUE);
    softly.assertThat(ass.restrictLit(F.NX)).isEqualTo(F.TRUE);
    softly.assertThat(ass.restrictLit(F.NA)).isEqualTo(F.FALSE);
    softly.assertThat(ass.restrictLit(F.X)).isEqualTo(F.FALSE);
    softly.assertThat(ass.restrictLit(F.B)).isNull();
    softly.assertThat(ass.restrictLit(F.NB)).isNull();
  }

  @Test
  public void testFormula() throws ParserException {
    PropositionalParser p = new PropositionalParser(F.f);
    softly.assertThat(new Assignment(Collections.singletonList(F.A)).formula(F.f)).isEqualTo(p.parse("a"));
    softly.assertThat(new Assignment(Collections.singletonList(F.NA)).formula(F.f)).isEqualTo(p.parse("~a"));
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B)).formula(F.f)).isEqualTo(p.parse("a & b"));
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY)).formula(F.f)).isEqualTo(p.parse("a & b & ~x & ~y"));
  }

  @Test
  public void testFastEvaluable() {
    Assignment ass = new Assignment(Arrays.asList(F.A, F.NX), false);
    softly.assertThat(ass.fastEvaluable()).isFalse();
    ass.convertToFastEvaluable();
    softly.assertThat(ass.fastEvaluable()).isTrue();
    softly.assertThat(ass.positiveLiterals()).isEqualTo(Collections.singletonList(F.A));
    softly.assertThat(ass.negativeLiterals()).isEqualTo(Collections.singletonList(F.NX));
    softly.assertThat(ass.negativeVariables()).isEqualTo(Collections.singletonList(F.X));
    ass.addLiteral(F.NB);
    ass.addLiteral(F.Y);
    softly.assertThat(ass.positiveLiterals()).isEqualTo(Arrays.asList(F.A, F.Y));
    softly.assertThat(ass.negativeLiterals()).isEqualTo(Arrays.asList(F.NB, F.NX));
    softly.assertThat(ass.negativeVariables()).isEqualTo(Arrays.asList(F.X, F.B));
    softly.assertThat(ass.evaluateLit(F.Y)).isTrue();
    softly.assertThat(ass.evaluateLit(F.B)).isFalse();
    softly.assertThat(ass.restrictLit(F.NB)).isEqualTo(F.TRUE);
    softly.assertThat(ass.restrictLit(F.X)).isEqualTo(F.FALSE);
    softly.assertThat(ass.restrictLit(F.C)).isEqualTo(null);
    softly.assertThat(ass.formula(F.f)).isEqualTo(F.f.and(F.A, F.NX, F.NB, F.Y));
    ass = new Assignment(Arrays.asList(F.A, F.NX), true);
    softly.assertThat(ass.fastEvaluable()).isTrue();
    ass.convertToFastEvaluable();
    softly.assertThat(ass.fastEvaluable()).isTrue();
  }

  @Test
  public void testHashCode() {
    Assignment ass = new Assignment();
    ass.addLiteral(F.A);
    ass.addLiteral(F.B);
    ass.addLiteral(F.NX);
    ass.addLiteral(F.NY);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY)).hashCode()).isEqualTo(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY)).hashCode());
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY)).hashCode()).isEqualTo(ass.hashCode());
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY)).hashCode()).isEqualTo(ass.hashCode());
  }

  @Test
  public void testEquals() {
    Assignment ass = new Assignment();
    ass.addLiteral(F.A);
    ass.addLiteral(F.B);
    ass.addLiteral(F.NX);
    ass.addLiteral(F.NY);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), false)).isEqualTo(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), false));
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), false)).isEqualTo(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), true));
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), true)).isEqualTo(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), false));
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), true)).isEqualTo(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY), true));
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY))).isEqualTo(ass);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY))).isEqualTo(ass);
    softly.assertThat(ass).isEqualTo(ass);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX))).isNotEqualTo(ass);
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY, F.C))).isNotEqualTo(ass);
    softly.assertThat(ass).isNotNull();
    softly.assertThat(ass).isNotEqualTo(F.TRUE);
  }

  @Test
  public void testBlockingClause() {
    Assignment ass = new Assignment();
    ass.addLiteral(F.A);
    ass.addLiteral(F.B);
    ass.addLiteral(F.NX);
    ass.addLiteral(F.NY);
    List<Literal> lits = new ArrayList<>();
    lits.add(F.A);
    lits.add(F.X);
    lits.add(F.C);
    Formula bc = ass.blockingClause(F.f, lits);
    softly.assertThat(bc.containsVariable(F.C)).isFalse();
    softly.assertThat(bc.toString()).isEqualTo("~a | x");
  }

  @Test
  public void testToString() {
    softly.assertThat(new Assignment().toString()).isEqualTo("Assignment{pos=[], neg=[]}");
    softly.assertThat(new Assignment(Collections.singletonList(F.A)).toString()).isEqualTo("Assignment{pos=[a], neg=[]}");
    softly.assertThat(new Assignment(Collections.singletonList(F.NA)).toString()).isEqualTo("Assignment{pos=[], neg=[~a]}");
    softly.assertThat(new Assignment(Arrays.asList(F.A, F.B, F.NX, F.NY, F.C)).toString()).isEqualTo("Assignment{pos=[a, b, c], neg=[~x, ~y]}");
  }
}
