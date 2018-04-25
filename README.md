## Atividade Colaborativa

Aluno: Rodrigo Bento Rodrigues

### Descrição

 É notório a necessidade de um sistema de autenticação nas mais diversas aplicações. Como este parece ser um requisito básico e recorrente não faz sentido ficar construindo um sistema deste para cada projeto e por isto este projeto foi proposto. A proposta é criar um sistema de autenticação que possibilite as seguintes funcionalidades:

- RF1: Uma aplicação de terceiro (provedor de autenticação de terceiro) pode se cadastrar neste sistema usando apenas um token de verificação (palavra chave informada pelo provedor) e uma uri de consulta. Esta uri de consulta será usada para realizar uma requisição de autenticação do usuário informando email, senha e token de verificação. 

- RF2: O sistema deverá prover um sistema próprio de autenticação baseado em email e senha. Tal sistema deverá ser cadastrado como se fosse uma aplicação de terceiro.

- RF3: A autenticação de uma aplicação cliente será confirmada com o envio de um token criado pelo sistema de autenticação, anexado aos dados do provedor no sistema de autenticação e enviado como confirmação de autenticação ao provedor.

- RF4: O provedor poderá solicitar a qualquer momento a cancelamento do token de autenticação.

- RF5: O provedor e a aplicação cliente poderá solicitar a qualquer momento a verificação de validade do token.



  
