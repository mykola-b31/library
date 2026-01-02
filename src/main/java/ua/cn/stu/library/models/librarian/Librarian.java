package ua.cn.stu.library.models.librarian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.cn.stu.library.models.BaseEntity;
import ua.cn.stu.library.models.Hall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "librarian")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Librarian extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", length = 50)
    private LibrarianPosition position;

    private LocalDate hireDate;

    private BigDecimal salary;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "librarian",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Hall> halls;

}
