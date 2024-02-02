package classes;

public class Odontograma {
    private int id;
    private char tipo;
    private String anotacoes;
    private Paciente paciente;
    private Dente[] dentes;

    public Odontograma() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dente[] getDentes() {
        return dentes;
    }

    public void setDentes(Dente[] dentes) {
        this.dentes = dentes;
    }
        
}
