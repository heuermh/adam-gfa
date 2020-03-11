# adam-gfa

Graphical Fragment Assembly (GFA) support for ADAM.

[![Build Status](https://travis-ci.org/heuermh/adam-gfa.svg?branch=master)](https://travis-ci.org/heuermh/adam-gfa)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.heuermh.adamgfa/adam-gfa_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.heuermh.adamgfa)

### Hacking adam-gfa

Install

 * JDK 1.8 or later, http://openjdk.java.net
 * Apache Maven 3.3.9 or later, http://maven.apache.org
 * Apache Spark 2.4.5 or later, built for Scala 2.11 http://spark.apache.org


To build

    $ mvn install


### Running adam-gfa

Read and write Graphical Fragment Assembly (GFA) version 1.0

```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa1 \
    target/adam-gfa_2.11-${version}.jar \
    in.gfa \
    out.gfa
```


Transform GFA 1.0 to generic `Gfa1Record` records in Parquet format

```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa1ToDataframe \
    target/adam-gfa_2.11-${version}.jar \
    in.gfa \
    out.parquet
```


Transform GFA 1.0 to specific `Link`, `Path`, `Segment`, and `Traversal` records in Parquet format

```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa1ToDataframes \
    target/adam-gfa_2.11-${version}.jar \
    in.gfa \
    out
```
(creates separate `out.links.parquet`, `out.paths.parquet`, `out.segments.parquet`, and `out.traversals.parquet` directories)


Read and write Graphical Fragment Assembly (GFA) version 2.0

```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa2 \
    target/adam-gfa_2.11-${version}.jar \
    in.gfa2 \
    out.gfa2
```
