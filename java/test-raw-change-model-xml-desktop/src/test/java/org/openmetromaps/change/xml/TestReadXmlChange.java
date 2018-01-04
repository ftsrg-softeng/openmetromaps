// Copyright 2018 Sebastian Kuerten
//
// This file is part of OpenMetroMaps.
//
// OpenMetroMaps is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// OpenMetroMaps is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with OpenMetroMaps. If not, see <http://www.gnu.org/licenses/>.

package org.openmetromaps.change.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.openmetromaps.change.Change;
import org.openmetromaps.change.Matcher;
import org.openmetromaps.change.RawChangeModel;
import org.openmetromaps.change.RegexMatcher;
import org.openmetromaps.change.SimpleMatcher;

import de.topobyte.xml.domabstraction.iface.ParsingException;

public class TestReadXmlChange
{

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, ParsingException
	{
		InputStream input = TestReadXmlChange.class.getClassLoader()
				.getResourceAsStream("berlin-changes.xml");
		RawChangeModel model = DesktopXmlChangeReader.read(input);
		for (Change change : model.getChanges()) {
			Matcher matcher = change.getMatcher();
			String changeLine = null;
			if (matcher instanceof SimpleMatcher) {
				SimpleMatcher sm = (SimpleMatcher) matcher;
				changeLine = sm.getName();
			} else if (matcher instanceof RegexMatcher) {
				RegexMatcher rm = (RegexMatcher) matcher;
				changeLine = rm.getPattern();
			}
			System.out
					.println(String.format("line %s towards %s at %s to %s: %s",
							change.getLine(), change.getTowards(),
							change.getAt(), changeLine, change.getLocation()));
		}
	}

}
