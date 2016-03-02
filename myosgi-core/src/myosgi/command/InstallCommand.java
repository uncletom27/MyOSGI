package myosgi.command;

import java.net.MalformedURLException;
import java.util.Properties;

import myosgi.Bundle;

public class InstallCommand implements Command{
    
    private String bundleName;
    public InstallCommand(String[] commandInfo){
        this.bundleName = commandInfo[1].trim();
    }

    @Override
    public void execute() {
        try {
            Bundle.install(bundleName);
        } catch (MalformedURLException e) {
            // TODO 在这里编写异常处理代码
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO 在这里编写异常处理代码
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO 在这里编写异常处理代码
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO 在这里编写异常处理代码
            e.printStackTrace();
        }
    }

}
