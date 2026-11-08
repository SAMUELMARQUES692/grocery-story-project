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

    private Integer calculoCodigoBarras(String codigo) {
        Produto produto = produtoRepository.findByCodigoProduto(codigo)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com codigo: " + codigo));

        String codigoBrasil = "789";
        String codigoEmpresa = "426";
        String codigoProduto = produto.getCodigoProduto();

        String codigoConcatenado = codigoBrasil + codigoEmpresa + codigoProduto;

        if (codigoConcatenado.length() != 12 || !codigoConcatenado.matches("\\d+")) {
            throw new IllegalArgumentException("Código deve ter 12 dígitos numéricos: " + codigoConcatenado);
        }

        int soma = 0;
        for (int i = 0; i < codigoConcatenado.length(); i++) {
            int digito = codigoConcatenado.charAt(i) - '0';   // converte o caractere em número
            int peso = (i % 2 == 0) ? 1 : 3;                  // posições pares: peso 1, ímpares: peso 3
            soma += digito * peso;
        }

        return (10 - (soma % 10)) % 10;   // dígito verificador
    }

    private String gerarCodigoDeBarras(String codigo) {
       Produto produto = produtoRepository.findByCodigoProduto(codigo)
               .orElseThrow(() -> new RuntimeException("Produto não encontrado com codigo: " + codigo));

       String codigoBrasil = "789";
       String codigoEmpresa = "426";
       String codigoProduto = produto.getCodigoProduto();
       String digitoVarificacao = String.valueOf(calculoCodigoBarras(codigo));

       return codigoBrasil + codigoEmpresa + codigoProduto + digitoVarificacao;
    }
}
