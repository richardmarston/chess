package net.richardmarston.service;

import net.richardmarston.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rich on 29/03/15.
 */
public interface ChessService {
    public User findById(int id) throws DataAccessException;

    public User findByName(String lastName) throws DataAccessException;

    public void saveUser(User user) throws DataAccessException;
}
