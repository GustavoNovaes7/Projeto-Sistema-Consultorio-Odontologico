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
 * os objetos da classe Paciente
 */
public class PacienteDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public PacienteDAO() {
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
     * Salva um objeto Paciente no banco de dados, retorna 1 caso obtenha
     * sucesso
     */
    public int salvar(Paciente paciente) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO pacientes (nome, nascimento, cpf, rg, endereco, telefone, email, indicacao, nome_indicacao,observacoes, convenios_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, paciente.getNome());
            st.setDate(2, paciente.getNascimento());
            st.setString(3, paciente.getCpf());
            st.setString(4, paciente.getRg());
            st.setString(5, paciente.getEndereco());
            st.setString(6, paciente.getTelefone());
            st.setString(7, paciente.getEmail());
            st.setString(8, paciente.getIndicacao());
            st.setString(9, paciente.getNomeIndicacao());
            st.setString(10, paciente.getObservacoes());
            if (paciente.getConvenio() == null) {
                st.setObject(11, null);
            } else {
                st.setObject(11, paciente.getConvenio().getId());
            }
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os pacientes armazenados no Banco de Dados
     */
    public List<Paciente> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM pacientes");
            rs = st.executeQuery();

            List<Paciente> listaPacientes = new ArrayList<>();
            ConvenioDAO dao = new ConvenioDAO();
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setNascimento(rs.getDate("nascimento"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setRg(rs.getString("rg"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEmail(rs.getString("email"));
                paciente.setIndicacao(rs.getString("indicacao"));
                paciente.setNomeIndicacao(rs.getString("nome_indicacao"));
                paciente.setObservacoes(rs.getString("observacoes"));
                paciente.setConvenio(dao.retornaConvenio2(rs.getInt("convenios_id")));

                listaPacientes.add(paciente);
            }
            dao.desconectar();
            return listaPacientes;
        } catch (Exception exc) {
            return null;
        }
    }

    /**
     * Retorna dados do Paciente tendo como referência seu ID.
     */
    public Paciente retornaPaciente(int id) {
        try {
            st = conn.prepareStatement("SELECT * FROM pacientes WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            ConvenioDAO dao = new ConvenioDAO();
            Paciente paciente = new Paciente();
            if (rs.next()) {
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setNascimento(rs.getDate("nascimento"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setRg(rs.getString("rg"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEmail(rs.getString("email"));
                paciente.setIndicacao(rs.getString("indicacao"));
                paciente.setNomeIndicacao(rs.getString("nome_indicacao"));
                paciente.setObservacoes(rs.getString("observacoes"));
                paciente.setConvenio(dao.retornaConvenio2(rs.getInt("convenios_id")));
            }
            return paciente;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Retorna dados do Paciente tendo como referência seu nome.
     */
    public Paciente retornaPaciente(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM pacientes WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            ConvenioDAO dao = new ConvenioDAO();
            Paciente paciente = new Paciente();
            if (rs.next()) {
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setNascimento(rs.getDate("nascimento"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setRg(rs.getString("rg"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEmail(rs.getString("email"));
                paciente.setIndicacao(rs.getString("indicacao"));
                paciente.setNomeIndicacao(rs.getString("nome_indicacao"));
                paciente.setObservacoes(rs.getString("observacoes"));
                paciente.setConvenio(dao.retornaConvenio2(rs.getInt("convenios_id")));
            }
            return paciente;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Retorna as observacoes sobre um determinado paciente de através de seu ID
     */
    public String retornaObservacoes(int id) {
        try {
            String observacoes = "";
            st = conn.prepareStatement("SELECT * FROM pacientes WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                observacoes = rs.getString("observacoes");
            }
            return observacoes;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Atualiza um objeto Observacoes de um determinado paciente no banco de
     * dados. Retorna 1 caso obtenha sucesso,
     */
    public int atualizaObservacoes(String observacoes, int id) {
        int status;
        try {
            st = conn.prepareStatement("UPDATE pacientes SET observacoes = ? WHERE id =  ?");
            st.setString(1, observacoes);
            st.setInt(2, id);
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os pacientes de acordo com a pesquisa de nome
     */
    public List<Paciente> pesquisaNome(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM pacientes WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");
            rs = st.executeQuery();

            List<Paciente> listaPacientes = new ArrayList<>();
            ConvenioDAO dao = new ConvenioDAO();
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setNascimento(rs.getDate("nascimento"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setRg(rs.getString("rg"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEmail(rs.getString("email"));
                paciente.setIndicacao(rs.getString("indicacao"));
                paciente.setNomeIndicacao(rs.getString("nome_indicacao"));
                paciente.setObservacoes(rs.getString("observacoes"));
                paciente.setConvenio(dao.retornaConvenio2(rs.getInt("convenios_id")));

                listaPacientes.add(paciente);
            }
            dao.desconectar();
            return listaPacientes;
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
