package tut.dushyant.modulith.cafe.shop.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class ShopUpdateEvent extends CafeEvent {
    public ShopUpdateEvent(int id) {
        super(id);
    }
}
