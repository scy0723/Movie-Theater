package 객지중간과제;

import java.io.IOException;
import java.util.Scanner;

public class Ticket{
	private Scanner scan;
	public Ticket(Scanner scan,Buffer buffer) {
		this.scan = scan;
	}

	private int command = 0;

	public void ticket_exe(Member member, Movie[] movieInfo) throws InvalidMenuException, IOException, InterruptedException{
		for (int i = 1; movieInfo[i] != null; i++) {
			System.out.println("제목: " + movieInfo[i].getTitle() + " / 상영시간: " + movieInfo[i].getDuration());
		}
		System.out.println("\n1. 예매\n" + "2. 종료\n" + "메뉴를 선택해주세요 : ");
		command = scan.nextInt();
		// 예매
		try {
		if (command == 1) {
			for (int i = 1; movieInfo[i] != null; i++) {
				System.out.println(i + ". 제목: " + movieInfo[i].getTitle() + " / 상영시간: " + movieInfo[i].getDuration());
			}
			reservation(member, movieInfo);
			return;
		} 
		else if (command == 2) {
			System.out.println("유저프로그램으로 돌아갑니다.");
			return;
		}
		else
			throw new InvalidMenuException(command+" is an invalid menu number.");
		}
		
		catch (InvalidMenuException e) {
			e.printExcep();
		}
	}

	// 예매
	public void reservation(Member member, Movie[] movieInfo) throws IOException, InterruptedException {
		if(member.getMy_tickets()>=21) {
			System.out.println("더 이상 예약할 수 없습니다.");
			return;
		}
		System.out.println("예매할 영화를 선택해주세요 : ");
		command=scan.nextInt();
		
		if(command<1||command>Movie.getHowManyMovies()) {
			System.out.println("입력하신 번호에 해당되는 영화가 없습니다.\n유저프로그램으로 돌아갑니다."); //번호에 해당되는 영화가 존재하지 않을 때
			//없는 영화
			return;
		}
		System.out.print("예매할 영화를 선택해주세요 : "+movieInfo[command].getNum());
		//매너저면 해당 영화의 정보도 출력
		if(member instanceof Manager) {
			System.out.println("\n\"" + movieInfo[command].getTitle()+"\"영화의 좌석 예매 점유율: "+String.format("%.2f", movieInfo[command].getTicketsReserved()*100/36.00)+"%" );
			System.out.println("\"" + movieInfo[command].getTitle()+"\"영화의 총 매출액: "+ movieInfo[command].getTicketsReserved()*10000+"\n");
		}
		else {
			System.out.println("제목 : "+movieInfo[command].getTitle()
					+"/ 상영 시간 : "+movieInfo[command].getDuration());
		}
			movieInfo[command].print_seats();
			if(movieInfo[command].getTicketsReserved()==36) {
				System.out.println("해당 영화는 매진입니다.\n"
						+ "예약을 하면 임의의 자리를 예매 받을 수 있습니다.\n"
						+ "예약을 진행하시겠습니까? (1.예/2.아니오)");
				int wait=scan.nextInt();
				if(wait==1) {
					System.out.println("몇 분간 대기하시겠습니까?");
					wait=scan.nextInt();
					System.out.println("예약이 완료되었습니다.");
					movieInfo[command].waitList.add(new Wait_er(member, movieInfo[command],  wait));
					movieInfo[command].waitList.get(movieInfo[command].waitList.size()-1).start();
					return;
				}
				else if(wait==2) {
					System.out.println("유저프로그램으로 돌아갑니다.");
					return;
				}
			}
			System.out.println("******************\n" + 
					"좌석을 선택해주세요(ex A1) :");
			String seat_selection=new String(); scan.nextLine();
			seat_selection=scan.nextLine();
			char row=seat_selection.charAt(0);
			int column=Integer.parseInt(seat_selection.substring(1));
			int row_int=(int)row-64;
			//존재하지 않는 자리를 선택한 경우
			try {
				if(row_int<1||row_int>6||column>6||column<1) {
					String message=seat_selection+" does not exist.";
					throw new NotExistSeatException(message);
				}
				//예약
				if(!movieInfo[command].isReserved(column,row_int)) {
					movieInfo[command].select_seat(column,row_int);
					member.add_rsv(command,seat_selection);
					movieInfo[command].addReservation();
					//예약 정보를 텍스트 파일에 저장해주기위해 추가한 함수
					movieInfo[command].SaveTickets(member.getMy_member_num(), seat_selection);
				}
				//이미 예약된 좌석을 선택한 경우
				else {
					String message=seat_selection+" is already reserved.";
					throw new DuplicatedReservationException(message);
				}
			}
			catch(NotExistSeatException e) {
				e.printExcep();
			}
			catch (DuplicatedReservationException e) {
				e.printExcep();
			}
	}
	/////////////////////////////////
	public synchronized void cancel(Member member, Movie[] movieInfo) throws Exception {
		if(member.getMy_tickets_actually()==0) {
			System.out.println("예약하신 영화가 없습니다.");
			return;
		}
		for(int i=1;i<=member.getMy_tickets();i++) {
			if(member.getMy_ticket_num()[i]==9999) continue;
			System.out.println(i+ ". Ticket number: "+member.getMy_ticket_num()[i]+" / "
			+movieInfo[member.getMov_reserved()[i]].toString()+" / Seat : "+ member.getSeat()[i]); 
		}
		System.out.println("\n어떤 티켓을 취소하시겠습니까? (돌아가기:0)");
		command=scan.nextInt();
		if(command==0)
			return;
		else {
			String seat_selection=member.getSeat()[command];
			char row=seat_selection.charAt(0);
			int column=Integer.parseInt(seat_selection.substring(1));
			int row_int=(int)row-64;
			Canceller cancel=new Canceller(movieInfo[member.getMov_reserved()[command]],seat_selection);
			movieInfo[member.getMov_reserved()[command]].SaveCancelings(member.getMy_member_num(), seat_selection);
			movieInfo[member.getMov_reserved()[command]].cancel_seat(column,row_int); //자리 O으로
			movieInfo[member.getMov_reserved()[command]].cancelReservation();//해당 영화 예매 수-1
			member.cancel_rsv(command);//영화관 총 예매수-1
			System.out.println("해당 티켓을 취소하였습니다.");
			cancel.start();
		}
	}
	
}
