import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class WinMemberManager extends JDialog {

	private final JPanel contentPanel = new JPanel();

	
	
	
	/**
	 * Create the dialog.
	 */
	public WinMemberManager(int type) {
		if(type==1)
			setTitle("회원 [추가] 창");
		else if(type==2)
			setTitle("회원 [변경] 창");
		else 
			setTitle("회원 [삭제] 창");
		setBounds(100, 100, 782, 572);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		Member member = new Member(type);
		getContentPane().add(member);
	}

}
