package kr.storekiosksystem;

public class Menu {
	// 변수
	private String menuName;
	private int price;

	// 생성자
	public Menu() {
		this(null, 0);
	}

	public Menu(String menuName, int price) {
		super();
		this.menuName = menuName;
		this.price = price;
	}

	// 함수
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return menuName + " 가격=" + price;
	}

}
