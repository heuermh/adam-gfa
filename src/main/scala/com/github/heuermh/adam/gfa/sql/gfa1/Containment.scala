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

import org.dishevelled.bio.assembly.gfa1.{ Containment => JContainment }

import scala.collection.JavaConverters._

/**
 * GFA 1.0 containment case class for use in data frames.
 */
case class Containment(
  id: String,
  container: Reference,
  contained: Reference,
  position: Option[Int],
  overlap: String,
  mismatchCount: Option[Int],
  readCount: Option[Int],
  annotations: Map[String, Annotation]) {

  def asJava(): JContainment = {
    new JContainment(
      container.asJava,
      contained.asJava,
      position.get,
      overlap,
      annotations.map(kv => (kv._1, kv._2.asJava)).asJava
    )
  }
}

object Containment {
  def apply(l: JContainment): Containment = {
    Containment(
      id = l.getIdOpt.orElse(null),
      container = Reference(l.getContainer),
      contained = Reference(l.getContained),
      position = Some(l.getPosition),
      overlap = l.getOverlap,
      mismatchCount = if (l.containsMismatchCount) Some(l.getMismatchCount) else None,
      readCount = if (l.containsReadCount) Some(l.getReadCount) else None,
      annotations = l.getAnnotations.asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(r: Gfa1Record): Containment = {
    Containment(
      r.id,
      r.container,
      r.contained,
      r.position,
      r.overlap,
      r.mismatchCount,
      r.readCount,
      r.annotations
    )
  }
}
