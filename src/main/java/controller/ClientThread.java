package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import dao.UserDAO;
import model.UserModel;
import run.ClientRun;
import run.ServerRun;
import view.HomeView;

public class ClientThread implements Runnable {
	private Socket socket;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private UserModel userModel;
	private UserDAO userDao;

	public ClientThread(Socket socket) throws IOException {
		this.socket = socket;
		this.dIn = new DataInputStream(socket.getInputStream());
		this.dOut = new DataOutputStream(socket.getOutputStream());

	}

	@Override
	public void run() {
		String request;
		while (!ServerRun.isShutdown()) {
			try {
				System.out.println("Đang chờ client request");
				request = dIn.readUTF();
				System.out.println("Receive request from client: " + request);
				String type = request.split(";")[0];
				switch (type) {
				case "LOGIN":

					handleLogin(request);
					break;
				case "CHAT_ALL":
					handleMess(request);
					break;
				case "REGISTER":
					handleRegister(request);
					break;
				case "PRIVATE_MESSAGE":
					handlePrivateMess(request);
				default:
					break;
				}
			} catch (Exception e) {
				System.err.println("Kết nối bị tắt đột ngột");
				break;
			}
		}

		// mat ket noi
		try {
			socket.close();
			dIn.close();
			dOut.close();
			ServerRun.removeConnect(this);
			ServerRun.updateOnlineList();
			System.out.println("Client disconnected: " + socket);
		} catch (IOException ex) {
			System.err.println("Mất ket noi: " + ex.getMessage());
		}

	}
	private void handleMess(String request) {
		ServerRun.getClientThreadManager().broadcast(request);
	}
	private void handlePrivateMess(String request) {
		//"PRIVATE_MESSAGE;" + sender + ";" + receiver + ";" + message;
		String[] parts = request.split(";");
		String sender = parts[1];
		String receiver = parts[2];
		String mess = parts[3];

		for (ClientThread client : ServerRun.getClientThreadManager().getClientThreads()) {
			if(client.getUserModel().getFullName().equals(receiver)&&client.getUserModel().getFullName().equals(sender)) {
			
				String data = "PRIVATE_MESSAGE;"+sender+";"+mess;
				client.sendDataToClient(data);
			}
		}
	}
	

	private void handleLogin(String request) {
		String[] splitted = request.split(";");
		String username = splitted[1];
		String password = splitted[2];

		boolean isLogedIn = ServerRun.getClientThreadManager().isLogedIn(username);
		if (isLogedIn) {
			sendDataToClient("LOGIN;failed;Tài khoản đã được đăng nhập ở nơi khác");
		} else {
			 userDao = new UserDAO();
			UserModel newUserLogin = new UserModel(username, password);
			UserModel loggedInUser = userDao.login(newUserLogin);

			// Nếu tài khoản đúng
			if (loggedInUser != null) {
				setUserModel(loggedInUser);
				sendDataToClient("LOGIN;success;" + loggedInUser.getFullName());
				ServerRun.updateOnlineList();

			} else {
				sendDataToClient("LOGIN;failed;Sai tài khoản hoặc mật khẩu");
			}
		}
	}

	private void handleRegister(String request) {
		String[] splitted = request.split(";");
		String username = splitted[1];
		String password = splitted[2];
		String fullname = splitted[3];
		
		
		UserModel newUser = new UserModel(username, password, fullname);
		userDao = new UserDAO();
		boolean isExist =  userDao.isUserNameExists(username);
		if(isExist) {
			sendDataToClient("REGISTER;failed;Tài khoản đã tồn tại");
			return;

		}
	    boolean isRegistered = userDao.register(newUser);
	    if (isRegistered) {
	        sendDataToClient("REGISTER;success;Đăng ký thành công");
	    } else {
	        sendDataToClient("REGISTER;failed;Đăng ký thất bại");
	    }
		
	}
	public void sendDataToClient(String format) {
		try {
			dOut.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

}
