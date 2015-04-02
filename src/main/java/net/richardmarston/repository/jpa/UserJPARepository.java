package net.richardmarston.repository.jpa;

import net.richardmarston.model.User;
import net.richardmarston.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by rich on 29/03/15.
 */
@Repository
public class UserJPARepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findByName(String name) {
        System.out.println("THIS em is: "+em+ " name: "+name);
        Query query = this.em.createQuery("FROM User WHERE name = :name");
        query.setParameter("name", name);
        try {
            return (User) query.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findById(int id) {
        return this.em.find(User.class, id);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            this.em.persist(user);
        }
        else {
            this.em.merge(user);
        }
    }
}
