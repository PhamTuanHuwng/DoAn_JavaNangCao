package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String userName;
	private DefaultListModel<String> listModel;
	private JList listOnline;

	public HomeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Online users");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(420, 116, 156, 31);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(420, 157, 166, 203);
		contentPane.add(scrollPane);

		 listOnline = new JList();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 386, 349);
		contentPane.add(scrollPane_1);

		JTextPane textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 388, 384, 65);
		contentPane.add(textArea);

		JButton btnNewButton = new JButton("Gửi");
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.setBounds(418, 390, 158, 63);
		contentPane.add(btnNewButton);
		
		listModel = new DefaultListModel<>();
        listModel.addElement("Không có ai ở trong server");
        listOnline = new JList<>(listModel);
        
		scrollPane.setViewportView(listOnline);


	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		setTitle(userName);
	}

	public void setOnlineList(ArrayList<String> onlines) {
		
		if (onlines == null ) {
			listModel.clear();
			listModel.addElement("Không có ai ở trong server");
			return;
		}
		listModel.clear();
		for (String user : onlines) {
			if(onlines.size()==1) {
				listModel.clear();
				listModel.addElement("Không có ai ở trong server");
			}
			else {
				if(!getUserName().equals(user)) {
					listModel.addElement(user);
				}
			}
		}
	}
}
