package br.com.souza.dao;
import br.com.souza.domain.Produto;
import br.com.souza.jdbcgeneric.Connectionfactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO{
    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = Connectionfactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            adicionarParametrosInsert(stm, produto);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm,null);
        }

    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto produto = null;
        try{
            connection = Connectionfactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, codigo);
            rs = stm.executeQuery();
            if(rs.next()){
                produto = new Produto();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cod = rs.getString("CODIGO");
                produto.setId(id);
                produto.setNome(nome);
                produto.setCodigo(cod);
            }

        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm,rs);
        }
        return produto;
    }

    @Override
    public Integer excluir(Produto produto) throws Exception{
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = Connectionfactory.getConnection();
            String sql = getSqlExcluir();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, produto);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection  connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto p = null;
        List<Produto> list = new ArrayList<>();
        try {

            connection = Connectionfactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next()){
                p = new Produto();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cod = rs.getString("CODIGO");
                p.setId(id);
                p.setNome(nome);
                p.setCodigo(cod);
                list.add(p);
            }
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, rs);
        }
        return list;
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = Connectionfactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm,produto);
            return stm.executeUpdate();

        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }

    }

    private void adicionarParametrosUpdate(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1,produto.getNome());
        stm.setString(2,produto.getCodigo());
        stm.setLong(3,produto.getId());
    }

    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE PRODUTO ");
        sb.append("SET NOME = ?, CODIGO = ? ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM PRODUTO");
        return sb.toString();
    }

    private void adicionarParametrosDelete(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1,produto.getCodigo());
    }

    private String getSqlExcluir() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM PRODUTO ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    private void adicionarParametrosSelect(PreparedStatement stm, String codigo) throws SQLException{
        stm.setString(1,codigo);
    }

    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM PRODUTO ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO PRODUTO (ID,CODIGO, NOME)");
        sb.append("VALUES (nextval('sq_produto'),?,?)");
        return sb.toString();
    }
    private void adicionarParametrosInsert(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1,produto.getCodigo());
        stm.setString(2,produto.getNome());
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
            if(stm != null && !stm.isClosed()){
                stm.close();
            }
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e1){
            e1.printStackTrace();;
        }
    }
}
