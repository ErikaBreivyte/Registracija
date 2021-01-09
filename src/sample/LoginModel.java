package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;
    public LoginModel (){
        connection = SQLiteconnection.Connector();
        if (connection == null) System.exit(1);
    }

    public  boolean isDbConnected(){
        try{
            return !connection.isClosed();
        } catch (SQLException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin (String user, String pass) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = " select * from REG where Nikas = ? and Pass = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e){
            return false;
            //TODO: handle exception
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }


    public boolean isLoginAdmin (String user, String pass) throws SQLException{

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = " select * from ADMIN where VarAdmin = ? and PasAdmin = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e){
            return false;
            //TODO: handle exception
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }

    public boolean isLoginFin (String user, String pass) throws SQLException{

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = " select * from FINANSISTAS where NAME = ? and PASS = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e){
            return false;
            //TODO: handle exception
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }


}