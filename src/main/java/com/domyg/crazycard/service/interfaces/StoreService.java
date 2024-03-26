package com.domyg.crazycard.service.interfaces;

import com.domyg.crazycard.dto.StoreDto;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;

import java.util.List;

public interface StoreService {

    List<StoreDto> findAll();
    Store findByName(String name);

    void updateStoreAuthorization(StoreDto storeDto);

    void updateStoreUsers(StoreDto storeDto, List<User> newList);

    void removeStore(StoreDto storeDto);

    void saveStore(StoreDto storeDto);

}
