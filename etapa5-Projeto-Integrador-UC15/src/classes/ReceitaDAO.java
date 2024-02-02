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
 * os objetos da classe Receita
 */
public class ReceitaDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public ReceitaDAO() {
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
     * Salva um objeto Receita no banco de dados, retorna 1 caso obtenha sucesso
     */
    public int salvar(Receita receita) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO receitas (medicamento, quantidade, instrucoes, atendimentos_id) VALUES(?,?,?,?)");
            st.setString(1, receita.getMedicamento());
            st.setInt(2, receita.getQuantidade());
            st.setString(3, receita.getInstrucoes());
            st.setObject(4, receita.getAtendimento().getId());

            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os medicamentos de um determinado atendimento, de acordo com
     * o ID do atendimento.
     */
    public List<Receita> listar(int idAtendimento) {
        try {
            st = conn.prepareStatement("SELECT * FROM receitas WHERE atendimentos_id = ?");
            st.setInt(1, idAtendimento);
            rs = st.executeQuery();

            List<Receita> listaReceitas = new ArrayList<>();
            while (rs.next()) {
                Receita receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setMedicamento(rs.getString("medicamento"));
                receita.setQuantidade(rs.getInt("quantidade"));
                receita.setInstrucoes(rs.getString("instrucoes"));

                listaReceitas.add(receita);
            }
            return listaReceitas;
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
