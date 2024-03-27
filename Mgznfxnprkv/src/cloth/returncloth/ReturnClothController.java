/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloth.returncloth;

import cloth.cloth.ClothController;
import cloth.tablecloth.TableClothController;
import entity.Cloth;
import entity.History;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jptv22clothingstore.HomeController;

/**
 * FXML Controller class
 *
 * @author Oleksandr
 */
public class ReturnClothController implements Initializable {
    private HomeController homeController;
    
    @FXML
    private TableView tvreturnclothRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setHomeController(HomeController homeController) {
        this.homeController=homeController;
    }

    public void initTable() {
        tvreturnclothRoot.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        List<History> listHistoryWhisReaderBooks = homeController.getEntityManager()
                        .createQuery("SELECT h FROM History h WHERE h.returncloth = null AND h.user.id = :userId")
                        .setParameter("userId", jptv22clothingstore.JPTV22СlothingStore.currentUser.getId())
                        .getResultList();
        tvreturnclothRoot.setItems(FXCollections.observableArrayList(listHistoryWhisReaderBooks));
        TableColumn<History, String> idHistoryCol = new TableColumn<>("№");
        idHistoryCol.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        
        TableColumn<History, String> titleHistoryBookCol = new TableColumn<>("Название одежды");
        titleHistoryBookCol.setCellValueFactory(cellData -> cellData.getValue()
                .getCloth().titleProperty());
        
        TableColumn<History, Image> coverHistoryBookCol = 
                new TableColumn<>("Фотография");
        coverHistoryBookCol.setCellValueFactory(cellData -> {
            Image coverImage = new Image(cellData.getValue().getCloth()
                    .getCoverAsStream());
            return new SimpleObjectProperty<>(coverImage);
        });
        coverHistoryBookCol.setCellFactory(param -> new ImageViewTableCell<>());

        TableColumn<History, String> dateHistoryBookCol = 
                new TableColumn<>("Дата выдачи продукта");
        dateHistoryBookCol.setCellValueFactory(cellData -> cellData.getValue()
                .giveUpDateProperty());
        
        tvreturnclothRoot.getColumns().addAll(
                idHistoryCol,
                titleHistoryBookCol,
                coverHistoryBookCol,
                dateHistoryBookCol
        );
        tvreturnclothRoot.setRowFactory(tv ->{
            TableRow<History> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    History history = row.getItem();
                    System.out.println(String.format("Возврат одежды \"%s\"",
                            history.getCloth().getTitle()));
                    this.returncloth(history);
                }
            });
            return row;
        });
    }

    private void returncloth(History history) {
        try {
            history.setReturnCloth(new GregorianCalendar().getTime());
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().merge(history);
            homeController.getEntityManager().getTransaction().commit();
            homeController.getLbInfoHome().setText(String.format("Одежда \"%s\" возврацена", history.getCloth().getTitle()));
            homeController.getLbInfoHome().getStyleClass().add("info-home");
            this.initTable();
        } catch (Exception e) {
            homeController.getLbInfoHome().getStyleClass().add("info-home-error");
            homeController.getLbInfoHome().setText(String.format("Одежду \"%s\" возвратить не удалось!", history.getCloth().getTitle()));
            homeController.getEntityManager().getTransaction().rollback();
        }
        
    }
    class ImageViewTableCell<S, T> extends TableCell<S, T> {
        private final ImageView imageView = new ImageView();
        public ImageViewTableCell() {
            imageView.setFitWidth(50);
            imageView.setFitHeight(80);
                     
        }
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage((Image) item);
                setGraphic(imageView);
            }
        }
    }
}
