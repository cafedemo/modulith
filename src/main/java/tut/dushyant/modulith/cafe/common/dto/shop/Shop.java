package tut.dushyant.modulith.cafe.common.dto.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.modulith.NamedInterface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tut.dushyant.modulith.cafe.common.dto.barista.Barista;

/**
 * Shop DTO
 */
@Builder
@AllArgsConstructor
@Setter
@Getter
@NamedInterface("shop-dto")
public class Shop {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    @Builder.Default
    private List<Barista> baristas = new ArrayList<>();

    public Shop() {
        this.id = 0;
    }
}
