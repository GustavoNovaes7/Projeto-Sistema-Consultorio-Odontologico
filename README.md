# Sistema para um Consultório Odontológico

Projeto Integrador desenvolvido para o curso de Desenvolvimento de Sistemas pelo SENAC-EAD.

## Status do Projeto
Concluído

## Tecnologias Aplicadas
- Java
- Java Swing
- JDBC
- MySQL

## Time de Desenvolvedores
Gustavo Novaes de Souza

## Usuários do Sistema
- Gerente: Tem acesso a todo sistema, podendo gerenciar cada setor do 
consultório.

- Cirurgiões Dentistas: Consultam seus próprios dados e conseguem registrar e 
gerenciar atendimentos e dados dos pacientes. Terão uma ficha para cada 
paciente já atendido, essa ficha é de exclusividade dos cirurgiões dentistas, 
somente eles e o gerente poderão acessá-la.

- Auxiliares: Graduados em Técnico em Saúde Bucal. Auxiliam os cirurgiões 
dentistas quando necessário e ficam encarregados principalmente com 
manuseamento, quantificação e controle dos materiais utilizados nos 
atendimentos, fazendo atualizações e controle de estoque.

- Atendentes / Secretários(as): Podem acessar os dados de consultas e pacientes, 
podendo criar novos registros de ambos. Também possuem acesso aos dados 
de cirurgiões dentistas, porém apenas para consultá-los.

- Pacientes: Tem acesso ao histórico de consultas do qual receberam 
atendimentos e podem realizar o requerimento para serem atendidos em novas 
consultas através da web, telefone ou presencialmente. Também podem ter 
acesso aos pagamentos já realizados por eles próprios.


## Funcionalidades do Sistema

### Requisitos Funcionais
[RF001] – Registro de cirurgiões dentistas
Ator: Gerente
Descrição: Cadastrar, acessar e atualizar registros de cirurgiões dentistas que 
farão parte da equipe do consultório. Os registros serão: Nome, nascimento, 
CPF, CRO, especialidade, endereço, telefone e e-mail.

[RF002] – Registro de pacientes
Ator: Gerente e Atendentes
Descrição: Cadastrar, acessar e atualizar registros de pacientes com as 
seguintes informações: Nome, nascimento, CPF, RG, endereço, telefone e e-mail e indicação (por onde conheceu o consultório: Rede social, panfletagem ou 
por um conhecido. Caso tenha conhecido por uma pessoa, registrar o nome da 
mesma). Manter um histórico de alteração nos registros, indicando o usuário que 
realizou a mudança.

[RF003] – Registro de atendimentos/consultas
Ator: Gerente, Atendentes e Cirurgiões Dentistas
Descrição: Cadastrar, acessar e atualizar atendimentos que foram e serão 
realizadas, contendo os seguintes registros: data e hora, valor, dados do 
paciente, dados do cirurgião dentista, dados do convenio (caso o paciente 
possua) e a especialidade procurada pelo paciente (caso tenha escolhido 
alguma). Manter um histórico de alteração nos registros, indicando o usuário que 
realizou a mudança.
Obs.: Em alguns casos os pacientes chegam ao consultório e o cirurgião dentista 
responsável por seu atendimento ainda está ocupado em outro atendimento. 
Caso queira, o paciente pode ser atendido por outro cirurgião dentista que esteja 
livre e possua a mesma especialidade necessária para o atendimento. Logo, no 
registro dos atendimentos, serão necessários os dados do cirurgião dentista que 
era previsto para realizar a consulta e o cirurgião dentista que de fato executou 
o atendimento. 

[RF004] – Registro de materiais
Ator: Gerente, Auxiliares
Descrição: Cadastrar, acessar e atualizar os materiais da clínica. Fazendo um 
registro do nome do material, marca, quantidade comprada, quantidade em 
estoque, quantidade gasta, valor unitário e valor total. Manter um histórico de 
alteração nos registros, indicando o usuário que realizou a mudança.

[RF005] – Registro financeiro
Ator: Gerente
Descrição: Cadastrar, acessar e atualizar valores de atendimentos, gastos 
salariais, gastos do imóvel, gastos dos materiais, gastos do próprio sistema 
desktop e web. Os pagamentos dos atendimentos devem indicar como foi a 
forma de pagamento (cartão, dinheiro, etc.) e número de parcelas. Manter um 
histórico de alteração nos registros, indicando o usuário que realizou a mudança.

[RF006] – Agenda de atendimentos
Ator: Gerente, Cirurgiões Dentistas e Atendentes
Descrição: Agenda que indique as datas das consultas previstas e os cirurgiões
dentistas que irão realizá-las.

[RF007] – Odontograma
Ator: Gerente, Cirurgiões Dentistas
Descrição: Um ‘mapa’ dos dentes com seus respectivos números, que poderá 
ser associado a ficha dos atendimentos indicando qual ou quais dentes do 
paciente necessitam de tratamento ou atenção.

### Requisitos Não Funcionais
[RNF001] - Autenticação por meio de log-in
Autenticação e grupos de usuários. Importante que apenas pessoas autorizadas tenham 
acesso ao sistema. Gerente deve ter acesso total; os demais terão acesso apenas 
aos seus próprios recursos.


## Demonstração do Sistema
https://github.com/GustavoNovaes7/Projeto-Sistema-Consultorio-Odontologico/assets/99483701/e80d59f9-542b-470c-b743-24cd9709413c

