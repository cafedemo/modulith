package tut.dushyant.modulith.cafe.shop.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class ShopCreatedEvent extends CafeEvent {
    public ShopCreatedEvent(int id) {
        super(id);
    }
}
