
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class WinShowAllBooks extends JDialog implements PropertyChangeListener {

	private final JPanel contentPanel = new JPanel();
	private Task task;
	private int total;
	private ProgressMonitor progressMonitor;	
	private JTabbedPane tabbedPane;
	int count=0;	
	class ScheduledJob extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			count++;
			if(count == tabbedPane.getTabCount())
				count = 0;
			tabbedPane.setSelectedIndex(count);
		}		
	}
	class Task extends SwingWorker<Void, Void>{
		@Override
		protected Void doInBackground() throws Exception {
			int progress = 0;
			setProgress(progress);
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
			
				Connection con = 
						DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/sqlDB",
								"root",
								"12345");
				Statement stmt = con.createStatement();			
				String sql = "SELECT count(*) FROM booktbl";
				ResultSet rs = stmt.executeQuery(sql);	
				if(rs.next())
					total = rs.getInt(1);
				//System.out.println("전체 책 수: " + total);
				
				sql = "SELECT * FROM booktbl";
				rs = stmt.executeQuery(sql);
				
				int cnt=0;
				while(rs.next() && progress < 100) {
					Thread.sleep(100);
					progress = 100 * (++cnt) / total;
					setProgress(Math.min(progress, 100));
					
					Vector <String> vec = new Vector<>();
					for(int i=1;i<=8;i++)
						vec.add(rs.getString(i)); // ISBN
									
					Book book = new Book(vec);
					tabbedPane.addTab(vec.get(1), book);					
				}
												
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void done() {
			ScheduledJob job = new ScheduledJob();
			Timer jobScheduler = new Timer();
			jobScheduler.scheduleAtFixedRate(job, 1000, 100);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public WinShowAllBooks() {
		setTitle("모든 도서 탭으로 보기");
		setBounds(100, 100, 623, 645);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		//--------------------------------------------------------
		UIManager.put("ProgressMonitor.progressText", "Loading..."); // 제목 수정
		progressMonitor = new ProgressMonitor(WinShowAllBooks.this,
				"전체 도서를 읽고 있습니다.", "", 0, 100);
		
		progressMonitor.setProgress(0);
		task = new Task();
		task.addPropertyChangeListener((PropertyChangeListener) this);
		task.execute();
		//--------------------------------------------------------
		
		tabbedPane.setBounds(12, 20, 591, 576);
		contentPanel.add(tabbedPane);
	}


	@Override  // implements PropertyChangeListener 추가시 unimplement~ 클릭으로 자동 생성
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		  if ("progress" == evt.getPropertyName() ) {
	            int progress = (Integer) evt.getNewValue();
	            progressMonitor.setProgress(progress);
	            String message =
	                String.format("%d%% 로딩 중...", progress);
	            progressMonitor.setNote(message);	            
	            if (progressMonitor.isCanceled() || task.isDone()) 
	                Toolkit.getDefaultToolkit().beep();
		  }
	}
}
