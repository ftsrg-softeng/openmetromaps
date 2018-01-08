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

package org.openmetromaps.change;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.openmetromaps.gtfs4j.csv.GtfsZip;
import org.openmetromaps.gtfs4j.model.Stop;
import org.openmetromaps.maps.MapModel;
import org.openmetromaps.maps.MapModelUtil;
import org.openmetromaps.maps.TestData;
import org.openmetromaps.maps.graph.LineNetwork;
import org.openmetromaps.maps.graph.LineNetworkBuilder;
import org.openmetromaps.maps.xml.XmlModel;
import org.openmetromaps.maps.xml.XmlModelConverter;
import org.openmetromaps.misc.NameChanger;
import org.openmetromaps.rawchange.RawChangeModel;
import org.openmetromaps.rawchange.xml.DesktopXmlChangeReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.xml.domabstraction.iface.ParsingException;

public class TestConvertChangeModelBerlin
{

	final static Logger logger = LoggerFactory
			.getLogger(TestConvertChangeModelBerlin.class);

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, ParsingException
	{
		XmlModel xmlModel = TestData.berlinXml();
		XmlModelConverter modelConverter = new XmlModelConverter();
		MapModel mapModel = modelConverter.convert(xmlModel);

		LineNetworkBuilder builder = new LineNetworkBuilder(mapModel.getData(),
				MapModelUtil.allEdges(mapModel));
		LineNetwork lineNetwork = builder.getGraph();

		InputStream input = TestConvertChangeModelBerlin.class.getClassLoader()
				.getResourceAsStream("berlin-changes.xml");
		RawChangeModel rawModel = DesktopXmlChangeReader.read(input);
		ChangeModel model = ChangeModels.derive(mapModel.getData(), rawModel);

		Path pathGtfs = Paths.get("/tmp/gtfs/filtered.zip");

		NameChanger nameChanger = TestData.berlinGtfsNameChanger();

		Map<String, String> nameToId = new HashMap<>();

		GtfsZip gtfs = new GtfsZip(pathGtfs);
		List<Stop> stops = gtfs.readStops();
		for (Stop stop : stops) {
			String name = nameChanger.applyNameFixes(stop.getName());
			if (stop.getLocationType().equals("1")) {
				if (nameToId.containsKey(name)) {
					String dup = nameToId.get(name);
					logger.warn(
							String.format("duplicate GTFS station %s → %s: %s",
									stop.getId(), dup, name));
				} else {
					nameToId.put(name, stop.getId());
				}
			}
		}
		gtfs.close();

		ChangeModelToCsvExporter exporter = new ChangeModelToCsvExporter(
				mapModel, lineNetwork, model);
		exporter.print();
	}

}