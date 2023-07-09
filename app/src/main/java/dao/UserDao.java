package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public User getUserByUserName(String name) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.username = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u").getResultList();
    }

}
