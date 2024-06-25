package view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import dao.UserDAO;
import model.UserModel;
import util.PasswordUtil;

public class RegisterView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField textFieldFullName;
    private JTextField textFieldPassword;
    private JTextField textFieldConfirmPassword;

    public RegisterView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 547);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 204, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Đăng Kí ngay");
        lblNewLabel.setForeground(new Color(51, 153, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblNewLabel.setBounds(121, 20, 148, 36);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Đăng Ký");
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 23));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    LoginView loginView = new LoginView();
                    loginView.setVisible(true);
                    dispose();
            }
        });
        btnNewButton.setBounds(36, 438, 318, 49);
        contentPane.add(btnNewButton);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(36, 106, 318, 36);
        contentPane.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Tên Đăng Nhập");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_1.setBounds(36, 77, 141, 19);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Mật Khẩu");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_2.setBounds(36, 235, 83, 24);
        contentPane.add(lblNewLabel_2);

        textFieldPassword = new JTextField();
        textFieldPassword.setBounds(36, 269, 318, 36);
        contentPane.add(textFieldPassword);
        textFieldPassword.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Nhập lại Mật Khẩu");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_3.setBounds(36, 315, 148, 24);
        contentPane.add(lblNewLabel_3);

        textFieldFullName = new JTextField();
        textFieldFullName.setColumns(10);
        textFieldFullName.setBounds(36, 186, 318, 36);
        contentPane.add(textFieldFullName);

        JLabel lblNewLabel_2_1 = new JLabel("Tài Khoản");
        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_2_1.setBounds(36, 152, 184, 24);
        contentPane.add(lblNewLabel_2_1);

        textFieldConfirmPassword = new JTextField();
        textFieldConfirmPassword.setColumns(10);
        textFieldConfirmPassword.setBounds(36, 349, 318, 36);
        contentPane.add(textFieldConfirmPassword);
    }

}
