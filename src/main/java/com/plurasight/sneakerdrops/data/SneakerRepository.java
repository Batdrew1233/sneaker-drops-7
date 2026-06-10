package com.plurasight.sneakerdrops.data;

import com.plurasight.sneakerdrops.models.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
    List<Sneaker> findByModelContaining(String text);
    List<Sneaker>findByPriceLessThan(double price);
    List<Sneaker>findByReleaseYear(int year);

    @Query ("SELECT s FROM Sneaker s WHERE s.price <= :maxPrice AND s.releaseYear >= :minYear")
    List<Sneaker> findByMaxPriceAndMinYear(@Param("maxPrice") double maxPrice, @Param("minYear") int minYear);

}
