package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contém os métodos que irão executar funções associadas com o banco de dados e
 * os objetos da classe Usuario.
 */
public class UsuarioDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public UsuarioDAO() {
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

    public Usuario validaUsuario(Usuario usuario) {
        Usuario usuarioEncontrado = new Usuario();
        
        try {
            st = conn.prepareStatement("SELECT * FROM usuarios WHERE login = ? AND senha = ?");
            st.setString(1, usuario.getLogin());
            st.setString(2, usuario.getSenha());
            rs = st.executeQuery();

            if (rs.next()) {
                usuarioEncontrado.setId(rs.getInt("id"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setLogin(rs.getString("login"));
                usuarioEncontrado.setSenha(rs.getString("senha"));
                usuarioEncontrado.setTipo(rs.getString("tipo"));
                return usuarioEncontrado;
            } else{
                return null;
            }           
        } catch (SQLException ex) {
            System.out.println("Sintaxe de comando invalida");
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
