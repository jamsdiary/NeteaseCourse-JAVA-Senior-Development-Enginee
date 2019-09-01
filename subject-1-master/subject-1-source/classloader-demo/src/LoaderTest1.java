import java.net.URL;
import java.net.URLClassLoader;

/**
 * 热加载，指定class 进行加载e
 */
public class LoaderTest1 {
    public static void main(String[] args) throws Exception {
        URL classUrl = new URL("file:D:\\");
        // 测试双亲委派机制
        // 如果使用此加载器作为父加载器,则下面的热更新会失效,因为双亲委派机制,HelloService实际上是被这个类加载器加载的;
        //  URLClassLoader parentLoader = new URLClassLoader(new URL[]{classUrl});

        while (true) {
            // 创建一个新的类加载器，它的父加载器为上面的parentLoader
            URLClassLoader loader = new URLClassLoader(new URL[]{classUrl}, LoaderTest1.class.getClassLoader());

            Class clazz = loader.loadClass("HelloService");
            System.out.println("HelloService所使用的类加载器：" + clazz.getClassLoader());
            Object newInstance = clazz.newInstance();
            Object value = clazz.getMethod("test").invoke(newInstance);
            System.out.println("调用getValue获得的返回值为：" + value);

            // help gc
            newInstance = null;
            value = null;

            System.gc();
            loader.close();

            Thread.sleep(3000L); // 1秒执行一次
            System.out.println();
        }
    }
}
