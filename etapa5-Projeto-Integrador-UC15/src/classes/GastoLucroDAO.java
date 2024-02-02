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
 * os objetos da classe Gasto Lucro.
 */
public class GastoLucroDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public GastoLucroDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConnection();
    }

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
     * Salva um objeto GastoLucro no banco de dados, retorna 1 caso obtenha
     * sucesso
     */
    public int salvar(GastoLucro gastoLucro) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO gastos_lucros (nome, tipo, valor, quantidade) VALUES(?,?,?,?)");
            st.setString(1, gastoLucro.getNome());
            st.setString(2, String.valueOf(gastoLucro.getTipo()));
            st.setDouble(3, gastoLucro.getValor());
            st.setInt(4, gastoLucro.getQuantidade());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os gastos/lucros armazenados no Banco de Dados
     */
    public List<GastoLucro> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM gastos_lucros");
            rs = st.executeQuery();

            List<GastoLucro> listaGastosLucros = new ArrayList<>();
            while (rs.next()) {
                GastoLucro gastoLucro = new GastoLucro();
                gastoLucro.setId(rs.getInt("id"));
                gastoLucro.setNome(rs.getString("nome"));
                gastoLucro.setTipo(rs.getString("tipo").charAt(0));
                gastoLucro.setValor(rs.getDouble("valor"));
                gastoLucro.setQuantidade(rs.getInt("quantidade"));

                listaGastosLucros.add(gastoLucro);
            }
            return listaGastosLucros;
        } catch (Exception exc) {
            return null;
        }
    }
    
    /**
     * Lista todos os gastos e lucros de acordo com a pesquisa de nome
     */
    public List<GastoLucro> pesquisaNome(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM gastos_lucros WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");
            rs = st.executeQuery();

            List<GastoLucro> listaGastosLucros = new ArrayList<>();
            while (rs.next()) {
                GastoLucro gastoLucro = new GastoLucro();
                gastoLucro.setId(rs.getInt("id"));
                gastoLucro.setNome(rs.getString("nome"));
                gastoLucro.setTipo(rs.getString("tipo").charAt(0));
                gastoLucro.setValor(rs.getDouble("valor"));
                gastoLucro.setQuantidade(rs.getInt("quantidade"));

                listaGastosLucros.add(gastoLucro);
            }
            return listaGastosLucros;
        } catch (Exception exc) {
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
