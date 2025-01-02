package tut.dushyant.modulith.cafe.barista.internal.data.repo;

import java.util.List;

import tut.dushyant.modulith.cafe.common.dto.barista.Barista;

/**
 * Repository for Barista entity.
 */
public interface BaristaDBRepository {
    void addBarista(Barista barista);
    List<Barista> getBaristas();
    Barista getBarista(int id);
    Barista updateBarista(Barista barista);
    int deleteBarista(int id);
    int deleteBaristasForShop(int shopId);
    Barista searchBarista(Barista barista);
    List<Barista> getBaristasForShop(int shopId);
}
