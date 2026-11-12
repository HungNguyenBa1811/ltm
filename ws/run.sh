#!/usr/bin/env bash
# Usage: ./run.sh WS_DATA2        (or WS_DATA2.java, or src/main/java/WS_DATA2.java)
set -e
if [ -z "$1" ]; then echo "Usage: $0 <ClassName|File.java>" >&2; exit 1; fi

main=$(basename "$1" .java)
cd "$(dirname "$0")"
mvn -q -Pcli compile org.codehaus.mojo:exec-maven-plugin:3.1.0:java -Dexec.mainClass="$main"
