package me.minion325.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    private InventoryUtils(){}

    private static String OBC = Bukkit.getServer().getClass().getPackage().getName();
    private static String NMS = OBC.replace("org.bukkit.craftbukkit", "net.minecraft.server");

    /**
     * Removes a certain amount of materials from an inventory.
     *
     * @param inventory The inventory that the materials are present in.
     * @param type The material to be removed.
     * @param amount The amount of material to be removed.
     */

    public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }

    /**
     * Checks whether an inventory can contain an itemstack
     * @param itemstack The itemstack you wish to check if it can be added.
     * @param inventory The inventory to be checked.
     * @return The amount of te itemstack that the inventory can hold.
     */

    public static int canContain(ItemStack itemstack, Inventory inventory) {
        int remains = itemstack.getAmount();

        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) == null) {
                return itemstack.getAmount();
            }

            if (inventory.getItem(i) != null && inventory.getItem(i).isSimilar(itemstack) && (inventory.getItem(i).getMaxStackSize() > 1) && inventory.getItem(i).getAmount() < inventory.getItem(i).getMaxStackSize() && inventory.getItem(i).getAmount() < itemstack.getMaxStackSize() && (inventory.getItem(i).getData().equals(itemstack.getData()))) {
                remains -= (inventory.getItem(i).getMaxStackSize() < itemstack.getMaxStackSize() ? inventory.getItem(i).getMaxStackSize() : itemstack.getMaxStackSize()) - inventory.getItem(i).getAmount();
            }

            if (remains <= 0) {
                return itemstack.getAmount();
            }
        }

        return itemstack.getAmount() - remains;
    }

}
