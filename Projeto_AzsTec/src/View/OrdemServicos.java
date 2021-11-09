package View;

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

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.DAO;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class OrdemServicos extends JDialog {
	private JTextField txtPesquisar;
	private JTable table;

	// Variável de apoio ao uso do checkBox
	private String tipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrdemServicos dialog = new OrdemServicos();
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
	public OrdemServicos() {
		setResizable(false);
		setTitle("AszTec - Ordem de Servi\u00E7os");
		setBounds(100, 100, 784, 421);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.setBounds(415, 17, 203, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(415, 48, 343, 107);
		getContentPane().add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 343, 107);
		desktopPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/pesquisar.png")));
		lblNewLabel.setBounds(628, 11, 33, 33);
		getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "OS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 17, 395, 138);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtOs = new JTextField();
		txtOs.setEditable(false);
		txtOs.setBounds(10, 23, 110, 20);
		panel.add(txtOs);
		txtOs.setColumns(10);

		chkOrcamento = new JCheckBox("Or\u00E7amento");
		chkOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Orçamento";
			}
		});
		buttonGroup.add(chkOrcamento);
		chkOrcamento.setBounds(10, 78, 97, 23);
		panel.add(chkOrcamento);

		chkServico = new JCheckBox("Servi\u00E7o");
		chkServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Serviço";
			}
		});
		buttonGroup.add(chkServico);
		chkServico.setBounds(10, 104, 68, 23);
		panel.add(chkServico);

		JLabel lblNewLabel_1 = new JLabel("Status :");
		lblNewLabel_1.setBounds(181, 82, 46, 14);
		panel.add(lblNewLabel_1);

		cboStatus = new JComboBox();
		cboStatus.setModel(
				new DefaultComboBoxModel(new String[] { "", "Aprovado", "Bancada", "Retirado", "Or\u00E7amento", "" }));
		cboStatus.setBounds(181, 108, 202, 22);
		panel.add(cboStatus);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(181, 23, 202, 20);
		panel.add(txtData);
		txtData.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Data :");
		lblNewLabel_2.setBounds(142, 26, 46, 14);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("ID :");
		lblNewLabel_3.setBounds(671, 20, 33, 14);
		getContentPane().add(lblNewLabel_3);

		txtId = new JTextField();
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		txtId.setBounds(697, 17, 61, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		JButton btnImprimir = new JButton("");
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/print.png")));
		btnImprimir.setBounds(322, 307, 68, 68);
		getContentPane().add(btnImprimir);

		btnPesquisar = new JButton("");
		btnPesquisar.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/read.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarOs();
			}
		});
		btnPesquisar.setBounds(88, 307, 68, 68);
		getContentPane().add(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarOs();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/adicionar.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setBounds(10, 307, 68, 68);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOs();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/editar.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(166, 307, 68, 68);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOs();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/excluir.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(244, 307, 68, 68);
		getContentPane().add(btnExcluir);

		JLabel lblNewLabel_4 = new JLabel("Equipamento :");
		lblNewLabel_4.setBounds(10, 194, 95, 14);
		getContentPane().add(lblNewLabel_4);

		txtEquipamento = new JTextField();
		txtEquipamento.setBounds(115, 191, 290, 20);
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("S\u00E9rie :");
		lblNewLabel_5.setBounds(415, 194, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtSerie = new JTextField();
		txtSerie.setBounds(480, 191, 138, 20);
		getContentPane().add(txtSerie);
		txtSerie.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Defeito :");
		lblNewLabel_6.setBounds(10, 222, 46, 14);
		getContentPane().add(lblNewLabel_6);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(115, 219, 503, 20);
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Obs :");
		lblNewLabel_7.setBounds(10, 253, 46, 14);
		getContentPane().add(lblNewLabel_7);

		txtObs = new JTextField();
		txtObs.setBounds(115, 250, 290, 20);
		getContentPane().add(txtObs);
		txtObs.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("T\u00E9cnico :");
		lblNewLabel_8.setBounds(415, 253, 55, 14);
		getContentPane().add(lblNewLabel_8);

		txtIdTec = new JTextField();
		txtIdTec.setBounds(480, 250, 138, 20);
		getContentPane().add(txtIdTec);
		txtIdTec.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Valor do servi\u00E7o :");
		lblNewLabel_9.setBounds(504, 354, 150, 14);
		getContentPane().add(lblNewLabel_9);

		txtValor = new JTextField();
		txtValor.setText("0");
		txtValor.setBounds(628, 351, 130, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("Forma de Pagto :");
		lblNewLabel_10.setBounds(504, 281, 114, 14);
		getContentPane().add(lblNewLabel_10);

		cboPagto = new JComboBox();
		cboPagto.setModel(new DefaultComboBoxModel(
				new String[] { "", "Cart\u00E3o D\u00E9bito", "Cart\u00E3o Cr\u00E9dito", "Dinheiro", "Pix" }));
		cboPagto.setBounds(628, 280, 130, 22);
		getContentPane().add(cboPagto);

		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setIcon(new ImageIcon(OrdemServicos.class.getResource("/icones/Ordem_Servi\u00E7os.jpg")));
		lblNewLabel_11.setBounds(628, 191, 130, 71);
		getContentPane().add(lblNewLabel_11);

	}// FIM DO CONSTRUTOR >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// Criando um objeto para acessar a classe DAO

	DAO dao = new DAO();
	private JTextField txtOs;
	private JTextField txtData;
	private JTextField txtId;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox chkOrcamento;
	private JTextField txtEquipamento;
	private JTextField txtSerie;
	private JTextField txtDefeito;
	private JTextField txtObs;
	private JTextField txtIdTec;
	private JTextField txtValor;
	private JComboBox cboStatus;
	private JButton btnPesquisar;
	private JCheckBox chkServico;
	private JComboBox cboPagto;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;

	private void pesquisarCliente() {
		// ? é um parâmetro
		String read = "select idcli as ID, nome as Cliente,  fone1 as Fone, cpf as CPF from clientes where nome like ?";
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
	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void setarCampos() {
		// A linha abaixo optem o conteudo da linha da tabela
		// int (indice = colunas) [0] [1] [2] ....
		int setar = table.getSelectedRow();
		// Setar os campos
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
	} // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * Métido responsável pela pesquisa da OS
	 */
	private void pesquisarOs() {
		// tecnica usada para capturar
		String numOs = JOptionPane.showInputDialog("Número da OS");
		String read = "select * from tbos where os=" + numOs;
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			// A linha abaixo, ResultSet, trás a info do banco de dados
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				if (tipo == "Serviço") {
					chkServico.setSelected(true);
					tipo = "Serviço";
				} else {
					chkOrcamento.setSelected(true);
					tipo = "Orçamento";
				}
				txtId.setText(rs.getString(12));
				txtOs.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtEquipamento.setText(rs.getString(5));
				txtSerie.setText(rs.getString(6));
				txtObs.setText(rs.getString(8));
				txtIdTec.setText(rs.getString(9));
				txtValor.setText(rs.getString(10));
				txtDefeito.setText(rs.getString(7));
				cboStatus.setSelectedItem(rs.getString(4).toString());
				cboPagto.setSelectedItem(rs.getString(11).toString());
				txtPesquisar.setEnabled(false);
				btnAdicionar.setEnabled(false);
				btnPesquisar.setEnabled(true);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);

			} else {
				JOptionPane.showMessageDialog(null, "O.S Não Localizada!!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {
			System.out.println(e);

		}

	}// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void adicionarOs() {
		// System.out.println("teste");
		// Validação de campos obrigatorio
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Equipamento", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtEquipamento.requestFocus();
		} else if (txtSerie.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Serie", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtSerie.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Defeito", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtDefeito.requestFocus();
		} else if (txtObs.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo OBs", "Atenção", JOptionPane.WARNING_MESSAGE);
			txtObs.requestFocus();
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Status", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboStatus.requestFocus();
		} else if (tipo == null) {
			JOptionPane.showMessageDialog(null, "Selecione o tipo de OS", "Atenção !", JOptionPane.WARNING_MESSAGE);
			chkOrcamento.requestFocus();
		} else {
			// Inserir o cliente no banco
			String create = "insert into tbos(tipo,statusos,equipos,serieos,defeitoos,obsos,valorserv,formadepagto,idcli,id) values (?,?,?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, tipo);
				pst.setString(2, cboStatus.getSelectedItem().toString());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtSerie.getText());
				pst.setString(5, txtDefeito.getText());
				pst.setString(6, txtObs.getText());
				pst.setString(7, txtValor.getText());
				pst.setString(8, cboPagto.getSelectedItem().toString());
				pst.setString(9, txtId.getText());
				pst.setString(10, txtIdTec.getText());
				// Criando uma variável que irá executar a query e receber o valor 1 em caso
				// positivo (inserção do cliente no banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "OS adicionado com sucesso", "Confirmado",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} /*
				 * catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				 * JOptionPane.showMessageDialog(null, "CPF já cadastrado","Atenção!",
				 * JOptionPane.WARNING_MESSAGE); txtCpf.setText(null); txtCpf.requestFocus(); }
				 */
			catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void editarOs() {

		if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Status", "Atenção", JOptionPane.WARNING_MESSAGE);
			cboStatus.requestFocus();
		} else {
			// Editar os dados do cliente
			String update = "update tbos set tipo=?,statusos=?,equipos=?,serieos=?,defeitoos=?,obsos=?,tecnico=?,valorserv=?,formadepagto=? where os=?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, tipo);
				pst.setString(2, cboStatus.getSelectedItem().toString());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtSerie.getText());
				pst.setString(5, txtDefeito.getText());
				pst.setString(6, txtObs.getText());
				pst.setString(7, txtIdTec.getText());
				pst.setString(8, txtValor.getText());
				pst.setString(9, cboPagto.getSelectedItem().toString());
				pst.setString(10, txtOs.getText());
				// Criando uma variável que irá executar a query e receber o valor 1 em caso
				// positivo (edição do cliente no banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados da OS atualizados", "Confirmado",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * Método excluir o cliente do banco de dados
	 */
	private void excluirOs() {
		// Confirmação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão da OS?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// Código principal 
			String delete = "delete from tbos where os=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtOs.getText());
				int excluir = pst.executeUpdate();
				if (excluir ==1) {
					limpar();
					JOptionPane.showMessageDialog(null, "OS excluido com sucesso","Mensagem", JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Exclusão não realizada", "Atenção!", JOptionPane.WARNING_MESSAGE);				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void limpar() {
		// limpar campos
		txtId.setText(null);
		txtOs.setText(null);
		txtPesquisar.setText(null);
		txtEquipamento.setText(null);
		txtSerie.setText(null);
		txtDefeito.setText(null);
		txtObs.setText(null);
		txtIdTec.setText(null);
		cboStatus.setSelectedItem(null);
		cboPagto.setSelectedItem(null);
		txtValor.setText(null);
		buttonGroup.clearSelection();
		// limpar a tabela
		// ((DefaultTableModel) table.getModel()).setRowCount(0);
		// Gerenciar os botões (default)
		btnAdicionar.setEnabled(true);
		btnPesquisar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
}
