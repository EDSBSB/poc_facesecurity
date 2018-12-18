-- DROP TABLE public.licitacao;
CREATE TABLE conteiner
(
  id serial NOT NULL PRIMARY KEY,
  tipo character varying(100),
  dado json NOT NULL
)

