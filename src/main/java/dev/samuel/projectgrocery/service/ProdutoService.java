package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Categoria;
import dev.samuel.projectgrocery.entity.Fornecedor;
import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.exception.*;
import dev.samuel.projectgrocery.mapper.ProdutoMapper;
import dev.samuel.projectgrocery.repository.CategoriaRepository;
import dev.samuel.projectgrocery.repository.FornecedorRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.ProdutoRequest;
import dev.samuel.projectgrocery.response.ProdutoResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoMapper produtoMapper;

   @Transactional
    public ProdutoResponse criarProduto(ProdutoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(request.categoriaId()));

        Fornecedor fornecedor = fornecedorRepository.findById(request.fornecedorId())
                .orElseThrow(() -> new FornecedorNotFoundException(request.fornecedorId()));

       String codigoProduto = gerarCodigoProduto();
       String codigoDeBarras = gerarCodigoDeBarras(codigoProduto);

       Produto produto = produtoMapper.toProduto(request);
       produto.setCategoria(categoria);
       produto.setFornecedor(fornecedor);
       produto.setCodigoProduto(codigoProduto);
       produto.setCodigoBarras(codigoDeBarras);

       Produto salvar = produtoRepository.save(produto);
       return produtoMapper.toProdutoResponse(salvar);
    }

    public List<ProdutoResponse> listarProdutos() {
       return produtoRepository.findAll().stream()
               .map(produtoMapper::toProdutoResponse)
               .toList();
    }

    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
       Produto produto = produtoRepository.findById(id)
               .orElseThrow(() -> new ProdutoNotFoundException(id));

       produtoMapper.atualizarProduto(request, produto);

       Produto salvar = produtoRepository.save(produto);
       return produtoMapper.toProdutoResponse(salvar);
    }

    public void deletarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        produtoRepository.delete(produto);
    }

    private String gerarCodigoProduto() {
        Long codigo;
        try {
            codigo = produtoRepository.proximoCodigo();
        } catch (DataAccessException e) {
            throw new LimiteSequencialException();
        }
        return String.format("%06d", codigo);
    }

    private static final String PREFIXO_USO_INTERNO = "200000"; // GS1: 20-29 = uso interno da loja

    private String gerarCodigoDeBarras(String codigoProduto) {
        String base = PREFIXO_USO_INTERNO + codigoProduto; // 6 + 6 = 12 digitos

        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            int digito = base.charAt(i) - '0';
            int peso = (i % 2 == 0) ? 1 : 3;
            soma += digito * peso;
        }
        int digitoVerificador = (10 - (soma % 10)) % 10;

        return base + digitoVerificador; // EAN-13
    }
}
