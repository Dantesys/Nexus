package org.dantesys.nexus.utilidade;

public enum NexusTipos {
    NEXUS("nexus",0),
    SOLAR("solar",1),
    LUNAR("lunar",2),
    NETHER("nether",3),
    ENDER("ender",4);
    private final String nome;
    private final int tipo;
    NexusTipos(String nome, int tipo) {
        this.nome=nome;
        this.tipo=tipo;
    }
    public int getTipo(){
        return tipo;
    }
    public String getNome(){
        return nome;
    }
    public static String getNomeTipo(int tipo){
        for(NexusTipos t: values()){
            if(t.getTipo()==tipo){
                return t.getNome();
            }
        }
        return NEXUS.getNome();
    }
    public static int getTipoNome(String nome){
        for(NexusTipos t: values()){
            if(t.getNome().equals(nome)){
                return t.getTipo();
            }
        }
        return NEXUS.getTipo();
    }
}
