package tut.dushyant.modulith.cafe.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import tut.dushyant.modulith.cafe.barista.events.BaristaCreateEvent;
import tut.dushyant.modulith.cafe.barista.events.BaristaDeleteEvent;
import tut.dushyant.modulith.cafe.barista.events.BaristaUpdateEvent;
import tut.dushyant.modulith.cafe.barista.events.BaristasDeleteForShopEvent;
import tut.dushyant.modulith.cafe.common.event.CafeEvent;
import tut.dushyant.modulith.cafe.shop.events.ShopCreatedEvent;
import tut.dushyant.modulith.cafe.shop.events.ShopDeletedEvent;
import tut.dushyant.modulith.cafe.shop.events.ShopUpdateEvent;

@Component
@Slf4j
public class CafeLogEvent {

    @ApplicationModuleListener
    public void onEvent(CafeEvent evt) {
        switch (evt) {
            case ShopCreatedEvent shopEvt -> log.atInfo().log("[Event Captured] :: Shop created: {}", evt.getId());
            case ShopDeletedEvent shopEvt -> log.atInfo().log("[Event Captured] :: Shop deleted: {}", evt.getId());
            case ShopUpdateEvent shopEvt -> log.atInfo().log("[Event Captured] :: Shop updated: {}", evt.getId());
            case BaristaCreateEvent baristaEvt -> log.atInfo().log("[Event Captured] :: Barista created: {}", evt.getId());
            case BaristaUpdateEvent baristaUpdateEvent -> log.atInfo().log("[Event Captured] :: Barista updated: {}", evt.getId());
            case BaristaDeleteEvent baristaDeleteEvent -> log.atInfo().log("[Event Captured] :: Barista deleted: {}", evt.getId());
            case BaristasDeleteForShopEvent baristasDeleteForShopEvent -> log.atInfo().log("[Event Captured] :: Baristas deleted for shop: {}", evt.getId());
            default -> log.atWarn().log("[Event Captured] :: Unknown event type: " + evt.getClass().getName());
        }
    }
}
