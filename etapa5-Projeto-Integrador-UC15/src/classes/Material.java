package classes;

/**Contém os dados para a criação de um objeto Material, incluindo Getters, Setters e um Constructor*/
public class Material {
    private int id, qntdComprada, qntdGasta;
    private String nome, marca;
    private double valorUnitario;

    public Material() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQntdComprada() {
        return qntdComprada;
    }

    public void setQntdComprada(int qntdComprada) {
        this.qntdComprada = qntdComprada;
    }

    public int getQntdGasta() {
        return qntdGasta;
    }

    public void setQntdGasta(int qntdGasta) {
        this.qntdGasta = qntdGasta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    public int valorQntdEstoque(int qntdComprada, int qntdGasta){
        return qntdComprada-qntdGasta;
    }

    public double valorTotalObjeto(int qntdComprada, double valorUnitario){
        return qntdComprada*valorUnitario;
    }
    
}
