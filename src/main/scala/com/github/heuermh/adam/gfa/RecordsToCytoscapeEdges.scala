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
 * Transform GFA 1.0 link and traversal records in Parquet format to edges.txt format for Cytoscape.
 */
object RecordsToCytoscapeEdges {
  val logger = Logger("com.github.heuermh.adam.gfa.RecordsToCytoscapeEdges")

  def main(args: Array[String]) {

    if (args.length < 2) {
      System.err.println("at least two arguments required, e.g. in.parquet out[-link-edges.txt,-traversal-edges.txt]")
      System.exit(1)
    }

    val conf = new SparkConf()
      .setAppName("Transform Graphical Fragment Assembly (GFA) 1.0 link and traversal records in Parquet format to edges.txt format for Cytoscape.")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.github.heuermh.adam.gfa.GfaKryoRegistrator")
      .set("spark.kryo.referenceTracking", "true")

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(sc.getConf).getOrCreate()
    import spark.implicits._

    // convert link records to edge.txt format
    logger.info("Reading GFA 1.0 link records from " + args(0) + " in Parquet format")
    val links = spark.sql("select source.id as source, source.orientation as sourceOrientation, target.id as target, target.orientation as targetOrientation, recordType as interaction, overlap, mappingQuality, mismatchCount from parquet.`" + args(0) + "` where recordType = 'L'")

    links
      .write
      .option("header", true)
      .option("delimiter", "\t")
      .option("emptyValue", "")
      .csv(args(1) + "-link-edges.txt")

    logger.info("Wrote " + links.count() + " link edges")

    // convert traversal records to edge.txt format
    logger.info("Reading GFA 1.0 traversal records from " + args(0) + " in Parquet format")
    val traversals = spark.sql("select source.id as source, source.orientation as sourceOrientation, target.id as target, target.orientation as targetOrientation, recordType as interaction, pathName, ordinal, overlap from parquet.`" + args(0) + "` where recordType = 't'")

    traversals
      .write
      .option("header", true)
      .option("delimiter", "\t")
      .option("emptyValue", "")
      .csv(args(1) + "-traversal-edges.txt")

    logger.info("Wrote " + traversals.count() + " traversal edges")
  }
}
