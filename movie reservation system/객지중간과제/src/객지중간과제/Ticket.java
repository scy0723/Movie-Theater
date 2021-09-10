package �����߰�����;

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
			System.out.println("����: " + movieInfo[i].getTitle() + " / �󿵽ð�: " + movieInfo[i].getDuration());
		}
		System.out.println("\n1. ����\n" + "2. ����\n" + "�޴��� �������ּ��� : ");
		command = scan.nextInt();
		// ����
		try {
		if (command == 1) {
			for (int i = 1; movieInfo[i] != null; i++) {
				System.out.println(i + ". ����: " + movieInfo[i].getTitle() + " / �󿵽ð�: " + movieInfo[i].getDuration());
			}
			reservation(member, movieInfo);
			return;
		} 
		else if (command == 2) {
			System.out.println("�������α׷����� ���ư��ϴ�.");
			return;
		}
		else
			throw new InvalidMenuException(command+" is an invalid menu number.");
		}
		
		catch (InvalidMenuException e) {
			e.printExcep();
		}
	}

	// ����
	public void reservation(Member member, Movie[] movieInfo) throws IOException, InterruptedException {
		if(member.getMy_tickets()>=21) {
			System.out.println("�� �̻� ������ �� �����ϴ�.");
			return;
		}
		System.out.println("������ ��ȭ�� �������ּ��� : ");
		command=scan.nextInt();
		
		if(command<1||command>Movie.getHowManyMovies()) {
			System.out.println("�Է��Ͻ� ��ȣ�� �ش�Ǵ� ��ȭ�� �����ϴ�.\n�������α׷����� ���ư��ϴ�."); //��ȣ�� �ش�Ǵ� ��ȭ�� �������� ���� ��
			//���� ��ȭ
			return;
		}
		System.out.print("������ ��ȭ�� �������ּ��� : "+movieInfo[command].getNum());
		//�ų����� �ش� ��ȭ�� ������ ���
		if(member instanceof Manager) {
			System.out.println("\n\"" + movieInfo[command].getTitle()+"\"��ȭ�� �¼� ���� ������: "+String.format("%.2f", movieInfo[command].getTicketsReserved()*100/36.00)+"%" );
			System.out.println("\"" + movieInfo[command].getTitle()+"\"��ȭ�� �� �����: "+ movieInfo[command].getTicketsReserved()*10000+"\n");
		}
		else {
			System.out.println("���� : "+movieInfo[command].getTitle()
					+"/ �� �ð� : "+movieInfo[command].getDuration());
		}
			movieInfo[command].print_seats();
			if(movieInfo[command].getTicketsReserved()==36) {
				System.out.println("�ش� ��ȭ�� �����Դϴ�.\n"
						+ "������ �ϸ� ������ �ڸ��� ���� ���� �� �ֽ��ϴ�.\n"
						+ "������ �����Ͻðڽ��ϱ�? (1.��/2.�ƴϿ�)");
				int wait=scan.nextInt();
				if(wait==1) {
					System.out.println("�� �а� ����Ͻðڽ��ϱ�?");
					wait=scan.nextInt();
					System.out.println("������ �Ϸ�Ǿ����ϴ�.");
					movieInfo[command].waitList.add(new Wait_er(member, movieInfo[command],  wait));
					movieInfo[command].waitList.get(movieInfo[command].waitList.size()-1).start();
					return;
				}
				else if(wait==2) {
					System.out.println("�������α׷����� ���ư��ϴ�.");
					return;
				}
			}
			System.out.println("******************\n" + 
					"�¼��� �������ּ���(ex A1) :");
			String seat_selection=new String(); scan.nextLine();
			seat_selection=scan.nextLine();
			char row=seat_selection.charAt(0);
			int column=Integer.parseInt(seat_selection.substring(1));
			int row_int=(int)row-64;
			//�������� �ʴ� �ڸ��� ������ ���
			try {
				if(row_int<1||row_int>6||column>6||column<1) {
					String message=seat_selection+" does not exist.";
					throw new NotExistSeatException(message);
				}
				//����
				if(!movieInfo[command].isReserved(column,row_int)) {
					movieInfo[command].select_seat(column,row_int);
					member.add_rsv(command,seat_selection);
					movieInfo[command].addReservation();
					//���� ������ �ؽ�Ʈ ���Ͽ� �������ֱ����� �߰��� �Լ�
					movieInfo[command].SaveTickets(member.getMy_member_num(), seat_selection);
				}
				//�̹� ����� �¼��� ������ ���
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
			System.out.println("�����Ͻ� ��ȭ�� �����ϴ�.");
			return;
		}
		for(int i=1;i<=member.getMy_tickets();i++) {
			if(member.getMy_ticket_num()[i]==9999) continue;
			System.out.println(i+ ". Ticket number: "+member.getMy_ticket_num()[i]+" / "
			+movieInfo[member.getMov_reserved()[i]].toString()+" / Seat : "+ member.getSeat()[i]); 
		}
		System.out.println("\n� Ƽ���� ����Ͻðڽ��ϱ�? (���ư���:0)");
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
			movieInfo[member.getMov_reserved()[command]].cancel_seat(column,row_int); //�ڸ� O����
			movieInfo[member.getMov_reserved()[command]].cancelReservation();//�ش� ��ȭ ���� ��-1
			member.cancel_rsv(command);//��ȭ�� �� ���ż�-1
			System.out.println("�ش� Ƽ���� ����Ͽ����ϴ�.");
			cancel.start();
		}
	}
	
}
