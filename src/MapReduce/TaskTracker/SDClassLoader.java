package MapReduce.TaskTracker;

/**
 * Created by amaliujia on 15-5-25.
 */
public class SDClassLoader extends ClassLoader {
    public Class<?> findClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}
