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

package org.logicng.cardinalityconstraints;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.logicng.configurations.ConfigurationType;
import org.logicng.datastructures.Assignment;
import org.logicng.datastructures.Tristate;
import org.logicng.formulas.CType;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Variable;
import org.logicng.handlers.NumberOfModelsHandler;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for the at-least-k configs.
 * @version 1.2
 * @since 1.0
 */
public class CCALKTest {

  @Rule
  public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

  private CCConfig[] configs;

  public CCALKTest() {
    configs = new CCConfig[3];
    configs[0] = new CCConfig.Builder().alkEncoding(CCConfig.ALK_ENCODER.TOTALIZER).build();
    configs[1] = new CCConfig.Builder().alkEncoding(CCConfig.ALK_ENCODER.MODULAR_TOTALIZER).build();
    configs[2] = new CCConfig.Builder().alkEncoding(CCConfig.ALK_ENCODER.CARDINALITY_NETWORK).build();
  }

  @Test
  public void testALK() {
    final FormulaFactory f = new FormulaFactory();
    int counter = 0;
    for (final CCConfig config : this.configs) {
      f.putConfiguration(config);
      testCC(10, 0, 1, f);
      testCC(10, 1, 1023, f);
      testCC(10, 2, 1013, f);
      testCC(10, 3, 968, f);
      testCC(10, 4, 848, f);
      testCC(10, 5, 638, f);
      testCC(10, 6, 386, f);
      testCC(10, 7, 176, f);
      testCC(10, 8, 56, f);
      testCC(10, 9, 11, f);
      testCC(10, 10, 1, f);
      testCC(10, 12, 0, f);
      softly.assertThat(f.newCCVariable().name()).as("New Var after Tests " + config).endsWith("_" + counter++);
    }
  }

  private void testCC(int numLits, int rhs, int expected, final FormulaFactory f) {
    final Variable[] problemLits = new Variable[numLits];
    for (int i = 0; i < numLits; i++)
      problemLits[i] = f.variable("v" + i);
    final SATSolver solver = MiniSat.miniSat(f);
    solver.add(f.cc(CType.GE, rhs, problemLits));
    if (expected != 0)
      softly.assertThat(solver.sat()).as("SolverSAT " + numLits + ", " + rhs + ", " + expected + ", " + f.configurationFor(ConfigurationType.CC_ENCODER)).isEqualTo(Tristate.TRUE);
    else
      softly.assertThat(solver.sat()).as("SolverSAT " + numLits + ", " + rhs + ", " + expected + ", " + f.configurationFor(ConfigurationType.CC_ENCODER)).isEqualTo(Tristate.FALSE);
    final List<Assignment> models = solver.enumerateAllModels(problemLits, new NumberOfModelsHandler(12000));
    softly.assertThat(models.size()).as("ModelSize " + numLits + ", " + rhs + ", " + expected + ", " + f.configurationFor(ConfigurationType.CC_ENCODER)).isEqualTo(expected);
    for (final Assignment model : models)
      softly.assertThat(model.positiveLiterals().size()).as("PosLits " + numLits + ", " + rhs + ", " + expected + ", " + f.configurationFor(ConfigurationType.CC_ENCODER)).isGreaterThanOrEqualTo(rhs);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCC1() {
    final FormulaFactory f = new FormulaFactory();
    final CCEncoder encoder = new CCEncoder(f);
    final int numLits = 100;
    final Variable[] problemLits = new Variable[numLits];
    for (int i = 0; i < numLits; i++)
      problemLits[i] = f.variable("v" + i);
    encoder.encode(f.cc(CType.GE, -1, problemLits));
  }

  @Test
  public void testToString() {
    FormulaFactory f = new FormulaFactory();
    softly.assertThat(configs[0].alkEncoder.toString()).isEqualTo("TOTALIZER");
    softly.assertThat(configs[1].alkEncoder.toString()).isEqualTo("MODULAR_TOTALIZER");
    softly.assertThat(configs[2].alkEncoder.toString()).isEqualTo("CARDINALITY_NETWORK");

    softly.assertThat(new CCTotalizer().toString()).isEqualTo("CCTotalizer");
    softly.assertThat(new CCModularTotalizer(f).toString()).isEqualTo("CCModularTotalizer");
    softly.assertThat(new CCCardinalityNetworks().toString()).isEqualTo("CCCardinalityNetworks");

    softly.assertThat(new CCALKTotalizer().toString()).isEqualTo("CCALKTotalizer");
    softly.assertThat(new CCALKModularTotalizer(f).toString()).isEqualTo("CCALKModularTotalizer");
    softly.assertThat(new CCALKCardinalityNetwork().toString()).isEqualTo("CCALKCardinalityNetwork");

    softly.assertThat(Arrays.asList(CCConfig.ALK_ENCODER.values()).contains(CCConfig.ALK_ENCODER.valueOf("MODULAR_TOTALIZER")));
  }
}
