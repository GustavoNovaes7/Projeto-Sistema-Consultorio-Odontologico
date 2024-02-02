package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 * Contém os métodos que irão executar funções associadas com o banco de dados e
 * os objetos da classe Auxiliar.
 */
public class AuxiliarDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public AuxiliarDAO() {
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
     * Salva um objeto Dentista no banco de dados, retorna 1 caso obtenha
     * sucesso
     */
    public int salvar(Auxiliar auxiliar) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO auxiliares (nome, nascimento, cpf, uf_cro, cro, endereco, telefone, email) VALUES(?,?,?,?,?,?,?,?)");
            st.setString(1, auxiliar.getNome());
            st.setDate(2, auxiliar.getNascimento());
            st.setString(3, auxiliar.getCpf());
            st.setString(4, auxiliar.getUfCro());
            st.setString(5, auxiliar.getCro());
            st.setString(6, auxiliar.getEndereco());
            st.setString(7, auxiliar.getTelefone());
            st.setString(8, auxiliar.getEmail());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    public List<Auxiliar> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM auxiliares");
            rs = st.executeQuery();

            List<Auxiliar> listaAuxiliares = new ArrayList<>();
            while (rs.next()) {
                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setId(rs.getInt("id"));
                auxiliar.setNome(rs.getString("nome"));
                auxiliar.setNascimento(rs.getDate("nascimento"));
                auxiliar.setCpf(rs.getString("cpf"));
                auxiliar.setUfCro(rs.getString("uf_cro"));
                auxiliar.setCro(rs.getString("cro"));
                auxiliar.setEndereco(rs.getString("endereco"));
                auxiliar.setTelefone(rs.getString("telefone"));
                auxiliar.setEmail(rs.getString("email"));

                listaAuxiliares.add(auxiliar);
            }
            return listaAuxiliares;
        } catch (Exception exc) {
            return null;
        }
    }

    /**
     * Retorna um objeto Auxiliar de acordo com seu ID.
     */
    public Auxiliar retornaAuxiliar(int id) {

        Auxiliar auxiliar = new Auxiliar();

        try {
            st = conn.prepareStatement("SELECT * FROM auxiliares WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                auxiliar.setId(rs.getInt("id"));
                auxiliar.setNome(rs.getString("nome"));
                auxiliar.setNascimento(rs.getDate("nascimento"));
                auxiliar.setCpf(rs.getString("cpf"));
                auxiliar.setUfCro(rs.getString("uf_cro"));
                auxiliar.setCro(rs.getString("cro"));
                auxiliar.setEndereco(rs.getString("endereco"));
                auxiliar.setTelefone(rs.getString("telefone"));
                auxiliar.setEmail(rs.getString("email"));
                return auxiliar;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Retorna um objeto Auxiliar de acordo com seu nome.
     */
    public Auxiliar retornaAuxiliar(String nome) {

        Auxiliar auxiliar = new Auxiliar();

        try {
            st = conn.prepareStatement("SELECT * FROM auxiliares WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();

            if (rs.next()) {
                auxiliar.setId(rs.getInt("id"));
                auxiliar.setNome(rs.getString("nome"));
                auxiliar.setNascimento(rs.getDate("nascimento"));
                auxiliar.setCpf(rs.getString("cpf"));
                auxiliar.setUfCro(rs.getString("uf_cro"));
                auxiliar.setCro(rs.getString("cro"));
                auxiliar.setEndereco(rs.getString("endereco"));
                auxiliar.setTelefone(rs.getString("telefone"));
                auxiliar.setEmail(rs.getString("email"));
                return auxiliar;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Lista todos os auxiliares de acordo com a pesquisa de nome
     */
    public List<Auxiliar> pesquisaNome(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM auxiliares WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");
            rs = st.executeQuery();

            List<Auxiliar> listaAuxiliares = new ArrayList<>();
            while (rs.next()) {
                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setId(rs.getInt("id"));
                auxiliar.setNome(rs.getString("nome"));
                auxiliar.setNascimento(rs.getDate("nascimento"));
                auxiliar.setCpf(rs.getString("cpf"));
                auxiliar.setUfCro(rs.getString("uf_cro"));
                auxiliar.setCro(rs.getString("cro"));
                auxiliar.setEndereco(rs.getString("endereco"));
                auxiliar.setTelefone(rs.getString("telefone"));
                auxiliar.setEmail(rs.getString("email"));

                listaAuxiliares.add(auxiliar);
            }
            return listaAuxiliares;
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
