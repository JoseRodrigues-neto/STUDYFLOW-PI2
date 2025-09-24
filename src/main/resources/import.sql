

-- Inserindo Atividades
INSERT INTO atividade(id, titulo, descricao, dataInicio, dataFim, status) VALUES(1, 'Estudar Limites', 'Capítulo 2 do livro guia. Focar em limites laterais.', '2025-09-24', '2025-09-26', 'PENDENTE');
INSERT INTO atividade(id, titulo, descricao, dataInicio, dataFim, status) VALUES(2, 'Resolver Exercícios de Derivadas', 'Lista de exercícios 3, itens 1 a 10.', '2025-09-27', '2025-09-30', 'PENDENTE');
INSERT INTO atividade(id, titulo, descricao, dataInicio, dataFim, status) VALUES(3, 'Implementar Bubble Sort', 'Criar uma função em Java que implemente o algoritmo Bubble Sort.', '2025-09-25', '2025-09-25', 'EM_ANDAMENTO');

-- Inserindo Anotações
-- Anotação para a atividade "Estudar Limites" (atividade_id = 1)
INSERT INTO anotacao(id, conteudo, atividade_id) VALUES(1, 'Lembrar do teorema do confronto, pode cair na prova.', 1);
-- Anotações para a atividade "Resolver Exercícios de Derivadas" (atividade_id = 2)
INSERT INTO anotacao(id, conteudo, atividade_id) VALUES(2, 'Revisar a regra da cadeia antes de começar os exercícios.', 2);
INSERT INTO anotacao(id, conteudo, atividade_id) VALUES(3, 'Os exercícios 9 e 10 são os mais difíceis, pedir ajuda se necessário.', 2);
