import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Member extends JPanel {
	private JTextField tfID;
	private JTextField tfPw;
	private JTextField tfName;
	private JTextField tfTel1;
	private JTextField tfEmail;
	private JTextField tfBirth;
	private JTextField tfTel2;
	private JButton btnCalendar;
	protected String filePath;
	private JComboBox cbLocal;
	protected String strID;
	protected String strPw;
	protected String strName;
	protected String strTel;
	protected String strEmail;
	protected String strBirth;
	protected int bLunar;
	protected String strPic;
	private JComboBox cbCompany;
	private JCheckBox ckLunar;
	
	public Member(int type) {
		setLayout(null);
		
		JLabel lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = 
							new FileNameExtensionFilter("이미지 파일","jpg","gif","png");
					chooser.addChoosableFileFilter(filter);
					int ret = chooser.showOpenDialog(null);
					if(ret == JFileChooser.APPROVE_OPTION) {
						filePath = chooser.getSelectedFile().getPath();
						filePath = filePath.replaceAll("\\\\", "/");
						System.out.println(filePath);
						ImageIcon image = new ImageIcon(filePath);
						Image img = image.getImage();
						img = img.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
						ImageIcon pic = new ImageIcon(img);
						lblPic.setIcon(pic);
					}
				}
			}
		});
		lblPic.setBackground(new Color(255, 255, 0));
		lblPic.setBounds(12, 20, 150, 200);
		lblPic.setOpaque(true);
		
		add(lblPic);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(210, 20, 57, 15);
		add(lblID);
		
		tfID = new JTextField();
		tfID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					SelectMember(tfID.getText().trim());
				}
			}
		});
		tfID.setBounds(284, 17, 209, 21);
		add(tfID);
		tfID.setColumns(10);
		
		tfPw = new JTextField();
		tfPw.setColumns(10);
		tfPw.setBounds(284, 58, 209, 21);
		add(tfPw);
		
		JLabel lblPw = new JLabel("PW:");
		lblPw.setBounds(210, 61, 57, 15);
		add(lblPw);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(210, 102, 57, 15);
		add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(284, 99, 209, 21);
		add(tfName);
		
		JLabel lblTel = new JLabel("Tel:");
		lblTel.setBounds(210, 143, 57, 15);
		add(lblTel);
		
		tfTel1 = new JTextField();
		tfTel1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(cbLocal.getSelectedItem().toString().equals("010") 
							&& tfTel1.getText().length() == 4)
						tfTel2.requestFocus();
					else if(!cbLocal.getSelectedItem().toString().equals("010") 
							&& tfTel1.getText().length() >= 3)
						tfTel2.requestFocus();
				}
			}
		});
		tfTel1.setColumns(10);
		tfTel1.setBounds(354, 140, 107, 21);
		add(tfTel1);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(210, 183, 57, 15);
		add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(284, 180, 129, 21);
		add(tfEmail);
		
		JLabel lblBirth = new JLabel("Birth:");
		lblBirth.setBounds(210, 220, 57, 15);
		add(lblBirth);
		
		tfBirth = new JTextField();
		tfBirth.setColumns(10);
		tfBirth.setBounds(284, 217, 129, 21);
		add(tfBirth);
		
		ckLunar = new JCheckBox("Lunar");
		ckLunar.setBounds(421, 216, 72, 23);
		add(ckLunar);
		
		JButton btnDup = new JButton("중복체크");
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = 
							DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/sqlDB",
									"root",
									"12345");
					Statement stmt = con.createStatement();			
					String sql = "select count(*) from memberTBL where id='";
					sql = sql + tfID.getText().trim() + "'";
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()) {
						if(rs.getInt(1)==1) {
							JOptionPane.showMessageDialog(null, "ID가 중복되었습니다");
							tfID.requestFocus();
						}else
							tfPw.requestFocus();
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnDup.setBounds(505, 16, 97, 23);
		add(btnDup);
		
		cbLocal = new JComboBox();
		cbLocal.setModel(new DefaultComboBoxModel(new String[] {"010", "02", "032", "042", "051", "052", "053", "062", "064", "031", "033", "041", "043", "054", "055", "061", "063"}));
		cbLocal.setBounds(284, 139, 57, 23);
		add(cbLocal);
		
		btnCalendar = new JButton("달력 선택...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfBirth.setText(winCalendar.getDate());
			}
		});
		btnCalendar.setBounds(505, 216, 97, 23);
		add(btnCalendar);
		
		JLabel lblAt = new JLabel("@");
		lblAt.setBounds(415, 183, 20, 15);
		add(lblAt);
		
		cbCompany = new JComboBox();
		cbCompany.setEditable(true);
		cbCompany.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "gmail.net", "daum.net", "korea.com", "ici.org", "samsung.co.kr", "lg.co.kr", "sk.com", "kt.com"}));
		cbCompany.setBounds(436, 179, 166, 23);
		add(cbCompany);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				strID = tfID.getText().trim();
				strPw = tfPw.getText().trim();
				strName = tfName.getText().trim();
				strTel = cbLocal.getSelectedItem() + tfTel1.getText().trim()
						+ tfTel2.getText().trim();
				strEmail = tfEmail.getText().trim();
				strBirth = tfBirth.getText().trim();
				if(ckLunar.isSelected())
					bLunar = 0; //음력
				else
					bLunar = 1; //양력
				strPic = filePath;
				
				InsertMember();
			}
		});
		btnInsert.setBounds(65, 278, 97, 23);
		add(btnInsert);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(284, 278, 97, 23);
		add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(505, 278, 97, 23);
		add(btnDelete);
		
		tfTel2 = new JTextField();
		tfTel2.setColumns(10);
		tfTel2.setBounds(495, 140, 107, 21);
		add(tfTel2);
		
		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setBounds(473, 143, 20, 15);
		add(lblNewLabel);
		
		
		if(type==1) { // insert
			btnInsert.setVisible(true);
			btnUpdate.setVisible(false);
			btnDelete.setVisible(false);
			btnDup.setVisible(true);
		}else if(type==2) { //update
			btnInsert.setVisible(false);
			btnUpdate.setVisible(true);
			btnDelete.setVisible(false);
			btnDup.setVisible(false);
		}else {			//delete
			btnInsert.setVisible(false);
			btnUpdate.setVisible(false);
			btnDelete.setVisible(true);		
			btnDup.setVisible(false);	
			btnCalendar.setVisible(false);
		}
	}

	protected void SelectMember(String sID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"12345");
			Statement stmt = con.createStatement();			
			String sql = "select * from memberTBL where id='";
			sql = sql + sID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				strPw = rs.getString("pw");
				strName = rs.getString("name");
				strTel = rs.getString("tel");
				strEmail = rs.getString("email");
				strBirth = rs.getString("birth");
				bLunar = rs.getInt("lunar");
				strPic = rs.getString("pic");
				
				tfPw.setText(strPw);
				tfName.setText(strName);
				tfTel1.setText(strTel); // 변경 -> 콤보, tfTel1, tfTel2
				tfEmail.setText(strEmail);
				tfBirth.setText(strBirth);
				if(bLunar==0)
					ckLunar.setSelected(false);
				else
					ckLunar.setSelected(true);
				// strPic 보이기
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	protected void InsertMember() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"12345");
			Statement stmt = con.createStatement();			
			String sql = "insert into memberTBL values('";
			sql = sql + strID + "','" + strPw + "','";
			sql = sql + strName + "','" + strTel + "','";
			sql = sql + strEmail +"@" + cbCompany.getSelectedItem() + "','";
			sql = sql + strBirth + "'," + bLunar + ",'" + strPic + "')";
			
			if(stmt.executeUpdate(sql) > 0) {
				JOptionPane.showMessageDialog(null, "회원이 추가되었습니다");				
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
}
