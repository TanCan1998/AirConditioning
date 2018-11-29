import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user {
	public int getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	private int id;
	private String name, password, address, tel, account;
	private DBHelper dh = new DBHelper();
	
	private static String toHex(byte buffer[]) {//byte[]转String
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}
		return sb.toString();
	}

	public int verification(String nm, String pw) {//身份验证
		dh.conn();
		try {
			String sql = "select * from user where account =" + nm;//sql语句
			ResultSet rs = dh.getRS(sql);
			if (rs.next()) {
				String password = rs.getString("password");//得到结果集中加密的密码
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");//
				messageDigest.update(pw.getBytes());						   //加输入的密码进行加密
				pw = toHex(messageDigest.digest());							   //
				if (pw.equals(password)) {
					id=rs.getInt("id");
					account=rs.getString("account");
					tel=rs.getString("tel");
					name=rs.getString("name");
					password=rs.getString("password");
					address=rs.getString("address");
					return 1;
				} else
					return -1;
			} else
				return -1;
		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
			dh.close();
		}
	}
}
