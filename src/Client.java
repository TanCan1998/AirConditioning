import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	// 建立客户端Socket
	static Socket s = null;
	// 消息接收者uid
	static StringBuilder uidReceiver = null;
	protected static loginFrame lf;
	protected static mainFrame mf = new mainFrame();

	public static void main(String[] args) {
		try {
			lf = new loginFrame();
			lf.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 连接服务器
			s = new Socket("192.168.43.111", 8888);
			// 获取输入流
			InputStream in = s.getInputStream();
			// 获取输出流
			OutputStream out = s.getOutputStream();

			// 获取服务端欢迎信息
			byte[] buf = new byte[1024];
			int len = in.read(buf);
			// 将欢迎信息打印在消息框内
			mf.mainContent.append(new String(buf, 0, len) + "\n");
			// 持续等待接收服务器信息直至退出
			while (true) {
				in = s.getInputStream();
				len = in.read(buf);
				System.out.println(len);
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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void out(String str) {
		try {
			OutputStream out = s.getOutputStream();
			out.write(str.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}