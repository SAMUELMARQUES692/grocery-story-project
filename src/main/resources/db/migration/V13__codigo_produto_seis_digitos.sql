ALTER TABLE produtos ALTER COLUMN codigo_do_produto TYPE VARCHAR(6);
ALTER SEQUENCE codigo_produto_seq MAXVALUE 999999;

UPDATE produtos SET codigo_do_produto = LPAD(codigo_do_produto, 6, '0');