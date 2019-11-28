package main;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.DBController;
import views.HomeController;
import views.MainController;
import views.MasterController;

public class Main extends Application {
	public static Main app;
	
	private StackPane dbPage;
	
	private Map<String, MasterController> controllerMap = new HashMap<>();
	
	public MasterController getController(String name) {
		return controllerMap.get(name);
	}
	
	@Override
	public void start(Stage primaryStage) {
		app = this;
		try {
			FXMLLoader mainLoader = new FXMLLoader();
			mainLoader.setLocation(getClass().getResource("/views/MainLayout.fxml"));
			StackPane mainPage = mainLoader.load();
			
			MainController mc = mainLoader.getController();
			mc.setRoot(mainPage);
			controllerMap.put("main", mc);
			
			FXMLLoader homeLoader = new FXMLLoader();
			homeLoader.setLocation(getClass().getResource("/views/HomeLayout.fxml"));
			AnchorPane homePage = homeLoader.load();
			
			HomeController hc = homeLoader.getController();
			hc.setRoot(homePage);
			controllerMap.put("home", hc);
			
			FXMLLoader dbLoader = new FXMLLoader();
			dbLoader.setLocation(getClass().getResource("/views/DBLayout.fxml"));
			dbPage = dbLoader.load();
			
			DBController dc = dbLoader.getController();
			dc.setRoot(dbPage);
			controllerMap.put("db", dc);
		
			dbPage.getChildren().add(mainPage);
			dbPage.getChildren().add(homePage);
//			mainPage.getChildren().add(homePage);
			Scene scene = new Scene(dbPage);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			primaryStage.setTitle("번역기");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	public void loadPane(String name) {
		MasterController c = controllerMap.get(name);
		c.reset();
		Pane pane = c.getRoot();
		dbPage.getChildren().add(pane);
		
		pane.setTranslateX(-800);
		pane.setOpacity(0);
		
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 0);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), toRight, fadeOut);
		
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	public void slideOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 800);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 0);
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), (e)->{ 
			dbPage.getChildren().remove(pane);
		}, toRight, fadeOut);
		
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
}
