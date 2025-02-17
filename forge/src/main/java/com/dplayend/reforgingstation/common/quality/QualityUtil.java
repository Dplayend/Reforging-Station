package com.dplayend.reforgingstation.common.quality;

import com.dplayend.reforgingstation.handler.HandlerCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;

import java.util.List;

import static com.dplayend.reforgingstation.common.quality.Quality.createQualities;

public class QualityUtil {
    public static boolean helmetQuality(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot().equals(EquipmentSlot.HEAD);
    }

    public static boolean chestPlateQuality(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot().equals(EquipmentSlot.CHEST) ||
                stack.getItem() instanceof ElytraItem && !ModList.get().isLoaded("elytraslot");
    }

    public static boolean leggingsQuality(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot().equals(EquipmentSlot.LEGS);
    }

    public static boolean bootsQuality(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot().equals(EquipmentSlot.FEET);
    }

    public static boolean petQuality(ItemStack stack) {
        return stack.getItem() instanceof HorseArmorItem;
    }

    public static boolean armorQuality(ItemStack stack) {
        return helmetQuality(stack) || chestPlateQuality(stack) || leggingsQuality(stack) || bootsQuality(stack) || petQuality(stack);
    }

    public static boolean shieldQuality(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem;
    }

    public static boolean bowQuality(ItemStack stack) {
        return stack.getItem() instanceof ProjectileWeaponItem bow && !(bow.asItem() instanceof ArrowItem);
    }

    public static boolean fishingRodQuality(ItemStack stack) {
        return stack.getItem() instanceof FishingRodItem;
    }

    public static boolean toolQuality(ItemStack stack) {
        return stack.getItem() instanceof TieredItem ||
                stack.getItem() instanceof TridentItem;
    }

    public static Quality.QualityType getQualityType(ItemStack stack, LivingEntity entity) {
        if (helmetQuality(stack)) return Quality.getQualityType(stack, Quality.helmetQualityList());
        if (chestPlateQuality(stack)) return Quality.getQualityType(stack, Quality.chestplateQualityList());
        if (leggingsQuality(stack)) return Quality.getQualityType(stack, Quality.leggingsQualityList());
        if (bootsQuality(stack)) return Quality.getQualityType(stack, Quality.bootsQualityList());
        if (petQuality(stack)) return Quality.getQualityType(stack, Quality.petQualityList());
        if (shieldQuality(stack)) return Quality.getQualityType(stack, Quality.shieldQualityList());
        if (bowQuality(stack)) return Quality.getQualityType(stack, Quality.bowQualityList());
        if (fishingRodQuality(stack)) return Quality.getQualityType(stack, Quality.rodQualityList());
        if (toolQuality(stack)) return Quality.getQualityType(stack, Quality.toolQualityList());
        if (HandlerCurios.accessoryQuality(stack) && entity instanceof ServerPlayer) return Quality.getQualityType(stack, Quality.accessoryQualityList());
        return null;
    }

    public static String getQuality(ItemStack stack) {
        if (helmetQuality(stack)) return "helmet";
        if (chestPlateQuality(stack)) return "chestplate";
        if (leggingsQuality(stack)) return "leggings";
        if (bootsQuality(stack)) return "boots";
        if (petQuality(stack)) return "pet";
        if (shieldQuality(stack)) return "shield";
        if (bowQuality(stack)) return "bow";
        if (fishingRodQuality(stack)) return "rod";
        if (toolQuality(stack)) return "tool";
        if (HandlerCurios.accessoryQuality(stack)) return "accessory";
        return "";
    }

    public static void createQuality(ItemStack stack) {
        switch (QualityUtil.getQuality(stack)) {
            case "helmet" : createQualities(stack, Quality.getRandomQuality(Quality.helmetQualityList()).quality()); break;
            case "chestplate" : createQualities(stack, Quality.getRandomQuality(Quality.chestplateQualityList()).quality()); break;
            case "leggings" : createQualities(stack, Quality.getRandomQuality(Quality.leggingsQualityList()).quality()); break;
            case "boots" : createQualities(stack, Quality.getRandomQuality(Quality.bootsQualityList()).quality()); break;
            case "shield" : createQualities(stack, Quality.getRandomQuality(Quality.shieldQualityList()).quality()); break;
            case "pet" : createQualities(stack, Quality.getRandomQuality(Quality.petQualityList()).quality()); break;
            case "bow" : createQualities(stack, Quality.getRandomQuality(Quality.bowQualityList()).quality()); break;
            case "rod" : createQualities(stack, Quality.getRandomQuality(Quality.rodQualityList()).quality()); break;
            case "tool" : createQualities(stack, Quality.getRandomQuality(Quality.toolQualityList()).quality()); break;
            case "accessory" : HandlerCurios.createAccessoryQuality(stack); break;
        }
    }

    public static void createTooltip(ItemStack stack, List<Component> list, boolean includeNormalQuality) {
        if (stack.hasTag()) {
            if (stack.getTag() != null && !stack.getTag().getString("quality").isEmpty()) {
                switch (QualityUtil.getQuality(stack)) {
                    case "helmet" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.helmetQualityList()), Component.translatable("item.modifiers.head"), includeNormalQuality); break;
                    case "chestplate" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.chestplateQualityList()), Component.translatable("item.modifiers.chest"), includeNormalQuality); break;
                    case "leggings" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.leggingsQualityList()), Component.translatable("item.modifiers.legs"), includeNormalQuality); break;
                    case "boots" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.bootsQualityList()), Component.translatable("item.modifiers.feet"), includeNormalQuality); break;
                    case "shield" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.shieldQualityList()), Component.translatable("item.modifiers.offhand"), includeNormalQuality); break;
                    case "pet" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.petQualityList()), Component.translatable("item.modifiers.entity", Component.translatable("entity.minecraft.horse")), includeNormalQuality); break;
                    case "bow" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.bowQualityList()), Component.translatable("item.modifiers.mainhand"), includeNormalQuality); break;
                    case "rod" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.rodQualityList()), Component.translatable("item.modifiers.mainhand"), includeNormalQuality); break;
                    case "tool" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.toolQualityList()), Component.translatable("item.modifiers.mainhand"), includeNormalQuality); break;
                    case "accessory" : qualityTooltip(list, stack, Quality.getQualityType(stack, Quality.accessoryQualityList()), Component.translatable("curios.modifiers.curio"), includeNormalQuality); break;
                }
            }
        }
    }

    private static void qualityTooltip(List<Component> list, ItemStack stack, Quality.QualityType qualityType, MutableComponent component, boolean itemFormat) {
        if (qualityType != null) {
            if (!itemFormat) list.add(Component.empty());
            list.add(Component.translatable("item.tooltip.quality", Component.translatable("item.quality." + stack.getTag().getString("quality")).withStyle(qualityType.formatting())).withStyle(ChatFormatting.GRAY));
            if (qualityType.attributes().length > 0) {
                list.add(component.withStyle(ChatFormatting.GRAY));
                appendHoverText(list, qualityType.attributes());
            }
        }
    }

    private static void appendHoverText(List<Component> list, Quality.Modifier... attributes) {
        for (Quality.Modifier provider : attributes) {
            if (provider.attribute() != null) {
                double d0 = provider.getAmount();
                double d1;
                if (Quality.attributeOperation(provider.attribute()) != AttributeModifier.Operation.MULTIPLY_BASE && Quality.attributeOperation(provider.attribute()) != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = provider.getAmount();
                } else {
                    d1 = provider.getAmount() * 100.0D;
                }
                if (d0 > 0.0D) {
                    list.add(Component.translatable("attribute.modifier.plus." + Quality.attributeOperation(provider.attribute()).toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(provider.attribute().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 *= -1.0D;
                    list.add(Component.translatable("attribute.modifier.take." + Quality.attributeOperation(provider.attribute()).toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(provider.attribute().getDescriptionId())).withStyle(ChatFormatting.RED));
                }
            }
        }
    }
}
