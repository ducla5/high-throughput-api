## High Throughput API Demo

### Introduction
 - Using Guava to caching Gzip Object and return directly to client without decompress.
 - Application will handle compress data only one time when object is get the first time < and cache it>
 - Using RabbitMQ to publish/consummer invalidate caching message

### How to
```
$ mvn clean package docker:build -DskipTests
$ docker-compose up -d
```
### API
 - Get all products:
   - http://localhost:8080/products/
 - Get product with cache <Will get from DB on the first time or after cache is invalidate>
   - http://localhost:8080/products/cache/{id}
 - Get product without cache
   - http://localhost:8080/products/nocache/{id}
 - Invalidate a specific product cache
   - http://localhost:8080/products/invalidate/{id}
 - Invalidate all products cache
   - http://localhost:8080/products/invalidate

### Benmark Result
- Without cache. Webserver handle zip process
  ```
  $ wrk -c 10000 -d 60 -t 10 --latency http://localhost:8080/products/nocache/5d9db5ae69515b0001cf4db3
  Running 1m test @ http://localhost:8080/products/nocache/5d9db5ae69515b0001cf4db3
    10 threads and 10000 connections
    Thread Stats   Avg      Stdev     Max   +/- Stdev
      Latency   322.14ms  279.10ms   2.00s    78.63%
      Req/Sec   469.57    436.44     2.25k    77.36%
    Latency Distribution
      50%  216.31ms
      75%  465.50ms
      90%  632.40ms
      99%    1.46s 
    182975 requests in 1.00m, 629.61MB read
    Socket errors: connect 8990, read 0, write 0, timeout 637
  Requests/sec:   3045.89
  Transfer/sec:     10.48MB
  ```
- With cache gzip object
  ```
  $ wrk -c 10000 -d 60 -t 10 --latency http://localhost:8080/products/cache/5d9db5ae69515b0001cf4db3 
  Running 1m test @ http://localhost:8080/products/cache/5d9db5ae69515b0001cf4db3
    10 threads and 10000 connections
    Thread Stats   Avg      Stdev     Max   +/- Stdev
      Latency    43.40ms   23.03ms   1.21s    78.01%
      Req/Sec     2.32k     1.98k    9.97k    63.24%
    Latency Distribution
      50%   43.38ms
      75%   53.16ms
      90%   65.59ms
      99%  103.61ms
    1383367 requests in 1.00m, 3.57GB read
    Socket errors: connect 8990, read 0, write 0, timeout 0
  Requests/sec:  23020.04
  Transfer/sec:     60.75MB
  ```
