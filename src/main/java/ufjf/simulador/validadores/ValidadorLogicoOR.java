package ufjf.simulador.validadores;

import ufjf.simulador.aluno.Aluno;
import ufjf.simulador.disciplinas.Disciplina;

import java.util.List;

public class ValidadorLogicoOR implements ValidadorPreRequisito {
    private List<Disciplina> disciplinasRequeridas;

    public ValidadorLogicoOR(List<Disciplina> disciplinasRequeridas) {
        this.disciplinasRequeridas = disciplinasRequeridas;
    }

    @Override
    public boolean validar(Aluno aluno) {
        for (Disciplina d : disciplinasRequeridas) {
            if (aluno.temDisciplinaComNotaMinima(d, 60)) {
                return true;
            }
        }
        return false;
    }
}
