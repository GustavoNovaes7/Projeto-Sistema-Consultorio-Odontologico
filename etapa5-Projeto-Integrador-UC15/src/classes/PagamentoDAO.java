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
 * os objetos da classe Pagamento
 */
public class PagamentoDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public PagamentoDAO() {
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
     * Salva um objeto Pagamento no banco de dados, retorna 1 caso obtenha
     * sucesso
     */
    public int salvar(Pagamento pagamento) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO pagamentos (tipo, parcelas, numero_cartao) VALUES(?,?,?)");
            st.setString(1, String.valueOf(pagamento.getTipo()));
            st.setInt(2, pagamento.getParcelas());
            st.setString(3, pagamento.getNumeroCartao());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os pagamentos armazenados no Banco de Dados
     */
    public List<Pagamento> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM pagamentos");
            rs = st.executeQuery();

            List<Pagamento> listaPagamentos = new ArrayList<>();
            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                pagamento.setTipo(rs.getString("tipo").charAt(0));
                pagamento.setParcelas(rs.getInt("parcelas"));
                pagamento.setNumeroCartao(rs.getString("numero_cartao"));

                listaPagamentos.add(pagamento);
            }
            return listaPagamentos;
        } catch (Exception exc) {
            return null;
        }
    }
    
    /**
     * Retorna um objeto Pagamento de acordo com seu ID.
     */
    public Pagamento retornaPagamento(int id) {

        Pagamento pagamento = new Pagamento();

        try {
            st = conn.prepareStatement("SELECT * FROM pagamentos WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                pagamento.setId(rs.getInt("id"));
                pagamento.setTipo(rs.getString("tipo").charAt(0));
                pagamento.setParcelas(rs.getInt("parcelas"));
                pagamento.setNumeroCartao(rs.getString("numero_cartao"));
 
                return pagamento;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Retorna o último Pagamento da tabela do Banco de Dados.
     */
    public Pagamento retornaUltimoPagamento() {

        Pagamento pagamento = new Pagamento();

        try {
            st = conn.prepareStatement("SELECT * FROM pagamentos ORDER BY id DESC LIMIT 1");
            rs = st.executeQuery();

            if (rs.next()) {
                pagamento.setId(rs.getInt("id"));
                pagamento.setTipo(rs.getString("tipo").charAt(0));
                pagamento.setParcelas(rs.getInt("parcelas"));
                pagamento.setNumeroCartao(rs.getString("numero_cartao"));
 
                return pagamento;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Exclui um objeto Pagamento do banco de dados, retorna True caso obtenha
     * sucesso
     */
    public boolean excluir(int id) {
        try {
            st = conn.prepareStatement("DELETE FROM pagamentos WHERE id= ?");
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
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
