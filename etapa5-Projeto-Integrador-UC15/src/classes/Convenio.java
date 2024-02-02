package classes;

public class Convenio {
    private int id, tempoCarencia;
    private String nome, cnpj;

    public Convenio() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTempoCarencia() {
        return tempoCarencia;
    }

    public void setTempoCarencia(int tempoCarencia) {
        this.tempoCarencia = tempoCarencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
}
