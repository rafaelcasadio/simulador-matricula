# 🎓 Simulador de Matrícula 

Simulação de um sistema de matrícula, em Java, que reproduz regras reais de inscrição em turmas de uma universidade.  
O projeto aplica orientação a objetos e testes automatizados com **JUnit**, permitindo criar disciplinas (obrigatórias, eletivas e optativas) com pré‑requisitos e co‑requisitos, turmas com horários e capacidade, e simular matrículas de alunos.

---

## 🚀 Funcionalidades

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
4. **Testes Automatizados (JUnit 5)**  
   - Cobertura de cenários principais e casos‑limite:
     - Matrícula bem‑sucedida
     - Rejeição por conflito de horário
     - Rejeição por falta de pré‑requisito ou co‑requisito
     - Prioridade de inscrição em disciplinas obrigatórias

---

## 📂 Estrutura do Projeto

```
simulador-matricula/
├── src/
│ ├── main/
│ │ └── java/
│ │ ├── model/
│ │ │ ├── Disciplina.java
│ │ │ ├── DisciplinaObrigatoria.java
│ │ │ ├── DisciplinaEletiva.java
│ │ │ ├── DisciplinaOptativa.java
│ │ │ ├── Aluno.java
│ │ │ └── Turma.java
│ │ └── service/
│ │ └── ControladorDeMatricula.java
│ └── test/
│ └── java/
│ └── test/
│ ├── MatriculaSucessoTest.java
│ ├── ConflitoHorarioTest.java
│ ├── PreRequisitoTest.java
│ └── PrioridadeObrigatoriaTest.java
└── pom.xml
```

---

## 🛠️ Tecnologias e Ferramentas

- **Java** (Orientação a Objetos)  
- **JUnit** (Teste unitário)  
- **Maven** (Gerenciamento de dependências e build)
---

## 🧪 Exemplos de Testes

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

📦 Como Executar <br>
1-Clone este repositório
```
git clone https://github.com/rafaelcasadio/simulador-matricula.git

```
2-Importe no IntelliJ/Eclipse/VS Code como projeto Maven

3-Compile e execute todos os testes JUnit:
```
mvn test

```
---

🙋‍♂️ Autor
Rafael Casadio Costa<br>
GitHub: rafaelcasadio<br>
LinkedIn: https://www.linkedin.com/in/rafael-casadio/



