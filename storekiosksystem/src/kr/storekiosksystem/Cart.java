package kr.storekiosksystem;

import java.util.ArrayList;

public class Cart {
	// 변수
	private String userName;
	private ArrayList<Menu> menu;
	private int totalPrice;

	// 생성자
	public Cart() {
		this(null);
	}

	public Cart(String name) {
		super();
		this.userName = name;
		this.menu = new ArrayList<>();
		this.totalPrice = totalPrice;
	}

	public void cartClear() {
		this.menu.clear();
		this.totalPrice = 0;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public String getUserName() {
		return userName;
	}

	public ArrayList<Menu> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<Menu> menu) {
		this.menu = menu;
	}

	public void addCartMenu(Menu menu) {
		this.menu.add(menu);
	}

	public void addTotalPrice(int price) {
		this.totalPrice += price;
	}

	@Override
	public String toString() {
		return "Cart [ menu=" + menu + "]";
	}

}
