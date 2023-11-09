
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectSql {
	
	static Connection conn;
	static String url = "jdbc:sqlite:./lichSuThanhThua.db";
	static Statement stmt;
	static PreparedStatement pstmt;
	static ResultSet rs;

	public static void ConnectSql() {
		conn =null;
		try {
//			url = "jdbc:sqlite:./lichSuThanhThua.db";
			conn = DriverManager.getConnection(url);
			System.out.println("Connection Thanh Cong");
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void taoBang() {
		String sql = "CREATE TABLE IF NOT EXISTS thangThua (\n"
				+ "	stt integer PRIMARY KEY,\n"
				+ "	player1 text NOT NULL,\n"
				+ "	player2 text NOT NULL,\n"
				+ "	playerwin text,\n"
				+ " tongso integer \n"
				+ ");";
		
//		String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
//				 + " id integer PRIMARY KEY,\n"
//				 + " name text NOT NULL,\n"
//				 + " capacity real\n"
//				 + " capacity real\n"
//				 + ");";
		
		try {
			
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			stmt.execute(sql);
			System.out.println("Tao bang thanh cong");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void insert(String player1, String player2, String playerwin, int tongso) {
		String sql = "INSERT INTO thangThua(player1,player2,playerwin,tongso) VALUES(?,?,?,?)";
		try {
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player1);
			pstmt.setString(2, player2);
			pstmt.setString(3, playerwin);
			pstmt.setInt(4, tongso);
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void selectAll() {
		String sql = "SELECT stt, player1, player2, playerwin, tongso FROM thangThua";
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("Stt \t Player1 \t Player2 \t Playerwin \t Tong so");
			while (rs.next()) {
				System.out.println(rs.getInt("stt") +  "\t" + 
                        rs.getString("player1") + "\t\t" +
                        rs.getString("player2") + "\t\t" +
                        rs.getString("playerwin") + "\t\t" +
                        rs.getInt("tongso"));
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(int stt) {
		String sql = "DELETE FROM thangThua WHERE stt = ?";
		try {
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, stt);
			pstmt.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteTable() {
		String sql = "DROP TABLE IF EXISTS 'thangThua' ";
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
//			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteAllData() {
		String sql = "DELETE FROM thangThua";
		try {
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		//ConnectSql();
		//taoBang();
	    //insert("van","a","van",1);
		//insert("c","b","a");
		//System.out.println("insert thang cong");
		//delete(20);
		//delete(21);
		//deleteTable();
		//deleteAllData();
		selectAll();
		
	}

}
