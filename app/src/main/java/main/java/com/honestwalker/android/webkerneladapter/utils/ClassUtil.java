package main.java.com.honestwalker.android.webkerneladapter.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanzhe on 17-7-27.
 */

public class ClassUtil {

    public static Object callMethod(Object object, String methodName, Object... params)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        List<Class> paramTypeList = new ArrayList<>();
//        if(params != null && params.length > 0) {
//            for (Object param : params) {
//                paramTypeList.add(param.getClass());
//            }
//        }
//        Class[] paramTypes = new Class[paramTypeList.size()];
//        paramTypeList.toArray(paramTypes);

        Method method = getMethod(object.getClass(), methodName, params);

        if(method != null) {
            method.setAccessible(true);
            return method.invoke(object, params);
        } else {
            throw new NoSuchMethodException("No such method named " + methodName + "  " + params);
        }

//        if(paramTypes.length > 0) {
//            method = object.getClass().getDeclaredMethod(methodName, paramTypes);
//            method.setAccessible(true);
//            return method.invoke(object, params);
//        } else {
//            method = object.getClass().getDeclaredMethod(methodName);
//            method.setAccessible(true);
//            return method.invoke(object);
//        }
    }

    private static Method getMethod(Class objectClass, String methodName, Object... params) throws NoSuchMethodException {
        List<Class> paramTypeList = new ArrayList<>();
        if(params != null && params.length > 0) {
            for (Object param : params) {
                paramTypeList.add(param.getClass());
            }
        }
        Class[] paramTypes = new Class[paramTypeList.size()];
        paramTypeList.toArray(paramTypes);
        try {
            if(paramTypes.length > 0) {
                return objectClass.getDeclaredMethod(methodName, paramTypes);
            } else {
                return objectClass.getDeclaredMethod(methodName);
            }
        } catch (Exception e) {
            if(objectClass.getSuperclass() == null) {
                throw new NoSuchMethodException("No such method named " + methodName + "  " + paramTypes);
            } else {
                return getMethod(objectClass.getSuperclass(), methodName, params);
            }
        }
    }

}
