package Anonimas;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Controller;

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

public class PagrindAnonimControl implements Initializable {

    private Connection con = null;
    private PreparedStatement pat = null;
    private ResultSet rs = null;
    private ObservableList<PagList> data;
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    private Desktop deskTop = Desktop.getDesktop();
    private Image image;
    private FileInputStream fis;


    @FXML
    private ImageView paveikslas;

    @FXML
    private Label lPav;

    @FXML
    private Label lApras;

    @FXML
    private Label lKaina;

    @FXML
    private TableView<PagList> table;

    @FXML
    private TableColumn<?, ?> prekes;

    @FXML
    private Label eur;

    @FXML
    private Label price;

    @FXML
    private Button Close;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        con = sample.SQLiteconnection.Connector();
        data = FXCollections.observableArrayList();


        lentinfo ();
        try {
            load();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setTableInfo ();

    }

    @FXML
    void Close(ActionEvent event) {

        Stage stage = (Stage) Close.getScene().getWindow();
        stage.close();

    }

    @FXML
    void Registruotis(ActionEvent event) {

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


    private void lentinfo (){

        prekes.setCellValueFactory(new PropertyValueFactory<>("pav"));

    }



    private void load () throws SQLException {
        try {
            pat = con.prepareStatement("Select * from PREKES ");
            rs = pat.executeQuery();
            while (rs.next()){
                data.add(new PagList(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)));
            }
        } catch(SQLException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            rs.close();
            pat.close();
        }
        table.setItems(data);;
    }



    public void setTableInfo (){
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PagList pl = table.getItems().get(table.getSelectionModel().getSelectedIndex());
                lPav.setText(pl.getPav());
                lApras.setText(pl.getApras());
                lKaina.setText(pl.getKain());
                eur.setText("Eur");
                price.setText("Kaina:");
                try {
                    showNuotrauka(pl.getPav());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        });
    }

    public void showNuotrauka(String pavadi) throws SQLException {

        try {
            pat = con.prepareStatement("Select FT from PREKFT where PREKE = ?");
            pat.setString(1, pavadi);
            rs = pat.executeQuery();
            if (rs.next()) {
                InputStream is = rs.getBinaryStream(1);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while ((size = is.read(contents)) != -1) {
                    os.write(contents, 0, size);
                }
                image = new Image("file:photo.jpg", 138, 196, true, true);
                paveikslas.setImage(image);
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
