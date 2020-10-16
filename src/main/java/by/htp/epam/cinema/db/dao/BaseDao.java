package by.htp.epam.cinema.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.htp.epam.cinema.domain.BaseEntity;

public interface BaseDao<T extends BaseEntity> {

	void create(T entity);

	T read(int id);

	List<T> readAll();

	void update(T entity);

	void delete(int entityId);

	T buildEntity(ResultSet rs) throws SQLException;
}