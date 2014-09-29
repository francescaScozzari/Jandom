/**
 * Copyright 2013, 2014 Gianluca Amato <gamato@unich.it>
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

package it.unich.jandom.parsers

import org.scalatest.FunSuite

import it.unich.jandom.domains.numerical.LinearForm
import it.unich.jandom.targets.Environment
import it.unich.jandom.targets.linearcondition.AndCond
import it.unich.jandom.targets.linearcondition.AtomicCond
import it.unich.jandom.targets.NumericExpression._

/**
 * Test suite for LinearExpressionParser.
 * @author Gianluca Amato <gamato@unich.it>
 */
class LinearConditionParserSuite extends FunSuite {
  object LocalParser extends LinearConditionParser with NumericExpressionParser {
    val env = Environment()
    val variable = ident ^^ { env.getBindingOrAdd(_) }
    def parseExpr(s: String) = parseAll(condition, s)
  }

  test("Linear expressions") {
    val expParsed = LocalParser.parseExpr("3*x+y-z==0 && x<=z").get
    val exp1Build = LinearForm(0, 3, 1, -1)
    val exp2Build = LinearForm(0, 1, 0, -1)
    val expCond = AndCond(AtomicCond(exp1Build, AtomicCond.ComparisonOperators.EQ), AtomicCond(exp2Build, AtomicCond.ComparisonOperators.LTE))
    assertResult(expCond) { expParsed }
  }
  
  test("Non-linear expressions") {    
    val x = VariableExpression[Double](0)
    assertResult( AtomicCond(x*x, AtomicCond.ComparisonOperators.LTE)) {  LocalParser.parseExpr("x*x <= 0").get } 
    assertResult( AtomicCond(x*x, AtomicCond.ComparisonOperators.GTE)) {  LocalParser.parseExpr("0 <= x*x").get }
  }
}
