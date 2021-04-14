package demo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author wangyao
 */
@SuppressWarnings("unused")
public final class ReflectUtil {

    /**
     * 加载Class
     *
     * @param className 类名
     * @return Class对象
     */
    public static Class<?> loadClass(String className) {
        // check parameter
        if (null == className || className.length() < 1) {
            return null;
        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (Exception e) {
            logException(e);
        }
        return clazz;
    }

    /**
     * 根据Class创建对象实例
     *
     * @param clazz     Class对象
     * @param argsClass 参数类型数组
     * @param args      参数数组
     * @return 实例
     */
    public static Object newInstance(Class<?> clazz, Class<?>[] argsClass, Object[] args) {
        // check parameter
        int argsClassLen = (null == argsClass) ? 0 : argsClass.length;
        int argsLen = (null == args) ? 0 : args.length;
        if (null == clazz || argsClassLen != argsLen) {
            return null;
        }

        Object result = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(argsClass);
            result = constructor.newInstance(args);
        } catch (Exception e) {
            logException(e);
        }
        return result;
    }

    /**
     * 根据Class创建对象实例（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param clazz Class对象
     * @param args  参数数组
     * @return 实例
     */
    public static Object newInstance(Class<?> clazz, Object[] args) {
        return newInstance(clazz, getArgsClass(args), args);
    }

    /**
     * 根据Class创建对象实例（适用于默认构造函数）
     *
     * @param clazz Class对象
     * @return 实例
     */
    public static Object newInstance(Class<?> clazz) {
        return newInstance(clazz, null, null);
    }

    /**
     * 根据类名创建对象实例
     *
     * @param className 类名
     * @param argsClass 参数类型数组
     * @param args      参数数组
     * @return 实例
     */
    public static Object newInstance(String className, Class<?>[] argsClass, Object[] args) {
        return newInstance(loadClass(className), argsClass, args);
    }

    /**
     * 根据类名创建对象实例（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param className 类名
     * @param args      参数数组
     * @return 实例
     */
    public static Object newInstance(String className, Object[] args) {
        return newInstance(loadClass(className), getArgsClass(args), args);
    }

    /**
     * 根据类名创建对象实例（适用于默认构造函数）
     *
     * @param className 类名
     * @return 实例
     */
    public static Object newInstance(String className) {
        return newInstance(loadClass(className), null, null);
    }

    /**
     * 获取对象成员变量值
     *
     * @param obj       对象
     * @param fieldName 成员变量名称
     * @return 对象成员变量值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        // check parameter
        if (null == obj || null == fieldName || fieldName.length() < 1) {
            return null;
        }

        Object result = null;
        Class<?> clazz = getObjClass(obj);
        try {
            // 优先获取自身的成员变量
            Field field = clazz.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            result = field.get(obj);
        } catch (NoSuchFieldException exception) {
            // 尝试获取基类的成员变量
            try {
                Field field = clazz.getField(fieldName);
                result = field.get(obj);
            } catch (Exception e) {
                logException(e);
            }
        } catch (Exception e) {
            logException(e);
        }
        return result;
    }

    /**
     * 获取对象成员变量值
     *
     * @param obj          对象
     * @param fieldName    成员变量名称
     * @param defaultValue 默认值
     * @return 对象成员变量值
     */
    public static Object getFieldValue(Object obj, String fieldName, Object defaultValue) {
        Object value = getFieldValue(obj, fieldName);
        return (null == value) ? defaultValue : value;
    }

    /**
     * 获取类静态成员变量值
     *
     * @param clazz     类
     * @param fieldName 静态成员变量名称
     * @return 类静态成员变量值
     */
    public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
        return getFieldValue(clazz, fieldName);
    }

    /**
     * 获取类静态成员变量值
     *
     * @param clazz        类
     * @param fieldName    静态成员变量名称
     * @param defaultValue 默认值
     * @return 类静态成员变量值
     */
    public static Object getStaticFieldValue(Class<?> clazz, String fieldName, Object defaultValue) {
        return getFieldValue(clazz, fieldName, defaultValue);
    }

    /**
     * 获取类静态成员变量值
     *
     * @param className 类名
     * @param fieldName 静态成员变量名称
     * @return 类静态成员变量值
     */
    public static Object getStaticFieldValue(String className, String fieldName) {
        return getFieldValue(loadClass(className), fieldName);
    }

    /**
     * 获取类静态成员变量值
     *
     * @param className    类名
     * @param fieldName    静态成员变量名称
     * @param defaultValue 默认值
     * @return 类静态成员变量值
     */
    public static Object getStaticFieldValue(String className, String fieldName, Object defaultValue) {
        return getFieldValue(loadClass(className), fieldName, defaultValue);
    }

    /**
     * 设置对象成员变量值
     *
     * @param obj        对象
     * @param fieldName  成员变量名称
     * @param fieldValue 值
     * @return 是否成功
     */
    public static boolean setFieldValue(Object obj, String fieldName, Object fieldValue) {
        // check parameter
        if (null == obj || null == fieldName || fieldName.length() < 1) {
            return false;
        }

        boolean result = false;
        Class<?> clazz = getObjClass(obj);
        try {
            // 优先获取自身的成员变量
            Field field = clazz.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(obj, fieldValue);
            result = true;
        } catch (NoSuchFieldException exception) {
            // 尝试获取基类的成员变量
            try {
                Field field = clazz.getField(fieldName);
                field.set(obj, fieldValue);
                result = true;
            } catch (Exception e) {
                logException(e);
            }
        } catch (Exception e) {
            logException(e);
        }

        return result;
    }

    /**
     * 设置类静态成员变量值
     *
     * @param clazz      类
     * @param fieldName  静态成员变量名称
     * @param fieldValue 值
     * @return 是否成功
     */
    public static boolean setStaticFieldValue(Class<?> clazz, String fieldName, Object fieldValue) {
        return setFieldValue(clazz, fieldName, fieldValue);
    }

    /**
     * 设置类静态成员变量值
     *
     * @param className  类名
     * @param fieldName  静态成员变量名称
     * @param fieldValue 值
     * @return 是否成功
     */
    public static boolean setStaticFieldValue(String className, String fieldName, Object fieldValue) {
        return setFieldValue(loadClass(className), fieldName, fieldValue);
    }

    /**
     * 调用对象指定方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param argsClass  参数类型数组
     * @param args       参数数组
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName, Class<?>[] argsClass, Object[] args) {
        // check parameter
        int argsClassLen = (null == argsClass) ? 0 : argsClass.length;
        int argsLen = (null == args) ? 0 : args.length;
        if (null == obj || null == methodName || methodName.length() < 1 || argsClassLen != argsLen) {
            return null;
        }

        Object result = null;
        Class<?> clazz = getObjClass(obj);
        try {
            // 优先获取自身的方法
            Method method = clazz.getDeclaredMethod(methodName, argsClass);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            result = method.invoke(obj, args);
        } catch (NoSuchMethodException exception) {
            // 尝试获取基类的方法
            try {
                Method method = clazz.getMethod(methodName, argsClass);
                result = method.invoke(obj, args);
            } catch (Exception e) {
                logException(e);
            }
        } catch (Exception e) {
            logException(e);
        }
        return result;
    }

    /**
     * 调用对象指定方法
     *
     * @param obj          对象
     * @param methodName   方法名
     * @param argsClass    参数类型数组
     * @param args         参数数组
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName, Class<?>[] argsClass, Object[] args, Object defaultValue) {
        Object value = invoke(obj, methodName, argsClass, args);
        return (null == value) ? defaultValue : value;
    }

    /**
     * 调用对象指定方法（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       参数数组
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName, Object[] args) {
        return invoke(obj, methodName, getArgsClass(args), args);
    }

    /**
     * 调用对象指定方法（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param obj          对象
     * @param methodName   方法名
     * @param args         参数数组
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName, Object[] args, Object defaultValue) {
        Object value = invoke(obj, methodName, getArgsClass(args), args);
        return (null == value) ? defaultValue : value;
    }

    /**
     * 调用对象指定方法（适用于无参数方法）
     *
     * @param obj        对象
     * @param methodName 方法名
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName) {
        return invoke(obj, methodName, null, null);
    }

    /**
     * 调用对象指定方法（适用于无参数方法）
     *
     * @param obj          对象
     * @param methodName   方法名
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName, Object defaultValue) {
        return invoke(obj, methodName, null, null, defaultValue);
    }

    /**
     * 调用静态方法
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param argsClass  参数类型数组
     * @param args       参数数组
     * @return 方法返回值
     */
    public static Object invokeStatic(Class<?> clazz, String methodName, Class<?>[] argsClass, Object[] args) {
        return invoke(clazz, methodName, argsClass, args);
    }

    /**
     * 调用静态方法
     *
     * @param clazz        类
     * @param methodName   方法名
     * @param argsClass    参数类型数组
     * @param args         参数数组
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invokeStatic(Class<?> clazz, String methodName, Class<?>[] argsClass, Object[] args, Object defaultValue) {
        return invoke(clazz, methodName, argsClass, args, defaultValue);
    }

    /**
     * 调用静态方法（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param args       参数数组
     * @return 方法返回值
     */
    public static Object invokeStatic(Class<?> clazz, String methodName, Object[] args) {
        return invoke(clazz, methodName, args);
    }

    /**
     * 调用静态方法（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param clazz        类
     * @param methodName   方法名
     * @param args         参数数组
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invokeStatic(Class<?> clazz, String methodName, Object[] args, Object defaultValue) {
        return invoke(clazz, methodName, args, defaultValue);
    }

    /**
     * 调用静态方法（适用于无参数方法）
     *
     * @param clazz      类
     * @param methodName 方法名
     * @return 方法返回值
     */
    public static Object invokeStatic(Class<?> clazz, String methodName) {
        return invoke(clazz, methodName, null, null);
    }

    /**
     * 调用静态方法（适用于无参数方法）
     *
     * @param clazz        类
     * @param methodName   方法名
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invokeStatic(Class<?> clazz, String methodName, Object defaultValue) {
        return invoke(clazz, methodName, null, null, defaultValue);
    }

    /**
     * 调用静态方法
     *
     * @param className  类名
     * @param methodName 方法名
     * @param argsClass  参数类型数组
     * @param args       参数数组
     * @return 方法返回值
     */
    public static Object invokeStatic(String className, String methodName, Class<?>[] argsClass, Object[] args) {
        return invoke(loadClass(className), methodName, argsClass, args);
    }

    /**
     * 调用静态方法
     *
     * @param className    类名
     * @param methodName   方法名
     * @param argsClass    参数类型数组
     * @param args         参数数组
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invokeStatic(String className, String methodName, Class<?>[] argsClass, Object[] args, Object defaultValue) {
        return invoke(loadClass(className), methodName, argsClass, args, defaultValue);
    }

    /**
     * 调用静态方法（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数数组
     * @return 方法返回值
     */
    public static Object invokeStatic(String className, String methodName, Object[] args) {
        return invoke(loadClass(className), methodName, args);
    }

    /**
     * 调用静态方法（适用于对象类型参数，原始类型参数必须指定参数类型）
     *
     * @param className    类名
     * @param methodName   方法名
     * @param args         参数数组
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invokeStatic(String className, String methodName, Object[] args, Object defaultValue) {
        return invoke(loadClass(className), methodName, args, defaultValue);
    }

    /**
     * 调用静态方法（适用于无参数方法）
     *
     * @param className  类名
     * @param methodName 方法名
     * @return 方法返回值
     */
    public static Object invokeStatic(String className, String methodName) {
        return invokeStatic(className, methodName, null, null);
    }

    /**
     * 调用静态方法（适用于无参数方法）
     *
     * @param className    类名
     * @param methodName   方法名
     * @param defaultValue 默认值
     * @return 方法返回值
     */
    public static Object invokeStatic(String className, String methodName, Object defaultValue) {
        return invokeStatic(className, methodName, null, null, defaultValue);
    }

    /**
     * 判断类是否存在
     *
     * @param className 类名
     * @return 存在时返回true
     */
    public static boolean isClassExist(String className) {
        Class<?> clazz = loadClass(className);
        return null != clazz;
    }

    /**
     * 判断常量或变量是否存在
     *
     * @param clazz     类
     * @param fieldName 成员变量名
     * @return 存在时返回true
     */
    public static boolean isFieldExist(Class<?> clazz, String fieldName) {
        // check parameter
        if (null == clazz || null == fieldName || fieldName.length() < 1) {
            return false;
        }

        boolean result = false;
        try {
            // 优先获取自身的成员变量
            clazz.getDeclaredField(fieldName);
            result = true;
        } catch (NoSuchFieldException exception) {
            // 尝试获取基类的成员变量
            try {
                clazz.getField(fieldName);
                result = true;
            } catch (Exception e) {
                logException(e);
            }
        } catch (Exception e) {
            logException(e);
        }

        return result;
    }

    /**
     * 判断常量或变量是否存在
     *
     * @param className 类名
     * @param fieldName 成员变量名
     * @return 存在时返回true
     */
    public static boolean isFieldExist(String className, String fieldName) {
        return isFieldExist(loadClass(className), fieldName);
    }

    /**
     * 判断方法是否存在
     *
     * @param clazz      Class对象
     * @param methodName 方法名
     * @param argsClass  参数类型数组
     * @return 存在时返回true
     */
    public static boolean isMethodExist(Class<?> clazz, String methodName, Class<?>[] argsClass) {
        if (null == clazz || null == methodName || methodName.length() < 1) {
            return false;
        }

        boolean result = false;
        try {
            // 优先获取自身的方法
            clazz.getDeclaredMethod(methodName, argsClass);
            result = true;
        } catch (NoSuchMethodException exception) {
            // 尝试获取基类的方法
            try {
                clazz.getMethod(methodName, argsClass);
                result = true;
            } catch (Exception e) {
                logException(e);
            }
        } catch (Exception e) {
            logException(e);
        }

        return result;
    }

    /**
     * 判断方法是否存在（适用于无参数方法）
     *
     * @param clazz      Class对象
     * @param methodName 方法名
     * @return 方法存在时返回true
     */
    public static boolean isMethodExist(Class<?> clazz, String methodName) {
        return isMethodExist(clazz, methodName, null);
    }

    /**
     * 判断方法是否存在
     *
     * @param className  类名
     * @param methodName 方法名
     * @param argsClass  参数类型数组
     * @return 方法存在时返回true
     */
    public static boolean isMethodExist(String className, String methodName, Class<?>[] argsClass) {
        return isMethodExist(loadClass(className), methodName, argsClass);
    }

    /**
     * 判断方法是否存在（适用于无参数方法）
     *
     * @param className  类名
     * @param methodName 方法名
     * @return 方法存在时返回true
     */
    public static boolean isMethodExist(String className, String methodName) {
        return isMethodExist(loadClass(className), methodName, null);
    }

    /**
     * 判断构造方法是否存在
     *
     * @param clazz     Class对象
     * @param argsClass 参数类型数组
     * @return 构造方法存在时返回true
     */
    public static boolean isConstructorExist(Class<?> clazz, Class<?>[] argsClass) {
        if (null == clazz) {
            return false;
        }

        boolean result = false;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(argsClass);
            result = true;
        } catch (Exception e) {
            logException(e);
        }
        return result;
    }

    /**
     * 判断构造方法是否存在
     *
     * @param className 类名
     * @param argsClass 参数类型数组
     * @return 构造方法存在时返回true
     */
    public static boolean isConstructorExist(String className, Class<?>[] argsClass) {
        return isConstructorExist(loadClass(className), argsClass);
    }

    /**
     * 获取参数数组对应的Class数组
     *
     * @param args 参数数组
     * @return 参数类型数组
     */
    public static Class<?>[] getArgsClass(Object[] args) {
        Class<?>[] result = null;
        if (null != args && args.length > 0) {
            result = new Class[args.length];
            for (int i = 0, length = args.length; i < length; i++) {
                result[i] = args[i].getClass();
            }
        }
        return result;
    }

    public static Class<?> getObjClass(Object obj) {
        return obj instanceof Class ? (Class<?>) obj : obj.getClass();
    }

    private static void logException(Exception e) {
        // if (null != e.getMessage()) {
        // Log.e(TAG, e.getMessage(), e);
        // } else {
        e.printStackTrace();
        // }
    }
}
