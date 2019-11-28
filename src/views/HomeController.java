package views;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.Main;

public class HomeController extends MasterController{
	
	@FXML
	private AnchorPane homePage;
	
	public void nextbtn(){
		Main.app.slideOut(homePage);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
