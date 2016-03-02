package service.impl;
import service.Man;


public class OldMan implements Man {

    @Override
    public void sayHi() {
        System.err.println("hi, I am a old man");
    }

}
