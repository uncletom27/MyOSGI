package caller;

import myosgi.Activator;
import myosgi.Register;
import service.Man;

public class MyActivator implements Activator{
    private Thread thread;
    @Override
    public void start() {
    	System.err.println("in caller 's MyActivator Man's class loader:" + Man.class.getClassLoader());
       this.thread =  new Thread(){

            @Override
            public void run() {
                while(!this.isInterrupted()){
                    MyActivator.this.call();
                    try {
                        Thread.sleep(10*1000);
                    } catch (InterruptedException e) {
                    	break;
                    }
                }
            }
        };
        this.thread.start();
    }
    
    private void call(){
        Man man = Register.getService(Man.class);
        if(man == null) {
            System.err.println("no service for :" + Man.class.getName());
            return;
        }
        man.sayHi();
    }

    @Override
    public void shutDown() {
        this.thread.interrupt();
        try {
			this.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
