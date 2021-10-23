package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import net.proteanit.sql.DbUtils;

public class Clientes extends JDialog {
	private JTextPane txtCep;
	private JTextPane txtCidade;
	private JTextPane txtBairro;
	private JTextPane txtEndereco;
	private JComboBox cboUf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		setModal(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/icones/pc.png")));
		setTitle("Clientes");
		setBounds(100, 100, 787, 455);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextPane();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.setBounds(10, 24, 277, 20);
		getContentPane().add(txtPesquisar);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.setIcon(new ImageIcon(Clientes.class.getResource("/icones/pesquisar.png")));
		lblNewLabel.setBounds(361, 11, 33, 33);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("* Campos obrigat\u00F3rios");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_1.setBounds(590, 24, 170, 14);
		getContentPane().add(lblNewLabel_1);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 55, 750, 64);
		getContentPane().add(desktopPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 750, 64);
		desktopPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel(" ID :");
		lblNewLabel_2.setBounds(10, 143, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JTextPane txtID = new JTextPane();
		txtID.setBounds(79, 143, 46, 20);
		getContentPane().add(txtID);

		JTextPane txtNome = new JTextPane();
		txtNome.setBounds(215, 143, 370, 20);
		getContentPane().add(txtNome);

		JLabel lblNewLabel_3 = new JLabel("* Nome :");
		lblNewLabel_3.setBounds(147, 143, 58, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("* Fone :");
		lblNewLabel_4.setBounds(595, 143, 46, 14);
		getContentPane().add(lblNewLabel_4);

		JTextPane txtFoneCli = new JTextPane();
		txtFoneCli.setBounds(670, 143, 90, 20);
		getContentPane().add(txtFoneCli);

		JLabel lblNewLabel_5 = new JLabel(" Cep :");
		lblNewLabel_5.setBounds(10, 197, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtCep = new JTextPane();
		txtCep.setBounds(79, 191, 90, 20);
		getContentPane().add(txtCep);

		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCep.setBounds(215, 193, 89, 23);
		getContentPane().add(btnCep);

		JLabel lblNewLabel_6 = new JLabel("*Endere\u00E7o :");
		lblNewLabel_6.setBounds(10, 238, 72, 14);
		getContentPane().add(lblNewLabel_6);

		txtEndereco = new JTextPane();
		txtEndereco.setBounds(106, 238, 224, 20);
		getContentPane().add(txtEndereco);

		JLabel lblNewLabel_7 = new JLabel("*N\u00BA :");
		lblNewLabel_7.setBounds(361, 238, 46, 14);
		getContentPane().add(lblNewLabel_7);

		JTextPane txtNumero = new JTextPane();
		txtNumero.setBounds(432, 238, 60, 20);
		getContentPane().add(txtNumero);

		JLabel lblNewLabel_8 = new JLabel(" Complemento :");
		lblNewLabel_8.setBounds(520, 238, 90, 14);
		getContentPane().add(lblNewLabel_8);

		JTextPane txtComplemento = new JTextPane();
		txtComplemento.setBounds(631, 238, 129, 20);
		getContentPane().add(txtComplemento);

		JLabel lblNewLabel_9 = new JLabel("*Bairro :");
		lblNewLabel_9.setBounds(29, 290, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtBairro = new JTextPane();
		txtBairro.setBounds(106, 284, 185, 20);
		getContentPane().add(txtBairro);

		JLabel lblNewLabel_10 = new JLabel("*Cidade :");
		lblNewLabel_10.setBounds(348, 290, 59, 14);
		getContentPane().add(lblNewLabel_10);

		txtCidade = new JTextPane();
		txtCidade.setBounds(432, 288, 138, 20);
		getContentPane().add(txtCidade);

		JLabel lblNewLabel_11 = new JLabel(" UF :");
		lblNewLabel_11.setBounds(620, 294, 46, 14);
		getContentPane().add(lblNewLabel_11);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(691, 290, 46, 22);
		getContentPane().add(cboUf);

		JLabel lblNewLabel_12 = new JLabel("");
		lblNewLabel_12.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_12.setIcon(new ImageIcon(Clientes.class.getResource("/icones/create.png")));
		lblNewLabel_12.setToolTipText("Adicionar");
		lblNewLabel_12.setBounds(220, 341, 64, 64);
		getContentPane().add(lblNewLabel_12);

		JLabel lblNewLabel_12_1 = new JLabel("");
		lblNewLabel_12_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_12_1.setIcon(new ImageIcon(Clientes.class.getResource("/icones/update.png")));
		lblNewLabel_12_1.setToolTipText("Modificar");
		lblNewLabel_12_1.setBounds(315, 341, 64, 64);
		getContentPane().add(lblNewLabel_12_1);

		JLabel lblNewLabel_12_2 = new JLabel("");
		lblNewLabel_12_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_12_2.setIcon(new ImageIcon(Clientes.class.getResource("/icones/delete.png")));
		lblNewLabel_12_2.setToolTipText("Excluir");
		lblNewLabel_12_2.setBounds(410, 341, 64, 64);
		getContentPane().add(lblNewLabel_12_2);

	}// Fim da construção

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						// lblStatus.setIcon(new
						// javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Criando um objeto para acessar a classe DAO
	DAO dao = new DAO();
	private JTable table;
	private JTextPane txtPesquisar;

	private void pesquisarCliente() {
		// ? é um parâmetro
		String read = "select idcli as ID, nome as Cliente, email as Email from clientes where nome like ?	";
		try {
			// abrir a conexao com o banco
			Connection con = dao.conectar();
			// preparar a query(instrucao sql) para pesquisar no banco
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro(?) Atencao ao % para completar a query
			// 1 -> é o parâmetro (?)
			pst.setString(1, txtPesquisar.getText() + "%");
			// Executar a query e obter os dados do banco (resultado)
			ResultSet rs = pst.executeQuery();
			// popular(preencher) a tabela com os dados do banco
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
