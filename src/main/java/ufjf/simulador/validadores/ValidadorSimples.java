package ufjf.simulador.validadores;

import ufjf.simulador.aluno.Aluno;
import ufjf.simulador.disciplinas.Disciplina;

public class ValidadorSimples implements ValidadorPreRequisito {
    private Disciplina disciplinaRequerida;

    public ValidadorSimples(Disciplina disciplinaRequerida) {
        this.disciplinaRequerida = disciplinaRequerida;
    }

    @Override
    public boolean validar(Aluno aluno) {
        return aluno.temDisciplinaComNotaMinima(disciplinaRequerida, 60);
    }
}
