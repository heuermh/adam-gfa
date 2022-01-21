/*

    adam-gfa  Graphical Fragment Assembly (GFA) support for ADAM.
    Copyright (c) 2017-2021 held jointly by the individual authors.

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

import com.github.heuermh.adam.gfa.sql.gfa1.Gfa1Record

/**
 * Convert Gfa1Records to property graph CSV format.
 */
object ToPropertyGraphCsv2 {
  val logger = Logger("com.github.heuermh.adam.gfa.ToPropertyGraphCsv2")

  def main(args: Array[String]) {

    if (args.length < 2) {
      System.err.println("at least two arguments required, e.g. in.parquet out[-segment-nodes.csv,-containment-edges.csv,-link-edges.csv,-traversal-edges.csv]")
      System.exit(1)
    }

    val conf = new SparkConf()
      .setAppName("Transform Graphical Fragment Assembly (GFA) 1.0 records in Parquet format to property graph CSV.")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrator", "com.github.heuermh.adam.gfa.GfaKryoRegistrator")
      .set("spark.kryo.referenceTracking", "true")

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(sc.getConf).getOrCreate()
    import spark.implicits._

    // convert segments to node csv
    logger.info("Reading GFA 1.0 segments from " + args(0) + " in Parquet format")
    val segments = spark.sql("select name, sequence, length, readCount, fragmentCount, kmerCount, sequenceChecksum, sequenceUri from parquet.`" + args(0) + "` where recordType = 'S'")

    // rename segment columns
    val renamedSegments = segments
      .withColumnRenamed("name", "~id") // can't do this ~ column name in sql, parse error
      .withColumnRenamed("sequence", "sequence:String")
      .withColumnRenamed("length", "length:Long")
      .withColumnRenamed("readCount", "readCount:Int")
      .withColumnRenamed("fragmentCount", "fragmentCount:Int")
      .withColumnRenamed("kmerCount", "kmerCount:Int")
      .withColumnRenamed("sequenceChecksum", "sequenceChecksum:String")
      .withColumnRenamed("sequenceUri", "sequenceUri:String")

    // and write to csv
    renamedSegments.write.option("header", true).csv(args(1) + "-segment-nodes.csv")
    logger.info("Wrote " + renamedSegments.count() + " segment nodes")

    // convert containments to edge csv
    logger.info("Reading GFA 1.0 containments from " + args(0) + " in Parquet format")
    val containments = spark.sql("select id, container.id as sourceId, contained.id as targetId, container.orientation as sourceOrientation, contained.orientation as targetOrientation, recordType as interaction, position, overlap, mismatchCount, readCount from parquet.`" + args(0) +"` where recordType = 'C'")

    // rename containment columns
    val renamedContainments = containments
      .withColumnRenamed("id", "~id") // can't do these ~ column names in sql, parse error
      .withColumnRenamed("sourceId", "~source")
      .withColumnRenamed("targetId", "~target")
      .withColumnRenamed("sourceOrientation", "sourceOrientation:String")
      .withColumnRenamed("targetOrientation", "targetOrientation:String")
      .withColumnRenamed("interaction", "interaction:String")
      .withColumnRenamed("position", "position:Int")
      .withColumnRenamed("overlap", "overlap:String")
      .withColumnRenamed("mismatchCount", "mismatchCount:Int")
      .withColumnRenamed("readCount", "readCount:Int")

    renamedContainments.write.option("header", true).csv(args(1) + "-containment-edges.csv")
    logger.info("Wrote " + renamedContainments.count() + " containment edges")

    // convert links to edge csv
    logger.info("Reading GFA 1.0 links from " + args(0) + " in Parquet format")
    val links = spark.sql("select id, source.id as sourceId, target.id as targetId, source.orientation as sourceOrientation, target.orientation as targetOrientation, recordType as interaction, overlap, mappingQuality, mismatchCount from parquet.`" + args(0) + "` where recordType = 'L'")

    // rename link columns
    val renamedLinks = links
      .withColumnRenamed("id", "~id") // can't do these ~ column names in sql, parse error
      .withColumnRenamed("sourceId", "~source")
      .withColumnRenamed("targetId", "~target")
      .withColumnRenamed("sourceOrientation", "sourceOrientation:String")
      .withColumnRenamed("targetOrientation", "targetOrientation:String")
      .withColumnRenamed("interaction", "interaction:String")
      .withColumnRenamed("overlap", "overlap:String")
      .withColumnRenamed("mappingQuality", "mappingQuality:Int")
      .withColumnRenamed("mismatchCount", "mismatchCount:Int")

    renamedLinks.write.option("header", true).csv(args(1) + "-link-edges.csv")
    logger.info("Wrote " + renamedLinks.count() + " link edges")

    logger.info("Reading GFA 1.0 traversals from " + args(0) + " in Parquet format")

    // convert traversals to edge csv
    val traversals = spark.sql("select id, source.id as sourceId, target.id as targetId, source.orientation as sourceOrientation, target.orientation as targetOrientation, recordType as interaction, pathName, ordinal, overlap from parquet.`" + args(0) + "` where recordType = 't'")

    // rename traversal columns
    val renamedTraversals = traversals
      .withColumnRenamed("id", "~id") // can't do these ~ column names in sql, parse error
      .withColumnRenamed("sourceId", "~source")
      .withColumnRenamed("targetId", "~target")
      .withColumnRenamed("sourceOrientation", "sourceOrientation:String")
      .withColumnRenamed("targetOrientation", "targetOrientation:String")
      .withColumnRenamed("interaction", "interaction:String")
      .withColumnRenamed("pathName", "pathName:String")
      .withColumnRenamed("ordinal", "ordinal:Int")
      .withColumnRenamed("overlap", "overlap:String")

    renamedTraversals.write.option("header", true).csv(args(1) + "-traversal-edges.csv")
    logger.info("Wrote " + renamedTraversals.count() + " traversal edges")
  }
}
