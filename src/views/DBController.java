package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.Main;
import util.JDBCUtil;

public class DBController extends MasterController {

	@FXML
	private TextArea left;
	@FXML
	private TextArea right;

	public void backBtnClick() {
		Main.app.loadPane("main");
	}
	public ArrayList<String> input = new ArrayList<String>();
	public ArrayList<String> result = new ArrayList<String>();
	
	@FXML
	private void initialize() {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Papago";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				input.add(rs.getString("input"));
				result.add(rs.getString("result"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("데이터베이스 오류");
		}
		
		refresh();
	}

	public void refresh() {
		String inputResult = "";
		String resultResult = "";
		for (String i : input)
			inputResult = i + "\n" + inputResult;
		for (String r : result)
			resultResult = r + "\n" + resultResult;
		left.setText(inputResult);
		right.setText(resultResult);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}