package org.openmetromaps.maps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openmetromaps.gtfs.DraftModel;
import org.openmetromaps.gtfs.GtfsImporter;
import org.openmetromaps.maps.model.ModelData;
import org.openmetromaps.misc.NameChanger;
import org.openmetromaps.model.gtfs.DraftModelConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
@RunWith(Parameterized.class)
public class TestModelModification {

    @Parameterized.Parameter(0)
    public String path;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "src/test/resources/test1" }
        });
    }

    @Test
    public void testModelsEqual() throws IOException {
        Path basePath = Path.of(path);
        Path inputPath = basePath.resolve("input.zip");
        Path expectedPath = basePath.resolve("expected.zip");
        Path operationPath = basePath.resolve("operation.txt");

        ModelData inputModel = importModelFromGtfs(inputPath);
        ModelData expectedModel = importModelFromGtfs(expectedPath);
        String[] operation = Files.readString(operationPath).split(";");

        switch (operation[0]) {
            case "MERGE":
                MapModelModification.mergeStations(
                        inputModel,
                        Stream.of(operation)
                                .skip(1)
                                .map(param -> inputModel.stations.stream().filter(s -> s.getName().equals(param)).findFirst().get())
                                .toList()

                );
        }

        Assert.assertTrue(MapModelUtil.modelsSemanticallyEqual(inputModel, expectedModel));
    }

    private ModelData importModelFromGtfs(Path pathInput) throws IOException {
        GtfsImporter importer = new GtfsImporter(
                pathInput,
                new NameChanger(new ArrayList<>(), new ArrayList<>()),
                false
        );
        importer.execute();

        DraftModel draft = importer.getModel();
        ModelData data = new DraftModelConverter().convert(draft);
        return data;
    }
}
