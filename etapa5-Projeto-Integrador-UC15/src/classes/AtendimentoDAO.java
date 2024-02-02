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
public class AtendimentoDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public AtendimentoDAO() {
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
    public int salvar(Atendimento atendimento) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO atendimentos (data_hora, valor, observacoes, numero_convenio, "
                    + "especialidades_id, pacientes_id, dentistas_id_marcado,"
                    + "pagamentos_id) VALUES(?,?,?,?,?,?,?,?)");
            st.setTimestamp(1, java.sql.Timestamp.valueOf(atendimento.getDataHora()));
            st.setDouble(2, atendimento.getValor());
            st.setString(3, atendimento.getObservacoes());
            st.setString(4, atendimento.getNumeroConvenio());
            st.setObject(5, atendimento.getEspecialidade().getId());
            st.setObject(6, atendimento.getPaciente().getId());
            st.setObject(7, atendimento.getDentistaMarcado().getId());
            st.setObject(8, atendimento.getPagamento().getId());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os atendimentos armazenados no Banco de Dados
     */
    public List<Atendimento> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM atendimentos");
            rs = st.executeQuery();

            List<Atendimento> listaAtendimentos = new ArrayList<>();
            PacienteDAO pacDao = new PacienteDAO();
            EspecialidadeDAO espDao = new EspecialidadeDAO();
            DentistaDAO denDao = new DentistaDAO();
            AuxiliarDAO auxDao = new AuxiliarDAO();
            Auxiliar auxiliarProvisorio = new Auxiliar();
            auxiliarProvisorio.setNome("");
            Dentista dentistaProvisorio = new Dentista();
            dentistaProvisorio.setNome("");
            ConvenioDAO conDao = new ConvenioDAO();
            PagamentoDAO pagDao = new PagamentoDAO();
            while (rs.next()) {
                Atendimento atendimento = new Atendimento();
                atendimento.setId(rs.getInt("id"));
                atendimento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                atendimento.setValor(rs.getDouble("valor"));
                atendimento.setObservacoes(rs.getString("observacoes"));
                atendimento.setNumeroConvenio(rs.getString("numero_convenio"));
                atendimento.setPaciente(pacDao.retornaPaciente(rs.getInt("pacientes_id")));
                atendimento.setEspecialidade(espDao.retornaEspecialidade2(rs.getInt("especialidades_id")));
                atendimento.setDentistaMarcado(denDao.retornaDentista(rs.getInt("dentistas_id_marcado")));
                if (rs.getInt("dentistas_id_atendeu") == 0) {
                    atendimento.setDentistaAtendeu(dentistaProvisorio);
                } else {
                    atendimento.setDentistaAtendeu(denDao.retornaDentista(rs.getInt("dentistas_id_atendeu")));
                }
                if (rs.getInt("auxiliares_id") == 0) {
                    atendimento.setAuxiliar(auxiliarProvisorio);
                } else {
                    atendimento.setAuxiliar(auxDao.retornaAuxiliar(rs.getInt("auxiliares_id")));
                }
                atendimento.setConvenio(conDao.retornaConvenio2(rs.getInt("convenios_id")));
                atendimento.setPagamento(pagDao.retornaPagamento(rs.getInt("pagamentos_id")));

                listaAtendimentos.add(atendimento);
            }
            return listaAtendimentos;
        } catch (Exception exc) {
            return null;
        }
    }

    /**
     * Retorna alguns dados de um atendimento de acordo com seu ID
     */
    public Atendimento retornaAtendimento(int id) {
        try {
            st = conn.prepareStatement("SELECT * FROM atendimentos WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            Atendimento atendimento = new Atendimento();
            EspecialidadeDAO espDao = new EspecialidadeDAO();
            PacienteDAO pacDao = new PacienteDAO();
            DentistaDAO denDao = new DentistaDAO();
            PagamentoDAO pagDao = new PagamentoDAO();
            if (rs.next()) {
                atendimento.setId(rs.getInt("id"));
                atendimento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                atendimento.setValor(rs.getDouble("valor"));
                atendimento.setObservacoes(rs.getString("observacoes"));
                atendimento.setNumeroConvenio(rs.getString("numero_convenio"));
                atendimento.setPaciente(pacDao.retornaPaciente(rs.getInt("pacientes_id")));
                atendimento.setEspecialidade(espDao.retornaEspecialidade2(rs.getInt("especialidades_id")));
                atendimento.setDentistaMarcado(denDao.retornaDentista(rs.getInt("dentistas_id_marcado")));
                atendimento.setPagamento(pagDao.retornaPagamento(rs.getInt("pagamentos_id")));
            }
            return atendimento;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Atualiza um objeto Atendimento no banco de dados quando o atendimento é
     * finalizado. Retorna 1 caso obtenha sucesso,
     */
    public int finalizaAtendimentoDentista(Atendimento atendimento) {
        int status;
        try {
            st = conn.prepareStatement("UPDATE atendimentos SET dentistas_id_atendeu = ? WHERE id =  ?");
            st.setInt(1, atendimento.getDentistaAtendeu().getId());
            st.setInt(2, atendimento.getId());

            status = st.executeUpdate();
            return status; //retornar 1
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            return ex.getErrorCode();
        }
    }

    /**
     * Atualiza um objeto Atendimento no banco de dados quando o atendimento é
     * finalizado. Retorna 1 caso obtenha sucesso,
     */
    public int finalizaAtendimentoAuxiliar(Atendimento atendimento) {
        int status;
        try {
            st = conn.prepareStatement("UPDATE atendimentos SET auxiliares_id = ? WHERE id =  ?");
            st.setInt(1, atendimento.getAuxiliar().getId());
            st.setInt(2, atendimento.getId());

            status = st.executeUpdate();
            return status; //retornar 1
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os atendimentos de acordo com a pesquisa de nome do paciente
     */
    public List<Atendimento> pesquisaNomePaciente(List<Paciente> listaPacientes) {
        try {

            List<Atendimento> listaAtendimentos = new ArrayList<>();

            for (Paciente p : listaPacientes) {

                st = conn.prepareStatement("SELECT * FROM atendimentos WHERE pacientes_id = ?");
                st.setInt(1, p.getId());
                rs = st.executeQuery();

                PacienteDAO pacDao = new PacienteDAO();
                EspecialidadeDAO espDao = new EspecialidadeDAO();
                DentistaDAO denDao = new DentistaDAO();
                AuxiliarDAO auxDao = new AuxiliarDAO();
                Auxiliar auxiliarProvisorio = new Auxiliar();
                auxiliarProvisorio.setNome("");
                Dentista dentistaProvisorio = new Dentista();
                dentistaProvisorio.setNome("");
                ConvenioDAO conDao = new ConvenioDAO();
                PagamentoDAO pagDao = new PagamentoDAO();
                while (rs.next()) {
                    Atendimento atendimento = new Atendimento();
                    atendimento.setId(rs.getInt("id"));
                    atendimento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    atendimento.setValor(rs.getDouble("valor"));
                    atendimento.setObservacoes(rs.getString("observacoes"));
                    atendimento.setNumeroConvenio(rs.getString("numero_convenio"));
                    atendimento.setPaciente(pacDao.retornaPaciente(rs.getInt("pacientes_id")));
                    atendimento.setEspecialidade(espDao.retornaEspecialidade2(rs.getInt("especialidades_id")));
                    atendimento.setDentistaMarcado(denDao.retornaDentista(rs.getInt("dentistas_id_marcado")));
                    if (rs.getInt("dentistas_id_atendeu") == 0) {
                        atendimento.setDentistaAtendeu(dentistaProvisorio);
                    } else {
                        atendimento.setDentistaAtendeu(denDao.retornaDentista(rs.getInt("dentistas_id_atendeu")));
                    }
                    if (rs.getInt("auxiliares_id") == 0) {
                        atendimento.setAuxiliar(auxiliarProvisorio);
                    } else {
                        atendimento.setAuxiliar(auxDao.retornaAuxiliar(rs.getInt("auxiliares_id")));
                    }
                    atendimento.setConvenio(conDao.retornaConvenio2(rs.getInt("convenios_id")));
                    atendimento.setPagamento(pagDao.retornaPagamento(rs.getInt("pagamentos_id")));

                    listaAtendimentos.add(atendimento);
                }
            }
            return listaAtendimentos;
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
