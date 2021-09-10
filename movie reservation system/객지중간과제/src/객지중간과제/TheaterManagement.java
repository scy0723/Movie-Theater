package 객지중간과제;

import java.util.Arrays; 
import java.util.Scanner;

//유저프로그램에서 매니저가 영화관 관리를 하는 클래스
public class TheaterManagement {
	private Scanner scan;
	public TheaterManagement(Scanner scan) { 
		this.scan=scan;
	}
	public void TheaterManagement_exe(Movie[] movieInfo, Member[] members) {
		int command=0;
		while(command!=3) {
			System.out.print("\n*******영화관 관리*******\n" + 
					"1. 영화관 정보\n"+ 
					"2. 유저 정보\n"+ 
					"3. 종료\n" + 	
					"메뉴를 선택해주세요 : ");
			command=scan.nextInt();
			try {
				if(command==1) //영화관 정보
					TheaterInfoManagement(members,movieInfo);
				else if(command==2)//유저 정보
					UserInfoManagement(movieInfo,members);
				else if(command!=1&&command!=2&&command!=3)
					throw new InvalidMenuException(command+" is an invalid menu number.");
			}
			catch (InvalidMenuException e) {
				e.printExcep();
			}
		}
		System.out.println("유저 프로그램으로 돌아갑니다.\n");
	}
	
	public void TheaterInfoManagement(Member[] members,Movie[] movieInfo) {
		Movie[] movieSorted= new Movie[Movie.getHowManyMovies()]; //영화를 점유율 순으로 정렬할 리스트를 만든다.
		for(int i=0;i<Movie.getHowManyMovies();i++) {
			movieSorted[i]=movieInfo[i+1];
		}
		Arrays.sort(movieSorted);
		System.out.println("\n점유된 전체 좌석 수:"+Member.all_tickets);//멤버들이 예약한 총 티켓 수
		System.out.println("전체 좌석 예매 점유율: "+String.format("%.2f", Member.all_tickets*100/ ( 36.00 * Movie.getHowManyMovies()) )+"%" ); //전체 좌석 중 예약된 좌석
		System.out.println("영화관 총 매출액: "+Member.all_tickets*10000);//전체 티켓수 * 가격
		
		System.out.println("---------------------------------------------------------------");
		//1,2위 출력
		for(int i=0;i<2;i++) {
			System.out.println(i+1+"위: "+movieSorted[i].getTitle()+"(예매 좌석:"+movieSorted[i].getTicketsReserved()+")"); 
		}
		//3위 출력
		//(3위와 같은 경우에는 공동 3위로 출력!)
		for(int i=2;i<Movie.getHowManyMovies();i++) {
			if(i!=2 && movieSorted[i].getTicketsReserved()==movieSorted[2].getTicketsReserved()) {
				System.out.println("공동 3위: "+movieSorted[i].getTitle()+"(예매 좌석: "+movieSorted[i].getTicketsReserved()+")");
			}
			else if(i==2){
				System.out.println("3위: "+movieSorted[i].getTitle()+"(예매 좌석: "+movieSorted[i].getTicketsReserved()+")");
			}
		}
		System.out.println("---------------------------------------------------------------");
		System.out.println("Press Enter to go back to Theater Management");
		Scanner enter = new Scanner(System.in);
		enter.nextLine();
	}
	
	public void UserInfoManagement(Movie[] movieInfo,Member[] members) {
		scan.nextLine();
		System.out.print("\n찾으려는 ID: ");
		String id=scan.nextLine();
		//멤버 리스트에서 id를 찾는다
		try {
			for(int i=0;i<Member.getHowManyMembers();i++) {
				if(members[i].getId().equals(id)) {
					System.out.print("\n"+members[i].toString());
					if(members[i] instanceof Manager)
						System.out.print("\n관리자 멤버입니다:");//관리자 멤버면 관리자 정보도 출력
					members[i].reservation_check(movieInfo,"M");
					return;
				}	
			}
			throw new InvalidMenuException("No such member");
		}
		catch (InvalidMenuException e) {
			e.printExcep();
		}
	}
}
