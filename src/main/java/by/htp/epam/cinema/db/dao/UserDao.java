package by.htp.epam.cinema.db.dao;

import by.htp.epam.cinema.domain.User;

public interface UserDao extends BaseDao<User> {

	User readByLogin(String login);

	User readByEmail(String email);

}
