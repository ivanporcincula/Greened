/***
 * LBYCPA2 TERM 3 PROJECT A.Y. 2020-2021
 * Date created: January 29, 2021
 * Date last modified: February 3, 2021
 * Developed by: Ivan Porcincula, Yuan Dumandan, Kenji Yoro
 */

package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class GreenedMainRun extends Application {
    private double x;
    private double y;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        root.setOnMousePressed(e->{
            x = e.getSceneX();
            y = e.getSceneY();
        });

        root.setOnMouseDragged(e->{
            primaryStage.setX(e.getScreenX()-x);
            primaryStage.setY(e.getScreenY()-y);
        });
        primaryStage.setTitle("Greened");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
