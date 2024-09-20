# OpenMetroMaps

This repository is a fork of the original
[OpenMetroMaps](https://www.openmetromaps.org) project used for educational purposes. The project was intentionally simplified by removing all web related subprojects.

**Table of Contents**
* [Requirements](#requirements)
* [Command line interface (CLI)](#command-line-interface-cli)
  * [Building the CLI module](#building-the-cli-module)
  * [CLI usage and tasks](#cli-usage-and-tasks)
  * [The osm-import task](#the-osm-import-task)
  * [The util task](#the-util-task)
  * [The export task](#the-export-task)
* [File Format](#file-format)
* [Desktop Tools](#desktop-tools)
  * [Map Editor](#map-editor)
  * [To-Do](#to-do)
* [Hacking](#hacking)
  * [Writing an optimization
    algorithm](#writing-an-optimization-algorithm)
* [Data Sources](#data-sources)
    * [OpenStreetMap](#openstreetmap)
    * [GTFS](#gtfs)
    * [Other Sources](#other-sources)
* [Station Data](#station-data)
* [Research](#research)
* [Other Ideas](#other-ideas)
    * [Nick-name map for Berlin](#nick-name-map-for-berlin)

## Requirements

In order to run the software from the development tree you need a Java
Development Kit (JDK), Version 21 or later. The project uses Maven as a
build tool, but you should use the included Maven Wrapper for building
the project.

On Debian-based systems such as Ubuntu or Mint, you can install the JDK
like this:

    sudo apt-get install openjdk-21-jdk

## Command line interface (CLI)

### Building the CLI module

Run the Maven `package` task to build the CLI:

    ./mvnw package

### CLI usage and tasks

This project has a main executable that can be executed like this:

    ./scripts/openmetromaps-cli <task>

Alternatively, add the `scripts` directory to your `PATH` environment
variable in order to run `openmetromaps-cli` without specifying its location
each time. The following examples assume you have done that:

    export PATH=$PATH:$(readlink -f scripts)

Then invoke the main executable like this:

    openmetromaps-cli <task>

Where `<task>` can be any of the following:

    ui-selector
    osm-filter
    osm-extract
    osm-query
    osm-import
    osm-inspect
    map-editor
    map-viewer
    map-morpher
    simple-map-viewer
    gtfs-import
    graphml-import
    create-markdown-view
    util
    export

Each task accepts its own set of command line parameters. To run the Map Viewer
you would type:

    openmetromaps-cli map-viewer --input test-data/src/main/resources/berlin.xml

To run the Map Editor, type:

    openmetromaps-cli map-editor --input test-data/src/main/resources/berlin.xml

### The osm-import task

The `osm-import` task imports data from OpenStreetMap and offers more sub-tasks:

    openmetromaps-cli osm-import <sub-task>

where `<sub-task>` may be one of the following:

    file
    overpass

### The util task

The `util` task works on map model files and offers more sub-tasks:

    openmetromaps-cli util <sub-task>

where `<sub-task>` may be one of the following:

    info
    list-change-stations
    list-lines-with-change-stations
    purge-stations

### The export task

The `export` task works on map model files and offers more sub-tasks:

    openmetromaps-cli export <sub-task>

where `<sub-task>` may be one of the following:

    svg
    png

Examples:

    openmetromaps-cli export png --input test-data/src/main/resources/berlin.xml
                                 --output berlin.png --zoom 2

    openmetromaps-cli export svg --input test-data/src/main/resources/berlin.xml
                                 --output berlin.svg --zoom 3


## File Format

A major goal of this project is to develop a file format for storing schematic
maps for public transport networks.
There's no formal specification of the file format yet and features of the
format are still under construction.
See an [example file](example-data/example.xml)
or the [Berlin testing file](subprojects/test-data/src/main/resources/berlin.omm)
to get an idea of how it's going to look.
See the [specification draft](docs/spec-map-format.md).

## Desktop Tools

We're developing a set of desktop tools for working with the map files.
Those tools are written in Java and user interfaces are based on Swing with
DockingFrames for dockable dialogs.

One core component is the Map Editor that allows you to create new maps based on
OpenStreetMap data or from scratch and lets you manipulate existing maps.

### Map Editor

The Map Editor is the main interface for creating and manipulating maps.
There's a separate [manual](docs/map-editor.md) that explains the features in
some detail.

### To-Do

Have a look at the [To-Do list](docs/TODO.md).

## Hacking

To start hacking on the project, you should use an IDE. We're using IntelliJ here.

Once you've set up your working environment, you can start running the editor
from within the IDE. Navigate to the class `TestMapEditor` and run this class
as a Java application.

### Writing an optimization algorithm

The Map Editor provides an infrastructure for implementing algorithms for
optimizing maps. When you run the editor, you can access the available
optimization algorithms via the menu (Edit → Algorithms). Currently there's only
two algorithms available:

* Dummy Optimization: This is a placeholder algorithm that does nothing and only
  exists in order to show how to add an algorithm to the menu. Have a look at
  class `DummyOptimizationAction`.
* StraightenAxisParallelLines: This is a very basic optimization algorithm that
  strives to detect subway lines with almost axis-parallel sections. Sections
  that are classified as quasi axis-parallel will be modified so that they are
  really axis-parallel afterwards. Have a look at `StraightenAxisParallelLinesAction`
  to see how the menu action can be set up and at
  `StraightenAxisParallelLinesOptimization` to see the actual optimization code.

To write your own optimization algorithm, we recommend to copy and rename
the classes `StraightenAxisParallelLinesAction` and
`StraightenAxisParallelLinesOptimization` and start modifying the existing code.

See [this list of papers](docs/research/research.md#optimization-algorithms)
for possible implementations that have been discussed in literature.

## Data Sources

We currently support data imports from the following sources:
* [OpenStreetMap](https://www.openstreetmap.org/about) (OSM)
* [General Transit Feed Specification](http://gtfs.org) (GTFS)

Both types of import can be done using the [Command Line
Interface](#command-line-interface-cli).
See the commands `osm-import` for importing OSM data and `gtfs-import` for
importing GTFS data.

### OpenStreetMap

* If you're not familiar with the OpenStreetMap project, start by browsing
  through the pages listed on the [Use
  OpenStreetMap](https://wiki.openstreetmap.org/wiki/Use_OpenStreetMap)
  page on the OSM Wiki.
* See the [Downloading
  data](https://wiki.openstreetmap.org/wiki/Downloading_data) page
  on the OSM Wiki on how to obtain suitable OSM data.
* Probably you want to use smaller extracts such as those available from
  [Geofabrik downloads](http://download.geofabrik.de) instead of downloading
  the whole planet as a file. Using the [Overpass
  API](https://wiki.openstreetmap.org/wiki/Overpass_API) is also a good
  way for obtaining relevant data sets.

### GTFS

* [TransitFeeds](https://transitfeeds.com) collects links to
  official GTFS data worldwide ([GitHub page](https://github.com/TransitFeeds))

### Other Sources

The file format is text-based and pretty simple, so you can create a data
file with a normal text editor.
When you want to use existing data, you can write an import algorithm of
your own.

[Wikidata](https://www.wikidata.org) also stores information about
stations, lines and the like.

## Station Data

In addition to the main map file format, we're also working on an additional
file format and corresponding tools to collect data about stations and their
tracks. In particular, files in this format store locations of things on a station's
track as a relative position on the train (front to back / tail).
This information can be used to compute efficient micro-routing within line
networks, i.e. optimize on which car to board a train to reach something most
quickly on the destination station such as a specific exit or a stairway to your
connecting train.
See an [example file](example-data/example-stations.xml)
or the [Berlin testing file](subprojects/test-data/src/main/resources/berlin-stations.xml)
to get an idea of how this file works.
Also see the [specification draft](docs/spec-station-format.md) and a longer
discussion on the [requirements of that format](docs/station-data.md).

## Research

We're also gathering material about transit maps in general on the
[Research](docs/research.md)
page.

## Other Ideas

* It would be nice to be able to create morphing animations from two views
  of the same network. Inspired by
  [this article](http://mymodernmet.com/animated-subway-maps) about
  a number of posts on reddit
  ([Berlin](https://www.reddit.com/r/dataisbeautiful/comments/6baefh/berlin_subway_map_compared_to_its_real_geography/)).
  **Done** via the `maps-morpher` module and the CLI's `map-morpher` task.
* Build something like [this interactive route planner](http://jannisr.de/vbb-map-routing) using
  the Javascript viewer component.
* Integrate the Android component into
  [Transportr](https://github.com/grote/Transportr).
* Implement different optimization algorithms to transform geographic
  maps into schematic maps automatically. Some hints on how to get started
  with that are [already available](#writing-an-optimization-algorithm).
  Also, there is an
  [implementation](https://github.com/dirkschumacher/TransitmapSolver.jl)
  available which could possibly be built upon (although the license changed
  from MIT to GPL, which makes it impossible to integrate easily).
  We collect a [list of papers](docs/optimization-algorithms.md)
  about possible algorithms.

### Nick-name map for Berlin

Possible resources:
* <https://de.wikipedia.org/wiki/Berolinismus>
* <https://github.com/derhuerst/vbb-common-places/blob/master/stations.json>
