package application;

import javafx.application.Application ;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Parent ;
import javafx.scene.Scene ;
import javafx.stage.Stage ;

/**
 * 
 * @author Ben
 *
 * @version 1.0 2025-11-24 Initial implementation
 *
 *
 * @since 1.0
 */
public class Main extends Application
    {

    private Stage primaryStage ;
    private String clientID;


    @Override
    public void start( Stage inputStage ) throws Exception
        {

        this.primaryStage = inputStage ;
        showLoginScreen() ;
        
        }


    /**
     * 
     * @throws Exception throws if the loader fails to load the fxml file.
     *
     * @since 1.0
     */
    public void showLoginScreen() throws Exception
        {

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "Login.fxml" ) ) ;
        Parent root = loader.load() ;

        LoginController controller = loader.getController() ;
        controller.setMain( this ) ;

        this.primaryStage.setTitle( "Login" ) ;
        this.primaryStage.setScene( new Scene( root ) ) ;
        this.primaryStage.show() ;

        }


    /**
     * 
     * @throws Exception throws if loader fails to load the fxml file.
     *
     * @since 1.0
     */
    public void showMessageScreen() throws Exception
        {

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "Message.fxml" ) ) ;
        Parent root = loader.load() ;

        MessageController controller = loader.getController() ;
        controller.setMain( this ) ;

        this.primaryStage.setTitle( "Messages" ) ;
        this.primaryStage.setScene( new Scene( root ) ) ;
        this.primaryStage.show() ;

        }
    
    /**
     * 
     * @return the clientID.
     *
     * @since 1.0
     */
    public String getClientID()
        {
            return this.clientID;
        }
    /**
     * 
     * @param id String to set the clientID to.
     *
     * @since 1.0
     */
    public void setClientID(String id)
        {
            this.clientID = id;
        }

    /**
     * 
     * @param args no input at the moment.
     *
     * @since 1.0
     */
    public static void main( String[] args )
        {

        launch( args ) ;

        }

    } //end Main class
