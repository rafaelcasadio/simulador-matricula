package ufjf.simulador.validadores;

import ufjf.simulador.aluno.Aluno;
import ufjf.simulador.disciplinas.Disciplina;

public interface ValidadorPreRequisito {
    boolean validar(Aluno aluno);
}