package kr.storekiosksystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProcessManager {
//	변수
	private static ProcessManager intance = new ProcessManager();
//	메뉴 테이블 타이틀
	public static String menuTitle;
//	유저 테이블 타이틀
	public static String userTitle;

//	생성자
	private ProcessManager() {
	}

//	멤버함수
	public static ProcessManager getIntance() {
		return intance;
	};

// 	로그인 함수
	public User loginFunc(ArrayList<User> userList) {
		Scanner scan = new Scanner(System.in);

		System.out.print("아이디를 입력해주세요: ");
		String userId = scan.nextLine();
		for (User userData : userList) {
			if (userData.getUserId().equals(userId)) {
				System.out.print("비밀번호를 입력해주세요: ");
				String userPassword = scan.nextLine();
				if (userData.getUserPassword().equals(userPassword)) {
					clear();
					System.out.printf("%s님 환영합니다.\n", userData.getUserName());
					return userData;
				} else {
					System.out.println("비밀번호가 일치하지 않습니다.");
					scan.nextLine();
					return null;
				}
			} // end of if
		} // end of for

		System.out.println("아이디가 존재하지 않습니다.");
		scan.nextLine();
		return null;
	}

// 	회원가입 함수
	public void userJoin(ArrayList<User> userList) {
		try {
			Scanner scan = new Scanner(System.in, "EUC-KR");
			FileOutputStream fo = new FileOutputStream("D:/javaWorkspace/storekiosksystem/res/userList.txt");
//			FileOutputStream fo = new FileOutputStream("C:/workspace/storekiosksystem/res/userList.txt");
			PrintStream out = new PrintStream(fo);

			out.printf("%s", userTitle);

			// String userId, String userPassword, String userName, String userPhoneNum
			while (true) {
				System.out.print("이름을 입력해주세요: ");
				String userName = scan.nextLine();
				boolean isNameCheck = Pattern.matches("^[가-힣]*$", userName);
				if (!isNameCheck) {
					System.out.println("이름을 다시 입력해주세요");
					continue;
				}

				System.out.print("아이디를 입력해주세요: ");
				String userId = scan.nextLine();
				boolean isIdCheck = Pattern.matches("^[a-zA-Z]*$", userId);
				if (!isIdCheck) {
					System.out.println("영문으로 만들어주세요.");
					continue;
				}
				for (User data : userList) {
					if (data.getUserId().equals(userId)) {
						System.out.println("이미 존재하는 아이디 입니다.");
						return;
					}
				}
				System.out.print("비밀번호를 입력해주세요: ");
				String userPassword = scan.nextLine();
				boolean isPwCheck = Pattern.matches("^[0-9]*$", userPassword);
				if (!isPwCheck) {
					System.out.println("숫자만 입력해주세요.");
					continue;
				}
				System.out.print("전화번호를 입력해주세요: ");
				String userPhoneNum = scan.nextLine();
				boolean isPhoneCheck = Pattern.matches("^[0-9]*$", userPhoneNum);
				if (!isPhoneCheck) {
					System.out.println("숫자만 입력해주세요.");
					continue;
				}

				User user = new User(userId, userPassword, userName, userPhoneNum);
				userList.add(user);
				break;
			}

			for (User data : userList) {
				out.printf("\n%s,%s,%s,%s", data.getUserId(), data.getUserPassword(), data.getUserName(),
						data.getUserPhoneNum());
			}

			userSave(userList);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

// 	메뉴 수정
	public void editMenu(ArrayList<Menu> storeMenu) {
		Scanner scan = new Scanner(System.in, "EUC-KR");
		System.out.print("수정 메뉴: ");
		String editMenu = scan.nextLine();
		boolean searchFlag = false;

		while (true) {
			for (Menu data : storeMenu) {
				if (data.getMenuName().equals(editMenu)) {
					searchFlag = true;
					System.out.print("메뉴 이름 수정: ");
					String menu = scan.nextLine();
					boolean isMenuCheck = Pattern.matches("^[가-힣]*$", menu);
					if (!isMenuCheck) {
						System.out.println("한글로 메뉴를 입력해주세요.");
						continue;
					}

					System.out.print("메뉴 가격 수정: ");
					int price = 0;
					try {
						price = Integer.parseInt(scan.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("숫자를 입력해주세요.");
						continue;
					}

					data.setMenuName(menu);
					data.setPrice(price);

					menuSave(storeMenu);
				}
			}
			break;
		}

		if (!searchFlag) {
			System.out.printf("%s 메뉴 찾지 못 하였습니다.\n", editMenu);
		}
	}

// 	메뉴 삭제
	public void deleteMenu(ArrayList<Menu> storeMenu) {
		Scanner scan = new Scanner(System.in, "EUC-KR");

		System.out.print("메뉴 삭제: ");
		String deleteMenu = scan.nextLine();
		boolean deleteFlag = false;

		for (int i = 0; i < storeMenu.size(); i++) {
			if (storeMenu.get(i).getMenuName().equals(deleteMenu)) {
				storeMenu.remove(i);
				System.out.println("삭제 완료!");
				deleteFlag = true;
			}
		}
		if (!deleteFlag) {
			System.out.printf("%s를 찾지 못 하였습니다.", deleteMenu);
		}

		menuSave(storeMenu);

	}

//	 메뉴 입력 함수
	public void menuInput(ArrayList<Menu> storeMenu) {
		Scanner scan = new Scanner(System.in, "EUC-KR");

		// 파일메뉴 추가
		while (true) {

			System.out.print("메뉴 등록: ");
			String menuInput = scan.nextLine();
			boolean isMenuCheck = Pattern.matches("^[가-힣]*$", menuInput);
			if (!isMenuCheck) {
				System.out.println("한글로 메뉴를 입력해주세요.");
				continue;
			}

			System.out.print("가격 등록: ");
			int priceInput = 0;
			try {
				priceInput = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력해주세요");
				continue;
			}
			Menu menu = new Menu(menuInput, priceInput);
			storeMenu.add(menu);

			break;
		}

		// 추가 메뉴 저장
		menuSave(storeMenu);

	}

// 	메뉴 로드 함수
	public void menuLoad(ArrayList<Menu> storeMenu) {
		FileInputStream fi;

		try {
			fi = new FileInputStream("D:/javaWorkspace/storekiosksystem/res/menuList.txt");
//			fi = new FileInputStream("C:/workspace/storekiosksystem/res/menuList.txt");
			Scanner scan = new Scanner(fi);

			if (scan.hasNextLine()) {
				userTitle = scan.nextLine();
			}

			while (true) {
				if (!scan.hasNextLine()) {
					break;
				}
				String data = scan.nextLine();
				String[] tokens = data.split(",");
				String menuName = tokens[0];
				int price = Integer.parseInt(tokens[1]);
				Menu menu = new Menu(menuName, price);
				storeMenu.add(menu);
			}
		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		}
	}

// 	메뉴 저장 함수
	public void menuSave(ArrayList<Menu> storeMenu) {
		try {
			FileOutputStream fo = new FileOutputStream("D:/javaWorkspace/storekiosksystem/res/menuList.txt");
//			FileOutputStream fo = new FileOutputStream("C:/workspace/storekiosksystem/res/menuList.txt");
			PrintStream out = new PrintStream(fo);
			Scanner scan = new Scanner(System.in);

			// 파일메뉴 추가
			out.printf("%s", menuTitle);

			for (Menu data : storeMenu) {
				out.printf("\n%s,%d", data.getMenuName(), data.getPrice());
			}

			try {
				out.close();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

// 	회원 저장 함수
	public void userSave(ArrayList<User> userList) {
		try {
			FileOutputStream fo = new FileOutputStream("D:/javaWorkspace/storekiosksystem/res/menuList.txt");
//			FileOutputStream fo = new FileOutputStream("C:/workspace/storekiosksystem/res/menuList.txt");
			PrintStream out = new PrintStream(fo);
			Scanner scan = new Scanner(System.in);

			// 파일메뉴 추가
			out.printf("%s", menuTitle);

			for (User data : userList) {
				out.printf("\n%s,%s,%s,%s", data.getUserId(), data.getUserPassword(), data.getUserName(),
						data.getUserPhoneNum());
			}

			try {
				out.close();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

// 	회원 정보 보여주기 함수
	public void showUser(ArrayList<User> userList) {
		System.out.println();
		System.out.println("―――――――――― 회원 리스트 ――――――――――");
		for (int i = 0; i < userList.size(); i++) {
			System.out.printf("%2d. %s\n", i + 1, userList.get(i).toString());
		}
		System.out.println("――――――――――――――――――――――――――");
		System.out.println();
	}

// 	회원 정보 로드 함수
	public void userLoad(ArrayList<User> userList) {
		FileInputStream fi;

		try {
			fi = new FileInputStream("D:/javaWorkspace/storekiosksystem/res/userList.txt");
//			fi = new FileInputStream("C:/workspace/storekiosksystem/res/userList.txt");
			Scanner scan = new Scanner(fi);

			if (scan.hasNextLine()) {
				userTitle = scan.nextLine();
			}

			while (true) {
				if (!scan.hasNextLine()) {
					break;
				}
				String data = scan.nextLine();
				String[] tokens = data.split(",");
				String id = tokens[0];
				String pw = tokens[1];
				String name = tokens[2];
				String phone = tokens[3];
				User user = new User(id, pw, name, phone);
				userList.add(user);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

// 	메뉴 보여주기
	public void showMenu(Owner owner) {
		System.out.println();
		System.out.println("―――――――――― 메뉴 리스트 ――――――――――");
		for (int i = 0; i < owner.getStoreMenu().size(); i++) {
			System.out.printf("%2d. %s\n", i + 1, owner.getStoreMenu().get(i).toString());
		}
		System.out.println("――――――――――――――――――――――――――");
		System.out.println();

	}

//	클리어 함수
	public static void clear() {
		try {
			String operatingSystem = System.getProperty("os.name");
			if (operatingSystem.contains("Windows")) {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			} else {
				ProcessBuilder pb = new ProcessBuilder("clear");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
