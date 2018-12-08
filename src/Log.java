import java.sql.ResultSet;
import java.sql.SQLException;

public class Log extends Thread {
	protected String mode, currentTemp, strongMode, dry, windSpeed;
	private String sqlStr;
	private static DBHelper dh = new DBHelper();
	mainFrame mf=Client.mf;
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(30000);
				sqlStr="insert into  log (time,tempre,mode,strongMode,dry)";
				dh.DoInsert(sqlStr);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
