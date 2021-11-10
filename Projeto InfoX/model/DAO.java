package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Conexão com o banco de dados
 * 
 * @author Cassio Rodrigues Braga
 * @version 1.0
 */
public class DAO {

	// Parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.115:3306/dbinfox";
	private String user = "Cassimbas";
	private String password = "123@Senha";

	/**
	 * Métido responsável pela conexão com o banco
	 * 
	 * @return con
	 */
	public Connection conectar() {
		// A linha abaixo cria o objeto de nome con
		Connection con = null;
		// Tratamento de exceções
		try {
			// As duas linhas abaixo, estabelecem a conexão
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
