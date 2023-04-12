package ListImplement;

import java.util.Arrays;

public class ListaVetor {

    private String[] itens;

    public ListaVetor() {
        itens = new String[1];
    }

    public void adicionar(String elemento) {
        int i;
        for (i=0; i<itens.length; i++){
            if (this.itens[i] == null){
                this.itens[i] = elemento;
                break;
            }
        }
        
        if (i == this.itens.length - 1) {
            int aux = this.itens.length + 1;
            itens = Arrays.copyOf(itens, aux);
        }
    }

    public int contar() {
        int aux = 0;

        for (int i=0; i<this.itens.length; i++)
            if (this.itens[i] != null) aux++;

        return aux;
    }

    public boolean contem(String elemento) {
        for (int i=0; i<this.itens.length; i++)
            if (this.itens[i] == elemento) return true;
        
        return false;
    }

    public boolean remove(String elemento) {
        for (int i=0; i<this.itens.length; i++)
            if (this.itens[i] == elemento) {
                for(int j=i; j<this.itens.length; j++)
                    if (j != this.itens.length-1) this.itens[j] = this.itens[j+1];
                    else this.itens[j] = null;
                return true;
            }
        
        return false;
    }

    public String remove(int posicao) throws IndexOutOfBoundsException {
        if (posicao >= this.itens.length)
            throw new IndexOutOfBoundsException();
        else {
            String aux = this.itens[posicao];
            for(int j=posicao; j<this.itens.length; j++)
                if (j != this.itens.length-1) this.itens[j] = this.itens[j+1];
                else this.itens[j] = null;
            return aux;
        }
    }

    public void listar() {
        
    }

    public int indiceDe(String elemento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indiceDe'");
    }

    public String obter(int posicao) throws IndexOutOfBoundsException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obter'");
    }
    
}
