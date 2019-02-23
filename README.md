# gfa-adam

Graphical Fragment Assembly (GFA) support for ADAM.

[![Build Status](https://travis-ci.org/heuermh/gfa-adam.svg?branch=master)](https://travis-ci.org/heuermh/gfa-adam)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.heuermh.gfaadam/gfa-adam_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.heuermh.gfaadam)

### Hacking gfa-adam

Install

 * JDK 1.8 or later, http://openjdk.java.net
 * Apache Maven 3.3.9 or later, http://maven.apache.org
 * Apache Spark 2.3.2 or later, http://spark.apache.org


To build

    $ mvn install


### Running gfa-adam

Graphical Fragment Assembly (GFA) version 1.0
```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa1 \
    target/gfa-adam_2.11-0.2.0-SNAPSHOT.jar \
    in.gfa \
    out.gfa
```

Graphical Fragment Assembly (GFA) version 2.0
```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa2 \
    target/gfa-adam_2.11-0.2.0-SNAPSHOT.jar \
    in.gfa2 \
    out.gfa2
```
