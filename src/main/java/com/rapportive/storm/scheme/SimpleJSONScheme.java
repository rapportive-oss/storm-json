package com.rapportive.storm.scheme;

import java.io.UnsupportedEncodingException;

import java.util.Collections;
import java.util.List;

import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import backtype.storm.spout.Scheme;

import backtype.storm.tuple.Fields;


/**
 * Deserialisation scheme for JSON values using the json-simple library.
 * Emits one-element tuples with the field name <tt>object</tt>, containing
 * the parsed JSON value.
 *
 * <strong>N.B.</strong> if passed invalid JSON it will throw an
 * IllegalArgumentException.
 *
 * @author Sam Stokes (sam@rapportive.com)
 * @see <a href="http://code.google.com/p/json-simple/">json-simple</a>
 */
public class SimpleJSONScheme implements Scheme {
    private static final long serialVersionUID = -7734176307841199017L;

    private final String encoding;


    /**
     * Create a new JSON deserialisation scheme using the given character
     * encoding.
     *
     * @param encoding  character encoding used to deserialise JSON from raw
     *                  bytes
     */
    public SimpleJSONScheme(String encoding) {
        this.encoding = encoding;
    }
    /**
     * Create a new JSON deserialisation scheme using UTF-8 as the character
     * encoding.
     */
    public SimpleJSONScheme() {
        this("UTF-8");
    }


    /**
     * Deserialise a JSON value from <tt>bytes</tt> using the requested
     * character encoding.
     *
     * @return a one-element tuple containing the parsed JSON value.
     *
     * @throws IllegalArgumentException  if <tt>bytes</tt> does not contain
     *           valid JSON encoded using the requested encoding.
     * @throws IllegalStateException  if the requested character encoding is
     *           not supported.
     */
    @Override
    public List<Object> deserialize(byte[] bytes) {
        final String chars;
        try {
            chars = new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        final Object json;
        try {
            json = JSONValue.parseWithException(chars);
        } catch (ParseException e) {
          throw new IllegalArgumentException(e); // TODO this is a bit impolite
        }
        return Collections.singletonList(json);
    }


    /**
     * Emits tuples containing only one field, named "object".
     */
    @Override
    public Fields getOutputFields() {
        return new Fields("object");
    }
}
