package tut.dushyant.modulith.cafe.shop.service;

import org.springframework.modulith.NamedInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import tut.dushyant.modulith.cafe.barista.service.BaristaService;
import tut.dushyant.modulith.cafe.common.dto.shop.Shop;
import tut.dushyant.modulith.cafe.shop.internal.data.repo.ShopDBRepository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service to access Shop data
 */
@Service
@NamedInterface("shop-service")
public class ShopService {

    private final ShopDBRepository repo;
    private final BaristaService baristaService;
    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");
    private final TransactionTemplate transactionTemplate;

    public ShopService(ShopDBRepository repo,
            BaristaService baristaService, 
            PlatformTransactionManager platformTransactionManager) {
        this.repo = repo;
        this.baristaService = baristaService;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    /**
     *
     * @param shop Shop to add
     * @return Shop Object
     */
    public Shop addShop(Shop shop) {
        return repo.addShop(shop);
    }

    /**
     *
     * @param shop Update to Shop Object
     * @return Updated Shop Object
     */
    public Shop updateShop(Shop shop) {
        return repo.updateShop(shop);
    }

    /**
     *
     * @param id ID to search Shop
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteShop(String id) {
        if (INTEGER_PATTERN.matcher(id).matches()) {
            // Test if shop exists in the database
            Shop shop = repo.getShop(Integer.parseInt(id));
            if (shop == null) {
                throw new IllegalArgumentException("Shop with id " + id + " not found");
            }
            String deletedShopId = transactionTemplate.execute((TransactionStatus status) -> {
                baristaService.deleteBaristasForShop(id);
                repo.deleteShop(Integer.parseInt(id));
                return id;
            });
            if (deletedShopId == null) {
                throw new IllegalArgumentException("Failed to delete shop with id " + id);
            }
        } else {
            throw new IllegalArgumentException("Invalid shop id: " + id);
        }
    }

    /**
     *
     * @return List of shops from database
     */
    public List<Shop> getShops() {
        return repo.getShops().stream().peek(shop -> shop.setBaristas(baristaService.getBaristasForShop(shop.getId()+""))).toList();
    }


    /**
     *
     * @param shopId ID of Shop
     * @return Shop details from database
     */
    public Shop getShop(String shopId) {
        if (INTEGER_PATTERN.matcher(shopId).matches()) {
            Shop shop = repo.getShop(Integer.parseInt(shopId));
            if (shop == null) {
                throw new IllegalArgumentException("Shop with id " + shopId + " not found");
            }
            shop.setBaristas(baristaService.getBaristasForShop(shopId));
            return shop;
        }
        throw new IllegalArgumentException("Input of shop id is not valid");
    }
}