# storm-json: JSON Serialisation for Storm #

storm-json provides JSON serialisation support for
[Storm](https://github.com/nathanmarz/storm).  It currently provides two
classes:

 * SimpleJSONScheme: an implementation of
   [`backtype.storm.spout.Scheme`](http://nathanmarz.github.com/storm/doc/backtype/storm/spout/Scheme.html)
   for writing spouts that consume from a source producing JSON.
 * SimpleJSONSerializer: an implementation of
   [`backtype.storm.serialization.ISerialization`](http://nathanmarz.github.com/storm/doc/backtype/storm/serialization/ISerialization.html)
   so that spouts and bolts can emit and receive tuples containing JSON values.

## Usage ##

To produce a jar:

    $ mvn package

To install in your local Maven repository:

    $ mvn install

To use in your `pom.xml`:

```xml
<project>
  <!-- ... -->
  <dependencies>
    <!-- ... -->
    <dependency>
      <groupId>com.rapportive</groupId>
      <artifactId>storm-json</artifactId>
      <version>0.0.1</version>
    </dependency>
    <!-- ... -->
  </dependencies>
  <!-- ... -->
</project>
```
