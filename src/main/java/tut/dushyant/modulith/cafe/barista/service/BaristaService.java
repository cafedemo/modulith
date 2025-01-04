package tut.dushyant.modulith.cafe.barista.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.NamedInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tut.dushyant.modulith.cafe.barista.events.BaristaCreateEvent;
import tut.dushyant.modulith.cafe.barista.events.BaristaDeleteEvent;
import tut.dushyant.modulith.cafe.barista.events.BaristaUpdateEvent;
import tut.dushyant.modulith.cafe.barista.events.BaristasDeleteForShopEvent;
import tut.dushyant.modulith.cafe.barista.internal.data.repo.BaristaDBRepository;
import tut.dushyant.modulith.cafe.common.dto.barista.Barista;
import tut.dushyant.modulith.cafe.util.exception.BadRequestException;

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
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Add a barista
     * @param barista Barista
     * @return Barista
     */
    @Transactional
    public Barista addBarista(Barista barista) {
        repo.addBarista(barista);
        eventPublisher.publishEvent(new BaristaCreateEvent(barista.getId()));
        return repo.searchBarista(barista);
    }

    /**
     * Update a barista
     * @param barista Barista
     * @return Barista
     */
    @Transactional
    public Barista updateBarista(Barista barista) {
        Barista updBarista = repo.updateBarista(barista);
        eventPublisher.publishEvent(new BaristaUpdateEvent(barista.getId()));
        return updBarista;
    }

    /**
     * Delete a barista
     * @param id String
     */
    @Transactional
    public void deleteBarista(String id) {
        if (INTEGER_PATTERN.matcher(id).matches()) {
            int rows = repo.deleteBarista(Integer.parseInt(id));
            log.atInfo().log("Delete barista with id: <{}> affected rows: <{}>",id,rows);
            eventPublisher.publishEvent(new BaristaDeleteEvent(Integer.parseInt(id)));
            return;
        }

        throw new BadRequestException("Input of barista id is not valid");
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
            eventPublisher.publishEvent(new BaristasDeleteForShopEvent(Integer.parseInt(shopId)));
            return;
        }
        throw new BadRequestException("Input of shop id is not valid");
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
        throw new BadRequestException("Input of shop id is not valid");
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
        throw new BadRequestException("Input of barista id is not valid");
    }

}
