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

import org.dishevelled.bio.assembly.gfa1.{ Traversal => JTraversal }

import scala.collection.JavaConverters._

/**
 * GFA 1.0 traversal case class for use in data frames.
 */
case class Traversal(
  pathName: String,
  ordinal: Option[Int],
  source: Reference,
  target: Reference,
  overlap: String,
  annotations: Map[String, Annotation]) {

  def asJava(): JTraversal = {
    new JTraversal(
      pathName,
      ordinal.get,
      source.asJava,
      target.asJava,
      overlap,
      annotations.map(kv => (kv._1, kv._2.asJava)).asJava
    )
  }
}

object Traversal {
  def apply(t: JTraversal): Traversal = {
    Traversal(
      pathName = t.getPathName,
      ordinal = Some(t.getOrdinal),
      source = Reference(t.getSource),
      target = Reference(t.getTarget),
      overlap = t.getOverlap,
      annotations = t.getAnnotations.asScala.map(kv => (kv._1, Annotation(kv._2))).toMap
    )
  }

  def apply(r: Gfa1Record): Traversal = {
    Traversal(
      r.pathName,
      r.ordinal,
      r.source,
      r.target,
      r.overlap,
      r.annotations
    )
  }
}
