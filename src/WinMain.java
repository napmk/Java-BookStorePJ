import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class WinMain extends JDialog {
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinMain dialog = new WinMain();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMain() {
		setTitle("도서대여점 version 1.0");
		setBounds(100, 100, 1039, 683);
		getContentPane().setLayout(new BorderLayout());
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnInsert = new JButton("");
		btnInsert.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\bookinsert.png"));
		toolBar.add(btnInsert);
		
		JButton btnDelete = new JButton("");
		btnDelete.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\bookdelete.png"));
		toolBar.add(btnDelete);
		
		JButton btnUpdate = new JButton("");
		btnUpdate.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\bookselect.png"));
		toolBar.add(btnUpdate);
		
		JButton btnSelect = new JButton("");
		btnSelect.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\booksearch.png"));
		toolBar.add(btnSelect);
		
		
		toolBar.addSeparator();
		textField = new JTextField();
		toolBar.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String columnNames[]= {"번호","ISBN","책 제목","저자","출판사","가격"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		table = new JTable(dtm);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImformation = new JLabel("정보표시");
		panel.add(lblImformation);
		
		//글꼴선택(WindowBuilder -> Design -> property
		table.setFont(new Font("맑은고딕" , Font.PLAIN,13));
		
		// 셀여백
		//table.setIntercellSpacing(new Dimension(0, 0));
		
		//셀 너비조정
		int widths[] = {5,5,200,10,10,5};
		for (int i=0; i<6 ; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(widths[i]);
		}
		
		scrollPane.setViewportView(table);
		//셀 높이 조절
		table.setRowHeight(23);
		
		
		
		//셀정렬
		DefaultTableCellRenderer cellAlignRight = new DefaultTableCellRenderer();
		DefaultTableCellRenderer cellAlignCenter = new DefaultTableCellRenderer();
		
		cellAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		cellAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumn("번호").setCellRenderer(cellAlignCenter);
		table.getColumn("ISBN").setCellRenderer(cellAlignCenter);
		table.getColumn("출판사").setCellRenderer(cellAlignCenter);
		table.getColumn("가격").setCellRenderer(cellAlignCenter);
		
		ShowAllBooks();
	//	tableCellCenter(table);
	//	setCoulmnSize(table);
		
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnuFile = new JMenu("File");
			mnuFile.setMnemonic('F');
			menuBar.add(mnuFile);
			
			JMenuItem mnuExit = new JMenuItem("Exit");
			mnuExit.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\exit.png"));
			mnuExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(DISPOSE_ON_CLOSE);
				}
			});
			
			
			
			JMenuItem mntmNewMenuItem = new JMenuItem("Print...");
			mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
			mntmNewMenuItem.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\print1.png"));
			mnuFile.add(mntmNewMenuItem);
			mnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_DOWN_MASK));
			
			
			mnuFile.add(mnuExit);
			
			JMenu mnuBook = new JMenu("Book");
			menuBar.add(mnuBook);
			
			JMenuItem mnuBookInsert = new JMenuItem("도서 등록...");
			mnuBookInsert.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\bookinsert.png"));
			mnuBookInsert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
			mnuBook.add(mnuBookInsert);
			
			JMenuItem mnuBookUpdate = new JMenuItem("도서 변경...");
			mnuBookUpdate.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\bookselect.png"));
			mnuBookUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
			mnuBook.add(mnuBookUpdate);
			
			JMenuItem mnuBookDelete = new JMenuItem("도서 삭제...");
			mnuBookDelete.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\bookdelete.png"));
			mnuBookDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
			mnuBook.add(mnuBookDelete);
			
			JMenuItem mnuBookSelect = new JMenuItem("도서 조회...");
			mnuBookSelect.setIcon(new ImageIcon("C:\\javawork\\BookStorePJ\\images\\booksearch.png"));
			mnuBookSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
			mnuBook.add(mnuBookSelect);
		}
	}

//	 public void tableCellCenter(JTable dtm){
//		    // 테이블 내용 가운데 정렬하기
//		      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
//		      dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
//		     
//		      TableColumnModel dtm1 = dtm.getColumnModel() ; // 정렬할 테이블의 컬럼모델을 가져옴
//		     
//		      //전체 열에 지정
//		      //for(int i = 0 ; i < tcm.getColumnCount() ; i++){
//		      //tcm.getColumn(i).setCellRenderer(dtcr);  
//		      // 컬럼모델에서 컬럼의 갯수만큼 컬럼을 가져와 for문을 이용하여
//		      // 각각의 셀렌더러를 아까 생성한 dtcr에 set해줌
//		      //}
//		       
//		      //특정 열에 지정
//		      dtm1.getColumn(0).setCellRenderer(dtcr);
//		      dtm1.getColumn(1).setCellRenderer(dtcr);  
//		      dtm1.getColumn(4).setCellRenderer(dtcr);
//		      dtm1.getColumn(5).setCellRenderer(dtcr);
//		    }
	 
//	    public void setCoulmnSize(JTable t){
//	        
//	        t.getTableHeader().setReorderingAllowed(false);      
//	        //테이블 컬럼의 이동을 방지한다. 이거 안쓰면 마우스로 드로그 앤 드롭으로 엉망진창이 될수 있다.
//	 
//	        t.getColumnModel().getColumn(0).setPreferredWidth(1);
//	        t.getColumnModel().getColumn(1).setPreferredWidth(30);
//	       // t.getColumnModel().getColumn(0).setResizable(20);
//	        t.getColumnModel().getColumn(2).setPreferredWidth(200);
//	        t.getColumnModel().getColumn(3).setPreferredWidth(100);
//	        t.getColumnModel().getColumn(4).setPreferredWidth(50);
//	        t.getColumnModel().getColumn(5).setPreferredWidth(10);
//	       
//	    } 
	
	
	
	 
	private void ShowAllBooks() {
		//DB 연동 sql 붙이기
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"12345");
			Statement stmt = con.createStatement();			
			String sql = "";
			sql = "SELECT * FROM bookTBL";
			ResultSet rs = stmt.executeQuery(sql);
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			dtm.setRowCount(0);
			int cnt=0;
			while(rs.next()) {
				String record[] = new String[6];
				record[0] = Integer.toString(++cnt);
				record[1] = rs.getString(1);
				record[2] = rs.getString(2);
				record[3] = rs.getString(3);
				record[4] = rs.getString(4);
				record[5] = rs.getString(7);
				dtm.addRow(record);
			}
											
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
	}

}
