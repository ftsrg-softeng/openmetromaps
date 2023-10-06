#!/bin/bash

files=($(dirname $0)/../subprojects/cli/target/openmetromaps-cli-*-jar-with-dependencies.jar)

if [ ! -e "${files[0]}" ]; then
	echo "Please run 'mvn package'"
	exit 1
fi

exec java -jar "${files[0]}" "$@"
