import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinShowTable extends JDialog {
	private JTable table;
	private String sISBN;
	
	public String getISBN() {
		return sISBN;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinShowTable dialog = new WinShowTable("","민음사");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinShowTable(String strTitle, String strPublisher) {
		setTitle("책 선택: " + strPublisher);
		setBounds(100, 100, 687, 456);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		String columnNames[]= {"ISBN","책 제목","저자","출판사"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				sISBN = table.getValueAt(row, 0).toString();
				setVisible(false);
			}
		});
		
		scrollPane.setViewportView(table);
		
		ShowSearchBooks(strTitle, strPublisher);
	}

	private void ShowSearchBooks(String strTitle, String strPublisher) {		
		if(!strTitle.equals("")) { // 제목으로 검색
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = 
						DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/sqlDB",
								"root",
								"12345");
				Statement stmt = con.createStatement();			
				String sql = "select * from booktbl";
				sql = sql + " where title = '" + strTitle + "'";
				ResultSet rs = stmt.executeQuery(sql);
				
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				while(rs.next()) {
					String record[] = new String[4];
					record[0] = rs.getString("ISBN");
					record[1] = rs.getString("Title");
					record[2] = rs.getString("Author");
					record[3] = rs.getString("Publisher");
					dtm.addRow(record);
				}			
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}else { // 출판사로 검색
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = 
						DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/sqlDB",
								"root",
								"12345");
				Statement stmt = con.createStatement();			
				String sql = "select * from booktbl";
				sql = sql + " where publisher = '" + strPublisher + "'";
				ResultSet rs = stmt.executeQuery(sql);
				
				DefaultTableModel dtm = (DefaultTableModel)table.getModel();
				while(rs.next()) {
					String record[] = new String[4];
					record[0] = rs.getString("ISBN");
					record[1] = rs.getString("Title");
					record[2] = rs.getString("Author");
					record[3] = rs.getString("Publisher");
					dtm.addRow(record);
				}			
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
