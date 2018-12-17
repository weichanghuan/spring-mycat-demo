package com.base.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

/**
 * <pre>
 * Bean工具类
 *
 * 特别说明：
 * 1. 因为采用java.beans中的标准类库，针对如下场景的属性将无法正确访问读写方法
 * 		a) boolean类型属性 isXXX() 错写成 getXXX()
 * 		b) Boolean类型属性 getXXX() 错写成 isXXX()
 *
 * 2. 请使用AdjustedPropDesc访问即可
 * </pre>
 */
public final class BeanUtil {
    /**
     * 属性描述解析缓存
     */
    private static final Map<Class<?>, WeakReference<Map<String, PropertyDescriptor>>> CLASS_PROP_DESC_CACHE = Collections
            .synchronizedMap(new WeakHashMap<Class<?>, WeakReference<Map<String, PropertyDescriptor>>>());

    /**
     * 修正后的属性描述解析缓存 (修正Boolean的is/get问题)
     */
    private static final Map<Class<?>, WeakReference<Map<String, PropertyDesc>>> ADJ_CLASS_PROP_DESC_CACHE = Collections
            .synchronizedMap(new WeakHashMap<Class<?>, WeakReference<Map<String, PropertyDesc>>>());

    /**
     * 属性定义解析缓存
     */
    private static final Map<Class<?>, WeakReference<Map<String, Field>>> CLASS_FIELD_CACHE = Collections
            .synchronizedMap(new WeakHashMap<Class<?>, WeakReference<Map<String, Field>>>());

    /**
     * 将Bean转换为Map，排除指定属性集
     *
     * @param obj
     * @param excludedProps 为null或空集合则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMap(Object obj, Collection<String> excludedProps) {
        String[] props = (excludedProps != null ? excludedProps.toArray(new String[excludedProps.size()]) : null);
        return asMap(AsMapModes.STANDARD, obj, props);
    }

    /**
     * 将Bean转换为Map，排除指定属性集
     *
     * @param obj
     * @param excludedProps 为null或空数组则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMap(Object obj, String... excludedProps) {
        return asMap(AsMapModes.STANDARD, obj, excludedProps);
    }

    /**
     * 将Bean转换为Map，排除指定属性集
     *
     * @param mode          模式: 1-忽略value为null的属性，其他-保留所有属性
     * @param obj
     * @param excludedProps 为null或空集合则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMap(int mode, Object obj, Collection<String> excludedProps) {
        String[] props = (excludedProps != null ? excludedProps.toArray(new String[excludedProps.size()]) : null);
        return asMap(mode, obj, props);
    }

    /**
     * 将Bean转换为Map，排除指定属性集
     *
     * @param mode          模式: 1-忽略value为null的属性，其他-保留所有属性
     * @param obj
     * @param excludedProps 为null或空数组则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMap(int mode, Object obj, String... excludedProps) {
        if (obj == null) {
            return null;
        }

        // result
        Map<String, Object> propMap = new LinkedHashMap<String, Object>();

        Map<String, PropertyDesc> propDescMap = getAdjustedPropDescMap(obj.getClass());
        for (Map.Entry<String, PropertyDesc> propDescEntry : propDescMap.entrySet()) {
            if (ArrayUtil.contains(excludedProps, propDescEntry.getKey())) {
                continue; // excluded props
            }

            try {
                Method readMethod = propDescEntry.getValue().getReadMethod();
                if (readMethod != null) {
                    Object val = readMethod.invoke(obj);

                    boolean doPut = true;
                    switch (mode) {
                        case AsMapModes.PROP_VALUE_NOT_NULL:
                            doPut = (val != null);
                            break;
                    }

                    if (doPut) {
                        propMap.put(propDescEntry.getKey(), val);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Read property value error, propName=" + propDescEntry.getKey(), e);
            }
        }

        return propMap;
    }

    /**
     * 将Bean转换为Map，包含指定属性集
     *
     * @param obj
     * @param includedProps 为null或空集合则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMapWithProps(Object obj, Collection<String> includedProps) {
        String[] props = (includedProps != null ? includedProps.toArray(new String[includedProps.size()]) : null);
        return asMapWithProps(AsMapModes.STANDARD, obj, props);
    }

    /**
     * 将Bean转换为Map，包含指定属性集
     *
     * @param obj
     * @param includedProps 为null或空数组则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMapWithProps(Object obj, String... includedProps) {
        return asMapWithProps(AsMapModes.STANDARD, obj, includedProps);
    }

    /**
     * 将Bean转换为Map，包含指定属性集
     *
     * @param mode          模式: 1-忽略value为null的属性，其他-保留所有属性
     * @param obj
     * @param includedProps 为null或空集合则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMapWithProps(int mode, Object obj, Collection<String> includedProps) {
        String[] props = (includedProps != null ? includedProps.toArray(new String[includedProps.size()]) : null);
        return asMapWithProps(mode, obj, props);
    }

    /**
     * 将Bean转换为Map，包含指定属性集
     *
     * @param mode          模式: 1-忽略value为null的属性，其他-保留所有属性
     * @param obj
     * @param includedProps 为null或空数组则返回所有属性值
     * @return
     */
    public static Map<String, Object> asMapWithProps(int mode, Object obj, String... includedProps) {
        if (obj == null) {
            return null;
        }

        // result
        Map<String, Object> propMap = new LinkedHashMap<String, Object>();
        if (ArrayUtil.isEmpty(includedProps)) {
            return propMap; // no props specified
        }

        Map<String, PropertyDesc> propDescMap = getAdjustedPropDescMap(obj.getClass());
        for (String prop : includedProps) {
            PropertyDesc propDesc = propDescMap.get(prop);
            if (propDesc == null) {
                // not included props
                continue;
            }

            try {
                Method readMethod = propDesc.getReadMethod();
                if (readMethod != null) {
                    Object val = readMethod.invoke(obj);

                    boolean doPut = true;
                    switch (mode) {
                        case AsMapModes.PROP_VALUE_NOT_NULL:
                            doPut = (val != null);
                            break;
                    }

                    if (doPut) {
                        propMap.put(prop, val);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Read property value error, propName=" + prop, e);
            }
        }

        return propMap;
    }

    /**
     * 将Bean转换为Map，包含标注指定注解的属性集
     *
     * @param obj
     * @param annos
     * @return
     */
    public static Map<String, Object> asMapWithAnnos(Object obj, Class<? extends Annotation>... annos) {
        return asMapWithAnnos(AsMapModes.STANDARD, obj, annos);
    }

    /**
     * 将Bean转换为Map，包含指定属性集
     *
     * @param mode  模式: 1-忽略value为null的属性，其他-保留所有属性
     * @param obj
     * @param annos
     * @return
     */
    public static Map<String, Object> asMapWithAnnos(int mode, Object obj, Class<? extends Annotation>... annos) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> propWithAnnoMap = new LinkedHashMap<String, Object>();
        if (ArrayUtil.isEmpty(annos)) {
            return propWithAnnoMap;
        }

        nextField:
        for (Map.Entry<String, Field> entry : getFieldMap(obj.getClass()).entrySet()) {
            for (Class<? extends Annotation> anno : annos) {
                Field field = entry.getValue();
                if (field.isAnnotationPresent(anno)) {
                    Object val = getFieldValue(obj, field);

                    boolean doPut = true;
                    switch (mode) {
                        case AsMapModes.PROP_VALUE_NOT_NULL:
                            doPut = (val != null);
                            break;
                    }

                    if (doPut) {
                        propWithAnnoMap.put(field.getName(), val);
                    }

                    continue nextField;
                }
            }
        }

        return propWithAnnoMap;
    }

    /**
     * 将source对象的属性值拷贝至target对象的同名属性中
     *
     * @param source
     * @param target
     * @param ignoredProps
     * @return
     */
    public static <T> T copyProperties(Object source, T target, String... ignoredProps) {
        return copyProperties(CopyPropModes.STANDARD, source, target, true, ignoredProps);
    }

    /**
     * 将source对象的属性值拷贝至target对象的同名属性中
     *
     * @param source
     * @param target
     * @param excluded  是否排除或包含propNames，true代表排除
     * @param propNames 为null或空则复制所有属性
     * @return
     */
    public static <T> T copyProperties(Object source, T target, boolean excluded, String... propNames) {
        return copyProperties(CopyPropModes.STANDARD, source, target, excluded, propNames);
    }

    /**
     * 将source对象的属性值拷贝至target对象的同名属性中
     *
     * @param mode         模式: 1-source对象属性值不为null，2-target对象属性值未设置，其他-所有均拷贝
     * @param source
     * @param target
     * @param ignoredProps
     * @return
     * @see BeanUtil.CopyPropModes
     */
    public static <T> T copyProperties(int mode, Object source, T target, String... ignoredProps) {
        return copyProperties(mode, source, target, true, ignoredProps);
    }

    /**
     * 将source对象的属性值拷贝至target对象的同名属性中
     *
     * @param mode      模式: 1-source对象属性值不为null，2-target对象属性值未设置，其他-所有均拷贝
     * @param source
     * @param target
     * @param excluded  是否排除或包含propNames，true代表排除
     * @param propNames 为null或空则复制所有属性
     * @return
     * @see BeanUtil.CopyPropModes
     */
    public static <T> T copyProperties(int mode, Object source, T target, boolean excluded, String... propNames) {
        if (source == null || target == null) {
            return target;
        }

        boolean propNamesNoEmpty = ArrayUtil.isNotEmpty(propNames);

        Map<String, PropertyDesc> srcPropDescMap = getAdjustedPropDescMap(source.getClass());
        Map<String, PropertyDesc> targetPropDescMap = getAdjustedPropDescMap(target.getClass());
        for (Map.Entry<String, PropertyDesc> srcPropDescEntry : srcPropDescMap.entrySet()) {
            if (propNamesNoEmpty) {
                if (excluded && ArrayUtil.contains(propNames, srcPropDescEntry.getKey())) {
                    // 排除范围内
                    continue;

                } else if (excluded == false && ArrayUtil.contains(propNames, srcPropDescEntry.getKey()) == false) {
                    // 未在包含范围内
                    continue;
                }
            }

            Method readMethod = srcPropDescEntry.getValue().getReadMethod();
            if (readMethod == null) {
                continue;
            }

            PropertyDesc targetPropDesc = targetPropDescMap.get(srcPropDescEntry.getKey());
            if (targetPropDesc == null || targetPropDesc.getWriteMethod() == null) {
                continue;
            }

            try {
                boolean doSet = true;
                Object srcValue = readMethod.invoke(source);

                switch (mode) {
                    case CopyPropModes.SOURCE_VALUE_NOT_NULL:
                        doSet = (srcValue != null);
                        break;

                    case CopyPropModes.TARGET_VALUE_NOT_SET:
                        Method targetReadMethod = targetPropDesc.getReadMethod();
                        if (targetReadMethod != null) {
                            Object targetValue = targetReadMethod.invoke(target);
                            doSet = (targetValue == null
                                    || targetValue.equals(ObjectUtil.defaultValue(targetPropDesc.getPropertyType())));
                        }
                        break;
                }

                if (doSet) {
                    targetPropDesc.getWriteMethod().invoke(target, srcValue);
                }
            } catch (Exception e) {
                throw new RuntimeException("Write property value error, propName=" + srcPropDescEntry.getKey(), e);
            }
        }

        return target;
    }

    /**
     * 将Map的属性值拷贝至target对象的同名属性中
     *
     * @param source
     * @param target
     * @param ignoredProps
     * @return
     */
    public static <T> T copyProperties(Map<String, Object> source, T target, String... ignoredProps) {
        return copyProperties(CopyPropModes.STANDARD, source, target, ignoredProps);
    }

    /**
     * 将Map的属性值拷贝至target对象的同名属性中
     *
     * @param source
     * @param target
     * @param excluded  是否排除或包含propNames，true代表排除
     * @param propNames 为null或空则复制所有属性
     * @return
     */
    public static <T> T copyProperties(Map<String, Object> source, T target, boolean excluded, String... propNames) {
        return copyProperties(CopyPropModes.STANDARD, source, target, excluded, propNames);
    }

    /**
     * 将Map的属性值拷贝至target对象的同名属性中
     *
     * @param mode         模式: 1-source对象属性值不为null，2-target对象属性值未设置，其他-所有均拷贝
     * @param source
     * @param target
     * @param ignoredProps
     * @return
     * @see BeanUtil.CopyPropModes
     */
    public static <T> T copyProperties(int mode, Map<String, Object> source, T target, String... ignoredProps) {
        return copyProperties(mode, source, target, true, ignoredProps);
    }

    /**
     * 将Map的属性值拷贝至target对象的同名属性中
     *
     * @param mode      模式: 1-source对象属性值不为null，2-target对象属性值未设置，其他-所有均拷贝
     * @param source
     * @param target
     * @param excluded  是否排除或包含propNames，true代表排除
     * @param propNames 为null或空则复制所有属性
     * @return
     * @see BeanUtil.CopyPropModes
     */
    public static <T> T copyProperties(int mode, Map<String, Object> source, T target, boolean excluded,
                                       String... propNames) {
        if (MapUtil.isEmpty(source) || target == null) {
            return target;
        }

        boolean propNamesNoEmpty = ArrayUtil.isNotEmpty(propNames);

        Map<String, PropertyDesc> propDescMap = getAdjustedPropDescMap(target.getClass());
        for (Map.Entry<String, Object> dataEntry : source.entrySet()) {
            if (propNamesNoEmpty) {
                if (excluded && ArrayUtil.contains(propNames, dataEntry.getKey())) {
                    // 排除范围内
                    continue;

                } else if (excluded == false && ArrayUtil.contains(propNames, dataEntry.getKey()) == false) {
                    // 未在包含范围内
                    continue;
                }
            }

            PropertyDesc propDesc = propDescMap.get(dataEntry.getKey());
            if (propDesc == null || propDesc.getWriteMethod() == null) {
                continue;
            }

            try {
                boolean doSet = true;
                Object srcValue = dataEntry.getValue();

                switch (mode) {
                    case CopyPropModes.SOURCE_VALUE_NOT_NULL:
                        doSet = (srcValue != null);
                        break;

                    case CopyPropModes.TARGET_VALUE_NOT_SET:
                        Method targetReadMethod = propDesc.getReadMethod();
                        if (targetReadMethod != null) {
                            Object targetValue = targetReadMethod.invoke(target);
                            doSet = (targetValue == null
                                    || targetValue.equals(ObjectUtil.defaultValue(propDesc.getPropertyType())));
                        }

                        break;
                }

                if (doSet) {
                    propDesc.getWriteMethod().invoke(target, srcValue);
                }
            } catch (Exception e) {
                throw new RuntimeException("Write property value error, propName=" + dataEntry.getKey(), e);
            }
        }

        return target;
    }

    /**
     * 获得class的propName对应的属性Get方法
     *
     * @param clazz
     * @param propName
     * @return
     */
    public static Method getReadMethod(Class<?> clazz, String propName) {
        if (clazz == null || propName == null) {
            return null;
        }

        PropertyDesc propDesc = getAdjustedPropDescMap(clazz).get(propName);
        return (propDesc != null ? propDesc.getReadMethod() : null);
    }

    /**
     * 获得class的propName对应的属性Set方法
     *
     * @param clazz
     * @param propName
     * @return
     */
    public static Method getWriteMethod(Class<?> clazz, String propName) {
        if (clazz == null || propName == null) {
            return null;
        }

        PropertyDesc propDesc = getAdjustedPropDescMap(clazz).get(propName);
        return (propDesc != null ? propDesc.getWriteMethod() : null);
    }

    /**
     * 获取属性描述集(class属性默认忽略)
     *
     * @param clazz
     * @param ignoredProps 忽略的属性名
     * @return
     */
    public static Map<String, PropertyDescriptor> getPropertyDescriptors(Class<?> clazz, String... ignoredProps) {
        if (clazz == null) {
            return null;
        }

        Map<String, PropertyDescriptor> propDescMap = new LinkedHashMap<String, PropertyDescriptor>(
                getPropDescMap(clazz));
        if (ArrayUtil.isNotEmpty(ignoredProps)) {
            for (String ignoredProp : ignoredProps) {
                propDescMap.remove(ignoredProp);
            }
        }

        return propDescMap;
    }

    /**
     * 获取属性描述(class属性默认忽略)
     *
     * @param clazz
     * @param propName
     * @return
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propName) {
        if (clazz == null || propName == null) {
            return null;
        }

        return getPropDescMap(clazz).get(propName);
    }

    /**
     * 获取修正后的属性描述集(class属性默认忽略)
     *
     * @param clazz
     * @param ignoredProps 忽略的属性名
     * @return
     */
    public static Map<String, PropertyDesc> getAdjustedPropertyDescs(Class<?> clazz, String... ignoredProps) {
        if (clazz == null) {
            return null;
        }

        Map<String, PropertyDesc> propDescMap = new LinkedHashMap<String, PropertyDesc>(getAdjustedPropDescMap(clazz));
        if (ArrayUtil.isNotEmpty(ignoredProps)) {
            for (String ignoredProp : ignoredProps) {
                propDescMap.remove(ignoredProp);
            }
        }

        return propDescMap;
    }

    /**
     * 获取修正后的属性描述(class属性默认忽略)
     *
     * @param clazz
     * @param propName
     * @return
     */
    public static PropertyDesc getAdjustedPropertyDesc(Class<?> clazz, String propName) {
        if (clazz == null || propName == null) {
            return null;
        }

        return getAdjustedPropDescMap(clazz).get(propName);
    }

    /**
     * 获取属性名称集
     *
     * @param clazz
     * @param ignoredProps 忽略的字段名
     * @return
     */
    public static List<String> getPropertyNames(Class<?> clazz, String... ignoredProps) {
        if (clazz == null) {
            return null;
        }

        Map<String, PropertyDesc> propDescMap = getAdjustedPropDescMap(clazz);

        List<String> propNames = new ArrayList<String>(propDescMap.size());
        for (Map.Entry<String, PropertyDesc> entry : propDescMap.entrySet()) {
            if (ArrayUtil.contains(ignoredProps, entry.getKey()) == false) {
                propNames.add(entry.getKey());
            }
        }

        return propNames;
    }

    /**
     * 获取两个对象指定属性中属性值不同的属性名集合，如果两个对象任意一个为null，返回空集合
     *
     * @param obj1
     * @param obj2
     * @param propNamesToCompare 为null或空数组，则比较两对象所有同名属性；如果指定属性名在两个对象的任意一个中不存在，则被忽略
     * @return
     */
    public static List<String> getPropertyNamesWithDiffValue(Object obj1, Object obj2, String... propNamesToCompare) {
        return new ArrayList<String>(getPropertyValuesWithDiffValue(obj1, obj2, false, propNamesToCompare).keySet());
    }

    /**
     * 获取两个对象指定属性中属性值不同的属性名集合，如果两个对象任意一个为null，返回空集合
     *
     * @param obj1
     * @param obj2
     * @param excluded  排除还是包含propNames，true代表排除
     * @param propNames 为null或空数组，则比较两对象所有同名属性；如果指定属性名在两个对象的任意一个中不存在，则被忽略
     * @return
     */
    public static List<String> getPropertyNamesWithDiffValue(Object obj1, Object obj2, boolean excluded,
                                                             String... propNames) {
        return new ArrayList<String>(getPropertyValuesWithDiffValue(obj1, obj2, excluded, propNames).keySet());
    }

    /**
     * 获取两个对象指定属性中属性值不同的属性值集合，如果两个对象任意一个为null，返回空集合
     *
     * @param obj1
     * @param obj2
     * @param propNamesToCompare 为null或空数组，则比较两对象所有同名属性；如果指定属性名在两个对象的任意一个中不存在，则被忽略
     * @return
     */
    public static Map<String, Object[]> getPropertyValuesWithDiffValue(Object obj1, Object obj2,
                                                                       String... propNamesToCompare) {
        return getPropertyValuesWithDiffValue(obj1, obj2, false, propNamesToCompare);
    }

    /**
     * 获取两个对象指定属性中属性值不同的属性值集合，如果两个对象任意一个为null，返回空集合
     *
     * @param obj1
     * @param obj2
     * @param excluded  排除还是包含propNames，true代表排除
     * @param propNames 为null或空数组，则比较两对象所有同名属性；如果指定属性名在两个对象的任意一个中不存在，则被忽略
     * @return
     */
    public static Map<String, Object[]> getPropertyValuesWithDiffValue(Object obj1, Object obj2, boolean excluded,
                                                                       String... propNames) {
        Map<String, Object[]> diffProps = new LinkedHashMap<String, Object[]>();
        if (obj1 == null || obj2 == null || obj1 == obj2) {
            return diffProps;
        }

        // 打造对象1的属性名与属性表述信息的映射关系
        Map<String, PropertyDescriptor> propDefMap1 = BeanUtil.getPropertyDescriptors(obj1.getClass());

        // 如果对象2与对象1的类型相同，则使用相同的映射关系
        Map<String, PropertyDescriptor> propDefMap2 = propDefMap1;
        if (obj1.getClass() != obj2.getClass()) {
            // 不同则解析
            propDefMap2 = BeanUtil.getPropertyDescriptors(obj2.getClass());
        }

        Set<String> propToComp;
        if (ArrayUtil.isEmpty(propNames)) {
            // 如果未指定属性名，则比较所有属性(选取任何一个对象的属性映射作为参考)
            propToComp = propDefMap1.keySet();

        } else if (excluded) {
            // 指定属性名且为忽略，则保留未忽略属性
            propToComp = new LinkedHashSet<String>(propDefMap1.keySet());
            propToComp.removeAll(ArrayUtil.asSet(propNames));

        } else {
            // 指定属性名且为包含
            propToComp = ArrayUtil.asSet(propNames);
        }

        // 遍历所有属性，比较属性值
        for (String propName : propToComp) {
            PropertyDescriptor pd1 = propDefMap1.get(propName);
            PropertyDescriptor pd2 = propDefMap2.get(propName);

            if (pd1 == null || pd1.getReadMethod() == null || pd2 == null || pd2.getReadMethod() == null) {
                continue;
            }

            try {
                // 如果不一致则记录
                Object val1 = pd1.getReadMethod().invoke(obj1);
                Object val2 = pd2.getReadMethod().invoke(obj2);
                if (ObjectUtil.isNotEqual(val1, val2)) {
                    diffProps.put(propName, new Object[]{val1, val2});
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return diffProps;
    }

    /**
     * 获取Class的属性描述集
     *
     * @param clazz
     * @return
     */
    private static Map<String, PropertyDescriptor> getPropDescMap(Class<?> clazz) {
        Map<String, PropertyDescriptor> propDescMap = getPropDescMapFromCache(clazz);
        if (propDescMap != null) {
            return propDescMap;
        }

        propDescMap = new TreeMap<String, PropertyDescriptor>();
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                if ("class".equals(pd.getName()) == false) {
                    propDescMap.put(pd.getName(), pd);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Analyse property desc error, class=" + clazz, e);
        }
        CLASS_PROP_DESC_CACHE.put(clazz, new WeakReference<Map<String, PropertyDescriptor>>(propDescMap));

        return propDescMap;
    }

    /**
     * 获取Class的修正后的属性描述集
     *
     * @param clazz
     * @return
     */
    private static Map<String, PropertyDesc> getAdjustedPropDescMap(Class<?> clazz) {
        Map<String, PropertyDesc> propDescMap = getAdjustedPropDescMapFromCache(clazz);
        if (propDescMap != null) {
            return propDescMap;
        }

        propDescMap = new TreeMap<String, PropertyDesc>();
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                if ("class".equals(pd.getName()) == false) {
                    propDescMap.put(pd.getName(), buildPropDesc(clazz, pd));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Analyse adjusted property desc error, class=" + clazz, e);
        }
        ADJ_CLASS_PROP_DESC_CACHE.put(clazz, new WeakReference<Map<String, PropertyDesc>>(propDescMap));

        return propDescMap;
    }

    /**
     * 构建属性描述
     *
     * @param clazz
     * @param pd
     * @return
     */
    private static PropertyDesc buildPropDesc(Class<?> clazz, PropertyDescriptor pd) {
        PropertyDesc propDesc = new PropertyDesc();
        propDesc.propDesc = pd;

        Class<?> propType = pd.getPropertyType();
        Method writeMethod = pd.getWriteMethod();
        Method readMethod = pd.getReadMethod();

        // 尝试补充缺少的读写方法
        if (propType == Boolean.TYPE && writeMethod == null && readMethod != null) {
            // 满足下列条件，导致无法识别write方法 - 错误的read方法名和返回值类型 (get写成is，返回值为boolean)
            // 属性：Boolean boolProp
            // 读方法：boolean isBoolProp()
            // 写方法：setBoolProp(Boolean)
            Method adjWriteMethod = BeanUtil.getMethod(clazz, "set" + readMethod.getName().substring(2),
                    new Class[]{Boolean.class});
            if (adjWriteMethod != null) {
                propDesc.writeMethod = adjWriteMethod;
                propDesc.propertyType = Boolean.class;
                propDesc.adjusted = true;
            }
        } else if (propType == Boolean.class && readMethod == null && writeMethod != null) {
            // 满足下列条件，导致无法识别read方法 - 错误的read方法名 (get写成is，返回值为Boolean)
            // 属性： Boolean boolProp
            // 读方法： Boolean isBoolProp()
            // 写方法： setBoolProp(Boolean)
            Method adjReadMethod = BeanUtil.getMethod(clazz, "is" + writeMethod.getName().substring(3));
            if (adjReadMethod != null && adjReadMethod.getReturnType() == Boolean.class) {
                propDesc.readMethod = adjReadMethod;
                propDesc.propertyType = Boolean.class;
                propDesc.adjusted = true;
            }
        }

        return propDesc;
    }

    /**
     * 从缓存中获取属性描述
     *
     * @param clazz
     * @return
     */
    private static Map<String, PropertyDescriptor> getPropDescMapFromCache(Class<?> clazz) {
        WeakReference<Map<String, PropertyDescriptor>> propDescRef = CLASS_PROP_DESC_CACHE.get(clazz);

        return (propDescRef != null ? propDescRef.get() : null);
    }

    /**
     * 从缓存中获取修正后的属性描述
     *
     * @param clazz
     * @return
     */
    private static Map<String, PropertyDesc> getAdjustedPropDescMapFromCache(Class<?> clazz) {
        WeakReference<Map<String, PropertyDesc>> propDescRef = ADJ_CLASS_PROP_DESC_CACHE.get(clazz);

        return (propDescRef != null ? propDescRef.get() : null);
    }

    /**
     * 获取字段定义集，保留子类中与父类同名的字段定义
     *
     * @param clazz
     * @param ignoredFields 忽略的字段名
     * @return
     */
    public static Map<String, Field> getFields(Class<?> clazz, String... ignoredFields) {
        if (clazz == null) {
            return null;
        }

        Map<String, Field> fieldMap = new LinkedHashMap<String, Field>(getFieldMap(clazz));
        if (ArrayUtil.isNotEmpty(ignoredFields)) {
            for (String ignoredField : ignoredFields) {
                fieldMap.remove(ignoredField);
            }
        }

        return fieldMap;
    }

    /**
     * 获取包含指定元注释的字段定义集，保留子类中与父类同名的字段定义
     *
     * @param clazz
     * @param annos
     * @return
     */
    public static Map<String, Field> getFieldsWithAnnos(Class<?> clazz, Class<? extends Annotation>... annos) {
        if (clazz == null) {
            return null;
        }

        Map<String, Field> fieldWithAnnoMap = new LinkedHashMap<String, Field>();
        if (ArrayUtil.isEmpty(annos)) {
            return fieldWithAnnoMap;
        }

        nextField:
        for (Map.Entry<String, Field> entry : getFieldMap(clazz).entrySet()) {
            for (Class<? extends Annotation> anno : annos) {
                Field field = entry.getValue();
                if (field.isAnnotationPresent(anno)) {
                    fieldWithAnnoMap.put(entry.getKey(), entry.getValue());
                    continue nextField;
                }
            }
        }

        return fieldWithAnnoMap;
    }

    /**
     * 获取Class的字段定义集
     *
     * @param clazz
     * @return
     */
    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = getFieldMapFromCache(clazz);
        if (fieldMap != null) {
            return fieldMap;
        }

        fieldMap = new TreeMap<String, Field>();

        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (fieldMap.containsKey(field.getName()) == false) {
                    fieldMap.put(field.getName(), field);
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);

        CLASS_FIELD_CACHE.put(clazz, new WeakReference<Map<String, Field>>(fieldMap));

        return fieldMap;
    }

    /**
     * 获取obj的字段定义
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getField(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }

        Class<?> clazz = null;
        if (obj instanceof Class) {
            clazz = ((Class<?>) obj);
        } else {
            clazz = obj.getClass();
        }

        Field f = null;
        try {
            f = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
        }

        if (f == null) {
            return getField(clazz.getSuperclass(), fieldName);
        }

        return f;
    }

    /**
     * 从缓存中获取字段定义集
     *
     * @param clazz
     * @return
     */
    private static Map<String, Field> getFieldMapFromCache(Class<?> clazz) {
        WeakReference<Map<String, Field>> fieldRef = CLASS_FIELD_CACHE.get(clazz);

        return (fieldRef != null ? fieldRef.get() : null);
    }

    /**
     * 获取指定方法对象
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Method getMethod(Object obj, String methodName) {
        return getMethod(obj, methodName, null);
    }

    /**
     * 获取指定Method
     *
     * @param obj
     * @param methodName
     * @param paraTypes
     * @return
     */
    public static Method getMethod(Object obj, String methodName, Class<?>[] paraTypes) {
        if (obj == null || methodName == null) {
            return null;
        }

        Class<?> clazz = null;
        if (obj instanceof Class) {
            clazz = ((Class<?>) obj);
        } else {
            clazz = obj.getClass();
        }

        Method m = null;
        if (paraTypes != null) {
            try {
                m = clazz.getDeclaredMethod(methodName, paraTypes);
            } catch (Exception e) {
            }
        } else {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    m = method; // first method by name
                    break;
                }
            }
        }

        if (m == null) {
            return getMethod(clazz.getSuperclass(), methodName, paraTypes);
        }

        return m;
    }

    /**
     * 设置指定属性值
     *
     * @param obj
     * @param propName
     * @param propValue
     */
    public static void setPropValue(Object obj, String propName, Object propValue) {
        PropertyDesc propDesc = getAdjustedPropDescMap(obj.getClass()).get(propName);
        if (propDesc != null) {
            try {
                Method writeMethod = propDesc.getWriteMethod();
                if (writeMethod != null) {
                    writeMethod.invoke(obj, propValue);
                }
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("Set property value error, name=%s, value=%s, obj=%s", propName, propValue, obj),
                        e);
            }
        }
    }

    /**
     * 获取指定属性值
     *
     * @param obj
     * @param propName
     * @return
     */
    public static Object getPropValue(Object obj, String propName) {
        PropertyDesc propDesc = getAdjustedPropDescMap(obj.getClass()).get(propName);
        if (propDesc != null) {
            try {
                Method readMethod = propDesc.getReadMethod();
                if (readMethod != null) {
                    return readMethod.invoke(obj);
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Get property value error, name=%s, obj=%s", propName, obj),
                        e);
            }
        }

        return null;
    }

    /**
     * 获取指定属性值，捕获所有异常
     *
     * @param obj
     * @param propName
     * @return
     */
    public static Object getPropValueQuietly(Object obj, String propName) {
        try {
            return getPropValue(obj, propName);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 根据方法和对象获取值
     *
     * @param method
     * @param o
     * @return
     */
    public static Object getValue(Method method, Object o) {
        try {
            return method.invoke(o);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行obj对象的指定方法，当调用抛出异常时返回null
     *
     * @param obj
     * @param methodName
     * @param args
     * @return
     */
    public static <T> T invokeMethodQuietly(Object obj, String methodName, Object... args) {
        try {
            return invokeMethod(obj, methodName, args);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 执行obj对象的指定方法，当调用抛出异常时返回null
     *
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static <T> T invokeMethodQuietly(Object obj, Method method, Object... args) {
        try {
            return invokeMethod(obj, method, args);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 执行obj对象的指定方法，调用可能会抛出运行时异常，如方法不存在等
     *
     * @param obj
     * @param methodName
     * @param args
     * @return
     */
    public static <T> T invokeMethod(Object obj, String methodName, Object... args) {
        if (obj == null || methodName == null) {
            return null;
        }

        Method method = getMethod(obj, methodName);
        if (method == null) {
            throw new RuntimeException("No such method, obj=" + obj + ", method=" + methodName);
        }

        return invokeMethod(obj, method, args);
    }

    /**
     * 执行obj对象的指定方法
     *
     * @param obj
     * @param method
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object obj, Method method, Object... args) {
        if (obj == null || method == null) {
            return null;
        }

        if (method.isAccessible() == false) {
            method.setAccessible(true);
        }

        try {
            return (T) method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());

        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());

        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 获取指定属性值，当调用抛出异常时返回null
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static <T> T getFieldValueQuietly(Object obj, String fieldName) {
        try {
            return getFieldValue(obj, fieldName);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取指定属性值，当调用抛出异常时返回null
     *
     * @param obj
     * @param field
     * @return
     */
    public static <T> T getFieldValueQuietly(Object obj, Field field) {
        try {
            return getFieldValue(obj, field);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取指定属性值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static <T> T getFieldValue(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }

        Field f = getField(obj, fieldName);
        if (f == null) {
            throw new RuntimeException("No such field, obj=" + obj + ", fieldName=" + fieldName);
        }

        return getFieldValue(obj, f);
    }

    /**
     * 获取指定属性值
     *
     * @param obj
     * @param field
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object obj, Field field) {
        if (obj == null || field == null) {
            return null;
        }

        if (field.isAccessible() == false) {
            field.setAccessible(true);
        }

        try {
            return (T) field.get(obj);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    private BeanUtil() {
    }

    /**
     * 属性拷贝模式类
     */
    public static final class CopyPropModes {
        /**
         * 拷贝模式 - 标准全部拷贝
         */
        public static final int STANDARD = 0;

        /**
         * 拷贝模式 - source对象属性值不为null
         */
        public static final int SOURCE_VALUE_NOT_NULL = 1;

        /**
         * 拷贝模式 - target对象属性值未设置(为对应类型的默认值)
         */
        public static final int TARGET_VALUE_NOT_SET = 2;

        private CopyPropModes() {
        }
    }

    /**
     * 对象转MAP模式类
     */
    public static final class AsMapModes {
        /**
         * 模式 - 全部属性
         */
        public static final int STANDARD = 0;

        /**
         * 模式 - 属性值不为null
         */
        public static final int PROP_VALUE_NOT_NULL = 1;

        private AsMapModes() {
        }
    }

    /**
     * 属性描述
     */
    public static final class PropertyDesc {
        /**
         * JDK标准属性描述器
         */
        private PropertyDescriptor propDesc;

        /**
         * 修正后的读取方法
         */
        private Method readMethod;

        /**
         * 修正后的写方法
         */
        private Method writeMethod;

        /**
         * 属性类型
         */
        private Class<?> propertyType;

        /**
         * 是否已调整
         */
        private boolean adjusted;

        public PropertyDescriptor getPropDesc() {
            return propDesc;
        }

        public String getName() {
            if (propDesc != null) {
                return propDesc.getName();
            }

            return null;
        }

        public Method getReadMethod() {
            if (readMethod != null) {
                return readMethod;
            }

            if (propDesc != null) {
                return propDesc.getReadMethod();
            }

            return null;
        }

        public Method getWriteMethod() {
            if (writeMethod != null) {
                return writeMethod;
            }

            if (propDesc != null) {
                return propDesc.getWriteMethod();
            }

            return null;
        }

        public Class<?> getPropertyType() {
            if (propertyType != null) {
                return propertyType;
            }

            if (propDesc != null) {
                return propDesc.getPropertyType();
            }

            return null;
        }

        public boolean isAdjusted() {
            return adjusted;
        }
    }

    static class Bean {
        private Boolean a;
        private Boolean b;
        private boolean c;
        private boolean d;

        public boolean isA() {
            if (a == null) {
                return false;
            }
            return a;
        }

        public void setA(Boolean a) {
            this.a = a;
        }

        public Boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public boolean getC() {
            return c;
        }

        public void setC(boolean c) {
            this.c = c;
        }

        public boolean isD() {
            return d;
        }

        public void setD(boolean d) {
            this.d = d;
        }
    }
}
