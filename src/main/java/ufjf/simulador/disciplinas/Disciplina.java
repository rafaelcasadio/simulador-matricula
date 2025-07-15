package ufjf.simulador.disciplinas;

import ufjf.simulador.validadores.ValidadorPreRequisito;
import java.util.ArrayList;
import java.util.List;

public abstract class Disciplina {
    
    private String codigo;
    private String nome;
    private int cargaHorariaSemanal;

    private List<ValidadorPreRequisito> validadoresPreRequisitos;
    private List<Disciplina> coRequisitos;

    public Disciplina(String codigo, String nome, int cargaHorariaSemanal) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
        this.validadoresPreRequisitos = new ArrayList<>();
        this.coRequisitos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public List<Disciplina> getCoRequisitos() {
        return coRequisitos;
    }

    public void adicionarCoRequisito(Disciplina disciplina) {
        coRequisitos.add(disciplina);
    }

    public void adicionarValidador(ValidadorPreRequisito validador) {
        validadoresPreRequisitos.add(validador);
    }

    public List<ValidadorPreRequisito> getValidadores() {
        return validadoresPreRequisitos;
    }

    public abstract int getPrioridade();
    
    
}
