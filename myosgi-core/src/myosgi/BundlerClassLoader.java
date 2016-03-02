package myosgi;

import java.net.URL;
import java.net.URLClassLoader;

class BundlerClassLoader extends URLClassLoader{
	static {
		registerAsParallelCapable();//
	}
    private Bundle bundle;
    public BundlerClassLoader(Bundle bundle, URL[] urls) {
        super(urls);
        this.bundle = bundle;
    }
    
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> result = null;
        String packageName = this.getPackageName(name);
        if(Bundle.isExported(packageName)){
            Bundle b = Bundle.getBundleByExport(packageName);
            if(b != this.bundle){
                result = b.loadClass(name);
            }
        }
        
        if(result == null){
            result = super.loadClass(name, resolve);
        }
        
        return result;
        
    }



    private String getPackageName(String className) {
        int offset = className.lastIndexOf('.');
        return (offset == -1) ? null : className.substring(0, offset);
    }

    @Override
    protected void finalize() throws Throwable {
        System.err.println("finalize loader of bundle:" + bundle.bundleName);
    }
    
    public String getBundleName(){
    	return this.bundle.bundleName;
    }

	@Override
	public String toString() {
		return super.toString()+":"+this.bundle.bundleName;
	}
    
    
    
    
}
