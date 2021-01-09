package Administratorius;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller;
import sample.PopUpMessage;
import sample.UserList;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KomentRemoveControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList<KomList> data1;

    @FXML
    private TableView<KomList> KomTable;

    @FXML
    private TableColumn<?, ?> Preke;

    @FXML
    private TableColumn<?, ?> Vardas;

    @FXML
    private TableColumn<?, ?> Data;

    @FXML
    private TableColumn<?, ?> Komentaras;

    @FXML
    private TextField TxtVardas;

    @FXML
    private TextField TxtPreke;

    @FXML
    private Label TxtId;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        con = sample.SQLiteconnection.Connector();
        data1 = FXCollections.observableArrayList();

        tableinfo ();
        try {
            loadkom ();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setTable ();

    }



    @FXML
    void Back(ActionEvent event) {

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


    @FXML
    void Remove(ActionEvent event) throws SQLException {


        String sql = "delete from KOMENTARAI where id = ? ";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, TxtId.getText());

            int i = pat.executeUpdate();
            if (i == 1){
                PopUpMessage.display("Info", "Komentaras ištrintas");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("KomentRemove.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 600, 460));
                primaryStage.show();

            }

        }catch (SQLException | IOException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }

    }


    private void tableinfo (){

        Preke.setCellValueFactory(new PropertyValueFactory<>("prek"));
        Vardas.setCellValueFactory(new PropertyValueFactory<>("nik"));
        Data.setCellValueFactory(new PropertyValueFactory<>("data"));
        Komentaras.setCellValueFactory(new PropertyValueFactory<>("kom"));

    }



    public void loadkom () throws SQLException {

        try {
            pat = con.prepareStatement("Select * from KOMENTARAI ");
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
        KomTable.setItems(data1);;
    }


    public void setTable (){
        KomTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                KomList pl = KomTable.getItems().get(KomTable.getSelectionModel().getSelectedIndex());
                TxtPreke.setText(pl.getPrek());
                TxtVardas.setText(pl.getNik());
                TxtId.setText(pl.getId());

            }
        });
    }

}
