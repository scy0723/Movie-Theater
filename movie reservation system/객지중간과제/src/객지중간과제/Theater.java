package 객지중간과제;

import java.util.Scanner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;


public class Theater {
	private Scanner scan = new Scanner(System.in);//variable로 scanner를 갖게 함

	private int command = 0;

	public Movie[] movieInfo = new Movie[20];// 영화는 유저가 누구든 똑같기 때문에 영화예매프로그램에서부터 입력해주기 위해(영화의 번호,제목,시간,좌석을 담는 클래스)
	//[1]부터 사용

	private Member[] members = new Member[21];
	// 회원명단, [0]부터사용
	
	private Buffer buff;

	
	private User_program user_program = new User_program(scan,buff);
	//유저프로그램으로 넘어갈 때 사용

	public void movie_theater_exe() throws Exception {
		while (command != 3) { 
			System.out.print("\n*******영화 예매 프로그램*******\n" + "1. 로그인\n" + "2. 회원가입\n" + "3. 종료\n" + "메뉴를 선택해주세요 :  ");
			command = scan.nextInt();
			try {
			if (command == 1)
				log_in();
			else if (command == 2)
				sign_up(buff);
			// 1,2,3이 아닌 숫자를 입력한 경우 예외처리
			else if (command != 1 && command != 2 && command != 3)
				throw new InvalidMenuException(command+" is an invalid menu number.");
			}
			catch (InvalidMenuException e) {
				e.printExcep();
			}
		}
		System.out.println("영화 예매 프로그램을 종료합니다.");
	}

	// 로그인
	public void log_in() throws Exception {
		System.out.print("\n*******로그인*******\n" + "ID : ");
		String id = scan.next();
		System.out.print("Password : ");
		String pwd = scan.next();
		try {
			//id가 존재하고 pwd가 맞을때 유저프로그램 진행
		for (int i = 0; i < Member.getHowManyMembers(); i++) {
			if (members[i].getId().equals(id)) {
				if (members[i].getPwd().equals(pwd)) {
					
					user_program.user_exe(members[i], this.movieInfo,members);// 유저프로그램 실행
					return;
				}
			}
		}
		//없는id나 틀린 pwd면 로그인 실패 예외처리
		throw new InvalidLoginException("Login failed.");
		}
		catch (InvalidLoginException e) {
			e.printExcep();
		}
	}

	// 회원 가입
	public void sign_up(Buffer buffer) throws DuplicatedIdException, InvalidMenuException {
		//회원이 이미 20명일 경우
		if (Member.getHowManyMembers()>=20) {
			System.out.println("회원은 최대 20명 입니다.\n영화 예매 프로그램으로 돌아갑니다.");
			return;
		}
		//아이디가 사용 가능한가?
		try {
			System.out.print("\n*******회원 가입*******\n" + "ID : ");
			String id = scan.next();
			System.out.print("Password : ");
			String pwd = scan.next();
			System.out.print("Manager : ");
			int isManager = scan.nextInt();
			Member m = new Member(id, pwd, isManager);
			//존재한다면 아이디중복 예외처리
			for (Member a : members) {
				if (m.equals(a)) {
					String message = id + " already exists";
					throw new DuplicatedIdException(message);
				}
			}
			//isManager에 따라서 Member배열에 알맞은 클래스로 회원 저장 (user/manager)
			if (isManager == 1) 
				members[Member.getHowManyMembers()] = new Manager(id, pwd);
			else if (isManager == 0)
				members[Member.getHowManyMembers()] = new User(id, pwd);
			//1도 2도 아니라면 invalid user type 메세지
			else throw new InvalidMenuException(isManager+" is an invalid user type number.");
			//멤버 저장
			members[Member.getHowManyMembers()].setMy_member_num(Member.getHowManyMembers());
			//회원가입한 멤버의 정보추가해  write한다
			PrintWriter wtUser = null;
			try {
				wtUser = new PrintWriter(new FileOutputStream("UserList.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("Sign up failed. UserList not found.");
				System.out.println("회원가입 실패. 유저 리스트를 찾지 못했습니다.");
				System.exit(0);
			}
			wtUser.println(Member.getHowManyMembers() + 1);//첫줄에는 회원수 입력
			for (int i = 0; i <= Member.getHowManyMembers(); i++) {
				wtUser.println(members[i].getIsManager() + " " + members[i].getId() + " " + members[i].getPwd());
			}
			Member.setHowManyMembers(Member.getHowManyMembers()+1);//멤버수 추가
			wtUser.close();
			return;
		}

		catch (DuplicatedIdException e) {
			e.printExcep();
		}
		catch (InvalidMenuException e) {
			e.printExcep();
		}

	}
	//파일에서 읽은 회원정보를 담는 함수
	public void GetUserInfoFromFile(String id, String pwd, String isManager) {
		if(isManager.equals("1")) {
			members[Member.getHowManyMembers()] = new Manager(id, pwd);
		}
		else if(isManager.equals("0")){
			members[Member.getHowManyMembers()] = new User(id, pwd);
		}
		members[Member.getHowManyMembers()].setMy_member_num(Member.getHowManyMembers());
		Member.setHowManyMembers(Member.getHowManyMembers()+1);
	}
	//파일에서 읽은 영화 예약정보를 담는 함수
	public void GetReservationInfoFromFile(int movNum, int memNum,String seat) {
		members[memNum].add_rsv(movNum,seat);
		char row=seat.charAt(0);
		int column=Integer.parseInt(seat.substring(1));
		int row_int=(int)row-64;
		movieInfo[movNum].select_seat(column, row_int);
		movieInfo[movNum].addReservation();
	}
	
	// main
	public static void main(String[] args) throws Exception {
		Buffer buffer= new Buffer();
		Theater mvt = new Theater();
		Scanner scanFile = null;

		//영화 정보 불러오기
		try {
			scanFile = new Scanner(new FileInputStream("MovieList.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Movie Information not found");
			System.out.println("영화 정보를 찾지 못했습니다.");
			System.exit(0);
		}
		//슬래쉬를 기준 substring들로 쪼개 으로 제목, 시간을 읽고 movieInfo 리스트에 저장한다.
		String slash = "/";
		String InfoLine;
		int moviesQuantt = scanFile.nextInt();
		scanFile.nextLine();
		Movie.setHowManyMovies(moviesQuantt);
		for (int i = 1; i <= Movie.getHowManyMovies(); i++) {
			InfoLine = scanFile.nextLine();
			String Title = InfoLine.substring(0, InfoLine.indexOf(slash));
			String start = InfoLine.substring(InfoLine.indexOf(slash) + 1, InfoLine.lastIndexOf(slash)) + ":00~";
			String end = InfoLine.substring(InfoLine.lastIndexOf(slash) + 1) + ":00";
			mvt.movieInfo[i] = new Movie(i, Title, start + end);
		}
		
		// 유저 정보 불러오기
				try {
					scanFile = new Scanner(new FileInputStream("UserList.txt"));
				} catch (FileNotFoundException e) {
					System.out.println("User Information not found");
					System.out.println("회원 정보를 찾지 못했습니다.");
					System.exit(0);
				}
				//space를 기준으로 substring들로 쪼개 유저 정보를 읽는다.
				String blank = " ";
				if (scanFile.hasNextLine())
					scanFile.nextLine();
				while (scanFile.hasNextLine()) {
					InfoLine = scanFile.nextLine();
					String isManager= InfoLine.substring(0, InfoLine.indexOf(blank));
					String id= InfoLine.substring(InfoLine.indexOf(blank)+1, InfoLine.lastIndexOf(blank));
					String pwd = InfoLine.substring(InfoLine.lastIndexOf(blank) + 1);
					mvt.GetUserInfoFromFile(id, pwd, isManager);
				}
				
		//예약 정보 불러오기
		try {
			scanFile = new Scanner(new FileInputStream("BookedSeats.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Reservation Information not found");
			System.out.println("영화 예약 정보를 찾지 못했습니다.");
			System.exit(0);
		}
		while (scanFile.hasNext()) {
			int movNum=scanFile.nextInt();
			int memNum=scanFile.nextInt();
			String seat=scanFile.next();
			mvt.GetReservationInfoFromFile(movNum, memNum, seat);
		}
		
		scanFile.close();
		mvt.movie_theater_exe();
	}
}

