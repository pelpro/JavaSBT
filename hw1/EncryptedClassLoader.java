import java.io.*;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;

    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String name) {
        String path = dir.getPath() + "/" + name.replace('.', '/') + ".class";

        try {
            InputStream file = new FileInputStream(new File(path));
            long length = new File(path).length();
            byte[] bytes = file.readAllBytes();
            for (int i = 0; i < length; i++) {
                bytes[i] = (byte) (bytes[i] + 1);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}