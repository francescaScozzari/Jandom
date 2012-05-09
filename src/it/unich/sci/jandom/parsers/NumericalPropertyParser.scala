/**
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
 *
 * (c) 2012 Gianluca Amato
 */

package it.unich.sci.jandom
package parsers

import targets.Environment
import domains.NumericalDomain
import domains.NumericalProperty

import scala.util.parsing.combinator.JavaTokenParsers

/**
 * @author Gianluca Amato <amato@sci.unich.it>
 *
 */
class PropertyParser extends JavaTokenParsers with LinearExpressionParser with LinearConditionParser  {
	val env = Environment()
	var closedVariables = false;
    
    protected val variable: Parser[Int] = new Parser[Int] {
      def apply(in: Input) = ident(in) match {
        case Success(i,in1) => env.getBinding(i) match {
        	case Some(v) => Success(v,in1)
        	case None => if (closedVariables) Failure("Unexpected variable",in1) else Success(env.addBinding(i),in1)
        }}}
    
	def parse[Property<: NumericalProperty[Property]] (s: String, domain: NumericalDomain[Property]) = parseAll(condition,s).get.analyze(domain.full(env.size))
}
