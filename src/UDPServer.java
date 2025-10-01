import java.io.IOException ;
import java.net.DatagramPacket ;
import java.net.DatagramSocket ;
import java.net.SocketException ;
import java.nio.channels.DatagramChannel ;

/**
 * 
 * 
 * TODO Implement UDP server capabilities.
 * 1) client --> server --> client  (server as the middleman)
 * This means we need to make a queue system to handle multiple datagrams at a time.
 * TODO Implement File I/O capabilities.
 * This means sending files through a router and a server to another client where
 * they can easily download and read files through their own file explorer.
 * 
 * TODO Decide if we want to do the middleman or just have one side be the server.
 * 
 * 
 * 
 * @author Benjamin MacDougall, Sean Perez, Zach "", Alex ""
 *
 * @version 1.0 2025-09-24 Initial implementation
 *
 * @since 1.0
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class UDPServer
    {
    
    DatagramSocket serverSocket;
    
    /**
     * 
     *
     * 
     *
     * @since 1.0
     */
    public UDPServer(int port)
        {

          try(DatagramSocket testSocket = new DatagramSocket(port))
            {
            this.serverSocket = testSocket;
            }
          catch ( SocketException e )
            {
                System.out.println( "ERROR: " + e );
            }

        }
    
    /**
     * 
     * @param sendPacket
     * @return
     *
     * @since 1.0
     */
    public boolean sendPacket(DatagramPacket sendPacket) {
    
          try
            {
            this.serverSocket.send( sendPacket );
            return true;
            }
          catch ( IOException e )
            {
            return false;
            }
        }
    
    public void recvPacket() {
    
    byte[] receiveData = new byte[1024];
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    try
        {
        serverSocket.receive(receivePacket);
        }
    catch ( IOException e )
        {
        System.out.println("ERROR: " + e);
        }
    
    }
    

    }
   // end class UDPServer