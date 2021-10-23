package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class Sobre extends JDialog {

	private final JPanel contentSobre = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Sobre dialog = new Sobre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setModal(true);
		setResizable(false);
		setTitle("Sobre...");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentSobre.setBackground(SystemColor.textInactiveText);
		contentSobre.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentSobre, BorderLayout.CENTER);
		contentSobre.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Sistema de Gest\u00E3o de OS Ver 1.0");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setBounds(10, 23, 254, 23);
			contentSobre.add(lblNewLabel);
		}
		
		JLabel lblNewLabel_1 = new JLabel("Autores: C\u00E1ssio Rodrigues Braga & Djeniffer Almeida Vidal");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel_1.setBounds(10, 70, 307, 14);
		contentSobre.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Projeto : Assist\u00EAncia de Consoles");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(10, 117, 172, 14);
		contentSobre.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/icones/mit.png")));
		lblNewLabel_3.setBounds(347, 175, 64, 64);
		contentSobre.add(lblNewLabel_3);
	}

}
