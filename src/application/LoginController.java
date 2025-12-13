package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * 
 * @author Ben, Sean
 *
 * @version 1.0 2025-12-12 Initial implementation
 *
 *
 * @since 1.0
 */
public class LoginController {

    /**  */
    Main main;

    @FXML
    private Button send;

    @FXML
    private TextField userMessage;

    @FXML
    private void clientIDSend() {
        String id = this.userMessage.getText();
        this.userMessage.clear();

        try {
            this.main.createClient(id, "localhost", 9001);
            this.main.showMessageScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param main1 the passed in reference
     *
     * @since 1.0
     */
    public void setMain(Main main1) {
        this.main = main1;
    }
}
