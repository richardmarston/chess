package net.richardmarston.service;

import java.util.Collection;
import net.richardmarston.model.User;
import net.richardmarston.repository.UserRepository;
import net.richardmarston.service.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ChessServiceImpl implements ChessService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ChessServiceImpl(UserRepository repository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public User findById(int id) throws DataAccessException {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String name) throws DataAccessException {
        System.out.println("name: "+name + " userRep: "+userRepository);
        return userRepository.findByName(name);
    }

    @Override
    @Transactional
    public void saveUser(User user) throws DataAccessException {
        userRepository.save(user);
    }
}