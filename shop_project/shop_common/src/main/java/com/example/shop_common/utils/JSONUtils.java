package com.example.shop_common.utils;

import com.example.shop_common.common.dto.CoreException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.function.Supplier;


/**
 * @author duo.tao
 * @Description: json处理工具类
 * @date 2022-06-13 23:14
 */
public final class JSONUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);

	private static final ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		SerializationConfig serializationConfig = mapper.getSerializationConfig();
		serializationConfig = serializationConfig.with(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN).without(SerializationFeature.WRITE_NULL_MAP_VALUES);
		mapper.setConfig(serializationConfig);
		// 忽略空值输出
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
		deserializationConfig = deserializationConfig.with(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS).without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setConfig(deserializationConfig);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

    /**
     * Instantiates a new JSON util.
     */
	private JSONUtils() {
	}

	private static ObjectMapper getMapper() {
		return mapper;
	}

    // -------------------------------------------------------------------------------------------

	/**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 用法：
     * Type returnType = method.getGenericReturnType();
     * Object result = JSONUtil.fromJSONString(json, returnType);
     * </pre>
     * 
     * @param <T> the generic type
     * @param json the json
     * @param type the type
     * @return the t
     */
	public static <T> T fromJSONString(String json, Type type) {
		try {
			ObjectMapper mapper = getMapper();
			JavaType typeReference = mapper.constructType(type);
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			LOGGER.info("反序列化报错Type:{}, JSON:{}", type, json);
			LOGGER.error("", e);
			throw new CoreException("反序列化报错Type");
		}
	}

	/**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 若反序列化的对象类型为<b><em>泛型</em></b>，则建议使用本。
     *  
     * 用法：
     * List&lt;QueryIndexDto&gt; subIndexes = JSONUtil.fromJSONString(content, new TypeReference&lt;List&lt;QueryIndexDto&gt;&gt;() {});
     * </pre>
     *
     * @param <T> the generic type
     * @param json the json
     * @param typeReference the type reference
     * @return the t
     */
	public static <T> T fromJSONString(String json, TypeReference<T> typeReference) {
		try {
			return getMapper().readValue(json, typeReference);
		} catch (IOException e) {
			LOGGER.info("反序列化报错Type:{}, JSON:{}", typeReference.getType(), json);
			LOGGER.error("", e);
			throw new CoreException("反序列化报错Type");
		}
	}

	/**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 用法：
     * ConsistenciesCompareJsonDto edcContext = JSONUtil.fromJSONString(jsonString, ConsistenciesCompareJsonDto.class);
     * 
     * 若反序列化的对象类型为<b><em>泛型</em></b>，则建议使用方法 {@link #fromJSONString(String, TypeReference)} ，示例如下：
     * List&lt;QueryIndexDto&gt; subIndexes = JSONUtil.fromJSONString(content, new TypeReference&lt;List&lt;QueryIndexDto&gt;&gt;() {});
     * </pre>
     *
     * @param <T> the generic type
     * @param json json字符串
     * @param clazz 目标类型
     * @return the t
     */
	public static <T> T fromJSONString(String json, Class<T> clazz) {
		try {
			return getMapper().readValue(json, clazz);
		} catch (IOException e) {
			LOGGER.info("反序列化报错Type:{}, JSON:{}", clazz, json);
			LOGGER.error("", e);
			throw new CoreException("反序列化报错Type");
		}
	}
	

    /**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 若反序列化的对象类型为<b><em>泛型</em></b>，则建议使用方法 {@link #fromJSONString(String, TypeReference)} ，示例如下：
     * List&lt;QueryIndexDto&gt; subIndexes = JSONUtil.fromJSONString(content, new TypeReference&lt;List&lt;QueryIndexDto&gt;&gt;() {});
     * 
     * 若源json串为空，则返回参数的值。
     * </pre>
     *
     * @param <T> the generic type
     * @param json the json
     * @param clazz the clazz
     * @param defaultValueIfNull the default value if null
     * @return the t
     */
    public static <T> T fromJSONString(String json, Class<T> clazz, T defaultValueIfNull) {
        if (DataUtil.isNullOrEmpty(json)) return defaultValueIfNull;
        return fromJSONString(json, clazz);
	}

    /**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 若反序列化的对象类型为<b><em>泛型</em></b>，则建议使用方法 {@link #fromJSONString(String, TypeReference)} ，示例如下：
     * List&lt;QueryIndexDto&gt; subIndexes = JSONUtil.fromJSONString(content, new TypeReference&lt;List&lt;QueryIndexDto&gt;&gt;() {});
     * 
     * 若源json串为空，则返回Supplier计算结果的值。
     * </pre>
     *
     * @param <T> the generic type
     * @param json the json
     * @param clazz the clazz
     * @param genDefaultValueIfNull the gen default value if null
     * @return the t
     */
    public static <T> T fromJSONString(String json, Class<T> clazz, Supplier<T> genDefaultValueIfNull) {
        if (DataUtil.isNullOrEmpty(json)) return genDefaultValueIfNull.get();
        return fromJSONString(json, clazz);
    }



    /**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 若反序列化的对象类型为<b><em>泛型</em></b>，则建议使用本方法。
     * 若源json串为空，则返回参数的值。
     * </pre>
     *
     * @param <T> the generic type
     * @param json the json
     * @param typeReference the type reference
     * @param defaultValueIfNull the default value if null
     * @return the t
     */
    public static <T> T fromJSONString(String json, TypeReference<T> typeReference, T defaultValueIfNull) {
        if (DataUtil.isNullOrEmpty(json)) return defaultValueIfNull;
        return fromJSONString(json, typeReference);
    }

    /**
     * 反序列化，From JSON string.
     * 
     * <pre>
     * 若反序列化的对象类型为<b><em>泛型</em></b>，则建议使用本方法。
     * 若源json串为空，则返回Supplier计算结果的值。
     * </pre>
     *
     * @param <T> the generic type
     * @param json the json
     * @param typeReference the type reference
     * @param genDefaultValueIfNull the gen default value if null
     * @return the t
     */
    public static <T> T fromJSONString(String json, TypeReference<T> typeReference, Supplier<T> genDefaultValueIfNull) {
        if (DataUtil.isNullOrEmpty(json)) return genDefaultValueIfNull.get();
        return fromJSONString(json, typeReference);
    }

	/**
     * 序列化，To JSON string.
     * 
     * @param <T> the generic type
     * @param t the t
     * @return the string
     * @JsonIgnoreProperties(ignoreUnknown = true)
     * 
     * ObjectMapper().write  
     * @JsonProperty(value = "DatabaseProjectManagerUrl")
     * @JsonFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
     */
	public static <T> String toJSONString(T t) {
		try {
			return getMapper().writeValueAsString(t);
		} catch (JsonProcessingException e) {
			LOGGER.info("序列化报错{}", t);
			LOGGER.error("", e);
			throw new CoreException("序列化报错");
		}
	}

}
