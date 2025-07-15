package ufjf.simulador.disciplinas;

public class DisciplinaObrigatoria extends Disciplina{
    
    public DisciplinaObrigatoria(String codigo, String nome, int cargaHorariaSemanal) {
        super(codigo, nome, cargaHorariaSemanal);
    }

    @Override
    public int getPrioridade() {
        return 3;
    }
}