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
package com.github.heuermh.adam.gfa.sql.gfa1

import com.github.heuermh.adam.gfa.sql.gfa.{ Reference, Tag }

import org.dishevelled.bio.assembly.gfa1.{
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
  id: String,
  sequence: String,
  length: Option[Int],
  readCount: Option[Int],
  fragmentCount: Option[Int],
  kmerCount: Option[Int],
  //sequenceChecksum: Byte[],
  sequenceChecksum: String,
  sequenceUri: String,

  // link
  //id: String,
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
  overlaps: Seq[String],

  // traversal
  //pathName: String,
  ordinal: Option[Int],
  //source: Reference,
  //target: Reference,
  //overlap: String,

  tags: Map[String, Tag]
)

object Gfa1Record {
  def apply(l: JLink): Gfa1Record = {
    Gfa1Record(
      recordType = "L",
      id = l.getIdOpt.orElse(null),
      sequence = null,
      length = None,
      readCount = if (l.containsReadCount) Some(l.getReadCount) else None,
      fragmentCount = if (l.containsFragmentCount) Some(l.getFragmentCount) else None,
      kmerCount = if (l.containsKmerCount) Some(l.getKmerCount) else None,
      sequenceChecksum = null,
      sequenceUri = null,
      source = Reference(l.getSource),
      target = Reference(l.getTarget),
      overlap = l.getOverlapOpt.orElse(null),
      mappingQuality = if (l.containsMappingQuality) Some(l.getMappingQuality) else None,
      mismatchCount = if (l.containsMismatchCount) Some(l.getMismatchCount) else None,
      pathName = null,
      segments = null,
      overlaps = null,
      ordinal = None,
      tags = l.getTags.asScala.map(kv => (kv._1, Tag(kv._2))).toMap
    )
  }

  def apply(p: JPath): Gfa1Record = {
    Gfa1Record(
      recordType = "P",
      id = null,
      sequence = null,
      length = None,
      readCount = None,
      fragmentCount = None,
      kmerCount = None,
      sequenceChecksum = null,
      sequenceUri = null,
      source = null,
      target = null,
      overlap = null,
      mappingQuality = None,
      mismatchCount = None,
      pathName = p.getName,
      segments = p.getSegments.asScala.map(Reference(_)),
      overlaps = p.getOverlaps.asScala,
      ordinal = None,
      tags = p.getTags().asScala.map(kv => (kv._1, Tag(kv._2))).toMap
    )
  }

  def apply(s: JSegment): Gfa1Record = {
    Gfa1Record(
      recordType = "S",
      id = s.getId,
      sequence = s.getSequence,
      length = if (s.containsLength) Some(s.getLength) else None,
      readCount = if (s.containsReadCount) Some(s.getReadCount) else None,
      fragmentCount = if (s.containsFragmentCount) Some(s.getFragmentCount) else None,
      kmerCount = if (s.containsKmerCount) Some(s.getKmerCount) else None,
      sequenceChecksum = null,
      sequenceUri = s.getSequenceUriOpt.orElse(null),
      source = null,
      target = null,
      overlap = null,
      mappingQuality = null,
      mismatchCount = null,
      pathName = null,
      segments = null,
      overlaps = null,
      ordinal = None,
      tags = s.getTags.asScala.map(kv => (kv._1, Tag(kv._2))).toMap
    )
  }

  def apply(t: JTraversal): Gfa1Record = {
    Gfa1Record(
      recordType = "T",
      id = null,
      sequence = null,
      length = None,
      readCount = None,
      fragmentCount = None,
      kmerCount = None,
      sequenceChecksum = null,
      sequenceUri = null,
      source = Reference(t.getSource),
      target = Reference(t.getTarget),
      overlap = t.getOverlapOpt.orElse(null),
      mappingQuality = None,
      mismatchCount = None,
      pathName = t.getPathName,
      segments = null,
      overlaps = null,
      ordinal = Some(t.getOrdinal),
      tags = t.getTags().asScala.map(kv => (kv._1, Tag(kv._2))).toMap
    )
  }
}
