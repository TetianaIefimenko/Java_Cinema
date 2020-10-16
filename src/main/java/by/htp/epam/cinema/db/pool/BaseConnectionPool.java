package by.htp.epam.cinema.db.pool;

import java.sql.Connection;

public interface BaseConnectionPool {

	public Connection getConnection();

	public void putConnection(Connection connection);

}
