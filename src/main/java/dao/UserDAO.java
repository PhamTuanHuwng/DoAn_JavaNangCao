package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.UserModel;
import util.HibernateUtil;
import util.PasswordUtil;

public class UserDAO {


	 public boolean isUserNameExists(String userName) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            Long count = session
	                    .createQuery("SELECT COUNT(u) FROM UserModel u WHERE u.userName = :username", Long.class)
	                    .setParameter("username", userName)
	                    .uniqueResult();
	            return count > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 public boolean register(UserModel user) {
	        if (isUserNameExists(user.getUserName())) {
	            return false; // Không đăng ký nếu tên người dùng đã tồn tại
	        }

	        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
	        String hashedPassword = PasswordUtil.hashPassword(user.getPassWord());
	        user.setPassWord(hashedPassword);

	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.save(user);
	            transaction.commit();
	            return true;
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	public UserModel login(UserModel user) {
		String userName = user.getUserName();
//		String passWord = user.getPassWord();
		String passWord = PasswordUtil.hashPassword(user.getPassWord()); // Mã hóa mật khẩu trước khi kiểm tra
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
