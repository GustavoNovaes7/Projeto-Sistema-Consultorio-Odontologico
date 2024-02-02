package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Faz a conexão com o banco de dados, retornando a conexão caso objetenha
 * sucesso e nulo caso contrário
 */
public class Conexao {

    public Connection getConnection() {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/etapa5_uc15_pi", "root", linhaConexao());
            return conexao;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }

    public String linhaConexao() {
        String linha = "Gaasmysql2092%";
        return linha;
    }
}
