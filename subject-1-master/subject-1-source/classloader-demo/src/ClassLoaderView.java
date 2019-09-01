/**
 * 查看类的加载器实例
 */
public class ClassLoaderView {
    public static void main(String[] args) throws Exception {
        // 加载核心类库的 BootStrap ClassLoader
        System.out.println("核心类库加载器："
                + ClassLoaderView.class.getClassLoader().loadClass("java.lang.String").getClassLoader());
        // 加载拓展库的 Extension ClassLoader
        System.out.println("拓展类库加载器：" + ClassLoaderView.class.getClassLoader()
                .loadClass("com.sun.nio.zipfs.ZipCoder").getClassLoader());
        // 加载应用程序的
        System.out.println("应用程序库加载器：" + ClassLoaderView.class.getClassLoader());

        // 双亲委派模型 Parents Delegation Model
        System.out.println("应用程序库加载器的父类：" + ClassLoaderView.class.getClassLoader().getParent());
        System.out.println(
                "应用程序库加载器的父类的父类：" + ClassLoaderView.class.getClassLoader().getParent().getParent());
    }
}
