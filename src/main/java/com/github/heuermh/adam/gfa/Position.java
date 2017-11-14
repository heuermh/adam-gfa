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
package com.github.heuermh.adam.gfa;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class Position {
    private final int position;
    private final boolean terminal;

    public Position(final int position, final boolean terminal) {
        checkArgument(position >= 0, "position must be at least zero");
        this.position = position;
        this.terminal = terminal;
    }

    public int getPosition() {
        return position;
    }

    public boolean isTerminal() {
        return terminal;
    }

    @Override
    public String toString() {
        return terminal ? position + "$" : String.valueOf(position);
    }

    public static Position valueOf(final String value) {
        checkNotNull(value);
        if (value.endsWith("$")) {
            return new Position(Integer.parseInt(value.substring(0, value.length() - 1)), true);
        }
        return new Position(Integer.parseInt(value), false);
    }
}
