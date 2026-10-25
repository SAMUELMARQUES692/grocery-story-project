ALTER TABLE movimentacoes
    ADD COLUMN gondola_id BIGINT REFERENCES gondolas(id);

-- gondola_id obrigatoria quando local = GONDOLA, e proibida quando local = DEPOSITO
ALTER TABLE movimentacoes
    ADD CONSTRAINT ck_movimentacao_gondola
        CHECK ((local_produto = 'GONDOLA') = (gondola_id IS NOT NULL));

CREATE INDEX idx_movimentacoes_gondola_id ON movimentacoes(gondola_id);