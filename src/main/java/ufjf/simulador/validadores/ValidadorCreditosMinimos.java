package ufjf.simulador.validadores;

import ufjf.simulador.aluno.Aluno;
import ufjf.simulador.disciplinas.Disciplina;

public class ValidadorCreditosMinimos implements ValidadorPreRequisito {
    private int minimoCreditos;

    public ValidadorCreditosMinimos(int minimoCreditos) {
        this.minimoCreditos = minimoCreditos;
    }

    @Override
    public boolean validar(Aluno aluno) {
        return aluno.getTotalCreditosCursados() >= minimoCreditos;
    }
}
