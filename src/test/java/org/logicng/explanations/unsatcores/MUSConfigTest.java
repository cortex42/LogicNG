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

package org.logicng.explanations.unsatcores;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.logicng.transformations.cnf.CNFConfig;

/**
 * Unit tests for the class {@link MUSConfig}.
 * @version 1.2
 * @since 1.1
 */
public class MUSConfigTest {

  @Rule
  public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

  @Test
  public void testMUSConfiguration() {
    final MUSConfig config = new MUSConfig.Builder().algorithm(MUSConfig.Algorithm.valueOf("DELETION")).build();
    softly.assertThat(config.toString()).isEqualTo("MUSConfig{\nalgorithm=DELETION\n}\n");
    softly.assertThat(CNFConfig.Algorithm.values()).contains(CNFConfig.Algorithm.valueOf("TSEITIN"));
  }
}
