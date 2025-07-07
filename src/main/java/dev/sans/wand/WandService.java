package dev.sans.wand;

import dev.sans.inventory.InventoryService;

import java.util.UUID;

public class WandService {

    private final WandRepository wandRepository;
    private final InventoryService inventoryService;

    public WandService(WandRepository wandRepository, InventoryService inventoryService) {
        this.wandRepository = wandRepository;
        this.inventoryService = inventoryService;
    }

    public Wand create(String wood, String core, int lengthInCm) {
        if (wood.isBlank()) throw new IllegalArgumentException("Wood is required");

        Wand wand = new Wand(UUID.randomUUID().toString(), wood, core, lengthInCm);

        boolean isCreated = wandRepository.save(wand);

        if (!isCreated) throw new WandOperationException("Wand not created");

        inventoryService.addEntry(wand);

        return wand;
    }
}
