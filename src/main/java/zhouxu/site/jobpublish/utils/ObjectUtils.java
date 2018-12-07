package zhouxu.site.jobpublish.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:对象转化工具
 * User: zhouxu
 * Date: 2018-12-07 9:41
 */
public class ObjectUtils {

    /**
     * @Author zhouxu
     * @Description //自动属性注入
     * @Date 2018/12/7 9:46
     * @Param [t, map]
     * @return T
     * @throws
     **/
    public static  <T extends Object> T map2Object(T t, Map<String,Object> map){
        Class<?> clazz = t.getClass();
        if(map != null && map.size() > 0) {
            for(Map.Entry<String, Object> entry : map.entrySet()) {
                String propertyName = entry.getKey();       //属性名
                Object value = entry.getValue();
                String setMethodName = "set"
                        + propertyName.substring(0, 1).toUpperCase()
                        + propertyName.substring(1);
                try{
                    Field field = getClassField(clazz, propertyName);
                    if(field==null)
                        continue;
                    clazz.getMethod(setMethodName, field.getType()).invoke(t, value);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    /**
     * @Author zhouxu
     * @Description //自动属性注入
     * @Date 2018/12/7 10:04
     * @Param [clazz, map]
     * @return T
     * @throws
     **/
    public static  <T> T map2Object(Class<T> clazz, Map<String,Object> map) {
        T t =null;
        try {
            t=(T)clazz.newInstance();
            map2Object(t,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    /**
     * @Author zhouxu
     * @Description 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
     * @Date 2018/12/7 10:01
     * @Param [clazz, fieldName]
     * @return java.lang.reflect.Field
     * @throws
     **/
    private static Field getClassField(Class<?> clazz, String fieldName) {
        if( Object.class.getName().equals(clazz.getName())) {
            return null;
        }
        Field []declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null) {// 简单的递归一下
            return getClassField(superClass, fieldName);
        }
        return null;
    }
}
