package dk.app.AjouStartup;

import android.app.Application;

public class GlobalVariable extends Application {
	
	private final String SERVERIP = "1.234.75.40";
	private final int HTTPPORT = 998;
	public String getSERVERIP() {
		return SERVERIP;
	}
	public int getHTTPPORT() {
		return HTTPPORT;
	}
	
	
}
