package 객지중간과제;

//회원들의 아이디,비밀번호,예매한 영화를 담을 class
public class User extends Member{

	public User(String id, String pwd){
		super(id, pwd, 0);
	}
	//member에있는 reservation_check을 override함 (user용 예약확인)
	@Override
	public void reservation_check(Movie[] movieInfo,String UorM) {
		System.out.println("\n*******예매 목록*******");
		super.reservation_check(movieInfo,UorM);
	}

}
