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

package org.openmetromaps.osm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.maps.xml.XmlModelWriter;
import org.openmetromaps.model.osm.filter.RouteTypeFilter;

public class TestImportOverpassVbbRegio
{

	public static void main(String[] args) throws MalformedURLException,
			IOException, ParserConfigurationException, TransformerException
	{
		String q = "(" + //
				"  relation" + //
				"    [route=train]" + //
				"    [network=\"Verkehrsverbund Berlin-Brandenburg\"]" + //
				"    (50.9030328,11.008300,53.917281,15.9301757);" + //
				"  >;" + //
				");" + //
				"out;";

		OverpassApiImporter importer = new OverpassApiImporter();
		ModelData data = importer.execute(q, new RouteTypeFilter("train"));

		System.out.println(String.format("Imported %d lines with %d stations",
				data.lines.size(), data.stations.size()));

		Path pathOutput = Paths.get("/tmp/vbb.xml");
		OutputStream os = Files.newOutputStream(pathOutput);
		new XmlModelWriter().write(os, data, new ArrayList<>());
		os.close();
	}

}
