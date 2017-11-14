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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Position.
 */
public final class PositionTest {

    @Test
    public void testCtr() {
        Position p = new Position(42, false);
        assertEquals(42, p.getPosition());
        assertFalse(p.isTerminal());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCtrNegativePosition() {
        new Position(-42, false);
    }

    @Test(expected=NullPointerException.class)
    public void testValueOfNull() {
        Position.valueOf(null);
    }

    @Test(expected=NumberFormatException.class)
    public void testValueOfEmpty() {
        Position.valueOf("");
    }

    @Test(expected=NumberFormatException.class)
    public void testValueOfInvalidInteger() {
        Position.valueOf("not an integer");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testValueOfNegativePosition() {
        Position.valueOf("-42");
    }

    @Test
    public void testValueOf() {
        Position p = Position.valueOf("42");
        assertEquals(42, p.getPosition());
        assertFalse(p.isTerminal());
    }

    @Test
    public void testValueOfTerminal() {
        Position p = Position.valueOf("42$");
        assertEquals(42, p.getPosition());
        assertTrue(p.isTerminal());
    }
}
