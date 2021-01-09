package Finansistas;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Controller;
import sample.Main;
import sample.PopUpMessage;
import sample.UserList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinansaiControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList<Finansistas.UzsakList> data1;
    private ObservableList<Finansistas.UzsakList> order;



    @FXML
    private TableView<Finansistas.UzsakList> ordertable;

    @FXML
    private TableColumn<?, ?> nr;

    @FXML
    private TableColumn<?, ?> vardas;

    @FXML
    private TableColumn<?, ?> data;

    @FXML
    private TableColumn<?, ?> kiekis;

    @FXML
    private TableColumn<?, ?> kaina;






    @Override
    public void initialize(URL location, ResourceBundle recources) {
        con = sample.SQLiteconnection.Connector();
        data1 = FXCollections.observableArrayList();
        order = FXCollections.observableArrayList();

        setTableInfo ();

        lentinfo2 ();
        try {
            load2 ();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    @FXML
    void Eksportuoti(ActionEvent event) throws IOException, SQLException {

        try {
            pat = con.prepareStatement("Select * from UZSAKYMAI ");
            rs = pat.executeQuery();
            while (rs.next()){

                order.add(new UzsakList(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5) ));

                Gson gson = new Gson();

                String json = gson.toJson(order);

                try {
                    FileWriter fileWriter = new FileWriter("order.json");

                    fileWriter.write(json);
                    fileWriter.close();

                    PopUpMessage.display("Info", "Duomenys eksportuoti");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
    }



    @FXML
    void LogOut(ActionEvent event) {

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



    private void lentinfo2 (){

        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        vardas.setCellValueFactory(new PropertyValueFactory<>("vard"));
        kiekis.setCellValueFactory(new PropertyValueFactory<>("kiek"));
        kaina.setCellValueFactory(new PropertyValueFactory<>("kain"));
        data.setCellValueFactory(new PropertyValueFactory<>("dat"));

    }

    public void load2 () throws SQLException {


        try {
            pat = con.prepareStatement("Select * from UZSAKYMAI ");
            rs = pat.executeQuery();
            while (rs.next()){

                data1.add(new Finansistas.UzsakList(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        ordertable.setItems(data1);;
    }

    public void setTableInfo (){
        ordertable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UzsakList pl = ordertable.getItems().get(ordertable.getSelectionModel().getSelectedIndex());

            }
        });
    }
}







