

public interface IProdutoFabricado extends IProduto {

    int getNumeroIngredientes();
    IProduto getIngrediente(int numero);

    @Override
    default float getCusto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCusto'");
    }
}