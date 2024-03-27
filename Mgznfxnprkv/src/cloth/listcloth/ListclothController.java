/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloth.listcloth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import jptv22clothingstore.JPTV22СlothingStore;

/**
 * FXML Controller class
 *
 * @author Oleksandr
 */
public class ListclothController implements Initializable {
    private JPTV22СlothingStore app;
    @FXML
    private HBox hblistclothsRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public JPTV22СlothingStore getApp() {
        return app;
    }

    public void setApp(JPTV22СlothingStore app) {
        this.app = app;
    }
    
}
