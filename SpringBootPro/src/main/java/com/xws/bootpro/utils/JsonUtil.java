package com.xws.bootpro.utils;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义响应结构
 */
@Slf4j
public class JsonUtil {
 
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
 
	/**
	 * 将对象转换成json字符串。
	 * <p>
	 * Title: pojoToJson
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			log.error("JsonUtil类将对象转换成json字符串异常：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 将json结果集转化为对象
	 * 
	 * @param jsonData
	 *            json数据
	 * @param beanType
	 *            对象中的object类型
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			log.error("JsonUtil类将json结果集转化为对象异常：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 将json数据转换成pojo对象list
	 * Title: jsonToList
	 * Description:
	 * 
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			log.error("JsonUtil类将json数据转换成pojo对象list异常：" + e.getMessage());
			e.printStackTrace();
		}
 
		return null;
	}
 
}