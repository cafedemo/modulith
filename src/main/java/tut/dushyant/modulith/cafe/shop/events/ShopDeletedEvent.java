package tut.dushyant.modulith.cafe.shop.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class ShopDeletedEvent extends CafeEvent {
    public ShopDeletedEvent(int id) {
        super(id);
    }
}
