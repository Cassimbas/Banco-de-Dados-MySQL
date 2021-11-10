package view;

import java.awt.EventQueue;
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
import javax.swing.JCheckBox;
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

public class Usuario extends JDialog {
	private JTextField txtPesquisar;
	private JTextField txtId;
	private JTable table;
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
		setResizable(false);
		setBounds(100, 100, 652, 390);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarUsuario();
			}
		});
		txtPesquisar.setBounds(10, 25, 246, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Usuario.class.getResource("/img/pesquisar.png")));
		lblNewLabel.setBounds(303, 12, 33, 33);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("ID :");
		lblNewLabel_1.setBounds(460, 28, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtId = new JTextField();
		txtId.setBounds(516, 25, 86, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 56, 592, 75);
		getContentPane().add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 592, 75);
		desktopPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampo();
				setarSenha();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel("Usu\u00E1rio :");
		lblNewLabel_2.setBounds(10, 150, 46, 14);
		getContentPane().add(lblNewLabel_2);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(80, 147, 238, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Login :");
		lblNewLabel_3.setBounds(353, 150, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtLogin = new JTextField();
		txtLogin.setBounds(420, 147, 182, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Senha :");
		lblNewLabel_4.setBounds(10, 198, 46, 14);
		getContentPane().add(lblNewLabel_4);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(80, 195, 170, 20);
		getContentPane().add(txtSenha);

		JLabel lblNewLabel_5 = new JLabel("Perfil :");
		lblNewLabel_5.setBounds(10, 248, 46, 14);
		getContentPane().add(lblNewLabel_5);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "Administrador", "Operador" }));
		cboPerfil.setBounds(80, 246, 129, 18);
		getContentPane().add(cboPerfil);

		chkSenha = new JCheckBox("Confirmar a altera\u00E7\u00E3o da senha");
		chkSenha.setBounds(80, 290, 206, 23);
		getContentPane().add(chkSenha);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Usuario.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(341, 275, 68, 68);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioPersonalizado();
				}

			}
		});
		btnEditar.setIcon(new ImageIcon(Usuario.class.getResource("/img/update.png")));
		btnEditar.setBounds(438, 275, 68, 68);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Usuario.class.getResource("/img/delete.png")));
		btnExcluir.setBounds(534, 275, 68, 68);
		getContentPane().add(btnExcluir);

	} // Fim do construtor

	DAO dao = new DAO();
	private JButton btnEditar;
	private JComboBox cboPerfil;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JCheckBox chkSenha;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void setarCampo() {
		// A linha abaixo optem o conteudo da linha da tabela
		// int (indice = colunas) [0] [1] [2] ....
		int setar = table.getSelectedRow();
		// Setar os campos
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
		txtUsuario.setText(table.getModel().getValueAt(setar, 1).toString());
		txtLogin.setText(table.getModel().getValueAt(setar, 2).toString());
		txtSenha.setText(table.getModel().getValueAt(setar, 3).toString());
		cboPerfil.setSelectedItem(table.getModel().getValueAt(setar, 4).toString());
		// Gerenciar os botões
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		chkSenha.setVisible(true);

	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * Métido especívifico para setar a senha
	 */
	private void setarSenha() {
		String read = "select senha from usuarios where id=?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
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

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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
	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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
	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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
	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void limpar() {
		// limpar campos
		txtId.setText(null);
		txtUsuario.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		cboPerfil.setSelectedItem(null);
		// limpar a tabela
		((DefaultTableModel) table.getModel()).setRowCount(0);
		// Gerenciar os botões (default)
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
}