package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contém os métodos que irão executar funções associadas com o banco de dados e
 * os objetos da classe Dente
 */
public class DenteDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Chamada que conecta com o banco de dados, retorna True caso obtenha
     * sucesso
     */
    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etapa5_uc15_pi", "root", conexao.linhaConexao());
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Retorna dados do Dente tendo como referência seu ID.
     */
    public Dente retornaDente(int id) {
        try {
            st = conn.prepareStatement("SELECT * FROM dentes WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            Dente dente = new Dente();
            if (rs.next()) {
                dente.setId(rs.getInt("id"));
                dente.setNome(rs.getString("nome"));
                dente.setNumero(rs.getInt("numero"));
            }
            return dente;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Retorna IDs dos dentes contidos em um Odontograma a partir do ID do mesmo
     * Odontograma
     */
    public List<Integer> retornaIDsDentes(int idOdontograma) {
        try {
            st = conn.prepareStatement("SELECT * FROM odontograma_dente WHERE odontogramas_id = ?");
            st.setInt(1, idOdontograma);
            rs = st.executeQuery();
            
            int idDente;
            List<Integer> listaIdsDentes = new ArrayList<>();
            while (rs.next()) {
               idDente = rs.getInt("dentes_id");
               listaIdsDentes.add(idDente);
            }           
            return listaIdsDentes;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Desconecta do banco de dados
     */
    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {

        }
    }
}
