import java.security.MessageDigest;

public class test {

	public static void main(String[] args) {
		try {
			String pw="321";
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(pw.getBytes());
			pw = toHex(messageDigest.digest());
			System.out.println(pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}
		return sb.toString();
	}
}
