package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Clientes2 extends JDialog {
	private JTextField txtPesquisar;
	private JTable table_1;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtFone;
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
		try {
			Clientes2 dialog = new Clientes2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Clientes2() {
		getContentPane().setBackground(SystemColor.activeCaptionBorder);
		setBounds(100, 100, 799, 450);
		getContentPane().setLayout(null);
		{
			txtPesquisar = new JTextField();
			txtPesquisar.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					pesquisarCliente();
				}
			});
			
			txtPesquisar.setBounds(10, 26, 278, 20);
			getContentPane().add(txtPesquisar);
			txtPesquisar.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setIcon(new ImageIcon(Clientes2.class.getResource("/icones/pesquisar.png")));
			lblNewLabel.setBounds(344, 20, 33, 33);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("* Campos Obrigat\u00F3rios");
			lblNewLabel_1.setForeground(Color.RED);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			lblNewLabel_1.setBounds(589, 29, 151, 14);
			getContentPane().add(lblNewLabel_1);
		}

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 64, 738, 82);
		getContentPane().add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 738, 82);
		desktopPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(10, 170, 46, 14);
		getContentPane().add(lblNewLabel_2);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(75, 167, 86, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("*Nome");
		lblNewLabel_3.setBounds(210, 170, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtNome = new JTextField();
		txtNome.setBounds(286, 167, 237, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("*Fone");
		lblNewLabel_4.setBounds(560, 170, 46, 14);
		getContentPane().add(lblNewLabel_4);

		txtFone = new JTextField();
		txtFone.setBounds(641, 167, 86, 20);
		getContentPane().add(txtFone);
		txtFone.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Cep");
		lblNewLabel_5.setBounds(10, 213, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtCep = new JTextField();
		txtCep.setBounds(75, 210, 86, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCep.setBounds(198, 209, 89, 23);
		getContentPane().add(btnCep);

		JLabel lblNewLabel_6 = new JLabel("*Endere\u00E7o");
		lblNewLabel_6.setBounds(10, 257, 69, 14);
		getContentPane().add(lblNewLabel_6);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(89, 254, 257, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("*N\u00BA");
		lblNewLabel_7.setBounds(373, 257, 46, 14);
		getContentPane().add(lblNewLabel_7);

		txtNumero = new JTextField();
		txtNumero.setBounds(405, 254, 86, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Complemento");
		lblNewLabel_8.setBounds(529, 257, 77, 14);
		getContentPane().add(lblNewLabel_8);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(641, 254, 86, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("*Bairro");
		lblNewLabel_9.setBounds(10, 306, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtBairro = new JTextField();
		txtBairro.setBounds(89, 303, 164, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("*Cidade");
		lblNewLabel_10.setBounds(300, 306, 46, 14);
		getContentPane().add(lblNewLabel_10);

		txtCidade = new JTextField();
		txtCidade.setBounds(368, 303, 123, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("UF :");
		lblNewLabel_11.setBounds(560, 306, 46, 14);
		getContentPane().add(lblNewLabel_11);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(645, 302, 46, 22);
		getContentPane().add(cboUf);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Clientes2.class.getResource("/icones/adicionar.png")));
		btnAdicionar.setBounds(206, 334, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();
			}
		});
		btnEditar.setIcon(new ImageIcon(Clientes2.class.getResource("/icones/editar.png")));
		btnEditar.setBounds(337, 334, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Clientes2.class.getResource("/icones/excluir.png")));
		btnExcluir.setBounds(473, 334, 64, 64);
		getContentPane().add(btnExcluir);
	}// fim do construtor

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
		String read = "select idcli as ID, nome as Cliente from clientes where nome like ?";
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

	private void adicionarCliente() {
		// System.out.println("teste");
		// Validação de campos obrigatorio
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo nome", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Fone", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtFone.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Endereço", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Número", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Bairro", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Cidade", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo UF", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboUf.requestFocus();
		} else {
			// Inserir o cliente no banco
			String create = "insert into clientes(nome,fone1,endereco,numero,bairro,cidade,uf) values (?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtBairro.getText());
				pst.setString(6, txtCidade.getText());
				pst.setString(7, cboUf.getSelectedItem().toString());
				// Criando uma variável que irá executar a query e receber o valor 1 em caso
				// positivo (inserção do cliente no banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso", "Confirmado",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void setarCampos() {
		// A linha abaixo optem o conteudo da linha da tabela
		// int (indice = colunas) [0] [1] [2] ....
		int setar = table.getSelectedRow();
		// Setar os campos
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
		txtNome.setText(table.getModel().getValueAt(setar, 1).toString());
		txtFone.setText(table.getModel().getValueAt(setar, 2).toString());
		txtEndereco.setText(table.getModel().getValueAt(setar, 3).toString());
		txtNumero.setText(table.getModel().getValueAt(setar, 4).toString());
		txtBairro.setText(table.getModel().getValueAt(setar, 5).toString());
		txtCidade.setText(table.getModel().getValueAt(setar, 6).toString());
		cboUf.setSelectedItem(table.getModel().getValueAt(setar, 7).toString());
		// Gerenciar os botões
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}

	private void editarCliente() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo nome", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Fone", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtFone.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Endereço", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Número", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Bairro", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Cidade", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Endereço", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboUf.requestFocus();
		} else {
			// Editar os dados do cliente
			String update = "update clientes set nome=?,email=?,senha=md5(?) where idcli=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtBairro.getText());
				pst.setString(6, txtCidade.getText());
				pst.setString(7, cboUf.getSelectedItem().toString());
				pst.setString(0, txtId.getText());
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

	/**
	 * Método excluir o cliente do banco de dados
	 */
	private void excluirCliente() {
		// Confirmação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// Código principal 
			String delete = "delete from clientes where idcli=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				int excluir = pst.executeUpdate();
				if (excluir ==1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso","Mensagem", JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Exclusão não realizada.\nCliente possui pedido em aberto.", "Atenção!", JOptionPane.WARNING_MESSAGE);				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void limpar() {
		// limpar campos
		txtId.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		// limpar a tabela
		((DefaultTableModel) table.getModel()).setRowCount(0);
		// Gerenciar os botões (default)
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
}
