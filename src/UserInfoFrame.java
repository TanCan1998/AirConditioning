import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;

public class UserInfoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7990621731756891776L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UserInfoFrame(User us) {
		setAlwaysOnTop(true);
		setTitle("用户信息");
		// 设置窗体图标
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/1.png"));
		setIconImage(icon.getImage());
		// 设置窗体按钮
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAutoRequestFocus(false);//禁止自动聚焦
		setBounds(100, 100, 325, 231);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("用户名");
		label.setForeground(Color.PINK);
		label.setBounds(64, 38, 57, 21);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("联系电话");
		label_1.setForeground(Color.PINK);
		label_1.setBounds(64, 69, 57, 21);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("地址");
		label_2.setForeground(Color.PINK);
		label_2.setBounds(64, 100, 57, 21);
		contentPane.add(label_2);
		
		JLabel lblNewLabel = new JLabel(us.getName());
		lblNewLabel.setForeground(Color.PINK);
		lblNewLabel.setBounds(146, 41, 119, 15);
		contentPane.add(lblNewLabel);
		
		JLabel label_3 = new JLabel(us.getTel());
		label_3.setForeground(Color.PINK);
		label_3.setBounds(146, 72, 119, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel(us.getAddress());
		label_4.setForeground(Color.PINK);
		label_4.setBounds(146, 103, 119, 15);
		contentPane.add(label_4);
		
		//界面显示居中
		Dimension screen = this.getToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
	}
}
