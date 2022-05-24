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

import grizzled.slf4j.Logger

import org.apache.spark.{ SparkConf, SparkContext }

import org.apache.spark.sql.SparkSession

/**
 * Transform GFA 1.0 traversals in Parquet format to edges.txt format for Cytoscape.
 */
object TraversalsToCytoscapeEdges {
  val logger = Logger("com.github.heuermh.adam.gfa.TraversalsToCytoscapeEdges")

  def main(args: Array[String]) {

    // todo: add argument for single file
    if (args.length < 2) {
      System.err.println("at least two arguments required, e.g. in.parquet out[-edges.txt]")
      System.exit(1)
    }

    val conf = new SparkConf()
      .setAppName("Transform Graphical Fragment Assembly (GFA) 1.0 traversals in Parquet format to edges.txt format for Cytoscape.")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.github.heuermh.adam.gfa.GfaKryoRegistrator")
      .set("spark.kryo.referenceTracking", "true")

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(sc.getConf).getOrCreate()
    import spark.implicits._

    // convert traversals to edge.txt format
    logger.info("Reading GFA 1.0 traversals from " + args(0) + " in Parquet format")
    val traversals = spark.sql("select source.id as source, source.orientation as sourceOrientation, target.id as target, target.orientation as targetOrientation, recordType as interaction, pathName, ordinal, overlap from parquet.`" + args(0) + "`")

    traversals
      .write
      .option("header", true)
      .option("delimiter", "\t")
      .option("emptyValue", "")
      .csv(args(1) + "-edges.txt")

    logger.info("Wrote " + traversals.count() + " traversal nodes")
  }
}
