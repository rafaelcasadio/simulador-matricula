package ufjf.simulador.sistema;

import ufjf.simulador.aluno.Aluno;
import ufjf.simulador.turma.Turma;
import ufjf.simulador.disciplinas.Disciplina;
import ufjf.simulador.validadores.ValidadorPreRequisito;
import ufjf.simulador.excecoes.*;

import java.util.ArrayList;
import java.util.List;

public class SistemaAcademico {

    public void simularMatricula(Aluno aluno) {
        List<Turma> aceitas = new ArrayList<>();
        List<String> relatorio = new ArrayList<>();
        int cargaAtual = 0;

        List<Turma> planejamento = aluno.getPlanejamento();
             
        for (Turma turma : planejamento) {
            Disciplina disciplina = turma.getDisciplina();

            try {
                //Já foi cursada e tem nota> > 60
                for (Disciplina d : aluno.getHistorico().keySet()) {
                     int nota = aluno.getHistorico().get(d);
            
                    if(disciplina == d && nota >=60){
                        throw new DisciplinaJaCursadaException("Disciplina ja foi cursada");
                    }
            }
                
                // PRÉ-REQUISITOS
                for (ValidadorPreRequisito validador : disciplina.getValidadores()) {
                    if (!validador.validar(aluno)) {
                        throw new PreRequisitoNaoCumpridoException("Pre-requisito nao cumprido para " + disciplina.getNome());
                    }
                }

                // CONFLITO DE HORÁRIO + PRECEDÊNCIA
                boolean conflito = false;
                for (Turma t : aceitas) {
                    if (turma.horarioConflitaCom(t)) {
                        int prNova = disciplina.getPrioridade();
                        int prAtual = t.getDisciplina().getPrioridade();

                        if (prNova > prAtual) {
                            relatorio.add("[REMOVIDA] " + t.getDisciplina().getNome() + " por conflito com " + disciplina.getNome());
                            aceitas.remove(t);
                            cargaAtual -= t.getDisciplina().getCargaHorariaSemanal();
                            break;
                        } else if (prNova < prAtual) {
                            conflito = true;
                            break;
                        } else {
                            relatorio.add("[REMOVIDA] " + t.getDisciplina().getNome() + " por conflito com " + disciplina.getNome());
                            aceitas.remove(t);
                            throw new ConflitoDeHorarioException("Conflito entre disciplinas de mesma prioridade: "
                                    + disciplina.getNome() + " e " + t.getDisciplina().getNome());
                        }
                    }
                }
                if (conflito) {
                    throw new ConflitoDeHorarioException("Conflito com disciplina mais prioritária ja aceita.");
                }

                // CARGA HORÁRIA
                if (cargaAtual + disciplina.getCargaHorariaSemanal() > aluno.getCargaHorariaMaxima()) {
                    throw new CargaHorariaExcedidaException("Carga horaria excedida ao tentar adicionar " + disciplina.getNome());
                }

                // VAGAS
                if (!turma.temVagas()) {
                    throw new TurmaCheiaException("Turma cheia: " + turma.getId());
                }

                //matricular (provisoriamente)
                turma.matricularAluno(aluno.getMatricula());
                aceitas.add(turma);
                cargaAtual += disciplina.getCargaHorariaSemanal();
                relatorio.add("[ACEITA] " + disciplina.getNome());

            } catch (MatriculaException e) {
                relatorio.add("[REJEITADA] " + disciplina.getNome() + " - Motivo: " + e.getMessage());
            }
        }

        //verificar co-requisitos entre disciplinas provisoriamente aceitas
        for (Turma turma : new ArrayList<>(aceitas)) {
            Disciplina d = turma.getDisciplina();
            for (Disciplina co : d.getCoRequisitos()) {
                boolean presente = aceitas.stream()
                    .anyMatch(t -> t.getDisciplina().equals(co));
                if (!presente) {
                    aceitas.remove(turma);
                    relatorio.remove("[ACEITA] " + d.getNome()); 
                    relatorio.add("[REJEITADA] " + d.getNome() + " - Faltando co-requisito: " + co.getNome());
                    break;
                }
            }
        }

        // Atualizar histórico do aluno com disciplinas aceitas
        for (Turma t : aceitas) {
            aluno.adicionarDisciplinaConcluida(t.getDisciplina(), 100);
        }

        // Exibir relatório
        System.out.println("==== RELATORIO DE MATRICULA DO ALUNO: " + aluno.getNome() +" ====");
        for (String linha : relatorio) {
            System.out.println(linha);
        }
        System.out.println("");
    }
}
