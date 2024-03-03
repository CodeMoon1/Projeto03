package br.com.souza.dao;

import br.com.souza.domain.Cliente;
import br.com.souza.jdbcgeneric.Connectionfactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO{
    @Override
    public Integer cadastrar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = Connectionfactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            adicionarParametrosInsert(stm, cliente);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Integer atualizar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = Connectionfactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm,cliente);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm,null);
        }
    }

    @Override
    public Cliente buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            connection = Connectionfactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, codigo);
            rs = stm.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cd = rs.getString("CODIGO");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cd);
            }
        } catch(Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
        return cliente;
    }

    @Override
    public List<Cliente> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Cliente> list = new ArrayList<>();
        Cliente cliente = null;
        try{
            connection = Connectionfactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next()){
                cliente = new Cliente();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cod = rs.getString("CODIGO");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cod);
                list.add(cliente);
            }
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm,rs);
        }
        return list;
    }

    @Override
    public Integer excluir(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = Connectionfactory.getConnection();
            String sql = getSqlExcluir();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, cliente);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }
    }

    private String getSqlInsert(){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO CLIENTE (ID,CODIGO, NOME)");
        sb.append("VALUES (nextval('sq_cliente'),?,?)");
        return sb.toString();
    }
    private void adicionarParametrosInsert(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1, cliente.getCodigo());
        stm.setString(2, cliente.getNome());
    }
    private String getSqlUpdate (){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CLIENTE ");
        sb.append("SET NOME = ?, CODIGO = ? ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }
    public void adicionarParametrosUpdate(PreparedStatement stm, Cliente cliente) throws SQLException{
        stm.setString(1,cliente.getNome());
        stm.setString(2,cliente.getCodigo());
        stm.setLong(3,cliente.getId());
    }
    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM CLIENTE ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    private void adicionarParametrosSelect(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo);
    }
    private String getSqlExcluir(){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM CLIENTE ");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }
    public void adicionarParametrosDelete(PreparedStatement stm, Cliente cliente)throws SQLException{
        stm.setString(1,cliente.getCodigo());
    }
    public String getSqlSelectAll(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM CLIENTE");
        return  sb.toString();
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs){
        try{
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
            e1.printStackTrace();
        }
    }

}
