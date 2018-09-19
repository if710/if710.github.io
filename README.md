# IF710 - Programação para Dispositivos Móveis com Android

## Ciência da Computação, [Centro de Informática](http://www.cin.ufpe.br), ([UFPE](http://www.ufpe.br))

### Instrutores

* **Professor** 
  * Leopoldo Motta Teixeira ([@leopoldomt](https://github.com/leopoldomt) --- lmt@cin)
  
### Horário e Local de Aulas

* Quarta (8h-10h), E112 
* Sexta (10h-12h), E112 

### Ementa

Este curso explora diversos princípios de desenvolvimento de aplicações para dispositivos móveis, usando a plataforma Android como estudo de caso.

### Bibliografia Sugerida

Não há livro texto obrigatório. Entretanto, a seguir estão alguns livros, sites, e newsletters que podem ser recomendados:

- [The Busy Coder’s Guide to Android Development - Mark Murphy](https://commonsware.com/Android/)
- [Android Programming: The Big Nerd Ranch Guide - Bill Phillips, Chris Stewart, Kristin Marsicano](https://www.bignerdranch.com/books/android-programming/)
- [Kotlin for Android Developers - Antonio Leiva](https://antonioleiva.com/kotlin-android-developers-book/)
- [Kotlin in Action - Dmitry Jemerov, Svetlana Isakova](https://www.manning.com/books/kotlin-in-action)
- [Android Weekly](http://androidweekly.net)
- [Android Reference](http://developer.android.com)
- [Android Developers Blog](http://android-developers.blogspot.com)

### Objetivos

- Motivar, apresentar, exercitar e consolidar o desenvolvimento de aplicações para dispositivos móveis, utilizando a plataforma Android como estudo de caso
- Estudar e desenvolver vários aplicativos simples no decorrer da disciplina para exercitar diferentes conceitos da plataforma
- Desenvolver um aplicativo como projeto da disciplina e usá-lo como estudo de caso;
- Compreender organização, padrões e mecanismos de programação da plataforma Android e estar apto a usá-los efetivamente para desenvolver sua própria aplicação;
- Usar ferramentas de desenvolvimento para criar, entender, depurar e otimizar aplicações Android;
- Compreender as características distintas e restrições que existem em dispositivos móveis e lidar com isto no contexto de aplicações Android;
- Estar apto a encontrar fontes adicionais de informação para entender e resolver problemas relacionados com desenvolvimento Android.

### Metodologia

Na disciplina, utilizaremos uma mistura de aulas tradicionais com exercícios e tarefas de programação em sala de aula e para casa. 

### Recursos

- [Slack](http://if710.slack.com)
- [Google Classroom](http://classroom.google.com) - Código: 9r6jm5

#### Ferramentas

* [Android Studio](https://developer.android.com/studio/index.html)

### Avaliação

* Exercícios diversos (50%)
  * Criação e avaliação de pequenos apps
  * Uso de ferramentas de análise de apps
  * Criação de Tutoriais de bibliotecas
* Projeto (50%)
  * A nota do projeto compreende não apenas a qualidade do resultado final, mas também acompanhamentos, respeito a prazos e datas de entrega, etc. 
  * [Insira seu grupo e o projeto a ser utilizado aqui](grupos.md)
  
- Observações:
  - Entrega fora do prazo: **redutor de 1 ponto por dia de atraso**. 
  - Atraso máximo: **1 (uma) semana**. Após esse prazo, será dada nota **zero** para a respectiva atividade de avaliação.
  - Trabalhos **“CTRL-C + CTRL-V”** terão nota **zero** (vale tanto para cópia de colegas, como para trabalhos copiados da internet).

### Plano de Ensino

**Atenção!** 
*Este plano de ensino está sujeito a alterações durante o semestre, visite frequentemente a página para obter a versão mais atualizada, ou acompanhe os updates no repositório.*

| # | Data | Assunto | Atividades |
|:---:|:----:|:----------------------:|:----------------------|
| 01 | 15.08.18 (qua) | [Apresentação e conceitos fundamentais de desenvolvimento de aplicações móveis](https://drive.google.com/open?id=1lwQVmKG9n-Dxbf9yufIt5sIkqx7Zi_jb) | --- |
| 02 | 17.08.18 (sex) | [Kotlin Basics](https://github.com/if710/kotlin-in-action) | --- |
| 03 | 22.08.18 (qua) | [Conceitos Básicos de Projetos Android - UI](#) | --- |
| 04 | 24.08.18 (sex) | [AdapterViews, RecyclerView](#) | --- |
| 05 | 29.08.18 (qua) | [Interação entre telas, ciclo de vida, intents](#) | [Exercício 1 lançado](https://github.com/if710/exercicio-calculadora) |
| 06 | 31.08.18 (sex) | [Threads, AsyncTasks, Permissions](#) | --- |
| 07 | 05.09.18 (qua) | [Exercício 2 lançado - Aula dedicada a praticar](https://github.com/if710/exercicio-rss) | *Deadline para entrega do Exercício 1* |
| -- | 07.09.18 (sex) | **FERIADO - Dia da Independência** | --- |
| 08 | 12.09.18 (qua) | [Services](#) | --- |
| 09 | 14.09.18 (sex) | [Data Management](#) | --- |
| 10 | 19.09.18 (qua) | [Exercício 3 lançado - Aula dedicada a praticar](https://github.com/if710/exercicio-rss-2) | *Deadline para entrega do Exercício 2* |
| 11 | 21.09.18 (sex) | [Tirar Dúvidas - Exercícios](#) | --- |
| 12 | 26.09.18 (qua) | [BroadcastReceivers & System Services](#) | --- |
| 13 | 28.09.18 (sex) | [Design Patterns](#) | --- |
| -- | 30.09.18 (dom) | *Deadline para entrega do Exercício 3* | --- |
| 14 | 03.10.18 (qua) | [Design Patterns](#) | --- |
| 15 | 05.10.18 (sex) | [Desenvolvendo um SDK Mobile (In Loco Media)](#) | --- |
| 16 | 10.10.18 (qua) | [APIs e Bibliotecas Externas úteis](#) | --- |
| -- | 12.10.18 (sex) | **FERIADO - Dia de Nossa Senhora Aparecida** | --- |
| 17 | 17.10.18 (qua) | [Processamento e Desempenho](#) | --- |
| 18 | 19.10.18 (sex) | [Memória](#) | --- |
| 19 | 24.10.18 (qua) | [Energia](#) | --- |
| 20 | 26.10.18 (sex) | [Consumo de Rede](#) | --- |
| 21 | 31.10.18 (qua) | [Aula convidada (a confirmar)](#) | --- |
| -- | 02.11.18 (sex) | **FERIADO - Dia de Finados** | --- |
| 22 | 07.11.18 (qua) | [Testes](#) | --- |
| 23 | 09.11.18 (sex) | [Testes](#) | --- |
| 24 | 14.11.18 (qua) | [Acompanhar projeto](#) | --- |
| 25 | 16.11.18 (sex) | [Acompanhar projeto](#) | --- |
| 26 | 21.11.18 (qua) | [Acompanhar projeto](#) | --- |
| 27 | 23.11.18 (sex) | [Acompanhar projeto](#) | --- |
| 28 | 28.11.18 (qua) | [Acompanhar projeto](#) | --- |
| 29 | 30.11.18 (sex) | [Acompanhar projeto](#) | --- |
| 30 | 05.12.18 (qua) | [Acompanhar projeto](#) | --- |
| 31 | 07.12.18 (sex) | [Acompanhar projeto](#) | --- |
| 32 | 12.12.18 (qua) | **Entrega do Projeto**  | --- |
| 33 | 14.12.18 (sex) | **Entrega do Projeto**  | --- |
