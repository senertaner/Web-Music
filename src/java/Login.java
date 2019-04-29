
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alica
 */
public class Login {
    public  static boolean validate(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;
               DataConnect connect=new DataConnect();
               
              if(password.equals("")||password.equals("")){
             FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please check all information",
		 					"Please enter correct username and Password"));
         return false;
        }else{
		try {
			
                        con=connect.dataSource.getConnection();
			ps = con.prepareStatement("Select USERNAME, PASSWORD from UYELER where USERNAME = ? and PASSWORD = ?");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
                    connect.close(con);
		}
		return false;
	}
              }
    public static class DataConnect {
DataSource dataSource;
	public  DataConnect() {
		try {
           Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/webmusic");
        } catch (NamingException e) {
             e.printStackTrace();
        }  
	}

	public  void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
}
}
