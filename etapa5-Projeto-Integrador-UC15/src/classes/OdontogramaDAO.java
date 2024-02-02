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
 * os objetos da classe Odontograma
 */
public class OdontogramaDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

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
     * Salva um objeto Odontograma no banco de dados, retorna 1 caso obtenha
     * sucesso
     */
    public int salvar(Odontograma odontograma) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO odontogramas (tipo, anotacoes, pacientes_id) VALUES(?,?,?)");
            st.setString(1, String.valueOf(odontograma.getTipo()));
            st.setString(2, odontograma.getAnotacoes());
            st.setInt(3, odontograma.getPaciente().getId());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Exclui relação Odontograma_Dente do Banco de Dados de acordo com o ID do
     * Odontograma especificado..
     */
    public boolean excluirRelacaoOdontoDenteAntiga(int idOdontograma) {
        try {
            st = conn.prepareStatement("DELETE FROM odontograma_dente WHERE odontogramas_id = ?");
            st.setInt(1, idOdontograma);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Exclui um objeto Odontograma do banco de dados para que um novo seja
     * salvo em outro método, sem acumular Odontogramas vinculados ao mesmo
     * paciente.
     */
    public boolean excluirOdontogramaAntigo(int idPaciente) {
        try {
            st = conn.prepareStatement("DELETE FROM odontogramas WHERE pacientes_id= ?");
            st.setInt(1, idPaciente);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Salva uma nova relação Odontograma-Dente no Banco de Dados
     */
    public int salvarDentesOdontograma(int idOdontograma, int idDente) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO odontograma_dente (odontogramas_id, dentes_id) VALUES(?,?)");
            st.setInt(1, idOdontograma);
            st.setInt(2, idDente);
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Retorna o ID do Odontograma tendo como referência o ID do Paciente.
     */
    public int retornaIDOdontograma(int idPaciente) {
        try {
            st = conn.prepareStatement("SELECT * FROM odontogramas WHERE pacientes_id = ?");
            st.setInt(1, idPaciente);
            rs = st.executeQuery();

            Odontograma odontograma = new Odontograma();
            int idOdontograma;
            if (rs.next()) {
                odontograma.setId(rs.getInt("id"));
            }
            idOdontograma = odontograma.getId();
            return idOdontograma;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return 0;
        }
    }

    /**
     * Retorna alguns dados do Odontograma tendo como referência o ID do
     * Paciente.
     */
    public Odontograma retornaOdontograma(int idPaciente) {
        try {
            st = conn.prepareStatement("SELECT * FROM odontogramas WHERE pacientes_id = ?");
            st.setInt(1, idPaciente);
            rs = st.executeQuery();

            Odontograma odontograma = new Odontograma();
            if (rs.next()) {
                odontograma.setId(rs.getInt("id"));
                odontograma.setTipo(rs.getString("tipo").charAt(0));
                odontograma.setAnotacoes(rs.getString("anotacoes"));
            }
            return odontograma;
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
