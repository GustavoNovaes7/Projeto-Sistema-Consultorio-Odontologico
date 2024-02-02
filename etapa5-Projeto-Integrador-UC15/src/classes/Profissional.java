package classes;

public abstract class Profissional extends Pessoa {
    private String ufCro, cro;

    public Profissional() {
    }

    public String getUfCro() {
        return ufCro;
    }

    public void setUfCro(String ufCro) {
        this.ufCro = ufCro;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }
    
}
