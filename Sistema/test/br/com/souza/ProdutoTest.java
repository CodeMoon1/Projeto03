package br.com.souza;

import br.com.souza.dao.IProdutoDAO;
import br.com.souza.dao.ProdutoDAO;
import br.com.souza.domain.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ProdutoTest {
    private IProdutoDAO produtoDAO;

    @Test
    public void cadastrarTest() throws Exception {
        produtoDAO = new ProdutoDAO();
        Produto produto = new Produto();
        produto.setNome("Pokemon");
        produto.setCodigo("101");
        Integer countCad = produtoDAO.cadastrar(produto);
        assertTrue(countCad == 1);


        Produto produtoBd = produtoDAO.buscar("101");
        Assert.assertNotNull(produtoBd);
        Assert.assertEquals(produto.getCodigo(), produtoBd.getCodigo());
        Assert.assertEquals(produto.getNome(), produtoBd.getNome());

        Integer countDel = produtoDAO.excluir(produtoBd);
        Assert.assertTrue(countDel == 1);
    }
    @Test
    public void buscarTest() throws Exception{
        produtoDAO = new ProdutoDAO();
        Produto produto = new Produto();
        produto.setNome("Pokemon");
        produto.setCodigo("102");
        Integer countCad = produtoDAO.cadastrar(produto);
        assertTrue(countCad == 1);

        Produto produtoBd = produtoDAO.buscar("102");
        Assert.assertNotNull(produtoBd);
        assertEquals(produtoBd.getCodigo(), produto.getCodigo());
        assertEquals(produtoBd.getNome(), produto.getNome());

        Integer countDel = produtoDAO.excluir(produtoBd);
        Assert.assertTrue(countDel == 1);
    }
    @Test
    public void buscarTodosTest() throws Exception {
        produtoDAO = new ProdutoDAO();

        Produto p = new Produto();
        p.setNome("Charmander");
        p.setCodigo("404");
        Integer countProd = produtoDAO.cadastrar(p);
        Assert.assertTrue(countProd == 1);

        Produto p2 = new Produto();
        p2.setNome("squirtle");
        p2.setCodigo("403");
        Integer countProd2 = produtoDAO.cadastrar(p2);
        Assert.assertTrue(countProd2 == 1);

        List<Produto> list = produtoDAO.buscarTodos();
        Assert.assertNotNull(list);
        assertEquals(2, list.size());

        int countDel = 0;
        for(Produto pc : list){
            produtoDAO.excluir(pc);
            countDel ++;
        }
        Assert.assertEquals(list.size(), countDel);

        list = produtoDAO.buscarTodos();
        assertEquals(list.size(), 0);

    }
    @Test
    public void atualizarTest()throws Exception{
        produtoDAO = new ProdutoDAO();

        Produto p = new Produto();
        p.setNome("Charmander");
        p.setCodigo("404");
        Integer countProd = produtoDAO.cadastrar(p);
        Assert.assertTrue(countProd == 1);

        Produto pbd = produtoDAO.buscar("404");
        assertNotNull(pbd);
        assertEquals(pbd.getCodigo(),p.getCodigo());

        pbd.setCodigo("21");
        pbd.setNome("Sonic");
        Integer countUpdate = produtoDAO.atualizar(pbd);
        Assert.assertTrue(countUpdate == 1);


        Produto pbdnull = produtoDAO.buscar("404");
        Assert.assertNull(pbdnull);

        Produto comp = produtoDAO.buscar("21");
        assertNotNull(comp);
        Assert.assertEquals(pbd.getId(), comp.getId());
        Assert.assertEquals(pbd.getNome(), comp.getNome());

        List<Produto> l = produtoDAO.buscarTodos();
        for(Produto i : l){
            produtoDAO.excluir(i);
        }

    }
}
