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

# Run the main class from compiled classes
echo "Running SearchEngineApp..."
java -cp "$PROJECT_DIR/target/classes" com.searchplayground.core.SearchEngineApp
