package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Clientes extends JDialog {
	private JTextField txtPesquisar;
	private JTextField txtIdCli;
	private JTextField txtNomeCli;
	private JTextField txtFoneCli;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtBairro;
	private JTextField txtCidade;
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
		setTitle("Clientes");
		setResizable(false);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/pc.png")));
		setBounds(150, 150, 798, 409);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.setBounds(10, 31, 329, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.setBorder(null);
		lblNewLabel.setIcon(new ImageIcon(Clientes.class.getResource("/img/pesquisar.png")));
		lblNewLabel.setBounds(371, 18, 33, 33);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(" * Campo obrigat\u00F3rio");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel_1.setBounds(642, 34, 130, 14);
		getContentPane().add(lblNewLabel_1);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(54, 86, 1, 1);
		getContentPane().add(desktopPane);

		JLabel lblNewLabel_2 = new JLabel(" ID :");
		lblNewLabel_2.setBounds(10, 185, 33, 14);
		getContentPane().add(lblNewLabel_2);

		txtIdCli = new JTextField();
		txtIdCli.setEditable(false);
		txtIdCli.setBounds(49, 182, 53, 20);
		getContentPane().add(txtIdCli);
		txtIdCli.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("*Nome :");
		lblNewLabel_3.setBounds(151, 185, 53, 14);
		getContentPane().add(lblNewLabel_3);

		txtNomeCli = new JTextField();
		txtNomeCli.setBounds(214, 182, 368, 20);
		getContentPane().add(txtNomeCli);
		txtNomeCli.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("*Fone :");
		lblNewLabel_4.setBounds(592, 185, 40, 14);
		getContentPane().add(lblNewLabel_4);

		txtFoneCli = new JTextField();
		txtFoneCli.setBounds(642, 182, 130, 20);
		getContentPane().add(txtFoneCli);
		txtFoneCli.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel(" CEP :");
		lblNewLabel_5.setBounds(10, 224, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtCep = new JTextField();
		txtCep.setBounds(49, 221, 86, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCep.setBounds(151, 220, 89, 23);
		getContentPane().add(btnCep);

		JLabel lblNewLabel_6 = new JLabel("*Endere\u00E7o :");
		lblNewLabel_6.setBounds(10, 263, 71, 14);
		getContentPane().add(lblNewLabel_6);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(92, 260, 334, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("*N\u00BA :");
		lblNewLabel_7.setBounds(435, 263, 33, 14);
		getContentPane().add(lblNewLabel_7);

		txtNumero = new JTextField();
		txtNumero.setBounds(478, 260, 53, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel(" Complemento :");
		lblNewLabel_8.setBounds(554, 263, 96, 14);
		getContentPane().add(lblNewLabel_8);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(660, 260, 112, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("*Bairro :");
		lblNewLabel_9.setBounds(10, 318, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtBairro = new JTextField();
		txtBairro.setBounds(92, 315, 107, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("*Cidade :");
		lblNewLabel_10.setBounds(232, 315, 58, 14);
		getContentPane().add(lblNewLabel_10);

		txtCidade = new JTextField();
		txtCidade.setBounds(313, 312, 86, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("*UF :");
		lblNewLabel_11.setBounds(409, 317, 33, 14);
		getContentPane().add(lblNewLabel_11);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(453, 313, 53, 22);
		getContentPane().add(cboUf);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(551, 291, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnEditar.setBounds(625, 291, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete.png")));
		btnExcluir.setBounds(699, 291, 64, 64);
		getContentPane().add(btnExcluir);

		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBounds(10, 73, 762, 88);
		getContentPane().add(desktopPane_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 762, 88);
		desktopPane_1.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		RestrictedTextField nome = new RestrictedTextField(this.txtNomeCli);
		nome.setLimit(100);
		RestrictedTextField fone = new RestrictedTextField(this.txtFoneCli);
		fone.setLimit(15);
		RestrictedTextField endereco = new RestrictedTextField(this.txtEndereco);
		endereco.setLimit(50);
		RestrictedTextField numero = new RestrictedTextField(this.txtNumero);
		numero.setLimit(12);
		RestrictedTextField complemento = new RestrictedTextField(this.txtComplemento);
		complemento.setLimit(30);
		RestrictedTextField bairro = new RestrictedTextField(this.txtBairro);
		bairro.setLimit(100);
		RestrictedTextField cidade = new RestrictedTextField(this.txtCidade);
		cidade.setLimit(100);

	}// fim do construtor >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	

	private void pesquisarCliente() {
		// ? é um parâmetro
		String read = "select idcli as ID, nomecli as Cliente from clientes where nomecli like ?";
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
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void adicionarCliente() {
		if (txtNomeCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o campo Nome", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNomeCli.requestFocus();
		} else if (txtFoneCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o campo Fone", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtFoneCli.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o campo Endereço", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o campo Número", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o campo Bairro", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencher o campo Cidade", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencher o campo UF", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboUf.requestFocus();
		} else {
			String create = "insert into clientes(nomecli,fonecli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli) values (?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNomeCli.getText());
				pst.setString(2, txtFoneCli.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, cboUf.getSelectedItem().toString());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cadastro Realizado com Sucesso", "Messagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void setarCampos() {
		// A linha abaixo optem o conteudo da linha da tabela
		// int (indice = colunas) [0] [1] [2] ....
		int setar = table.getSelectedRow();
		// Setar os campos
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		txtIdCli.setText(table.getModel().getValueAt(setar, 0).toString());
		txtNomeCli.setText(table.getModel().getValueAt(setar, 1).toString());
		txtFoneCli.setText(table.getModel().getValueAt(setar, 2).toString());
		txtCep.setText(table.getModel().getValueAt(setar, 3).toString());
		txtEndereco.setText(table.getModel().getValueAt(setar, 4).toString());
		txtNumero.setText(table.getModel().getValueAt(setar, 5).toString());
		txtComplemento.setText(table.getModel().getValueAt(setar, 6).toString());
		txtBairro.setText(table.getModel().getValueAt(setar, 7).toString());
		txtCidade.setText(table.getModel().getValueAt(setar, 8).toString());
		cboUf.setSelectedItem(table.getModel().getValueAt(setar, 9).toString());
	}
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void editarCliente() {
		if (txtNomeCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo nome", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNomeCli.requestFocus();
		} else if (txtFoneCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Fone", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtFoneCli.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Endereco", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Número", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNumero.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Cidade", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Endereço", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboUf.requestFocus();
		} else {
			// Editar os dados do cliente
			String update = "update clientes set nomecli=?,fonecli=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,Uf=?,Cep=? where idcli=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtNomeCli.getText());
				pst.setString(2, txtFoneCli.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, cboUf.getSelectedItem().toString());
				pst.setString(9, txtCep.getText());
				pst.setString(10, txtIdCli.getText());
				// Criando uma variável que irá executar a query e receber o valor 1 em caso
				// positivo (edição do cliente no banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do cliente atualizados", "Confirmado",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void limpar() {
		txtIdCli.setText(null);
		txtNomeCli.setText(null);
		txtFoneCli.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		((DefaultTableModel) table.getModel()).setRowCount(0);
	}

}
