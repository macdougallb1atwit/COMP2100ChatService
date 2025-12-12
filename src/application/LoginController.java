
package application ;

import javafx.event.ActionEvent ;
import javafx.fxml.FXML ;
import javafx.scene.control.Button ;
import javafx.scene.control.TextField ;

/**
 * 
 * @author Ben
 *
 * @version 1.0 2025-11-24 Initial implementation
 *
 *
 * @since 1.0
 */
public class LoginController
    {
    
    Main main;
    

    @FXML
    private Button send ;

    @FXML
    private TextField userMessage;

    @FXML
    private void cliendIDSend( ActionEvent event )
        {

        String userTyped = this.userMessage.getText() ;
        this.userMessage.clear() ;
        
        try
            {
            this.main.showMessageScreen();
            }
        catch ( Exception e )
            {
            e.printStackTrace() ;
            }
        
        

        }

    /**
     * 
     * @param main
     *
     * @since 1.0
     */
    public void setMain( Main main )
        {

        this.main = main;

        }


    }
