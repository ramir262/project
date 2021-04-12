/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mmatt
 */
public class Env {
    
    private Map<String,String> map;
    
    public Env() {
        map = new HashMap<String,String>();
        try {
            File myObj = new File("../.env");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.length() < 1)
                    continue;
                if(data.substring(0, 1).equals("#"))
                    continue;
                
                data = data.replace(" ", "");
                String[] kv = data.split("=");
                map.put(kv[0],kv[1]);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public String get(String key) {
        return map.get(key);
    }
    
    public Boolean contains(String key) {
        return map.containsKey(key);
    }
    
    public String ToString() {
        String output = "";
        Set<String> keys = map.keySet();
        for(String key : keys) {
            output += key+"="+map.get(key)+"\n";
        }
        return output;
    }
    
}
