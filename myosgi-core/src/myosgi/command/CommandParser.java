package myosgi.command;


public class CommandParser {
    public static Command parse(String commandString){
        String[] split = commandString.split(" ");
        if(split.length < 1) {
            return null;
        }
        if(split[0].trim().equals("install")){
           return new InstallCommand(split);
        }
        
        if(split[0].trim().equals("uninstall")){
            return new UnInstallCommand(split);
         }
        
        if(split[0].trim().equals("refresh")){
            return new RefreshConfigCommand(split);
         }
        
        return null;
        
    }
}
