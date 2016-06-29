package com.app.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDTO implements Serializable {

	private List<Param> params;

	private Map<String, String> paramMap;

	public BaseDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
		if (params != null && params.size() > 0) {
			paramMap = new HashMap<String, String>();
			for (Param param : params) {
				paramMap.put(param.getKey(), param.getValue());
			}
		}
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public static class Param {

		private String key;

		private String value;

		public Param() {
			// TODO Auto-generated constructor stub
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
