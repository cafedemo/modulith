package tut.dushyant.modulith.cafe.barista.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.NamedInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tut.dushyant.modulith.cafe.barista.internal.data.repo.BaristaDBRepository;
import tut.dushyant.modulith.cafe.common.dto.barista.Barista;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Barista Service
 */
@Service
@NamedInterface("barista-service")
@Slf4j
@AllArgsConstructor
public class BaristaService {

    private final BaristaDBRepository repo;
    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    /**
     * Add a barista
     * @param barista Barista
     * @return Barista
     */
    public Barista addBarista(Barista barista) {
        repo.addBarista(barista);
        return repo.searchBarista(barista);
    }

    /**
     * Update a barista
     * @param barista Barista
     * @return Barista
     */
    public Barista updateBarista(Barista barista) {
        return repo.updateBarista(barista);
    }

    /**
     * Delete a barista
     * @param id String
     */
    public void deleteBarista(String id) {
        if (INTEGER_PATTERN.matcher(id).matches()) {
            int rows = repo.deleteBarista(Integer.parseInt(id));
            log.atInfo().log("Delete barista with id: <{}> affected rows: <{}>",id,rows);
            return;
        }

        throw new IllegalArgumentException("Input of barista id is not valid");
    }

    /**
     * Delete baristas for a shop
     * @param shopId ID for Shop to delete baristas
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBaristasForShop(String shopId) {
        log.atInfo().log("Started deleting baristas for shop {}",shopId);
        if (shopId != null && INTEGER_PATTERN.matcher(shopId).matches()) {
            int rows = repo.deleteBaristasForShop(Integer.parseInt(shopId));
            log.atInfo().log("Deleted baristas for shop: <{}> and <{}> rows deleted.",shopId, rows);
            return;
        }
        throw new IllegalArgumentException("Input of shop id is not valid");
    }

    /**
     * Get all baristas
     * @return List of Barista
     */
    public List<Barista> getBaristas() {
        return repo.getBaristas();
    }

    public List<Barista> getBaristasForShop(String shopId) {
        if (INTEGER_PATTERN.matcher(shopId).matches()) {
            return repo.getBaristasForShop(Integer.parseInt(shopId));
        }
        throw new IllegalArgumentException("Input of shop id is not valid");
    }

    /**
     * Get a barista
     * @param id String
     * @return Barista
     */
    public Barista getBarista(String id) {
        if (INTEGER_PATTERN.matcher(id).matches()) {
            return repo.getBarista(Integer.parseInt(id));
        }
        throw new IllegalArgumentException("Input of barista id is not valid");
    }

}
