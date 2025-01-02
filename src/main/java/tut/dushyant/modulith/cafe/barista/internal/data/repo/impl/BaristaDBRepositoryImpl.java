package tut.dushyant.modulith.cafe.barista.internal.data.repo.impl;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tut.dushyant.modulith.cafe.barista.internal.data.repo.BaristaDBRepository;
import tut.dushyant.modulith.cafe.common.dto.barista.Barista;

import java.util.List;

/**
 * BaristaDBRepositoryImpl
 */
@Repository
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

        int rows = jdbcTemplate.update(
            "INSERT INTO barista (name, email, shop_id)"
                + " VALUES (:name, :email, :shop_id)",
            new MapSqlParameterSource()
                    .addValue("name", barista.getName())
                    .addValue("email", barista.getEmail())
                    .addValue("shop_id", barista.getShopId()));

        if (rows <=0 )
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to insert barista", 1, rows);

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
        int rows = jdbcTemplate.update(
            "UPDATE barista SET name = :name, email = :email, shop_id = :shop_id WHERE id = :id",
            new MapSqlParameterSource()
                .addValue("name", barista.getName())
                .addValue("email", barista.getEmail())
                .addValue("shop_id", barista.getShopId())
                .addValue("id", barista.getId()));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to update barista", 1, rows);

        return searchBarista(barista);
    }

    /**
     *
     * @param id Unique id to delete Barista
     */
    @Override
    public int deleteBarista(int id) {
        int rows = jdbcTemplate.update(
            "DELETE FROM barista WHERE id = :id",
            new MapSqlParameterSource().addValue("id", id));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to delete barista", 1, rows);

        return rows;
    }

    /**
     *
     * @param shopId Shop from which Baristas is to be deleted
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteBaristasForShop(int shopId) {
        int rows = jdbcTemplate.update(
            "DELETE FROM barista WHERE shop_id = :shop_id",
            new MapSqlParameterSource().addValue("shop_id", shopId));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to delete baristas", 1, rows);

        return rows;
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
