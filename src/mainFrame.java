import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTable;

public class mainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JMenuItem deviceBinding, userSwitch, accountInfo, troubleShooting, exit;
	private JTextField model;
	private user us;
	private userInfo usinf;
	protected JComboBox<?> mode, windSpeed;
	protected JTextArea textArea, mainContent;
	private JButton refresh, switchButton, history, on_off, down, up;
	protected JLabel currentTemp, setTemp;
	protected JRadioButton dry, strongMode;
	private JScrollPane log;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); // 设置成nimbus风格
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		setResizable(false);
		// 设置窗体图标
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/1.png"));
		setIconImage(icon.getImage());
		// 设置窗体按钮
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 583, 439);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu system = new JMenu("系统");
		menuBar.add(system);
		system.addActionListener(this);

		deviceBinding = new JMenuItem("设备绑定");
		system.add(deviceBinding);
		deviceBinding.addActionListener(this);

		exit = new JMenuItem("退出");
		system.add(exit);
		exit.addActionListener(this);

		JMenu detection = new JMenu("检测");
		menuBar.add(detection);

		troubleShooting = new JMenuItem("故障排查");
		detection.add(troubleShooting);
		troubleShooting.addActionListener(this);

		JMenu account = new JMenu("账号");
		menuBar.add(account);

		accountInfo = new JMenuItem("账户信息");
		account.add(accountInfo);

		userSwitch = new JMenuItem("切换账号");
		account.add(userSwitch);
		userSwitch.addActionListener(this);
		accountInfo.addActionListener(this);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 251, 246));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(213, 241, 219), 5, true));
		panel.setBackground(Color.WHITE);
		panel.setBounds(20, 10, 353, 273);
		contentPane.add(panel);
		panel.setLayout(null);

		mainContent = new JTextArea();
		mainContent.setEnabled(false);
		mainContent.setEditable(false);
		mainContent.setBounds(6, 6, 341, 126);
		panel.add(mainContent);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(6, 141, 341, 126);
		panel.add(scrollPane);
		String[] cloum = { "日期", "室温", "模式", "强力", "除湿" };
		Object[][] row = new Object[30][5];
		DefaultTableModel dtm = new DefaultTableModel(row, cloum);
		table = new JTable(dtm);
		table.setCellSelectionEnabled(true);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.getTableHeader().setReorderingAllowed(false);// 表头不可拖动
		table.setSelectionBackground(new Color(224, 255, 255));
		table.setSelectionForeground(Color.black);
		table.setDefaultRenderer(Object.class, tcr);
		scrollPane.setViewportView(table);

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

		model = new JTextField("格力");
		model.setEnabled(false);
		model.setBounds(433, 22, 126, 26);
		model.setColumns(10);
		model.setEditable(false);
		contentPane.add(model);

		JLabel label_1 = new JLabel("模式：");
		label_1.setBounds(383, 63, 54, 15);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("风速：");
		label_2.setBounds(383, 98, 54, 15);
		contentPane.add(label_2);

		String s1[] = { "制冷", "制热" };
		mode = new JComboBox(s1);
		mode.setBounds(433, 60, 126, 21);
		mode.setBackground(new Color(213, 241, 219));
		mode.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// 如果选中了一个
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String type = (String) mode.getSelectedItem();
					if (type.equals("制热")) {
						logUpdate("模式\t制热\n");
						Client.out("mode/制热");
					} else {
						logUpdate("模式\t制冷\n");
						Client.out("mode/制冷");
					}
				}
			}
		});
		contentPane.add(mode);

		history = new JButton("历史数据");
		history.setBackground(new Color(213, 241, 219));
		history.setBounds(131, 341, 93, 23);
		contentPane.add(history);
		history.addActionListener(this);

		String s2[] = { "大", "中", "小" };
		windSpeed = new JComboBox(s2);
		windSpeed.setBounds(433, 95, 126, 21);
		windSpeed.setBackground(new Color(213, 241, 219));
		windSpeed.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// 如果选中了一个
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String type = (String) windSpeed.getSelectedItem();
					if (type.equals("大")) {
						logUpdate("风速\t大\n");
						Client.out("windSpeed/大");
					} else if (type.equals("中")) {
						logUpdate("风速\t中\n");
						Client.out("windSpeed/中");
					} else {
						logUpdate("风速\t小\n");
						Client.out("windSpeed/小");
					}
				}
			}
		});
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

		currentTemp = new JLabel("25");
		currentTemp.setForeground(new Color(60, 179, 113));
		currentTemp.setBounds(443, 132, 24, 18);
		contentPane.add(currentTemp);

		setTemp = new JLabel("20");
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

	// 设置用户
	public void setUser(user u) {
		us = u;
		usinf = new userInfo(us);
		setTitle("欢迎您，" + us.getName());
	}

	// 点击按钮后的操作
	public void actionPerformed(ActionEvent e) {
		Object temp = e.getSource();
		if (temp == userSwitch) {// 切换账号
			Client.lf.userSwitch();
			Client.lf.setVisible(true);
			textArea.setText(null);
			this.dispose();
		} else if (temp == accountInfo) {// 查看账号信息
			logUpdate("账号信息\n" + "id:" + "  " + us.getId() + "\n");
			usinf.setVisible(true);
		} else if (temp == up) {// 提高设定温度
			int i = Integer.parseInt(setTemp.getText());
			if (i == 30) {
				JOptionPane.showMessageDialog(null, "温度不能超过30度！", "【提醒】", JOptionPane.DEFAULT_OPTION);
			} else {
				logUpdate("升温\t" + setTemp.getText() + "+1\n");
				setTemp.setText(i + 1 + "");
				Client.out("setTemp/" + setTemp.getText());
			}
		} else if (temp == down) {// 降低设定温度
			int i = Integer.parseInt(setTemp.getText());
			if (i == 16) {
				JOptionPane.showMessageDialog(null, "温度不能低于16度！", "【提醒】", JOptionPane.DEFAULT_OPTION);
			} else {
				logUpdate("降温\t" + setTemp.getText() + "-1\n");
				setTemp.setText(i - 1 + "");
				Client.out("setTemp/" + setTemp.getText());
			}
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
				Client.out("dry/开");
			} else {
				logUpdate("除湿\t关\n");
				Client.out("dry/关");
			}
		} else if (temp == strongMode) {// 故障排查
			if (strongMode.isSelected()) {
				logUpdate("强力\t开\n");
				Client.out("strongMode/关");
			} else {
				logUpdate("强力\t关\n");
				Client.out("strongMode/关");
			}
		} else if (temp == exit) {// 故障排查
			Client.out("Exit/");
			System.exit(0);
		}
	}

	// 更新日志框文本
	public void logUpdate(String s) {
		textArea.append("----------------------------------\n" + s);
		log.getVerticalScrollBar().setValue(log.getVerticalScrollBar().getMaximum());
	}
}
