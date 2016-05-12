package com.smartystreets.api;

public class GoogleSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public T deserialize(byte[] payload) {
        //T.class

        return null;
    }


}



/*
        JacksonFactory jacksonFactory = new JacksonFactory();
        StringWriter jsonWriter = new StringWriter();
        JsonGenerator generator = jacksonFactory.createJsonGenerator(jsonWriter);

        generator.serialize(obj);
        generator.close();
 */
