# adam-gfa

Graphical Fragment Assembly (GFA) support for ADAM.

[![Build Status](https://travis-ci.org/heuermh/adam-gfa.svg?branch=master)](https://travis-ci.org/heuermh/adam-gfa)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.heuermh.adam.gfa/adam-gfa_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.heuermh.adam.gfa)

### Hacking adam-gfa

Install

 * JDK 1.8 or later, http://openjdk.java.net
 * Apache Maven 3.3.9 or later, http://maven.apache.org
 * Apache Spark 2.3.2 or later, http://spark.apache.org


To build

    $ mvn install


### Running adam-gfa

Graphical Fragment Assembly (GFA) version 1.0
```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa1 \
    target/adam-gfa_2.11-0.2.0-SNAPSHOT.jar \
    in.gfa \
    out.gfa
```

Graphical Fragment Assembly (GFA) version 2.0
```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa2 \
    target/adam-gfa_2.11-0.2.0-SNAPSHOT.jar \
    in.gfa2 \
    out.gfa2
```
