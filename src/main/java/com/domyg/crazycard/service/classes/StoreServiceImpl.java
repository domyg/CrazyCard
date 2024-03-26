package com.domyg.crazycard.service.classes;

import com.domyg.crazycard.dto.StoreDto;
import com.domyg.crazycard.model.Store;
import com.domyg.crazycard.model.User;
import com.domyg.crazycard.repository.StoreRepository;
import com.domyg.crazycard.service.interfaces.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<StoreDto> findAll() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map((store) -> mapToStoreDto(store))
                .collect(Collectors.toList());

    }

    @Override
    public Store findByName(String name) {
        return storeRepository.findByName(name);
    }

    @Override
    public void updateStoreAuthorization(StoreDto storeDto) {
        Store store = storeRepository.findByName(storeDto.getName());
        store.setAuthorized(storeDto.getAuthorized());
        storeRepository.save(store);
    }

    @Override
    public void updateStoreUsers(StoreDto storeDto, List<User> newList) {
        Store store = storeRepository.findByName(storeDto.getName());
        store.setUsers(newList);
        storeRepository.save(store);
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        Store store = new Store();
        store.setAuthorized(true);
        store.setName(storeDto.getName());
        store.setLocality(storeDto.getLocality());
        storeRepository.save(store);
    }

    @Override
    public  void removeStore(StoreDto storeDto) {
        Store store = storeRepository.findByName(storeDto.getName());
        storeRepository.delete(store);
    }

    private StoreDto mapToStoreDto(Store store) {
        StoreDto storeDto = new StoreDto();
        storeDto.setName(store.getName());
        storeDto.setAuthorized(store.getAuthorized());
        storeDto.setLocality(store.getLocality());
        return storeDto;
    }
}