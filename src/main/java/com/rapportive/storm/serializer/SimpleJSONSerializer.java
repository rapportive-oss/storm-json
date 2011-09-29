package com.rapportive.storm.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import backtype.storm.serialization.ISerialization;

/**
 * Serialisation scheme for JSON values using the json-simple library.
 * Assumes the deserialised form will be a UTF-8-encoded string, and serialises
 * into that form.
 *
 * <strong>N.B.</strong> currently this only supports JSON objects and arrays
 * at the top level (though nested values are fine).
 *
 * <strong>N.B.</strong> if passed invalid JSON it will throw an
 * IllegalArgumentException.
 *
 * @author Sam Stokes (sam@rapportive.com)
 * @see <a href="http://code.google.com/p/json-simple/">json-simple</a>
 */
public class SimpleJSONSerializer implements ISerialization<Object> {
    /**
     * Encoding used to serialise and deserialise between byte streams and
     * JSON structures.
     *
     * This is hard-coded to UTF-8 because the mechanism for registering a
     * serialisation does not allow passing arguments to the serialisation
     * constructor.
     */
    public static final String ENCODING = "UTF-8";

    /**
     * Returns whether this serialisation can handle the given type.
     *
     * @return <tt>true</tt> if c is {@link org.json.simple.JSONObject} or
     *           {@link org.json.simple.JSONArray}.
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean accept(Class c) {
        return JSONObject.class.equals(c) ||
          JSONArray.class.equals(c);
    }

    /**
     * Serialise a JSON object or array to the stream.
     *
     * @throws IllegalArgumentException  if <tt>object</tt> is not a JSON
     *           object or array
     * @throws IOException  if there is an error writing to the stream.
     */
    @Override
    public void serialize(Object object, DataOutputStream stream)
            throws IOException {
        final Writer writer = new OutputStreamWriter(stream, ENCODING);
        if (object instanceof JSONObject) {
            ((JSONObject) object).writeJSONString(writer);
        } else if (object instanceof JSONArray) {
            ((JSONArray) object).writeJSONString(writer);
        } else {
            throw new IllegalArgumentException("Unexpected class " + object.getClass().getCanonicalName());
        }
        writer.flush();
    }

    /**
     * Deserialise a JSON value from the stream.
     *
     * @throws IllegalArgumentException  if unable to parse a JSON value from
     *           the stream
     */
    @Override
    public Object deserialize(DataInputStream stream) throws IOException {
        final Reader reader = new InputStreamReader(stream, ENCODING);
        try {
            return JSONValue.parseWithException(reader);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e); // TODO this is a bit impolite
        }
    }
}
