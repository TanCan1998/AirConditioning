import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBHelper {
	Connection con = null;
	public void conn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String uri = "jdbc:mysql://localhost/airconditioning?characterEncoding=utf-8";
			String user = "root";
			String password = "root";
			con = DriverManager.getConnection(uri, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ResultSet getRS(String sql) throws SQLException {
		Statement s;
		s = con.createStatement();
		return s.executeQuery(sql);
	}
	public void DoInsert(String sql) {
		Statement s;
		try {
			s = con.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
