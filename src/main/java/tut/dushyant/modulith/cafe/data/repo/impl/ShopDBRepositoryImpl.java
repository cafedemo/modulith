package tut.dushyant.modulith.cafe.data.repo.impl;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
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
     * @param shop
     * @return Updated shop
    */
    @Override
    public Shop addShop(Shop shop) {
        int rows = jdbcTemplate.update(
            "INSERT INTO shop (name, address, phone, email)"
                + " VALUES (':name', ':address', ':phone', ':email')",
            new BeanPropertySqlParameterSource(shop));

        if (rows <=0 )
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to insert shop", 1, rows);

        //get updated shop
        return jdbcTemplate.query("SELECT id, name, address, phone, email FROM shop WHERE id = :id",
            new BeanPropertySqlParameterSource(shop), (rs, rowNum) -> {
                return Shop.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .build();
            }).getFirst();
    }

    /**
    *
     * @return List of shops
    */
    @Override
    public List<Shop> getShops() {
        return jdbcTemplate.query(
            "SELECT id, name, address, phone, email FROM shop",
            (rs, rowNum) -> {
                return Shop.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .address(rs.getString("address"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .build();
            });
    }

    /**
    *
     * @param id
     * @return Gives a shop based on id
    */
    @Override
    public Shop getShop(String id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, address, phone, email FROM shop WHERE id = :id",
                new MapSqlParameterSource().addValue("id", id),
                (rs, rowNum) -> {
                    return Shop.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .address(rs.getString("address"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .build();
                });
    }

    /**
    *
     * @param shop
     * @return update shop based on input shop
    */
    @Override
    public Shop updateShop(Shop shop) {
        int rows = jdbcTemplate.update(
            "UPDATE shop SET name = :name, address = :address, phone = :phone, email = :email WHERE id = :id",
            new BeanPropertySqlParameterSource(shop));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to update shop", 1, rows);

        //get updated row
        return jdbcTemplate.queryForObject(
                "SELECT id, name, address, phone, email FROM shop WHERE id = :id",
                new MapSqlParameterSource().addValue("id", shop.id()),
                (rs, rowNum) -> {
                    return Shop.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .address(rs.getString("address"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .build();
                });
    }

    /**
    *
     * @param id Unique identifier of shop
    */
    @Override
    public void deleteShop(String id) {
        int rows = jdbcTemplate.update(
            "DELETE FROM shop WHERE id = :id",
            new MapSqlParameterSource().addValue("id", id));

        if (rows <= 0)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                "Failed to delete shop", 1, rows);
    }
}
