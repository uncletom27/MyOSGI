package myosgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import myosgi.command.Command;
import myosgi.command.CommandParser;

public class Console {
    public static void main(String[] args) throws IOException {
        Configuration.refresh();
        System.out.println("started console");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String s = reader.readLine().trim();
            Command command = CommandParser.parse(s);
            if(command == null) {
                System.out.println("commend err :" + s);
                continue;
            }
            command.execute();
        }
    }
}
