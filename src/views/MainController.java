package views;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import util.JDBCUtil;

public class MainController extends MasterController {
	@FXML
	private Button translation;
	@FXML
	private TextField textField;
	@FXML
	private ChoiceBox<Object> choicebox = new ChoiceBox<>();
	@FXML
	//private Label label;
	private TextArea label;
		
	@FXML
	private void initialize() {
		choicebox.getItems().add("독일어");
		choicebox.getItems().add("러시아어");
		choicebox.getItems().add("베트남어");	
		choicebox.getItems().add("스페인어");
		choicebox.getItems().add("영어");
		choicebox.getItems().add("이탈리아어");
		choicebox.getItems().add("인도네시아어");
		choicebox.getItems().add("일본어");
		choicebox.getItems().add("중국어 간체");
		choicebox.getItems().add("중국어 번체");
		choicebox.getItems().add("태국어");
		choicebox.getItems().add("프랑스어");
		
	}
	
	public void clickBtn() {
		String text = textField.getText();
		String source = PapagoNMT.KOREAN , target = PapagoNMT.ENGLISH;
		if(text.isEmpty()) {
			util.Util.showAlert("경고", "입력되지 않았습니다.", AlertType.INFORMATION);
			return;
		}
		String language = (String) choicebox.getValue();
		System.out.println(language);
		switch (language) {
		case "영어":
			target = PapagoNMT.ENGLISH;
			break;
		case "프랑스어":
			target = PapagoNMT.FRENCH;		
			break;
		case "중국어 간체":
			target = PapagoNMT.CHINESE;
			break;
		case "중국어 번체":
			target = PapagoNMT.CHINESETWO;
		case "베트남어":
			target = PapagoNMT.VIETNAMESE;
			break;
		case "일본어":
			target = PapagoNMT.JAPANESE;
			break;
		case "스페인어":
			target = PapagoNMT.SPANISH;
			break;
		case "러시아어":
			target = PapagoNMT.RUSSIAN;
			break;
		case "태국어":
			target = PapagoNMT.THAI;
			break;
		case "인도네시아어":
			target = PapagoNMT.INDONESIA;
			break;
		case "독일어":
			target = PapagoNMT.GERMAN;
			break;
		case "이탈리아어":
			target = PapagoNMT.LTALIAN;
			break;
			
		}
		System.out.println(target);
		String translated = PapagoNMT.translate(source,target,text);

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(translated);
		System.out.println(translated);
		String result = element.getAsJsonObject().get("message").getAsJsonObject().get("result").getAsJsonObject().get("translatedText").getAsString();
		label.setText(result);
		
		inPutWord(text, result);
		
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public void inPutWord(String text, String result) {
		Connection con = JDBCUtil.getConnection(); //DB연결 가져오기
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO Papago VALUES(?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, text);
			pstmt.setString(2, result);
			pstmt.executeUpdate();
			
			DBController dc = (DBController)Main.app.getController("db");
			dc.input.add(text);
			dc.result.add(result);
			dc.refresh();
			
		} catch (Exception e) {
			System.out.println("데이터베이스 쿼리중 오류 발생");
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
	}
		
	}
	
	public void next2() {
//		Main.app.loadPane("db");
		Main.app.slideOut(getRoot());
		System.out.println("aa");
	}
}
