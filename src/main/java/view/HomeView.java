package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.SocketController;
import run.ClientRun;

import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String userName;
	private DefaultListModel<String> listModel;
	private JList listOnline;
	private JTextPane textPane;

	public HomeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Online users");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(586, 115, 156, 31);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(586, 156, 166, 203);
		contentPane.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 533, 349);
		contentPane.add(scrollPane_1);

		textPane = new JTextPane();
		textPane.setContentType("text/html");
		scrollPane_1.setViewportView(textPane);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 388, 533, 65);
		contentPane.add(textArea);

		JButton btnNewButton = new JButton("Gửi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea.getText().trim().isEmpty())
					return;
				ClientRun.getSocketController().sendMess(getUserName(), textArea.getText());
				
				textArea.setText("");
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.setBounds(594, 390, 158, 63);
		btnNewButton.setDefaultCapable(true);
		contentPane.add(btnNewButton);

		listModel = new DefaultListModel<>();
		listModel.addElement("Không có ai ở trong server");
		listOnline = new JList<>(listModel);

		scrollPane.setViewportView(listOnline);

		String initialContent = "<html><head><style>"
				+ "body {background-color: #242526; color: white; font-family: Arial, sans-serif; margin: 0; padding: 1px;}"
				+ ".self {text-align: right; color: white;}" + ".other {text-align: left; color: white}"
				+ "</style></head><body></body></html>";
		textPane.setText(initialContent);

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		setTitle(userName);
	}

	public void setOnlineList(ArrayList<String> onlines) {

		if (onlines == null) {
			listModel.clear();
			listModel.addElement("Không có ai ở trong server");
			return;
		}
		listModel.clear();
		for (String user : onlines) {
			if (onlines.size() == 1) {
				listModel.clear();
				listModel.addElement("Không có ai ở trong server");
			} else {
				if (!getUserName().equals(user)) {
					listModel.addElement(user);
				}
			}
		}
	}

	public void updateTextPane(String username, String mess) {
		String alignmentClass = username.equals(getUserName()) ? "self" : "other";
		String formattedMessage = String.format("<div class='%s'>%s: %s</div>", alignmentClass, username, mess);

		String currentContent = textPane.getText();
		int bodyEndIndex = currentContent.lastIndexOf("</body>");
		String updatedContent = currentContent.substring(0, bodyEndIndex) + formattedMessage + "</body></html>";

		textPane.setText(updatedContent);
	}
}
