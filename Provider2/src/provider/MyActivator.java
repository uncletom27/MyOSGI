package provider;

import myosgi.Activator;
import myosgi.Register;
import service.Man;
import service.impl.BigMan;

public class MyActivator implements Activator{
    private Object registed;
    @Override
    public void start() {
        BigMan littleMan = new BigMan();
        Register.regist(Man.class, littleMan);
        this.registed = littleMan;
    }

    @Override
    public void shutDown() {
       Register.unregist(Man.class, registed);
    }

}
