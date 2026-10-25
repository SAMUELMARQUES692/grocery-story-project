package dev.samuel.projectgrocery.exception;

public class EstoqueInsuficienteException extends RegraNegocioException {
    public EstoqueInsuficienteException(Long produtoId, Integer estoqueQuantidade, int quantidade) {
        super("ESTOQUE_INSUFICIENTE", "Produto com o ID: " + produtoId + " possui apenas " + estoqueQuantidade + " em estoque, insuficinete para repor " + quantidade);
    }
}
