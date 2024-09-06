///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2009, Rob Eden All Rights Reserved.
// Copyright (c) 2009, Jeff Randall All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////

package com.slimjars.dist.gnu.trove.sets;

//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////


import com.slimjars.dist.gnu.trove.set.TDoubleSet;
import com.slimjars.dist.gnu.trove.impl.unmodifiable.TUnmodifiableDoubleSet;


public class TUnmodifiableDoubleSets {

    // Disallow creation of instances of this class
    private TUnmodifiableDoubleSets() { }

    /**
     * Returns an unmodifiable view of the specified Trove primitive set.  This method allows
     * modules to provide users with "read-only" access to internal sets.
     * Query operations on the returned set "read through" to the specified
     * set, and attempts to modify the returned set, whether direct or via its
     * iterator, result in an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned set will be serializable if the specified set
     * is serializable.
     *
     * @param  s the set for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified Trove primitive set.
     */
    public static TDoubleSet wrap( TDoubleSet s ) {
	    return new TUnmodifiableDoubleSet( s );
    }
}
