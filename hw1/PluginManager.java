import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        URL urls;
        URLClassLoader urlClassLoader;
        Plugin downloadPlugin = new MyPlugin();
        try {
            urls = new URL("file:/" + pluginRootDirectory + "/" + pluginName + "/");
            urlClassLoader = new URLClassLoader(new URL[]{urls});
            downloadPlugin = (Plugin) urlClassLoader.loadClass(pluginClassName).getDeclaredConstructor().newInstance();
            return downloadPlugin;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}