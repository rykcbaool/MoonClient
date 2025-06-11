package dev.ryk.core.impl;

import dev.ryk.MoonClient;
import dev.ryk.api.utils.world.BlockUtil;
import dev.ryk.api.events.eventbus.EventHandler;
import dev.ryk.api.events.eventbus.EventPriority;
import dev.ryk.api.events.impl.TickEvent;
import dev.ryk.mod.modules.impl.render.PlaceRender;

public class ThreadManager {
    public static ClientService clientService;

    public ThreadManager() {
        MoonClient.EVENT_BUS.subscribe(this);
        clientService = new ClientService();
        clientService.setName("AlienClientService");
        clientService.setDaemon(true);
        clientService.start();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(TickEvent event) {
        if (event.isPre()) {
            if (!clientService.isAlive()) {
                clientService = new ClientService();
                clientService.setName("AlienClientService");
                clientService.setDaemon(true);
                clientService.start();
            }
            BlockUtil.placedPos.forEach(pos -> PlaceRender.renderMap.put(pos, PlaceRender.INSTANCE.create(pos)));
            BlockUtil.placedPos.clear();
            MoonClient.SERVER.onUpdate();
            MoonClient.PLAYER.onUpdate();
            MoonClient.MODULE.onUpdate();
            MoonClient.GUI.onUpdate();
            MoonClient.POP.onUpdate();
        }
    }

    public static class ClientService extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (MoonClient.MODULE != null) {
                        MoonClient.MODULE.onThread();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
