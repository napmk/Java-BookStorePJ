import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class Book extends JPanel {
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTextField tfpDate;
	private JTextField tfPrice;
	private JTextField tfISBN;
	
	public Book(Vector vec) {
		setLayout(null);
		
		JLabel lblPic = new JLabel("<html><img src='https://shopping-phinf.pstatic.net/main_3248718/32487182777.20221019152842.jpg' width=150 height=200></html>");
		lblPic.setOpaque(true);
		lblPic.setBackground(Color.YELLOW);
		lblPic.setBounds(12, 10, 150, 200);
		add(lblPic);
		
		JLabel lblTitle = new JLabel("책 제목:");
		lblTitle.setBounds(191, 13, 57, 15);
		add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		tfTitle.setBackground(Color.YELLOW);
		tfTitle.setBounds(256, 10, 316, 21);
		add(tfTitle);
		
		JLabel lblAuthor = new JLabel("저자:");
		lblAuthor.setBounds(191, 53, 57, 15);
		add(lblAuthor);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(256, 50, 316, 21);
		add(tfAuthor);
		
		JLabel lblPublisher = new JLabel("출판사:");
		lblPublisher.setBounds(191, 94, 57, 15);
		add(lblPublisher);
		
		tfPublisher = new JTextField();
		tfPublisher.setColumns(10);
		tfPublisher.setBackground(Color.YELLOW);
		tfPublisher.setBounds(256, 91, 316, 21);
		add(tfPublisher);
		
		JLabel lblpDate = new JLabel("출판일:");
		lblpDate.setBounds(191, 138, 57, 15);
		add(lblpDate);
		
		tfpDate = new JTextField();
		tfpDate.setColumns(10);
		tfpDate.setBounds(256, 135, 158, 21);
		add(tfpDate);
		
		tfPrice = new JTextField();
		tfPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrice.setColumns(10);
		tfPrice.setBounds(256, 179, 104, 21);
		add(tfPrice);
		
		JLabel lblPrice = new JLabel("\uAC00\uACA9:");
		lblPrice.setBounds(191, 182, 57, 15);
		add(lblPrice);
		
		JLabel lblContents = new JLabel("책 소개:");
		lblContents.setBounds(12, 220, 57, 15);
		add(lblContents);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 234, 560, 302);
		add(scrollPane);
		
		JTextArea taContents = new JTextArea();
		taContents.setLineWrap(true);
		scrollPane.setViewportView(taContents);
		
		JButton btnCalendar = new JButton("날짜 선택");
		btnCalendar.setBounds(421, 134, 97, 23);
		add(btnCalendar);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(403, 182, 57, 15);
		add(lblIsbn);
		
		tfISBN = new JTextField();
		tfISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		tfISBN.setColumns(10);
		tfISBN.setBounds(468, 179, 104, 21);
		add(tfISBN);
		
		tfISBN.setText(vec.get(0).toString());
		tfTitle.setText(vec.get(1).toString());
		tfAuthor.setText(vec.get(2).toString());
		tfPublisher.setText(vec.get(3).toString());
		tfpDate.setText(vec.get(4).toString());
		tfPrice.setText(vec.get(6).toString());
		taContents.setText(vec.get(7).toString());
		lblPic.setText("<html><img src='"+vec.get(5).toString()+"' width=150 height=200></html>");
	}
}
