/*

    gfa-adam  Graphical Fragment Assembly (GFA) 2.0 support for ADAM.
    Copyright (c) 2017 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package com.github.heuermh.adam.gfa

import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.SparkContext._

import org.apache.spark.rdd.RDD

/**
 * Graphical Fragment Assembly (GFA) 2.0 support for ADAM.
 */
object Gfa {
  def main(args: Array[String]) {
    System.out.println(args(0))

    if (args.length < 2) {
      System.err.println("at least two arguments required, e.g. in.gfa2 out.gfa2")
      System.exit(1)
    }

    val conf = new SparkConf()
      .setAppName("Graphical Fragment Assembly (GFA) 2.0 support for ADAM.")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.github.heuermh.adam.gfa.GfaKryoRegistrator")
      .set("spark.kryo.referenceTracking", "true")

    val sc = new SparkContext(conf)

    def parse(s: String): Option[GfaRecord] = s.charAt(0) match {
      case 'E' => Some(Edge.valueOf(s))
      case 'F' => Some(Fragment.valueOf(s))
      case 'G' => Some(Gap.valueOf(s))
      case 'H' => Some(Header.valueOf(s))
      case 'O' => Some(Path.valueOf(s))
      case 'S' => Some(Segment.valueOf(s))
      case 'U' => Some(Set.valueOf(s))
      case _ => None
    }

    val gfa: RDD[GfaRecord] = sc.textFile(args(0))
      .map(parse)
      .filter(_.isDefined)
      .map(_.get)

    System.out.println("read " + gfa.count() + " gfa records")

    gfa.saveAsTextFile(args(1))
  }
}
