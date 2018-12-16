import java.text.SimpleDateFormat;
import java.util.Date;


public class LogThread extends Thread {
	protected String mode, currentTemp, strongMode, dry, time;
	private String sqlStr;
	private static DBHelper dh = new DBHelper();
	MainFrame mf = Client.mf;

	@Override
	public void run() {
		try {
			while (Client.flag == 1) {
				Thread.sleep(6000);
				Date date = new Date();// 获得系统时间.
				SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
				time = "'" + sdf.format(date) + "',";
				mode = "'" + Client.mf.mode.getSelectedItem().toString() + "',";
				currentTemp = "'" + Client.mf.currentTemp.getText() + "',";
				strongMode = "'" + (Client.mf.strongMode.isSelected() ? "开" : "关") + "',";
				dry = "'" + (Client.mf.dry.isSelected() ? "开" : "关") + "'";
				sqlStr = "insert into log (time,tempre,mode,strongMode,dry)values(" + time + currentTemp + mode
						+ strongMode + dry + ")";
				dh.conn();
				dh.DoInsert(sqlStr);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			dh.close();
		}
	}
}
