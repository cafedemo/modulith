package tut.dushyant.modulith.cafe.data.repo;

import tut.dushyant.modulith.cafe.data.dto.Shop;

import java.util.List;

public interface ShopDBRepository {
    Shop addShop(Shop shop);
    List<Shop> getShops();
    Shop getShop(String id);
    Shop updateShop(Shop shop);
    void deleteShop(String id);
}
