package ufjf.simulador.excecoes;

public abstract class ValidacaoMatriculaException extends MatriculaException {
    public ValidacaoMatriculaException(String mensagem) {
        super(mensagem);
    }
}
