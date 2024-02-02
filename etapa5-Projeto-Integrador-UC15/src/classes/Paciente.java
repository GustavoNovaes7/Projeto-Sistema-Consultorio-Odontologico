package classes;

public class Paciente extends Pessoa{
    private String rg, observacoes, indicacao, nomeIndicacao;
    private Convenio convenio;

    public Paciente() {
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    public String getNomeIndicacao() {
        return nomeIndicacao;
    }

    public void setNomeIndicacao(String nomeIndicacao) {
        this.nomeIndicacao = nomeIndicacao;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
    
}