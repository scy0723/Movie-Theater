package �����߰�����;

public class Manager extends Member{
	public Manager(String id, String pwd) {
		super(id, pwd, 1);
	}
	//member���ִ� reservation_check�� override�� (manager�� ����Ȯ��)
	@Override
	public void reservation_check(Movie[] movieInfo,String UorM) {
		System.out.println("\n�����ڰ� ������ Ƽ�� ��: "+this.getMy_tickets_actually());
		System.out.println("�����: "+this.getMy_tickets_actually()*10000);
		super.reservation_check(movieInfo,UorM);
	}
	
}
