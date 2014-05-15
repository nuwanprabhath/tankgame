/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tankgame;

import java.util.ArrayList;

/**
 *
 * @author Nuwan Prabhath
 */
/*
This class is used to communicate with the server.
*/
public class Communicator {
    
    public String receiveMessage(){
        
        return null;
    }
    
    public String sendToServer(String message){
        
        return null;
    }
    
    public String sendBuffer(ArrayList commands){
        
        //Accept a array list and send to server appopiate time and return results. 
        //Sould handle errors like  TOO_QUIC#
        return  null;
    }
    
    public boolean isSendBufferEmpty(){
        //If empty send true. 
        return true;
    }
    
}
