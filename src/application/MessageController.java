
package application ;

import javafx.event.ActionEvent ;
import javafx.fxml.FXML ;
import javafx.scene.control.Button ;
import javafx.scene.control.TextArea ;
import javafx.scene.control.TextField ;

public class MessageController
    {

    @FXML
    private Button send ;

    @FXML
    private TextField userMessage;

    @FXML
    private void messageSend( ActionEvent event )
        {

        String userTyped = this.userMessage.getText() ;
        this.userMessage.clear() ;
        System.out.print(userTyped);
        
        

        }


    }
