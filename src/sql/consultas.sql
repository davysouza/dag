-- SELECT ??
SELECT i.name, i.type FROM sys.indexes I
	INNER JOIN sysobjects T ON T.id = I.object_id
WHERE upper(T.name) = 'labBD'

-- INDEX
CREATE INDEX despesavalor  ON despesa(valor DESC);
-- DROP INDEX despesavalor;

-- TESTA INDEX
EXPLAIN ANALYZE
	SELECT valor FROM despesa WHERE valor = 5000  ORDER BY valor DESC;

-- Essa busca retorna um ranking dos valores de cada despesa de cada subdominio. Campos: despesa.valor, subdominio.descricao.
-- (Maiores que um valor)
--explain analyze
SELECT valor, descricao FROM despesa, subdominio WHERE despesa.codigosubdominio = subdominio.codigo AND valor > <num>  ORDER BY valor DESC;

-- Essa busca retorna a soma das despesas de cada tipo de licitação, filtradas por uma busca relativa do campo tipolicitacao.descrição. Campos: SUM(valor), tipolicitacao.descricao.
SELECT * FROM (
	SELECT ROUND(SUM(valor), 2), descricao FROM despesa, tipolicitacao 
		WHERE despesa.codigotipolicitacao = tipolicitacao.codigo GROUP BY tipolicitacao.codigo) AS foo 
WHERE foo.descricao LIKE '%tra%';


-- HOJE 21/5 projeto fase 2

DROP TABLE despxsub
DROP VIEW despxsub

CREATE TEMPORARY TABLE despxsub(descricao, valor) AS
	SELECT descricao, valor FROM despesa, subdominio WHERE despesa.codigosubdominio = subdominio.codigo;

CREATE FUNCTION subdominio(num real) RETURNS TABLE (descr varchar(100), val numeric) AS $$
BEGIN
	RETURN QUERY	
		--SELECT descricao, valor FROM despesa, subdominio WHERE despesa.codigosubdominio = subdominio.codigo AND valor > num  ORDER BY valor DESC;
		SELECT * FROM despxsub WHERE valor > num ORDER BY valor;
END;
$$ Language plpgsql;


CREATE FUNCTION total_tipolic(d varchar(100)) RETURNS TABLE (descr varchar(100), val numeric) AS $$
BEGIN
	RETURN QUERY
		select * from (
			select descricao, ROUND(SUM(valor), 2) from despesa, tipolicitacao where despesa.codigotipolicitacao = tipolicitacao.codigo group by tipolicitacao.codigo) as foo 
			where foo.descricao like d;
END;
$$ Language plpgsql;

EXPLAIN ANALYZE
SELECT * FROM subdominio(0);
--SELECT * FROM total_tipolic('%%') ORDER BY val DESC;
--DROP FUNCTION subdominio(num real); 287.514/189
--DROP FUNCTION total_tipolic(d varchar(100));
