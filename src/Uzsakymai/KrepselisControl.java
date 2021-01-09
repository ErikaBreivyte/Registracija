package Uzsakymai;

import Pagrindinis.KomList;
import Pagrindinis.PagList;
import Registruotas.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KrepselisControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList<PirkiniaiList> data1;


    @FXML
    private TableView<PirkiniaiList> Table;

    @FXML
    private TableColumn<?, ?> Preke;

    @FXML
    private TableColumn<?, ?> Kaina;

    @FXML
    private Label Suma;

    @FXML
    private Label kiekis;

    @FXML
    private Label date;

    @FXML
    private Label name;

    @FXML
    private Label nr;

    @FXML
    private Button uzsak;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        con = sample.SQLiteconnection.Connector();
        data1 = FXCollections.observableArrayList();


        lentinfo2 ();
        setTableInfo();
        currentdate();

        // TODO Auto-generated method stud
    }


    @FXML
    void Istrinti(ActionEvent event) throws SQLException {

        String vars = nr.getText();


        String sql = "delete from KREPSELIS where ID = ? ";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, vars);

            int i = pat.executeUpdate();
            if (i == 1){
                PopUpMessage.display("Info", "Prekė ištrinta iš pirkinių sąrašo");
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Krepselis.fxml").openStream());
                KrepselisControl loaduzsak = (KrepselisControl) loader.getController();
                loaduzsak.krepsel(name.getText());
                KrepselisControl sumos = (KrepselisControl) loader.getController();
                sumos.suma(name.getText());
                KrepselisControl K = (KrepselisControl) loader.getController();
                K.kiekis(name.getText());
                primaryStage.setTitle("");
                primaryStage.setScene(new Scene(root, 384, 434));
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
    void Uzsakyti(ActionEvent event) throws SQLException {


            String sql = " insert into UZSAKYMAI(VARDAS, DATA, KIEKIS, KAINA) values(?,?,?,?)";
            String var = name.getText();
            String dat = date.getText();
            String kiek = kiekis.getText();
            String kain = Suma.getText(); ;



            try {
                pat = con.prepareStatement(sql);
                pat.setString(1, var);
                pat.setString(2, dat);
                pat.setString(3, kiek);
                pat.setString(4, kain);


                int i = pat.executeUpdate();
                if (i == 1) {
                    sample.PopUpMessage.display("Info", "Užsakymas vykdomas");
                    trinti (name.getText());
                    Stage stage = (Stage) uzsak.getScene().getWindow();
                    stage.close();

                }

            } catch (SQLException ex) {
                sample.PopUpMessage.display("Info", "Užsakyti nepavyko");
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                pat.close();
            }



        }

    public void krepsel(String s) throws SQLException {

        // TODO Auto-generated method stud

        name.setText(s);

        try {
            pat = con.prepareStatement("Select * from KREPSELIS where VARDAS = ? ");
            pat.setString(1, s);
            rs = pat.executeQuery();
            while (rs.next()){

                data1.add(new PirkiniaiList(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        Table.setItems(data1);;


    }

    private void lentinfo2 (){

        Preke.setCellValueFactory(new PropertyValueFactory<>("preke"));
        Kaina.setCellValueFactory(new PropertyValueFactory<>("kaina"));


    }

    public void suma (String v) throws SQLException {
        try {
            pat = con.prepareStatement("Select sum(KAINA) from KREPSELIS where VARDAS = ? ");
            pat.setString(1, v);
            rs = pat.executeQuery();
            if(rs.next()){

                String sum =rs.getString("sum(KAINA)");
                Suma.setText(sum);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            rs.close();
            pat.close();
        }
    }

    public void kiekis  (String k) throws SQLException {

        try {
            pat = con.prepareStatement("Select count(PREKE) from KREPSELIS where VARDAS = ? ");
            pat.setString(1, k);
            rs = pat.executeQuery();
            if(rs.next()){

                String sum =rs.getString("count(PREKE)");
                kiekis.setText(sum);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            rs.close();
            pat.close();
        }

    }

    private void currentdate() {
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date.setText(String.valueOf(dateFormat.format(currentDate)));
    }

    private void trinti (String vars) throws SQLException {


        String sql = "delete from KREPSELIS where VARDAS = ? ";
        try {
            pat = con.prepareStatement(sql);
            pat.setString(1, vars);

            int i = pat.executeUpdate();
            if (i == 1){
            }

        }catch (SQLException ex){

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }

    }

    public void setTableInfo() {
        Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PirkiniaiList pl = Table.getItems().get(Table.getSelectionModel().getSelectedIndex());
                nr.setText(pl.getId());

            }
        });
    }


    }



