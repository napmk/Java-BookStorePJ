import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinChoice extends JDialog {
	private String strPublisher;
	public WinChoice(Vector v) {
		setTitle("출판사 선택");
		setBounds(100, 100, 220, 262);
		
		JList lstPublisher = new JList();
		lstPublisher.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				strPublisher = lstPublisher.getSelectedValue().toString();
				setVisible(false);
			}
		});
		getContentPane().add(lstPublisher, BorderLayout.CENTER);
			
		lstPublisher.setListData(v);
	}
	public String getPublisher() {
		return strPublisher;
	}

}
