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

import com.slimjars.dist.gnu.trove.set.TIntSet;
import com.slimjars.dist.gnu.trove.impl.sync.TSynchronizedIntSet;


public class TSynchronizedIntSets {

    // Disallow creation of instances of this class
    private TSynchronizedIntSets() { }

    /**
     * Returns a synchronized (thread-safe) Trove set backed by the specified
     * set.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing set is accomplished
     * through the returned set.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * set when iterating over it:
     * <pre>
     *  TIntSet s = TSynchronizedIntSets.wrap( new TIntHashSet() );
     *      ...
     *  synchronized(s) {
     *      TIntIterator i = s.iterator(); // Must be in the synchronized block
     *      while ( i.hasNext() )
     *          foo( i.next() );
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned set will be serializable if the specified set is
     * serializable.
     *
     * @param  s the set to be "wrapped" in a synchronized set.
     * @return a synchronized view of the specified set.
     */
    public static TIntSet wrap( TIntSet s ) {
	    return new TSynchronizedIntSet( s );
    }

    static TIntSet wrap( TIntSet s, Object mutex ) {
	    return new TSynchronizedIntSet( s, mutex );
    }
}
