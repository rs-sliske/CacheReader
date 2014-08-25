package com.sk.cache.wrappers.protocol.extractor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

import com.sk.Debug;
import com.sk.datastream.Stream;

public class FieldExtractor {

	private final String fieldName;
	private final StreamExtractor extractor;

	public FieldExtractor(StreamExtractor ext, String fieldName) {
		this.fieldName = fieldName;
		this.extractor = ext;
	}

	public FieldExtractor(StreamExtractor ext) {
		this(ext, null);
	}

	public void read(Object destination, int minLoc, int type, Stream s) {
		Object newValue = extractor.get(s);
		if (fieldName != null) {
			setValue(destination, minLoc, type, fieldName, newValue);
		} else if (Debug.on) {
			System.out.println(type + " " + newValue);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setValue(Object destination, int minLoc, int type, String fieldName, Object newValue) {
		if (fieldName == null)
			return;
		Class<?> clazz = destination.getClass();
		try {
			Field field = clazz.getField(fieldName);
			if (field.getType().isArray() && (newValue == null || !newValue.getClass().isArray())) {
				Array.set(field.get(destination), type - minLoc, newValue);
			} else if (Collection.class.isAssignableFrom(field.getType())
					&& (newValue == null || !newValue.getClass().equals(field.getType()))) {
				((Collection) field.get(destination)).add(newValue);
			} else if (newValue.getClass().equals(Object[].class) && field.getType().isArray()) {
				int length = Array.getLength(newValue);
				Object copiedArr = Array.newInstance(field.getType().getComponentType(), length);
				for (int i = 0; i < length; ++i) {
					Array.set(copiedArr, i, Array.get(newValue, i));
				}
				field.set(destination, copiedArr);
			} else {
				field.set(destination, newValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("Failed to put data into destination object %s field %s",
					destination, fieldName));
		}
	}

	public static FieldExtractor[] wrap(StreamExtractor... extractors) {
		FieldExtractor[] ret = new FieldExtractor[extractors.length];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = new FieldExtractor(extractors[i]);
		}
		return ret;
	}
}
