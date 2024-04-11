package ua.project.dropmarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 14, message = "Кількість символів повинна від 2 до 14")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 14, message = "Кількість символів повинна від 2 до 14")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Невірний email")
    private String email;

    @Min(value = 8, message = "Мінімальна кількість символів має бути 8")
    private int phone;

    @Min(value = 2, message = "Мінімальна кількість символів має бути 2")
    private int age;

    @NotEmpty
    private String address;



    @OneToOne
    @MapKey
    @MapsId
    @JoinColumn(name = "id")
    private Users user;
}