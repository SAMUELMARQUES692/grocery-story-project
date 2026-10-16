package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Fornecedor;
import dev.samuel.projectgrocery.exception.CnpjDuplicadoException;
import dev.samuel.projectgrocery.exception.EmailDuplicadoException;
import dev.samuel.projectgrocery.exception.FornecedorNotFoundException;
import dev.samuel.projectgrocery.mapper.FornecedorMapper;
import dev.samuel.projectgrocery.repository.FornecedorRepository;
import dev.samuel.projectgrocery.request.FornecedorRequest;
import dev.samuel.projectgrocery.response.FornecedorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    @Transactional
    public FornecedorResponse criarFornecedor(FornecedorRequest request) {
        String email = request.email().trim().toLowerCase();

        if (fornecedorRepository.existsByCnpj(request.cnpj())) {
            throw new CnpjDuplicadoException(request.cnpj());
        }

        if (fornecedorRepository.existsByEmail(email)) {
            throw new EmailDuplicadoException(email);
        }

        Fornecedor fornecedor = fornecedorMapper.toFornecedor(request);
        fornecedor.setEmail(email);
        Fornecedor salvar = fornecedorRepository.save(fornecedor);
        return fornecedorMapper.toFornecedorResponse(salvar);
    }

    public List<FornecedorResponse> listarFornecedores() {
        return fornecedorRepository.findAll().stream()
                .map(fornecedorMapper::toFornecedorResponse)
                .toList();
    }

    public FornecedorResponse atualizarFornecedor(Long id, FornecedorRequest request) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));

        fornecedorMapper.atualizarFornecedor(request, fornecedor);
        fornecedor.setEmail(request.email());
        fornecedor.setCnpj(request.cnpj());
        Fornecedor salvar = fornecedorRepository.save(fornecedor);
        return fornecedorMapper.toFornecedorResponse(salvar);
    }

    public void deletarFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));
        fornecedorRepository.delete(fornecedor);
    }
}
