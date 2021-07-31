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
package com.github.heuermh.adam.gfa.sql.gfa1

import org.dishevelled.bio.assembly.gfa1.{
  Containment => JContainment,
  Link => JLink,
  Path => JPath,
  Segment => JSegment,
  Traversal => JTraversal
}

import scala.collection.JavaConverters._

/**
 * GFA 1.0 record case class for use in data frames.
 */
case class Gfa1Record(
  recordType: String,

  // segment
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

  // link
  id: String,
  source: Reference,
  target: Reference,
  overlap: String,
  mappingQuality: Option[Int],
  mismatchCount: Option[Int],
  //readCount: Option[Int],
  //fragmentCount: Option[Int],
  //kmerCount: Option[Int],

  // path
  pathName: String,
  segments: Seq[Reference],
  overlaps: Option[Seq[String]],

  // traversal
  //id: String,
  //pathName: String,
  ordinal: Option[Int],
  //source: Reference,
  //target: Reference,
  //overlap: String,

  // containment
  //id: String,
  container: Reference,
  contained: Reference,
  position: Option[Int],
  //overlap: String,
  //mismatchCount: Option[Int],
  //readCount: Option[Int],

  annotations: Map[String, Annotation]
)

object Gfa1Record {
  def apply(l: JLink): Gfa1Record = {
    Gfa1Record(
      recordType = "L",
      name = null,
      sequence = null,
      length = None,
      readCount = if (l.containsReadCount) Some(l.getReadCount) else None,
      fragmentCount = if (l.containsFragmentCount) Some(l.getFragmentCount) else None,
      kmerCount = if (l.containsKmerCount) Some(l.getKmerCount) else None,
      sequenceChecksum = null,
      sequenceUri = null,
      stableName = null,
      stableOffset = None,
      stableRank = None,
      id = l.getIdOpt.orElse(null),
      source = Reference(l.getSource),
      target = Reference(l.getTarget),
      overlap = l.getOverlapOpt.orElse(null),
      mappingQuality = if (l.containsMappingQuality) Some(l.getMappingQuality) else None,
      mismatchCount = if (l.containsMismatchCount) Some(l.getMismatchCount) else None,
      pathName = null,
      segments = null,
      overlaps = null,
      ordinal = None,
      container = null,
      contained = null,
      position = None,
      annotations = l.getAnnotations.asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(p: JPath): Gfa1Record = {
    Gfa1Record(
      recordType = "P",
      name = null,
      sequence = null,
      length = None,
      readCount = None,
      fragmentCount = None,
      kmerCount = None,
      sequenceChecksum = null,
      sequenceUri = null,
      stableName = null,
      stableOffset = None,
      stableRank = None,
      id = null,
      source = null,
      target = null,
      overlap = null,
      mappingQuality = None,
      mismatchCount = None,
      pathName = p.getName,
      segments = p.getSegments.asScala.map(Reference(_)),
      overlaps = if (p.hasOverlaps) Some(p.getOverlaps.asScala) else None,
      ordinal = None,
      container = null,
      contained = null,
      position = None,
      annotations = p.getAnnotations().asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(s: JSegment): Gfa1Record = {
    Gfa1Record(
      recordType = "S",
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
      id = null,
      source = null,
      target = null,
      overlap = null,
      mappingQuality = null,
      mismatchCount = null,
      pathName = null,
      segments = null,
      overlaps = null,
      ordinal = None,
      container = null,
      contained = null,
      position = None,
      annotations = s.getAnnotations.asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(t: JTraversal): Gfa1Record = {
    Gfa1Record(
      recordType = "t",
      name = null,
      sequence = null,
      length = None,
      readCount = None,
      fragmentCount = None,
      kmerCount = None,
      sequenceChecksum = null,
      sequenceUri = null,
      stableName = null,
      stableOffset = None,
      stableRank = None,
      id = t.getIdOpt.orElse(null),
      source = Reference(t.getSource),
      target = Reference(t.getTarget),
      overlap = t.getOverlapOpt.orElse(null),
      mappingQuality = None,
      mismatchCount = None,
      pathName = t.getPathName,
      segments = null,
      overlaps = null,
      ordinal = Some(t.getOrdinal),
      container = null,
      contained = null,
      position = None,
      annotations = t.getAnnotations().asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(c: JContainment): Gfa1Record = {
    Gfa1Record(
      recordType = "C",
      name = null,
      sequence = null,
      length = None,
      readCount = if (c.containsReadCount) Some(c.getReadCount) else None,
      fragmentCount = None,
      kmerCount = None,
      sequenceChecksum = null,
      sequenceUri = null,
      stableName = null,
      stableOffset = None,
      stableRank = None,
      id = c.getIdOpt.orElse(null),
      source = null,
      target = null,
      overlap = c.getOverlapOpt.orElse(null),
      mappingQuality = None,
      mismatchCount = if (c.containsMismatchCount) Some(c.getMismatchCount) else None,
      pathName = null,
      segments = null,
      overlaps = null,
      ordinal = None,
      container = Reference(c.getContainer),
      contained = Reference(c.getContained),
      position = Some(c.getPosition),
      annotations = c.getAnnotations().asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }
}
