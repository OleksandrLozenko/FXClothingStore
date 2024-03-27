/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloth.newcloth;

import entity.Cloth;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import jptv22clothingstore.JPTV22СlothingStore;
import org.imgscalr.Scalr;

/**
 * FXML Controller class
 *
 * @author Oleksandr
 */
public class NewClothController implements Initializable {
    private EntityManager em;
    private JPTV22СlothingStore app;
    private File selectedFile;
    @FXML
    private TextField tfTitleBook;
    @FXML
    private Button btSelectCover;
    @FXML
    private Button btAddnewcloth;
    @FXML
    private Label lbInfo;
    @FXML
    private TextField tfPrice;

    
    
    public NewClothController() {
       
    }
    @FXML
    public void selectCover(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор фотографии");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        btSelectCover.setText("Выбран файл "+selectedFile.getName());
        btSelectCover.disableProperty().set(true);
    }
@FXML
public void addnewcloth() {
    if (tfTitleBook.getText().isEmpty() || tfPrice.getText().isEmpty()) {
        lbInfo.setText("Одежду добавить не удалось. Все поля должны быть заполнены");
        return;
    }
    try {
        int price = Integer.parseInt(tfPrice.getText());
        if (price <= 0) {
            lbInfo.setText("Цена должна быть положительным числом");
            return;
        }

        Cloth cloth = new Cloth();
        cloth.setTitle(tfTitleBook.getText());
        cloth.setPrice(price);

        BufferedImage biBookCover = ImageIO.read(selectedFile);
        BufferedImage biScaledBookCover = Scalr.resize(biBookCover, Scalr.Mode.FIT_TO_WIDTH, 400);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(biScaledBookCover, "jpg", baos);
        cloth.setCover(baos.toByteArray());

        em.getTransaction().begin();
        em.persist(cloth);
        em.getTransaction().commit();
        lbInfo.setText("Одежда успешно добавлена");
    } catch (NumberFormatException | IOException ex) {
        lbInfo.setText("Ошибка при добавлении одежды");
        Logger.getLogger(NewClothController.class.getName()).log(Level.SEVERE, null, ex);
    }
    btSelectCover.disableProperty().set(false);
    selectedFile = null;
    tfTitleBook.setText("");
    tfPrice.setText("");
    btSelectCover.setText("Выбрать фотографию");
}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public JPTV22СlothingStore getApp() {
        return app;
    }

    public void setApp(JPTV22СlothingStore app) {
        this.app = app;
    }
    public static BufferedImage convertToBufferedImage(File file) {
        try {
            // Чтение изображения из файла с использованием ImageIO
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
