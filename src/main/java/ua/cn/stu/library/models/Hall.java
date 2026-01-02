package ua.cn.stu.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.cn.stu.library.models.librarian.Librarian;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "hall")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hall extends BaseEntity {

    @Column(name = "name", nullable = false, length = 40, unique = true)
    private String name;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hall",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Book> books;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;

}
