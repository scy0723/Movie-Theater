package �����߰�����;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


//��ȭ ������ ���� class
public class Movie implements Comparable <Movie>{
	private int num; 
	private String title;
	private String duration;
	private char[][]seats=new char[7][7];
	
	private int TicketsReserved=0;//�ش翵ȭ ���� Ƽ�� ��
	private static int howManyMovies=0;//��ȭ�� ������ �����ϱ� ���� �߰��� ��
	public Buffer buff=new Buffer();
	public ArrayList<Wait_er> waitList=new ArrayList<Wait_er>();
	
	//��ȭ ������
	public Movie(int num, String title, String duration) {
		super();
		this.num = num;
		this.title = title;
		this.duration = duration;		
		seats[0][0]=' '; 
		for(int i=1;i<7;i++)
			seats[0][i]=(char)(i+48);//1�� �ƽ�Ű �ڵ� == 49
		for(int i=1;i<7;i++) {
			Arrays.fill(seats[i], 'O');//������ �κ��� ���� "O"�� ä���ְ�
		}
		for(int i=1;i<7;i++) {
			seats[i][0]=(char)(64+i);//A�� �ƽ�Ű �ڵ� == 65
		}
	}
	

	//toString
	public String toString() {
		return ("���� : "+this.title+" / �󿵽ð� : "+this.duration); 
	}
	public int getNum() {
		return num;
	}
	public String getTitle() {
		return title;
	}
	public String getDuration() {
		return duration;
	}
	public char[][] getSeats() {
		return seats;
	}
	public int getTicketsReserved() {
		return TicketsReserved;
	}
	public static int getHowManyMovies() {
		return howManyMovies;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public void setSeats(char[][] seats) {
		this.seats = seats;
	}
	public void setTicketsReserved(int ticketsReserved) {
		TicketsReserved = ticketsReserved;
	}
	public static void setHowManyMovies(int howManyMovies) {
		Movie.howManyMovies = howManyMovies;
	}
	
	
	public void addWaitings(Member member,int wait) throws InterruptedException {
		waitList.add(new Wait_er(member, this, wait));
	}

	//�¼����
	public void print_seats() {
		System.out.println("*******�� ��*******");
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				System.out.print(seats[i][j]+" ");
			}
			System.out.println();
		}
	}

	// �¼�����
	public void select_seat(int column, int row_int) {
			seats[row_int][column]='X'; 
	}
	public void cancel_seat(int column, int row_int) {
			seats[row_int][column]='O';
	}

	// ���� ���� ���� Ȯ��
	public boolean isReserved(int column, int row_int) {
		if (seats[row_int][column] == 'X')
			return true;
		else
			return false;
	}
	
	//����� Ƽ�ϼ� �߰�
	public void addReservation() {
		TicketsReserved++;
	}
	//////////////////////////////
	public void cancelReservation() {
		TicketsReserved--;
	}
	//**��ȭ���������� ��ȭ���� �������� ���ϴ� �κп���
	//��ȭ���� Ƽ�� ���� �������� �����ϱ� ���� comparable�� implement�ϰ� Ƽ�� ���� ��Ҹ� ���� �� �ְ� ����.
	public int compareTo(Movie movie) {
		if(this.TicketsReserved<movie.getTicketsReserved())
			return 1;
		else if(this.TicketsReserved==movie.getTicketsReserved())
			return 0;
		else return -1;
	}
	
	//��ȭǥ�� �����ϰ��� �ؽ�Ʈ���Ͽ� ���� ������ ���� �Լ�
	public void SaveTickets(int member, String seat) throws IOException {
		PrintWriter wtTicket=null;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("BookedSeats.txt",true));
			wtTicket= new PrintWriter(bw, true);
		} 
		catch (FileNotFoundException e) {
			System.out.println("SaveTickets failed. BookedSeats.txt not found.");
			System.out.println("���� ���� ����. ���� ���� ������ ã�� ���߽��ϴ�.");
			System.exit(0);
		}
		wtTicket.println(this.num+" "+member+" "+seat); //��ȭ �迭 index, ������ ȸ�� ��ȣ, �¼� ���� write��.
	}
	
	public void SaveCancelings(int member, String seat) throws Exception {
		try {
		File inputFile= new File("BookedSeats.txt");
		String cancel=new String(this.num+" "+member+" "+seat);
		StringBuffer newTxt= new StringBuffer();
		BufferedReader br =new BufferedReader(new FileReader(inputFile));
		String line;
		while ((line = br.readLine()) != null) {
            if (!line.trim().equals(cancel)) {
                newTxt.append(line);
                newTxt.append("\n");
            }
        }
		br.close();
		FileWriter removeLine=new FileWriter(inputFile);
		BufferedWriter change = new BufferedWriter(removeLine);
		PrintWriter replace = new PrintWriter(change);
		replace.write(newTxt.toString());
		replace.close();
		}
		catch(Exception e) {
			e.getMessage();
		}
		
	}

}
