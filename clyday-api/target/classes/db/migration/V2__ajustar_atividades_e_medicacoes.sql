-- A entidade Atividade evoluiu (ganhou "descricao" e passou a usar
-- "dataHora" como LocalDateTime) mas a V1 só criava "tipo", "data" e
-- "pontos". Este script alinha a tabela ao mapeamento JPA atual.
ALTER TABLE atividades ADD COLUMN IF NOT EXISTS descricao VARCHAR(500);
ALTER TABLE atividades ADD COLUMN IF NOT EXISTS data_hora TIMESTAMP;

-- Migra os dados antigos da coluna "data" (DATE) para "data_hora"
-- (TIMESTAMP), assumindo meia-noite para o horário.
UPDATE atividades
SET data_hora = CAST(data AS TIMESTAMP)
WHERE data_hora IS NULL AND data IS NOT NULL;

ALTER TABLE atividades DROP COLUMN IF EXISTS data;

-- A entidade Medicacao ganhou "dosagem" (String) e "obrigatoria"
-- (Boolean), e "horario" passou a ser tratado como texto (ex: "08:00"),
-- não mais como TIME.
ALTER TABLE medicacoes ADD COLUMN IF NOT EXISTS dosagem VARCHAR(255);
ALTER TABLE medicacoes ALTER COLUMN horario VARCHAR(50);
ALTER TABLE medicacoes ALTER COLUMN obrigatoria SET DEFAULT FALSE;
