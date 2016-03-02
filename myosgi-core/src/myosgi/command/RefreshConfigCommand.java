package myosgi.command;

import java.io.IOException;

import myosgi.Configuration;


public class RefreshConfigCommand implements Command{
    
    public RefreshConfigCommand(String[] commandInfo){
        
    }

    @Override
    public void execute() {
        try {
            Configuration.refresh();
        } catch (IOException e) {
            // TODO 在这里编写异常处理代码
            e.printStackTrace();
        }
    }

}
