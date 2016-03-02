package service.impl;
import service.Man;


public class LittleMan implements Man {

    @Override
    public void sayHi() {
        System.err.println("hi, I am a little man");
    }

}
