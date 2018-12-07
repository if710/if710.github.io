# IF710 - Programação para Dispositivos Móveis com Android

## Ciência da Computação, [Centro de Informática](http://www.cin.ufpe.br), ([UFPE](http://www.ufpe.br))

### Especificação do projeto

A ideia é que você utilize os conceitos vistos em sala para desenvolver um app em Kotlin, 
produzir um diagnóstico de funcionamento, e, potencialmente, fazer ajustes e refatoração 
no mesmo. 

*Segue o conjunto mínimo de tarefas esperadas como resultado do projeto, além da implementação:* 

01. Use o [Android Profiler](https://developer.android.com/studio/test/index.html), disponível em Android Studio 3.0 para avaliar a implementação do seu app, com relação a desempenho, memória, uso de rede, e bateria.
02. Além do Android Profiler, use também outras ferramentas para avaliar os tópicos listados, como [LeakCanary](https://github.com/square/leakcanary), [AndroidDevMetrics](https://github.com/frogermcs/androiddevmetrics), entre outros. 
03. Apresente, para cada um dos tópicos listados no ponto 2, exemplos de boas práticas que foram adotadas no desenvolvimento do seu app, ilustradas com trechos de código da sua implementação. Especifique se o código foi alterado com base na avaliação feita por meio do Android Profiler.

*Tarefas opcionais:* 

01. Crie uma suíte de testes para o seu app usando JUnit para testar classes isoladamente e Espresso ou uiautomator (escolha um) para testes de integração e interface. [Algumas dicas aqui](https://developer.android.com/studio/test/index.html).
02. Refatore o código do app para aplicação de _Architecture Components_, trocando a forma de acesso ao banco de dados para usar `Room`. Crie também ao menos um caso de uso de `LiveData`.

*Relatório*

Registre todos os passos realizados em arquivos .MD a serem disponibilizados no repositório do seu projeto, sendo um para cada tópico, ou seja: 

| Tópico | Nome do Arquivo |
| ------ | ------ |
| Descrição do Projeto | README.md |
| CPU & Performance | cpu.md |
| Consumo de Rede | bandwidth.md |
| Memória | memoria.md |
| Bateria | bateria.md |
| Testes | testes.md |
| _Architecture Components_ | archcomponents.md |