package com.example.shop_common.utils;

import com.example.shop_common.common.dto.CoreException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author duo.tao
 * @Description: 数据处理工具类
 * @date 2022-06-13 23:14
 */
public class DataUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtil.class);

    /**
     * Description: 判断换行的正则表达式
     */
    private static final String REGEXP_LINE_CRLF = "((\\r\\n)|\\r|\\n)";
    /**
     * Description:变量名称。
     */
    private static final String REGEXP_VAR_NAME = "\\w+";

    private static final String CHR_EQUAL = "=";

    private DataUtil() {
    }

    // ------------------------------------------------------------------------

    /**
     * Not null.
     *
     * @param object the object
     * @return true, if successful
     */
    public static boolean notNull(Object object) {
        return object != null;
    }

    /**
     * Not null or empty.
     *
     * @param string the string
     * @return true, if successful
     */
    public static boolean notNullOrEmpty(String string) {
        return !StringUtils.isEmpty(string);
    }

    public static boolean notNullOrEmpty(Object[] objects) {
        return objects != null && objects.length > 0;
    }

    /**
     * Not null or empty.
     *
     * @param collection the collection
     * @return true, if successful
     */
    public static boolean notNullOrEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }
    // ------------------------------------------------------------------------

    /**
     * contains.
     *
     * @param collection the collection
     * @return true, if successful
     */
    public static boolean contains(Collection<?> collection, Collection<?> mCollection) {
        if (notNullOrEmpty(collection) && notNullOrEmpty(mCollection)) {
            for (Object o : mCollection) {
                if (collection.contains(o)) {
                    return true;
                }
            }
        }
        return false;
    }
    // ------------------------------------------------------------------------

    /**
     * Checks if is null.
     *
     * @param object the object
     * @return true, if is null
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Checks if is null or empty.
     *
     * @param string the string
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(String string) {
        return StringUtils.isEmpty(string);
    }

    /**
     * Checks if is null or empty.
     *
     * @param collection the collection
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks if is null or empty.
     *
     * @param map the dataAuthMap
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean notNullOrEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * Empty to null.
     *
     * @param value the value
     * @return the string
     */
    public static String emptyToNull(String value) {
        return "".equals(value) ? null : value;
    }


    public static String toString(Object object) {
        return toString(object, DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * To string.
     *
     * @param object the object
     * @return the string
     */
    public static String toString(Object object, String dateFormat) {
        if (object == null) {
            return null;
        } else if (object instanceof String) {
            return (String) object;
        } else if (object instanceof Date) {
            if (notNullOrEmpty(dateFormat)) {
                return new SimpleDateFormat(dateFormat).format(object);
            }
            return new SimpleDateFormat(dateFormat).format(DateUtil.YYYY_MM_DD_HH_MM_SS);
        } else if (object instanceof BigDecimal) { // BigDecimal情况下，对尾部的.00情况移除
            String string = String.valueOf(object);
            while (string.contains(".") && StringUtils.endsWith(string, "0")) {
                string = StringUtils.removeEnd(string, "0");
            }
            if (string.endsWith(".")) {
                string = StringUtils.removeEnd(string, ".");
            }
            return string;
        } else {
            return String.valueOf(object);
        }

    }

    /**
     * 返回第一个非空的对象.
     *
     * @param values the values
     * @return the object
     */
    public static final Object firstNotNull(Object... values) {
        return ObjectUtils.firstNonNull(values);
    }

    /**
     * 返回第一个非空（及非""）的字符串。
     * 没有匹配的情况下，返回最后一个。.
     *
     * @param strings the strings
     * @return the string
     */
    public static final String firstNotNullOrBlank(String... strings) {
        if (strings == null) {
            return null;
        }
        if (strings.length == 0) {
            return null;
        }
        return Arrays.stream(strings).filter(DataUtil::notNullOrEmpty).findFirst().orElse(strings[strings.length - 1]);
    }

    /**
     * 转换为Long对象。.
     *
     * @param object the object
     * @return the long
     */
    public static Long toLong(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        if (object instanceof Integer) {
            return Long.valueOf((Integer) object);
        }
        if (object instanceof String) {
            return Long.parseLong((String) object);
        }
        return Long.parseLong(String.valueOf(object));
    }

    /**
     * 转换为Integer对象。.
     *
     * @param object the object
     * @return the integer
     */
    public static Integer toInteger(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        if (object instanceof Integer) {
            return (Integer) object;
        }
        if (object instanceof Long) {
            return ((Long) object).intValue();
        }
        if (object instanceof String) {
            return Double.valueOf((String) object).intValue();
        }
        return Integer.parseInt(String.valueOf(object));
    }

    /**
     * 将字符串值转换为指定类型的数据。
     *
     * @param value
     * @param clazz
     * @return
     */
    public static Object toType(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (clazz == Date.class) {
            return DateUtil.parseDateInLongFormat(value);
        } else if (clazz == Long.class) {
            return Long.valueOf(value);
        } else if (clazz == BigDecimal.class) {
            return new BigDecimal(value);
        } else {
            return value;
        }
    }

    // ------------------------------------------------------------------------

    /**
     * 对mapper.clone(s, t)进行一次封装。
     *
     * @param <S> the generic type
     * @param <T> the generic type
     * @param s   原来
     * @param t   目标
     * @return 返回目标
     */
    public static <S, T> T cloneBean(S s, T t) {
        if (s != null && t != null) {
            BeanUtils.copyProperties(s, t); //
        }
        return t;
    }

    /**
     * Clone bean.
     *
     * @param <S> the generic type
     * @param s   the s
     * @return the s
     */
    public static <S> S cloneBean(S s) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked") S result = (S) cloneBean(s, s.getClass());
        return result;
    }

    /**
     * Clone bean.
     *
     * @param <S>    the generic type
     * @param <T>    the generic type
     * @param s      the s
     * @param clazzT the clazz T
     * @return the t
     */
    public static <S, T> T cloneBean(S s, Class<T> clazzT) {
        if (s == null) {
            return null;
        }
        //
        try {
            T t = clazzT.getDeclaredConstructor().newInstance();
            cloneBean(s, t);
            return t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            LOGGER.error("", e);
        }
        return null;
    }

    /**
     * Clone bean with non null fields.
     *
     * @param <S> the generic type
     * @param <T> the generic type
     * @param s   the s
     * @param t   the t
     * @return the t
     */
    public static <S, T> T cloneBeanWithNonNullFields(S s, T t) {
        if (t == null) {
            return null;
        }
        if (s == null) {
            return t;
        }
        Set<Field> fields = getNonNullFields(s);
        if (isNullOrEmpty(fields)) {
            return t;
        }
        //
        fields.stream().map(Field::getName).forEach(name -> {
            try {
                Object value = FieldUtils.readField(s, name, true);
                FieldUtils.writeField(t, name, value, true);
            } catch (IllegalAccessException e) {
                LOGGER.error("", e);
            }
        });
        return t;
    }

    /**
     * 获取对象的属性Field（仅值非空的）.
     *
     * @param object the object
     * @return the non null fields
     */
    public static final Set<Field> getNonNullFields(Object object) {
        List<Field> fields = FieldUtils.getAllFieldsList(object.getClass());
        return getNonNullFields(object, fields);
    }

    /**
     * 获取对象的属性Field（仅值非空的）.
     *
     * @param object the object
     * @param fields the fields
     * @return the non null fields
     */
    public static final Set<Field> getNonNullFields(Object object, List<Field> fields) {
        return fields.stream().filter(field -> {
            boolean isNonNull = false;
            try {
                isNonNull = Objects.nonNull(FieldUtils.readField(field, object, true));
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return isNonNull;
        }).collect(Collectors.toSet());
    }

    // ------------------------------------------------------------------------

    /**
     * 相等
     * 前提：都不为null或""
     * 通过转为String进行判断的。.
     *
     * @param <T> the generic type
     * @param a   the a
     * @param b   the b
     * @return true, if successful
     */
    public static <T extends Comparable<T>> boolean equalsByStringNotNullOrEmpty(Object a, Object b) {
        if (isNull(a) || isNull(b)) {
            return false;
        }
        if (a instanceof String && isNullOrEmpty((String) a) || b instanceof String && isNullOrEmpty((String) b)) {
            return false;
        }
        return Optional.ofNullable(toString(a)).map(e -> e.equals(toString(b))).orElse(false);
    }

    /**
     * 相等
     * 前提：都不为null或"".
     *
     * @param <T> the generic type
     * @param a   the a
     * @param b   the b
     * @return true, if successful
     */
    public static <T extends Comparable<T>> boolean equalsNotNullOrEmpty(T a, T b) {
        if (isNull(a) || isNull(b)) {
            return false;
        }
        if (a instanceof String && isNullOrEmpty((String) a) || b instanceof String && isNullOrEmpty((String) b)) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * 相等.
     *
     * @param ary1 the ary 1
     * @param ary2 the ary 2
     * @return true, if successful
     */
    public static boolean equals(byte[] ary1, byte[] ary2) {
        if (ary1 == null || ary2 == null) {
            return false;
        }
        if (ary1.length != ary2.length) {
            return false;
        }
        for (int i = 0; i < ary2.length; i++) {
            if (ary1[i] != ary2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 相等.
     *
     * @param <T> the generic type
     * @param a   the a
     * @param b   the b
     * @return true, if successful
     */
    public static <T extends Comparable<T>> boolean equals(T a, T b) {
        return !notEquals(a, b);
    }

    /**
     * 不等.
     *
     * @param <T> the generic type
     * @param v1  the v 1
     * @param v2  the v 2
     * @return true, if successful
     */
    public static <T extends Comparable<T>> boolean notEquals(T v1, T v2) {
        if (v1 == null && v2 == null) {
            return false;
        }
        if (v1 == null || v2 == null) {
            return true;
        }
        return v1.compareTo(v2) != 0;
    }

    // ------------------------------------------------------------------------

    /**
     * 转换为百分比字符串，指定留n位小数位.
     *
     * @param bigDecimal the big decimal
     * @param scale      the scale
     * @return the string
     */
    public static String toPercentageString(BigDecimal bigDecimal, int scale) {
        BigDecimal result = bigDecimal.multiply(BigDecimal.valueOf(100)).setScale(scale);
        return result + "%";
    }

    /**
     * 转换为百分比字符串，默认留1位小数位.
     *
     * @param bigDecimal the big decimal
     * @return the string
     */
    public static String toPercentageString(BigDecimal bigDecimal) {
        return toPercentageString(bigDecimal, 1);
    }

    /**
     * 将null替换为默认值"".
     *
     * @param value the value
     * @return the string
     */
    public static String nullToEmpty(String value) {
        return nullTo(value, "");
    }

    /**
     * 将null替换为默认值.
     *
     * @param <T>   the generic type
     * @param value the value
     * @param def   the def
     * @return the t
     */
    public static <T> T nullTo(T value, T def) {
        return Objects.isNull(value) ? def : value;
    }


    /**
     * Gets the val not null. 尝试获取对象的属性，为空判断返回 nullDef 值。
     * <pre>
     * Sample 没有使用该方法的代码:
     * DataUtils.notNull(n.getLdi()) ? n.getLdi().toPlainString() : null
     * Sample 切换使用该方法的代码:
     * DataUtils.getBeanProp(n.getLdi(), null, BigDecimal::toPlainString)
     *
     * </pre>
     *
     * @param <T>    the generic type
     * @param <R>    the generic type
     * @param getter the getter
     * @return the val not null
     */
    public static <T, R> R getBeanProp(T bean, R nullDef, Function<T, R> getter) {
        R result = nullDef;
        if (notNull(bean)) {
            R temp = getter.apply(bean);
            if (notNull(temp)) {
                result = temp;
            }
        }
        return result;
    }

    /**
     * Gets the val not null. 尝试获取对象的属性，为空判断返回nulldef值。
     *
     * @param <T>     the generic type
     * @param <R>     the generic type
     * @param <S>     the generic type
     * @param nulldef the nulldef
     * @param getter1 the getter 1
     * @param getter2 the getter 2
     * @return the val not null
     */
    public static <T, R, S> S getBeanProp(T bean, S nulldef, Function<T, R> getter1, Function<R, S> getter2) {
        S result = nulldef;
        if (notNull(bean)) {
            R temp1 = getter1.apply(bean);
            if (notNull(temp1)) {
                S temp2 = getter2.apply(temp1);
                if (notNull(temp2)) {
                    result = temp2;
                }
            }
        }
        return result;
    }

    /**
     * Gets the val not null or empty. 尝试获取对象的属性，为空判断返回nulldef值。
     *
     * @param <T>     the generic type
     * @param nullDef the nullDef
     * @param getter  the getter
     * @return the val not null or empty
     */
    public static <T> String getBeanProp(T bean, String nullDef, Function<T, String> getter) {
        String result = nullDef;
        if (notNull(bean)) {
            String temp = getter.apply(bean);
            if (notNullOrEmpty(temp)) {
                result = temp;
            }
        }
        return result;
    }

    /**
     * Gets the val not null or empty. 尝试获取对象的属性，为空判断返回nulldef值。
     *
     * @param <T>     the generic type
     * @param <R>     the generic type
     * @param nulldef the nulldef
     * @param getter1 the getter 1
     * @param getter2 the getter 2
     * @return the val not null or empty
     */
    public static <T, R> String getBeanProp(T bean, String nulldef, Function<T, R> getter1, Function<R, String> getter2) {
        String result = nulldef;
        if (notNull(bean)) {
            R temp1 = getter1.apply(bean);
            if (notNull(temp1)) {
                String temp2 = getter2.apply(temp1);
                if (notNullOrEmpty(temp2)) {
                    result = temp2;
                }
            }
        }
        return result;
    }

    /**
     * Removes the str repeat.
     *
     * @param str   the str
     * @param split the split
     * @return the string
     * @Description: 清除字符串重复的数据，如“A,B,C,A,B”变成A,B,C
     * @Author staton
     * @Date 2018/11/28 16:03
     */
    public static String removeStrRepeat(String str, String split) {
        if (isNullOrEmpty(str)) {
            return null;
        }
        return Arrays.stream(StringUtils.splitPreserveAllTokens(str, split)).collect(Collectors.toSet()).stream().collect(Collectors.joining(split));
    }

    public static Map<String, Object> objectToMapNoNull(Object obj) {
        return objectAllToMap(obj, false);
    }

    private static Map<String, Object> objectAllToMap(Object obj, boolean withNull) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                key = toLowerCaseFirstOne(key);
                if (key.compareToIgnoreCase("class") == 0 || "extData".equals(key)) {
                    continue;
                }
                Method getter = property.getReadMethod();
                if (getter == null || getter.isAnnotationPresent(JsonIgnore.class)) {
                    continue;
                }
                Object value = getter.invoke(obj);
                if (value != null || withNull) {
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            LOGGER.error("对象转Map报错，抛出异常e：{}", e);
        }

        return map;
    }

    /**
     * 这种方法不能保持属性的原类型
     * objectAllToMap 可以保持数据的类型
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return Collections.emptyMap();
        }
        try {
            return JSONUtils.fromJSONString(JSONUtils.toJSONString(obj), Map.class);
        } catch (Exception e) {
            LOGGER.error("对象转Map报错，抛出异常e：{}", e);
        }
        return Collections.emptyMap();
    }

    /**
     * Map to object.
     *
     * @param <T>       the generic type
     * @param map       the dataAuthMap
     * @param beanClass the bean class
     * @return the t
     * @Description: map转对象
     * @Author staton
     * @Date 2018/12/6 19:44
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }
        try {
            T t = beanClass.getDeclaredConstructor().newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (DataUtil.isNull(setter)) {
                    continue;
                }

                Object obj = DataUtil.notNull(map.get(property.getName())) ? convertValType(setter, map.get(property.getName())) : null;
                setter.invoke(t, obj);
            }
            return t;
        } catch (Exception e) {
            LOGGER.error("Map转对象报错，抛出异常e：{}", e);
        }
        return null;
    }

    /**
     * @param
     * @return
     * @Description: 针对日期类型做特殊处理
     * @Author staton
     * @Date 2018/12/10 16:51
     */
    private static Object convertValType(Method setter, Object value) {
        Object retVal = value;
        if ("java.util.Date".equals(setter.getParameterTypes()[0].getName())) {
            retVal = DateUtil.format((Date) value, DateUtil.YYYY_MM_DD_HH_MM);
        } else if ("java.lang.Integer".equals(setter.getParameterTypes()[0].getName())) {
            retVal = Integer.valueOf(value.toString());
        }
        return retVal;
    }

    /**
     * 合并带逗号的字符串。.
     *
     * @param oldStrings the old strings
     * @param newStrings the new strings
     * @return the string
     */
    public static final String mergeStrings(String oldStrings, String newStrings) {
        if (DataUtil.isNullOrEmpty(oldStrings) && DataUtil.isNullOrEmpty(newStrings)) {
            return null;
        }
        if (DataUtil.isNullOrEmpty(oldStrings)) {
            return newStrings;
        }
        if (DataUtil.isNullOrEmpty(newStrings)) {
            return oldStrings;
        }
        //
        List<String> oldList = Arrays.stream(oldStrings.split(",")).collect(toList());
        List<String> newList = Arrays.stream(newStrings.split(",")).toList();
        newList.forEach(o -> {
            if (!oldList.contains(o)) {
                oldList.add(o);
            }
        });
        return String.join(",", oldList);
    }


    /**
     * 将从注解中获取的中文名称，进行切除冒号以后的文字，作为字段中文名称之用。.
     *
     * @param name the name
     * @return the string
     */
    public static final String subStringToBizName(String name) {
        if (name.contains("：")) {
            name = StringUtils.substringBefore(name, "：");
        }
        if (name.contains(":")) {
            name = StringUtils.substringBefore(name, ":");
        }
        return name;
    }

    /**
     * 字符串数字保留一位小数
     * 如果能拆解，则吐出格式化后的字符串，如果不能拆解，吐出原字符串.
     *
     * @param str       100   100.0  100.00  100%   100.0%  100.00%
     * @param needChage the need chage
     * @return the string
     */
    public static final String holdOneDecimalPlace(String str, boolean needChage) {
        if (DataUtil.isNullOrEmpty(str)) {
            return null;
        }
        if (!needChage) {
            return str;
        }
        String xx = str.substring(str.length() - 1);
        Number number;
        try {
            number = NumberFormat.getInstance().parse(str);
        } catch (ParseException e) {
            return null;
        }
        String result = new BigDecimal(number.toString()).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
        if ("%".equals(xx)) {
            result += xx;
        }
        return result;
    }

    /**
     * Strip trailing zeros.
     *
     * @param bigDecimal the big decimal
     * @return the big decimal
     */
    public static final BigDecimal stripTrailingZeros(BigDecimal bigDecimal) {
        if (isNull(bigDecimal)) {
            return null;
        }
        return bigDecimal.stripTrailingZeros();
    }

    /**
     * Gets the big decimal.
     *
     * @param data        the data
     * @param defaultData the default data
     * @return the big decimal
     */
    public static final BigDecimal getBigDecimal(String data, BigDecimal defaultData) {
        if (DataUtil.isNullOrEmpty(data)) {
            return defaultData;
        }
        String rule = "([-]?\\d+\\.?\\d*).*";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(data);
        if (!matcher.matches()) {
            return defaultData;
        }
        String dataPart = matcher.group(1);
        if (DataUtil.notNullOrEmpty(dataPart)) {
            return new BigDecimal(dataPart);
        }
        return defaultData;
    }

    /**
     * Gets the big decimal.
     *
     * @param data        the data
     * @param defaultData the default data
     * @return the big decimal
     */
    public static final BigDecimal getBigDecimal(Object data, BigDecimal defaultData) {
        BigDecimal result = defaultData;
        if (data != null) {
            if (data instanceof BigDecimal) {
                return (BigDecimal) data;
            } else if (data instanceof String) {
                return getBigDecimal((String) data, defaultData);
            } else {
                LOGGER.warn("not support type {}", data.getClass().getName());
            }
        }
        return result;
    }

    /**
     * Compare big decimal between.
     *
     * @param v  the v
     * @param v1 the v 1
     * @param v2 the v 2
     * @return true, if successful
     */
    public static final boolean compareBetween(BigDecimal v, BigDecimal v1, BigDecimal v2) {
        return v.compareTo(v1) >= 0 && v.compareTo(v2) <= 0;
    }

    public static final boolean compareBetween(BigDecimal v, long v1, long v2) {
        return v.compareTo(BigDecimal.valueOf(v1)) >= 0 && v.compareTo(BigDecimal.valueOf(v2)) <= 0;
    }

    /**
     * Convert content to list.
     *
     * @param content the content
     * @return the list
     */
    public static final List<String> convertContentToList(String content) {
        if (content == null) {
            return new ArrayList<String>();
        }
        String[] tempAry = content.split(REGEXP_LINE_CRLF);
        return Arrays.stream(tempAry).collect(Collectors.toList());
    }

    /**
     * Convert content to list.
     *
     * @param content   the content
     * @param predicate the predicate
     * @return the list
     */
    public static final List<String> convertContentToList(String content, Predicate<String> predicate) {
        List<String> list = convertContentToList(content);
        if (predicate != null) {
            list = list.stream().filter(predicate).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * Convert lines to dataAuthMap.
     *
     * @param lines the lines
     * @return the dataAuthMap
     */
    public static final Map<String, String> convertLinesToMap(List<String> lines) {
        return lines.stream().map(String::trim).filter(s -> StringUtils.contains(s, CHR_EQUAL) && StringUtils.substringBefore(s, CHR_EQUAL).trim().matches(REGEXP_VAR_NAME)).collect(Collectors.toMap(line -> StringUtils.substringBefore(line, CHR_EQUAL).trim(), line -> StringUtils.substringAfter(line, CHR_EQUAL).trim(), (o1, o2) -> o2));
    }

    /**
     * Description: 获取均值，并转换为文本。供导出程序简化使用。
     *
     * @param arg0
     * @param arg1
     * @return
     */
    public static final String calcAverageStr(BigDecimal arg0, BigDecimal arg1) {
        return arg0.add(arg1).divide(BigDecimal.valueOf(2)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }

    // ------------------------------------------------------------------------


    /**
     * 判断匹配任何一个
     *
     * @param x
     * @param ary
     * @return
     */
    public static boolean anyMatch(String x, String... ary) {
        if (ary == null) {
            return false;
        }
        return Arrays.stream(ary).filter(Objects::nonNull).anyMatch(o -> o.equals(x));
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public static Map<String, Field> getFieldAllToMap(Class c) {
        LinkedHashMap<String, Field> result = new LinkedHashMap<>();
        while (c != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            if (DataUtil.notNullOrEmpty(c.getDeclaredFields())) {
                for (Field field : c.getDeclaredFields()) {
                    if (!field.isAnnotationPresent(JsonIgnore.class)) {
                        result.put(field.getName(), field);
                    }
                }
            }
            c = c.getSuperclass(); //得到父类,然后赋给自己
        }
        return result;
    }


    /**
     * 枚举转换为map
     *
     * @param enumT
     * @param methodNames 可替换方法名
     * @param <T>
     * @return
     */
    public static <T> Map<Object, String> getEnumToMap(Class<T> enumT, String... methodNames) {
        LinkedHashMap<Object, String> enummap = new LinkedHashMap<>();
        if (!enumT.isEnum()) {
            return enummap;
        }
        T[] enums = enumT.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return enummap;
        }
        int count = methodNames.length;
        String codeMathod = "getCode"; //默认接口value方法
        String descMathod = "getDesc";//默认接口description方法
        if (count >= 1 && !"".equals(methodNames[0])) { //扩展方法
            codeMathod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            descMathod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T tobj = enums[i];
            try {
                Object resultcode = getMethodValue(codeMathod, tobj); //获取code值
                if (DataUtil.isNull(resultcode)) {
                    continue;
                }
                Object resultDesc = getMethodValue(descMathod, tobj); //获取desc描述值
                if (DataUtil.isNullOrEmpty((String) resultDesc)) { //如果描述不存在获取属性值
                    resultDesc = tobj;
                }
                enummap.put(resultcode, resultDesc + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return enummap;
    }

    /**
     * 通过code值获取枚举中对应的描述信息
     *
     * @param code
     * @param enumT
     * @return enum description
     */
    public static <T> Object getEnumDescriotionByValue(Object code, Class<T> enumT, String... methodNames) {
        if (!enumT.isEnum()) { //不是枚举则返回""
            return "";
        }
        T[] enums = enumT.getEnumConstants();  //获取枚举的所有枚举属性，似乎这几句也没啥用，一般既然用枚举，就一定会添加枚举属性
        if (enums == null || enums.length <= 0) {
            return "";
        }
        int count = methodNames.length;
        String codeMathod = "getCode";    //默认获取枚举code方法，与接口方法一致
        String descMathod = "getDesc"; //默认获取枚举desc方法
        if (count >= 1 && !"".equals(methodNames[0])) {
            codeMathod = methodNames[0];
        }
        if (count == 2 && !"".equals(methodNames[1])) {
            descMathod = methodNames[1];
        }
        for (int i = 0, len = enums.length; i < len; i++) {
            T t = enums[i];
            try {
                Object resultValue = getMethodValue(codeMathod, t);//获取枚举对象code
                if (resultValue.toString().equals(code + "")) {
                    Object resultDes = getMethodValue(descMathod, t); //存在则返回对应描述
                    return resultDes;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 根据反射，通过方法名称获取方法值，忽略大小写的
     *
     * @param methodName
     * @param obj
     * @param args
     * @return return value
     */
    private static <T> Object getMethodValue(String methodName, T obj, Object... args) {
        Object resut = "";
        try {
            Method[] methods = obj.getClass().getMethods(); //获取方法数组，这里只要共有的方法
            if (methods.length <= 0) {
                return resut;
            }
            Method method = null;
            for (int i = 0, len = methods.length; i < len; i++) {
                if (methods[i].getName().equalsIgnoreCase(methodName)) { //忽略大小写取方法
                    method = methods[i];
                    break;
                }
            }
            if (method == null) {
                return resut;
            }
            resut = method.invoke(obj, args); //方法执行
            if (resut == null) {
                resut = "";
            }
            return resut; //返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resut;
    }

    public static boolean isValidId(Integer i) {
        return i != null && i > 0;
    }

    public static boolean notValidId(Integer i) {
        return i == null || i <= 0;
    }

    public static Double StringToDouble(String s) {
        try {
            return DataUtil.notNullOrEmpty(s) ? Double.parseDouble(s) : 0;
        } catch (NumberFormatException e) {
            throw new CoreException("不合法的数值：" + s);
        }
    }

    /**
     * 获取map中第一个key值
     *
     * @param map 数据源
     * @return
     */
    public static Object getKeyOrNull(Map<Object, Object> map) {
        Object obj = null;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return
     */
    public static Object getFirstOrNull(Map<?, ?> map) {
        Object obj = null;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 字符串重新排序
     * 针对 "456,123,789" =>"123,456,789"
     *
     * @param str
     * @return
     */
    public static String rearrangeStr(String str) {
        return Arrays.asList(str.split(",")).stream().sorted(Comparator.comparing(Objects::toString)).collect(Collectors.joining(","));
    }

    public static double toDouble(String s) {
        if (isNullOrEmpty(s)) {
            return 0;
        }
        return Double.valueOf(s);
    }

    public static String toTrimStr(String s) {
        if (s == null) {
            return StringUtils.EMPTY;
        }
        return s.trim();
    }

    public static String formatUrlParam(Map<String, String> param, boolean isLower) {
        String params;
        try {
            List<Map.Entry<String, String>> items = new ArrayList<>(param.entrySet());
            //对所有传入的参数按照字段名从小到大排序
            //Collections.sort(items); 默认正序
            //可通过实现Comparator接口的compare方法来完成自定义排序
            items.sort(Map.Entry.comparingByKey());
            //构造URL 键值对的形式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : items) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (isLower) {
                        sb.append(key.toLowerCase()).append("=").append(val);
                    } else {
                        sb.append(key).append("=").append(val);
                    }
                    sb.append("&");
                }
            }

            params = sb.toString();
            if (!params.isEmpty()) {
                params = params.substring(0, params.length() - 1);
            }
        } catch (Exception e) {
            return "";
        }
        return params;
    }

    public static BigDecimal toBig(String s) {
        if (DataUtil.isNullOrEmpty(s)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(s);
    }

    public static String toUtf8String(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c >= 0 && c <= 255) {
                stringBuffer.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes(StandardCharsets.UTF_8);
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    stringBuffer.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 字符串是否为N位小数
     *
     * @param str     字符串
     * @param decimal 小数位
     * @return
     */
    public static boolean checkDecimal(String str, Integer decimal) {
        String[] ss = str.split("\\.");
        if (ss.length > 1) {
            String m = ss[1];
            return m.length() <= decimal;
        }
        return true;
    }

    public static Object forOneSet(HashSet<Object> hashSet) {
        if (DataUtil.isNullOrEmpty(hashSet)) {
            return null;
        }
        Iterator<Object> it = hashSet.iterator();
        return it.hasNext() ? it.next() : null;
    }

    /**
     * 字符串中unicode转中文，针对复杂字符串使用
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }

        }
        return outBuffer.toString();
    }

    public String appendStr(String oriStr, String... appendStrs) {
        if (appendStrs == null || appendStrs.length == 0) {
            return oriStr;
        }
        StringBuilder stringBuilder = new StringBuilder(oriStr);
        for (String appendStr : appendStrs) {
            stringBuilder.append(appendStr);
        }

        return stringBuilder.toString();
    }

    // // 删除完文件后删除文件夹
    // // param folderPath 文件夹完整绝对路径
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            //不想删除文佳夹隐藏下面
            // String filePath = folderPath;
            // filePath = filePath.toString();
            // java.io.File myFilePath = new java.io.File(filePath);
            // myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                // delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


}

