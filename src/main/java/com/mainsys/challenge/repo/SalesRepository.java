package com.mainsys.challenge.repo;

import com.mainsys.challenge.model.Sales;
import com.mainsys.challenge.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    List<Sales> findByUserId(Long id);
    List<Sales> findByDate(Date date);

}
