package Administratorius;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller;
import sample.PopUpMessage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RedagAdminControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;

    @Override
    public void initialize(URL location, ResourceBundle recources){
        con = sample.SQLiteconnection.Connector();
        try {
            irast ();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @FXML
    private TextField Vadmin;

    @FXML
    private TextField Sadmin;

    @FXML
    void adminredag(ActionEvent event) throws SQLException {

        String sql = "Update ADMIN set VarAdmin = ?, PasAdmin = ? where ID = ? ";

        try {

            String var = Vadmin.getText();
            String pas = Sadmin.getText();
            int sk = 1;

            pat = con.prepareStatement(sql);

            pat.setString(1,var);
            pat.setString(2,pas);
            pat.setInt(3,sk);

            int i = pat.executeUpdate();
            if (i == 1) {
                PopUpMessage.display("Info", "Informacija atnaujinta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("AdminMain.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 400, 400));
                primaryStage.show();


            }

        } catch(SQLException | IOException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }



}

    @FXML
    void logaut(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("AdminMain.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }


    }

    public void irast () throws SQLException {

        int id= 1;
        String pag = "ID";
        String vard = "VarAdmin";
        String pas = "PasAdmin";


        try {

            pat = con.prepareStatement("Select * from ADMIN where ID = ?");
            pat.setInt(1, id);
            rs = pat.executeQuery();


            while (rs.next()){

                Vadmin.setText(rs.getString("VarAdmin"));
                Sadmin.setText(rs.getString("PasAdmin"));



            }
        }catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }

    }



}
