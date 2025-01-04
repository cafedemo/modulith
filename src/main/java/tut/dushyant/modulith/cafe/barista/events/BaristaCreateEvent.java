package tut.dushyant.modulith.cafe.barista.events;

import tut.dushyant.modulith.cafe.common.event.CafeEvent;

public class BaristaCreateEvent extends CafeEvent {
    public BaristaCreateEvent(int id) {
        super(id);
    }
}
