//package by.htp.epam.cinema;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PowerMockIgnore;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//
//import by.htp.epam.cinema.db.dao.impl.RoleDaoImpl;
//import by.htp.epam.cinema.db.pool.BaseConnectionPool;
//import by.htp.epam.cinema.db.pool.impl.CustomConnectionPool;
//import by.htp.epam.cinema.domain.Role;
//import by.htp.epam.cinema.domain.Role.Builder;
//
//import static org.mockito.Mockito.times;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ Role.class, RoleDaoImpl.class })
//@PowerMockIgnore("javax.management.*")
//public class TestRoleDaoImpl {
//
//	private RoleDaoImpl roleDaoImpl;
//
//	private Role role;
//
//	private BaseConnectionPool connectionPool;
//
//	private Connection connection;
//
//	private PreparedStatement preparedStatement;
//
//	private Statement statement;
//
//	private ResultSet resultSet;
//
//	private Builder roleBuilder;
//
//	@Before
//	public void init() throws SQLException {
//		role = Mockito.mock(Role.class);
//		connectionPool = Mockito.mock(CustomConnectionPool.class);
//		connection = Mockito.mock(Connection.class);
//		preparedStatement = Mockito.mock(PreparedStatement.class);
//		statement = Mockito.mock(Statement.class);
//		resultSet = Mockito.mock(ResultSet.class);
//		roleBuilder = Mockito.mock(Builder.class);
//		PowerMockito.mockStatic(Role.class);
//
//		Mockito.when(connectionPool.getConnection()).thenReturn(connection);
//		Mockito.doNothing().when(connectionPool).putConnection(connection);
//		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
//		Mockito.when(connection.createStatement()).thenReturn(statement);
//		Mockito.doNothing().when(preparedStatement).setString(Mockito.anyInt(), Mockito.anyString());
//		Mockito.doNothing().when(preparedStatement).setInt(Mockito.anyInt(), Mockito.anyInt());
//		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
//		Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
//		Mockito.when(resultSet.next()).thenReturn(true, true, false);
//		Mockito.when(resultSet.getInt(Mockito.anyString())).thenReturn(1, 2);
//		Mockito.when(resultSet.getString(Mockito.anyString())).thenReturn("ROLE_ADMIN", "ROLE_USER");
//		Mockito.doNothing().when(resultSet).close();
//		Mockito.when(role.getId()).thenReturn(1);
//		Mockito.when(role.getRoleName()).thenReturn("ROLE_ADMIN");
//		PowerMockito.when(Role.newBuilder()).thenReturn(roleBuilder);
//		Mockito.when(roleBuilder.setId(Mockito.anyInt())).thenReturn(roleBuilder);
//		Mockito.when(roleBuilder.setRoleName(Mockito.anyString())).thenReturn(roleBuilder);
//		Mockito.when(roleBuilder.build()).thenReturn(role);
//
//		roleDaoImpl = new RoleDaoImpl();
//		roleDaoImpl.setConnectionPool(connectionPool);
//
//	}
//
//	@Test
//	public void testCreate() throws SQLException {
//		roleDaoImpl.create(role);
//		Mockito.verify(connectionPool, times(1)).getConnection();
//		Mockito.verify(connection, times(1)).prepareStatement(Mockito.anyString());
//		Mockito.verify(role, times(1)).getRoleName();
//		Mockito.verify(preparedStatement, times(1)).setString(Mockito.anyInt(), Mockito.anyString());
//		Mockito.verify(preparedStatement, times(1)).executeUpdate();
//		Mockito.verify(connectionPool, times(1)).putConnection(connection);
//	}
//
//	@Test
//	public void testRead() throws SQLException {
//		roleDaoImpl.read(role.getId());
//		Mockito.verify(connectionPool, times(1)).getConnection();
//		Mockito.verify(connection, times(1)).prepareStatement(Mockito.anyString());
//		Mockito.verify(preparedStatement, times(1)).setInt(Mockito.anyInt(), Mockito.anyInt());
//		Mockito.verify(preparedStatement, times(1)).executeQuery();
//		Mockito.verify(resultSet, times(1)).next();
//
//		Mockito.verify(resultSet, times(1)).getInt(Mockito.anyString());
//		Mockito.verify(resultSet, times(1)).getString(Mockito.anyString());
//		PowerMockito.verifyStatic(times(1));
//		Role.newBuilder();
//		Mockito.verify(roleBuilder, times(1)).setId(Mockito.anyInt());
//		Mockito.verify(roleBuilder, times(1)).setRoleName(Mockito.anyString());
//		Mockito.verify(roleBuilder, times(1)).build();
//
//		Mockito.verify(connectionPool, times(1)).putConnection(connection);
//		Mockito.verify(resultSet, times(1)).close();
//	}
//
//	@Test
//	public void testReadAll() throws SQLException {
//		roleDaoImpl.readAll();
//		Mockito.verify(connectionPool, times(1)).getConnection();
//		Mockito.verify(connection, times(1)).createStatement();
//		Mockito.verify(statement, times(1)).executeQuery(Mockito.anyString());
//		Mockito.verify(resultSet, times(3)).next();
//
//		Mockito.verify(resultSet, times(2)).getInt(Mockito.anyString());
//		Mockito.verify(resultSet, times(2)).getString(Mockito.anyString());
//		PowerMockito.verifyStatic(times(2));
//		Role.newBuilder();
//		Mockito.verify(roleBuilder, times(2)).setId(Mockito.anyInt());
//		Mockito.verify(roleBuilder, times(2)).setRoleName(Mockito.anyString());
//		Mockito.verify(roleBuilder, times(2)).build();
//
//		Mockito.verify(connectionPool, times(1)).putConnection(connection);
//		Mockito.verify(resultSet, times(1)).close();
//	}
//
//	@Test
//	public void testUpdate() throws SQLException {
//		roleDaoImpl.update(role);
//		Mockito.verify(connectionPool, times(1)).getConnection();
//		Mockito.verify(connection, times(1)).prepareStatement(Mockito.anyString());
//		Mockito.verify(preparedStatement, times(1)).setString(Mockito.anyInt(), Mockito.anyString());
//		Mockito.doNothing().when(preparedStatement).setInt(Mockito.anyInt(), Mockito.anyInt());
//		Mockito.verify(preparedStatement, times(1)).executeUpdate();
//		Mockito.verify(connectionPool, times(1)).putConnection(connection);
//	}
//
//	@Test
//	public void testDelete() throws SQLException {
//		roleDaoImpl.update(role);
//		Mockito.verify(connectionPool, times(1)).getConnection();
//		Mockito.verify(connection, times(1)).prepareStatement(Mockito.anyString());
//		Mockito.verify(preparedStatement, times(1)).setInt(Mockito.anyInt(), Mockito.anyInt());
//		Mockito.verify(preparedStatement, times(1)).executeUpdate();
//		Mockito.verify(connectionPool, times(1)).putConnection(connection);
//	}
//
//	@Test
//	public void testBuildRole() throws Exception {
//		RoleDaoImpl roleDaoImplSpy = PowerMockito.spy(roleDaoImpl);
//		Whitebox.invokeMethod(roleDaoImplSpy, "buildRole", resultSet);
//		PowerMockito.verifyPrivate(roleDaoImplSpy).invoke("buildRole", Mockito.any(ResultSet.class));
//		Mockito.verify(resultSet, times(1)).getInt(Mockito.anyString());
//		Mockito.verify(resultSet, times(1)).getString(Mockito.anyString());
//		PowerMockito.verifyStatic(times(1));
//		Role.newBuilder();
//		Mockito.verify(roleBuilder, times(1)).setId(Mockito.anyInt());
//		Mockito.verify(roleBuilder, times(1)).setRoleName(Mockito.anyString());
//		Mockito.verify(roleBuilder, times(1)).build();
//	}
//}
