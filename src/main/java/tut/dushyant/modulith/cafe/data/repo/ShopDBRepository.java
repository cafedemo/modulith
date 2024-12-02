package tut.dushyant.modulith.cafe.data.repo;

import org.springframework.stereotype.Repository;
import tut.dushyant.modulith.cafe.data.dto.Shop;

import java.util.List;

@Repository
public interface ShopDBRepository {
    void addShop(Shop shop);
    List<Shop> getShops();
    Shop getShop(int id);
    Shop getShop(Shop shop);
    void updateShop(Shop shop);
    void deleteShop(String id);
}
