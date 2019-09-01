/** 类加载测试类 */
public class HelloService {

    public static String value = getValue();

    static {
        System.out.println("静态代码块运行");
    }

    private static String getValue() {
        System.out.println("静态方法被运行");
        return "netease";
    }

    public void test() {
        System.out.println("hello.." + value);
    }
}
