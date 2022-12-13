import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;

public class WinBookInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTextField tfpDate;
	private JTextField tfPrice;
	private JTextField tfISBN;
	private String imgText;
	private JTextArea tfContent;
	private String picURL; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinBookInsert dialog = new WinBookInsert();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinBookInsert() {
		setTitle("도서 등록 창");
		setBounds(100, 100, 607, 662);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		imgText = "<html><img src='";
		imgText = imgText + "https://shopping-phinf.pstatic.net/main_3246352/32463527641.20221019105938.jpg"; //URL로 이미지 넣는법
		imgText = imgText + "' width=150 height=200></html>"; // 이미지 사이즈 넣기
		JLabel lblPic = new JLabel(imgText);
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 if(e.getClickCount()==2 ) {
					 picURL = JOptionPane.showInputDialog("그림주소를 입력하시오");
					 lblPic.setText("<html><img src='"+picURL+"' width=150 height=200></html>");
				 }
				
			}
		});
		lblPic.setOpaque(true);
		lblPic.setBackground(new Color(255, 255, 128));
		lblPic.setBounds(24, 34, 150, 200);
		contentPanel.add(lblPic);
		
		JLabel lblTitle = new JLabel("책 제목 :");
		lblTitle.setBounds(203, 42, 57, 15);
		contentPanel.add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(272, 39, 295, 25);
		contentPanel.add(tfTitle);
		tfTitle.setColumns(10);
		
		JLabel lblAuthor = new JLabel("\uC800\uC790 :");
		lblAuthor.setBounds(203, 85, 57, 15);
		contentPanel.add(lblAuthor);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(272, 82, 295, 25);
		contentPanel.add(tfAuthor);
		
		JLabel lblPublisher = new JLabel("\uCD9C\uD310\uC0AC :");
		lblPublisher.setBounds(203, 126, 57, 15);
		contentPanel.add(lblPublisher);
		
		tfPublisher = new JTextField();
		tfPublisher.setColumns(10);
		tfPublisher.setBounds(272, 123, 159, 25);
		contentPanel.add(tfPublisher);
		
		JLabel lbpDate = new JLabel("\uCD9C\uD310\uC77C :");
		lbpDate.setBounds(203, 172, 57, 15);
		contentPanel.add(lbpDate);
		
		tfpDate = new JTextField();
		tfpDate.setColumns(10);
		tfpDate.setBounds(272, 169, 188, 25);
		contentPanel.add(tfpDate);
		
		JLabel lblPrice = new JLabel("\uAC00\uACA9 :");
		lblPrice.setBounds(203, 216, 57, 15);
		contentPanel.add(lblPrice);
		
		tfPrice = new JTextField();
		tfPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrice.setColumns(10);
		tfPrice.setBounds(272, 213, 105, 25);
		contentPanel.add(tfPrice);
		
		JLabel lblContents = new JLabel("\uCC45\uC18C\uAC1C");
		lblContents.setBounds(24, 263, 57, 15);
		contentPanel.add(lblContents);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(24, 536, 543, -247);
		contentPanel.add(scrollPane);
		
		tfContent = new JTextArea();
		tfContent.setBounds(24, 288, 543, 261);
		contentPanel.add(tfContent);
		
		JButton btnInsert = new JButton("\uB4F1\uB85D");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertBook();
			}

			
			private void InsertBook() {
				// TODO Auto-generated method stub
			
				//DB 연동 sql 붙이기
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = 
							DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/sqlDB",
									"root",
									"12345");
					Statement stmt = con.createStatement();			
				
					String sql = "insert into bookTBL values('";
					sql = sql + tfISBN.getText()  + "','" + tfTitle.getText()  + "','";
					sql = sql + tfAuthor.getText()  + "','" + tfPublisher.getText()  + "','";
					sql = sql + tfpDate.getText()  + "','" + picURL + "',";
					sql = sql + tfPrice.getText() + ",'"  + tfContent.getText().replaceAll("'", "&apos;") + "')";
					System.out.println(sql);
					if(stmt.executeUpdate(sql) > 0)
						JOptionPane.showMessageDialog(null, "도서 등록 완료");
					else
						JOptionPane.showMessageDialog(null, "도서 등록 오류");
													
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				
				
			}
			
			
		});
		btnInsert.setBounds(246, 567, 97, 25);
		contentPanel.add(btnInsert);
		
		JButton btnCalendar = new JButton("날짜선택");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfpDate.setText(winCalendar.getDate());
			}
		});
		btnCalendar.setBounds(470, 168, 97, 25);
		contentPanel.add(btnCalendar);
		
		JLabel lblISBN = new JLabel("ISBN :");
		lblISBN.setBounds(393, 216, 57, 15);
		contentPanel.add(lblISBN);
		
		tfISBN = new JTextField();
		tfISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		tfISBN.setColumns(10);
		tfISBN.setBounds(462, 213, 105, 25);
		contentPanel.add(tfISBN);
		
		JCheckBox ckOnce = new JCheckBox("\uD55C\uAD8C \uC785\uB825");
		ckOnce.setSelected(true);
		ckOnce.setBounds(452, 124, 115, 23);
		contentPanel.add(ckOnce);
	}
}
