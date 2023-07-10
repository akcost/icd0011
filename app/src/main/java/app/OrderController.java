package app;

import dao.OrderDao;
import jakarta.validation.Valid;
import model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {

    private OrderDao dao;

    public OrderController(OrderDao dao) {
        this.dao = dao;
    }


    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return dao.findAll();
    }

    @GetMapping("/api/orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return dao.findOrderById(id);
    }

    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order save(@RequestBody @Valid Order order) {
        return dao.save(order);
    }

    @PreAuthorize("authentication.name == 'admin'")
    @DeleteMapping("/api/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable Long id) {
        dao.deleteOrderById(id);
    }

}