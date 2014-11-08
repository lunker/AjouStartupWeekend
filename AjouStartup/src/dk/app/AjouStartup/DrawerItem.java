package dk.app.AjouStartup;

public class DrawerItem {

	
	String itemName;
	boolean isUserPart;

	
	 public DrawerItem(String itemName) {
         this(null, false);
         this.itemName = itemName;
   }
	 

	public DrawerItem(String itemName, boolean isUserPart) {
		super();
		this.itemName = itemName;
		this.isUserPart = isUserPart;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		itemName = itemName;
	}


	public boolean getIsUserPart() {
		return this.isUserPart;
	}

}
