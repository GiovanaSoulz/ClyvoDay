# 🐾 ClyvoDay

**ClyvoDay** é uma solução digital que transforma o acompanhamento da saúde e do bem-estar do pet em uma experiência contínua, personalizada e mais íntima para o tutor.

A proposta é funcionar como um **diário digital da vida do animal**, no qual o tutor registra e acompanha momentos importantes da jornada do pet: alimentação, peso, atividades, medicações, consultas, vacinas, exames, conquistas e pequenas situações do dia a dia.

Em vez de enxergar o cuidado veterinário apenas como algo que acontece quando o animal está doente, o ClyvoDay busca fazer com que o tutor perceba que **cada pequeno registro também faz parte da história e da saúde do seu pet** — construindo, com o tempo, uma verdadeira linha do tempo da vida do animal.

Para incentivar a participação frequente, a aplicação usa **gamificação**: um sistema de pontuação e ranking premia a constância dos registros, incentivando o tutor a manter uma sequência de cuidados ao longo dos dias — sem transformar a saúde do pet em uma obrigação, mas em um hábito natural e prazeroso.

> Porque cuidar de um pet não acontece apenas quando ele precisa de um veterinário. Acontece todos os dias.

---

## 📋 Sumário

- [Funcionalidades](#-funcionalidades)
- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Arquitetura e estrutura do projeto](#-arquitetura-e-estrutura-do-projeto)
- [Perfis de usuário e permissões](#-perfis-de-usuário-e-permissões)
- [Pré-requisitos](#-pré-requisitos)
- [Como instalar e executar](#-como-instalar-e-executar)
- [Como acessar a aplicação](#-como-acessar-a-aplicação)
- [Usuários de teste](#-usuários-de-teste)
- [Endpoints principais](#-endpoints-principais)
- [Banco de dados e migrações](#-banco-de-dados-e-migrações)
- [Documentação da API (Swagger)](#-documentação-da-api-swagger)

---

## ✨ Funcionalidades

- **Cadastro e gestão de pets**: nome, espécie, raça, peso e demais informações do animal.
- **Cadastro e gestão de tutores** (restrito ao perfil veterinário).
- **Registro de atividades diárias** do pet (alimentação, água, cuidados gerais), formando o "diário digital" da jornada do animal.
- **Registro e gestão de medicações**, incluindo cadastro e exclusão pelo veterinário.
- **Dashboard** com visão consolidada dos cuidados do pet.
- **Ranking / gamificação**: pontuação por constância de registros, incentivando o hábito diário de cuidado.
- **Autenticação e autorização** com dois perfis distintos (Tutor e Veterinário), cada um com permissões específicas.
- **Área administrativa** exclusiva para o perfil Veterinário.
- **Documentação interativa da API** via Swagger/OpenAPI.

## 🛠 Tecnologias utilizadas

- **Java 17**
- **Spring Boot 4** (Spring Web / Web MVC, Spring Data JPA, Spring Security, Spring Cache)
- **Thymeleaf** (camada web/HTML server-side) + integração com Spring Security (`thymeleaf-extras-springsecurity6`)
- **H2 Database** (banco em memória)
- **Flyway** (versionamento e controle do schema do banco de dados)
- **Bean Validation** (`spring-boot-starter-validation`)
- **springdoc-openapi** (Swagger UI)
- **Maven** (gerenciador de dependências e build, via Maven Wrapper)
- **Spring Boot DevTools** (produtividade em ambiente de desenvolvimento)

## 🏗 Arquitetura e estrutura do projeto

O projeto segue a arquitetura em camadas tradicional do Spring Boot:

```
src/main/java/com/clyday/clyday_api/
├── config/          # Configurações (Security, Cache, Swagger, carga inicial de dados)
├── controller/       # Controllers REST e Thymeleaf (rotas web)
├── dto/               # Objetos de transferência de dados (DTOs)
├── entity/            # Entidades JPA (Pet, Tutor, Atividade, Medicacao)
├── exception/         # Tratamento global de exceções
├── repository/        # Repositórios Spring Data JPA
├── service/           # Regras de negócio
└── util/              # Estratégias de pontuação (padrão Strategy/Factory) para a gamificação

src/main/resources/
├── static/css/        # Estilos das páginas
├── templates/         # Páginas Thymeleaf (dashboard, pets, atividades, medicações, ranking, login)
└── db/migration/       # Scripts de migração do Flyway (versionamento do banco)
```

## 👤 Perfis de usuário e permissões

A aplicação possui dois perfis de usuário, cada um com um conjunto de permissões:

| Perfil | Pode fazer |
|---|---|
| **TUTOR** | Cadastrar/editar pets, registrar atividades (cuidados diários), visualizar medicações e acompanhar o ranking |
| **VETERINARIO** | Tudo que o Tutor faz, **além de**: gerenciar tutores, cadastrar/excluir medicações e acessar a área administrativa (`/admin`) |

## ✅ Pré-requisitos

Antes de começar, você precisa ter instalado:

- **JDK 17** ou superior ([Adoptium Temurin](https://adoptium.net/) ou equivalente)
- **Git**
- Uma IDE de sua preferência (o projeto foi desenvolvido usando **IntelliJ IDEA**)

> Não é necessário instalar o Maven manualmente: o projeto já inclui o **Maven Wrapper** (`mvnw` / `mvnw.cmd`).

## 🚀 Como instalar e executar

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU-USUARIO/clyday-api.git
cd clyday-api
```

### 2. Executar a aplicação

**Usando o Maven Wrapper (recomendado, sem precisar instalar o Maven):**

Linux/macOS:
```bash
./mvnw spring-boot:run
```

Windows:
```bash
mvnw.cmd spring-boot:run
```

**Ou, se preferir, pela IDE (IntelliJ IDEA):**

1. Abra o projeto no IntelliJ (`File > Open` e selecione a pasta `clyday-api`).
2. Aguarde o Maven baixar as dependências automaticamente.
3. Localize a classe `ClydayApiApplication.java` (pacote `com.clyday.clyday_api`).
4. Clique com o botão direito sobre ela e escolha **Run 'ClydayApiApplication'**.

A aplicação sobe por padrão na porta **8080**. Ao iniciar, o **Flyway** cria automaticamente o schema no banco H2 em memória e o `DataInitializer` cadastra um tutor inicial de exemplo.

## 🌐 Como acessar a aplicação

Com a aplicação em execução, acesse no navegador:

| Recurso | URL |
|---|---|
| Tela de login | http://localhost:8080/login |
| Dashboard (pós-login) | http://localhost:8080/dashboard |
| Console do banco H2 | http://localhost:8080/h2-console |
| Documentação da API (Swagger UI) | http://localhost:8080/swagger-ui/index.html |

### Configuração do H2 Console

Ao acessar `/h2-console`, utilize:

- **JDBC URL:** `jdbc:h2:mem:clyday`
- **User Name:** `sa`
- **Password:** *(em branco)*

## 🔑 Usuários de teste

A aplicação já vem com dois usuários pré-configurados em memória para fins de teste/demonstração:

| Usuário | Senha | Perfil |
|---|---|---|
| `tutor` | `1234` | TUTOR |
| `veterinario` | `1234` | VETERINARIO |

## 🔗 Endpoints principais

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/login` | Tela de login | Público |
| GET | `/dashboard` | Painel principal | Autenticado |
| GET / POST | `/pets`, `/pets/{id}`, `/pets/novo`, `/pets/{id}/editar`, `/pets/{id}/excluir` | CRUD de pets | Tutor / Veterinário |
| GET / POST / DELETE | `/tutores`, `/tutores/{id}`, `/tutores/buscar` | Gestão de tutores | Veterinário |
| GET / POST / DELETE | `/atividades`, `/atividades/pet/{petId}`, `/atividades/{id}`, `/atividades/nova`, `/atividades/cadastrar` | Registro de atividades (diário do pet) | Tutor / Veterinário |
| GET / POST | `/medicacoes`, `/medicacoes/novo`, `/medicacoes/{id}/deletar` | Gestão de medicações (cadastro/exclusão restrito ao Veterinário) | Tutor (visualizar) / Veterinário (gerenciar) |
| GET | `/ranking` | Ranking de gamificação | Tutor / Veterinário |
| GET | `/admin/**` | Área administrativa | Veterinário |
| GET | `/swagger-ui/**`, `/v3/api-docs/**` | Documentação da API | Público |

## 🗄 Banco de dados e migrações

O projeto utiliza **H2 em memória**, cujo schema é controlado inteiramente pelo **Flyway** (não pelo Hibernate — `ddl-auto=validate` apenas confere se as entidades batem com o schema já criado pelas migrations). Os scripts de migração ficam em `src/main/resources/db/migration`:

- `V1__criar_tabelas.sql`
- `V2__ajustar_atividades_e_medicacoes.sql`

> ⚠️ Por ser um banco em memória, **todos os dados são perdidos ao reiniciar a aplicação**, exceto o tutor inicial recriado automaticamente pelo `DataInitializer`.

## 📖 Documentação da API (Swagger)

Com a aplicação rodando, a documentação interativa dos endpoints REST fica disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 👨‍💻 Autor

Projeto desenvolvido como avaliação acadêmica.
