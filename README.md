# gfa-adam

Graphical Fragment Assembly (GFA) 2.0 support for ADAM.

### Hacking gfa-adam

Install

 * JDK 1.8 or later, http://openjdk.java.net
 * Apache Maven 3.3.9 or later, http://maven.apache.org
 * Apache Spark 2.2.0 or later, http://spark.apache.org


To build

    $ mvn install


To run

```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa \
    target/gfa-adam-1.0.0-SNAPSHOT.jar \
    in.gfa2 \
    out.gfa2
```
