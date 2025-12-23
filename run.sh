#!/usr/bin/env bash
set -euo pipefail

# Simple script to build and run the search-engine-playground app using Java 21
# Usage: ./run.sh [--skip-package]

# Preferred JDK path (detected in environment)
DEFAULT_JAVA_HOME="/usr/local/sdkman/candidates/java/21.0.9-ms"
export JAVA_HOME="${JAVA_HOME:-$DEFAULT_JAVA_HOME}"
export PATH="$JAVA_HOME/bin:$PATH"

ROOT="$(dirname "$0")"
PROJECT_DIR="$ROOT/search-engine-playground"

echo "Using JAVA_HOME=$JAVA_HOME"

# If --skip-package is passed, just run from compiled classes
SKIP_PACKAGE=0
if [ "${1:-}" = "--skip-package" ]; then
  SKIP_PACKAGE=1
fi

if [ $SKIP_PACKAGE -eq 0 ]; then
  echo "Building project (Maven)..."
  mvn -f "$PROJECT_DIR/pom.xml" -DskipTests package
fi

echo "Running SearchEngineApp..."
# Ensure runtime dependencies are copied to target/dependency and run with them on the classpath
echo "Copying runtime dependencies..."
mvn -f "$PROJECT_DIR/pom.xml" dependency:copy-dependencies -DoutputDirectory="$PROJECT_DIR/target/dependency" -DincludeScope=runtime

JAR="$PROJECT_DIR/target/search-engine-playground-1.0-SNAPSHOT.jar"
if [ ! -f "$JAR" ]; then
  # If jar is missing, fall back to classes directory
  echo "Jar not found, running from classes + dependency classpath"
  CP="$PROJECT_DIR/target/classes:$PROJECT_DIR/target/dependency/*"
else
  CP="$JAR:$PROJECT_DIR/target/dependency/*"
fi

echo "Running SearchEngineApp with classpath: $CP"
java -Dorg.slf4j.simpleLogger.logFile=./search-engine-playground/app.log \
     -Dorg.slf4j.simpleLogger.defaultLogLevel=info \
     -cp "$CP" com.searchplayground.core.SearchEngineApp
