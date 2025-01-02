package tut.dushyant.modulith.cafe.shop.internal.data.repo.impl;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tut.dushyant.modulith.cafe.common.dto.shop.Shop;
import tut.dushyant.modulith.cafe.shop.internal.data.repo.ShopDBRepository;

import java.util.List;

/**
 * ShopDBRepositoryImpl is the implementation of ShopDBRepository
 */
@Repository
public class ShopDBRepositoryImpl implements ShopDBRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Constructor
     * @param jdbcTemplate Database connection details
     */
    public ShopDBRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
    *
     * @param shop Details of shop to be added
     */
    @Override
    @Transactional
    public Shop addShop(Shop shop) {
    int rows =
        jdbcTemplate.update(
            "INSERT INTO shop (name, address, phone, email)"
                + " VALUES (:name, :address, :phone, :email)",
            new MapSqlParameterSource()
                    .addValue("name", shop.getName())
                    .addValue("address", shop.getAddress())
                    .addValue("phone", shop.getPhone())
                    .addValue("email", shop.getEmail()));

        if (rows <=0 )
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to insert shop", 1, rows);

        return jdbcTemplate.query(
            "SELECT id, name, address, phone, email FROM shop WHERE name = :name AND address = :address AND phone = :phone AND email = :email",
            new MapSqlParameterSource()
                    .addValue("name", shop.getName())
                    .addValue("address", shop.getAddress())
                    .addValue("phone", shop.getPhone())
                    .addValue("email", shop.getEmail()),
            (rs, rowNum) -> Shop.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .build()
        ).get(0);
    }

    /**
    *
     * @return List of shops
    */
    @Override
    public List<Shop> getShops() {
        return jdbcTemplate.query(
            "SELECT id, name, address, phone, email FROM shop",
            (rs, rowNum) ->
                Shop.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .build()
        );
    }

    /**
    *
     * @param id ID of shop to search for
     * @return Gives a shop based on id
    */
    @Override
    public Shop getShop(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, address, phone, email FROM shop WHERE id = :id",
                new MapSqlParameterSource().addValue("id", id),
                (rs, rowNum) -> Shop.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .address(rs.getString("address"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .build()
                );
    }

    /**
     * @param shop Shop details to update in database
     */
    @Override
    @Transactional
    public Shop updateShop(Shop shop) {
        int rows = jdbcTemplate.update(
            "UPDATE shop SET name = :name, address = :address, phone = :phone, email = :email WHERE id = :id",
            new MapSqlParameterSource()
                    .addValue("id", shop.getId())
                    .addValue("name", shop.getName())
                    .addValue("address", shop.getAddress())
                    .addValue("phone", shop.getPhone())
                    .addValue("email", shop.getEmail()));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to update shop", 1, rows);

        //get updated row
        return jdbcTemplate.queryForObject(
                "SELECT name, address, phone, email FROM shop WHERE id = :id",
                new MapSqlParameterSource().addValue("id", shop.getId()),
                (rs, rowNum) -> Shop.builder()
                            .id(shop.getId())
                            .name(rs.getString("name"))
                            .address(rs.getString("address"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .build()
        );
    }

    /**
    *
     * @param id Unique identifier of shop
    */
    @Override
    @Transactional
    public void deleteShop(int id) {
        int rows = jdbcTemplate.update(
            "DELETE FROM shop WHERE id = :id",
            new MapSqlParameterSource().addValue("id", id));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to delete shop", 1, rows);
    }
}
