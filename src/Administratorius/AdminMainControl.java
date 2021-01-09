package Administratorius;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMainControl {



    @FXML
    void RedagAdminDuomenis(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("RedagAdmin.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }

    }

    @FXML
    void RedagNaudotojus(ActionEvent actionevent){
        try {
            ((Node) actionevent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("adminfxml.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }
    }

    @FXML
    void RedagPrekes(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/Prekės/PrekRedag.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 800, 700));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }


    }

    @FXML
    void logaut(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/sample/sample.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 700, 600));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }

    }



    @FXML
    void RedagPrekiuKoment (ActionEvent event) {
        try {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("KomentRemove.fxml").openStream());
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 600, 460));
        primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }
    }


}
