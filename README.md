# Kanbrunco

O **Kanbrunco** é uma aplicação desenvolvida em Java com Spring Boot, que implementa um sistema de organização de tarefas baseado em quadros, listas e cartões, semelhante ao site **Trello**.  

Seu funcionamento é estruturado em camadas, garantindo separação de responsabilidades e organização do código.  

---

## Funcionamento Geral  

A aplicação inicia pela classe principal **`KanbruncoApplication`**, responsável por carregar o Spring Boot e configurar os recursos necessários.  

O usuário interage por meio das páginas HTML, inserindo dados em formulários. Esses dados percorrem o seguinte fluxo:  

- **Controllers** → recebem as requisições vindas das páginas e direcionam para os services.  
- **Services** → processam os dados recebidos, aplicando as os métodos necessários. Como exemplo: o `UsuarioService` verifica se um usuário com um determinado email já existe antes de salvar.  
- **Repositories** → utilizam o **Spring Data JPA** para manipular o banco de dados.  
- **Models** → representam as entidades principais do sistema (`Usuario`, `Quadro`, `ListaTarefas`, `Cartao` e `Comentario`).  

Esse ciclo garante que o usuário consiga **criar quadros, adicionar listas, incluir cartões e registrar comentários** de forma contínua e organizada.  

---

## Como executar  

1. Execute o arquivo `KanbruncoApplication.java` como uma **Java Application**.  
2. Após a inicialização, abra no navegador:  
http://localhost:8080
3. A página inicial será exibida, permitindo cadastro e acesso às funcionalidades.  

## Testes com JUnit  

O projeto utiliza **JUnit** para validar a camada de persistência (`repositories`).  
- Testes de integração confirmam se entidades e relacionamentos são salvos e recuperados corretamente.  
- Há testes para deleção em cascata, garantindo que, ao excluir um quadro, todas as listas, cartões e comentários vinculados também sejam removidos.  
Isso evita inconsistências no banco de dados e garante integridade, evitando registros “órfãos”.  

## Padrão Factory  

O **Factory Method** foi aplicado nas classes que possuem herança, por exemplo a classe **Cartão** (`CartaoSimples` e `CartaoComChecklist`).  

### 🔹 Sem Factory  
Cada vez que fosse necessário criar um cartão, era preciso repetir código nos controllers ou services, instanciando manualmente cada tipo e configurando atributos como título, descrição e checklist.  

### 🔹 Com Factory  
Agora, basta solicitar ao Factory que será responsável pela criação de cartões.  

### Vantagens do uso do Factory no projeto

- **Centralização da criação**: toda a lógica de instanciar cartões fica em um único lugar.  
- **Redução de código duplicado**: não é preciso repetir a criação em cada Controller ou Service.  
- **Menor acoplamento**: os Controllers e Services não precisam conhecer as classes concretas (`CartaoSimples`, `CartaoComChecklist`).  
- **Extensibilidade**: se um novo tipo de cartão for adicionado, basta alterar o Factory, sem precisar mexer em várias partes do código.  
- **Segurança na inicialização**: garante que objetos sejam sempre criados corretamente.  
- **Manutenção mais simples**: alterações de lógica de criação são feitas em apenas um ponto do código.  

