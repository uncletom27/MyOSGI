package myosgi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Register {
    private static Map<Class<?>, Object> services = new ConcurrentHashMap<>();
    
    
    
    public static void regist(Class<?> serviceClass, Object service){
        services.put(serviceClass, service);
    }
    
    public static <T>  T getService(Class<?> serviceClass){
        return (T) services.get(serviceClass);
    }
    
    public static void unregist(Class<?> serviceClass,  Object service){
        if(services.get(serviceClass) == service){
            services.remove(serviceClass);
        }
    }
    
    public static void clear(){
        services.clear();
    }
}
