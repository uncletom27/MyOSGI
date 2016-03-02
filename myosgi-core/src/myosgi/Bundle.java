package myosgi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bundle {
	public static final String ACTIVATOR = "activator";
    public static final String BUNDLE_PATH = "bundlePath";
	public static final String EXPORT = "export";
	public static final String NAME = "name";
	public static final String REQUIRE = "require";
	
	private static Map<String, Bundle> nameToBundle = new HashMap<>();
    private static Map<String, Bundle> exportToBundle = new ConcurrentHashMap<>();
    String bundleName;
    private String activatorName;
    private Activator activator;
    private String export;
    private BundlerClassLoader classLoader;
    private AtomicBoolean isStarted = new AtomicBoolean(false);
    private Bundle(){
    }
    
    
    public static void install(String bundleName) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
    	if(nameToBundle.containsKey(bundleName)){
    		System.out.println("bundle already installed, so did nothing. bundleName : "+ bundleName);
    		return;
    	}
    	Properties config = Configuration.getConfig(bundleName);
    	if(config == null) {
            System.out.println("no bundle found. bundleName : "+ bundleName);
            return;
        }
    	
    	String require = config.getProperty(REQUIRE);
    	if(require!=null&& !checkRequire(require)){
    		return;
    	}
    	
        Bundle bundle = new Bundle();
        bundle.bundleName = config.getProperty(NAME);
        bundle.export = config.getProperty(EXPORT);
        bundle.classLoader = new BundlerClassLoader(bundle, new URL[]{new File(config.getProperty(BUNDLE_PATH)).toURI().toURL()});
        bundle.activatorName = config.getProperty(ACTIVATOR);
        
        nameToBundle.put(bundle.bundleName, bundle);
        if(bundle.export!=null){
            exportToBundle.put(bundle.export, bundle);
        }
        
        bundle.start();
        System.out.println("installed "+ bundle.bundleName);
    }
    
    private static boolean checkRequire(String require){
    	String[] bundleNames = require.split(";");
    	for (String s : bundleNames) {
    		String bundleName = s.trim();
    		if(!Configuration.ContainsBundleConfig(bundleName)){
    			System.out.println("required bundle not found. bundleName : "+ bundleName);
    			return false;
    		}
			if(!nameToBundle.containsKey(bundleName)){
				System.out.println("required bundle not installed please installed it first. bundleName : "+ bundleName);
    			return false;
			}
		}
    	
    	return true;
    }
    
    private void start() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        if(isStarted.compareAndSet(false, true)){
            activator = (Activator) this.classLoader.loadClass(activatorName).newInstance();
            activator.start();
        }
    }
    
    Class<?> loadClass(String className) throws ClassNotFoundException{
        return this.classLoader.loadClass(className);
    }
    
    public static void uninstall(String bundleName) throws IOException{
    	Properties config = Configuration.getConfig(bundleName);
    	if(config == null) {
            System.out.println("no bundle found. bundleName : "+ bundleName);
            return;
        }
    	
        Bundle bundle = nameToBundle.get(bundleName);
        if(bundle == null) {
        	System.out.println("bundle not installed, so did nothing. bundleName : "+ bundleName);
        	return;
        }
        bundle.activator.shutDown();
        nameToBundle.remove(bundleName);
        if(bundle.export != null){
            exportToBundle.remove(bundle.export);
        }
        bundle.classLoader.close();
        
        System.out.println("uninstalled "+ bundleName);
    }
    
    public static Bundle getBundleByExport(String exportPackageName){
        return exportToBundle.get(exportPackageName);
    }
    
    public static boolean isExported(String packageName){
        return exportToBundle.containsKey(packageName);
    }
    
}
