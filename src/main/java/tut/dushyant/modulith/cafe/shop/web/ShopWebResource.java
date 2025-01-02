package tut.dushyant.modulith.cafe.shop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tut.dushyant.modulith.cafe.common.dto.shop.Shop;
import tut.dushyant.modulith.cafe.shop.service.ShopService;

import java.util.List;

/**
 * Shop Web Resource
 */
@RestController
@RequestMapping("/api/shop")
public class ShopWebResource {

    private final ShopService svc;
    private final ShopService shopService;

    public ShopWebResource(ShopService svc, ShopService shopService) {
        this.svc = svc;
        this.shopService = shopService;
    }

    @GetMapping(produces = "application/json")
    public List<Shop> getShopDetails() {
        return svc.getShops();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Shop> addShop(@RequestBody Shop shop) {
        return new ResponseEntity<>(svc.addShop(shop), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Shop getShop(@PathVariable(name = "id")  String id) {
        return shopService.getShop(id);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public Shop updateShop(@RequestBody Shop shop) {
        return svc.updateShop(shop);
    }

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable(name = "id") String id) {
        svc.deleteShop(id);
    }
}