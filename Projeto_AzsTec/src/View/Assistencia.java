package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Cursor;

public class Assistencia extends JFrame {

	private JPanel contentPane;
	private JLabel lblData;
	//Moduficar de private para public para ser visível para outras classes
	public JButton btnUsuarios;
	public JButton btnRelatorios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Assistencia frame = new Assistencia();
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
	public Assistencia() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();
			}
		});
		setTitle("AzsTec - Assist\u00EAncia de Consoles e Perif\u00E9ricos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 404);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.windowBorder);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setEnabled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				usuario.setVisible(true);
			}
		});
		btnUsuarios.setBorder(new CompoundBorder());
		btnUsuarios.setIcon(new ImageIcon(Assistencia.class.getResource("/icones/Usu\u00E1rio.jpg")));
		btnUsuarios.setToolTipText("Usu\u00E1rios");
		btnUsuarios.setBounds(20, 24, 130, 130);
		contentPane.add(btnUsuarios);
		
		btnRelatorios = new JButton("");
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setEnabled(false);
		btnRelatorios.setBorder(new CompoundBorder());
		btnRelatorios.setIcon(new ImageIcon(Assistencia.class.getResource("/icones/pexels-anna-nekrashevich-6801648 (1).jpg")));
		btnRelatorios.setToolTipText("Relat\u00F3rios");
		btnRelatorios.setBounds(206, 24, 130, 130);
		contentPane.add(btnRelatorios);
		
		JButton btnClientes = new JButton("");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes2 cliente = new Clientes2();
				cliente.setVisible(true);
			}
		});
		btnClientes.setBorder(new CompoundBorder());
		btnClientes.setIcon(new ImageIcon(Assistencia.class.getResource("/icones/Clientes.jpg")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(20, 187, 130, 130);
		contentPane.add(btnClientes);
		
		JButton btnOrdem_Servicos = new JButton("");
		btnOrdem_Servicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOrdem_Servicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrdemServicos ordemservicos = new OrdemServicos();
				ordemservicos.setVisible(true);
			}
		});
		btnOrdem_Servicos.setBorder(new CompoundBorder());
		btnOrdem_Servicos.setIcon(new ImageIcon(Assistencia.class.getResource("/icones/Ordem_Servi\u00E7os.jpg")));
		btnOrdem_Servicos.setToolTipText("Ordem de Servi\u00E7os");
		btnOrdem_Servicos.setBounds(206, 187, 130, 130);
		contentPane.add(btnOrdem_Servicos);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 204, 153));
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 328, 652, 37);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblData = new JLabel("");
		lblData.setBounds(21, 11, 445, 14);
		panel.add(lblData);
		
		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clicar no botao
				Sobre sobre = new Sobre(); //criar objeto
				sobre.setVisible(true); //exibit o JDialog Sobre
			}
		});
		btnSobre.setBackground(SystemColor.windowBorder);
		btnSobre.setBorder(new CompoundBorder());
		btnSobre.setBorderPainted(false);
		btnSobre.setIcon(new ImageIcon(Assistencia.class.getResource("/icones/Capturar.PNG")));
		btnSobre.setToolTipText("Sobre...");
		btnSobre.setBounds(495, 24, 130, 130);
		contentPane.add(btnSobre);
	}
	
	/**
	 * Metodo responsavel por setar a data e hora do rodape
	 */
	private void setarData() {
		// as linhas abaixo sao usadas para obter e formatar a hora do sistema
		Date dataLabel = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// a linha abaixo substitui a label do rodape pela data
		lblData.setText(formatador.format(dataLabel));
	}
}
