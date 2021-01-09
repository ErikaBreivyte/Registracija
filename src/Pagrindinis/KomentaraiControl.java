package Pagrindinis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Controller;

import javax.xml.transform.sax.SAXResult;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KomentaraiControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList <KomList> data1;

    @FXML
    private Label KomPavad;

    @FXML
    private TableView<KomList> table;

    @FXML
    private TableColumn<?, ?> KomVardas;

    @FXML
    private TableColumn<?, ?> KomData;

    @FXML
    private TableColumn<?, ?> KomKom;

    @FXML
    private Button ok;





    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        con = sample.SQLiteconnection.Connector();
        data1 = FXCollections.observableArrayList();


        lentinfo2 ();

    // TODO Auto-generated method stud
    }



    private void lentinfo2 (){

        KomVardas.setCellValueFactory(new PropertyValueFactory<>("nik"));
        KomData.setCellValueFactory(new PropertyValueFactory<>("data"));
        KomKom.setCellValueFactory(new PropertyValueFactory<>("kom"));

    }



    public void load2 (String prekpav) throws SQLException {
        // TODO Auto-generated method stud

        KomPavad.setText(prekpav);

        try {
            pat = con.prepareStatement("Select * from KOMENTARAI where PREKE = ? ");
            pat.setString(1, prekpav);
            rs = pat.executeQuery();
            while (rs.next()){

                data1.add(new KomList(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        table.setItems(data1);;
    }


    @FXML
    void ok (ActionEvent event) {

        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();

    }


}
