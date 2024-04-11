package br.com.agenciaviagens;


import java.util.ArrayList;

/**
 * Cria e exibe opcoes de menu. 
 */
public class Menu {
    
    private String[] opcoes;
    
    /**
     * Construtor.
     * 
     * @param opcoes Opcoes do menu.
     */
    public Menu(String[] opcoes){     
        this.opcoes = opcoes;
    }
    
    /**
     * Construtor.
     * 
     * @param opcoes Opcoes do menu.
     */
    public Menu(ArrayList<String> opcoes){
        
        this.opcoes = new String[opcoes.size()];
        for (int i = 0; i< this.opcoes.length; i++){
            this.opcoes[i] = opcoes.get(i).toString();
        }
    }
    
    /**
     * Exibe o menu com as suas opcoes.
     * 
     * @param cabecalho Cabecalho do menu.
     * 
     * @return Texto do menu.
     */
    public String exibeMenu(String cabecalho){
        
        String menu = "***** " + cabecalho + " ******** \n";
        
        for (int cont = 0; cont < opcoes.length; cont++){
            int num = cont + 1;
            menu = menu.concat("\n" + num +") " + opcoes[cont] + ".");
        }
        
        return menu;
    }
}
