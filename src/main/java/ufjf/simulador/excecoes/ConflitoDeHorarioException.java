package ufjf.simulador.excecoes;

public class ConflitoDeHorarioException extends ValidacaoMatriculaException {
    public ConflitoDeHorarioException(String mensagem) {
        super(mensagem);
    }
}
