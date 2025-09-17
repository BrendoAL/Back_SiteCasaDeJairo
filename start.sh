#!/bin/sh

echo "=== RENDER DEPLOYMENT DEBUG ==="
echo "PORT variable: $PORT"
echo "All environment variables:"
printenv | grep -E "(PORT|SERVER|JAVA)"
echo "=== END DEBUG ==="

# Verifica se PORT está definida
if [ -z "$PORT" ]; then
    echo "ERROR: PORT variable not set, using default 8080"
    export PORT=8080
fi

echo "Starting Spring Boot application on port $PORT"

# Inicia a aplicação
java $JAVA_OPTS \
    -Dserver.port=$PORT \
    -Dserver.address=0.0.0.0 \
    -Dspring.profiles.active=prod \
    -jar app.jar