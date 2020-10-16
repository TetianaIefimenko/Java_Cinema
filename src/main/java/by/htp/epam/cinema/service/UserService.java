package by.htp.epam.cinema.service;

import javax.servlet.http.HttpServletRequest;

import by.htp.epam.cinema.domain.User;

public interface UserService extends Service {

	String checkUserData(String login, String email, String password);

	User getUser(String login);

	User getUser(String login, String password);

	void createUser(String login, String email, String password);

	boolean isUserAdmin(HttpServletRequest request);

	User getUser(int userId);

	void updateUser(User user);

	User buildUser(HttpServletRequest request);

	User changeUserPassword(int userId, String oldPassword, String newPassword);

	String checkUserLogin(String login);

	String checkUserEmail(String email);

	String checkUserPassword(String password);

}
