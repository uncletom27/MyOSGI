package myosgi.command;

import java.io.IOException;

import myosgi.Bundle;


public class UnInstallCommand implements Command{
    
    private String bundleName;
    public UnInstallCommand(String[] commandInfo){
        this.bundleName = commandInfo[1].trim();
    }

    @Override
    public void execute() {
        try {
			Bundle.uninstall(bundleName);
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.gc();
        
    }

}
