package sample;

/***
 * LBYCPA2 TERM 3 PROJECT A.Y. 2020-2021
 * Date created: January 29, 2021
 * Date last modified: February 2, 2021
 * Developed by: Ivan Porcincula, Yuan Dumandan, Kenji Yoro
 */


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;

public class loginController {
    private double x;
    private double y;

    public Button login;

    public Button exit;

    public Button register;

    public TextField username;
    public TextField password;

    public void initialize(){
        login.setDisable(true);

        exit.setOnMouseClicked(e->{
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

        register.setOnMouseClicked(e->{
            FXMLLoader main = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = null;
            try {

                root = main.load();
                Stage stage = (Stage) register.getScene().getWindow();
                root.setOnMousePressed(d->{
                    x = d.getSceneX();
                    y = d.getSceneY();
                });

                root.setOnMouseDragged(d->{
                    stage.setX(d.getScreenX()-x);
                    stage.setY(d.getScreenY()-y);
                });
                stage.setTitle("Register");
                stage.setScene(new Scene(root));

                stage.show();
            } catch (IOException d) {
                d.printStackTrace();
            }


        });
    }

    public void infoFilled(){
        login.setDisable(username.getText().isEmpty() || password.getText().isEmpty());
    }

    public void login(){

        String user = username.getText();
        String pass = password.getText();

        String fileName = "assets/"+user+"linkedlist.dat";

        File database = new File(fileName);

        if(database.exists()){

            FileInputStream readFile = null;
            try {
                readFile = new FileInputStream(fileName);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            ObjectInputStream readObject;
            try {
                readObject = new ObjectInputStream(readFile);
                LinkedList<String> information = (LinkedList<String>) readObject.readObject();

                if(!pass.equals(information.get(1))){
                    //TODO: PROMPT THAT THE PASSWORD IS INCORRECT
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("errorLogin.fxml"));
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Greened");
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    username.setText("");
                    password.setText("");

                }
                else{

                    FileWriter recentLogin = new FileWriter("assets/tmp.dat");
                    recentLogin.write(user);
                    recentLogin.close();

                    FXMLLoader main = new FXMLLoader(getClass().getResource("Greened.fxml"));
                    Parent root;
                    try {
                        root = main.load();
                        Stage stage = (Stage) login.getScene().getWindow();
                        root.setOnMousePressed(e->{
                            x = e.getSceneX();
                            y = e.getSceneY();
                        });

                        root.setOnMouseDragged(e->{
                            stage.setX(e.getScreenX()-x);
                            stage.setY(e.getScreenY()-y);
                        });
                        stage.setTitle("Greened");
                        stage.setScene(new Scene(root));

                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }



        }
        else{
            //TODO: PROMPT THAT THE ACCOUNT DOES NOT EXIST
            try {
                Parent root = FXMLLoader.load(getClass().getResource("errorLogin.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Greened");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            username.setText("");
            password.setText("");


        }






    }



}
