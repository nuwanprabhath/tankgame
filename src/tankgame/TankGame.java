/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import com.pubnub.api.*;
import org.json.*;

/**
 *
 * @author Nuwan Prabhath
 */
public class TankGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        use to communicate using pubnub protocol. This is not used in this program.
        Normal TCP communication is used. 
        Required jar files are:
        bcprov-jdk15on-1.47
        json-20090211
        Pubnub-StandardEdition-3.6.0
        slf4j-api-1.7.5
        */
        Pubnub pubnub = new Pubnub("demo", "demo");

        PubnubUtil u = new PubnubUtil();
        
        
        try {
            pubnub.subscribe("b", new Callback() {

                @Override
                public void connectCallback(String channel, Object message) {
                    System.out.println("PUBNUB SUBSCRIBE : CONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    System.out.println("PUBNUB SUBSCRIBE : DISCONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                public void reconnectCallback(String channel, Object message) {
                    System.out.println("PUBNUB SUBSCRIBE : RECONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void successCallback(String channel, Object message) {
                    System.out.println("PUBNUB SUBSCRIBE : " + channel + " : "
                            + message.getClass() + " : " + message.toString());
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    System.out.println("PUBNUB SUBSCRIBE : ERROR on channel " + channel
                            + " : " + error.toString());
                }
            }
            );
        } catch (PubnubException e) {
            System.out.println("PUBNUB" + e.toString());
        }

        Callback callback = new Callback() {
            public void successCallback(String channel, Object response) {
                System.out.println("PUBNUB"+ response.toString());
            }

            public void errorCallback(String channel, PubnubError error) {
                System.out.println("PUBNUB"+ error.toString());
            }
        };

        //pubnub.publish("demo", "Hello World !!", callback);
        //pubnub.unsubscribe("a");
    }

}
