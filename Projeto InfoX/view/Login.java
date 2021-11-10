package view;//infox

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("Infox - Login");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/pc.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Usu\u00E1rio :");
		lblNewLabel.setBounds(78, 51, 61, 14);
		contentPane.add(lblNewLabel);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(149, 48, 175, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Senha :");
		lblNewLabel_1.setBounds(85, 114, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(149, 111, 175, 20);
		contentPane.add(txtSenha);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnEntrar.setToolTipText("Entrar");
		btnEntrar.setBounds(10, 180, 89, 23);
		contentPane.add(btnEntrar);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dberror.png")));
		lblStatus.setBounds(369, 171, 32, 32);
		contentPane.add(lblStatus);
	}// fim do construtor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	DAO dao = new DAO();

	/**
	 * Métido responsavel pela autenticação do usuário/cliente
	 */
	private void logar() {
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preenchar o Login", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtUsuario.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preenchar a Senha", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtSenha.requestFocus();
		} else {
			try {
				String read = "select * from usuarios where login=? and senha=md5(?)";
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtSenha.getText());
				// A linha abaixo executa a Query(instrução sql) armazenando o resultado no
				// objeto rs
				ResultSet rs = pst.executeQuery();
				// Se existir o login e senha correspondente entrar na tela principal
				if (rs.next()) {
					// Capturar o perfil do usuário
					String perfil = rs.getString(5);
					System.out.println(perfil);

					// Tratamento de perfil do usuário
					if (perfil.equals("Administrador")) {
						Assistencia assistencia = new Assistencia();
						assistencia.setVisible(true);
						// Liberar os botões
						assistencia.btnRelatorios.setEnabled(true);
						assistencia.btnUsuarios.setEnabled(true);
						// Após o login, finalizar o JFrame
						this.dispose();
					} else {
						Assistencia assistencia = new Assistencia();
						assistencia.setVisible(true);
						// Após o login, finalizar o JFrame
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login e/ou senha inválida", "Atenção",
							JOptionPane.WARNING_MESSAGE);
					txtSenha.requestFocus();
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
