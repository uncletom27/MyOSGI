package myosgi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {
    private static final String homePath = "lib";
    private static Map<String, Properties> configs = new HashMap<>();
    
    public static Properties getConfig(String bundleName){
        return configs.get(bundleName);
    }
    
    public static void refresh() throws IOException{
        File f = new File(homePath);
        File[] listFiles = f.listFiles();
        for (File file : listFiles) {
            URL url = file.toURI().toURL();
            URLClassLoader loader = new  URLClassLoader(new URL[]{url});
            URL resource = loader.getResource("META-INF/a.txt");
            Properties config = new Properties();
            config.load(resource.openStream());
            config.put(Bundle.BUNDLE_PATH, url.getPath());
            String bundleName = config.getProperty(Bundle.NAME);
            if(bundleName == null){
                System.out.println("error config:"+ resource.getPath());
                continue;
            }
            configs.put(bundleName, config);
            System.out.println("loaded config:"+ bundleName);
            loader.close();
        }
        System.out.println("configuration refreshed");
    }
    
    public static boolean ContainsBundleConfig(String bundleName){
    	return configs.containsKey(bundleName);
    }
}
