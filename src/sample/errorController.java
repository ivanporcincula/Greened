package sample;

import javafx.scene.control.Button;
import javafx.stage.Stage;


public class errorController {

    public Button ok;
    public void ok(){
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

}
