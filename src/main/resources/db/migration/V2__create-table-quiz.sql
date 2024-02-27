CREATE TABLE quiz (
	id_pergunta SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	pergunta VARCHAR(250) NOT NULL,
	resposta VARCHAR(250) NOT NULL,
	fk_usuario INTEGER REFERENCES usuarios(id_usuario)
);