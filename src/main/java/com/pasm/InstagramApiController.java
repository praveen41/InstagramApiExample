/**
 * 
 */
package com.pasm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Peter
 *
 */
@Controller
public class InstagramApiController {

	//
	private final String clientId="xxxx";
	private final String clientSecret="xxxxx";
	private final String redirectUrl="http://localhost:8080/InstagramApiExample/authSuccess";
	private final String accessTokenURl="https://api.instagram.com/oauth/access_token";
	
	
	/**
	 * @param model
	 * @param request
	 * @return
	 * This method is used to redirect to instagram  api.
	 */
	@RequestMapping(value = "/instgramLogin", method = RequestMethod.GET)
	public String buildLoginUrl(Map<String, Object> model,
			HttpServletRequest request) {
				return "redirect:https://api.instagram.com/oauth/authorize/?client_id="+clientId+"&redirect_uri="+redirectUrl+"&response_type=code";
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 * This method is calling after authentication success and it will return a
	 *  response code.
	 * 
	 */
	@RequestMapping(value = "/authSuccess", method = RequestMethod.GET)
	public String authSuccess(Map<String, Object> model,
			HttpServletRequest request) throws IOException {
		String responseCode=request.getParameter("code");
		String userInfoValue=getContent(accessTokenURl, responseCode);
		JSONObject instInfo = new JSONObject(userInfoValue);
		JSONObject userInfo = new JSONObject(instInfo.get("user").toString());
		model.put("userId", userInfo.get("id"));
		model.put("userName", userInfo.get("username"));
		model.put("fullName", userInfo.get("full_name"));
		model.put("profilePic", userInfo.get("profile_picture"));
		return "userInfo";
	}
	
	
	/**
	 * @param httpurl
	 * @param code
	 * @return String content of user basic information and Access token
	 * In this method we are sending request to instagram server to get user information by using response code.
	 * 
	 */
	public String getContent(String httpurl,String code ){
		String line = null;
		try {
        	String urlParameters = "client_id=" + clientId
                    + "&client_secret=" + clientSecret
                    + "&grant_type=authorization_code"
                    + "&redirect_uri=" + redirectUrl
                    + "&code="+code;
            URL url = new URL(httpurl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
			DataOutputStream  dataOutputStream= new DataOutputStream(httpURLConnection.getOutputStream ());
			dataOutputStream.writeBytes(urlParameters);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			line = reader.readLine();
			reader.close();
			dataOutputStream.flush();
			dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }
}
