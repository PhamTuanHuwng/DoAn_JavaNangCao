	package view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.UserDAO;
import model.UserModel;
import run.ClientRun;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField textPass;
    private JTextField textName;
    private JButton btnNewButton;
    private JButton btnngK;

    public LoginView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 204, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Đăng nhập");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(220, 10, 134, 30);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_1.setBounds(30, 46, 151, 30);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2.setBounds(30, 132, 92, 30);
        contentPane.add(lblNewLabel_2);
        
        textPass = new JPasswordField();
        textPass.setBounds(30, 162, 489, 37);
        contentPane.add(textPass);
        
        textName = new JTextField();
        textName.setBounds(30, 75, 489, 37);
        contentPane.add(textName);
        
        btnNewButton = new JButton("Đăng Nhập");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String userName = textName.getText();
                String passWord = String.valueOf(textPass.getPassword());
                if (userName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Tên đăng nhập rỗng");
                    textName.requestFocus();
                } else if (passWord.equals("")) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu rỗng");
                    textPass.requestFocus();
                } else {
                	ClientRun.getSocketController().connect();
					ClientRun.getSocketController().login(userName, passWord);

                }
            }
        });
        btnNewButton.setFont(new Font("Arial", Font.BOLD, 20));
        btnNewButton.setBounds(88, 223, 151, 30);
        contentPane.add(btnNewButton);
        getRootPane().setDefaultButton(btnNewButton);
        
        btnngK = new JButton("Đăng Ký");
        btnngK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterView registerView = new RegisterView();
                registerView.setVisible(true);
                dispose();
            }
        });
        btnngK.setFont(new Font("Arial", Font.BOLD, 20));
        btnngK.setBounds(317, 223, 151, 30);
        contentPane.add(btnngK);
    }
}
