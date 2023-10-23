package com.sample.meliorapp.repository;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface FragranceRepository extends JpaRepository<FragranceType, Integer> {
    @Query("SELECT DISTINCT fragrance FROM FragranceType fragrance WHERE fragrance.name LIKE :name%")
    Collection<FragranceType> findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Order order WHERE order.fragranceType = :fragranceType")
    void deleteRelatedOrdersByFragranceType(@Param("fragranceType") FragranceType fragranceType);

    @Transactional
    @Modifying
    @Query("DELETE FROM FragranceType fragranceType WHERE fragranceType = :fragranceType")
    void delete(@Param("fragranceType") FragranceType fragranceType);
}
