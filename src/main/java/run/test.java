package run;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.UserModel;
import util.HibernateUtil;

public class test {
	public static void main(String[] args) {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session = sessionFactory.openSession();
				try {
					Transaction transaction = session.beginTransaction();
					UserModel user1 = new UserModel();
					user1.setUserName("hieuxau123");
					user1.setPassWord("123");
					user1.setFullName("Trần Minh Hiếu");
					session.save(user1);
					transaction.commit();
				} finally {
					session.close();
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
