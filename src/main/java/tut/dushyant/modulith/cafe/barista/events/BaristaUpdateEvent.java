package tut.dushyant.modulith.cafe.barista.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class BaristaUpdateEvent extends CafeEvent {
    public BaristaUpdateEvent(int id) {
        super(id);
    }
}
