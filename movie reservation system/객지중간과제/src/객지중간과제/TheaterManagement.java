package �����߰�����;

import java.util.Arrays; 
import java.util.Scanner;

//�������α׷����� �Ŵ����� ��ȭ�� ������ �ϴ� Ŭ����
public class TheaterManagement {
	private Scanner scan;
	public TheaterManagement(Scanner scan) { 
		this.scan=scan;
	}
	public void TheaterManagement_exe(Movie[] movieInfo, Member[] members) {
		int command=0;
		while(command!=3) {
			System.out.print("\n*******��ȭ�� ����*******\n" + 
					"1. ��ȭ�� ����\n"+ 
					"2. ���� ����\n"+ 
					"3. ����\n" + 	
					"�޴��� �������ּ��� : ");
			command=scan.nextInt();
			try {
				if(command==1) //��ȭ�� ����
					TheaterInfoManagement(members,movieInfo);
				else if(command==2)//���� ����
					UserInfoManagement(movieInfo,members);
				else if(command!=1&&command!=2&&command!=3)
					throw new InvalidMenuException(command+" is an invalid menu number.");
			}
			catch (InvalidMenuException e) {
				e.printExcep();
			}
		}
		System.out.println("���� ���α׷����� ���ư��ϴ�.\n");
	}
	
	public void TheaterInfoManagement(Member[] members,Movie[] movieInfo) {
		Movie[] movieSorted= new Movie[Movie.getHowManyMovies()]; //��ȭ�� ������ ������ ������ ����Ʈ�� �����.
		for(int i=0;i<Movie.getHowManyMovies();i++) {
			movieSorted[i]=movieInfo[i+1];
		}
		Arrays.sort(movieSorted);
		System.out.println("\n������ ��ü �¼� ��:"+Member.all_tickets);//������� ������ �� Ƽ�� ��
		System.out.println("��ü �¼� ���� ������: "+String.format("%.2f", Member.all_tickets*100/ ( 36.00 * Movie.getHowManyMovies()) )+"%" ); //��ü �¼� �� ����� �¼�
		System.out.println("��ȭ�� �� �����: "+Member.all_tickets*10000);//��ü Ƽ�ϼ� * ����
		
		System.out.println("---------------------------------------------------------------");
		//1,2�� ���
		for(int i=0;i<2;i++) {
			System.out.println(i+1+"��: "+movieSorted[i].getTitle()+"(���� �¼�:"+movieSorted[i].getTicketsReserved()+")"); 
		}
		//3�� ���
		//(3���� ���� ��쿡�� ���� 3���� ���!)
		for(int i=2;i<Movie.getHowManyMovies();i++) {
			if(i!=2 && movieSorted[i].getTicketsReserved()==movieSorted[2].getTicketsReserved()) {
				System.out.println("���� 3��: "+movieSorted[i].getTitle()+"(���� �¼�: "+movieSorted[i].getTicketsReserved()+")");
			}
			else if(i==2){
				System.out.println("3��: "+movieSorted[i].getTitle()+"(���� �¼�: "+movieSorted[i].getTicketsReserved()+")");
			}
		}
		System.out.println("---------------------------------------------------------------");
		System.out.println("Press Enter to go back to Theater Management");
		Scanner enter = new Scanner(System.in);
		enter.nextLine();
	}
	
	public void UserInfoManagement(Movie[] movieInfo,Member[] members) {
		scan.nextLine();
		System.out.print("\nã������ ID: ");
		String id=scan.nextLine();
		//��� ����Ʈ���� id�� ã�´�
		try {
			for(int i=0;i<Member.getHowManyMembers();i++) {
				if(members[i].getId().equals(id)) {
					System.out.print("\n"+members[i].toString());
					if(members[i] instanceof Manager)
						System.out.print("\n������ ����Դϴ�:");//������ ����� ������ ������ ���
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
