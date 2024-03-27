package statistic.statisticcloth;

import cloth.cloth.ClothController;
import entity.Cloth;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class StaticCloth implements Initializable {

    @FXML
    private TableView<Cloth> tvCloth;
    
    private HomeController homeController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

public void initTable() {

    tvCloth.setItems(FXCollections.observableArrayList(
            homeController.getEntityManager()
                    .createQuery("SELECT c FROM Cloth c ORDER BY c.quantityOfGoodsPurchased DESC")
                    .getResultList()
    ));
    
    TableColumn<Cloth, String> idBookCol = new TableColumn<>("ТОП");
    idBookCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    
    TableColumn<Cloth, String> titleBookCol = new TableColumn<>("Название продукта");
    titleBookCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    
    TableColumn<Cloth, Image> coverBookCol = new TableColumn<>("Фотография");
    coverBookCol.setCellValueFactory(cellData -> {
        Image coverImage = new Image(cellData.getValue().getCoverAsStream());
        return new SimpleObjectProperty<>(coverImage);
    });
    coverBookCol.setCellFactory(param -> new ImageViewTableCell<>());

    TableColumn<Cloth, String> priceBookCol = new TableColumn<>("Цена");
    priceBookCol.setCellValueFactory(cellData -> {
        double price = cellData.getValue().getPrice();
        return new SimpleStringProperty(String.valueOf(price));
    });
    TableColumn<Cloth, String> quantityCol = new TableColumn<>("Количество приобретенных товаров");
    quantityCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantityOfGoodsPurchased())));
    

    tvCloth.getColumns().addAll(idBookCol, titleBookCol, coverBookCol, priceBookCol, quantityCol);
}

public void setHomeController(HomeController homeController) {
    this.homeController = homeController;
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
