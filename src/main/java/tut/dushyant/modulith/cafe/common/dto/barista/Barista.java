package tut.dushyant.modulith.cafe.common.dto.barista;

import org.springframework.modulith.NamedInterface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Barista DTO
 */
@NamedInterface("barista-dto")
@Builder
@Setter
@Getter
@AllArgsConstructor
public class Barista {
    private int id;
    private String name;
    private String email;
    private int shopId;    
}
