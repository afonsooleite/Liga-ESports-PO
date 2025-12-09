package Backend;

/**
 * Enumeração que define os tipos de treinadores no sistema.
 */
public enum TipoTreinador {

    FPS("Treinador de Jogos FPS"),
    MOBA("Treinador de Jogos MOBA"),
    EFOOTBALL("Treinador de Jogos de Futebol Eletrónico");

    private final String descricao;

    TipoTreinador(String descricao) {
        this.descricao = descricao;
    }

  


    public static TipoTreinador fromString(String valor) {
        for (TipoTreinador tipo : TipoTreinador.values()) {
            if (tipo.name().equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de treinador inválido: " + valor);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
