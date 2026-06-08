package br.com.zenom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private ConnectionFactory(){}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/zenom_frauds", "root", "senha123");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com banco de dados", e);
        }
    }

}
