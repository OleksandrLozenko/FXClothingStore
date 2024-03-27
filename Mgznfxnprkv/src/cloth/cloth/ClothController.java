/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloth.cloth;

import entity.Cloth;
import entity.History;
import entity.User;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jptv22clothingstore.HomeController;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ClothController implements Initializable {
    private Image image;
    private HomeController homeController;
    private Button btnRead;
    private Button btnClose;
    private Stage clothWindow;
    @FXML
    private Pane pClothRoot;
    @FXML
    private ImageView ivCover;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void showBook(Cloth cloth) {
       // System.out.println(cloth.toString());
       clothWindow = new Stage();
       clothWindow.setTitle(cloth.getTitle());
       clothWindow.initModality(Modality.WINDOW_MODAL);
       clothWindow.initOwner(homeController.getApp().getPrimaryStage());
       image = new Image(new ByteArrayInputStream(cloth.getCover()));
       ImageView ivCoverBig = new ImageView(image);
       ivCoverBig.setId("big_cloth_cover");
       VBox vbCloth = new VBox();
       vbCloth.setAlignment(Pos.CENTER);
       vbCloth.getChildren().add(ivCoverBig);
       btnRead = new Button("Купить");
       btnClose = new Button("Закрыть");
       btnClose.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                clothWindow.close();
            }
        });
        
        btnClose.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               clothWindow.close();
            }
        });
       btnRead.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                takeUpCloth(cloth);
            }
        });
        
        btnRead.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               takeUpCloth(cloth);
            }
        });
       HBox hbButtons = new HBox();
       hbButtons.setPrefSize(Double.MAX_VALUE, 29);
       hbButtons.alignmentProperty().set(Pos.CENTER_RIGHT);
       hbButtons.setSpacing(10);
       hbButtons.setPadding(new Insets(10));
       hbButtons.getChildren().addAll(btnRead,btnClose);
       vbCloth.getChildren().add(hbButtons);
       Scene scene = new Scene(vbCloth,450,700);
       scene.getStylesheets().add(getClass().getResource("/cloth/cloth/cloth.css")
               .toExternalForm());
       clothWindow.setScene(scene);
       clothWindow.show();
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

public void takeUpCloth(Cloth cloth) {
    double price = cloth.getPrice();
    User currentUser = jptv22clothingstore.JPTV22СlothingStore.currentUser;
   
    
    if (currentUser.getMoney() >= price) {
        currentUser.setMoney((int) (currentUser.getMoney() - price));
        System.out.println("Товар успешно куплен. Баланс пользователя: " + currentUser.getMoney());
        currentUser.setPurchaseCount(currentUser.getPurchaseCount() + 1);
        cloth.setQuantityOfGoodsPurchased(cloth.getQuantityOfGoodsPurchased() + 1);
        
        
        History history = new History();
        history.setUser(currentUser);
        history.setCloth(cloth);
        history.setGiveClothToReaderDate(new Date());
        history.setPrice(price);
        
        try {
            // Сохраняем историю в базу данных
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().persist(history);
            homeController.getEntityManager().getTransaction().commit();
            System.out.println("Запись в историю успешно сохранена.");
        } catch (Exception e) {
            homeController.getEntityManager().getTransaction().rollback();
            System.err.println("Ошибка при сохранении записи в историю: " + e.getMessage());
        }
    } else {
        System.out.println("Недостаточно средств для покупки товара. Пожалуйста, пополните баланс.");
    }
}





    public void returnCloth(Cloth cloth) {
        
    }
}