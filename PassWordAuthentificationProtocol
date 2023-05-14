import java.net.*;
import java.io.*;
import java.util.*;
/**
 * @summary The purpose of this class is to authenticate the user to the server
 * @author Alexandre Nguyen
 * @version 1.0 Last modified on 13/05/2023
 */ 
public class PassWordAuthentificationProtocol {
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
     * @summary The purpose of this function is to process the user's input and determine if it is a username/password combination
     * @param theInput which is the user's input
     * @return theOutput which is the server's response to the user's input
     * @author Alexandre Nguyen
     * @version 1.0 Last modified on 13/05/2023
     * 
     */ 
    public String processInput(String theInput) {
        String theOutput = null;
 
        if (state == WAITING) {
        	theOutput ="Enter your username:";
        	state = AUTHENTICATINGUSERNAME;
        }
        else if (state == AUTHENTICATINGUSERNAME) {
        	currentUser=theInput;
        	isUserFOund=users.containsKey(theInput);
        	if (isUserFOund) {
        		currentPassword=users.get(theInput);
        	}
        	theOutput ="Enter password:";
            state = AUTHENTICATINGPASSWORD;
        } else if (state ==  AUTHENTICATINGPASSWORD) {
            if (isUserFOund && theInput==currentPassword) {
                theOutput = "You have successfully authenticated.";
                state = AUTHENTICATED;
            } else if (isUserFOund && theInput!=currentPassword) {
                theOutput = "Wrong password for this user. Please reenter your password";
            }
            else {
            	users.put(currentUser,theInput);
            	theOutput = "Welcome user:"+currentUser+"Your password is: "+theInput+ "Press n/N to exit the server.";
            	state = AUTHENTICATED;
            }
        } else if (state ==  AUTHENTICATED) {
        	if (theInput.equalsIgnoreCase("n")) {
	        	theOutput = "Bye.";
	        	state = EXIT;
        	}
        }
        return theOutput;
    }
    /**
     * @summary The purpose of this function is to write the username/password combinations to a CSV file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen
     * @version 1.0 Last modified on 13/05/2023
     * 
     */ 
    
    public boolean writeToCSV() {
    	return true;
    }
    /**
     * @summary The purpose of this function is to read the username/password combinations from a CSV file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen
     * @version 1.0 Last modified on 13/05/2023
     * 
     */ 
    public boolean readCSV() {
    	return true;
    }
}
