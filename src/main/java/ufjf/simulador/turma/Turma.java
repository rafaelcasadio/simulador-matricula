package ufjf.simulador.turma;

import ufjf.simulador.disciplinas.Disciplina;

import java.util.List;
import java.util.ArrayList;

public class Turma {
    private String id;
    private Disciplina disciplina;
    private int capacidadeMaxima;
    private List<String> matriculasAlunos;
    private String horario; 

    public Turma(String id, Disciplina disciplina, int capacidadeMaxima, String horario) {
        this.id = id;
        this.disciplina = disciplina;
        this.capacidadeMaxima = capacidadeMaxima;
        this.horario = horario;
        this.matriculasAlunos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public int getQuantidadeMatriculados() {
        return matriculasAlunos.size();
    }

    public String getHorario() {
        return horario;
    }

    public boolean temVagas() {
        return matriculasAlunos.size() < capacidadeMaxima;
    }

    public boolean horarioConflitaCom(Turma outraTurma) {
        return this.horario.equals(outraTurma.getHorario());
    }

    public void matricularAluno(String matriculaAluno) {
        if (!matriculasAlunos.contains(matriculaAluno)) {
            matriculasAlunos.add(matriculaAluno);
        }
    }
}
