package com.mainsys.challenge.repo;

import com.mainsys.challenge.model.Sales;
import com.mainsys.challenge.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findByUser(User user);

    Boolean existsByUser(User user);

    List<Sales> findByUserId(Long id);

    List<Sales> findByDate(Date date);

    List<Sales> findByDateOrderByUserId(Date date);

    List<Sales> findByUserIdAndDate(Long id, Date date);
}
