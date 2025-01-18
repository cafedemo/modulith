package tut.dushyant.modulith.cafe.barista.internal.data.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tut.dushyant.modulith.cafe.barista.internal.data.repo.BaristaDBRepository;
import tut.dushyant.modulith.cafe.common.dto.barista.Barista;
import tut.dushyant.modulith.cafe.common.exception.UpdateFailedException;

import java.util.List;

/**
 * BaristaDBRepositoryImpl
 */
@Repository
@Slf4j
public class BaristaDBRepositoryImpl implements BaristaDBRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BaristaDBRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *
     * @param barista Add Barista
     */
    @Override
    public void addBarista(Barista barista) {
        try {
            int rows = jdbcTemplate.update(
                    "INSERT INTO barista (name, email, shop_id)"
                            + " VALUES (:name, :email, :shop_id)",
                    new MapSqlParameterSource()
                            .addValue("name", barista.getName())
                            .addValue("email", barista.getEmail())
                            .addValue("shop_id", barista.getShopId()));

            if (rows <= 0)
                throw new UpdateFailedException(
                        "Failed to insert barista with name: " + barista.getName() + " and email: " + barista.getEmail());
        } catch (Exception e) {
            if (e instanceof UpdateFailedException)
                throw e;
            log.error("Failed to insert barista with name: {} and email: {}", barista.getName(), barista.getEmail(), e);
            throw new UpdateFailedException(
                    "Failed to insert barista with name: " + barista.getName() + " and email: " + barista.getEmail());
        }
    }

    /**
     *
     * @return List of Barista in all shops
     */
    @Override
    public List<Barista> getBaristas() {
        return jdbcTemplate.query(
            "SELECT id, name, email, shop_id FROM barista",
            (rs, rowNum) ->
                Barista.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .shopId(rs.getInt("shop_id"))
                    .build()
        );
    }

    /**
     *
     * @param id Unique id to get Barista
     * @return Barista from database
     */
    @Override
    public Barista getBarista(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT id, name, email, shop_id FROM barista WHERE id = :id",
            new MapSqlParameterSource().addValue("id", id),
            (rs, rowNum) ->
                Barista.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .shopId(rs.getInt("shop_id"))
                    .build()
        );
    }

    /**
     *
     * @param barista Barista details to search for
     * @return Barista object as fetched from DB
     */
    @Override
    public Barista searchBarista(Barista barista) {
        return jdbcTemplate.queryForObject(
            "SELECT id, name, email, shop_id FROM barista WHERE name = :name AND email = :email AND shop_id = :shop_id",
            new MapSqlParameterSource()
                .addValue("name", barista.getName())
                .addValue("email", barista.getEmail())
                .addValue("shop_id", barista.getShopId()),
            (rs, rowNum) ->
                Barista.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .shopId(rs.getInt("shop_id"))
                    .build()
        );
    }

    /**
     *
     * @param barista Update Barista
     */
    @Override
    public Barista updateBarista(Barista barista) {
        try {
            int rows = jdbcTemplate.update(
                    "UPDATE barista SET name = :name, email = :email, shop_id = :shop_id WHERE id = :id",
                    new MapSqlParameterSource()
                            .addValue("name", barista.getName())
                            .addValue("email", barista.getEmail())
                            .addValue("shop_id", barista.getShopId())
                            .addValue("id", barista.getId()));

            if (rows <= 0)
                throw new UpdateFailedException(
                        "Failed to update barista with id: " + barista.getId());

            return searchBarista(barista);
        } catch (Exception e) {
            if (e instanceof UpdateFailedException)
                throw e;
            log.error("Failed to update barista with id: {}", barista.getId(), e);
            throw new UpdateFailedException(
                    "Failed to update barista with id: " + barista.getId());
        }
    }

    /**
     *
     * @param id Unique id to delete Barista
     */
    @Override
    public int deleteBarista(int id) {
        try {
            int rows = jdbcTemplate.update(
                    "DELETE FROM barista WHERE id = :id",
                    new MapSqlParameterSource().addValue("id", id));

            if (rows <= 0)
                throw new UpdateFailedException(
                        "Failed to delete barista with id: " + id);

            return rows;
        } catch (Exception e) {
            if (e instanceof UpdateFailedException)
                throw e;
            log.error("Failed to delete barista with id: {}", id, e);
            throw new UpdateFailedException(
                    "Failed to delete barista with id: " + id);
        }
    }

    /**
     *
     * @param shopId Shop from which Baristas is to be deleted
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteBaristasForShop(int shopId) {
        try {
            int rows = jdbcTemplate.update(
                    "DELETE FROM barista WHERE shop_id = :shop_id",
                    new MapSqlParameterSource().addValue("shop_id", shopId));

            if (rows <= 0)
                throw new UpdateFailedException(
                        "Failed to delete baristas with shop id: " + shopId);

            return rows;
        } catch (Exception e) {
            if (e instanceof UpdateFailedException)
                throw e;
            log.error("Failed to delete baristas with shop id: {}", shopId, e);
            throw new UpdateFailedException(
                    "Failed to delete baristas with shop id: " + shopId);
        }
    }

    /**
     *
     * @param shopId Shop id to get Baristas
     * @return List of Barista for a shop
     */
    @Override
    public List<Barista> getBaristasForShop(int shopId) {
        return jdbcTemplate.query(
            "SELECT id, name, email, shop_id FROM barista WHERE shop_id = :shop_id",
            new MapSqlParameterSource().addValue("shop_id", shopId),
            (rs, rowNum) ->
                Barista.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .shopId(rs.getInt("shop_id"))
                    .build()
        );
    }
}
