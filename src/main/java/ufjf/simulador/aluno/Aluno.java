package ufjf.simulador.aluno;

import ufjf.simulador.disciplinas.Disciplina;
import ufjf.simulador.turma.Turma;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Aluno {
    private String nome;
    private String matricula;
    private int cargaHorariaMaxima;

    // Disciplinas já cursadas (com nota)
    private Map<Disciplina, Integer> historicoDisciplinas;

    // (turmas desejadas para o próximo semestre)
    private List<Turma> planejamento;

    public Aluno(String nome, String matricula, int cargaHorariaMaxima) {
        this.nome = nome;
        this.matricula = matricula;
        this.cargaHorariaMaxima = cargaHorariaMaxima;
        this.historicoDisciplinas = new HashMap<>();
        this.planejamento = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getCargaHorariaMaxima() {
        return cargaHorariaMaxima;
    }

    public void adicionarDisciplinaConcluida(Disciplina disciplina, int nota) {
        historicoDisciplinas.put(disciplina, nota);
    }

    public boolean temDisciplinaComNotaMinima(Disciplina disciplina, int notaMinima) {
        Integer nota = historicoDisciplinas.get(disciplina);
        return nota != null && nota >= notaMinima;
    }

    public int getTotalCreditosCursados() {
        
        int total = 0;
        for (Disciplina d : historicoDisciplinas.keySet()) {
            int nota = historicoDisciplinas.get(d);
            
            if(nota >=60){
             total += d.getCargaHorariaSemanal();
            }
        }
        
        return total;
    }

    public void planejarTurma(Turma turma) {
        planejamento.add(turma);
    }

    public List<Turma> getPlanejamento() {
        return planejamento;
    }

    public Map<Disciplina, Integer> getHistorico() {
        return historicoDisciplinas;
    }
}
