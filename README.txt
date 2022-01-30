# s3withspring

Descrição
---------
Este repositório faz parte do curso "Criando um Microsserviço de Upload de Imagens com o Amazon S3" 
disponível na DIO - Digital Innovation One. O objetivo é apresentar ao aluno o primeiro contato com microserviços implementando 
a criação de um bucket na AWS S3, para posteriormente realizar o upload de imagens. A conexão com o servidor em nuvem é realizada 
pelo uso das access key e secret key que devem ser criadas via console na AWS. O arquivo AmazonConfig.java contem a classe 
relacionada ao acesso remoto. Neste projeto foi utilizado a versão 17 do Java.

Página para criar seu projeto spring-boot: https://start.spring.io/

No primero pacote utilizamos o framework spring boot com aws-java-sdk-s3 com intuito de criar um programa de upload via linha de comando. No segundo pacote o programa metódos HTTP foram definidos no Controller para executar as ações sobre os buckets e objetos que os compõem.

O código está organizado como segue:

Conteúdo
--------

Package s3withspring
  ->Config
    AmazonConfig.java
  ->ServiceBucket
    BucketBasicOperations.java
  ->ServiceObjects
    ObjectsServices.java
  S3withspringApplication.java

Package UploadImagesAPI
  ->Config
    AcessConfig.java
  ->Controller
    Controller.java
  UploadMainApplication.java
