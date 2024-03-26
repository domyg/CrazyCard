package com.domyg.crazycard.repository;
import com.domyg.crazycard.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List <Store> findAll();
    Store findByName(String name);

    Store findByLocalityAndAuthorizedTrue(String locality);

}
