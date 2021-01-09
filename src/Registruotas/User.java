package Registruotas;

import Pagrindinis.KomList;
import Pagrindinis.PagrindinisControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;



import javafx.scene.control.*;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.apache.log4j.PropertyConfigurator;
import sample.Controller;
import sample.PopUpMessage;


import javax.swing.*;
import javafx.scene.image.Image;



import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList<NoraiList> data;
    private ObservableList<UzsakList> data1;

    @FXML
    private Label registruotas;
    @FXML
    private Label redNikas;

    @FXML
    private Label pasas;

    @FXML
    private TextField redVardas;

    @FXML
    private TextField redPavard;

    @FXML
    private TextField redMetai;

    @FXML
    private TextField redMen;

    @FXML
    private TextField redDien;

    @FXML
    private Button redaguoti;

    @FXML
    private TextField namefile;

    @FXML
    private ImageView pav;

    @FXML
    private Pane Pane;


    @FXML
    private TableView<NoraiList> NoraiTable;

    @FXML
    private TableColumn<?, ?> NoraiPrekes;

    @FXML
    private Label txtNorai;

    @FXML
    private TableView<UzsakList> uzsaktable;

    @FXML
    private TableColumn<?, ?> nr;

    @FXML
    private TableColumn<?, ?> vnt;

    @FXML
    private TableColumn<?, ?> kain;

    @FXML
    private TableColumn<?, ?> dat;

    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Desktop deskTop = Desktop.getDesktop();
    private Image image;
    private FileInputStream fis;

    static final Logger logger = Logger.getLogger(User.class.getName());



    @FXML
    void redguotiuser(ActionEvent event) throws SQLException {

        String sql = "Update REG set Vardas = ?, Pavard = ?, Pass = ?, Metai = ?, Menesis = ?, Diena = ? where Nikas = ? ";
        try {
            String var = redVardas.getText();
            String pav = redPavard.getText();
            String nik = redNikas.getText();
            String slap = pasas.getText();
            String  met = redMetai.getText();
            String menes= redMen.getText();
            String dien = redDien.getText();



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
            }

        } catch(SQLException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        } finally {
            rs.close();
            pat.close();
        }
        }



    @FXML
    void keistslapt (ActionEvent event){

        try {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("slaptKeitimas.fxml").openStream());
            primaryStage.setTitle(" ");
            primaryStage.setScene(new Scene(root, 300, 400));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle recources){
        con = sample.SQLiteconnection.Connector();
        data = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();

        setle();
        info();

        lentinfo2 ();


        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.gif"),
                new FileChooser.ExtensionFilter("Text files", "*.txt")
        );


      // TODO Auto-generated method stud
    }

    public void userreg (String user) throws SQLException {
        // TODO Auto-generated method stud
        registruotas.setText(user);


        String vardas = "Vardas";
        String pavarde = "Pavard";
        String nikas = "Nikas";
        String pass = "Pass";
        String metai = "Metai";
        String men = "Menesis";
        String diena = "Diena";

        try {
            pat = con.prepareStatement("Select * from REG where Nikas = ?");
            pat.setString(1, user);
            rs = pat.executeQuery();


            while (rs.next()){
                redVardas.setText(rs.getString("Vardas"));
                redPavard.setText(rs.getString("Pavard"));
                redNikas.setText(rs.getString("Nikas"));
                pasas.setText(rs.getString("Pass"));
                redMetai.setText(rs.getString("Metai"));
                redMen.setText(rs.getString("Menesis"));
                redDien.setText(rs.getString("Diena"));


            }
        }catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }


    }


    @FXML
    private Button signout;

    @FXML
    public void signout(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/sample/sample.fxml").openStream());
            primaryStage.setTitle("Pradinis");
            primaryStage.setScene(new Scene(root, 700, 600));
            primaryStage.show();
        } catch (Exception e){
            //TODO: handle exception
        }
    }

    @FXML
    void refr (ActionEvent event) throws SQLException {

        refres(registruotas.getText());
    }

    private void refres (String user ) throws SQLException {


        String nikas = "Nikas";
        String pass = "Pass";


        try {
            pat = con.prepareStatement("Select * from REG where Nikas = ?");
            pat.setString(1, user);
            rs = pat.executeQuery();


            while (rs.next()){

                pasas.setText(rs.getString("Pass"));

            }

        }catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }


    }

    @FXML
    public void issaugoti (ActionEvent event) throws SQLException {

        String sql = "insert into REGFT(VARDAS, FT) values(?,?)";

        String vard = redNikas.getText();



        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, vard);
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

        }finally {
            rs.close();
            pat.close();
        }

    }

    @FXML
    public void pridetift (ActionEvent event){

        stage =(Stage) Pane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            System.out.println(""+file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString());
            pav.setImage(image);

            }
        }


        public void showNuotrauka (String user) throws SQLException {
        try {
            pat = con.prepareStatement("Select FT from REGFT where VARDAS = ?");
            pat.setString(1, user);
            rs = pat.executeQuery();
            if(rs.next()){
                InputStream is = rs.getBinaryStream(1);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while ((size= is.read(contents)) != -1){
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg", 95, 131,true, true);
                pav.setImage(image);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            rs.close();
            pat.close();
        }
        }





    @FXML
    void ToTheMain(ActionEvent event) {




        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/Pagrindinis/Pagrindinis.fxml").openStream());

            PagrindinisControl reg = (PagrindinisControl) loader.getController();
            reg.useris(registruotas.getText());
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 600, 644));
            primaryStage.show();

            PropertyConfigurator.configure(getClass().getResource("Pagrindinis.log4j.properties"));

        } catch (Exception e){
            //TODO: handle exception
        }
        

    }

    public void load (String user) throws SQLException {
        // TODO Auto-generated method stud

        try {
            pat = con.prepareStatement("Select * from NORAI where VARDAS = ?");
            pat.setString(1, user);
            rs = pat.executeQuery();
            while (rs.next()){

                data.add(new NoraiList(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        NoraiTable.setItems(data);;

    }

    public void setle(){
        NoraiTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                NoraiList pl = NoraiTable.getItems().get(NoraiTable.getSelectionModel().getSelectedIndex());
                txtNorai.setText(pl.getId());

            }
        });


    }
    private void info(){

        NoraiPrekes.setCellValueFactory(new PropertyValueFactory<>("prek"));

    }


    @FXML
    void DeleteFromNorai(ActionEvent event) throws SQLException {


        String sql = "delete from NORAI where ID = ? ";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, txtNorai.getText());

            int i = pat.executeUpdate();
            if (i == 1){
                PopUpMessage.display("Info", "Prekė ištrinta iš norų sąrašo");
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("user.fxml").openStream());
                User reguser = (User)loader.getController();
                reguser.userreg(redNikas.getText());
                User loadT = (User)loader.getController();
                loadT.load(redNikas.getText());
                User im = (User) loader.getController();
                im.showNuotrauka(redNikas.getText());
                User uzsakimai = (User)loader.getController();
                uzsakimai.load2(redNikas.getText());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 719, 638));
                primaryStage.show();

            }

        }catch (SQLException | IOException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }

    }



    private void lentinfo2 (){

        nr.setCellValueFactory(new PropertyValueFactory<>("nr"));
        vnt.setCellValueFactory(new PropertyValueFactory<>("kiek"));
        kain.setCellValueFactory(new PropertyValueFactory<>("kain"));
        dat.setCellValueFactory(new PropertyValueFactory<>("dat"));

    }

    public void load2 (String user) throws SQLException {
        // TODO Auto-generated method stud

        try {
            pat = con.prepareStatement("Select * from UZSAKYMAI where VARDAS = ? ");
            pat.setString(1, user);
            rs = pat.executeQuery();
            while (rs.next()){

                data1.add(new UzsakList(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        uzsaktable.setItems(data1);;
    }




}


