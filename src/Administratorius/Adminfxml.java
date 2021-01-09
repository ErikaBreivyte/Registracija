package Administratorius;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller;
import sample.PopUpMessage;
import sample.UserList;

import java.io.IOException;
import java.lang.annotation.Inherited;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Adminfxml implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList <UserList> data;


    @FXML
    private TextField adVardas;

    @FXML
    private TextField adPavard;

    @FXML
    private TextField adNikas;

    @FXML
    private TextField adPass;

    @FXML
    private TextField adMetai;

    @FXML
    private TextField adMen;

    @FXML
    private TextField adDien;

    @FXML
    private TableView<UserList> lentele;

    @FXML
    private TableColumn<?, ?> stVardas;

    @FXML
    private TableColumn<?, ?> stPavarde;

    @FXML
    private TableColumn<?, ?> stNikas;

    @FXML
    private TableColumn<?, ?> stPass;

    @FXML
    private TableColumn<?, ?> stMetai;

    @FXML
    private TableColumn<?, ?> stMenesis;

    @FXML
    private TableColumn<?, ?> stDiena;

    @FXML
    private Label informacija;

    @FXML
    private Button delete;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        con = sample.SQLiteconnection.Connector();
        data = FXCollections.observableArrayList();
        lentinfo();
        try {
            loaddata();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setTableInfo();
    }



    private void lentinfo (){

        stVardas.setCellValueFactory(new PropertyValueFactory<>("vardas"));
        stPavarde.setCellValueFactory(new PropertyValueFactory<>("pavarde"));
        stNikas.setCellValueFactory(new PropertyValueFactory<>("nikas"));
        stPass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        stMetai.setCellValueFactory(new PropertyValueFactory<>("metai"));
        stMenesis.setCellValueFactory(new PropertyValueFactory<>("menesis"));
        stDiena.setCellValueFactory(new PropertyValueFactory<>("diena"));

    }

    private void loaddata () throws SQLException {
        try {
            pat = con.prepareStatement("Select * from REG ");
            rs = pat.executeQuery();
            while (rs.next()){
                data.add(new UserList(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4),""+rs.getInt(5),""+rs.getInt(6),""+rs.getInt(7)));
            }
        } catch(SQLException ex){
        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
    }finally {
            rs.close();
            pat.close();
        }
        lentele.setItems(data);
    }



    public void setTableInfo (){
        lentele.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserList pl = lentele.getItems().get(lentele.getSelectionModel().getSelectedIndex());
                adVardas.setText(pl.getVardas());
                adPavard.setText(pl.getPavarde());
                adNikas.setText(pl.getNikas());
                adPass.setText(pl.getPass());
                adMetai.setText(pl.getMetai());
                adMen.setText(pl.getMenesis());
                adDien.setText(pl.getDiena());
            }
        });
    }

    @FXML
    void update(ActionEvent event) throws SQLException {
        String sql = "Update REG set Vardas = ?, Pavard = ?, Pass = ?, Metai = ?, Menesis = ?, Diena = ? where Nikas = ? ";
        try {
            String var = adVardas.getText();
            String pav = adPavard.getText();
            String nik = adNikas.getText();
            String slap = adPass.getText();
            String  met = adMetai.getText();
            String menes= adMen.getText();
            String dien = adDien.getText();
            pat = con.prepareStatement(sql);
            pat.setString(1,var);
            pat.setString(2,pav);
            pat.setString(3,slap);
            pat.setString(4,met);
            pat.setString(5,menes);
            pat.setString(6,dien);
            pat.setString(7,nik);

            int i = pat.executeUpdate();
            if (i == 1) {
                PopUpMessage.display("Info", "Informacija atnaujinta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("adminfxml.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 600));
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
    private void deleteRegistruota (ActionEvent event) throws SQLException {

        String sql = "delete from REG where Nikas = ?";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, adNikas.getText());
            int i = pat.executeUpdate();
            if (i == 1){
                PopUpMessage.display("Info", "Vartotojas ištrintas");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("adminfxml.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 600));
                primaryStage.show();

            }

        }catch (SQLException | IOException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            pat.close();
        }

    }

    @FXML
    void backtomenu(ActionEvent event) {

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
    void out(ActionEvent event) {

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
    void AddNew(ActionEvent event) throws SQLException {

        String sql = " insert into REG(Vardas, Pavard, Nikas, Pass, Metai, Menesis, Diena) values(?,?,?,?,?,?,?)";
        String var = adVardas.getText();
        String pav = adPavard.getText();
        String nik = adNikas.getText();
        String slap = adPass.getText();
        String met = adMetai.getText();
        String menes = adMen.getText();
        String dien = adDien.getText();

        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, var);
            pat.setString(2, pav);
            pat.setString(3, nik);
            pat.setString(4, slap);
            pat.setString(5, met);
            pat.setString(6, menes);
            pat.setString(7, dien);


            int i = pat.executeUpdate();
            if (i == 1) {
                sample.PopUpMessage.display("Info", "Vartotojas sukurtas");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("adminfxml.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 600));
                primaryStage.show();

            }

            }catch (SQLException | IOException ex){
                sample.PopUpMessage.display("Info", "Klaida");
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

            }
        finally{
                pat.close();
            }

        }


    @FXML
    void clear (ActionEvent event) {

        adVardas.clear();
        adPavard.clear();
        adNikas.clear();
        adPass.clear();
        adMetai.clear();
        adMen.clear();
        adDien.clear();
    }

}
