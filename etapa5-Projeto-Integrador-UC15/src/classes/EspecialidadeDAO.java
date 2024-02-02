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
 * os objetos da classe Especialidade
 */
public class EspecialidadeDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public EspecialidadeDAO() {
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
     * Coleta os dados das Especialidades do Banco de Dados e retorna um modelo
     * ComboBox contendo-as, para ser usado na tela de cadastro de
     * especialidades
     */
    public DefaultComboBoxModel modeloComboBox() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            st = conn.prepareStatement("SELECT * FROM especialidades");
            rs = st.executeQuery();

            while (rs.next()) {
                modelo.addElement(rs.getString("nome"));
            }
            return modelo;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Salva uma nova relação Dentista-Especialidade no Banco de Dados
     */
    public int salvar(int idDentista, int idEspecialidade) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO dentista_especialidade (dentistas_id, especialidades_id) VALUES(?,?)");
            st.setInt(1, idDentista);
            st.setInt(2, idEspecialidade);
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Retorna uma especialidade do Banco de Dados com o mesmo nome do item
     * selecionado na Combo Box.Usado para Atendimentos.
     */
    public Especialidade retornaEspecialidade(String nome) {
        Especialidade especialidade = new Especialidade();

        try {
            st = conn.prepareStatement("SELECT * FROM especialidades WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if (rs.next()) {
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
            }
            return especialidade;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Retorna uma especialidade do Banco de Dados de acordo com seu ID
     */
    public Especialidade retornaEspecialidade2(int id) {
        Especialidade especialidade = new Especialidade();

        try {
            st = conn.prepareStatement("SELECT * FROM especialidades WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
            }
            return especialidade;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Retorna o ID de uma especialidade do Banco de Dados com o mesmo nome do
     * item selecionado na Combo Box
     */
    public int retornaIdEspecialidade(String nome) {
        int idEspecialidade;
        try {
            st = conn.prepareStatement("SELECT * FROM especialidades WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if (rs.next()) {
                idEspecialidade = rs.getInt("id");
            } else {
                idEspecialidade = 0;
            }
            return idEspecialidade;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Retorna uma lista especialidade do Banco de Dados vinculadas a um
     * determinado(a) Dentista de acordo com seu ID
     */
    public List<String> listar(int idDentista) {
        try {
            List<String> especialidades = new ArrayList();
            List<Integer> idEspecialidades = new ArrayList();

            st = conn.prepareStatement("SELECT * FROM dentista_especialidade WHERE dentistas_id = ?");
            st.setInt(1, idDentista);
            rs = st.executeQuery();

            while (rs.next()) {
                idEspecialidades.add(rs.getInt("especialidades_id"));
            }

            for (int i = 0; i < idEspecialidades.size(); i++) {
                st = conn.prepareStatement("SELECT * FROM especialidades WHERE id = ?");
                st.setInt(1, idEspecialidades.get(i));
                rs = st.executeQuery();
                if (rs.next()) {
                    especialidades.add(rs.getString("nome"));
                }
            }

            return especialidades;
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
