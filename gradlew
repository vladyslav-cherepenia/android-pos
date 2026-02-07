#!/bin/sh
# -----------------------------------------------------------------------------
# Gradle start script for UN*X
# -----------------------------------------------------------------------------

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

# Find java
if [ -z "$JAVA_HOME" ] ; then
  JAVA_CMD=$(command -v java)
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$JAVA_CMD" -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
