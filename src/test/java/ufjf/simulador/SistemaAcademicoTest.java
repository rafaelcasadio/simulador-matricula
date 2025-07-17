package ufjf.simulador;

import ufjf.simulador.aluno.Aluno;
import ufjf.simulador.disciplinas.*;
import ufjf.simulador.sistema.SistemaAcademico;
import ufjf.simulador.turma.Turma;
import ufjf.simulador.validadores.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaAcademicoTest {

    @Test
    public void testMatriculaValida() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Disciplina algoritmos = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);
        algoritmos.adicionarValidador(new ValidadorSimples(poo));

        Aluno aluno = new Aluno("Joao", "202501001", 20);
        aluno.adicionarDisciplinaConcluida(poo, 70); // Já cursou POO

        Turma turmaAlg = new Turma("T01", algoritmos, 10, "segunda 08h–10h");
        aluno.planejarTurma(turmaAlg);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        // Espera-se que Algoritmos tenha sido adicionada ao histórico pois tem o prerequisito poo
        assertTrue(aluno.getHistorico().containsKey(algoritmos));
    }

    @Test
    public void testRejeicaoPorFaltaDePreRequisitoSimples() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Disciplina algoritmos = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);
        algoritmos.adicionarValidador(new ValidadorSimples(poo));

        Aluno aluno = new Aluno("Gustavo", "202501002", 20);

        Turma turmaAlg = new Turma("T02", algoritmos, 10, "segunda 10h–12h");
        aluno.planejarTurma(turmaAlg);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que nao tenha algoritimos no historico pois nao tem prerequisito poo
        assertFalse(aluno.getHistorico().containsKey(algoritmos));
    }
    
    @Test
    public void testRejeicaoPorFaltaDePreRequisitoAND() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Disciplina algoritmos = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);
        Disciplina estrutura = new DisciplinaObrigatoria("INF003", "Estrutura", 4);
        
        List<Disciplina> d = new ArrayList<>();
        d.add(poo);
        d.add(estrutura);
        algoritmos.adicionarValidador(new ValidadorLogicoAND(d));

        Aluno aluno = new Aluno("Rafael", "202501002", 20);
        aluno.adicionarDisciplinaConcluida(estrutura, 60);

        Turma turmaAlg = new Turma("T02", algoritmos, 10, "segunda 10h–12h");
        aluno.planejarTurma(turmaAlg);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que nao tenha algoritimos no historico pois ele so cumpriu um dos requisitos
        assertFalse(aluno.getHistorico().containsKey(algoritmos));
    }
    
     @Test
    public void testMatriculaValidaPorFaltaDePreRequisitoOR() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Disciplina algoritmos = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);
        Disciplina estrutura = new DisciplinaObrigatoria("INF003", "Estrutura", 4);
        
        List<Disciplina> d = new ArrayList<>();
        d.add(poo);
        d.add(estrutura);
        algoritmos.adicionarValidador(new ValidadorLogicoOR(d));

        Aluno aluno = new Aluno("Caio", "202501002", 20);
        aluno.adicionarDisciplinaConcluida(estrutura, 60);

        Turma turmaAlg = new Turma("T02", algoritmos, 10, "segunda 10h–12h");
        aluno.planejarTurma(turmaAlg);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que tenha algortimos no historico, pois ele ja conluiu pelo menos um dos requisitos
        assertTrue(aluno.getHistorico().containsKey(algoritmos));
    }
    
    @Test
    public void testRejeicaoPorFaltaDePreRequistoCreditosMinimos() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 9);
        Disciplina algoritmos = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);
        algoritmos.adicionarValidador(new ValidadorCreditosMinimos(10));

        Aluno aluno = new Aluno("Gustavo", "202501002", 20);
        aluno.adicionarDisciplinaConcluida(poo, 60);

        Turma turmaAlg = new Turma("T02", algoritmos, 10, "segunda 10h–12h");
        aluno.planejarTurma(turmaAlg);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        
        assertFalse(aluno.getHistorico().containsKey(algoritmos));
    }
    
    @Test
    public void testRejeicaoPorFaltaDePreRequisitoCursadoComNotaInferior() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Disciplina algoritmos = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);
        algoritmos.adicionarValidador(new ValidadorSimples(poo));

        Aluno aluno = new Aluno("Cleber", "202501002", 20);
        aluno.adicionarDisciplinaConcluida(poo, 20);

        Turma turmaAlg = new Turma("T02", algoritmos, 10, "segunda 10h–12h");
        aluno.planejarTurma(turmaAlg);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que nao tenha algoritimos no historico pois nao tem requisito poo, ja cursou mas nao tem nota > 60;
        assertFalse(aluno.getHistorico().containsKey(algoritmos));
    }

    @Test
    public void testRejeicaoPorFaltaDeCoRequisito() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Disciplina matematica = new DisciplinaObrigatoria("MAT001", "Matematica", 4);
        matematica.adicionarCoRequisito(poo);

        Aluno aluno = new Aluno("Lucas", "202501003", 20);
        Turma turmaMat = new Turma("T03", matematica, 10, "terça 08h–10h");
        aluno.planejarTurma(turmaMat);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que nao tenha algoritimo no historico pois nao tem co-requisito poo
        assertFalse(aluno.getHistorico().containsKey(matematica));
    }

    @Test
    public void testRejeicaoPorTurmaCheia() {
        Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
        Turma turmaPOO = new Turma("T04", poo, 0, "quarta 08h–10h");

        Aluno aluno = new Aluno("Lais", "202501004", 20);
        aluno.planejarTurma(turmaPOO);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que nao tenha poo no historico pois a turma nao tem vaga
        assertFalse(aluno.getHistorico().containsKey(poo));
    }
    
    @Test
    public void testRejeicaoPorCargaHorariaExcedida() {
        Disciplina d1 = new DisciplinaObrigatoria("D1", "Disciplina 1", 10);
        Disciplina d2 = new DisciplinaObrigatoria("D2", "Disciplina 2", 8);

        Turma t1 = new Turma("T07", d1, 10, "sexta 08h–12h");
        Turma t2 = new Turma("T08", d2, 10, "sexta 14h–18h");

        Aluno aluno = new Aluno("Luis", "202501006", 15); // limite: 15h
        aluno.planejarTurma(t1);
        aluno.planejarTurma(t2);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera que d2 nao esteja no historico pois ela excede a carga horaria
        assertFalse(aluno.getHistorico().containsKey(d2)); 
    }
    
    @Test
    public void testRejeicaoPorConflitoDeHorarioComPrioridadeObrigatoria() {
        Disciplina poo = new DisciplinaEletiva("INF001", "POO", 4);
        Disciplina alg = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);

        Turma t1 = new Turma("T05", poo, 10, "quinta 08h–10h");
        Turma t2 = new Turma("T06", alg, 10, "quinta 08h–10h");

        Aluno aluno = new Aluno("Carlos", "202501005", 20);

        aluno.planejarTurma(t1);
        aluno.planejarTurma(t2);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que tenha alg no historco e n tenha poo, pois tiveram conflito e alg tem prioridade
        assertTrue(aluno.getHistorico().containsKey(alg));
        assertFalse(aluno.getHistorico().containsKey(poo));
    }
    
    @Test
    public void testRejeicaoPorConflitoDeHorarioComPrioridadeEletiva() {
        Disciplina poo = new DisciplinaOptativa("INF001", "POO", 4);
        Disciplina alg = new DisciplinaEletiva("INF002", "Algoritmos", 4);

        Turma t1 = new Turma("T05", poo, 10, "quinta 08h–10h");
        Turma t2 = new Turma("T06", alg, 10, "quinta 08h–10h");

        Aluno aluno = new Aluno("Augusto", "202501005", 20);

        aluno.planejarTurma(t1);
        aluno.planejarTurma(t2);

        SistemaAcademico sistema = new SistemaAcademico();
        sistema.simularMatricula(aluno);
        
        //Espera-se que tenha alg no historco e n tenha poo, pois tiveram conflito e alg tem prioridade
        assertTrue(aluno.getHistorico().containsKey(alg));
         assertFalse(aluno.getHistorico().containsKey(poo));
    }

    @Test
    public void testRejeicaoPorConflitoDeHorarioSemPrioridade() {
    Disciplina poo = new DisciplinaObrigatoria("INF001", "POO", 4);
    Disciplina alg = new DisciplinaObrigatoria("INF002", "Algoritmos", 4);

    Turma t1 = new Turma("T05", poo, 10, "quinta 08h–10h");
    Turma t2 = new Turma("T06", alg, 10, "quinta 08h–10h");

    Aluno aluno = new Aluno("Anderson", "202501005", 20);

    aluno.planejarTurma(t1);
    aluno.planejarTurma(t2);

    SistemaAcademico sistema = new SistemaAcademico();
    sistema.simularMatricula(aluno);
    
    //Espera-se que nenhum das duas tenham sido aceitas
    assertEquals(0, aluno.getHistorico().size());
    }

}
