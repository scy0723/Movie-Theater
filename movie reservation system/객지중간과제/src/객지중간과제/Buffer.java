package 객지중간과제;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class Buffer {
	public String cancelledSeats=null;
	public Movie cancelledMovie=null;
	
	//consumer
	public synchronized void somoneCancelled(Movie movie,String seat) throws InterruptedException {
		this.cancelledSeats=seat;
		this.cancelledMovie=movie;
		notifyAll();
	}
	
	//producer
	public synchronized String waitedAndRsv(Wait_er mem, Boolean firstTime, Movie movie,int time) throws InterruptedException, IOException{
		while((System.currentTimeMillis()-mem.getStartTime())<=mem.getTime()*60*1000) {
			if(cancelledMovie==null) {
				try {
					if(firstTime)
						wait(mem.getTime()*60*1000);
					else
						wait(mem.getTime()*60*1000 - (System.currentTimeMillis() - mem.getStartTime()));

				}
				catch(Exception e) {
					e.getMessage();
				}
			}
			else {
			char row=cancelledSeats.charAt(0);
			int column=Integer.parseInt(cancelledSeats.substring(1));
			int row_int=(int)row-64;
			movie.addReservation();
			movie.select_seat(column, row_int);
			movie.SaveTickets(movie.waitList.get(0).getMem().getMy_member_num(), cancelledSeats);
			mem.getMem().add_rsv(movie.getNum(), cancelledSeats);
			PrintWriter wtTicket=null;
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("Display.txt",true));
				wtTicket= new PrintWriter(bw, true);
			}
			catch (FileNotFoundException e) {
				System.out.println("Display failed. BookedSeats.txt not found.");
				System.out.println("파일을 찾지 못했습니다.");
				System.exit(0);
			}
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy.MM.dd HH:mm");
			String formatTime=format1.format(System.currentTimeMillis());
			wtTicket.println(formatTime +"에 "+movie.waitList.get(0).getMem().getId()+"님이 "
						+movie.getTitle()+"영화 "+cancelledSeats+"좌석이 예매되었습니다."); 
			cancelledMovie=null;
			cancelledSeats=null;
			movie.waitList.remove(0);
			}
		}
		return cancelledSeats;
	}
	
}
