package net.minestom.generators;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minestom.datagen.DataGenerator;

public final class EnchantmentGenerator extends DataGenerator {
    @Override
    public JsonObject generate() {
        JsonObject enchantments = new JsonObject();
        for (var entry : Registry.ENCHANTMENT.entrySet()) {
            final var location = entry.getKey().location();
            final var enchantment = entry.getValue();
            JsonObject enchantmentJson = new JsonObject();
            enchantmentJson.addProperty("id", Registry.ENCHANTMENT.getId(enchantment));
            enchantmentJson.addProperty("translationKey", enchantment.getDescriptionId());
            enchantmentJson.addProperty("maxLevel", enchantment.getMaxLevel());
            enchantmentJson.addProperty("rarity", enchantment.getRarity().toString());
            addDefaultable(enchantmentJson, "curse", enchantment.isCurse(), false);
            addDefaultable(enchantmentJson, "discoverable", enchantment.isDiscoverable(), true);
            addDefaultable(enchantmentJson, "tradeable", enchantment.isTradeable(), true);
            addDefaultable(enchantmentJson, "treasureOnly", enchantment.isTreasureOnly(), false);
            enchantmentJson.addProperty("category", enchantment.category.name());
            enchantments.add(location.toString(), enchantmentJson);
        }
        return enchantments;
    }
}
