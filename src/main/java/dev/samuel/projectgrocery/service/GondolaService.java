package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Gondola;
import dev.samuel.projectgrocery.exception.CodigoGondolaDuplicadoException;
import dev.samuel.projectgrocery.exception.GondolaNotFoundException;
import dev.samuel.projectgrocery.mapper.GondolaMapper;
import dev.samuel.projectgrocery.repository.GondolaRepository;
import dev.samuel.projectgrocery.request.GondolaRequest;
import dev.samuel.projectgrocery.response.GondolaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GondolaService {

    private final GondolaRepository gondolaRepository;
    private final GondolaMapper gondolaMapper;

    @Transactional
    public GondolaResponse criarGondola(GondolaRequest request) {
        if (gondolaRepository.existsGondolaByCodigo(request.codigo())) {
            throw new CodigoGondolaDuplicadoException(request.codigo());
        }

        Gondola gondola = gondolaMapper.toGondola(request);
        Gondola salvar = gondolaRepository.save(gondola);
        return gondolaMapper.toGondolaResponse(salvar);
    }

    public List<GondolaResponse> listarGondolas() {
        return gondolaRepository.findAll().stream()
                .map(gondolaMapper::toGondolaResponse)
                .toList();
    }

    public GondolaResponse atualizarGondola(Long id, GondolaRequest request) {
        Gondola gondola = gondolaRepository.findById(id)
                .orElseThrow(() -> new GondolaNotFoundException(id));

        gondolaMapper.atualizarGondola(request, gondola);
        Gondola salvar = gondolaRepository.save(gondola);
        return gondolaMapper.toGondolaResponse(salvar);
    }

    public void deletarGondola(Long id) {
        Gondola gondola = gondolaRepository.findById(id)
                .orElseThrow(() -> new GondolaNotFoundException(id));
        gondolaRepository.delete(gondola);
    }
}
