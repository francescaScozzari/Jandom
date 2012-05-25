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
package targets

import domains.NumericalProperty

/**
 * The abstract class for targets.
 * @author Gianluca Amato <amato@sci.unich.it>
 *
 */
trait Target {
  /**
   * Abstract type for program points
   */
  type ProgramPoint

  /**
   * Abstract type for widening program points
   */
  type WideningPoint = ProgramPoint

  /**
   * The type of the given target
   */
  type Tgt <: Target

  /**
   * The class for program annotations
   */
  type Annotation[Property] <: scala.collection.mutable.Map[ProgramPoint, Property] 
  
  /**
   * Returns an empty annotation
   */
  def getAnnotation[Property]: Annotation[Property]
  
  /**
   * An alias for parameters in input by the analyzer
   */
  protected type Parameters[Property <: NumericalProperty[Property]] = targets.Parameters[Property, Tgt]
  
  /** 
   * Returns the size of the target in terms of the number of program points
   */  
  def size: Int
  
  /**
   * Perform a static analysis over the target.
   * @tparam Property the type of the property we want to analyze
   * @param param the parameters which drive the analyzer
   * @param bb the blackboard where it is possible to put annotation during the analysis
   */
  def analyze[Property <: NumericalProperty[Property]](params: Parameters[Property]): Annotation[Property]
}