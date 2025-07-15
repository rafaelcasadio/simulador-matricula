package ufjf.simulador.disciplinas;

public class DisciplinaOptativa extends Disciplina{
    
    public DisciplinaOptativa(String codigo, String nome, int cargaHorariaSemanal) {
        super(codigo, nome, cargaHorariaSemanal);
    }

    @Override
    public int getPrioridade() {
        return 1;
    }
}