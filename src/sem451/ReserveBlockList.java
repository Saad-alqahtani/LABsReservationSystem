package sem451;

import java.time.LocalDate;
import java.util.*;

public class ReserveBlockList {
    // A map to store ReserveBlock objects with a String key
    private Map<String, ReserveBlock> reserveBlocks;

    // Constructor to initialize the reserveBlocks map
    public ReserveBlockList() {
        reserveBlocks = new HashMap<>();
    }

    // Method to add a ReserveBlock to the map
    public boolean addReserveBlock(ReserveBlock block) {
        // Check if the block is null to avoid adding invalid data
        if (block == null) {
            return false;
        }

        // Create a unique key for the block
        String key = createCompositeKey(block);

        // Check if a block with the same key already exists
        if (reserveBlocks.containsKey(key)) {
            return false;
        }

        // Add the block to the map with its unique key
        reserveBlocks.put(key, block);
        return true;
    }

    // Method to remove a ReserveBlock from the map
    public boolean removeReserveBlock(ReserveBlock block) {
        // Check if the block is null
        if (block == null) {
            return false;
        }

        // Create a unique key for the block
        String key = createCompositeKey(block);

        // Check if the block exists in the map
        if (!reserveBlocks.containsKey(key)) {
            return false;
        }

        // Remove the block from the map
        reserveBlocks.remove(key);
        return true;
    }

    // Method to retrieve a ReserveBlock based on date, time, and room
    public ReserveBlock getReserveBlock(LocalDate date, int clock, Room room) {
        // Create a unique key based on date, time, and room
        String key = createCompositeKey(date, clock, room);
        // Retrieve and return the block from the map
        return reserveBlocks.get(key);
    }

    // Method to print all ReserveBlocks
    public void printAllReserveBlocks() {
        for (ReserveBlock block : reserveBlocks.values()) {
            System.out.println(block);
        }
    }

    // Method to count the number of ReserveBlocks
    public int countReserveBlocks() {
        return reserveBlocks.size();
    }

    // Method to clear all ReserveBlocks from the map
    public void clearAllReserveBlocks() {
        reserveBlocks.clear();
    }

    // Helper method to create a composite key for a ReserveBlock
    private String createCompositeKey(ReserveBlock block) {
        return createCompositeKey(block.getDate(), block.getClock(), block.getRoom());
    }

    // Helper method to create a composite key from date, time, and room
    private String createCompositeKey(LocalDate date, int clock, Room room) {
        return date.toString() + "_" + clock + "_" + room.getName();
    }
}
