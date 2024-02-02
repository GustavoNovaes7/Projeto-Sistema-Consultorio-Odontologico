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
 * os objetos da classe Convenio
 */
public class ConvenioDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public ConvenioDAO() {
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
     * Lista todos os convenios armazenados no Banco de Dados
     */
    public List<Convenio> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM convenios");
            rs = st.executeQuery();

            List<Convenio> listaConvenios = new ArrayList<>();
            while (rs.next()) {
                Convenio convenio = new Convenio();
                convenio.setId(rs.getInt("id"));
                convenio.setNome(rs.getString("nome"));
                convenio.setCnpj(rs.getString("cnpj"));
                convenio.setTempoCarencia(rs.getInt("tempo_carencia"));

                listaConvenios.add(convenio);
            }
            return listaConvenios;
        } catch (Exception exc) {
            return null;
        }
    }

    /**
     * Retorna o objeto Convenio de acordo com o nome recebido
     */
    public Convenio retornaConvenio(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM convenios WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            Convenio convenio = new Convenio();

            if (rs.next()) {
                convenio.setId(rs.getInt("id"));
                convenio.setNome(rs.getString("nome"));
                convenio.setNome(rs.getString("cnpj"));
                convenio.setTempoCarencia(rs.getInt("tempo_carencia"));
            }
            return convenio;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Retorna o objeto Convenio de acordo com o ID recebido
     */
    public Convenio retornaConvenio2(int id) {
        try {
            st = conn.prepareStatement("SELECT * FROM convenios WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            Convenio convenio = new Convenio();

            if (rs.next()) {
                convenio.setId(rs.getInt("id"));
                convenio.setNome(rs.getString("nome"));
                convenio.setCnpj(rs.getString("cnpj"));
                convenio.setTempoCarencia(rs.getInt("tempo_carencia"));
            }
            return convenio;
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
