package Registruotas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class slaptKeitimasControl implements Initializable {


    Connection con = null;
    PreparedStatement pat = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL location, ResourceBundle recources) {
        con = sample.SQLiteconnection.Connector();

    }


    @FXML
    private TextField senas;

    @FXML
    private TextField naujas;

    @FXML
    private TextField VartVardas;

    @FXML
    private Label zinute;


    @FXML
    void savenaujas(ActionEvent event) {
     try {

         if (patikrinimas()) {
             pakeitimas();
         zinute.setText("OK");
         } else {
           zinute.setText("Klaida");
        }
     }catch (SQLException ex){

         Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
     }
    }


    private boolean patikrinimas() throws SQLException {



        String query = " select * from REG where Nikas = ? and Pass = ?";
        try {
            pat = con.prepareStatement(query);
            pat.setString(1,VartVardas.getText());
            pat.setString(2,senas.getText());

            rs = pat.executeQuery();
            if (rs.next()){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e){
            return false;
            //TODO: handle exception

        }finally {
           pat.close();
           rs.close();
        }
    }



void pakeitimas() throws SQLException {

    String sql = "Update REG set Pass = ? where Nikas = ? ";
    try {

        pat = con.prepareStatement(sql);
        pat.setString(1, naujas.getText());
        pat.setString(2, VartVardas.getText());

        int i = pat.executeUpdate();
        if (i == 1) {
            PopUpMessage.display("Info", "Informacija atnaujinta");
        }

    } catch (SQLException ex) {

        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }finally {
        rs.close();
        pat.close();
    }
}
}
