/***
 * LBYCPA2 TERM 3 PROJECT A.Y. 2020-2021
 * Date created: January 29, 2021
 * Date last modified: February 2, 2021
 * Developed by: Ivan Porcincula, Yuan Dumandan, Kenji Yoro
 */


package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class registerController {

    private double x;
    private double y;


    public Button exit;
    public Button register;

    public TextField username;
    public TextField password;
    public TextField name;
    public TextField contactNumber;
    public Button back;



    private  HashMap<String, LinkedList<String>> updateDatabase;
    public Text success;




    public void initialize(){

        register.setDisable(true);


        exit.setOnMouseClicked(e->{
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

        register.setOnMouseClicked(e->{
            LinkedList<String> userInformation = new LinkedList<>();
            String user = username.getText();
            String pass = password.getText();
            String Name = name.getText();
            String num = contactNumber.getText();

            String filename = "assets/allUserDatabase.dat";
            File file = new File(filename);

            String fileName = "assets/"+user+"linkedlist.dat";
            File database = new File(fileName);

            HashMap<String, LinkedList<String>> userInfo  = new HashMap<>();
            LinkedList<String> nodes1 = new LinkedList<>();
            LinkedList<String> nodes2 = new LinkedList<>();
            LinkedList<String> nodes3 = new LinkedList<>();
            nodes1.add(num);
            nodes1.add(user);
            nodes2.add(Name);
            nodes2.add(user);
            nodes3.add(Name);
            nodes3.add(num);

            userInformation.add(user);
            userInformation.add(pass);
            userInformation.add(Name);
            userInformation.add(num);



            if(!database.exists()){
                FileOutputStream writeToFile = null;
                try {
                    writeToFile = new FileOutputStream(fileName);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                try {
                    ObjectOutputStream out = new ObjectOutputStream(writeToFile);
                    out.writeObject(userInformation);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                username.setText("");
                password.setText("");
                name.setText("");
                contactNumber.setText("");



                if (!file.exists()){
                    userInfo.put(Name,nodes1);
                    userInfo.put(num,nodes2);
                    userInfo.put(user,nodes3);

                    FileOutputStream fout = null;
                    try {
                        fout = new FileOutputStream(filename);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    ObjectOutputStream out;
                    try {
                        out = new ObjectOutputStream(fout);
                        out.writeObject(userInfo);
                        System.out.println(userInfo);
                        assert fout != null;
                        fout.close();
                        out.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    success.setText("Registered Successfully");


                }

                else{
                    FileInputStream fin = null;
                    try {
                        fin = new FileInputStream(filename);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    try {
                        ObjectInputStream in = new ObjectInputStream(fin);
                        updateDatabase = (HashMap<String, LinkedList<String>>) in.readObject();
                        updateDatabase.put(Name,nodes1);
                        updateDatabase.put(num,nodes2);
                        updateDatabase.put(user,nodes3);


                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }

                    FileOutputStream fout = null;
                    try {
                        fout = new FileOutputStream(filename);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(fout);
                        out.writeObject(updateDatabase);
                        System.out.println(updateDatabase);

                        fout.close();
                        out.close();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    success.setText("Registered Successfully");
                }

            }


            else{
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("errorRegister.fxml"));
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Greened");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                username.setText("");
                password.setText("");
                name.setText("");
                contactNumber.setText("");
            }
        });



    }
    public void infoFilled(){
        if(!username.getText().isEmpty() && !password.getText().isEmpty() && !name.getText().isEmpty() && !contactNumber.getText().isEmpty()) register.setDisable(false);
        else register.setDisable(true);
    }

    public void back(){
        FXMLLoader main = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root;
        try {
            root = main.load();

            Stage stage = (Stage) back.getScene().getWindow();
            root.setOnMousePressed(e->{
                x = e.getSceneX();
                y = e.getSceneY();
            });

            root.setOnMouseDragged(e->{
                stage.setX(e.getScreenX()-x);
                stage.setY(e.getScreenY()-y);
            });
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}
