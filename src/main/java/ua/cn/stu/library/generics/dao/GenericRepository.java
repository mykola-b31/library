package ua.cn.stu.library.generics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.cn.stu.library.models.BaseEntity;

public interface GenericRepository <T extends BaseEntity> extends JpaRepository<T, Integer> {

}
