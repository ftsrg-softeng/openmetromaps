# OpenMetroMaps

This is the main repository of the
[OpenMetroMaps](https://www.openmetromaps.org) project.
Data projects for cities/regions are stored within the
[OpenMetroMapsData](https://github.com/OpenMetroMapsData) organization to keep
the namespaces for data and code projects separated. Also see the
[list of data projects](data-projects.md).

## File format

A major goal of this project is to develop a file format for storing schematic
maps for public transport networks.
There's no formal specification of the file format yet and features of the
format are still under construction.
See an [example file](example-data/example.xml)
or the [Berlin testing file](java/test-data/src/main/resources/berlin.xml)
to get an idea of how it's going to look.

## Desktop Tools

We're developing a set of desktop tools for working with the map files.
Those tools are written in Java and user interfaces are based on Swing with
DockingFrames for dockable dialogs.

One core component is the Map Editor that allows you to create new maps based on
OpenStreetMap data or from scratch and lets you manipulate existing maps.

If you want to run the editor or start hacking on the desktop tools, please have
a look at the relevant
[README file](java/README.md).

## Web Viewer

We're also building a Javascript-based Web Viewer for the file format to
allow for easy presentation of results in a browser without the need to install
any desktop software. Although it would also be nice to have a native Javascript
implementation of a web viewer, we're eager to maximize code reuse and try to
use the main Java source via transpilation to Javascript. In order to do that
we experimented with both GWT and JSweet based approaches. Currently the
[GWT](java/maps-gwt) solution looks more promising and is close-to-usable.
The [JSweet](jsweet) solution doesn't work quite yet, but the technology
certainly has potential.

## Efficient changing and other station data

In addition to the main map file format, we're also working on an additional
file format and corresponding tools to collect data about stations and their
tracks. In particular, files in this format store locations of things on a station's
track as a relative position on the train (front to back / tail).
This information can be used to compute efficient micro-routing within line
networks, i.e. optimize on which car to board a train to reach something most
quickly on the destination station such as a specific exit or a stairway to your
connecting train.
See an [example file](example-data/example-changes.xml)
or the [Berlin testing file](java/test-data/src/main/resources/berlin-changes.xml)
to get an idea of how this file works.

## Research

We're also gathering material about transit maps in general on the
[Research](research/Research.md)
page.

## Other ideas

* It would be nice to be able to create morphing animations from two views
  of the same network. Inspired by
  [this article](http://mymodernmet.com/animated-subway-maps) about
  a number of posts on reddit.
* Build something like [this interactive route planner](http://jannisr.de/vbb-map-routing) using
  the Javascript viewer component.
* Integrate the Android component into
  [Transportr](https://github.com/grote/Transportr).
* Implement different optimization algorithms to transform geographic
  maps into schematic maps automatically. Some hints on how to get started
  with that are [already available](java#writing-an-optimization-algorithm).
  Also, there is an
  [implementation](https://github.com/dirkschumacher/TransitmapSolver.jl)
  available which could possibly be built upon (although the license changed
  from MIT to GPL, which makes it impossible to integrate easily).
  We collect a [list of papers](research/Research.md#optimization-algorithms)
  about possible algorithms.
