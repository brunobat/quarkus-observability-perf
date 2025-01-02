#!/bin/bash
# Quarkus must have been built with OTel disabled at runtime, otherwise an error will show at startup: quarkus.otel.enabled=false
# Use agent version for the latest OTel SDK used in Quarkus:
wget -O target/opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.8.0/opentelemetry-javaagent.jar
java -javaagent:target/opentelemetry-javaagent.jar -jar target/quarkus-app/quarkus-run.jar
