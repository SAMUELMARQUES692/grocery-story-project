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
       String codigoProduto = gerarCodigoProduto();

        if (produtoRepository.existsByCodigoBarras(request.codigoBarras())) {
            throw new CodigoBarrasDuplicadoException(request.codigoBarras());
        }

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(request.categoriaId()));

        Fornecedor fornecedor = fornecedorRepository.findById(request.fornecedorId())
                .orElseThrow(() -> new FornecedorNotFoundException(request.fornecedorId()));

       Produto produto = produtoMapper.toProduto(request);
       produto.setCategoria(categoria);
       produto.setFornecedor(fornecedor);
       produto.setCodigoProduto(codigoProduto);
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

    public String gerarCodigoProduto() {
        Long codigo;
        try {
            codigo = produtoRepository.proximoCodigo();
        } catch (DataAccessException e) {
            throw new LimiteSequencialException();
        }
        return String.format("%04d", codigo);
    }
}
