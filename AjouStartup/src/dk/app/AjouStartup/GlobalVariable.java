package dk.app.AjouStartup;

import android.app.Application;

public class GlobalVariable extends Application {
	
	private final String SERVERIP = "172.30.90.235";
	private final int HTTPPORT = 888;
	
	
	
	
	public String getSERVERIP() {
		return SERVERIP;
	}
	public int getHTTPPORT() {
		return HTTPPORT;
	}
	
	
}
