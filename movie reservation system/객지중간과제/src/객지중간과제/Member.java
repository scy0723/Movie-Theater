package 객지중간과제;

import java.util.ArrayList;
import java.util.Scanner;

public class Member{
	private String id;
	private String pwd;
	private int[] mov_reserved=new int[21];
	private String[] seat=new String[21];
	private int[] my_ticket_num=new int [21];
	private int my_tickets=0;
	private int my_tickets_actually=0;
	private int my_member_num; //회원 번호
	private static int howManyMembers=0; //전체 멤버수를 저장하기 위해 추가
	
	public static int all_tickets=0;
	public static int all_tickets_actually=0;
	
	private ArrayList<Integer> Moviewaiting=new ArrayList<Integer>();
	private ArrayList<Integer> waitingNum=new ArrayList<Integer>();
	
	private int isManager=2;//유저인지 매니저인지 저장하기 위해 추가한 값
	
	
	//이름이 같으면 같은 멤버라고 간주 (아이디중복확인/유저정보출력)
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		Member other = (Member) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Member(String id, String pwd, int isManager) { 
		super();
		this.id = id;
		this.pwd = pwd;
		this.isManager=isManager;
	}
	
	//getters & setters
	public String getId() {
		return id;
	}
	public String getPwd() {
		return pwd;
	}
	public int[] getMov_reserved() {
		return mov_reserved;
	}
	public String[] getSeat() {
		return seat;
	}
	public int[] getMy_ticket_num() {
		return my_ticket_num;
	}
	public int getMy_tickets() {
		return my_tickets;
	}
	public int getMy_tickets_actually() {
		return my_tickets_actually;
	}
	public int getMy_member_num() {
		return my_member_num;
	}
	public static int getHowManyMembers() {
		return howManyMembers;
	}
	public static int getAll_tickets() {
		return all_tickets;
	}
	public static int getAll_tickets_actually() {
		return all_tickets_actually;
	}
	public int getIsManager() {
		return isManager;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setMov_reserved(int[] mov_reserved) {
		this.mov_reserved = mov_reserved;
	}
	public void setSeat(String[] seat) {
		this.seat = seat;
	}
	public void setMy_ticket_num(int[] my_ticket_num) {
		this.my_ticket_num = my_ticket_num;
	}
	public void setMy_tickets(int my_tickets) {
		this.my_tickets = my_tickets;
	}
	public void setMy_tickets_actually(int my_tickets_actually) {
		this.my_tickets_actually = my_tickets_actually;
	}
	public void setMy_member_num(int my_member_num) {
		this.my_member_num = my_member_num;
	}
	public static void setHowManyMembers(int howManyMembers) {
		Member.howManyMembers = howManyMembers;
	}
	public static void setAll_tickets(int all_tickets) {
		Member.all_tickets = all_tickets;
	}
	public static void setAll_tickets_actually(int all_tickets_actually) {
		Member.all_tickets_actually = all_tickets_actually;
	}
	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}
	
	
	public String toString() {
		return(this.id+"님이 발행한 티켓 수: "+ this.my_tickets_actually);
	}
	//유저 영화 예약 정보 추가
	public void add_rsv(int movie_num, String seat) {
		all_tickets++;
		all_tickets_actually++;
		my_tickets++;
		my_tickets_actually++;
		mov_reserved[my_tickets]=movie_num;
		this.seat[my_tickets]=seat;
		my_ticket_num[my_tickets]=all_tickets;
	}
	public void cancel_rsv(int ticket) {
		all_tickets_actually--;
		my_tickets_actually--;
		int tmpMovie_num=mov_reserved[ticket];
		String tmpSeat=this.seat[ticket];
		mov_reserved[ticket]=9999;
		this.seat[ticket]=null;
		my_ticket_num[ticket]=9999;
		
	}
	//유저 영화 예약 확인
	// 변수 UorM은 UserProgram or Management 로 예약정보를 어디서 불러냈는지 확인하기 위해 추가함.
	public void reservation_check(Movie[] movieInfo,String UorM) {
		if(my_tickets_actually==0) {
			System.out.println("\n예약하신 영화가 없습니다.");
			return;
		}
		System.out.println("---------------------------------------------------------------");
		for(int i=1;i<=my_tickets;i++) {
			if(my_ticket_num[i]==9999) continue;
			System.out.println("Ticket number: "+my_ticket_num[i]+" / "+movieInfo[mov_reserved[i]].toString()+" / Seat : "+seat[i]); 
		}
		System.out.println("---------------------------------------------------------------");
		// 유저 프로그램에서 정보출력을 사용했을 때 / 매니저가 영화관관리에서 사용했을 때
		if(UorM.equals(new String("U")))
			System.out.println("Press Enter to go back to User Program");
		if(UorM.equals(new String("M")))
			System.out.println("Press Enter to go back to Theater Management");
		Scanner enter=new Scanner(System.in);
		enter.nextLine();
		return;
	}
	
	public void re_reservation() {
		System.out.println("엥에에에엥");
	}


}
