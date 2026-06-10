package com.plurasight.sneakerdrops.data;

import com.plurasight.sneakerdrops.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
