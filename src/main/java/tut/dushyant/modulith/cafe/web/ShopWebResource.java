package tut.dushyant.modulith.cafe.web;

import org.springframework.web.bind.annotation.*;
import tut.dushyant.modulith.cafe.data.dto.Shop;
import tut.dushyant.modulith.cafe.data.repo.ShopDBRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShopWebResource {

    private final ShopDBRepository repo;

    public ShopWebResource(ShopDBRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public List<Shop> getShopDetails() {
        return repo.getShops();
    }

    @PostMapping("/")
    public Shop addShop(@RequestBody Shop shop) {
        repo.addShop(shop);
        return repo.getShop(shop);
    }

    @GetMapping("/{id}")
    public Shop getShop(@PathVariable(name = "id")  int id) {
        return repo.getShop(id);
    }

    @PutMapping("/")
    public Shop updateShop(@RequestBody Shop shop) {
        repo.updateShop(shop);
        return repo.getShop(shop);
    }

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable(name = "id") String id) {
        repo.deleteShop(id);
    }
}