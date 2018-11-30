import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;

public class mainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JMenuItem deviceBinding, userSwitch, accountInfo, troubleShooting;
	private JTextField model;
	private loginFrame lf;
	private user us;
	private userInfo usinf;
	private JComboBox<String> mode, windSpeed;
	private JTextArea textArea;
	private JButton refresh, switchButton, history, on_off, down, up;
	private JLabel currentTemp, setTemp;
	private JRadioButton dry, strongMode;
	private JScrollPane log;

	/**
	 * Create the frame.
	 */
	public mainFrame(loginFrame lof, user u) {
		lf = lof;
		us = u;
		usinf = new userInfo(us);
		setTitle("欢迎您，" + us.getName());
		setResizable(false);
		// 设置窗体图标
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/1.png"));
		setIconImage(icon.getImage());
		// 设置窗体按钮
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 439);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu system = new JMenu("系统");
		menuBar.add(system);
		system.addActionListener(this);

		deviceBinding = new JMenuItem("设备绑定");
		system.add(deviceBinding);
		deviceBinding.addActionListener(this);

		userSwitch = new JMenuItem("切换账号");
		system.add(userSwitch);
		userSwitch.addActionListener(this);

		JMenu detection = new JMenu("检测");
		menuBar.add(detection);

		troubleShooting = new JMenuItem("故障排查");
		detection.add(troubleShooting);
		troubleShooting.addActionListener(this);

		JMenu account = new JMenu("账号");
		menuBar.add(account);

		accountInfo = new JMenuItem("账户信息");
		account.add(accountInfo);
		accountInfo.addActionListener(this);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 251, 246));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(213, 241, 219), 5, true));
		panel.setBackground(Color.WHITE);
		panel.setBounds(20, 10, 353, 287);
		contentPane.add(panel);

		refresh = new JButton("刷新");
		refresh.setBackground(new Color(213, 241, 219));
		refresh.setBounds(131, 307, 93, 23);
		contentPane.add(refresh);
		refresh.addActionListener(this);

		switchButton = new JButton("切换设备");
		switchButton.setBackground(new Color(213, 241, 219));
		switchButton.setBounds(234, 307, 93, 23);
		contentPane.add(switchButton);
		switchButton.addActionListener(this);

		dry = new JRadioButton("除湿");
		dry.setBackground(new Color(245, 251, 246));
		dry.setBounds(21, 307, 79, 23);
		contentPane.add(dry);
		dry.addActionListener(this);

		strongMode = new JRadioButton("强力");
		strongMode.setBackground(new Color(245, 251, 246));
		strongMode.setBounds(20, 341, 79, 23);
		contentPane.add(strongMode);
		strongMode.addActionListener(this);

		JLabel label = new JLabel("型号：");
		label.setBounds(383, 28, 54, 15);
		contentPane.add(label);

		model = new JTextField();
		model.setEnabled(false);
		model.setBounds(433, 25, 126, 21);
		model.setColumns(10);
		model.setEditable(false);
		contentPane.add(model);

		JLabel label_1 = new JLabel("模式：");
		label_1.setBounds(383, 63, 54, 15);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("风速：");
		label_2.setBounds(383, 98, 54, 15);
		contentPane.add(label_2);

		mode = new JComboBox<String>();
		mode.setBounds(433, 60, 126, 21);
		mode.setBackground(new Color(213, 241, 219));
		contentPane.add(mode);

		history = new JButton("历史数据");
		history.setBackground(new Color(213, 241, 219));
		history.setBounds(131, 341, 93, 23);
		contentPane.add(history);
		history.addActionListener(this);

		windSpeed = new JComboBox<String>();
		windSpeed.setBounds(433, 95, 126, 21);
		windSpeed.setBackground(new Color(213, 241, 219));
		contentPane.add(windSpeed);

		log = new JScrollPane();
		log.setBorder(new LineBorder(new Color(213, 241, 219), 4, true));
		log.setBounds(387, 259, 172, 105);
		log.setEnabled(false);
		log.getViewport().setBackground(Color.WHITE);
		contentPane.add(log);

		textArea = new JTextArea();
		textArea.setEditable(false);
		log.setViewportView(textArea);

		JLabel label_3 = new JLabel("使用日志");
		label_3.setBounds(445, 232, 54, 15);
		contentPane.add(label_3);

		on_off = new JButton("电源");
		on_off.setBackground(new Color(213, 241, 219));
		on_off.setBounds(234, 342, 93, 23);
		contentPane.add(on_off);
		on_off.addActionListener(this);

		down = new JButton("降");
		down.setBounds(479, 194, 54, 30);
		contentPane.add(down);
		down.addActionListener(this);

		up = new JButton("升");
		up.setBounds(413, 194, 54, 30);
		contentPane.add(up);
		up.addActionListener(this);

		JLabel lblNewLabel = new JLabel("当前温度");
		lblNewLabel.setBounds(382, 132, 55, 18);
		contentPane.add(lblNewLabel);

		JLabel label_4 = new JLabel("设定温度");
		label_4.setBounds(382, 164, 55, 18);
		contentPane.add(label_4);

		currentTemp = new JLabel("0");
		currentTemp.setForeground(new Color(60, 179, 113));
		currentTemp.setBounds(443, 132, 24, 18);
		contentPane.add(currentTemp);

		setTemp = new JLabel("0");
		setTemp.setForeground(new Color(60, 179, 113));
		setTemp.setBounds(444, 164, 23, 18);
		contentPane.add(setTemp);

		JLabel lblNewLabel_2 = new JLabel("℃");
		lblNewLabel_2.setBounds(470, 132, 17, 18);
		contentPane.add(lblNewLabel_2);

		JLabel label_6 = new JLabel("℃");
		label_6.setBounds(470, 164, 17, 18);
		contentPane.add(label_6);

		// 界面显示居中
		Dimension screen = this.getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
	}

	// 点击按钮后的操作
	public void actionPerformed(ActionEvent e) {
		Object temp = e.getSource();
		if (temp == userSwitch) {// 切换账号
			lf.userSwitch();
			lf.setVisible(true);
			this.dispose();
		} else if (temp == accountInfo) {// 查看账号信息
			logUpdate("账号信息\n" + "id:" + "  " + us.getId() + "\n");
			usinf.setVisible(true);
		} else if (temp == up) {// 提高设定温度
			logUpdate("升温\n");
		} else if (temp == down) {// 降低设定温度
			logUpdate("降温\n");
		} else if (temp == history) {// 查看历史数据
			logUpdate("历史数据\n");
		} else if (temp == refresh) {// 刷新图表框
			logUpdate("刷新图表\n");
		} else if (temp == on_off) {// 开机、关机
			logUpdate("电源\n");
		} else if (temp == switchButton) {// 切换设备
			logUpdate("切换设备\n");
		} else if (temp == deviceBinding) {// 绑定设备
			logUpdate("绑定设备\n");
		} else if (temp == troubleShooting) {// 故障排查
			logUpdate("故障排查\n");
		} else if (temp == dry) {// 故障排查
			if (dry.isSelected()) {
				logUpdate("除湿\t开\n");
			} else {
				logUpdate("除湿\t关\n");
			}
		} else if (temp == strongMode) {// 故障排查
			if (strongMode.isSelected()) {
				logUpdate("强力\t开\n");
			} else {
				logUpdate("强力\t关\n");
			}
		}
	}

	// 更新日志框文本
	public void logUpdate(String s) {
		textArea.append("----------------------------------\n" + s);
		log.getVerticalScrollBar().setValue(log.getVerticalScrollBar().getMaximum() + 1);
	}

}
