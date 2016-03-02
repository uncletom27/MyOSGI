package service.impl;
import service.Man;


public class BigMan implements Man {

    @Override
    public void sayHi() {
        System.err.println("hi, I am a big man");
    }

}
