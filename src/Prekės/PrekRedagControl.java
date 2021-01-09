package Prekės;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Controller;
import sample.PopUpMessage;
import sample.UserList;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrekRedagControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList<KategorList> data;
    private ObservableList<prekList> data1;


    @FXML
    private Pane Pane;

    @FXML
    private TableView<KategorList> lentele2;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> pavadin;

    @FXML
    private TextField addTxt;

    @FXML
    private TableColumn<?, ?> k_id;

    @FXML
    private TableColumn<?, ?> pav;

    @FXML
    private TableColumn<?, ?> apras;

    @FXML
    private TableColumn<?, ?> kain;

    @FXML
    private TableView<prekList> lentel3;

    @FXML
    private TextField txtPav;

    @FXML
    private TextField txtApras;

    @FXML
    private TextField txtKaina;

    @FXML
    private TextField txtId;

    @FXML
    private Label txtlab;

    @FXML
    private ImageView nuotrauka;

    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Desktop deskTop = Desktop.getDesktop();
    private Image image;
    private FileInputStream fis;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        con = sample.SQLiteconnection.Connector();
        data = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();
        infolent2();
        try {
            loadlent2();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setTable();

        infolent3 ();
        try {
            loadlent3();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setTable3();

       fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("All files", "*.*"),
              new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.gif"),
              new FileChooser.ExtensionFilter("Text files", "*.txt"));


    }


    private void infolent2 (){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pavadin.setCellValueFactory(new PropertyValueFactory<>("pavadin"));
    }

    private void loadlent2 () throws SQLException {
        try {
            pat = con.prepareStatement("Select * from KATEGORIJOS ");
            rs = pat.executeQuery();
            while (rs.next()){
                data.add(new KategorList(""+rs.getInt(1),rs.getString(2)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        lentele2.setItems(data);
    }



    public void setTable (){
        lentele2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                KategorList pl = lentele2.getItems().get(lentele2.getSelectionModel().getSelectedIndex());
                addTxt.setText(pl.getPavadin());
                txtlab.setText(pl.getId());

            }
        });
    }

    @FXML
    void addKateg(ActionEvent event) throws SQLException {

        String sql = " insert into KATEGORIJOS(ID, PAVADINIMAS) values(?,?)";

        String pavadin = addTxt.getText();


        try {
            pat = con.prepareStatement(sql);
            pat.setString(2, pavadin);



            int i = pat.executeUpdate();
            if (i == 1) {
                sample.PopUpMessage.display("Info", "Kategorija sukurta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PrekRedag.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 700));
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
    void clear(ActionEvent event) {

        addTxt.clear();
    }

    @FXML
    void minusKateg(ActionEvent event) throws SQLException {


        String sql = "delete from KATEGORIJOS where PAVADINIMAS = ?";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, addTxt.getText());
            int i = pat.executeUpdate();
            if (i == 1){
                PopUpMessage.display("Info", "Kategorija panaikinta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PrekRedag.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 700));
                primaryStage.show();

            }

        }catch (SQLException | IOException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }

    }

 /// lentele numeris tris prekes

    private void infolent3 (){
        k_id.setCellValueFactory(new PropertyValueFactory<>("k_id"));
        pav.setCellValueFactory(new PropertyValueFactory<>("pav"));
        apras.setCellValueFactory(new PropertyValueFactory<>("apras"));
        kain.setCellValueFactory(new PropertyValueFactory<>("kain"));


    }

    private void loadlent3 () throws SQLException {
        try {
            pat = con.prepareStatement("Select * from PREKES ");
            rs = pat.executeQuery();
            while (rs.next()){
                data1.add(new prekList(""+rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        lentel3.setItems(data1);
    }



    public void setTable3 (){
        lentel3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prekList pl = lentel3.getItems().get(lentel3.getSelectionModel().getSelectedIndex());
                txtPav.setText(pl.getPav());
                txtApras.setText(pl.getApras());
                txtKaina.setText(pl.getKain());
                txtlab.setText(pl.getK_id());
                try {
                    showNuotrauka (pl.getPav());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    @FXML
    void bIsvalyti(ActionEvent event) {

        txtPav.clear();
        txtApras.clear();
        txtKaina.clear();

    }

    @FXML
    void bPrideti(ActionEvent event) throws SQLException {

        String sql = "insert into PREKES(KID, PAVADINIMAS, APRASYMAS, KAINA) values(?,?,?,?)";

        String k_id = txtlab.getText();
        String pav = txtPav.getText();
        String apras = txtApras.getText();
        String kain = txtKaina.getText();


        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, k_id);
            pat.setString(2, pav);
            pat.setString(3, apras);
            pat.setString(4, kain);




            int i = pat.executeUpdate();
            if (i == 1) {
                sample.PopUpMessage.display("Info", "Prekė pridėta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PrekRedag.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 700));
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
    void bSalinti(ActionEvent event) throws SQLException {

        String sql = "delete from PREKES where PAVADINIMAS = ?";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, txtPav.getText());
            int i = pat.executeUpdate();
            if (i == 1){
                PopUpMessage.display("Info", "Prekė panaikinta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PrekRedag.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 700));
                primaryStage.show();

            }

        }catch (SQLException | IOException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }


    }

    @FXML
    void bKeisti(ActionEvent event) throws SQLException {

        String sql = "Update PREKES set KID = ? where PAVADINIMAS = ? ";
        try {

            String pav = txtPav.getText();
            String k_id = txtlab.getText();

            pat = con.prepareStatement(sql);
            pat.setString(1,k_id);
            pat.setString(2,pav);

            int i = pat.executeUpdate();
            if (i == 1) {
                PopUpMessage.display("Info", "Informacija atnaujinta");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PrekRedag.fxml").openStream());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 800, 700));
                primaryStage.show();
            }

        } catch(SQLException | IOException ex){
                PopUpMessage.display("Info", "Klaida");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }


    }

    @FXML
    void close(ActionEvent event) {

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
    void BackMenu(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/Administratorius/AdminMain.fxml").openStream());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }

    }

    @FXML
    void ieskoti(ActionEvent event) {

        stage =(Stage) Pane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            System.out.println(""+file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString());
            nuotrauka.setImage(image);
        }
    }

    @FXML
    void save(ActionEvent event) throws SQLException {

        String sql = "insert into PREKFT(PREKE, FT) values(?,?)";

        String pa = txtPav.getText();

        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, pa);
            InputStream is = new FileInputStream(file);
            fis = new FileInputStream(file);
            pat.setBinaryStream(2,fis, fis.available());


            int i = pat.executeUpdate();
            if (i == 1) {
                sample.PopUpMessage.display("Info", "Nuotrauka pridėta");

            }

        }catch (SQLException | IOException ex){
            sample.PopUpMessage.display("Info", "Klaida");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

        }finally{
            pat.close();
        }

    }

    public void showNuotrauka (String pavadi) throws SQLException {

        try {
            pat = con.prepareStatement("Select FT from PREKFT where PREKE = ?");
            pat.setString(1, pavadi);
            rs = pat.executeQuery();
            if(rs.next()){
                InputStream is = rs.getBinaryStream(1);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while ((size= is.read(contents)) != -1){
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg", 167, 126,true, true);
                nuotrauka.setImage(image);
            }
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            rs.close();
            pat.close();
        }
    }






}





