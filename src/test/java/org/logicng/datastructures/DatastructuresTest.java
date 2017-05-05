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

import org.assertj.core.api.Assertions;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit tests for the package data structures.
 * @version 1.1
 * @since 1.1
 */
public class DatastructuresTest {

  @Rule
  public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

  @Test
  public void testTristate() {
    softly.assertThat(Tristate.TRUE).isEqualTo(Tristate.valueOf("TRUE"));
    softly.assertThat(Tristate.FALSE).isEqualTo(Tristate.valueOf("FALSE"));
    softly.assertThat(Tristate.UNDEF).isEqualTo(Tristate.valueOf("UNDEF"));
  }

  @Test
  public void testEncodingAuxiliaryVariable() {
    EncodingAuxiliaryVariable eav = new EncodingAuxiliaryVariable("var", false);
    Assertions.assertThat(eav.toString()).isEqualTo("var");
  }
}
