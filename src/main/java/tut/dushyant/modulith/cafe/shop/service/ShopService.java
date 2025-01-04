package tut.dushyant.modulith.cafe.shop.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.NamedInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import tut.dushyant.modulith.cafe.barista.service.BaristaService;
import tut.dushyant.modulith.cafe.common.dto.shop.Shop;
import tut.dushyant.modulith.cafe.shop.events.ShopCreatedEvent;
import tut.dushyant.modulith.cafe.shop.events.ShopDeletedEvent;
import tut.dushyant.modulith.cafe.shop.events.ShopUpdateEvent;
import tut.dushyant.modulith.cafe.shop.internal.data.repo.ShopDBRepository;
import tut.dushyant.modulith.cafe.common.exception.BadRequestException;
import tut.dushyant.modulith.cafe.common.exception.NotFoundException;
import tut.dushyant.modulith.cafe.common.exception.UpdateFailedException;

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
    private final ApplicationEventPublisher applicationEventPublisher;

    public ShopService(ShopDBRepository repo,
                       BaristaService baristaService,
                       PlatformTransactionManager platformTransactionManager,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.repo = repo;
        this.baristaService = baristaService;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     *
     * @param shop Shop to add
     * @return Shop Object
     */
    @Transactional
    public Shop addShop(Shop shop) {
        Shop newShop = repo.addShop(shop);
        applicationEventPublisher.publishEvent(new ShopCreatedEvent(newShop.getId()));
        return newShop;
    }

    /**
     *
     * @param shop Update to Shop Object
     * @return Updated Shop Object
     */
    @Transactional
    public Shop updateShop(Shop shop) {
        Shop updShop = repo.updateShop(shop);
        if (updShop == null) {
            throw new UpdateFailedException("Failed to update shop with id " + shop.getId());
        }
        applicationEventPublisher.publishEvent(new ShopUpdateEvent(updShop.getId()));
        return updShop;
    }

    /**
     * Search for shop and delete it
     * @param id ID to search Shop
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteShop(String id) {
        if (INTEGER_PATTERN.matcher(id).matches()) {
            // Test if shop exists in the database
            Shop shop = repo.getShop(Integer.parseInt(id));
            if (shop == null) {
                throw new NotFoundException("Shop with id " + id + " not found");
            }
            String deletedShopId = transactionTemplate.execute((TransactionStatus status) -> {
                baristaService.deleteBaristasForShop(id);
                repo.deleteShop(Integer.parseInt(id));
                return id;
            });
            if (deletedShopId == null) {
                throw new UpdateFailedException("Failed to delete shop with id " + id);
            }
            applicationEventPublisher.publishEvent(new ShopDeletedEvent(Integer.parseInt(deletedShopId)));
        } else {
            throw new BadRequestException("Invalid shop id: " + id);
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
                throw new NotFoundException("Shop with id " + shopId + " not found");
            }
            shop.setBaristas(baristaService.getBaristasForShop(shopId));
            return shop;
        }
        throw new BadRequestException("Input of shop id is not valid");
    }
}