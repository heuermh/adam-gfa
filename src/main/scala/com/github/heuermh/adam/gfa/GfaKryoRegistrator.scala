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

import com.esotericsoftware.kryo.Kryo

import de.javakaffee.kryoserializers.guava.ImmutableListSerializer
import de.javakaffee.kryoserializers.guava.ImmutableMapSerializer
import de.javakaffee.kryoserializers.guava.ImmutableSetSerializer

import org.bdgenomics.adam.serialization.ADAMKryoRegistrator

/**
 * Kryo registrator for GFA model classes. 
 */
class GfaKryoRegistrator extends ADAMKryoRegistrator {
  override def registerClasses(kryo: Kryo) {
    super.registerClasses(kryo)

    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Containment])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Gfa1Record])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Header])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Orientation])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Link])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Path])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Reference])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Segment])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa1.Traversal])

    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Alignment])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Edge])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Fragment])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Gap])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Gfa2Record])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Header])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Orientation])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Path])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Position])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Reference])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Segment])
    kryo.register(classOf[org.dishevelled.bio.assembly.gfa2.Set])

    ImmutableListSerializer.registerSerializers(kryo)
    ImmutableMapSerializer.registerSerializers(kryo)
    ImmutableSetSerializer.registerSerializers(kryo)
  }
}
