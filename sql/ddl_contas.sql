-- DDL para tabela CONTA
CREATE TABLE conta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(20),
  descricao VARCHAR(255),
  valor DECIMAL(10,2),
  data_vencimento DATE,
  status VARCHAR(20)
);
