package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "orders")

public class Order {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;

    @NonNull
    @Column(name = "order_number")
    @Size(min = 2)
    private String orderNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_rows", joinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"))
    @Valid
    private List<OrderRow> orderRows = new ArrayList<>();

}
