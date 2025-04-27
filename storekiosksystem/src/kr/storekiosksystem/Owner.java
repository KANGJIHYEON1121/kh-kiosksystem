package kr.storekiosksystem;

import java.util.ArrayList;

public class Owner {
	// 변수
	private String onerId = "master";
	private String onerPassword = "1234";
	private String storeName;
	private ArrayList<User> userList;
	private ArrayList<Menu> storeMenuList;
	private int dailySales;

	// 생성자
	public Owner() {
		this(null, null, null);
	}

	public Owner(String onerId, String onerPassword, String storeName) {
		super();
		this.onerId = onerId;
		this.onerPassword = onerPassword;
		this.storeName = storeName;
		this.userList = new ArrayList<>();
		this.storeMenuList = new ArrayList<>();
		this.dailySales = 0;
	}
	// 함수

	public String getOnerId() {
		return onerId;
	}

	public void setOnerId(String onerId) {
		this.onerId = onerId;
	}

	public String getOnerPassword() {
		return onerPassword;
	}

	public void setOnerPassword(String onerPassword) {
		this.onerPassword = onerPassword;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public ArrayList<Menu> getStoreMenu() {
		return storeMenuList;
	}

	public void addStoreMenu(Menu menu) {
		this.storeMenuList.add(menu);
	}

	public int getDailySales() {
		return dailySales;
	}

	public void setDailySales(int dailySales) {
		this.dailySales += dailySales;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void addUserList(User user) {
		this.userList.add(user);
	}

}
