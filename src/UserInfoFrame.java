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
	User us;
	private JPanel contentPane;
	protected JLabel name,tel,address;

	/**
	 * Create the frame.
	 */
	public UserInfoFrame(User us) {
		this.us=us;
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
		
		name = new JLabel(us.name);
		name.setForeground(Color.PINK);
		name.setBounds(146, 41, 119, 15);
		contentPane.add(name);
		
		tel = new JLabel(us.tel);
		tel.setForeground(Color.PINK);
		tel.setBounds(146, 72, 119, 15);
		contentPane.add(tel);
		
		address = new JLabel(us.address);
		address.setForeground(Color.PINK);
		address.setBounds(146, 103, 119, 15);
		contentPane.add(address);
		
		//界面显示居中
		Dimension screen = this.getToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
	}
	public void set() {
		name.setText(us.name);
		address.setText(us.address);
		tel.setText(us.tel);
	}
}
