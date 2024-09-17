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

package com.slimjars.dist.gnu.trove.decorators;

//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

import com.slimjars.dist.gnu.trove.decorator.TCharObjectMapDecorator;
import com.slimjars.dist.gnu.trove.map.TCharObjectMap;

import java.util.Map;


public class TCharObjectMapDecorators {

    // Disallow creation of instances of this class
    private TCharObjectMapDecorators() { }

    /**
     * Wrap the given map in a decorator that uses the standard {@link java.util.Map Map}
     * interface.
     *
     * @param map the <tt>TCharObjectMap</tt> to be wrapped
     * @return the wrapped map.
     */
    public static <T> Map<Character,T> wrap( TCharObjectMap<T> map ) {
        return new TCharObjectMapDecorator<T>( map );
    }
}
