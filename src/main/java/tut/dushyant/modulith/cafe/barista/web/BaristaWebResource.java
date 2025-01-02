package tut.dushyant.modulith.cafe.barista.web;

import org.springframework.web.bind.annotation.*;

import tut.dushyant.modulith.cafe.barista.service.BaristaService;
import tut.dushyant.modulith.cafe.common.dto.barista.Barista;

import java.util.List;

/**
 * Barista Web Resource
 */
@RestController
@RequestMapping("/api/barista")
public class BaristaWebResource {

    private final BaristaService svc;

    public BaristaWebResource(BaristaService svc) {
        this.svc = svc;
    }

    @GetMapping(produces = "application/json")
    public List<Barista> getBaristaDetails() {
        return svc.getBaristas();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Barista getBarista(@PathVariable String id) {
        return svc.getBarista(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Barista addBarista(@RequestBody Barista barista) {
        return svc.addBarista(barista);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Barista updateBarista(@RequestBody Barista barista) {
        return svc.updateBarista(barista);
    }

    @DeleteMapping("/{id}")
    public void deleteBarista(@PathVariable String id) {
        svc.deleteBarista(id);
    }

    @DeleteMapping("/shop/{shopId}")
    public void deleteBaristaForShop(@PathVariable String shopId) {
        svc.deleteBaristasForShop(shopId);
    }

}
