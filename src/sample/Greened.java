/***
 * LBYCPA2 TERM 3 PROJECT A.Y. 2020-2021
 * Date created: January 29, 2021
 * Date last modified: February 3, 2021
 * Developed by: Ivan Porcincula, Yuan Dumandan, Kenji Yoro
 */



package sample;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Greened implements Initializable {

    //buttons for the sliding menu
    public ImageView Menu;
    public ImageView closeMenu;
    public Button exit;
    public VBox menu;


    //anchor pane to group the nodes
    public AnchorPane profile;
    public AnchorPane contacts;
    public AnchorPane newContacts;
    public AnchorPane newMessage;
    public AnchorPane readMessages;
    public AnchorPane unreadMessages;

    //profile
    public Text name;
    public Text number;
    public Text username;
    public TextField directory;
    public Button addPhoto;
    public Image picture;
    public ImageView setPicture;

    //contacts
    public ListView<String> contactList;

    //new contact

    public TextField inputName;
    public TextField inputNumber;
    public TextField inputUser;


    //new message

    public TextField contactInput;
    public TextArea bodyMessage;


    // unread
    public Text unreadMessage;

    // read
    public Text readMess;
    public Queue<String> read = new LinkedList<>();

    public HashMap<String, LinkedList<String>> userDatabase;

    //search
    public TextField searchName;
    public Text exists;
    public Button addContact;

    //edit
    public Button editName;
    public Button editNumber;
    public Button doneName;
    public Button doneNumber;
    public TextField newName;
    public TextField newNumber;
    public String previousName;
    public String previousNumber;



    //database of the current user
    private String user;
    private String fullName;
    private String num;
    private final Graph graph = new Graph();
    private double x;
    private double y;

    public Button logout;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        File tmp = new File("assets/tmp.dat");

        if(tmp.exists()){
            try{
                Scanner sc = new Scanner(tmp);
                while(sc.hasNext()){
                    user = sc.next();
                }
                sc.close();

            }catch(IOException e){
                e.printStackTrace();
            }

            tmp.delete();
        }

        String fileName = "assets/"+user+"linkedlist.dat";

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

            File profileData = new File("assets/"+user+"profile.dat");

            if(profileData.exists()){
                Scanner sc = new Scanner(profileData);
                String photoDirectory = sc.next();
                sc.close();
                picture = new Image(photoDirectory);
                setPicture.setImage(picture);
            }

            name.setText(information.get(2));
            number.setText(information.get(3));
            username.setText(information.get(0));

            fullName = information.get(2);
            num = information.get(3);


        }
        catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }


        menu.setTranslateX(-200);

        Menu.setVisible(true);
        closeMenu.setVisible(false);

        exit.setOnMouseClicked(e->{
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });


        Menu.setOnMouseClicked(e->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(menu);
            slide.setToX(0);

            slide.play();

            menu.setTranslateX(-200);

            slide.setOnFinished((ActionEvent d)->{
                Menu.setVisible(false);
                closeMenu.setVisible(true);
            });


        });

        closeMenu.setOnMouseClicked(e->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(menu);
            slide.setToX(-200);

            slide.play();

            menu.setTranslateX(0);

            slide.setOnFinished((ActionEvent d)->{
                Menu.setVisible(true);
                closeMenu.setVisible(false);
            });



        });

        String nameFile = "assets/"+user+"graph.dat";
        File data = new File(nameFile);

        if(data.exists()){

            FileInputStream fin = null;
            try {
                fin = new FileInputStream(nameFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(fin);
                HashMap<String, LinkedList<String>> updateGraph = (HashMap<String, LinkedList<String>>) in.readObject();
                fin.close();
                in.close();


                for(String s : updateGraph.get(fullName)){
                    graph.addEdge(fullName,s);
                }

                for(String s : updateGraph.get(num)){
                    graph.addEdge(num,s);
                }

                for(String s : updateGraph.get(user)){
                    graph.addEdge(user,s);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            contactList.getItems().clear();
            if(graph.connection.get(fullName)!=null){
                for(String person : graph.connection.get(fullName)) contactList.getItems().add(person);
            }

        }

        graph.connection.clear();

        doneName.setDisable(true);
        doneNumber.setDisable(true);


    }




    // menu button handlers

    public void profileButtonHandler() throws FileNotFoundException {

        this.name.setText(fullName);
        this.number.setText(num);
        this.username.setText(user);

        File profileData = new File("assets/"+user+"profile.dat");

        if(profileData.exists()){
            Scanner sc = new Scanner(profileData);
            String photoDirectory = sc.next();
            sc.close();
            picture = new Image(photoDirectory);
            setPicture.setImage(picture);
        }

        else{
            picture = new Image("sample/Profile/default.jpg");
            setPicture.setImage(picture);

        }

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);
        slide.setToX(-200);

        slide.play();

        menu.setTranslateX(0);

        Menu.setVisible(true);
        closeMenu.setVisible(false);
        profile.setVisible(true);
        directory.setVisible(true);
        addPhoto.setVisible(true);
        addContact.setVisible(false);
        contacts.setVisible(false);
        newContacts.setVisible(false);
        newMessage.setVisible(false);
        unreadMessages.setVisible(false);
        readMessages.setVisible(false);
        editName.setVisible(false);
        editNumber.setVisible(false);
        doneName.setVisible(false);
        doneNumber.setVisible(false);
        newName.setVisible(false);
        newNumber.setVisible(false);



    }

    //profile button handler

    public void setProfilePhoto() throws FileNotFoundException {
        String path = directory.getText();

        PrintWriter direct = new PrintWriter("assets/"+user+"profile.dat");
        direct.write(path);
        direct.close();

        picture = new Image(path);
        setPicture.setImage(picture);

        directory.setText("");

    }

    //to go the contacts menu
    public void contactsButtonHandler(){

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);
        slide.setToX(-200);

        slide.play();

        menu.setTranslateX(0);

        Menu.setVisible(true);
        closeMenu.setVisible(false);
        profile.setVisible(false);
        contacts.setVisible(true);
        newContacts.setVisible(false);
        newMessage.setVisible(false);
        unreadMessages.setVisible(false);
        readMessages.setVisible(false);

    }

    //to create a new message

    public void newMessageButtonHandler(){

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);
        slide.setToX(-200);

        slide.play();

        menu.setTranslateX(0);

        Menu.setVisible(true);
        closeMenu.setVisible(false);
        profile.setVisible(false);
        contacts.setVisible(false);
        newContacts.setVisible(false);
        newMessage.setVisible(true);
        unreadMessages.setVisible(false);
        readMessages.setVisible(false);

    }

    //new message button handler

    public void sendMessage() throws IOException, ClassNotFoundException {
        String contact = contactInput.getText();
        String message = bodyMessage.getText();
        Stack<String> incomingMessage = new Stack<>();

        String whole;


        String filename = "assets/allUserDatabase.dat";
        File database = new File(filename);


        whole = "From: " + fullName +"\n\n" +message;


        if(database.exists()) {


            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fin);
            userDatabase = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();

            String username;
            if(userDatabase.containsKey(contact)){
                if(contact.equals(userDatabase.get(userDatabase.get(contact).get(0)).get(1))) username = contact;
                else username = userDatabase.get(contact).get(1);

                String nameFile = "assets/"+username+"newmessages.dat";
                File dat = new File(nameFile);

                if(!dat.exists()){
                    incomingMessage.push(whole);
                    FileOutputStream fout = new FileOutputStream(nameFile);
                    ObjectOutputStream out = new ObjectOutputStream(fout);
                    out.writeObject(incomingMessage);
                    fout.close();
                    out.close();
                    contactInput.setText("");
                    bodyMessage.setText("");

                }

                else{
                    FileInputStream fin2 = new FileInputStream(nameFile);
                    ObjectInputStream in2 = new ObjectInputStream(fin2);
                    Stack<String> updateStack= (Stack<String>) in2.readObject();
                    fin2.close();
                    in2.close();

                    for(String s : updateStack) incomingMessage.push(s);
                    incomingMessage.push(whole);

                    contactInput.setText("");
                    bodyMessage.setText("");

                    FileOutputStream fout = new FileOutputStream(nameFile);
                    ObjectOutputStream out = new ObjectOutputStream(fout);
                    out.writeObject(incomingMessage);
                    fout.close();
                    out.close();


                }
                incomingMessage.clear();

            }

            else{

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("errorMessage.fxml"));
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Greened");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                contactInput.setText("");
            }

        }

    }



    //for new messages
    public void unreadMessagesButtonHandler() throws IOException, ClassNotFoundException {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);
        slide.setToX(-200);

        slide.play();

        menu.setTranslateX(0);

        Menu.setVisible(true);
        unreadMessages.setVisible(true);
        readMessages.setVisible(false);
        closeMenu.setVisible(false);
        profile.setVisible(false);
        contacts.setVisible(false);
        newContacts.setVisible(false);
        newMessage.setVisible(false);

        String nameFile = "assets/"+user+"newmessages.dat";
        File fp1 = new File(nameFile);

        if(fp1.exists()){
            FileInputStream fin1 = new FileInputStream(nameFile);
            ObjectInputStream in1 = new ObjectInputStream(fin1);
            Stack<String> updateStack= (Stack<String>) in1.readObject();
            fin1.close();
            in1.close();


            String str = updateStack.pop();
            unreadMessage.setText(str);
            read.add(str);

            FileOutputStream fout1 = new FileOutputStream(nameFile);
            ObjectOutputStream out1 = new ObjectOutputStream(fout1);
            out1.writeObject(updateStack);

            fout1.close();
            out1.close();

            String path = "assets/"+user+"oldmessages.dat";
            File fp2 = new File(path);

            if(!fp2.exists()){
                FileOutputStream fout2 = new FileOutputStream(path);
                ObjectOutputStream out2 = new ObjectOutputStream(fout2);
                out2.writeObject(read);
                fout2.close();
                out2.close();

            }
            else{
                FileInputStream fin = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fin);
                Queue<String> updateQueue = (LinkedList<String>) in.readObject();
                fin.close();
                in.close();

                updateQueue.add(str);

                FileOutputStream fout2 = new FileOutputStream(path);
                ObjectOutputStream out2 = new ObjectOutputStream(fout2);
                out2.writeObject(updateQueue);
                fout2.close();
                out2.close();

            }

        }

        else unreadMessage.setText("No new messages for now!");




    }


    public void nextBtnHandler() throws IOException, ClassNotFoundException {


        String nameFile = "assets/"+user+"newmessages.dat";
        String path = "assets/"+user+"oldmessages.dat";
        FileInputStream fin2 = new FileInputStream(nameFile);
        ObjectInputStream in2 = new ObjectInputStream(fin2);
        Stack<String> updateStack= (Stack<String>) in2.readObject();
        fin2.close();
        in2.close();



        if(!updateStack.isEmpty()){

            String str = updateStack.pop();
            unreadMessage.setText(str);
            read.add(str);

            FileOutputStream fout = new FileOutputStream(nameFile);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(updateStack);

            fout.close();
            out.close();


            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);
            Queue<String> updateQueue = (LinkedList<String>) in.readObject();
            fin.close();
            in.close();

            updateQueue.add(str);

            FileOutputStream fout2 = new FileOutputStream(path);
            ObjectOutputStream out2 = new ObjectOutputStream(fout2);
            out2.writeObject(updateQueue);
            fout2.close();
            out2.close();

        }


        else{
            unreadMessage.setText("No new messages for now!");
           try
            {
                Files.deleteIfExists(Paths.get("assets/"+user+"newmessages.dat"));
            } catch(IOException e)
            {
                e.printStackTrace();
            }

        }

    }

    //
    public void readMessagesButtonHandler() throws IOException, ClassNotFoundException {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);
        slide.setToX(-200);


        slide.play();

        menu.setTranslateX(0);

        Menu.setVisible(true);
        readMessages.setVisible(true);
        unreadMessages.setVisible(false);
        closeMenu.setVisible(false);
        profile.setVisible(false);
        contacts.setVisible(false);
        newContacts.setVisible(false);
        newMessage.setVisible(false);

        String nameFile = "assets/"+user+"oldmessages.dat";
        File dat = new File(nameFile);

        if(dat.exists()){
            FileInputStream fin2 = new FileInputStream(nameFile);
            ObjectInputStream in2 = new ObjectInputStream(fin2);
            Queue<String> updateQueue= (LinkedList<String>) in2.readObject();
            fin2.close();
            in2.close();

            read.addAll(updateQueue);

            String str = read.remove();
            readMess.setText(str);

        }

        else readMess.setText("No messages.");

    }

    //next button for reading messages
    public void readNxtBtnHandler() throws IOException, ClassNotFoundException {
        if(!read.isEmpty()){
            String str = read.remove();
            readMess.setText(str);
        }

        else{
            String nameFile = "assets/"+user+"oldmessages.dat";
            File dat = new File(nameFile);

            if(dat.exists()){
                FileInputStream fin2 = new FileInputStream(nameFile);
                ObjectInputStream in2 = new ObjectInputStream(fin2);
                Queue<String> updateQueue= (LinkedList<String>) in2.readObject();
                fin2.close();
                in2.close();

                read.addAll(updateQueue);

                String str = read.remove();
                readMess.setText(str);

            }
        }



    }



    //to logout
    public void logoutButtonHandler(){

        FXMLLoader main = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root;
        try {
            root = main.load();

            Stage stage = (Stage) logout.getScene().getWindow();
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



    //contact button handler
    public void addNewContactHandler(){

        profile.setVisible(false);
        contacts.setVisible(false);
        newContacts.setVisible(true);
        newMessage.setVisible(false);
        unreadMessages.setVisible(false);
        readMessages.setVisible(false);

    }

    //search user

    public void searchHandler() throws IOException, ClassNotFoundException {

        String contact = searchName.getText();
        String filename = "assets/allUserDatabase.dat";
        File data = new File(filename);
        String username;

        editName.setVisible(false);
        editNumber.setVisible(false);
        doneName.setVisible(false);
        doneNumber.setVisible(false);
        newName.setVisible(false);
        newNumber.setVisible(false);

        if(data.exists()) {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> database = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();

            if (database.containsKey(contact)){
                if(contact.equals(database.get(database.get(contact).get(0)).get(1))) {
                    username = contact;
                    this.name.setText(database.get(username).get(0));
                    this.number.setText(database.get(username).get(1));
                    this.username.setText(username);
                }

                else {
                    username = database.get(contact).get(1);
                    if(contact.matches("\\d+")){
                        this.name.setText(database.get(contact).get(0));
                        this.number.setText(contact);
                        this.username.setText(database.get(contact).get(1));
                    }
                    else{
                        this.name.setText(contact);
                        this.number.setText(database.get(contact).get(0));
                        this.username.setText(database.get(contact).get(1));

                    }
                }

                File profileData = new File("assets/"+username+"profile.dat");

                if(profileData.exists()){
                    Scanner sc = new Scanner(profileData);
                    String photoDirectory = sc.next();
                    sc.close();
                    picture = new Image(photoDirectory);
                    setPicture.setImage(picture);
                }
                else{
                    picture = new Image("sample/Profile/default.jpg");
                    setPicture.setImage(picture);
                }

                profile.setVisible(true);
                contacts.setVisible(false);
                newContacts.setVisible(false);
                newMessage.setVisible(false);
                directory.setVisible(false);
                addPhoto.setVisible(false);
                unreadMessages.setVisible(false);
                readMessages.setVisible(false);
                addContact.setVisible(true);
                exists.setText("");


            }
            else exists.setText("This user does not exist.");

            searchName.setText("");

        }

    }


    //to view the selected contact from the contact list
    public void viewHandler() throws IOException, ClassNotFoundException {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);
        slide.setToX(-200);

        slide.play();

        menu.setTranslateX(0);

        Menu.setVisible(true);
        closeMenu.setVisible(false);

        String fileName = "assets/"+user+"graph.dat";
        File data = new File(fileName);
        String name = contactList.getSelectionModel().getSelectedItem();

        if(data.exists()) {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> updateGraph = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();

            for(String s : updateGraph.get(fullName)) graph.addEdge(fullName,s);

            for(String s : updateGraph.get(num))graph.addEdge(num,s);

            for(String s : updateGraph.get(user)) graph.addEdge(user,s);

            if(name!=null){
                int i = graph.connection.get(fullName).indexOf(name);
                String number = graph.connection.get(num).get(i);
                String username = graph.connection.get(user).get(i);

                this.name.setText(name);
                this.number.setText(number);
                this.username.setText(username);

                File profileData = new File("assets/"+username+"profile.dat");

                if(profileData.exists()){
                    Scanner sc = new Scanner(profileData);
                    String photoDirectory = sc.next();
                    sc.close();
                    picture = new Image(photoDirectory);
                    setPicture.setImage(picture);
                }
                else{
                    picture = new Image("sample/Profile/default.jpg");
                    setPicture.setImage(picture);
                }

                profile.setVisible(true);
                contacts.setVisible(false);
                newContacts.setVisible(false);
                newMessage.setVisible(false);
                directory.setVisible(false);
                addPhoto.setVisible(false);
                unreadMessages.setVisible(false);
                readMessages.setVisible(false);
                addContact.setVisible(false);
                editName.setVisible(true);
                editNumber.setVisible(true);

            }

        }

    }


    //the following methods are for editing the contact details:

    public void editNameHandler() {

        previousName = name.getText();
        name.setVisible(false);
        editName.setVisible(false);
        editNumber.setVisible(false);
        newName.setVisible(true);
        doneName.setVisible(true);
        directory.setVisible(false);
        addPhoto.setVisible(false);

    }


    public void doneNameHandler() throws IOException, ClassNotFoundException {

        String filename = "assets/"+user+"graph.dat";
        File data = new File(filename);

        if(data.exists()){

            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> editInfo = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();


            this.name.setText(newName.getText());
            editInfo.get(fullName).set(editInfo.get(fullName).indexOf(previousName),newName.getText());

            contactList.getItems().clear();

            for(String person : editInfo.get(fullName)) contactList.getItems().add(person);

            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(editInfo);

            fout.close();
            out.close();
            System.out.println(editInfo);
        }
        name.setVisible(true);
        newName.setVisible(false);
        doneName.setVisible(false);
        editName.setVisible(true);
        editNumber.setVisible(true);


    }



    public void editNumberHandler(){

        previousNumber= number.getText();
        number.setVisible(false);
        editName.setVisible(false);
        editNumber.setVisible(false);
        newNumber.setVisible(true);
        doneNumber.setVisible(true);
        directory.setVisible(false);
        addPhoto.setVisible(false);

    }


    public void doneNumberHandler() throws IOException, ClassNotFoundException {

        String filename = "assets/" + user + "graph.dat";
        File data = new File(filename);

        if (data.exists()) {

            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> editInfo = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();


            this.number.setText(newNumber.getText());
            editInfo.get(num).set(editInfo.get(num).indexOf(previousNumber), newNumber.getText());

            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(editInfo);
            System.out.println(editInfo);

            fout.close();
            out.close();

        }
        number.setVisible(true);
        newNumber.setVisible(false);
        doneNumber.setVisible(false);
        editName.setVisible(true);
        editNumber.setVisible(true);
    }

    public void nameFilled(){
        doneName.setDisable(newName.getText().isEmpty());
    }

    public void contactFilled(){
        doneNumber.setDisable(newNumber.getText().isEmpty());
    }


    //remove the contact from both contact list and the graph of the user
    public void removeContactHandler() throws IOException, ClassNotFoundException {

        String fileName = "assets/"+user+"graph.dat";
        File data = new File(fileName);
        graph.connection.clear();
        if(data.exists()){
            String name = contactList.getSelectionModel().getSelectedItem();

            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> updateGraph = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();


            if(name!=null){

                for(String s : updateGraph.get(fullName)) graph.addEdge(fullName,s);

                for(String s : updateGraph.get(num))graph.addEdge(num,s);

                for(String s : updateGraph.get(user)) graph.addEdge(user,s);

                int i = graph.connection.get(fullName).indexOf(name);

                graph.removeEdge(fullName,name);
                graph.removeEdge(num,graph.connection.get(num).get(i));
                graph.removeEdge(user,graph.connection.get(user).get(i));

                contactList.getItems().clear();

                for(String person : graph.connection.get(fullName)) contactList.getItems().add(person);

                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(graph.connection);

                //System.out.println(graph.connection);

                fout.close();
                out.close();


            }
            graph.connection.clear();
        }


    }

    //add new contact button handler

    public void addHandler() throws IOException, ClassNotFoundException {
        String fileName = "assets/"+user+"graph.dat";
        File data = new File(fileName);

        String name = inputName.getText();
        String number = inputNumber.getText();
        String username = inputUser.getText();
        graph.connection.clear();
        if(!data.exists()){

            if(!name.isEmpty() && !number.isEmpty() && !username.isEmpty()){
                graph.addEdge(fullName, name);
                graph.addEdge(num, number);
                graph.addEdge(user, username);

                inputName.setText("");
                inputNumber.setText("");
                inputUser.setText("");

                contactList.getItems().clear();
                for(String person : graph.connection.get(fullName)) contactList.getItems().add(person);

                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(graph.connection);
                //System.out.println(graph.connection);

                fout.close();
                out.close();
            }


        }

        else{
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> updateGraph = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();


            for(String s : updateGraph.get(fullName)) graph.addEdge(fullName,s);

            for(String s : updateGraph.get(num))graph.addEdge(num,s);

            for(String s : updateGraph.get(user)) graph.addEdge(user,s);

            if(!name.isEmpty() && !number.isEmpty() && !username.isEmpty()){
                graph.addEdge(fullName, name);
                graph.addEdge(num, number);
                graph.addEdge(user, username);

                inputName.setText("");
                inputNumber.setText("");
                inputUser.setText("");

                contactList.getItems().clear();
                for(String person : graph.connection.get(fullName)) contactList.getItems().add(person);

                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(graph.connection);
                //System.out.println(graph.connection);

                fout.close();
                out.close();
            }


        }
        graph.connection.clear();


    }

    public void addHandler2() throws IOException, ClassNotFoundException {
        String fileName = "assets/"+user+"graph.dat";
        File data = new File(fileName);

        String name = this.name.getText();
        String number = this.number.getText();
        String username = this.username.getText();
        graph.connection.clear();
        if(!data.exists()){

            if(!name.isEmpty() && !number.isEmpty() && !username.isEmpty()){
                graph.addEdge(fullName, name);
                graph.addEdge(num, number);
                graph.addEdge(user, username);


                contactList.getItems().clear();
                for(String person : graph.connection.get(fullName)) contactList.getItems().add(person);

                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(graph.connection);
                //System.out.println(graph.connection);

                fout.close();
                out.close();
            }


        }

        else{
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fin);
            HashMap<String, LinkedList<String>> updateGraph = (HashMap<String, LinkedList<String>>) in.readObject();
            fin.close();
            in.close();

           for(String s : updateGraph.get(fullName)) graph.addEdge(fullName,s);

            for(String s : updateGraph.get(num)) graph.addEdge(num,s);

            for(String s : updateGraph.get(user)) graph.addEdge(user,s);

            if(!name.isEmpty() && !number.isEmpty() && !username.isEmpty()){
                graph.addEdge(fullName, name);
                graph.addEdge(num, number);
                graph.addEdge(user, username);

                contactList.getItems().clear();
                for(String person : graph.connection.get(fullName)) contactList.getItems().add(person);

                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(graph.connection);
                //System.out.println(graph.connection);

                fout.close();
                out.close();
            }


        }
        graph.connection.clear();


    }





    public void sortHandler(){

        ObservableList<String> obs = contactList.getItems();
        ArrayList<String> names = new ArrayList<>(obs);

        String[] nameArray = names.toArray(new String[0]);

        QuickSort(nameArray,0,nameArray.length-1);
        contactList.getItems().clear();
        for(String person : nameArray) contactList.getItems().add(person);

    }

    public int partition(String[] names, int first, int last) {
        String pivot = names[first];
        int low = first + 1;
        String temp;

        //partition checking
        for(int i = low ; i<=last ; i++){

            // if the element is less than the pivot
            // swap the previous element and current element
            if (names[i].charAt(0)<pivot.charAt(0)){
                temp = names[low];
                names[low] = names[i];
                names[i] = temp;
                low++;
            }
        }

        // swap the pivot to the element at low-1
        temp = names[low-1];
        names[low-1]=pivot;
        names[first]=temp;

        return low-1;

    }

    public void QuickSort(String[] names, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(names, first, last);
            QuickSort(names, first, pivotIndex - 1);
            QuickSort(names, pivotIndex + 1, last);

        }
    }

}
