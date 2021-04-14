package demo.java.lang.reflect;

import demo.util.ReflectUtil;

public class ReflectUtilDemo {

    public static void main(String[] args) {
        System.out.println("1.ReflectUtil.loadClass");
        Class<?> clazz = ReflectUtil.loadClass("java.util.ArrayList$SubList");
        System.out.println("clazz=" + clazz);
        clazz = ReflectUtil.loadClass("java.util.ArrayList");
        System.out.println("clazz=" + clazz);

        System.out.println("2.ReflectUtil.newInstance");
        Object obj = ReflectUtil.newInstance(clazz, null);
        System.out.println("obj=" + obj);

        System.out.println("3.ReflectUtil.getFieldValue");
        System.out.println(ReflectUtil.getFieldValue(obj, "size"));

        System.out.println("4.ReflectUtil.getStaticFieldValue");
        System.out.println(ReflectUtil.getStaticFieldValue("java.util.ArrayList", "DEFAULT_CAPACITY"));

        System.out.println("5.ReflectUtil.setFieldValue");
        ReflectUtil.setFieldValue(obj, "size", 1);
        System.out.println(ReflectUtil.getFieldValue(obj, "size"));

        System.out.println("6.ReflectUtil.setStaticFieldValue");
        // 不能设置final修饰的静态成员变量
        // ReflectUtil.setStaticFieldValue("java.util.ArrayList", "DEFAULT_CAPACITY", 8);
        System.out.println(ReflectUtil.getStaticFieldValue("java.util.ArrayList", "DEFAULT_CAPACITY"));

        System.out.println("7.ReflectUtil.invoke");
        ReflectUtil.invoke(obj, "add", new Class[]{Object.class}, new Object[]{1});
        System.out.println("obj=" + obj);
    }

}
