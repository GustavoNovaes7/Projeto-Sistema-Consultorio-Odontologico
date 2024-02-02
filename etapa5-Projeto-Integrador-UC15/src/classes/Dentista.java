package classes;

/**Contém os dados para a criação de um objeto Dentista, incluindo Getters, Setters e um Constructor*/
public class Dentista extends Profissional{
    private Especialidade[] especialidade;

    public Dentista() {
    }

    public Especialidade[] getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade[] especialidade) {
        this.especialidade = especialidade;
    }
    
}
