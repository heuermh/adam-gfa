/*

    adam-gfa  Graphical Fragment Assembly (GFA) support for ADAM.
    Copyright (c) 2017-2022 held jointly by the individual authors.

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

import com.github.heuermh.adam.gfa.sql.gfa1.Gfa1Record

import grizzled.slf4j.Logger

import org.apache.spark.{ SparkConf, SparkContext }

import org.apache.spark.rdd.RDD

import org.apache.spark.sql.SparkSession

import org.dishevelled.bio.assembly.gfa1.{
  Containment => JContainment,
  Gfa1Record => JGfa1Record,
  Link => JLink,
  Path => JPath,
  Segment => JSegment,
  Traversal => JTraversal
}

import scala.collection.JavaConverters._

/**
 * Convert Graphical Fragment Assembly (GFA) 1.0 to single DF in Parquet format.
 */
object Gfa1ToDataframe {
  val logger = Logger("com.github.heuermh.adam.gfa.Gfa1ToDataframe")

  def main(args: Array[String]) {

    if (args.length < 2) {
      System.err.println("at least two arguments required, e.g. in.gfa out.parquet")
      System.exit(1)
    }

    val conf = new SparkConf()
      .setAppName("Transform Graphical Fragment Assembly (GFA) 1.0 to single DF in Parquet format.")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.github.heuermh.adam.gfa.GfaKryoRegistrator")
      .set("spark.kryo.referenceTracking", "true")

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(sc.getConf).getOrCreate()
    import spark.implicits._

    def parseGfa1(s: String): Option[JGfa1Record] = s.charAt(0) match {
      case 'C' => Some(JContainment.valueOf(s))
      case 'L' => Some(JLink.valueOf(s))
      case 'P' => Some(JPath.valueOf(s))
      case 'S' => Some(JSegment.valueOf(s))
      case 't' => Some(JTraversal.valueOf(s))
      case _ => None
    }

    logger.info("Reading GFA 1.0 records from " + args(0))

    val gfa: RDD[JGfa1Record] = sc.textFile(args(0))
      .map(parseGfa1)
      .filter(_.isDefined)
      .map(_.get)

    logger.info("Read " + gfa.count() + " GFA 1.0 { C, L, P, S, t } records")

    val df = gfa
      .map(_ match {
        case c: JContainment => Some(Gfa1Record(c))
        case l: JLink => Some(Gfa1Record(l))
        case p: JPath => Some(Gfa1Record(p))
        case s: JSegment => Some(Gfa1Record(s))
        case t: JTraversal => Some(Gfa1Record(t))
        case _ => None
      })
      .filter(_.isDefined)
      .map(_.get)
      .toDF()

    logger.info("Writing data frame to Parquet " + args(1))

    df.write.parquet(args(1))
  }
}
