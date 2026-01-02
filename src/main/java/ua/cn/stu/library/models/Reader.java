package ua.cn.stu.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "reader")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reader extends BaseEntity {

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "email", length = 80, unique = true)
    private String email;

    @Column(name = "phone", length = 13, unique = true)
    private String phone;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Loan> loans;

}
