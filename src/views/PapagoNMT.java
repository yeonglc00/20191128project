package views;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



public class PapagoNMT {
	public static String KOREAN = "ko";
	public static String ENGLISH = "en";
	public static String FRENCH = "fr";
	public static String CHINESE = "zh-CN";
	public static String VIETNAMESE = "vi";
	public static String CHINESETWO = "zh-TW";
	public static String JAPANESE = "ja";
	public static String SPANISH = "es";
	public static String RUSSIAN = "ru";
	public static String THAI = "th";
	public static String INDONESIA = "id";
	public static String GERMAN = "de";
	public static String LTALIAN = "it";
	
	
	private static String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
	private static String clientId = "hm6kN6lgE6Bh9QcA8_lF";
	private static String clientSecret = "YGe0BjYpYz";
	
	private static String result;

	public static String translate(String source, String target, String text) {
		try {
			text = URLEncoder.encode(text, "UTF-8");
			
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			String postParams = "source=" + source + "&target=" + target + "&text=" + text;
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			BufferedReader br;
			
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = br.readLine()) != null ) {
				response.append(inputLine);
			}
			br.close();
		   result = response.toString();
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return result;
	}
}
