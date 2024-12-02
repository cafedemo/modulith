package tut.dushyant.modulith.cafe.data.repo.impl;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tut.dushyant.modulith.cafe.data.dto.Shop;
import tut.dushyant.modulith.cafe.data.repo.ShopDBRepository;

import java.util.List;

@Repository
public class ShopDBRepositoryImpl implements ShopDBRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ShopDBRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
    *
     * @param shop Details of shop to be added
     */
    @Override
    @Transactional
    public void addShop(Shop shop) {
    int rows =
        jdbcTemplate.update(
            "INSERT INTO shop (name, address, phone, email)"
                + " VALUES (:name, :address, :phone, :email)",
            new MapSqlParameterSource()
                    .addValue("name", shop.name())
                    .addValue("address", shop.address())
                    .addValue("phone", shop.phone())
                    .addValue("email", shop.email()));

        if (rows <=0 )
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to insert shop", 1, rows);
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
    *
     * @param shop Shop details to search database
     * @return Shop from database based on input shop
    */
    @Override
    public Shop getShop(Shop shop) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, address, phone, email FROM shop WHERE " +
                        "name = :name AND address = :address AND phone = :phone AND email = :email",
                new MapSqlParameterSource().addValue("name", shop.name())
                        .addValue("address", shop.address())
                        .addValue("phone", shop.phone())
                        .addValue("email", shop.email()),
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
    public void updateShop(Shop shop) {
    int rows =
        jdbcTemplate.update(
            "UPDATE shop SET name = :name, address = :address, phone = :phone, email = :email WHERE id = :id",
            new MapSqlParameterSource()
                    .addValue("name", shop.name())
                    .addValue("address", shop.address())
                    .addValue("phone", shop.phone())
                    .addValue("email", shop.email())
                    .addValue("id", shop.id()));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to update shop", 1, rows);

        //get updated row
        jdbcTemplate.queryForObject(
                "SELECT id, name, address, phone, email FROM shop WHERE id = :id",
                new MapSqlParameterSource().addValue("id", shop.id()),
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
    *
     * @param id Unique identifier of shop
    */
    @Override
    @Transactional
    public void deleteShop(String id) {
        int rows = jdbcTemplate.update(
            "DELETE FROM shop WHERE id = :id",
            new MapSqlParameterSource().addValue("id", id));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to delete shop", 1, rows);
    }
}
