package jptv22clothingstore;

import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.PassEncrypt;

public class JPTV22СlothingStore extends Application {
    public static enum roles {ADMINISTRATOR, MANAGER, USER};
    public static User currentUser;
    private final EntityManager em;
    private Stage primaryStage;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public JPTV22СlothingStore() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22FXLibraryPU");
        em = emf.createEntityManager();
        createSuperUser();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JPTV22ClothingStore");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            if (homeController == null) {
                System.out.println("HomeController is null");
            } else {
                homeController.setApp(this);
            }
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/jptv22clothingstore/home.css").toExternalForm());
            primaryStage.centerOnScreen();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void createSuperUser() {
        try {
            em.createQuery("SELECT user FROM User user WHERE user.login = :login")
                            .setParameter("login", "admin")
                            .getSingleResult();
            
        } catch (Exception e) {
            User user = new User();
            user.setFirstname("Oleksandr");
            user.setLastname("Lozenko");
            user.setLogin("admin");
            PassEncrypt pe = new PassEncrypt();
            user.setPassword(pe.getEncryptPassword("12345",pe.getSalt()));
            user.getRoles().add(jptv22clothingstore.JPTV22СlothingStore.roles.ADMINISTRATOR.toString());
            user.getRoles().add(jptv22clothingstore.JPTV22СlothingStore.roles.MANAGER.toString());
            user.getRoles().add(jptv22clothingstore.JPTV22СlothingStore.roles.USER.toString());
            try {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().setRollbackOnly();
            }
        }
    }
}
