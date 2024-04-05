/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22clothingstore;

import admin.adminpane.AdminpaneController;
import cloth.cloth.ClothController;
import cloth.listcloth.ListclothController;
import cloth.newcloth.NewClothController;
import cloth.returncloth.ReturnClothController;
import statistic.statisticuser.StaticUser;
import cloth.tablecloth.TableClothController;
import entity.Cloth;
import entity.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import statistic.statisticcloth.StaticCloth;
import users.login.LoginController;
import users.newuser.NewuserController;
import users.profile.ProfileController;

/**
 *
 * @author Oleksandr
 */
public class HomeController implements Initializable {
    private JPTV22СlothingStore app;
    private EntityManager em;
    private MenuItem menuItem;
    @FXML private VBox vbHomeContent;
    @FXML private Label lbInfoHome;
    @FXML private Label lbInfoUser;
    @FXML private MenuItem mbClothsAddnewcloth;
    @FXML private MenuItem mbClothslistcloths;
    @FXML private MenuItem mbClothstablecloth;
    @FXML private MenuItem mbClothsreturncloth;
    @FXML private MenuItem mbTableUser;
    @FXML private MenuItem mbTableCloth;
    @FXML private MenuItem mbEditPrfile;
    @FXML private MenuItem mbAdminPane;
    

    public HomeController() {
        
    }
    @FXML public void clickMenuEditProfile(){
        setMenuItem(mbEditPrfile);
        if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.USER.toString())){
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/profile/profile.fxml"));
            VBox vbProfileRoot = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setHomeController(this);
            profileController.initProfileForm();
            app.getPrimaryStage().setTitle("JPTV22ClothingStore - профайл пользователя");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbProfileRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/profile/profile.fxml", ex);
        }
    }
    @FXML public void clickMenuAddnewcloth(){
        setMenuItem(mbClothsAddnewcloth);
        if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.MANAGER.toString())){
            clickMenuLogin("Вы должны иметь роль "+JPTV22СlothingStore.roles.MANAGER.toString());
            return;
        }
        
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cloth/newcloth/newcloth.fxml"));
            VBox vbnewclothRoot = loader.load();
            NewClothController newClothController = loader.getController();
            newClothController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-добавление новой одежды");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbnewclothRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /cloth/newcloth/newcloth.fxml", ex);
        }
    }


    
    @FXML public void clickMenuLogin(){
        clickMenuLogin("");
    }
    @FXML public void clickMenuLogin(String massage){
        lbInfoHome.setText(massage);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/login/login.fxml"));
            VBox vbLoginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getEntityManager());
            loginController.setHomeController(this);
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-Вход");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/login/login.fxml", ex);
        }
    }
    @FXML public void clickMenuLogout(){
        this.menuItem=null;
        jptv22clothingstore.JPTV22СlothingStore.currentUser = null;
        vbHomeContent.getChildren().clear();
        lbInfoHome.setText("Вы вышли!");
        lbInfoUser.setText("");
        clickMenuLogin();
    }
    
    @FXML public void clickMenuAddNewUser(){
                

        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
            VBox vbNewuserRoot = loader.load();
            NewuserController newuserController = loader.getController();
            newuserController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-регистрация пользователя");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbNewuserRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен //users/newuser/newuser.fxml", ex);
        }
    }
    

    @FXML 
    public void clickMenulistcloths(){
        setMenuItem(mbClothslistcloths);
        if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.USER.toString())){
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cloth/listcloth/listcloth.fxml"));
            HBox hblistclothsRoot = loader.load();
            ListclothController ListclothController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-список одежд");
            List<Cloth> listcloths = getEntityManager().createQuery("SELECT b FROM Cloth b").getResultList();
            hblistclothsRoot.getChildren().clear();
            hblistclothsRoot.getStyleClass().add("border-hbox");
            for (int i = 0; i < listcloths.size(); i++) {
                Cloth cloth = listcloths.get(i);
                FXMLLoader clothLoader = new FXMLLoader();
                clothLoader.setLocation(getClass().getResource("/cloth/cloth/cloth.fxml"));
                ImageView ivCoverRoot = clothLoader.load();
                ivCoverRoot.setCursor(Cursor.OPEN_HAND);
                ivCoverRoot.setId("small_cloth_cover");
                ClothController ClothController = clothLoader.getController();
                ClothController.setHomeController(this);
                ivCoverRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        ClothController.showBook(cloth);
                    }
                });

                ivCoverRoot.setImage(new Image(new ByteArrayInputStream(cloth.getCover())));
                hblistclothsRoot.getChildren().add(ivCoverRoot);
            }
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(hblistclothsRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /cloth/listcloths/listcloth.fxml", ex);
        }
    }
    @FXML 
    public void clickMenureturncloth(){
        setMenuItem(mbClothsreturncloth);
         if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.USER.toString())){
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cloth/returncloth/returncloth.fxml"));
            TableView tvreturnclothRoot = loader.load();
            ReturnClothController ReturnClothController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22ClothingStore - возврат одежды");
            ReturnClothController.setHomeController(this);
            ReturnClothController.initTable();
            vbHomeContent.getChildren().clear();
            tvreturnclothRoot.setPrefSize(vbHomeContent.getWidth(), vbHomeContent.getHeight());
            vbHomeContent.getChildren().add(tvreturnclothRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /cloth/tablecloth/tablecloth.fxml", ex);
        }
    }
    @FXML 
    public void clickMenutablecloth(){
        setMenuItem(mbClothstablecloth);
         if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.USER.toString())){
            clickMenuLogin();
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cloth/tablecloth/tablecloth.fxml"));
            TableView tvClothsRoot = loader.load();
            TableClothController TableClothController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-статистика");
            TableClothController.setHomeController(this);
            TableClothController.initTable();
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(tvClothsRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /cloth/tablecloth/tablecloth.fxml", ex);
        }
    }
    
    @FXML 
    public void clickMenuTableUser(){
        setMenuItem(mbTableUser);
         if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.USER.toString())){
            clickMenuLogin();
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/statistic/statisticuser/staticuser.fxml"));
            TableView tvUser = loader.load();
            StaticUser staticUser = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-Статистика покупателей");
            staticUser.setHomeController(this);
            staticUser.initTable();
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(tvUser);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "/statistic/statisticuser/staticuser.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuTableCloth(){
        setMenuItem(mbTableCloth);
         if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.USER.toString())){
            clickMenuLogin();
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/statistic/statisticcloth/staticcloth.fxml"));
            TableView tvCloth = loader.load();
            StaticCloth staticcloth = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22ClothingStore-Статистика товаров");
            staticcloth.setHomeController(this);
            staticcloth.initTable();
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(tvCloth);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /statistic/statisticcloth/staticcloth.fxml", ex);
        }
    }

    @FXML 
    public void clickMenuShowAdminpane(){
        setMenuItem(mbAdminPane);
         if(JPTV22СlothingStore.currentUser == null){
           clickMenuLogin();
           return;
        }
        if(!JPTV22СlothingStore.currentUser.getRoles().contains(JPTV22СlothingStore.roles.ADMINISTRATOR.toString())){
            clickMenuLogin("Вы должны иметь роль "+JPTV22СlothingStore.roles.ADMINISTRATOR.toString());
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/admin/adminpane/adminpane.fxml"));
            app.getPrimaryStage().setTitle("JPTV22ClothingStore - Панель администратора");
            AnchorPane apAdminRoot = loader.load();
            AdminpaneController adminpaneController = loader.getController();
            adminpaneController.setEntityManager(getApp().getEntityManager());
            adminpaneController.setCbUsers();
            adminpaneController.setCbRoles();
            apAdminRoot.setPrefWidth(JPTV22СlothingStore.WIDTH);
            apAdminRoot.setPrefHeight(JPTV22СlothingStore.HEIGHT);
            this.vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(apAdminRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /admin/adminpane/adminpane.fxml", ex);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbHomeContent.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        if(jptv22clothingstore.JPTV22СlothingStore.currentUser == null){
            lbInfoUser.setText("Авторизуйтесь!");
        }else{
            lbInfoUser.setText("Управление программой от имени пользователя: "+jptv22clothingstore.JPTV22СlothingStore.currentUser.getLogin());
        }
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
        this.em = app.getEntityManager();
    }
    public void setLbInfoUser(String message){
        this.lbInfoUser.setText(message);
    }

    public void setLbInfoHome(String massage) {
       this.lbInfoHome.setText(massage);
    }

    public VBox getVbHomeContent() {
        return this.vbHomeContent;
    }

    private void authorizationInfo(String role, MenuItem menuItem) {
        if(jptv22clothingstore.JPTV22СlothingStore.currentUser == null){
            lbInfoHome.setText("");
            vbHomeContent.getChildren().clear();
            clickMenuLogin("Авторизуйтесь");
            return;
        }
        if(!jptv22clothingstore.JPTV22СlothingStore.currentUser.getRoles().contains(role)){
            clickMenuLogin(jptv22clothingstore.JPTV22СlothingStore.currentUser.getLogin() + " не имеет права на эту операцию");
            
        }
    }

    public Label getLbInfoHome() {
        return lbInfoHome;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
}
