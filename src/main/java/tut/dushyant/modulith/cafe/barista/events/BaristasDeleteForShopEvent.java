package tut.dushyant.modulith.cafe.barista.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class BaristasDeleteForShopEvent extends CafeEvent {

        public BaristasDeleteForShopEvent(int shopId) {
            super(shopId);
        }
}
