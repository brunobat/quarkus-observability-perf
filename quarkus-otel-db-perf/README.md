
## Instructions to run app with the agent manually 
### Download agent
wget -O opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.11.0/opentelemetry-javaagent.jar

```shell
export JAVA_OPTS="$JAVA_OPTS -Dotel.traces.sampler=traceidratio -Dotel.traces.sampler.arg=0.1 -Dotel.bsp.max.export.batch.size=512 -Dotel.metrics.exporter=none -javaagent:opentelemetry-javaagent.jar -Xms512m -Xmx512m"
```

```shell     
java $JAVA_OPTS -jar target/quarkus-app/quarkus-run.jar
```
