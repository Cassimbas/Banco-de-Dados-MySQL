package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Cursor;

public class Principal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setResizable(false);
		setTitle("InfoX - Principal");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/pc.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 405);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnUsuarios = new JButton("");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				usuario.setVisible(true);
			}
		});
		btnUsuarios.setBounds(38, 32, 128, 128);
		btnUsuarios.setToolTipText("Usu\u00E1rios");
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users.png")));
		contentPane.add(btnUsuarios);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes cliente = new Clientes();
				cliente.setVisible(true);
			}
		});
		btnClientes.setBounds(198, 32, 128, 128);
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		contentPane.add(btnClientes);
		
		JButton btnOs = new JButton("");
		btnOs.setBounds(38, 202, 128, 128);
		btnOs.setToolTipText("OS");
		btnOs.setIcon(new ImageIcon(Principal.class.getResource("/img/os.png")));
		contentPane.add(btnOs);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(382, 110, 256, 256);
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/x.png")));
		contentPane.add(lblNewLabel);
		
		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBackground(SystemColor.control);
		btnSobre.setBorder(null);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setBounds(564, 11, 64, 64);
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		contentPane.add(btnSobre);
		
		JButton btnRelatorios = new JButton("");
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/relatorios.png")));
		btnRelatorios.setToolTipText("Relat\u00F3rios");
		btnRelatorios.setBounds(198, 203, 128, 128);
		contentPane.add(btnRelatorios);
	}
}
