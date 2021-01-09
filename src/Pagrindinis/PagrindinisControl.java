package Pagrindinis;

import Prekės.KategorList;
import Registruotas.User;
import Uzsakymai.KrepselisControl;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;
import sample.Controller;
import sample.UserList;

import java.awt.*;
import java.io.*;
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

public class PagrindinisControl implements Initializable {

   private Connection con = null;
   private PreparedStatement pat = null;
   private ResultSet rs = null;
   private ObservableList<PagList> data;
   private ObservableList<KomList> data1;
   private FileChooser fileChooser;
   private File file;
   private Stage stage;
   private Desktop deskTop = Desktop.getDesktop();
   private Image image;
   private FileInputStream fis;



   @FXML
   private Label lPav;

   @FXML
   private Label lApras;

   @FXML
   private Label lKaina;

   @FXML
   private ImageView paveiksl;

   @FXML
   private TableView<PagList> table;

   @FXML
   private TableColumn<?, ?> prekes;

   @FXML
   private TableColumn<?, ?> id;

   @FXML
   private Label vardasUser1;

   @FXML
   private Label vardasUser;

   @FXML
   private Label komPavad;

   @FXML
   private TextArea KomNaujas;


   @FXML
   private TableView<KomList> KomTable;

   @FXML
   private TableColumn<?, ?> KomVardas;

   @FXML
   private TableColumn<?, ?> KomData;

   @FXML
   private TableColumn<?, ?> KomKom;

   @FXML
   private Label eur;

   @FXML
   private Label price;

   @FXML
   private Label dateDabar;


   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      con = sample.SQLiteconnection.Connector();
      data = FXCollections.observableArrayList();
      data1 = FXCollections.observableArrayList();

      lentinfo();
      try {
         load();
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
      setTableInfo();
      currentdate();

      // TODO Auto-generated method stud
   }


   public void useris(String user) {

      // TODO Auto-generated method stud

      vardasUser.setText(user);
      vardasUser1.setText(user);

   }


   private void lentinfo() {

      prekes.setCellValueFactory(new PropertyValueFactory<>("pav"));

   }


   private void load() throws SQLException {
      try {
         pat = con.prepareStatement("Select * from PREKES ");
         rs = pat.executeQuery();
         while (rs.next()) {
            data.add(new PagList(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
         }
      } catch (SQLException ex) {
         Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
      }finally {
         rs.close();
         pat.close();
      }
      table.setItems(data);

   }


   public void setTableInfo() {
      table.setOnMouseClicked(new EventHandler<MouseEvent>() {


         @Override
         public void handle(MouseEvent mouseEvent) {
            PagList pl = table.getItems().get(table.getSelectionModel().getSelectedIndex());
            lPav.setText(pl.getPav());
            lApras.setText(pl.getApras());
            lKaina.setText(pl.getKain());
            komPavad.setText(pl.getPav());
            eur.setText("Eur");
            price.setText("Kaina:");
            try {
               showNuotrauka(pl.getPav());
            } catch (SQLException throwables) {
               throwables.printStackTrace();
            }

            Logger.getLogger(PagrindinisControl.class.getName()).info("Registruotas peržiūrėjo prekę");

         }
      });
   }

   @FXML
   void backlog(ActionEvent event) {
      try {
         ((Node) event.getSource()).getScene().getWindow().hide();
         Stage primaryStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         Pane root = loader.load(getClass().getResource("/Registruotas/user.fxml").openStream());
         User reguser = (User) loader.getController();
         reguser.userreg(vardasUser.getText());
         User loadT = (User) loader.getController();
         loadT.load(vardasUser.getText());
         User im = (User) loader.getController();
         im.showNuotrauka(vardasUser.getText());
         User uzsakimai = (User)loader.getController();
         uzsakimai.load2(vardasUser.getText());
         primaryStage.setTitle("");
         primaryStage.setScene(new Scene(root, 719, 638));
         primaryStage.show();
      } catch (Exception e) {
         //TODO: handle exception
      }

   }

   @FXML
   void komentarai(ActionEvent event) {

      try {
         Stage primaryStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         Pane root = loader.load(getClass().getResource("Komentarai.fxml").openStream());
         KomentaraiControl pavad = (KomentaraiControl) loader.getController();
         pavad.load2(komPavad.getText());
         primaryStage.setTitle("");
         primaryStage.setScene(new Scene(root, 582, 400));
         primaryStage.show();
      } catch (Exception e) {
         //TODO: handle exception
      }

   }

   @FXML
   void AddNewKoment(ActionEvent event) throws SQLException {

      String sql = " insert into KOMENTARAI(PREKE, NIKAS, DATA, KOMENT) values(?,?,?,?)";
      String prek = komPavad.getText();
      String nik = vardasUser1.getText();
      String data = dateDabar.getText();
      String koment = KomNaujas.getText();


      try {
         pat = con.prepareStatement(sql);
         pat.setString(1, prek);
         pat.setString(2, nik);
         pat.setString(3, data);
         pat.setString(4, koment);


         int i = pat.executeUpdate();
         if (i == 1) {
            KomNaujas.clear();
            sample.PopUpMessage.display("Info", "Komentaras pridėtas");

         }

      } catch (SQLException ex) {
         sample.PopUpMessage.display("Info", "Klaida");
         Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

      } finally {
         pat.close();
      }


   }

   private void currentdate() {
      Date currentDate = new Date();

      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      dateDabar.setText(String.valueOf(dateFormat.format(currentDate)));
   }


   @FXML
   void PridetiNora(ActionEvent event) throws SQLException {

      String sql = " insert into NORAI(VARDAS, PREKE) values(?,?)";
      String var = vardasUser.getText();
      String prek = lPav.getText();


      try {
         pat = con.prepareStatement(sql);
         pat.setString(1, var);
         pat.setString(2, prek);


         int i = pat.executeUpdate();
         if (i == 1) {
            sample.PopUpMessage.display("Info", "Prekė pridėta į norų sąrašą");

         }

      } catch (SQLException ex) {
         sample.PopUpMessage.display("Info", "Klaida");
         Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

      } finally {
         pat.close();
      }

      Logger.getLogger(PagrindinisControl.class.getName()).info("Registruotas vartotojas pridejo patinkančią prekę");


   }

   @FXML
   void logout(ActionEvent event) {

      try {
         ((Node) event.getSource()).getScene().getWindow().hide();
         Stage primaryStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         Pane root = loader.load(getClass().getResource("/sample/sample.fxml").openStream());
         primaryStage.setTitle("");
         primaryStage.setScene(new Scene(root, 700, 600));
         primaryStage.show();
      } catch (Exception e) {
         //TODO: handle exception
      }


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
            image = new Image("file:photo.jpg", 142, 158, true, true);
            paveiksl.setImage(image);
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


   @FXML
   void PridetiKrepsel(ActionEvent event) throws SQLException {

      String sql = " insert into KREPSELIS( VARDAS, PREKE, KAINA) values(?,?,?)";
      String prek = lPav.getText();
      String nik = vardasUser.getText();
      String kaina = lKaina.getText();


      try {
         pat = con.prepareStatement(sql);
         pat.setString(1, nik);
         pat.setString(2, prek);
         pat.setString(3, kaina);


         int i = pat.executeUpdate();
         if (i == 1) {
            sample.PopUpMessage.display("Info", "Prekė pridėta į krepšelį");

         }

      } catch (SQLException ex) {
         sample.PopUpMessage.display("Info", "Klaida");
         Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

      } finally {
         pat.close();
      }


   }


   @FXML
   void ZiuretiKrepsel(ActionEvent event) {

      try {
         Stage primaryStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         Pane root = loader.load(getClass().getResource("/Uzsakymai/Krepselis.fxml").openStream());
         KrepselisControl loaduzsak = (KrepselisControl) loader.getController();
         loaduzsak.krepsel(vardasUser.getText());
         KrepselisControl sumos = (KrepselisControl) loader.getController();
         sumos.suma(vardasUser.getText());
         KrepselisControl K = (KrepselisControl) loader.getController();
         K.kiekis(vardasUser.getText());
         primaryStage.setTitle("");
         primaryStage.setScene(new Scene(root, 384, 434));
         primaryStage.show();
      } catch (Exception e) {
         //TODO: handle exception
      }
   }
}
