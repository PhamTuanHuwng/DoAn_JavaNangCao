package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.UserModel;
import util.HibernateUtil;

public class UserDAO {

//	Statement stm = null;
//	PreparedStatement pstm = null;
//	ResultSet rs = null;
//	Connection conn = null;

//	public UserModel login(UserModel user) {
//		String userName = user.getUserName();
//		String passWord = user.getPassWord();
//		String sql = "SELECT * FROM AccountUser where Username=? and Password=?";
//		try {
//			pstm = conn.prepareStatement(sql);
//			pstm.setString(1, userName);
//			pstm.setString(2, passWord);
//			rs = pstm.executeQuery();
//			if(rs.next()) {
//                UserModel loggedInUser = new UserModel();
//                loggedInUser.setUserName(rs.getString("Username"));
//                loggedInUser.setPassWord(rs.getString("Password"));
//                loggedInUser.setFullName(rs.getString("Fullname"));
//                return loggedInUser;
//			}
//		} catch (Exception e) {
//			return null;
//		}
//		return null;
//	}
	public UserModel login(UserModel user) {
		String userName = user.getUserName();
		String passWord = user.getPassWord();
		UserModel loggedInUser = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			loggedInUser = session
					.createQuery("FROM UserModel WHERE userName = :username AND passWord = :password",UserModel.class)
					.setParameter("username", userName).setParameter("password", passWord)
					.uniqueResult();

		} catch (Exception e) {
			
			e.printStackTrace(); 
			return null;
		}
		return loggedInUser;
	}

}
