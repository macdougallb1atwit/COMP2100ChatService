
package application ;

import java.io.BufferedReader ;
import java.io.BufferedWriter ;
import java.io.IOException ;
import java.io.InputStreamReader ;
import java.io.OutputStreamWriter ;
import java.net.Socket ;
import java.util.concurrent.ConcurrentLinkedQueue ;

/**
 * 
 * @author Ben
 *
 * @version 1.0 2025-12-12 Initial implementation
 *
 *
 * @since 1.0
 */
public class TCPClient implements Runnable
    {

    private final String clientID ;
    private final Socket clientSocket ;
    private final BufferedReader in ;
    private final BufferedWriter out ;
    private final Thread thread ;
    private volatile boolean running = true ;
    private volatile boolean req = false ;
    private final ConcurrentLinkedQueue<String> messageQueue ;
    private final ConcurrentLinkedQueue<String> messageConfirmation ;
    private MessageListener messageListener ;


    /**
     * 
     * Allows the GUI to be updated asynchronously when a new message arrives.
     * 
     * 
     * @author Ben
     *
     * @version 1.0 2025-12-12 Initial implementation
     *
     *
     * @since 1.0
     */
    public interface MessageListener
        {

        /**
         * 
         * @param message from the sender.
         *
         * @since 1.0
         */
        void onMessage( String message ) ;

        }


    /**
     * @param id is this clients id.
     * @param hostIP (server)
     * @param port (server port)
     * @throws IOException if the IO fails.
     *
     * @since 1.0
     */
    public TCPClient( final String id,
                      final String hostIP,
                      final int port ) throws IOException
        {

        this.clientID = id ;
        this.clientSocket = new Socket( hostIP, port ) ;
        this.in = new BufferedReader( new InputStreamReader( this.clientSocket.getInputStream() ) ) ;
        this.out = new BufferedWriter( new OutputStreamWriter( this.clientSocket.getOutputStream() ) ) ;
        this.messageQueue = new ConcurrentLinkedQueue<>() ;
        this.messageConfirmation = new ConcurrentLinkedQueue<>() ;
        this.thread = new Thread( this ) ;
        this.thread.start() ;

        }


    @Override
    public void run()
        {

        try
            {

            while ( this.running )
                {
                final String sender = this.in.readLine() ;

                if ( sender == null )
                    {
                    break ;
                    }

                final String receiver = this.in.readLine() ;
                final String message = this.in.readLine() ;

                if ( ( receiver == null ) || ( message == null ) )
                    {
                    break ;
                    }

                if ( sender.endsWith( "REQ" ) )
                    {
                    String line ;

                    while ( true )
                        {
                        line = this.in.readLine() ;

                        if ( line == null )
                            {
                            this.running = false ;
                            break ;
                            }

                        if ( "REQ".equals( line ) )
                            {
                            break ;
                            }

                        this.messageQueue.add( line ) ;

                        if ( this.messageListener != null )
                            {
                            this.messageListener.onMessage( line ) ;
                            }

                        }

                    this.req = false ;
                    continue ;
                    }

                if ( receiver.equals( this.clientID ) )
                    {
                    final String formatted = sender + "::" + message ;
                    this.messageQueue.add( formatted ) ;

                    if ( this.messageListener != null )
                        {
                        this.messageListener.onMessage( formatted ) ;
                        }

                    }

                if ( sender.equals( this.clientID ) )
                    {
                    this.messageConfirmation.remove( message ) ;
                    }

                }

            }
        catch ( final IOException e )
            {
            System.err.println( "[CLIENT " + this.clientID +
                                "] Exception in reader thread: " +
                                e.getMessage() ) ;
            e.printStackTrace() ;
            }

        }


    /**
     * 
     * @param receiverID is the ID of the receiver.
     * @param message the message being sent.
     *
     * @since 1.0
     */
    public void sendMessage( final String receiverID,
                             final String message )
        {

        try
            {
            this.out.write( this.clientID + "\n" ) ;
            this.out.write( receiverID + "\n" ) ;
            this.out.write( message + "\n" ) ;
            this.out.flush() ;
            this.messageConfirmation.add( message ) ;
            }
        catch ( final IOException e )
            {
            e.printStackTrace() ;
            }

        }


    /**
     * 
     * @param recipientID is the id which the server will look for along with the clientID.
     * @throws IOException if the IO fails.
     *
     * @since 1.0
     */
    public void messageRequest( final String recipientID ) throws IOException
        {

        this.req = true ;
        this.out.write( this.clientID + " REQ\n" ) ;
        this.out.write( recipientID + "\n" ) ;
        this.out.flush() ;

        while ( this.req )
            {
            // busy wait until history is processed
            }

        }

    /**
     * 
     * @param message the message that is being tested.
     * @return true if the queue does not contain the message.
     *
     * @since 1.0
     */
    public boolean confirmed( final String message )
        {

        return !this.messageConfirmation.contains( message ) ;

        }


    /**
     * 
     * @param listener is the passed in reference.
     *
     * @since 1.0
     */
    public void setMessageListener( final MessageListener listener )
        {

        this.messageListener = listener ;

        }


    /**
     * 
     * @return the client ID.
     *
     * @since 1.0
     */
    public String getClientID()
        {

        return this.clientID ;

        }


    /**
     * Stops the client cleanly, currently unused.
     *
     * @since 1.0
     */
    public void stop()
        {

        this.running = false ;

        try
            {
            this.clientSocket.close() ;
            }
        catch ( final IOException e )
            {
            e.printStackTrace() ;
            }

        }

    }
