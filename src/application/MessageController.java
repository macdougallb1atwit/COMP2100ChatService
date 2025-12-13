
package application ;

import javafx.application.Platform ;
import javafx.fxml.FXML ;
import javafx.scene.control.Button ;
import javafx.scene.control.TextField ;
import javafx.scene.layout.VBox ;
import javafx.scene.text.Text ;


/**
 * 
 * This handles the GUI part of the chat service. It isn't great, and it looks terrible but 
 * I have been bug fixing for hours and if I touch something it will implode, I already tested it.
 * 
 * @author Ben, Sean
 *
 * @version 1.0 2025-12-12 Initial implementation
 *
 *
 * @since 1.0
 */
public class MessageController
    {

    private TCPClient tcpClient ;
    private String recipientID ;

    @FXML
    private VBox messageVBox ;

    @FXML
    private TextField messageField ;

    @FXML
    private TextField recipientField ;

    @FXML
    private Button sendButton ;

    @FXML
    private Button actionButton ;


    /**
     * 
     * @param client passed in as a reference to the TCPCLient object.
     *
     * @since 1.0
     */
    public void setClient( final TCPClient client )
        {

        this.tcpClient = client ;


        this.tcpClient.setMessageListener( msg -> Platform.runLater( () -> handleIncomingMessage( msg ) ) ) ;

        }


    @FXML
    private void loadAndSetRecipient()
        {

        this.recipientID = this.recipientField.getText().trim() ;
        this.messageVBox.getChildren().clear() ;

        if ( ( this.tcpClient == null ) || this.recipientID.isEmpty() )
            {
            return ;
            }

        try
            {
            this.tcpClient.messageRequest( this.recipientID ) ;
            }
        catch ( final Exception e )
            {
            e.printStackTrace() ;
            }

        }


    @FXML
    private void messageSend()
        {

        if ( ( this.tcpClient == null ) || ( this.recipientID == null ) ||
             this.recipientID.isEmpty() )
            {
            return ;
            }

        final String msg = this.messageField.getText().trim() ;

        if ( msg.isEmpty() )
            {
            return ;
            }

        this.tcpClient.sendMessage( this.recipientID, msg ) ;
        addMessage( "Me", msg ) ;
        this.messageField.clear() ;

        }


    private void handleIncomingMessage( final String msg )
        {

        if ( msg.contains( "->" ) )
            {
            final String[] parsed = parseHistoryMessage( msg ) ;
            final String sender = parsed[ 0 ] ;
            final String message = parsed[ 1 ] ;

            addMessage( sender.equals( this.tcpClient.getClientID() )
                ? "Me"
                : sender, message ) ;
            return ;
            }

        final int sep = msg.indexOf( "::" ) ;

        if ( sep != -1 )
            {
            final String sender = msg.substring( 0, sep ) ;
            final String message = msg.substring( sep + 2 ) ;

            addMessage( sender.equals( this.tcpClient.getClientID() )
                ? "Me"
                : sender, message ) ;
            }

        }


    private void addMessage( final String sender,
                             final String message )
        {

        this.messageVBox.getChildren()
                        .add( new Text( sender + ": " + message ) ) ;

        }


    private static String[] parseHistoryMessage( final String line )
        {

        final int arrow = line.indexOf( "->" ) ;
        final int colon = line.indexOf( ":", arrow ) ;

        if ( ( arrow != -1 ) && ( colon != -1 ) )
            {
            final String sender = line.substring( line.indexOf( ']' ) + 1,
                                                  arrow )
                                      .trim() ;
            final String message = line.substring( colon + 1 ).trim() ;
            return new String[] { sender, message } ;
            }

        return new String[] { "", line } ;

        }

    }
