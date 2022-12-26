import java.awt.Color;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.thehowtotutorial.splashscreen.JSplash;

public class SplashExample {

	public static void main(String[] args) throws Exception {
		int total = numOfRecords();	
		System.out.println(total);
		JSplash splash = 
				new JSplash(SplashExample.class.getResource("/images/bookstore.png"), true, true, false, "Ver1.0",null,Color.BLUE, Color.RED);
		splash.splashOn();
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("번호");
		columnNames.add("ISBN");
		columnNames.add("책 제목");
		columnNames.add("저자");
		columnNames.add("출판사");
		columnNames.add("가격");
		
		Vector data = new Vector<>(); //테이블 (레코드들의 집합)
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"12345");
			Statement stmt = con.createStatement();			
			String sql = "SELECT * FROM booktbl";
			ResultSet rs = stmt.executeQuery(sql);		
			
			int cnt=0;
			while(rs.next()) {
				String record[] = new String[6];
				record[0] = Integer.toString(++cnt);
				record[1] = rs.getString("ISBN");
				record[2] = rs.getString("Title");
				record[3] = rs.getString("Author");
				record[4] = rs.getString("Publisher");
				record[5] = rs.getString("Price");
				
				Vector row = new Vector<>();//하나의 레코드
				for(int i=0;i<6;i++)
					row.add(record[i]);
				data.add(row);
				
				
				splash.setProgress((cnt*100/total), (cnt*100/total) + "% Loading...");
				Thread.sleep(10);
				System.out.println(cnt);
			}
											
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		splash.splashOff();				
		
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		
		WinMain win = new WinMain(dtm);
		win.setVisible(true);
	}

	private static int numOfRecords() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"12345");
			Statement stmt = con.createStatement();			
			String sql = "SELECT count(*) as cnt FROM booktbl";
			ResultSet rs = stmt.executeQuery(sql);			
			if(rs.next())
				return rs.getInt("cnt");											
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

}
