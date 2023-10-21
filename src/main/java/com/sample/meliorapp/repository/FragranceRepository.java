package com.sample.meliorapp.repository;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FragranceRepository extends JpaRepository<FragranceType, Integer> {
    @Query("SELECT DISTINCT fragrance FROM FragranceType fragrance WHERE fragrance.name LIKE :name%")
    Collection<FragranceType> findByName(@Param("name") String name);
    @Query("SELECT fragrance FROM FragranceType fragrance WHERE fragrance.id =:id")
    FragranceType findById(@Param("id") int id);
}
