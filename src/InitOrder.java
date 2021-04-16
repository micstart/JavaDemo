@SuppressWarnings("unused")
public class InitOrder {

    /**
     * Java初始化次序：
     * 父类静态变量、静态初始化块依次执行（首次加载时）
     * 子类静态变量、静态初始化块依次执行（首次加载时）
     * 父类实例变量、实例初始化块依次执行
     * 父类构造方法
     * 子类实例变量、实例初始化块依次执行
     * 子类构造方法
     */
    public static void main(String[] args) {
        System.out.println("--- First time ---");
        Sub sub1 = new Sub();
        System.out.println("\n--- Second time ---");
        Sub sub2 = new Sub();
    }
}

@SuppressWarnings("unused")
class Base {
    static String baseStaticStr1 = printAndReturn("Base static var 1");
    String baseStr1 = printAndReturn("Base instance var 1");

    static {
        System.out.println("Base static init 1");
    }

    {
        System.out.println("Base instance init 1");
    }

    public Base() {
        System.out.println("Base constructor ***");
    }

    static String baseStaticStr2 = printAndReturn("Base static var 2");
    String baseStr2 = printAndReturn("Base instance var 2");

    static {
        System.out.println("Base static init 2");
    }

    {
        System.out.println("Base instance init 2");
    }

    public static String printAndReturn(String str) {
        System.out.println(str);
        return str;
    }
}

@SuppressWarnings("unused")
class Sub extends Base {
    static String subStaticStr1 = printAndReturn("Sub static var 1");
    String subStr1 = printAndReturn("Sub instance var 1");

    static {
        System.out.println("Sub static init 1");
    }

    {
        System.out.println("Sub instance init 1");
    }

    public Sub() {
        System.out.println("Sub constructor ***");
    }

    static String subStaticStr2 = printAndReturn("Sub static var 2");
    String subStr2 = printAndReturn("Sub instance var 2");

    static {
        System.out.println("Sub static init 2");
    }

    {
        System.out.println("Sub instance init 2");
    }
}
