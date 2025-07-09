# Quarkus app to measure observability performance

The server is a CRUD app made with:
* REST (Jackson)
* OpenAPI
* Hibernate
* Panache
* Hibernate validator
* PostgreSQL

Can be used with:
* quarkus-micrometer-opentelemetry extension
* OpenTelemetry Java agent

Hyperfoil is used to generate load and qDup is used for test execution orchestration. 

Next we explain how to run the performance test locally.

## Start the infrastructure

Run with Docker compose:

```shell
cd infra
docker compose up
```

In the end, don't forget to take down the infrastructure:

```shell
docker compose down
```

## Instructions to run app with the extension manually


Start the app

```shell     
java -jar target/quarkus-app/quarkus-run.jar
```

## Instructions to run app with the agent manually 

Download agent:

```shell
wget -O opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.11.0/opentelemetry-javaagent.jar
```

Set configs similar to the ones used by the extension:
```shell
export JAVA_OPTS="$JAVA_OPTS -Dotel.traces.sampler=traceidratio -Dotel.traces.sampler.arg=0.1 -Dotel.bsp.max.export.batch.size=512 -Dotel.metrics.exporter=none -javaagent:opentelemetry-javaagent.jar -Xms512m -Xmx512m"
```

Run the app with the agent:
```shell     
java $JAVA_OPTS -jar target/quarkus-app/quarkus-run.jar
```

## Test if the app is working

Verify if it's working with Curl or just open in the browser:

```shell
$ curl -v http://localhost:8080/legumes
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> GET /legumes HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.11.1
> Accept: */*
> 
* Request completely sent off
< HTTP/1.1 200 OK
< content-length: 2
< Content-Type: application/json;charset=UTF-8
< 
* Connection #0 to host localhost left intact
[]
```
The response will be an empty list because no entities have been added to the DB yet.

## Generate load 
If you have a machine with many processors, you can run this locally along with the app.

1. Download Hyperfoil: https://hyperfoil.io/download/
2. Extract and go to the `/bin` folder
3. Start the cli tool: `$ ./cli.sh`
4. Start a local instance of Hyperoil: `[hyperfoil]$ start-local`
5. Upload the file with the job: `[hyperfoil]$  upload <path to your checkout>/quarkus-observability-perf/hyperfoil-scripts/quarkus-otel-perf.hf.yaml`
6. Run the test: `[hyperfoil]$  run quarkus-activemq`
7. See the final stats at the end of the run: `[hyperfoil]$ stats`

For more details, please check: https://hyperfoil.io/docs/getting-started/quickstart1/

Stats will get you a full set of results:

```shell
PHASE                  METRIC                 THROUGHPUT     REQUESTS  MEAN       STD_DEV    MAX        p50        p90        p99        p99.9      p99.99     TIMEOUTS  ERRORS  BLOCKED
firstReq               createLegumesScenario     2.44 req/s         1  391.12 ms       0 ns  392.17 ms  392.17 ms  392.17 ms  392.17 ms  392.17 ms  392.17 ms         0       0       0 ns
rampup/createFork      createLegumesScenario  1002.13 req/s    300644    9.16 ms   27.04 ms  133.69 ms    3.51 ms    4.52 ms  133.69 ms  133.69 ms  133.69 ms         0       0       0 ns
rampup/gettersFork     getLegumesScenario     4003.70 req/s   1201133   11.03 ms   49.55 ms  246.42 ms  501.76 μs    1.51 ms  246.42 ms  246.42 ms  246.42 ms         0       0       0 ns
mainPhase/createFork   createLegumesScenario  1679.87 req/s    506032  216.28 ms  538.92 ms    2.32 s     3.51 ms  876.61 ms    2.32 s     2.32 s     2.32 s          0    2060   53.73 s 
mainPhase/gettersFork  getLegumesScenario     6724.03 req/s   2025454  211.90 ms  538.97 ms    2.32 s   501.76 μs  872.42 ms    2.32 s     2.32 s     2.32 s          0    8124  200.97 s 
mainPhase/createFork/createLegumesScenario: Exceeded session limit
mainPhase/createFork/createLegumesScenario: Progress was blocked waiting for a free connection. Hint: increase http.sharedConnections.
mainPhase/gettersFork/getLegumesScenario: Exceeded session limit
mainPhase/gettersFork/getLegumesScenario: Progress was blocked waiting for a free connection. Hint: increase http.sharedConnections.

```

In this case the test failed because the app was not performant enough. The app started to throw errors and Hyperfoil exhausted the available pool of connections to perform more requests. This happened because all connections were waiting for the app to reply.  