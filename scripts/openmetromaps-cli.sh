#!/bin/bash

scriptdir=$(dirname "$0")
files=$(find "$scriptdir/../subprojects/cli/target/" -name 'openmetromaps-cli-*-jar-with-dependencies.jar')

if [ ! -e "${files[0]}" ]; then
    echo "Please run 'mvn package'"
    exit 1
fi

exec java -Dfile.encoding=UTF-8 -Xmx3g -jar "${files[0]}" "$@"
