/*

    adam-gfa  Graphical Fragment Assembly (GFA) support for ADAM.
    Copyright (c) 2017-2020 held jointly by the individual authors.

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

import grizzled.slf4j.Logger

import org.apache.spark.{ SparkConf, SparkContext }

import org.apache.spark.rdd.RDD

import org.bdgenomics.adam.util.TextRddWriter

import org.dishevelled.bio.assembly.gfa1.Gfa1Record

/**
 * Graphical Fragment Assembly (GFA) 1.0 support for ADAM.
 */
object Gfa1 {
  val logger = Logger("com.github.heuermh.adam.gfa.Gfa1")

  def main(args: Array[String]) {

    if (args.length < 2) {
      System.err.println("at least two arguments required, e.g. in.gfa out.gfa")
      System.exit(1)
    }

    val conf = new SparkConf()
      .setAppName("Graphical Fragment Assembly (GFA) 1.0 support for ADAM.")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.github.heuermh.adam.gfa.GfaKryoRegistrator")
      .set("spark.kryo.referenceTracking", "true")

    val sc = new SparkContext(conf)

    def parseGfa1(s: String): Option[Gfa1Record] = s.charAt(0) match {
      case 'C' => Some(org.dishevelled.bio.assembly.gfa1.Containment.valueOf(s))
      case 'H' => Some(org.dishevelled.bio.assembly.gfa1.Header.valueOf(s))
      case 'L' => Some(org.dishevelled.bio.assembly.gfa1.Link.valueOf(s))
      case 'P' => Some(org.dishevelled.bio.assembly.gfa1.Path.valueOf(s))
      case 'S' => Some(org.dishevelled.bio.assembly.gfa1.Segment.valueOf(s))
      case 't' => Some(org.dishevelled.bio.assembly.gfa1.Traversal.valueOf(s))
      case _ => None
    }

    logger.info("Reading GFA 1.0 records from " + args(0))

    val gfa: RDD[Gfa1Record] = sc.textFile(args(0))
      .map(parseGfa1)
      .filter(_.isDefined)
      .map(_.get)

    logger.info("Read " + gfa.count() + " GFA 1.0 records")

    TextRddWriter.writeTextRdd(gfa, outputPath = args(1), asSingleFile = true, disableFastConcat = false, optHeaderPath = None)
  }
}
