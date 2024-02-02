package classes;
import java.time.LocalDateTime;

/**Contém os dados para a criação de um objeto Atendimento, incluindo Getters, Setters e um Constructor*/
public class Atendimento {
    private int id;
    private String observacoes, numeroConvenio;
    LocalDateTime dataHora;
    private double valor;
    private Especialidade especialidade;
    private Paciente paciente;
    private Dentista dentistaMarcado;
    private Dentista dentistaAtendeu;
    private Auxiliar auxiliar;
    private Convenio convenio;
    private Pagamento pagamento;

    public Atendimento() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(String numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dentista getDentistaMarcado() {
        return dentistaMarcado;
    }

    public void setDentistaMarcado(Dentista dentistaMarcado) {
        this.dentistaMarcado = dentistaMarcado;
    }

    public Dentista getDentistaAtendeu() {
        return dentistaAtendeu;
    }

    public void setDentistaAtendeu(Dentista dentistaAtendeu) {
        this.dentistaAtendeu = dentistaAtendeu;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliar auxiliar) {
        this.auxiliar = auxiliar;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
  
}
