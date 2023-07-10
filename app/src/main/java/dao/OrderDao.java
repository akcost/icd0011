package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Order save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }

        return order;
    }

    public List<Order> findAll() {
        return em.createQuery("select p from Order p").getResultList();
    }

    public Order findOrderById(Long id) {
        TypedQuery<Order> query = em.createQuery("select p from Order p where p.id = :id", Order.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public void deleteOrderById(Long id) {
        Order order = em.find(Order.class, id);

        if (order != null) {
            em.remove(order);
        }
    }
}
