public class HelloWorld {
    private String name;

    public HelloWorld(String name) {
        this.name = name;
    }

    public void hello() {
        System.out.println("Hello " + name);
    }

    public static void main(String[] args) {
        System.out.println("Hello World 1");

        new HelloWorld("World 2").hello();

        String name = (null != args && args.length > 0 && null != args[0] && args[0].length() > 0) ? args[0] : "World";
        System.out.println(String.format("Hello %s 3", name));

        for (String str : args) {
            System.out.printf("Hello %s 4", str);
        }
    }

}
