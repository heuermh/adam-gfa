# adam-gfa

Graphical Fragment Assembly (GFA) support for ADAM.

[![Build Status](https://travis-ci.org/heuermh/adam-gfa.svg?branch=master)](https://travis-ci.org/heuermh/adam-gfa)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.heuermh.adamgfa/adam-gfa_2.12.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.heuermh.adamgfa)
[![API Documentation](http://javadoc.io/badge/com.github.heuermh.adamgfa/adam-gfa_2.12.svg?color=brightgreen&label=scaladoc)](http://javadoc.io/doc/com.github.heuermh.adamgfa/adam-gfa_2.12)

### Hacking adam-gfa

Install

 * JDK 1.8 or later, http://openjdk.java.net
 * Apache Maven 3.3.9 or later, http://maven.apache.org
 * Apache Spark 3.0.0 or later, built for Scala 2.12 http://spark.apache.org


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


Transform GFA 1.0 to generic [`Gfa1Record`](#gfa1record) records in Parquet format

```
$ spark-submit \
    --class com.github.heuermh.adam.gfa.Gfa1ToDataframe \
    target/adam-gfa_2.11-${version}.jar \
    in.gfa \
    out.parquet
```


Transform GFA 1.0 to specific [`Link`](#link), [`Path`](#path), [`Segment`](#segment), and [`Traversal`](#traversal) records in Parquet format

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

### Graphical Fragment Assembly (GFA) version 1.0 schema in Parquet format

#### Gfa1Record

[Gfa1Record (scaladoc)](https://www.javadoc.io/static/com.github.heuermh.adamgfa/adam-gfa_2.12/0.6.0/com/github/heuermh/adam/gfa/sql/gfa1/Gfa1Record.html)

```
message spark_schema {
  optional binary recordType (UTF8);
  optional binary id (UTF8);
  optional binary sequence (UTF8);
  optional int32 length;
  optional int32 readCount;
  optional int32 fragmentCount;
  optional int32 kmerCount;
  optional binary sequenceChecksum (UTF8);
  optional binary sequenceUri (UTF8);
  optional group source {
    optional binary id (UTF8);
    optional binary orientation (UTF8);
  }
  optional group target {
    optional binary id (UTF8);
    optional binary orientation (UTF8);
  }
  optional binary overlap (UTF8);
  optional int32 mappingQuality;
  optional int32 mismatchCount;
  optional binary pathName (UTF8);
  optional group segments (LIST) {
    repeated group list {
      optional group element {
        optional binary id (UTF8);
        optional binary orientation (UTF8);
      }
    }
  }
  optional group overlaps (LIST) {
    repeated group list {
      optional binary element (UTF8);
    }
  }
  optional int32 ordinal;
  optional group tags (MAP) {
    repeated group key_value {
      required binary key (UTF8);
      optional group value {
        optional binary name (UTF8);
        optional binary type (UTF8);
        optional binary value (UTF8);
      }
    }
  }
}
```

#### Link

[Link (scaladoc)](https://www.javadoc.io/static/com.github.heuermh.adamgfa/adam-gfa_2.12/0.6.0/com/github/heuermh/adam/gfa/sql/gfa1/Link.html)

```
message spark_schema {
  optional binary id (UTF8);
  optional group source {
    optional binary id (UTF8);
    optional binary orientation (UTF8);
  }
  optional group target {
    optional binary id (UTF8);
    optional binary orientation (UTF8);
  }
  optional binary overlap (UTF8);
  optional int32 mappingQuality;
  optional int32 mismatchCount;
  optional int32 readCount;
  optional int32 fragmentCount;
  optional int32 kmerCount;
  optional group tags (MAP) {
    repeated group key_value {
      required binary key (UTF8);
      optional group value {
        optional binary name (UTF8);
        optional binary type (UTF8);
        optional binary value (UTF8);
      }
    }
  }
}
```

#### Path

[Path (scaladoc)](https://www.javadoc.io/static/com.github.heuermh.adamgfa/adam-gfa_2.12/0.6.0/com/github/heuermh/adam/gfa/sql/gfa1/Path.html)

```
message spark_schema {
  optional binary pathName (UTF8);
  optional group segments (LIST) {
    repeated group list {
      optional group element {
        optional binary id (UTF8);
        optional binary orientation (UTF8);
      }
    }
  }
  optional group overlaps (LIST) {
    repeated group list {
      optional binary element (UTF8);
    }
  }
  optional group tags (MAP) {
    repeated group key_value {
      required binary key (UTF8);
      optional group value {
        optional binary name (UTF8);
        optional binary type (UTF8);
        optional binary value (UTF8);
      }
    }
  }
}
```

#### Segment

[Segment (scaladoc)](https://www.javadoc.io/static/com.github.heuermh.adamgfa/adam-gfa_2.12/0.6.0/com/github/heuermh/adam/gfa/sql/gfa1/Segment.html)

```
message spark_schema {
  optional binary id (UTF8);
  optional binary sequence (UTF8);
  optional int32 length;
  optional int32 readCount;
  optional int32 fragmentCount;
  optional int32 kmerCount;
  optional binary sequenceChecksum (UTF8);
  optional binary sequenceUri (UTF8);
  optional group tags (MAP) {
    repeated group key_value {
      required binary key (UTF8);
      optional group value {
        optional binary name (UTF8);
        optional binary type (UTF8);
        optional binary value (UTF8);
      }
    }
  }
}
```

#### Traversal

[Traversal (scaladoc)](https://www.javadoc.io/static/com.github.heuermh.adamgfa/adam-gfa_2.12/0.6.0/com/github/heuermh/adam/gfa/sql/gfa1/Traversal.html)

```
message spark_schema {
  optional binary pathName (UTF8);
  optional int32 ordinal;
  optional group source {
    optional binary id (UTF8);
    optional binary orientation (UTF8);
  }
  optional group target {
    optional binary id (UTF8);
    optional binary orientation (UTF8);
  }
  optional binary overlap (UTF8);
  optional group tags (MAP) {
    repeated group key_value {
      required binary key (UTF8);
      optional group value {
        optional binary name (UTF8);
        optional binary type (UTF8);
        optional binary value (UTF8);
      }
    }
  }
}
```
