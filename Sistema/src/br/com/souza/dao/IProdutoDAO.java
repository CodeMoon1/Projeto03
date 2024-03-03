package br.com.souza.dao;

import br.com.souza.domain.Cliente;
import br.com.souza.domain.Produto;

import java.util.List;

public interface IProdutoDAO {

    Integer cadastrar(Produto produto) throws Exception;

    Produto buscar(String codigo) throws Exception;

    Integer excluir(Produto produto) throws Exception;

    List<Produto> buscarTodos() throws Exception;

    Integer atualizar(Produto produto) throws Exception;
}
