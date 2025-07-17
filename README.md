# 🎓 Simulador de Matrícula 

Projeto de Orientação a Objetos (UFJF 2025).
Simulação de um sistema de matrícula, em Java, que reproduz regras reais de inscrição em turmas de uma universidade.  
O projeto aplica orientação a objetos e testes automatizados com **JUnit**, permitindo criar disciplinas (obrigatórias, eletivas e optativas) com pré‑requisitos e co‑requisitos, turmas com horários e capacidade, e simular matrículas de alunos.

---

# 🚀 Funcionalidades

1. **Modelagem de Disciplinas**  
   - **Obrigatórias**, **Eletivas** e **Optativas**  
   - Cada disciplina possui código, nome, créditos, e listas de:
     - **Pré‑requisitos** (disciplinas que devem ter sido cursadas)
     - **Co‑requisitos** (disciplinas que devem ser cursadas em conjunto)
2. **Criação de Turmas**  
   - Associa disciplina, horário (e.g. “quinta 08h–10h”) e capacidade de vagas
3. **Matrícula de Alunos**  
   - Verifica **conflicto de horários** entre turmas
   - Garante cumprimento de **pré‑requisitos** e **co‑requisitos**
   - Em caso de conflito de vaga, define **prioridade**:
     1. Disciplinas Obrigatórias  
     2. Disciplinas Eletivas  
     3. Disciplinas Optativas
4. **Testes Automatizados (JUnit)**  
   - Cobertura de cenários principais e casos‑limite:
     - Matrícula bem‑sucedida
     - Rejeição por conflito de horário
     - Rejeição por falta de pré‑requisito ou co‑requisito
     - Prioridade de inscrição em disciplinas obrigatórias



---
# 💻 Tecnologias e Execução

- Linguagem: **Java 21**
- Build: **Maven**
- Execução no terminal 

---
## ✅ Requisitos

- JDK 21 instalado
- Maven instalado

---
#  ▶️ Como executar

```bash
# Clonar o projeto
git clone https://github.com/rafaelcasadio/simulador-matricula.git
cd simulador-matricula

# Executar os testes
mvn test
```
---

#  🧪 Exemplos de Testes

-Matrícula aceita pois Joao já cursou o prerequisito poo
```java
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
```

-Matrícula negada pois Cleber não tem  prerequisito poo, ele ja cursou mas não obteve nota maior que 60.
```java
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
```

---

# 🙋‍♂️ Autor<br>
Rafael Casadio Costa<br>
GitHub: rafaelcasadio<br>
LinkedIn: https://www.linkedin.com/in/rafael-casadio/



