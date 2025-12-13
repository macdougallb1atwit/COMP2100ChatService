package application ;

import javafx.application.Application ;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Parent ;
import javafx.scene.Scene ;
import javafx.stage.Stage ;

/**
 * 
 * @author Ben
 *
 * @version 1.0 2025-12-12 Initial implementation
 *
 *
 * @since 1.0
 */
public class Main extends Application
    {

    private Stage primaryStage ;
    private TCPClient client ;
    private String clientID ;


    @Override
    public void start( final Stage inputStage ) throws Exception
        {

        this.primaryStage = inputStage ;
        showLoginScreen() ;

        }


    /**
     * @throws Exception if the FXML loader fails to load the file.
     *
     * @since 1.0
     */
    public void showLoginScreen() throws Exception
        {

        final FXMLLoader loader = new FXMLLoader( getClass().getResource( "Login.fxml" ) ) ;
        final Parent root = loader.load() ;
        final LoginController controller = loader.getController() ;
        controller.setMain( this ) ;

        this.primaryStage.setTitle( "Login" ) ;
        this.primaryStage.setScene( new Scene( root ) ) ;
        this.primaryStage.show() ;

        }


    /**
     * @throws Exception if the FXML loader fails to load the file.
     *
     * @since 1.0
     */
    public void showMessageScreen() throws Exception
        {

        final FXMLLoader loader = new FXMLLoader( getClass().getResource( "Message.fxml" ) ) ;
        final Parent root = loader.load() ;
        final MessageController controller = loader.getController() ;
        controller.setClient( this.client ) ;

        this.primaryStage.setTitle( "Messages" ) ;
        this.primaryStage.setScene( new Scene( root ) ) ;
        this.primaryStage.show() ;

        }


    /**
     * @param id the id to pass into the TCPClient class.
     * @param host the name to pass into the TCPClient class.
     * @param port the port to be used in the TCPClient class.
     *
     * @throws Exception if the TCPClient fails to initialize the readers/writers.
     *
     * @since 1.0
     */
    public void createClient( final String id,
                              final String host,
                              final int port )
        throws Exception
        {

        this.clientID = id ;
        this.client = new TCPClient( id, host, port ) ;

        }


    /**
     * @return the client ID.
     *
     * @since 1.0
     */
    public String getClientID()
        {

        return this.clientID ;

        }


    /**
     * @return the TCPCLient object.
     *
     * @since 1.0
     */
    public TCPClient getClient()
        {

        return this.client ;

        }


    /**
     * @param args unused.
     *
     * @since 1.0
     */
    public static void main( final String[] args )
        {

        launch( args ) ;

        }

    }
