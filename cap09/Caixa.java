// T é um parâmetro de tipo: será definido por quem usar a classe
public class Caixa<T> {
    private T conteudo;

    public void guardar(T item) {
        this.conteudo = item;
    }

    public T abrir() {
        return conteudo;
    }

    public boolean vazia() {
        return conteudo == null;
    }
}
