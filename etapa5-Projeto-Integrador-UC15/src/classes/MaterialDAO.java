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
 * os objetos da classe Material.
 */
public class MaterialDAO {

    private Conexao conexao;
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * Constructor que faz a ligação com Conexão
     */
    public MaterialDAO() {
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
     * Salva um objeto Material no banco de dados, retorna 1 caso obtenha
     * sucesso
     */
    public int salvar(Material material) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO materiais (nome, marca, quantidade_comprada, quantidade_gasta, valor_unitario) VALUES(?,?,?,?,?)");
            st.setString(1, material.getNome());
            st.setString(2, material.getMarca());
            st.setInt(3, material.getQntdComprada());
            st.setInt(4, material.getQntdGasta());
            st.setDouble(5, material.getValorUnitario());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    /**
     * Atualiza um objeto Material no banco de dados. Retorna 1 caso obtenha
     * sucesso,
     */
    public int atualizar(Material material) {
        int status;
        try {
            st = conn.prepareStatement("UPDATE materiais SET nome = ?, marca = ?, quantidade_comprada = ? , quantidade_gasta = ?, valor_unitario = ? WHERE id =  ?");
            st.setString(1, material.getNome());
            st.setString(2, material.getMarca());
            st.setInt(3, material.getQntdComprada());
            st.setInt(4, material.getQntdGasta());
            st.setDouble(5, material.getValorUnitario());
            st.setInt(6, material.getId());
            status = st.executeUpdate();
            return status; //retornar 1
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            return ex.getErrorCode();
        }
    }

    /**
     * Lista todos os materiais armazenados no Banco de Dados
     */
    public List<Material> listar() {
        try {
            st = conn.prepareStatement("SELECT * FROM materiais");
            rs = st.executeQuery();

            List<Material> listaMateriais = new ArrayList<>();
            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("id"));
                material.setNome(rs.getString("nome"));
                material.setMarca(rs.getString("marca"));
                material.setQntdComprada(rs.getInt("quantidade_comprada"));
                material.setQntdGasta(rs.getInt("quantidade_gasta"));
                material.setValorUnitario(rs.getDouble("valor_unitario"));

                listaMateriais.add(material);
            }
            return listaMateriais;
        } catch (Exception exc) {
            return null;
        }
    }

    /**
     * Retorna um material de acordo com seu ID
     */
    public Material retornaMaterial(int id) {
        Material material = new Material();
        try {
            st = conn.prepareStatement("SELECT * FROM materiais WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                material.setId(rs.getInt("id"));
                material.setNome(rs.getString("nome"));
                material.setMarca(rs.getString("marca"));
                material.setQntdComprada(rs.getInt("quantidade_comprada"));
                material.setQntdGasta(rs.getInt("quantidade_gasta"));
                material.setValorUnitario(rs.getDouble("valor_unitario"));

                return material;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Lista todos os materiais de acordo com a pesquisa de nome
     */
    public List<Material> pesquisaMaterial(String nome) {
        try {
            st = conn.prepareStatement("SELECT * FROM materiais WHERE nome LIKE ?");
            st.setString(1, "%" + nome + "%");
            rs = st.executeQuery();

            List<Material> listaMateriais = new ArrayList<>();
            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("id"));
                material.setNome(rs.getString("nome"));
                material.setMarca(rs.getString("marca"));
                material.setQntdComprada(rs.getInt("quantidade_comprada"));
                material.setQntdGasta(rs.getInt("quantidade_gasta"));
                material.setValorUnitario(rs.getDouble("valor_unitario"));

                listaMateriais.add(material);
            }
            return listaMateriais;
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
