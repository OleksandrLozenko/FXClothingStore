package statistic.statisticuser;
import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import jptv22clothingstore.HomeController;

public class StaticUser implements Initializable {

    @FXML
    private TableView<User> tvUser;

    private HomeController homeController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initTable() {
        tvUser.setItems(FXCollections.observableArrayList(
        homeController.getEntityManager()
            .createQuery("SELECT u FROM User u ORDER BY u.purchaseCount DESC")
            .getResultList()

        ));

        TableColumn<User, Long> idCol = new TableColumn<>("ТОП");
        idCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

        TableColumn<User, String> loginCol = new TableColumn<>("Логин");
        loginCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));


        TableColumn<User, String> purchaseCountCol = new TableColumn<>("Количество покупок");
        purchaseCountCol.setCellValueFactory(cellData -> {
            int purchaseCount = cellData.getValue().getPurchaseCount();
            return new SimpleStringProperty(String.valueOf(purchaseCount));
        });

        tvUser.getColumns().addAll(idCol, loginCol, purchaseCountCol);

        tvUser.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User user = row.getItem();
                    System.out.println("Выбран пользователь с ID: " + user.getId());
                    // Дополнительные действия при двойном щелчке на пользователе
                }
            });
            return row;
        });
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
