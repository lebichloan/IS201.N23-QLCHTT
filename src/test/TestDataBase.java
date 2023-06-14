package test;


import java.sql.Connection;

import db.database;

public class TestDataBase {

	public static void main(String[] args) {
			Connection connection = database.getConnection();
			System.out.println(connection);
	}
}

