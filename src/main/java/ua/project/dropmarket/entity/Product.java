    package ua.project.dropmarket.entity;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import java.math.BigDecimal;

    @Getter
    @Setter
    @Entity
    @Table(name = "products")
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String photo;

        private String producer;
        private String name;
        private String description;
        private BigDecimal price;
    }
