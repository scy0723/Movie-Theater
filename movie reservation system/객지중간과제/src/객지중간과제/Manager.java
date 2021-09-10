package 객지중간과제;

public class Manager extends Member{
	public Manager(String id, String pwd) {
		super(id, pwd, 1);
	}
	//member에있는 reservation_check을 override함 (manager용 예약확인)
	@Override
	public void reservation_check(Movie[] movieInfo,String UorM) {
		System.out.println("\n관리자가 발행한 티켓 수: "+this.getMy_tickets_actually());
		System.out.println("매출액: "+this.getMy_tickets_actually()*10000);
		super.reservation_check(movieInfo,UorM);
	}
	
}
