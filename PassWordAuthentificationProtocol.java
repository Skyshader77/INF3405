/**
 * @summary The purpose of this class is to authenticate the user to the server
 * and save each user's respective username and password
 * @author Alexandre Nguyen & Louis-Antoine
 * @version 4 Last modified on 26/05/2023
 * 
 */ 

import java.io.*;
import java.util.*;

public class PassWordAuthentificationProtocol {
	// ATTRIBUTES
	private static final int WAITING= 0;
    private static final int AUTHENTICATINGUSERNAME = 1;
    private static final int AUTHENTICATINGPASSWORD = 2;
    private static final int AUTHENTICATED= 3;
    private static final int EXIT= 4;
 
    private int state = WAITING;
    private boolean isUserFOund=false;
    
    private Map<String, String> users = new HashMap<String, String>();
    private String currentPassword="";
    private String currentUser="";

    /**
     * @summary The purpose of this constructor is to grab the password file if it exists and put it in the users map
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 1.0 Last modified on 15/05/2023
     * 
     */ 
    public  PassWordAuthentificationProtocol() {
    	try {
    		users= readTxt();
    	}
    	catch(IOException e){
    		users = new HashMap<String, String>();
    	}
    }
    
    /**
     * @summary The purpose of this method is to process the user's input and determine if it is a username/password combination
     * @param theInput which is the user's input
     * @return theOutput which is the server's response to the user's input
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 2.0 Last modified on 21/05/2023
     * 
     */ 
    public String processInput(String theInput) {
    	
        String theOutput = null;
        
        if (state == WAITING) {
        	theOutput ="Please enter your username, unsuspecting Canadian user:";
        	state = AUTHENTICATINGUSERNAME;
        }
        else if (state == AUTHENTICATINGUSERNAME) {
        	currentUser=theInput;
        	isUserFOund=users.containsKey(theInput);
        	if (isUserFOund) {
        		currentPassword=users.get(theInput);
        	}
        	theOutput ="Please enter your password, unsuspecting Canadian user:";
            state = AUTHENTICATINGPASSWORD;
        } else if (state ==  AUTHENTICATINGPASSWORD) {
        	//if the user was found in the database
            if (isUserFOund==false) {
            	users.put(currentUser,theInput);
            	theOutput = "Welcome unsuspecting Canadian user:"+currentUser+". Your password is: "+theInput+ ". Write disconnect to exit the server.\n";
            	try {
            		writeToTxt();
            	}
            	catch(IOException e){
            		System.out.println("Program failed to write the username/password to a text file.");
            	}
            	state = AUTHENTICATED; 
        	}
          //if the user has entered the correct password
            else if (isUserFOund==true && theInput.equals(currentPassword)==true) {
                theOutput = "Welcome back unsuspecting Canadian user:"+currentUser+". Write disconnect to exit the server.\n";
                state = AUTHENTICATED;   
             //if the user has not entered the correct password
            } else if (isUserFOund==true && theInput.equals(currentPassword)==false) {
                theOutput = "Wrong password for this unsuspecting Canadian user. Please reenter your password \n";
            }
        } else if (state ==  AUTHENTICATED) {
        	if (theInput.equals("disconnect")) {
	        	theOutput = "Bye.";
	        	state = EXIT;
        	}
		//Temporary line to stop program from crashing due to blank output
		theOutput = "Test";
        }
        return theOutput;
    }
    
    /**
     * @summary The purpose of this function is to write the username/password combinations to a TXT file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 2.0 Last modified on 15/05/2023
     * 
     */ 
    
    public void writeToTxt() throws IOException {
    	BufferedWriter fbiLeak = new BufferedWriter(new FileWriter("FBI_Passwords.txt"));
    	Iterator<Map.Entry<String, String>> itr = users.entrySet().iterator();
        
        while(itr.hasNext())
        {
             Map.Entry<String, String> entry = itr.next();
             fbiLeak.write(entry.getKey() + ":"
                     + entry.getValue());
             fbiLeak.newLine();
        }
        fbiLeak.close();
    }
    
    /**
     * @summary The purpose of this function is to read the username/password combinations from a TXT file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
     * @version 2.0 Last modified on 15/05/2023
     * 
     */ 
    public Map<String, String> readTxt() throws IOException{
    	String line;
    	Map<String, String> container = new HashMap<String, String>();
        BufferedReader reader = new BufferedReader(new FileReader("FBI_Passwords.txt"));
        while ((line = reader.readLine()) != null)
        {
            String[] passwordCombo = line.split(":", 2);
            if (passwordCombo.length >= 2)
            {
                String key = passwordCombo[0];
                String value = passwordCombo[1];
                container.put(key, value);
            } 
        }
        reader.close();
    	return container;
    }
    
    /**
     * @summary The purpose of this function is to grab the current state of the password Authentification protocol
     * @return an integer with the state of the password Authentification protocol
     * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
     * @version 1.0 Last modified on 21/05/2023
     * 
     */ 
    public int getState() {
    	return state;
    }
    
    /**
     * @summary The purpose of this function is to grab the username current stored in the password Authentification protocol
     * @return a string with the username
     * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
     * @version 1.0 Last modified on 21/05/2023
     * 
     */ 
    public String getUser() {
    	return currentUser;
    }
}
