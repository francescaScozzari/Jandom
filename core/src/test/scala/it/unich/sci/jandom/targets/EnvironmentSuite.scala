/**
 * Copyright 2013 Gianluca Amato
 * 
 * This file is part of JANDOM: JVM-based Analyzer for Numerical DOMains
 * JANDOM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JANDOM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty ofa
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JANDOM.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unich.sci.jandom
package targets

import org.scalatest.FunSuite

/**
 * The test suite for the Environment class
 * @author Gianluca Amato <amato@sci.unich.it>
 *
 */

class EnvironmentSuite  extends FunSuite {
  test ("Environment standard operations") {
    val env: Environment = new Environment()
    val v1 = env.addBinding("prova")
    val v2 = env.getBindingOrAdd("micio")
    expectResult(2) { env.size }
    expectResult(Some(v1)) { env.getBinding("prova")}
    expectResult(v1) { env("prova")}
    expectResult("micio") { env(v2) }
    expectResult(Seq("prova", "micio")) { env.getNames }
  }
  
  test ("Environment companion object constructors") {
    val env = Environment("A","B","C")
    expectResult ("C") { env(env("C")) }
  }
  
  test("Environment equality") {
    val env1 = Environment("x","y","z")
    val env2 = Environment("x","y","z")
    expectResult (env1) { env2 }
    val env3 = Environment("a","b","c")
    expectResult (false) { env1 == env3 }
  }
}