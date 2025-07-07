package dev.sans.inventory;

public class InventoryService {

    public <T> void addEntry(T product) {
        System.out.println(product + " added to inventory");
    }
}
