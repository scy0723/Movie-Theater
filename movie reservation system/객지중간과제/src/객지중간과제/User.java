package �����߰�����;

//ȸ������ ���̵�,��й�ȣ,������ ��ȭ�� ���� class
public class User extends Member{

	public User(String id, String pwd){
		super(id, pwd, 0);
	}
	//member���ִ� reservation_check�� override�� (user�� ����Ȯ��)
	@Override
	public void reservation_check(Movie[] movieInfo,String UorM) {
		System.out.println("\n*******���� ���*******");
		super.reservation_check(movieInfo,UorM);
	}

}
