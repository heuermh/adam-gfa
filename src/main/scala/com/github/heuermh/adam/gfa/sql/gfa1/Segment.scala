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
package com.github.heuermh.adam.gfa.sql.gfa1

import org.dishevelled.bio.assembly.gfa1.{ Segment => JSegment }

import scala.collection.JavaConverters._

/**
 * GFA 1.0 segment case class for use in data frames.
 */
case class Segment(
  name: String,
  sequence: String,
  length: Option[Int],
  readCount: Option[Int],
  fragmentCount: Option[Int],
  kmerCount: Option[Int],
  //sequenceChecksum: Byte[],
  sequenceChecksum: String,
  sequenceUri: String,
  stableName: String,
  stableOffset: Option[Int],
  stableRank: Option[Int],
  annotations: Map[String, Annotation]) {

  def asJava(): JSegment = {
    new JSegment(
      name,
      sequence,
      annotations.map(kv => (kv._1, kv._2.asJava)).asJava
    )
  }
}

object Segment {
  def apply(s: JSegment): Segment = {
    Segment(
      name = s.getName,
      sequence = s.getSequence,
      length = if (s.containsLength) Some(s.getLength) else None,
      readCount = if (s.containsReadCount) Some(s.getReadCount) else None,
      fragmentCount = if (s.containsFragmentCount) Some(s.getFragmentCount) else None,
      kmerCount = if (s.containsKmerCount) Some(s.getKmerCount) else None,
      sequenceChecksum = null,
      sequenceUri = s.getSequenceUriOpt.orElse(null),
      stableName = s.getStableNameOpt.orElse(null),
      stableOffset = if (s.containsStableOffset) Some(s.getStableOffset) else None,
      stableRank = if (s.containsStableRank) Some(s.getStableRank) else None,
      annotations = s.getAnnotations.asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(r: Gfa1Record): Segment = {
    Segment(
      r.name,
      r.sequence,
      r.length,
      r.readCount,
      r.fragmentCount,
      r.kmerCount,
      r.sequenceChecksum,
      r.sequenceUri,
      r.stableName,
      r.stableOffset,
      r.stableRank,
      r.annotations
    )
  }
}
