package 객지중간과제;

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


//영화 정보를 담을 class
public class Movie implements Comparable <Movie>{
	private int num; 
	private String title;
	private String duration;
	private char[][]seats=new char[7][7];
	
	private int TicketsReserved=0;//해당영화 예약 티켓 수
	private static int howManyMovies=0;//영화의 개수를 저장하기 위해 추가한 값
	public Buffer buff=new Buffer();
	public ArrayList<Wait_er> waitList=new ArrayList<Wait_er>();
	
	//영화 생성자
	public Movie(int num, String title, String duration) {
		super();
		this.num = num;
		this.title = title;
		this.duration = duration;		
		seats[0][0]=' '; 
		for(int i=1;i<7;i++)
			seats[0][i]=(char)(i+48);//1의 아스키 코드 == 49
		for(int i=1;i<7;i++) {
			Arrays.fill(seats[i], 'O');//나머지 부분은 전부 "O"로 채워주고
		}
		for(int i=1;i<7;i++) {
			seats[i][0]=(char)(64+i);//A의 아스키 코드 == 65
		}
	}
	

	//toString
	public String toString() {
		return ("제목 : "+this.title+" / 상영시간 : "+this.duration); 
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

	//좌석출력
	public void print_seats() {
		System.out.println("*******좌 석*******");
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				System.out.print(seats[i][j]+" ");
			}
			System.out.println();
		}
	}

	// 좌석고르기
	public void select_seat(int column, int row_int) {
			seats[row_int][column]='X'; 
	}
	public void cancel_seat(int column, int row_int) {
			seats[row_int][column]='O';
	}

	// 예약 가능 여부 확인
	public boolean isReserved(int column, int row_int) {
		if (seats[row_int][column] == 'X')
			return true;
		else
			return false;
	}
	
	//예약된 티켓수 추가
	public void addReservation() {
		TicketsReserved++;
	}
	//////////////////////////////
	public void cancelReservation() {
		TicketsReserved--;
	}
	//**영화관관리에서 영화들의 점유율을 비교하는 부분에서
	//영화들을 티켓 수를 기준으로 정렬하기 위해 comparable을 implement하고 티켓 수로 대소를 비교할 수 있게 만듬.
	public int compareTo(Movie movie) {
		if(this.TicketsReserved<movie.getTicketsReserved())
			return 1;
		else if(this.TicketsReserved==movie.getTicketsReserved())
			return 0;
		else return -1;
	}
	
	//영화표를 예약하고나서 텍스트파일에 예약 정보를 적는 함수
	public void SaveTickets(int member, String seat) throws IOException {
		PrintWriter wtTicket=null;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("BookedSeats.txt",true));
			wtTicket= new PrintWriter(bw, true);
		} 
		catch (FileNotFoundException e) {
			System.out.println("SaveTickets failed. BookedSeats.txt not found.");
			System.out.println("예약 저장 실패. 예약 저장 파일을 찾지 못했습니다.");
			System.exit(0);
		}
		wtTicket.println(this.num+" "+member+" "+seat); //영화 배열 index, 예약한 회원 번호, 좌석 으로 write함.
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
