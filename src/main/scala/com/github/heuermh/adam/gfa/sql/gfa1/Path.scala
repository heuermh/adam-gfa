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

import org.dishevelled.bio.assembly.gfa1.{ Path => JPath }

import scala.collection.JavaConverters._

/**
 * GFA 1.0 path case class for use in data frames.
 */
case class Path(
  pathName: String,
  segments: Seq[Reference],
  overlaps: Option[Seq[String]],
  annotations: Map[String, Annotation]) {

  def asJava(): JPath = {
    new JPath(
      pathName,
      segments.map(_.asJava).asJava,
      overlaps.map(_.asJava).orNull,
      annotations.map(kv => (kv._1, kv._2.asJava)).asJava
    )
  }
}

object Path {
  def apply(p: JPath): Path = {
    Path(
      pathName = p.getName,
      segments = p.getSegments.asScala.map(Reference(_)),
      overlaps = if (p.hasOverlaps) Some(p.getOverlaps.asScala) else None,
      annotations = p.getAnnotations.asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(r: Gfa1Record): Path = {
    Path(
      r.pathName,
      r.segments,
      r.overlaps,
      r.annotations
    )
  }
}
