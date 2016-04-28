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

/**
 * Formula types for LogicNG.
 * @version 1.0
 * @since 1.0
 */
public enum FType {
  PBC((byte) 0x00),
  EQUIV((byte) 0x01),
  IMPL((byte) 0x02),
  OR((byte) 0x03),
  AND((byte) 0x04),
  NOT((byte) 0x05),
  LITERAL((byte) 0x06),
  TRUE((byte) 0x07),
  FALSE((byte) 0x08),
  NONE((byte) 0x42);

  private byte precedence;

  /**
   * Constructs a new formula type with a given precedence and syntax string
   * @param precedence the precedence
   */
  FType(final byte precedence) {
    this.precedence = precedence;
  }

  /**
   * Returns the precedence of this formula type.
   * @return the precedence of this formula type
   */
  public byte precedence() {
    return this.precedence;
  }
}
