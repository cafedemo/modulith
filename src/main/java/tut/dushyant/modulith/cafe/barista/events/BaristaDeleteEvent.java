package tut.dushyant.modulith.cafe.barista.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class BaristaDeleteEvent extends CafeEvent {
    public BaristaDeleteEvent(int id) {
        super(id);
    }
}
