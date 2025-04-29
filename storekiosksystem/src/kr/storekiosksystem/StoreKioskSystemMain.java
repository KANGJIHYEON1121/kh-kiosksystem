package kr.storekiosksystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StoreKioskSystemMain {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Owner owner = new Owner();
		ProcessManager pm = ProcessManager.getIntance();
		owner.setStoreName("coffee");

		// 메뉴, 유저 정보 불러오기
		pm.menuLoad(owner.getStoreMenu());
		pm.userLoad(owner.getUserList());

		// 변수선언
		boolean stopFlag = false;
		boolean isLogin = false; // 로그인 상태 확인
		boolean isManager = false; // 관리자 상태 확인
		boolean loginFlag = false;
		User currentUser = null;

		// 반복(입력, 출력, 연산) 반복문 4가지(카운트셀때 for, 계속돌때 while, 한번은 해줄때 do-while, 향상된 for문)
		while (!stopFlag) {

			while (!loginFlag) {

				pm.clear();
				System.out.println("――――――――――" + owner.getStoreName() + "―――――――――――");
				System.out.println("1.로그인 ");
				System.out.println("2.회원가입 ");
				System.out.println("――――――――――――――――――――――――");
				System.out.print(">>");

				int _no = 0;
				try {
					_no = Integer.parseInt(scan.nextLine());
					if (0 < _no && _no <= 2) {
					} else {
						System.out.println("번호(1~2)를 다시 입력해주세요");
						scan.nextLine();

					}
				} catch (Exception e) {
					System.out.println("잘못된 입력입니다 다시 입력해주세요.");
					scan.nextLine();
				}

				switch (_no) {
				case 1:
					// 로그인
					currentUser = pm.loginFunc(owner.getUserList());
					if (currentUser != null) {
						// 로그인 성공
						isLogin = true;
						loginFlag = true;
						if (currentUser.getUserId().equals("master")) {
							isManager = true;
							System.out.println("관리자 모드 입니다.");
						}
					} else {
						// 로그인 실패
						isLogin = false;
					}
					break;
				case 2:
					// 회원가입
					pm.userJoin(owner.getUserList());
					break;
				}
			}

			int no;
			// 메뉴선택
			while (true) {
				// 메뉴 디스플레이
				menuDisplay(owner);

				// 메뉴선택실행
				try {
					no = Integer.parseInt(scan.nextLine());
					if ((0 < no && no <= 7)) {
						break;
					} else {
						System.out.println("번호(1~7)를 다시 입력해주세요");
					}
				} catch (NumberFormatException e) {
					System.out.println("잘못된 입력입니다 숫자를 입력해주세요.");
				}
			}

			switch (no) {
			case 1: // 메뉴선택기능

				while (true) {
					pm.clear();
					pm.showMenu(owner);
					System.out.print("메뉴 입력(exit:-1): ");
					try {
						int menuNum = Integer.parseInt(scan.nextLine());
						if (menuNum < -1 || menuNum > owner.getStoreMenu().size()) {
							System.out.println("메뉴 리스트에 없습니다. 다시 선택해주세요.");
							scan.nextLine();
							continue;
						}
						if (menuNum == -1) {
							pm.clear();
							break;
						}
						Menu myList = owner.getStoreMenu().get(menuNum - 1);
						currentUser.getUserCart().addCartMenu(myList);
						currentUser.getUserCart().addTotalPrice(myList.getPrice());
						System.out.println(owner.getStoreMenu().get(menuNum - 1));
						System.out.print("장바구니에 추가완료 계속(Enter)/중지(N) ");
						String choice = scan.nextLine();
						pm.clear();
						if (choice.toLowerCase().equals("n")) {
							break;
						}
					} catch (NumberFormatException e) {
					}
				}

				break;
			case 2: // 장바구니 확인 기능
				pm.clear();
				System.out.println("――――――――――" + "장바구니" + "―――――――――――");

				if (currentUser.getUserCart().getMenu().isEmpty()) {
					System.out.println("장바구니가 비었습니다.");
				} else {
					for (int i = 0; i < currentUser.getUserCart().getMenu().size(); i++) {
						System.out.println(currentUser.getUserCart().getMenu().get(i).toString());
					}
					System.out.println("-----------------------------------------");
					System.out.printf("총 상품 갯수: %d개\n", currentUser.getUserCart().getMenu().size());
				}

				System.out.println("―――――――――――――――――――――――――");
				break;
			case 3: // 장바구니 비우기 기능
				pm.clear();
				currentUser.getUserCart().cartClear();
				System.out.println("――――――――――" + "장바구니 초기화" + "―――――――――――");
				System.out.println("장바구니 초기화 완료.");
				break;
			case 4: // 결제하기 기능
				pm.clear();
				System.out.println("――――――――――" + "장바구니" + "―――――――――――");

				if (currentUser.getUserCart().getMenu().isEmpty()) {
					System.out.println("장바구니에 상품이 없습니다.");
				} else {
					for (int i = 0; i < currentUser.getUserCart().getMenu().size(); i++) {
						System.out.println(currentUser.getUserCart().getMenu().get(i).toString());
					}
					System.out.println("―――――――――――――――――――――――――");
					System.out.printf("총 금액: %d원\n", currentUser.getUserCart().getTotalPrice());

					owner.setDailySales(currentUser.getUserCart().getTotalPrice());
					currentUser.getUserCart().cartClear();
				}

				break;
			case 5: // 관리자모드
//				pm.clear();
				if (isManager) {
					boolean _stopFlag = false;
					while (!_stopFlag) {
						pm.clear();
						// 관리자 메뉴 디스플레이
						masterMenu(currentUser);

						int menuNum = 0;

						// 메뉴선택실행
						try {
							menuNum = Integer.parseInt(scan.nextLine());
							if ((0 < menuNum && menuNum <= 7)) {
								switch (menuNum) {
								case 1: {
									pm.clear();
									// 회원리스트확인
									pm.showUser(owner.getUserList());
									System.out.print("계속(enter): ");
									scan.nextLine();
									break;
								}
								case 2: {
									pm.clear();
									// 메뉴리스트확인
									pm.showMenu(owner);
									System.out.print("계속(enter): ");
									scan.nextLine();
									break;
								}
								case 3: {
									pm.clear();
									// 메뉴추가
									pm.showMenu(owner);
									pm.menuInput(owner.getStoreMenu());
									System.out.print("계속(enter): ");
									scan.nextLine();
									break;
								}
								case 4: {
									pm.clear();
									// 메뉴삭제
									pm.showMenu(owner);
									pm.deleteMenu(owner.getStoreMenu());
									System.out.print("계속(enter): ");
									scan.nextLine();
									break;
								}
								case 5: {
									pm.clear();
									// 메뉴수정
									pm.showMenu(owner);
									pm.editMenu(owner.getStoreMenu());
									System.out.print("계속(enter): ");
									scan.nextLine();
									break;
								}
								case 6: { // 하루매출확인
									pm.clear();
									System.out.println();
									System.out.println("―――――――――― 오늘의 매출 ――――――――――");
									System.out.printf("▶ 총 매출: %,d원\n", owner.getDailySales());
									System.out.println("――――――――――――――――――――――――――――");
									System.out.print("계속하려면 Enter 키를 눌러주세요...");
									scan.nextLine();
									System.out.println();
								}
								case 7: { // 사용자모드
									pm.clear();
									_stopFlag = true;
									break;
								}
								}// end of switch
							} else {
								System.out.println("번호(1~7)를 다시 입력해주세요");
							}
						} catch (NumberFormatException e) {
							System.out.println("잘못된 입력입니다 숫자를 입력해주세요.");
						}

					} // end of while
				} else {
					pm.clear();
					System.out.println("관리자 모드는 관리자만 사용 가능합니다.");
				} // end of if 관리자 상태 확인문
				break;
			case 6:// 로그아웃
				isLogin = false;
				isManager = false;
				loginFlag = false;
				System.out.printf("%s(%s)님 로그아웃", currentUser.getUserId(), currentUser.getUserName());
				scan.nextLine();
				break;
			case 7:// 종료
				stopFlag = true;
				System.out.println("System end");
				break;
			}
		} // end of while
	} // end of main

	// 메뉴 디스플레이
	private static void menuDisplay(Owner owner) {
		System.out.println();
		System.out.println("-----------------------------------------");
		System.out.printf("          Welcome to %s \n", owner.getStoreName());
		System.out.println("-----------------------------------------");
		System.out.println("1. 메뉴 선택");
		System.out.println("2. 장바구니 확인");
		System.out.println("3. 장바구니 초기화");
		System.out.println("4. 결제하기");
		System.out.println("5. 관리자 메뉴");
		System.out.println("6. 로그아웃");
		System.out.println("7. 프로그램 종료");
		System.out.println("-----------------------------------------");
		System.out.println();
		System.out.print(">>");
	}

	// 관리자 모드 메뉴 디스플레이
	private static void masterMenu(User currentUser) {
		System.out.println();
		System.out.println("―――――――――― " + currentUser.getUserName() + " 모드 ―――――――――――");
		System.out.println("1. 회원 리스트 확인");
		System.out.println("2. 메뉴 리스트 확인");
		System.out.println("3. 메뉴 추가");
		System.out.println("4. 메뉴 삭제");
		System.out.println("5. 메뉴 수정");
		System.out.println("6. 하루 매출 확인");
		System.out.println("7. 사용자 모드 전환");
		System.out.println("――――――――――――――――――――――――――");
		System.out.println();
		System.out.print(">>");
	}

}
