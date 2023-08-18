# api-pessoa
## :information_source: Como Utilizar: 

Clonar o projeto, e iniciar aplicacao, precisando [Git](https://git-scm.com). linhas de comando:

```bash
# Clone do repositório
$ git clone https://github.com/QueziaMayara/api-pessoa.git

# Entre neste repositório
$ cd api-pessoa

```


## Rotas

<blockquote>baseUrl: http://localhost:8082</blockquote>

## Address

<p>
  Listar todas pessoas (GET)
  
   <code>
    baseUrl/v1/people
  </code>
</p>

<p>
  Mostra única Pessoa (GET)
  
   <code>
    baseUrl/v1/people/:id
  </code>

</p>

<p>
  Criar Pessoa (POST)
  
  <code>
    baseUrl/v1/people
  </code>
</p>

<p>
  Atualizar Pessoa (PUT)
  
  <code>
    baseUrl/v1/people/:id
  </code>
</p>

<p>
  Deletar Pessoa (DELETE)
  
  <code>
    baseUrl/v1/people/:id
  </code>
</p>

<p>
  Criar Endereço (POST)
  
  <code>
    baseUrl/v1/address/:id
  </code>
</p>

<p>
  Atualizar Endereço como Principal (PUT)
  
  <code>
    baseUrl/v1/address/:id/:idAddress
  </code>
</p>

<p>
  Buscar Endereços da Pessoa (GET)
  
  <code>
    baseUrl/v1/address/:id
  </code>
</p>
