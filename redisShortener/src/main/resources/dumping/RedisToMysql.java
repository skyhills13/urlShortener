import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RedisToMysql {

	private static Logic logic = new Logic();

	public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		insert("/home/next/Applications/tomcat/webapps/redisCommands");
	}
	
	public static void insert(String fileName) throws IOException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		Connection conn = null;
		Statement st = null;

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			String line;
			line = br.readLine();
			while (line != null) {
				String[] array = line.split(",");
				String longUrl = array[0];
				String shortUrl = array[1];

				String sqlInsert = "INSERT INTO url_data(long_url, short_url) VALUES('"
						+ longUrl.trim() + "','" + shortUrl.trim() + "')";
				try {

					conn = logic.getConnection();
					st = conn.createStatement();
					st.execute(sqlInsert);
					line = br.readLine();

				} finally {
					if (st != null) {
						st.close();
					}
					if (conn != null) {
						conn.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
