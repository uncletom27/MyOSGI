package provider;

import myosgi.Activator;
import myosgi.Register;
import service.Man;
import service.impl.LittleMan;

public class MyActivator implements Activator{
    private Object registed;
    @Override
    public void start() {
        LittleMan littleMan = new LittleMan();
        Register.regist(Man.class, littleMan);
        this.registed = littleMan;
    }

    @Override
    public void shutDown() {
       Register.unregist(Man.class, registed);
    }

}
