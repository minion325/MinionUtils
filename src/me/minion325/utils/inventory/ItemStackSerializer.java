package me.minion325.utils.inventory;

import me.minion325.utils.other.TextUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackSerializer {

    private ItemStackSerializer(){}

    public static ItemStack deserialize(ConfigurationSection section) {
        try {
            Material type = Material.valueOf(section.getString("type", "STONE"));
            int amount = section.getInt("amount", 1);

            ItemStack stack = new ItemStack(type, amount);

            ItemMeta meta = stack.getItemMeta();

            if (section.contains("name"))
                meta.setDisplayName(TextUtils.colorMessage(section.getString("name")));
            if (section.contains("lore"))
                meta.setLore(TextUtils.colorMessages(section.getStringList("lore")));
            if (section.contains("enchants")) {
                List<String> enchants = section.getStringList("enchants");
                for (String enchant : enchants) {
                    try {
                        String[] enchantAndLvl = enchant.split(":");
                        Enchantment enchantment = Enchantment.getByName(enchantAndLvl[0].toUpperCase());
                        meta.addEnchant(enchantment, Integer.parseInt(enchantAndLvl[1]), true);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            stack.setItemMeta(meta);

            return stack;

        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> serialize(ItemStack itemStack) {
        Map<String, Object> stackData = new HashMap<>();

        ItemMeta meta = itemStack.getItemMeta();

        stackData.put("type", itemStack.getType().name());
        stackData.put("amount", itemStack.getAmount());

        if (meta != null) {
            if (meta.hasDisplayName())
                stackData.put("name", TextUtils.decolorMessage(meta.getDisplayName()));
            if (meta.hasLore())
                stackData.put("lore", TextUtils.decolorMessages(meta.getLore()));
            if (meta.hasEnchants()) {
                List<String> enchants = new ArrayList<>();

                for (Enchantment enchantment : meta.getEnchants().keySet()) {
                    enchants.add(enchantment.getName() + ":" + itemStack.getEnchantmentLevel(enchantment));
                }
                stackData.put("enchants", enchants);
            }
        }
        return stackData;
    }
}