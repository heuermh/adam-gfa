/*

    gfa-adam  Graphical Fragment Assembly (GFA) 2.0 support for ADAM.
    Copyright (c) 2017 held jointly by the individual authors.

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

//import de.javakaffee.kryoserializers.guava.ImmutableListSerializer
//import de.javakaffee.kryoserializers.guava.ImmutableSetSerializer

import org.bdgenomics.adam.serialization.ADAMKryoRegistrator

class GfaKryoRegistrator extends ADAMKryoRegistrator {
  override def registerClasses(kryo: Kryo) {
    super.registerClasses(kryo)

    kryo.register(classOf[com.github.heuermh.adam.gfa.Alignment])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Edge])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Fragment])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Gap])
    kryo.register(classOf[com.github.heuermh.adam.gfa.GfaRecord])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Orientation])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Path])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Position])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Reference])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Segment])
    kryo.register(classOf[com.github.heuermh.adam.gfa.Set])

    //ImmutableListSerializer.registerSerializers(kryo)
    //ImmutableSetSerializer.registerSerializers(kryo)
  }
}
