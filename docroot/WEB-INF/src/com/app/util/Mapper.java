package com.app.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public final class Mapper {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
				true);
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);

	}

	private Mapper() {

	}

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
