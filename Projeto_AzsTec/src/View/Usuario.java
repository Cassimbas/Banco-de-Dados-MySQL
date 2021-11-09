package View;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.DAO;
import net.proteanit.sql.DbUtils;
import javax.swing.JCheckBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Usuario extends JDialog {
	private JTable tableUsuario;
	private JTextField txtUsuario;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario dialog = new Usuario();
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
	public Usuario() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// Evento
				chkSenha.setVisible(false);
			}
		});
		setTitle("Usu\u00E1rio");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuario.class.getResource("/icones/pc.png")));
		setBounds(150, 150, 649, 373);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Usuario.class.getResource("/icones/pesquisar.png")));
		lblNewLabel.setBounds(273, 18, 33, 33);
		getContentPane().add(lblNewLabel);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(20, 73, 591, 65);
		getContentPane().add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 591, 65);
		desktopPane.add(scrollPane);

		tableUsuario = new JTable();
		tableUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
				setarSenha();
			}
		});
		scrollPane.setViewportView(tableUsuario);

		JLabel lblNewLabel_1 = new JLabel("Usu\u00E1rio:");
		lblNewLabel_1.setBounds(20, 162, 63, 14);
		getContentPane().add(lblNewLabel_1);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(111, 159, 283, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(421, 162, 63, 14);
		getContentPane().add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setBounds(494, 159, 117, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Senha:");
		lblNewLabel_3.setBounds(20, 212, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(111, 209, 123, 20);
		getContentPane().add(txtSenha);

		JLabel lblNewLabel_4 = new JLabel("Perfil:");
		lblNewLabel_4.setBounds(20, 254, 63, 14);
		getContentPane().add(lblNewLabel_4);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Usuario.class.getResource("/icones/excluir.png")));
		btnExcluir.setBounds(518, 252, 64, 64);
		getContentPane().add(btnExcluir);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "Administrador", "Operador" }));
		cboPerfil.setBounds(111, 250, 124, 22);
		getContentPane().add(cboPerfil);

		btnAdicionar2 = new JButton("");
		btnAdicionar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnAdicionar2.setIcon(new ImageIcon(Usuario.class.getResource("/icones/adicionar.png")));
		btnAdicionar2.setBounds(327, 253, 64, 64);
		getContentPane().add(btnAdicionar2);

		JLabel lblNewLabel_5 = new JLabel("Id:");
		lblNewLabel_5.setBounds(408, 27, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarUsuario();
			}
		});
		txtPesquisar.setBounds(20, 27, 179, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(441, 24, 86, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);
		txtId.setColumns(10);

		btnEditar2 = new JButton("");
		btnEditar2.setEnabled(false);
		btnEditar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Tratamento do checkBox (senha do usuário)
				if (chkSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioPersonalizado();
				}

			}
		});
		btnEditar2.setIcon(new ImageIcon(Usuario.class.getResource("/icones/editar.png")));
		btnEditar2.setBounds(419, 252, 64, 64);
		getContentPane().add(btnEditar2);

		chkSenha = new JCheckBox("Confirmar a alltera\u00E7\u00E3o da senha");
		chkSenha.setBounds(370, 196, 250, 23);
		getContentPane().add(chkSenha);

	}// Fim do construtor >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	DAO dao = new DAO();
	private JTextField txtPesquisar;
	private JTextField txtId;
	private JComboBox cboPerfil;
	private JButton btnAdicionar2;
	private JButton btnEditar2;
	private JButton btnExcluir;
	private JCheckBox chkSenha;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void pesquisarUsuario() {
		// ? é um parâmetro
		String read = "select * from usuarios where usuario like ?";
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
			tableUsuario.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void setarCampos() {
		// A linha abaixo optem o conteudo da linha da tabela
		// int (indice = colunas) [0] [1] [2] ....
		int setar = tableUsuario.getSelectedRow();
		// Setar os campos
		txtId.setText(tableUsuario.getModel().getValueAt(setar, 0).toString());
		txtUsuario.setText(tableUsuario.getModel().getValueAt(setar, 1).toString());
		txtLogin.setText(tableUsuario.getModel().getValueAt(setar, 2).toString());
		txtSenha.setText(tableUsuario.getModel().getValueAt(setar, 3).toString());
		cboPerfil.setSelectedItem(tableUsuario.getModel().getValueAt(setar, 4).toString());
		// Gerenciar os botões
		btnAdicionar2.setEnabled(false);
		btnEditar2.setEnabled(true);
		btnExcluir.setEnabled(true);
		chkSenha.setVisible(true);
	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * Métido especívifico para setar a senha
	 */
	private void setarSenha() {
		String read2 = "select senha from usuarios where id=?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, txtId.getText());
			// A linha abaixo executa a instrução sql e armazena o resultado
			ResultSet rs = pst.executeQuery();
			// a linha abaixo verifica se existe uma senha para o idcli
			if (rs.next()) {
				txtSenha.setText(rs.getString(1));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void editarUsuario() {
		// ? é um parâmetro
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo usuário", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo login", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo senha", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo perfil", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboPerfil.requestFocus();
		} else {
			String update = "update usuarios set usuario=?,login=?,senha=md5(?),perfil=? where id=?";
			try {
				// abrir a conexao com o banco
				Connection con = dao.conectar();
				// preparar a query(instrucao sql) para pesquisar no banco
				PreparedStatement pst = con.prepareStatement(update);
				// substituir o parametro(?) Atencao ao % para completar a query
				// 1 -> é o parâmetro (?)
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtId.getText());
				/*
				 * Executar a query e obter os dados do banco (resultado) ResultSet rs =
				 * pst.executeQuery(); // popular(preencher) a tabela com os dados do banco
				 * tableUsuario.setModel(DbUtils.resultSetToTableModel(rs));
				 */
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do usuário atualizado", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * Método responsável de editar o usuário
	 */
	private void editarUsuarioPersonalizado() {
		// ? é um parâmetro
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo usuário", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo login", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo senha", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Perfil", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboPerfil.requestFocus();
		} else {
			String update = "update usuarios set usuario=?,login=?,perfil=? where id=?";
			try {
				// abrir a conexao com o banco
				Connection con = dao.conectar();
				// preparar a query(instrucao sql) para pesquisar no banco
				PreparedStatement pst = con.prepareStatement(update);
				// substituir o parametro(?) Atencao ao % para completar a query
				// 1 -> é o parâmetro (?)
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtId.getText());
				/*
				 * Executar a query e obter os dados do banco (resultado) ResultSet rs =
				 * pst.executeQuery(); // popular(preencher) a tabela com os dados do banco
				 * tableUsuario.setModel(DbUtils.resultSetToTableModel(rs));
				 */
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do usuário atualizado", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void adicionarUsuario() {
		// System.out.println("teste");
		// Validação de campos obrigatorio
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo nome", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo login", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo senha", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo perfil", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboPerfil.requestFocus();
		} else {
			// Inserir o cliente no banco
			String create = "insert into usuarios(usuario,login,senha,perfil) values(?,?,md5(?),?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// Criando uma variável que irá executar a query e receber o valor 1 em caso
				// positivo (inserção do cliente no banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso", "Confirmado",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * Método excluir o cliente do banco de dados
	 */
	private void excluirUsuario() {
		// Confirmação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// Código principal
			String delete = "delete from usuarios where id=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Usuário excluido com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Exclusão não realizada.\nUsuario não existe.", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void limpar() {
		// limpar campos
		txtId.setText(null);
		txtUsuario.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		cboPerfil.setSelectedItem(null);
		// limpar a tabela
		((DefaultTableModel) tableUsuario.getModel()).setRowCount(0);
		// Gerenciar os botões (default)
		btnAdicionar2.setEnabled(true);
		btnEditar2.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
}
