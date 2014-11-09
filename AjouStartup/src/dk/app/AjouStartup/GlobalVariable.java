package dk.app.AjouStartup;

import android.app.Application;

public class GlobalVariable extends Application {
	
	private final String SERVERIP = "192.168.43.137";
	private final int HTTPPORT = 8787;
	
	private final String SERVER = "http://" + SERVERIP + ":" + HTTPPORT ;
//	private final String SERVER = "http://192.168.43.137:8787";
	
	private final String MAINPRODUCTNAME = "mtest";
	private final String MAINPRODUCTEXTEN = ".PNG";
	public String getSERVERIP() {
		return SERVERIP;
	}
	public int getHTTPPORT() {
		return HTTPPORT;
	}
	public String getSERVER() {
		return SERVER;
	}
	
	
	
	
	
}
