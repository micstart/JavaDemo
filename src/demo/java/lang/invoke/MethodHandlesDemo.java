package demo.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;

public class MethodHandlesDemo {

    public static void main(String[] args) throws Throwable {
        Object obj;
        MethodType mt;
        MethodHandle mh;
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 成员方法
        mt = MethodType.methodType(String.class, char.class, char.class);
        mh = lookup.findVirtual(String.class, "replace", mt);
        obj = mh.invoke("haha", 'a', 'o');
        System.out.println(obj);
        obj = (String) mh.invokeExact("jovo", 'o', 'a');
        System.out.println(obj);

        // 静态方法
        mt = MethodType.methodType(List.class, Object[].class);
        mh = lookup.findStatic(Arrays.class, "asList", mt);
        System.out.println(mh.isVarargsCollector());
        obj = mh.invoke("one", "two");
        System.out.println(obj);
        obj = mh.invokeWithArguments("one", "two", "three");
        System.out.println(obj);
        // WrongMethodTypeException: expected (Object[])List but found (String,String,String)List
//        x = (List<?>) mh.invokeExact("a", "b", "c");
        mt = MethodType.genericMethodType(3);
        mh = mh.asType(mt);
        obj = mh.invokeExact((Object) "a", (Object) "b", (Object) "c");
        System.out.println(obj);

        // 构造方法
        mt = MethodType.methodType(void.class, char[].class);
        mh = lookup.findConstructor(String.class, mt);
        obj = mh.invoke(new char[]{'a', 'b', 'c'});
        System.out.println(obj);

        // 成员变量(需保证可见性)
        Book book = new Book(1, "TITLE", 9.5f);
        // Getter
        System.out.println("book=" + book);
        mh = lookup.findGetter(Book.class, "id", int.class);
        System.out.println("book.id=" + mh.invoke(book));
        mh = lookup.findGetter(Book.class, "title", String.class);
        System.out.println("book.title=" + mh.invoke(book));
        mh = lookup.findGetter(Book.class, "price", float.class);
        System.out.println("book.price=" + mh.invoke(book));
        // Setter(final不可修改)
        mh = lookup.findSetter(Book.class, "id", int.class);
        mh.invoke(book, 2);
        mh = lookup.findSetter(Book.class, "title", String.class);
        mh.invoke(book, "TITLE2");
        mh = lookup.findSetter(Book.class, "price", float.class);
        mh.invoke(book, 10.5f);
        System.out.println("book=" + book);

        // 静态变量(需保证可见性)
        mh = lookup.findStaticGetter(Integer.class, "MAX_VALUE", int.class);
        obj = mh.invoke();
        System.out.println(obj);
    }

    public static class Book {
        public int id;
        String title;
        private float price;

        public Book(int id, String title, float price) {
            this.id = id;
            this.title = title;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

}
