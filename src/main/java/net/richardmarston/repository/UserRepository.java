package net.richardmarston.repository;

import net.richardmarston.model.User;
import org.springframework.data.repository.*;

/**
 * Created by rich on 29/03/15.
 */
public interface UserRepository extends Repository<User, Long> {

    public User findByName(String name);

    public User findById(int id);

    public void save(User user);
}