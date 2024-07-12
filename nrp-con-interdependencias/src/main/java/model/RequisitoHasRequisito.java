package model;

public class RequisitoHasRequisito {
    public enum tipo {
        exclusion, implicacion, combinacion, norelacion,
    }

    private tipo tipo;
    private int requisito_id;
    private int requisito_id1;

    public RequisitoHasRequisito(tipo tipo, int id, int id1) {
        this.tipo = tipo;
        this.requisito_id = id;
        this.requisito_id1 = id1;
    }

    public tipo getTipo() {
        return tipo;
    }

    public void setTipo(tipo tipo) {
        this.tipo = tipo;
    }

    public int getRequisito_id() {
        return requisito_id;
    }

    public int getRequisito_id1() {
        return requisito_id1;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof RequisitoHasRequisito)) return false;
        RequisitoHasRequisito relacion = (RequisitoHasRequisito) obj;

        if (this.tipo != relacion.getTipo() || this.requisito_id != relacion.getRequisito_id() || this.requisito_id1 != relacion.getRequisito_id1()) {
            return false;
        }
        return true;
    }
}
