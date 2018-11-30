import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class loginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField t_user;
	private JPasswordField t_password;
	private static loginFrame frame;
	private static mainFrame mf;
	private user us = new user();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new loginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); // 设置成nimbus风格
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		// 设置窗体图标
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/1.png"));
		setIconImage(icon.getImage());
		// 设置窗体按钮
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAutoRequestFocus(false);//禁止自动聚焦
		setTitle("登录");
		setBounds(100, 100, 330, 317);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		t_user = new JTextField();
		t_user.setBounds(126, 84, 122, 22);
		t_user.setColumns(10);
		contentPane.setLayout(null);

		JLabel title = new JLabel("   欢迎使用空调控制系统   ");
		title.setFont(new Font("SansSerif", Font.PLAIN, 15));
		title.setBounds(74, 39, 174, 20);
		contentPane.add(title);
		title.setForeground(Color.PINK);
		title.setBackground(Color.DARK_GRAY);

		JLabel l_user = new JLabel("账  号：");
		l_user.setBounds(74, 88, 52, 18);
		l_user.setForeground(Color.PINK);
		contentPane.add(l_user);
		contentPane.add(t_user);

		JLabel l_password = new JLabel("密  码：");
		l_password.setBounds(74, 124, 52, 18);
		l_password.setForeground(Color.PINK);
		contentPane.add(l_password);

		t_password = new JPasswordField();
		t_password.setBounds(126, 120, 122, 22);
		t_password.setColumns(10);
		contentPane.add(t_password);

		JButton enter = new JButton("进入");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s1, s2;
				s1 = t_user.getText().replace(" ", "");
				s2 = new String(t_password.getPassword()).replace(" ", "");
				if (s1.equals("")) {
					JOptionPane.showMessageDialog(null, "账号未输入！", "【提示】", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int check = us.verification(s1, s2);
				if (check == 1) {
					mf = new mainFrame(frame, us);
					mf.setVisible(true);
					frame.setVisible(false);
				} else if (check == -1) {
					JOptionPane.showMessageDialog(null, "账号或密码错误！", "【出错啦】", JOptionPane.WARNING_MESSAGE);
					t_password.setText("");
				}
			}
		});
		enter.setBounds(173, 179, 73, 28);
		enter.setForeground(Color.GRAY);
		contentPane.add(enter);

		JButton exit = new JButton("退出");
		exit.setBounds(173, 225, 73, 28);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exit.setForeground(Color.GRAY);
		contentPane.add(exit);

		JButton register = new JButton("注册");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		register.setForeground(Color.GRAY);
		register.setBounds(74, 179, 73, 28);
		contentPane.add(register);

		JButton about = new JButton("关于");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Version:  1.0.0\nAuthers:  苏敬澎，谭灿", "【软件信息】", JOptionPane.DEFAULT_OPTION);
			}
		});
		about.setForeground(Color.GRAY);
		about.setBounds(74, 225, 73, 28);
		contentPane.add(about);
		// 界面不可调整大小
		this.setResizable(false);
		// 界面显示居中
		Dimension screen = this.getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
		
		//测试避免输入密码
		login("123", "123");
	}
	
	public void userSwitch() {
		t_user.setText("");
		t_password.setText("");
	}
	
	public void login(String nm, String pw) {
		t_user.setText(nm);
		t_password.setText(pw);
	}
}
