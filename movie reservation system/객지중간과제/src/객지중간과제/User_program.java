package 객지중간과제;
import java.io.IOException;
import java.util.Scanner;

//유저프로그램
public class User_program {
	private Scanner scan;
	private int command=0;
	private Ticket ticket;
	private Buffer buffer;
	private TheaterManagement theaterManagement; //유저프로그램에서 매니저가 영화관관리를 하는 클래스
	public User_program(Scanner scan,Buffer buffer){
		this.scan = scan;
		ticket = new Ticket(scan,buffer);
		theaterManagement=new TheaterManagement(scan);
		this.buffer=buffer;
	}
	//유저프로그램실행
	public void user_exe(Member member, Movie[] movieInfo, Member[] members) throws Exception {
		command=0;
		while(command!=5) {
			System.out.print("\n*******유저 프로그램*******\n" + 
					"1. 영화 목록\n"+
					"2. 예매 확인\n"+
					"3. 영화관 관리\n"+
					"4. 예매 취소\n"+
					"5. 종료\n" + 	
					"메뉴를 선택해주세요 : ");
			command=scan.nextInt();
			//영화목록
			try {
				if(command==1)
					ticket.ticket_exe(member,movieInfo);
				//예매 확인
				else if(command==2)
					member.reservation_check(movieInfo,"U"); 
				else if(command==3) {
					if(member.getIsManager()==1) {
						theaterManagement.TheaterManagement_exe(movieInfo,members);//매니저일 경우에만 접근 가능
					}
					else
						throw new InvalidMenuException("Invalid Access.");
				}
				else if(command==4)
					ticket.cancel(member,movieInfo);
				else if(command!=1&&command!=2&&command!=3&&command!=4&&command!=5)
					throw new InvalidMenuException(command+" is an invalid menu number.");
			}
			catch (InvalidMenuException e) {
				e.printExcep();
			}
		}
		System.out.println("영화 예매 프로그램으로 돌아갑니다.\n");
	}
}
