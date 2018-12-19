import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
	// 客户端Socket
	static Socket s = null;
	protected static LoginFrame lf;// 登陆页面
	protected static MainFrame mf = new MainFrame();// 主页面
	protected static LoadingFrame loadf = new LoadingFrame();// 启动页面
	static OutputStream out;// 输出输入流
	static InputStream in ;
	protected static int flag, flag1;// 线程or循环标签
	protected static Thread th;
	private static DBHelper dh = new DBHelper();
	protected static User us = new User();

	public static void main(String[] args) {
		try {
			lf = new LoginFrame();
			loadf.setVisible(true);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		flag = 1;
		try {
			flag1 = 1;
			while (flag1 != 0) {
				try {
					// 连接服务器
					s = new Socket();
					s.connect(new InetSocketAddress("192.168.2.111", 8888), 4000);
					flag1 = 0;// 连接成功退出循环
				} catch (Exception e) {
					flag1++;
					String s = (flag1 % 2 == 0) ? "连接服务器中......" : "请稍等";
					loadf.label.setText(s);
				}
			}
			Thread.sleep(3500);// 缓冲加载登陆界面，以免太快而闪烁
			loadf.setVisible(false);
			lf.setVisible(true);
			lf.toFront();// 登陆页面前置
			// 获取输入流
			in = s.getInputStream();
			// 获取输出流
			out = s.getOutputStream();
			// 获取服务端首次信息
			byte[] buf = new byte[1024];
			int len = in.read(buf);
			// 将首次信息打印在主显示框内
			mf.mainContent.append(new String(buf, 0, len) + "\n");
			// 启动日志记录线程
			th = new Thread(new LogThread());
			th.start();
			// 持续等待接收服务器信息直至退出
			while (flag == 1) {
				in = s.getInputStream();
				len = in.read(buf);
				// 处理服务器传来的消息
				String msg = new String(buf, 0, len);
				// 消息类型：更新信息
				String type = msg.substring(0, msg.indexOf("/"));
				// 消息本体
				String content = msg.substring(msg.indexOf("/") + 1);
				// 根据消息类型分别处理
				if (type.equals("currentTemp")) {
					// 更新信息
					mf.currentTemp.setText(content);
					int i = Integer.parseInt(content);
					if (i >= 30) {
						mf.currentTemp.setForeground(Color.red);
					} else if (i >= 20) {
						mf.currentTemp.setForeground(new Color(60, 179, 113));
					} else {
						mf.currentTemp.setForeground(new Color(32, 209, 206));
					}
				} else if (type.equals("verification")) {
					if(content.equals("1")) {
						mf.setUser();
						lf.toMainf();
					} else {
						lf.failed();
					}
				} else if (type.equals("userinfo")) {
					String name,address,tel,id,rest;
					name=content.substring(0, content.indexOf("/"));
					rest=content.substring(content.indexOf("/") + 1);
					tel=rest.substring(0, rest.indexOf("/"));
					rest=rest.substring(rest.indexOf("/") + 1);
					address=rest.substring(0, rest.indexOf("/"));
					rest=rest.substring(rest.indexOf("/") + 1);
					id=rest.substring(0, rest.length());
					us.name=name;
					us.tel=tel;
					us.address=address;
					us.id=Integer.parseInt(id);
					mf.usinf.set();
				} else if (type.equals("EXIT")) {
					flag = 0;
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {// 5秒后自动退出
						int MAX_COUNTER = 5;
						int counter = 0;

						@Override
						public void run() {
							if (MAX_COUNTER == counter) {
								System.exit(0);
							}
							mf.logUpdate("服务器关闭\n" + (MAX_COUNTER - counter) + "秒钟后将自动退出\n");
							counter++;
						}
					}, 0, 1000);
				}
			}
		} catch (Exception e) {
			flag = 0;
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {// 服务器发生异常退出
				int MAX_COUNTER = 5;
				int counter = 0;

				@Override
				public void run() {
					if (MAX_COUNTER == counter) {
						System.exit(0);
					}
					mf.logUpdate("服务器异常\n" + (MAX_COUNTER - counter) + "秒钟后将自动退出\n");
					counter++;
				}
			}, 0, 1000);
		}
	}

	public static void out(String str) throws IOException {// 发送信息
		try {
			OutputStream out = s.getOutputStream();
			out.write(str.getBytes());
		} catch (IOException e) {
			mf.mainContent.append("发送未成功\n");
			throw new IOException();
		}
	}

	public static void tableflush() {//刷新表格
		dh.conn();
		String[] cloum = { "日期", "室温", "模式", "强力", "除湿" };
		Object[][] row = new Object[30][5];
		try {
			String sql = "select * from log order by id desc limit 30";// sql语句
			ResultSet rs = dh.getRS(sql);
			int i = 0;
			while (rs.next()) {
				row[i][0] = rs.getString("time");
				row[i][1] = rs.getString("tempre");
				row[i][2] = rs.getString("mode");
				row[i][3] = rs.getString("strongMode");
				row[i][4] = rs.getString("dry");
				i++;
			}
			MainFrame.dtm.setDataVector(row, cloum);
			MainFrame.dtm.fireTableStructureChanged();
			MainFrame.tableresize();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.close();
		}
	}
}