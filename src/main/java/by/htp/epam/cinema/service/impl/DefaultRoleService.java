package by.htp.epam.cinema.service.impl;

import java.util.List;

import by.htp.epam.cinema.db.dao.RoleDao;
import by.htp.epam.cinema.domain.Role;
import by.htp.epam.cinema.service.RoleService;

public class DefaultRoleService implements RoleService {

	private RoleDao roleDao;

	public DefaultRoleService(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Role> getAll() {
		return roleDao.readAll();
	}

}
