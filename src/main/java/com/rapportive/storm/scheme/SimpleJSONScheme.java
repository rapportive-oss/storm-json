package com.rapportive.storm.scheme;

import java.io.UnsupportedEncodingException;

import java.util.Collections;
import java.util.List;

import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import backtype.storm.spout.Scheme;

import backtype.storm.tuple.Fields;


public class SimpleJSONScheme implements Scheme {
    private static final long serialVersionUID = -7734176307841199017L;

    private final String encoding;


    public SimpleJSONScheme(String encoding) {
        this.encoding = encoding;
    }
    public SimpleJSONScheme() {
        this("UTF-8");
    }


    @Override
    public List<Object> deserialize(byte[] bytes) {
        final String chars;
        try {
            chars = new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        final Object json;
        try {
            json = JSONValue.parseWithException(chars);
        } catch (ParseException e) {
          throw new RuntimeException(e); // TODO this is a bit impolite
        }
        return Collections.singletonList(json);
    }


    @Override
    public Fields getOutputFields() {
        return new Fields("object");
    }
}
