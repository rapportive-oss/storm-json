# storm-json: JSON Serialisation for Storm #

storm-json provides JSON serialisation support for
[Storm](https://github.com/nathanmarz/storm).  It currently provides two
classes:

 * [SimpleJSONScheme](http://code.rapportive.com/storm-json/doc/com/rapportive/storm/scheme/SimpleJSONScheme.html):
   an implementation of
   [`backtype.storm.spout.Scheme`](http://nathanmarz.github.com/storm/doc/backtype/storm/spout/Scheme.html)
   for writing spouts that consume from a source producing JSON.
 * [SimpleJSONSerializer](http://code.rapportive.com/storm-json/doc/com/rapportive/storm/serializer/SimpleJSONSerializer.html):
   an implementation of
   [`backtype.storm.serialization.ISerialization`](http://nathanmarz.github.com/storm/doc/backtype/storm/serialization/ISerialization.html)
   so that spouts and bolts can emit and receive tuples containing JSON values.

## Documentation ##

The Javadocs can be found at [http://code.rapportive.com/storm-json]().

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

## Caveats ##

`SimpleJSONSerializer` will only deserialise JSON objects, arrays, or `null`;
it will not accept top-level strings or numbers (although of course strings and
numbers *inside* arrays or objects are fine).

The behaviour on invalid JSON or top-level strings or numbers is currently to
throw an exception, which is not very friendly.
