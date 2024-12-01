package tut.dushyant.modulith.cafe.data.dto;

import lombok.Builder;

@Builder
public record Shop (
        int id,
    String name,
    String address,
    String phone,
    String email
) {
    public static class ShopBuilder {
        private int id = 0;
    }
}
