package sample;


import Administratorius.RedagAdminControl;
import Registruotas.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable
{


    public LoginModel loginModel = new LoginModel();
    private Connection con = null;
    private PreparedStatement pat = null;


    @FXML
    private Label isConnected;

    @FXML
    private TextField vardas;

    @FXML
    private TextField pavard;

    @FXML
    private TextField nicas;

    @FXML
    private TextField pass;

    @FXML
    private Button newlog;

    @FXML
    private TextField nicname;

    @FXML
    private TextField pasword;

    @FXML
    private TextField metai;

    @FXML
    private TextField men;

    @FXML
    private TextField diena;

    @FXML
    private Button close;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        con = sample.SQLiteconnection.Connector();

        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else {
            isConnected.setText("Not Connected");
        }
    }
    
    @FXML
    public void singin (ActionEvent event) {
        try {
            if (loginModel.isLogin(nicas.getText(),pass.getText())) {
                isConnected.setText("Vartojo vardas ir slaptažodis teisingi");
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("/Registruotas/user.fxml").openStream());
                User reguser = (User)loader.getController();
                reguser.userreg(nicas.getText());
                User loadT = (User)loader.getController();
                loadT.load(nicas.getText());
                User im = (User)loader.getController();
                im.showNuotrauka(nicas.getText());
                User uzsakimai = (User)loader.getController();
                uzsakimai.load2(nicas.getText());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 719, 638));
                primaryStage.show();
            } else {
                isConnected.setText("Vartojo vardas ir slaptažodis yra neteisingi");
            }
        }catch (SQLException e){
            isConnected.setText("Vartojo vardas ir slaptažodis yra neteisingi");
            //TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void admin(ActionEvent actionEvent) {
        try {
            if (loginModel.isLoginAdmin(nicas.getText(),pass.getText())) {
                isConnected.setText("Vartojo vardas ir slaptažodis teisingi");
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/Administratorius/AdminMain.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.show();
            } else {
                isConnected.setText("Vartojo vardas ir slaptažodis yra neteisingi");
            }
        } catch (SQLException | IOException e){
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @FXML
    void close (ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/Anonimas/PagrindAnonim.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 594, 496));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }

    }


    @FXML
    private void newlog (ActionEvent event) throws SQLException{

        String sql = " insert into REG(Vardas, Pavard, Nikas, Pass, Metai, Menesis, Diena) values(?,?,?,?,?,?,?)";
        String var = vardas.getText();
        String pav = pavard.getText();
        String nik = nicname.getText();
        String slap = pasword.getText();
        String met =  metai.getText();
        String menes= men.getText();
        String dien = diena.getText();

        try{
            pat = con.prepareStatement(sql);
            pat.setString(1,var);
            pat.setString(2,pav);
            pat.setString(3,nik);
            pat.setString(4,slap);
            pat.setString(5,met);
            pat.setString(6,menes);
            pat.setString(7,dien);


            int i = pat.executeUpdate();
            if(i == 1){
                System.out.println("Atnaujinta");
               isConnected.setText("Vartotojas užregistruotas. Prisijunkite");}


        }catch(SQLException ex){
            isConnected.setText("Klaida. Bandykite dar kart");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);

        } finally {
            pat.close();
        }

    }

    @FXML
    void fin(ActionEvent event) {

        try {
            if (loginModel.isLoginFin(nicas.getText(),pass.getText())) {
                isConnected.setText("Vartojo vardas ir slaptažodis teisingi");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("/Finansistas/Finansai.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 484, 400));
                primaryStage.show();
            } else {
                isConnected.setText("Vartojo vardas ir slaptažodis yra neteisingi");
            }
        } catch (SQLException | IOException e){
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
