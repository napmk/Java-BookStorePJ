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
import java.util.Vector;

import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WinBookInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTextField tfpDate;
	private JTextField tfPrice;
	private JTextField tfISBN;

	private String picURL;
	private JTextArea taContents;
	private JCheckBox ckOnce;
	private JLabel lblPic;
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
		setBounds(100, 100, 600, 621);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		String imgText = "<html><img src='";
		imgText = imgText + "https://shopping-phinf.pstatic.net/main_3248718/32487182777.20221019152842.jpg";
		imgText = imgText + "' width=150 height=200></html>";
		lblPic = new JLabel(imgText);
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					picURL = JOptionPane.showInputDialog("그림 주소를 입력하시오");
					lblPic.setText("<html><img src='"+picURL+"' width=150 height=200></html>");
				}
			}
		});
		
		lblPic.setOpaque(true);
		lblPic.setBackground(new Color(255, 255, 0));
		lblPic.setBounds(12, 10, 150, 200);
		contentPanel.add(lblPic);
		
		JLabel lblTitle = new JLabel("책 제목:");
		lblTitle.setBounds(191, 13, 57, 15);
		contentPanel.add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(256, 10, 316, 21);
		contentPanel.add(tfTitle);
		tfTitle.setColumns(10);
		
		JLabel lblAuthor = new JLabel("저자:");
		lblAuthor.setBounds(191, 53, 57, 15);
		contentPanel.add(lblAuthor);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(256, 50, 316, 21);
		contentPanel.add(tfAuthor);
		
		JLabel lblPublisher = new JLabel("출판사:");
		lblPublisher.setBounds(191, 94, 57, 15);
		contentPanel.add(lblPublisher);
		
		tfPublisher = new JTextField();
		tfPublisher.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					String word = tfPublisher.getText().trim();
					
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = 
								DriverManager.getConnection(
										"jdbc:mysql://localhost:3306/sqlDB",
										"root",
										"12345");
						Statement stmt = con.createStatement();			
						String sql = "select distinct publisher from booktbl";
						sql = sql + " where publisher like '" + word + "%'";
						ResultSet rs = stmt.executeQuery(sql);
						int cnt=0;
						Vector<String> v = new Vector<>();
						while(rs.next()) {	
							v.add(rs.getString("publisher"));
							System.out.println(rs.getString("publisher"));
							cnt++;
						}
						if(cnt == 1)
							tfPublisher.setText(v.get(0));
						else {
							WinPublisher winPublisher = new WinPublisher(v);
							winPublisher.setModal(true);
							winPublisher.setVisible(true);
							
							tfPublisher.setText(winPublisher.getPublisher());
							tfpDate.requestFocus();
						}
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		tfPublisher.setColumns(10);
		tfPublisher.setBounds(256, 91, 158, 21);
		contentPanel.add(tfPublisher);
		
		JLabel lblpDate = new JLabel("출판일:");
		lblpDate.setBounds(191, 138, 57, 15);
		contentPanel.add(lblpDate);
		
		tfpDate = new JTextField();
		tfpDate.setColumns(10);
		tfpDate.setBounds(256, 135, 158, 21);
		contentPanel.add(tfpDate);
		
		tfPrice = new JTextField();
		tfPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrice.setColumns(10);
		tfPrice.setBounds(256, 179, 104, 21);
		contentPanel.add(tfPrice);
		
		JLabel lblPrice = new JLabel("가격:");
		lblPrice.setBounds(191, 182, 57, 15);
		contentPanel.add(lblPrice);
		
		JLabel lblContents = new JLabel("책 소개:");
		lblContents.setBounds(12, 220, 57, 15);
		contentPanel.add(lblContents);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 234, 560, 302);
		contentPanel.add(scrollPane);
		
		taContents = new JTextArea();
		taContents.setLineWrap(true);
		scrollPane.setViewportView(taContents);
		
		JButton btnCalendar = new JButton("날짜 선택");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfpDate.setText(winCalendar.getDate());
			}
		});
		btnCalendar.setBounds(421, 134, 97, 23);
		contentPanel.add(btnCalendar);
		
		JButton btnInsert = new JButton("등록");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertBook();
			}
		});
		btnInsert.setBounds(243, 546, 97, 23);
		contentPanel.add(btnInsert);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(403, 182, 57, 15);
		contentPanel.add(lblIsbn);
		
		tfISBN = new JTextField();
		tfISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		tfISBN.setColumns(10);
		tfISBN.setBounds(468, 179, 104, 21);
		contentPanel.add(tfISBN);
		
		ckOnce = new JCheckBox("한번 입력");
		ckOnce.setBounds(440, 90, 115, 23);
		contentPanel.add(ckOnce);
	}

	protected void InsertBook() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"12345");
			Statement stmt = con.createStatement();			
			String sql = "insert into booktbl values('";
			sql = sql + tfISBN.getText() + "','" + tfTitle.getText() + "','";
			sql = sql + tfAuthor.getText() + "','" + tfPublisher.getText() + "','";
			sql = sql + tfpDate.getText() + "','" + picURL + "',";
			sql = sql + tfPrice.getText() + ",'" + taContents.getText().replaceAll("'", "&apos;") +"')" ; 
			System.out.println(sql);
			if(stmt.executeUpdate(sql) > 0) {
				JOptionPane.showMessageDialog(null, "도서 등록 완료!!");
				if(ckOnce.isSelected())
					setVisible(false);
				else
					clearAll();
			}
			else
				JOptionPane.showMessageDialog(null, "도서 등록 오류!!");
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	private void clearAll() {
		tfTitle.setText("");
		tfAuthor.setText("");
		tfPublisher.setText("");
		tfpDate.setText("");
		tfPrice.setText("");
		tfISBN.setText("");
		taContents.setText("");
		picURL = "";
		lblPic.setText(picURL);
		tfTitle.requestFocus();
	}
}
