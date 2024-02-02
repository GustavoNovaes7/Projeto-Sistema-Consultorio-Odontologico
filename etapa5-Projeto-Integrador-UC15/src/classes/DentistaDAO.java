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
 * os objetos da classe Dentista
 */
public class DentistaDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public DentistaDAO() {
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
    public int salvar(Dentista dentista) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO dentistas (nome, nascimento, cpf, uf_cro, cro, endereco, telefone, email) VALUES(?,?,?,?,?,?,?,?)");
            st.setString(1, dentista.getNome());
            st.setDate(2, dentista.getNascimento());
            st.setString(3, dentista.getCpf());
            st.setString(4, dentista.getUfCro());
            st.setString(5, dentista.getCro());
            st.setString(6, dentista.getEndereco());
            st.setString(7, dentista.getTelefone());
            st.setString(8, dentista.getEmail());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Exclui um objeto Dentista do banco de dados, retorna True caso obtenha
     * sucesso
     */
    public boolean excluir(int id) {
        try {
            st = conn.prepareStatement("DELETE FROM dentistas WHERE id= ?");
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Realiza consulta no banco de dados, retorna o Dentista encontrado
     */
    public Dentista consultar(String nome) {

        Dentista dentista = new Dentista();

        try {
            st = conn.prepareStatement("SELECT * FROM dentistas WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");
            rs = st.executeQuery();

            if (rs.next()) {
                dentista.setId(rs.getInt("id"));
                dentista.setNome(rs.getString("nome"));
                dentista.setNascimento(rs.getDate("nascimento"));
                dentista.setCpf(rs.getString("cpf"));
                dentista.setUfCro(rs.getString("uf_cro"));
                dentista.setUfCro(rs.getString("cro"));
                dentista.setUfCro(rs.getString("endereco"));
                dentista.setUfCro(rs.getString("telefone"));
                dentista.setUfCro(rs.getString("email"));
                return dentista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Atualiza um objeto Dentista no banco de dados. Retorna 1 caso obtenha
     * sucesso,
     */
    public int atualizar(Dentista dentista) {
        int status;
        try {
            st = conn.prepareStatement("UPDATE dentistas SET nome = ?, nascimento = ?, cpf = ? , uf_cro = ?, cro = ?, endereco = ?, telefone = ?, email = ? WHERE id =  ?");
            st.setString(1, dentista.getNome());
            st.setDate(2, dentista.getNascimento());
            st.setString(3, dentista.getCpf());
            st.setString(4, dentista.getUfCro());
            st.setString(5, dentista.getCro());
            st.setString(6, dentista.getEndereco());
            st.setString(7, dentista.getTelefone());
            st.setString(8, dentista.getEmail());
            st.setInt(9, dentista.getId());
            status = st.executeUpdate();
            return status; //retornar 1
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os dentistas armazenados no Banco de Dados
     */
    public List<Dentista> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM dentistas");
            rs = st.executeQuery();

            List<Dentista> listaDentistas = new ArrayList<>();
            while (rs.next()) {
                Dentista dentista = new Dentista();
                dentista.setId(rs.getInt("id"));
                dentista.setNome(rs.getString("nome"));
                dentista.setNascimento(rs.getDate("nascimento"));
                dentista.setCpf(rs.getString("cpf"));
                dentista.setUfCro(rs.getString("uf_cro"));
                dentista.setCro(rs.getString("cro"));
                dentista.setEndereco(rs.getString("endereco"));
                dentista.setTelefone(rs.getString("telefone"));
                dentista.setEmail(rs.getString("email"));

                listaDentistas.add(dentista);
            }
            return listaDentistas;
        } catch (Exception exc) {
            return null;
        }
    }
    
    /**
     * Retorna um objeto Dentista de acordo com seu nome.
     */
    public Dentista retornaDentista(String nome) {

        Dentista dentista = new Dentista();

        try {
            st = conn.prepareStatement("SELECT * FROM dentistas WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();

            if (rs.next()) {
                dentista.setId(rs.getInt("id"));
                dentista.setNome(rs.getString("nome"));
                dentista.setNascimento(rs.getDate("nascimento"));
                dentista.setCpf(rs.getString("cpf"));
                dentista.setUfCro(rs.getString("uf_cro"));
                dentista.setCro(rs.getString("cro"));
                dentista.setEndereco(rs.getString("endereco"));
                dentista.setTelefone(rs.getString("telefone"));
                dentista.setEmail(rs.getString("email"));
                return dentista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Retorna um objeto Dentista de acordo com seu ID.
     */
    public Dentista retornaDentista(int id) {

        Dentista dentista = new Dentista();

        try {
            st = conn.prepareStatement("SELECT * FROM dentistas WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                dentista.setId(rs.getInt("id"));
                dentista.setNome(rs.getString("nome"));
                dentista.setNascimento(rs.getDate("nascimento"));
                dentista.setCpf(rs.getString("cpf"));
                dentista.setUfCro(rs.getString("uf_cro"));
                dentista.setCro(rs.getString("cro"));
                dentista.setEndereco(rs.getString("endereco"));
                dentista.setTelefone(rs.getString("telefone"));
                dentista.setEmail(rs.getString("email"));
                return dentista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Lista todos os dentistas de acordo com a pesquisa de nome
     */
    public List<Dentista> pesquisaNome(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM dentistas WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");
            rs = st.executeQuery();

            List<Dentista> listaDentistas = new ArrayList<>();
            while (rs.next()) {
                Dentista dentista = new Dentista();
                dentista.setId(rs.getInt("id"));
                dentista.setNome(rs.getString("nome"));
                dentista.setNascimento(rs.getDate("nascimento"));
                dentista.setCpf(rs.getString("cpf"));
                dentista.setUfCro(rs.getString("uf_cro"));
                dentista.setCro(rs.getString("cro"));
                dentista.setEndereco(rs.getString("endereco"));
                dentista.setTelefone(rs.getString("telefone"));
                dentista.setEmail(rs.getString("email"));

                listaDentistas.add(dentista);
            }
            return listaDentistas;
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
