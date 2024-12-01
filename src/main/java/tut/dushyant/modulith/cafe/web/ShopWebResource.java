package tut.dushyant.modulith.cafe.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}