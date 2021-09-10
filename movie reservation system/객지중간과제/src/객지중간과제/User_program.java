package �����߰�����;
import java.io.IOException;
import java.util.Scanner;

//�������α׷�
public class User_program {
	private Scanner scan;
	private int command=0;
	private Ticket ticket;
	private Buffer buffer;
	private TheaterManagement theaterManagement; //�������α׷����� �Ŵ����� ��ȭ�������� �ϴ� Ŭ����
	public User_program(Scanner scan,Buffer buffer){
		this.scan = scan;
		ticket = new Ticket(scan,buffer);
		theaterManagement=new TheaterManagement(scan);
		this.buffer=buffer;
	}
	//�������α׷�����
	public void user_exe(Member member, Movie[] movieInfo, Member[] members) throws Exception {
		command=0;
		while(command!=5) {
			System.out.print("\n*******���� ���α׷�*******\n" + 
					"1. ��ȭ ���\n"+
					"2. ���� Ȯ��\n"+
					"3. ��ȭ�� ����\n"+
					"4. ���� ���\n"+
					"5. ����\n" + 	
					"�޴��� �������ּ��� : ");
			command=scan.nextInt();
			//��ȭ���
			try {
				if(command==1)
					ticket.ticket_exe(member,movieInfo);
				//���� Ȯ��
				else if(command==2)
					member.reservation_check(movieInfo,"U"); 
				else if(command==3) {
					if(member.getIsManager()==1) {
						theaterManagement.TheaterManagement_exe(movieInfo,members);//�Ŵ����� ��쿡�� ���� ����
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
		System.out.println("��ȭ ���� ���α׷����� ���ư��ϴ�.\n");
	}
}
