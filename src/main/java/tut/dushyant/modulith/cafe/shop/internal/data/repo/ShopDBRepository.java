package tut.dushyant.modulith.cafe.shop.internal.data.repo;

import java.util.List;

import tut.dushyant.modulith.cafe.common.dto.shop.Shop;

/**
 * ShopDBRepository
 */
public interface ShopDBRepository {
    Shop addShop(Shop shop);
    List<Shop> getShops();
    Shop getShop(int id);
    Shop updateShop(Shop shop);
    void deleteShop(int id);
}
