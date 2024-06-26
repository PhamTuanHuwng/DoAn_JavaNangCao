package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import run.ClientRun;
import run.ServerRun;
import view.LoginView;

public class SocketController {
	private Socket socket;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private Thread listener = null;

	public boolean connect() {
		try {
			socket = new Socket("localhost", 9999);
			System.out.println("Connected to localhost : 9999, localport:" + socket.getLocalPort());
			dIn = new DataInputStream(socket.getInputStream());
			dOut = new DataOutputStream(socket.getOutputStream());

			listener = new Thread(new Runnable() {
				@Override
				public void run() {
					listen();
				}
			});
			listener.start();
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public void login(String username, String password) {
		if (!isConnected()) {
			System.err.println("Not connected to server. Cannot perform login.");
			return;
		}

		String data = "LOGIN;" + username + ";" + password;
		sendDataToServer(data);
	}

	public void register(String username, String password, String fullname) {
		String data = "REGISTER;" + username + ";" + password + ";" + fullname;
		sendDataToServer(data);
	}

	public void sendDataToServer(String data) {
		if (!isConnected()) {
			System.err.println("Not connected to server. Cannot perform login.");
			return;
		}

		try {

			dOut.writeUTF(data);
			dOut.flush();

		} catch (IOException e) {
			System.err.println("Failed to send data to server: " + e.getMessage());

		}
	}

	public void sendMess(String username, String mess) {
		String data = "CHAT_ALL;" + username + ";" + mess;
		sendDataToServer(data);
	}

	public void sendPrivateMessage(String sender, String receiver, String message) {
		String data = "PRIVATE_MESSAGE;" + sender + ";" + receiver + ";" + message;
		sendDataToServer(data);
	}

	private void listen() {
		while (true) {
			try {
				String received = dIn.readUTF();
				System.out.println("Receive from server: " + received);
				String type = received.split(";")[0];
				switch (type) {
				case "LOGIN":
					onReceiveLogin(received);
					break;
				case "ONLINE_LIST":
					updateDataList(received);

					break;
				case "CHAT_ALL":
					onReceiveMess(received);
					break;

				case "REGISTER":
					onReceiveRegister(received);
					break;
				case "PRIVATE_MESSAGE":
					onReceiveMess(received);
					break;
				default:

					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private void updateDataList(String received) {
		String[] parts = received.split(";");
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 1; i < parts.length; i++)
			names.add(parts[i]);

		ClientRun.updateOnlineList(names);
	}

	private void onReceiveLogin(String received) {
		// nhận message phản hồi từ server, sau khi server xử lý yêu cầu đăng nhập
		// nếu lgoin thành công: LOGIN;success;username
		// login thất bại: LOGIN;failed;msg
		String[] splitted = received.split(";");
		String status = splitted[1];
		String fullName = splitted[2];
		if (status.equals("failed")) {
			String failedMsg = splitted[2];
			JOptionPane.showMessageDialog(ClientRun.getLoginView(), failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

		} else if (status.equals("success")) {

			ClientRun.closeView(ClientRun.ViewName.LOGIN_VIEW);
			ClientRun.getHomeView().setUserName(fullName);
			ClientRun.openView(ClientRun.ViewName.HOME_VIEW);

		}
	}
	
	private void onReceiveMess(String received) {
		String[] parts = received.split(";");
		String userName = parts[1];
		String mess = parts[2];
		ClientRun.textPane(userName, mess);
	}

	private void onReceiveRegister(String received) {
		// REGISTER;success;Đăng ký thành công
		// REGISTER;failed;Đăng ký thất bại
		String[] parts = received.split(";");
		String status = parts[1];
		String mess = parts[2];
		if (status.equals("failed")) {
			JOptionPane.showMessageDialog(ClientRun.getRegisterView(), mess, "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else if (status.equals("success")) {
			JOptionPane.showMessageDialog(ClientRun.getRegisterView(), mess, "Thành công",
					JOptionPane.INFORMATION_MESSAGE);
			ClientRun.closeView(ClientRun.ViewName.REGISTER_VIEW);
			ClientRun.openView(ClientRun.ViewName.LOGIN_VIEW);
		}

	}

	private boolean isConnected() {
		return socket != null && socket.isConnected() && !socket.isClosed();
	}
}
