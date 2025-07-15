package ufjf.simulador.disciplinas;

public class DisciplinaEletiva extends Disciplina{
    
    public DisciplinaEletiva(String codigo, String nome, int cargaHorariaSemanal) {
        super(codigo, nome, cargaHorariaSemanal);
    }

    @Override
    public int getPrioridade() {
        return 2;
    }
}