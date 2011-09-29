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

public class SimpleJSONSerializer implements ISerialization<Object> {
    public static final String ENCODING = "UTF-8";

    @SuppressWarnings("rawtypes")
    @Override
    public boolean accept(Class c) {
        return JSONObject.class.equals(c) ||
          JSONArray.class.equals(c);
    }

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
