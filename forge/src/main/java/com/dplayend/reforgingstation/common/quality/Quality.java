package com.dplayend.reforgingstation.common.quality;

import com.dplayend.reforgingstation.handler.HandlerConfig;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import com.dplayend.reforgingstation.registry.RegistryAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Quality {
    public static Attribute MAX_HEALTH = Attributes.MAX_HEALTH;
    public static Attribute ARMOR_TOUGHNESS = Attributes.ARMOR_TOUGHNESS;
    public static Attribute ARMOR = Attributes.ARMOR;
    public static Attribute MOVEMENT_SPEED = Attributes.MOVEMENT_SPEED;
    public static Attribute LUCK = Attributes.LUCK;
    public static Attribute ATTACK_SPEED = Attributes.ATTACK_SPEED;
    public static Attribute ATTACK_DAMAGE = Attributes.ATTACK_DAMAGE;
    public static Attribute KNOCKBACK_RESISTANCE = Attributes.KNOCKBACK_RESISTANCE;
    public static Attribute STEP_HEIGHT = ForgeMod.STEP_HEIGHT_ADDITION.get();
    public static Attribute ENTITY_REACH = ForgeMod.ENTITY_REACH.get();
    public static Attribute BLOCK_REACH = ForgeMod.BLOCK_REACH.get();
    public static Attribute JUMP_HEIGHT = RegistryAttributes.JUMP_HEIGHT.get();
    public static Attribute DIG_SPEED = RegistryAttributes.DIG_SPEED.get();
    public static Attribute PROJECTILE_DAMAGE = RegistryAttributes.PROJECTILE_DAMAGE.get();
    public static Attribute DAMAGE_RESISTANCE = RegistryAttributes.DAMAGE_RESISTANCE.get();
    public static Attribute CRITICAL_DAMAGE = RegistryAttributes.CRITICAL_DAMAGE.get();
    public static Attribute MAGIC_RESIST = RegistryAttributes.MAGIC_RESIST.get();

    public static AttributeModifier.Operation attributeOperation(Attribute attribute) {
        if (attribute.equals(ARMOR) || attribute.equals(ARMOR_TOUGHNESS) || attribute.equals(MAGIC_RESIST) || attribute.equals(JUMP_HEIGHT) || attribute.equals(STEP_HEIGHT) || attribute.equals(LUCK) || attribute.equals(MAX_HEALTH) || attribute.equals(BLOCK_REACH) || attribute.equals(ENTITY_REACH)) {
            return AttributeModifier.Operation.ADDITION;
        } else {
            return AttributeModifier.Operation.MULTIPLY_TOTAL;
        }
    }

    public static Map<Attribute, String> attributeMap() {
        Map<Attribute, String> map = new HashMap<>();
        map.put(MAX_HEALTH, "3176cb2e-5a6f-4f19-a2dc-f86982c98300");
        map.put(ARMOR_TOUGHNESS, "3176cb2e-5a6f-4f19-a2dc-f86982c98301");
        map.put(ARMOR, "3176cb2e-5a6f-4f19-a2dc-f86982c98302");
        map.put(MOVEMENT_SPEED, "3176cb2e-5a6f-4f19-a2dc-f86982c98303");
        map.put(LUCK, "3176cb2e-5a6f-4f19-a2dc-f86982c98304");
        map.put(ATTACK_SPEED, "3176cb2e-5a6f-4f19-a2dc-f86982c98305");
        map.put(ATTACK_DAMAGE, "3176cb2e-5a6f-4f19-a2dc-f86982c98306");
        map.put(KNOCKBACK_RESISTANCE, "3176cb2e-5a6f-4f19-a2dc-f86982c98307");
        map.put(STEP_HEIGHT, "3176cb2e-5a6f-4f19-a2dc-f86982c98308");
        map.put(ENTITY_REACH, "3176cb2e-5a6f-4f19-a2dc-f86982c98309");
        map.put(BLOCK_REACH, "3176cb2e-5a6f-4f19-a2dc-f86982c98310");
        map.put(JUMP_HEIGHT, "3176cb2e-5a6f-4f19-a2dc-f86982c98311");
        map.put(DIG_SPEED, "3176cb2e-5a6f-4f19-a2dc-f86982c98312");
        map.put(PROJECTILE_DAMAGE, "3176cb2e-5a6f-4f19-a2dc-f86982c98313");
        map.put(DAMAGE_RESISTANCE, "3176cb2e-5a6f-4f19-a2dc-f86982c98314");
        map.put(CRITICAL_DAMAGE, "3176cb2e-5a6f-4f19-a2dc-f86982c98315");
        map.put(MAGIC_RESIST, "3176cb2e-5a6f-4f19-a2dc-f86982c98316");
        return map;
    }

    public static void createQualities(ItemStack invSlot, String randomQuality) {
        if (invSlot.getOrCreateTag().getString("quality").isEmpty()) {
            invSlot.getOrCreateTag().putString("quality", randomQuality);
        }
    }

    public record SimpleModifier(ItemStack stack, boolean qualityType) {}

    public static void addModifiers(LivingEntity livingEntity) {
        List<SimpleModifier> map = new ArrayList<>();
        map.add(new SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.toolQuality(livingEntity.getMainHandItem())));
        map.add(new SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.bowQuality(livingEntity.getMainHandItem())));
        map.add(new SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.fishingRodQuality(livingEntity.getMainHandItem())));
        map.add(new SimpleModifier(livingEntity.getOffhandItem(), QualityUtil.shieldQuality(livingEntity.getOffhandItem())));
        livingEntity.getArmorSlots().forEach(stack -> map.add(new SimpleModifier(stack, QualityUtil.armorQuality(stack))));
        if (HandlerCurios.isModLoaded()) HandlerCurios.accessoryStackList(livingEntity).forEach(stack -> map.add(new SimpleModifier(stack, HandlerCurios.accessoryQuality(livingEntity))));

        attributeMap().forEach((attribute, uuid) -> {
            AtomicReference<Double> value = new AtomicReference<>(0.0D);
            AtomicReference<AttributeModifier.Operation> operation = new AtomicReference<>(AttributeModifier.Operation.ADDITION);

            map.forEach(simpleModifier -> {
                ItemStack stack = simpleModifier.stack;
                boolean quality = simpleModifier.qualityType;
                Quality.QualityType type = QualityUtil.getQualityType(stack, livingEntity);
                if (type != null && !stack.isEmpty() && stack.getTag() != null && !stack.getTag().getString("quality").isEmpty() && quality) {
                    for (Modifier provider : type.attributes) {
                        if (provider.attribute() != null && provider.attribute().equals(attribute)) {
                            value.updateAndGet(aDouble -> ((aDouble + provider.getAmount())));
                            operation.set(attributeOperation(provider.attribute()));
                        }
                    }
                }
            });

            AttributeModifier attackModifier = new AttributeModifier(UUID.fromString(uuid), attribute::getDescriptionId, value.get(), operation.get());
            AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.removePermanentModifier(attackModifier.getId());
                if (!attributeInstance.hasModifier(attackModifier)) {
                    attributeInstance.addPermanentModifier(attackModifier);
                }
            }
        });
    }

    public static boolean matchingMaterial(ItemStack stack, ItemStack materialStack) {
        boolean flag = false;

        if (stack.getItem() instanceof ArmorItem armorItem) {
            for (ItemStack ingredient : armorItem.getMaterial().getRepairIngredient().getItems()) {
                if (ingredient.is(materialStack.getItem())) {
                    flag = true;
                }
            }
        }

        if (stack.getItem() instanceof ElytraItem armorItem && armorItem.isValidRepairItem(stack, materialStack)) {
            flag = true;
        }

        if (stack.getItem() instanceof TieredItem tieredItem) {
            for (ItemStack ingredient : tieredItem.getTier().getRepairIngredient().getItems()) {
                if (ingredient.is(materialStack.getItem())) {
                    flag = true;
                }
            }
        }

        String materialValue = ForgeRegistries.ITEMS.getKey(materialStack.getItem()).toString();
        String stackValue = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
        for (QualityConfig.Quality data : QualityConfig.DATA) {
            if (data.material.equals(materialValue)) {
                for (String material : data.values) {
                    if (material.equals(stackValue)) {
                        flag = true;
                        break;
                    }
                }
            }
        }

        return flag;
    }

    public static QualityType getQualityType(ItemStack stack, List<QualityType> list) {
        for (QualityType qualityType : list) {
            if (stack.getOrCreateTag().getString("quality").equals(qualityType.quality)) return qualityType;
        }
        return null;
    }

    public static QualityType getRandomQuality(List<QualityType> list) {
        return list.get(((int) Math.floor(Math.random() * list.size())));
    }

    public static Item getAllMatchingMaterial() {
        return ForgeRegistries.ITEMS.getHolder(new ResourceLocation(HandlerConfig.DATA.allMatchingMaterial.split(":")[0], HandlerConfig.DATA.allMatchingMaterial.split(":")[1])).get().get();
    }

    public static List<QualityType> helmetQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("crumbled", ChatFormatting.DARK_RED, new Modifier(ARMOR, -2.0D), new Modifier(MAGIC_RESIST, -1.5D), new Modifier(ARMOR_TOUGHNESS, -1.0D)));
        list.add(new QualityType("dented", ChatFormatting.DARK_GRAY, new Modifier(ARMOR, -3.0D)));
        list.add(new QualityType("heavy", ChatFormatting.RED, new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("thick", ChatFormatting.YELLOW, new Modifier(ARMOR, 2.0D), new Modifier(MOVEMENT_SPEED, -0.05D)));
        list.add(new QualityType("tough", ChatFormatting.BLUE, new Modifier(ARMOR_TOUGHNESS, 1.0D)));
        list.add(new QualityType("protective", ChatFormatting.BLUE, new Modifier(ARMOR, 2.0D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("lucky", ChatFormatting.AQUA, new Modifier(LUCK, 0.5D)));
        list.add(new QualityType("masterful", ChatFormatting.LIGHT_PURPLE, new Modifier(ARMOR, 2.0D), new Modifier(ARMOR_TOUGHNESS, 1.0D), new Modifier(LUCK, 0.5D)));
        return list;
    }

    public static List<QualityType> chestplateQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("crumbled", ChatFormatting.DARK_RED, new Modifier(ARMOR, -3.0D), new Modifier(MAGIC_RESIST, -2.0D), new Modifier(ARMOR_TOUGHNESS, -1.0D)));
        list.add(new QualityType("cumbersome", ChatFormatting.DARK_GRAY, new Modifier(DIG_SPEED, -0.1D), new Modifier(ATTACK_SPEED, -0.1D)));
        list.add(new QualityType("dented", ChatFormatting.DARK_GRAY, new Modifier(ARMOR, -2.0D)));
        list.add(new QualityType("heavy", ChatFormatting.RED, new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("thick", ChatFormatting.YELLOW, new Modifier(ARMOR, 1.0D), new Modifier(MOVEMENT_SPEED, -0.05D)));
        list.add(new QualityType("tough", ChatFormatting.BLUE, new Modifier(ARMOR_TOUGHNESS, 1.0D)));
        list.add(new QualityType("protective", ChatFormatting.BLUE, new Modifier(ARMOR, 1.0D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("solid", ChatFormatting.BLUE, new Modifier(KNOCKBACK_RESISTANCE, 0.5D)));
        list.add(new QualityType("masterful", ChatFormatting.LIGHT_PURPLE, new Modifier(ARMOR, 1.0D), new Modifier(ARMOR_TOUGHNESS, 1.0D), new Modifier(KNOCKBACK_RESISTANCE, 0.5D)));
        return list;
    }

    public static List<QualityType> leggingsQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("crumbled", ChatFormatting.DARK_RED, new Modifier(ARMOR, -1.5D), new Modifier(ARMOR_TOUGHNESS, -1.0D), new Modifier(MAGIC_RESIST, -1.0D)));
        list.add(new QualityType("dented", ChatFormatting.DARK_GRAY, new Modifier(ARMOR, -1.0D)));
        list.add(new QualityType("heavy", ChatFormatting.RED, new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("thick", ChatFormatting.YELLOW, new Modifier(ARMOR, 1.0D), new Modifier(MOVEMENT_SPEED, -0.05D)));
        list.add(new QualityType("tough", ChatFormatting.BLUE, new Modifier(ARMOR_TOUGHNESS, 1.0D)));
        list.add(new QualityType("protective", ChatFormatting.BLUE, new Modifier(ARMOR, 1.0D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("springy", ChatFormatting.BLUE, new Modifier(JUMP_HEIGHT, 0.5D)));
        list.add(new QualityType("masterful", ChatFormatting.LIGHT_PURPLE, new Modifier(ARMOR, 1.0D), new Modifier(ARMOR_TOUGHNESS, 1.0D), new Modifier(JUMP_HEIGHT, 0.5D)));
        return list;
    }

    public static List<QualityType> bootsQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("crumbled", ChatFormatting.DARK_RED, new Modifier(ARMOR, -1.0D), new Modifier(ARMOR_TOUGHNESS, -0.5D), new Modifier(MAGIC_RESIST, -0.5D)));
        list.add(new QualityType("dented", ChatFormatting.DARK_GRAY, new Modifier(ARMOR, -0.5D)));
        list.add(new QualityType("heavy", ChatFormatting.RED, new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("thick", ChatFormatting.YELLOW, new Modifier(ARMOR, 1.0D), new Modifier(MOVEMENT_SPEED, -0.05D)));
        list.add(new QualityType("tough", ChatFormatting.BLUE, new Modifier(ARMOR_TOUGHNESS, -1.0D)));
        list.add(new QualityType("protective", ChatFormatting.BLUE, new Modifier(ARMOR, 1.0D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("tall", ChatFormatting.BLUE, new Modifier(STEP_HEIGHT, 0.5D)));
        list.add(new QualityType("speedy", ChatFormatting.BLUE, new Modifier(MOVEMENT_SPEED, 0.1D)));
        list.add(new QualityType("masterful", ChatFormatting.LIGHT_PURPLE, new Modifier(ARMOR, 1.0D), new Modifier(ARMOR_TOUGHNESS, 1.0D), new Modifier(MOVEMENT_SPEED, 0.1D), new Modifier(STEP_HEIGHT, 0.5D)));
        return list;
    }

    public static List<QualityType> shieldQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("worthless", ChatFormatting.DARK_RED, new Modifier(ARMOR, -1.5D), new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("heavy", ChatFormatting.RED, new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("damaged", ChatFormatting.RED, new Modifier(ARMOR, -1.5D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("thick", ChatFormatting.YELLOW, new Modifier(ARMOR, 0.5D), new Modifier(MOVEMENT_SPEED, -0.05D)));
        list.add(new QualityType("protective", ChatFormatting.BLUE, new Modifier(ARMOR, 1.5D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("solid", ChatFormatting.BLUE, new Modifier(KNOCKBACK_RESISTANCE, 1.0D)));
        list.add(new QualityType("light", ChatFormatting.AQUA, new Modifier(MOVEMENT_SPEED, 0.1D)));
        list.add(new QualityType("legendary", ChatFormatting.LIGHT_PURPLE, new Modifier(ARMOR, 1.0D), new Modifier(KNOCKBACK_RESISTANCE, 0.5D), new Modifier(MAGIC_RESIST, 0.5D)));
        return list;
    }

    public static List<QualityType> petQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("crumbled", ChatFormatting.DARK_RED, new Modifier(ARMOR, -2.0D), new Modifier(MAGIC_RESIST, -1.0D)));
        list.add(new QualityType("dented", ChatFormatting.DARK_GRAY, new Modifier(ARMOR, -1.0D)));
        list.add(new QualityType("heavy", ChatFormatting.RED, new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("thick", ChatFormatting.YELLOW, new Modifier(ARMOR, 1.0D), new Modifier(MOVEMENT_SPEED, -0.1D)));
        list.add(new QualityType("protective", ChatFormatting.BLUE, new Modifier(ARMOR, 1.5D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("speedy", ChatFormatting.BLUE, new Modifier(MOVEMENT_SPEED, 2.0D)));
        list.add(new QualityType("masterful", ChatFormatting.LIGHT_PURPLE, new Modifier(ARMOR, 2.0D), new Modifier(MOVEMENT_SPEED, 0.15D)));
        return list;
    }

    public static List<QualityType> bowQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("awful", ChatFormatting.DARK_RED, new Modifier(PROJECTILE_DAMAGE, -0.15D)));
        list.add(new QualityType("awkward", ChatFormatting.RED, new Modifier(PROJECTILE_DAMAGE, -0.05D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("deadly", ChatFormatting.BLUE, new Modifier(PROJECTILE_DAMAGE, 0.05D)));
        list.add(new QualityType("powerful", ChatFormatting.AQUA, new Modifier(PROJECTILE_DAMAGE, 0.1D)));
        list.add(new QualityType("unreal", ChatFormatting.GOLD, new Modifier(PROJECTILE_DAMAGE, 0.15D)));
        return list;
    }

    public static List<QualityType> rodQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("unlucky", ChatFormatting.RED, new Modifier(LUCK, -0.5D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("lucky", ChatFormatting.AQUA, new Modifier(LUCK, 0.5D)));
        return list;
    }

    public static List<QualityType> toolQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("worthless", ChatFormatting.DARK_RED, new Modifier(ATTACK_DAMAGE, -0.1D), new Modifier(ATTACK_SPEED, -0.1D), new Modifier(ENTITY_REACH, -1.0D)));
        list.add(new QualityType("terrible", ChatFormatting.DARK_RED, new Modifier(DIG_SPEED, -0.1D), new Modifier(BLOCK_REACH, -1.0D)));
        list.add(new QualityType("broken", ChatFormatting.DARK_GRAY, new Modifier(DIG_SPEED, -0.05D), new Modifier(BLOCK_REACH, -0.5D)));
        list.add(new QualityType("bulky", ChatFormatting.DARK_GRAY, new Modifier(ATTACK_DAMAGE, -0.05D), new Modifier(ATTACK_SPEED, -0.05D)));
        list.add(new QualityType("rusted", ChatFormatting.RED, new Modifier(ATTACK_DAMAGE, -0.1D)));
        list.add(new QualityType("clumsy", ChatFormatting.RED, new Modifier(ATTACK_SPEED, -0.1D)));
        list.add(new QualityType("chipped", ChatFormatting.RED, new Modifier(DIG_SPEED, -0.1D)));
        list.add(new QualityType("short", ChatFormatting.RED, new Modifier(ENTITY_REACH, -1.0D)));
        list.add(new QualityType("small", ChatFormatting.RED, new Modifier(BLOCK_REACH, -1.0D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("broad", ChatFormatting.YELLOW, new Modifier(ATTACK_DAMAGE, -0.1D), new Modifier(ENTITY_REACH, 0.5D)));
        list.add(new QualityType("thin", ChatFormatting.YELLOW, new Modifier(ATTACK_DAMAGE, 0.1D), new Modifier(ENTITY_REACH, -0.5D)));
        list.add(new QualityType("pokey", ChatFormatting.YELLOW, new Modifier(ATTACK_DAMAGE, 0.1D), new Modifier(ATTACK_SPEED, -0.1D)));
        list.add(new QualityType("vicious", ChatFormatting.YELLOW, new Modifier(ATTACK_DAMAGE, 0.15D), new Modifier(ENTITY_REACH, -0.5D)));
        list.add(new QualityType("long", ChatFormatting.BLUE, new Modifier(ENTITY_REACH, 1.0D)));
        list.add(new QualityType("massive", ChatFormatting.BLUE, new Modifier(BLOCK_REACH, 1.5D)));
        list.add(new QualityType("sharp", ChatFormatting.BLUE, new Modifier(ATTACK_DAMAGE, 0.15D)));
        list.add(new QualityType("keen", ChatFormatting.BLUE, new Modifier(ATTACK_SPEED, 0.1D)));
        list.add(new QualityType("energetic", ChatFormatting.BLUE, new Modifier(CRITICAL_DAMAGE, 0.1D)));
        list.add(new QualityType("graceful", ChatFormatting.AQUA, new Modifier(ATTACK_SPEED, 0.1D), new Modifier(DIG_SPEED, 0.1D), new Modifier(BLOCK_REACH, 0.5D)));
        list.add(new QualityType("sweeping", ChatFormatting.AQUA, new Modifier(ATTACK_SPEED, 0.2D), new Modifier(ENTITY_REACH, 0.5D)));
        list.add(new QualityType("strong", ChatFormatting.AQUA, new Modifier(ATTACK_DAMAGE, 0.15D), new Modifier(CRITICAL_DAMAGE, 0.05D)));
        list.add(new QualityType("agile", ChatFormatting.AQUA, new Modifier(ATTACK_SPEED, 0.15D), new Modifier(CRITICAL_DAMAGE, 0.05D)));
        list.add(new QualityType("legendary", ChatFormatting.LIGHT_PURPLE, new Modifier(DIG_SPEED, 0.15D), new Modifier(BLOCK_REACH, 1.0D)));
        list.add(new QualityType("mythical", ChatFormatting.GREEN, new Modifier(ATTACK_DAMAGE, 0.15D), new Modifier(ATTACK_SPEED, 0.1D), new Modifier(CRITICAL_DAMAGE, 0.05D), new Modifier(ENTITY_REACH, 0.5D)));
        return list;
    }

    public static List<QualityType> accessoryQualityList() {
        List<QualityType> list = new ArrayList<>();
        list.add(new QualityType("horrible", ChatFormatting.DARK_RED, new Modifier(ATTACK_DAMAGE, -0.05D), new Modifier(CRITICAL_DAMAGE, -0.05D), new Modifier(PROJECTILE_DAMAGE, -0.05D)));
        list.add(new QualityType("defective", ChatFormatting.DARK_RED, new Modifier(ATTACK_SPEED, -0.05D), new Modifier(DIG_SPEED, -0.05D), new Modifier(MOVEMENT_SPEED, -0.05D)));
        list.add(new QualityType("unlucky", ChatFormatting.RED, new Modifier(LUCK, -0.2D)));
        list.add(new QualityType("normal", ChatFormatting.GRAY));
        list.add(new QualityType("healthy", ChatFormatting.BLUE, new Modifier(MAX_HEALTH, 2.0D)));
        list.add(new QualityType("armored", ChatFormatting.BLUE, new Modifier(DAMAGE_RESISTANCE, 0.03D)));
        list.add(new QualityType("speedy", ChatFormatting.BLUE, new Modifier(MOVEMENT_SPEED, 0.03D)));
        list.add(new QualityType("springy", ChatFormatting.BLUE, new Modifier(JUMP_HEIGHT, 0.5D)));
        list.add(new QualityType("prospecting", ChatFormatting.BLUE, new Modifier(DIG_SPEED, 0.03D)));
        list.add(new QualityType("flailing", ChatFormatting.BLUE, new Modifier(ATTACK_SPEED, 0.03D)));
        list.add(new QualityType("arcane", ChatFormatting.BLUE, new Modifier(MAGIC_RESIST, 1.0D)));
        list.add(new QualityType("aiming", ChatFormatting.BLUE, new Modifier(PROJECTILE_DAMAGE, 0.03D)));
        list.add(new QualityType("strengthening", ChatFormatting.BLUE, new Modifier(ATTACK_DAMAGE, 0.03D)));
        list.add(new QualityType("precise", ChatFormatting.BLUE, new Modifier(CRITICAL_DAMAGE, 0.03D)));
        list.add(new QualityType("lucky", ChatFormatting.AQUA, new Modifier(LUCK, 0.2D)));
        list.add(new QualityType("graceful", ChatFormatting.AQUA, new Modifier(ATTACK_SPEED, 0.03D), new Modifier(DIG_SPEED, 0.03D)));
        list.add(new QualityType("athletic", ChatFormatting.AQUA, new Modifier(MOVEMENT_SPEED, 0.05D), new Modifier(JUMP_HEIGHT, 0.5D)));
        list.add(new QualityType("versatile", ChatFormatting.LIGHT_PURPLE, new Modifier(ATTACK_SPEED, 0.03D), new Modifier(DIG_SPEED, 0.03D), new Modifier(MOVEMENT_SPEED, 0.03D)));
        list.add(new QualityType("punishing", ChatFormatting.LIGHT_PURPLE, new Modifier(ATTACK_DAMAGE, 0.03D), new Modifier(CRITICAL_DAMAGE, 0.03D), new Modifier(PROJECTILE_DAMAGE, 0.03D)));
        list.add(new QualityType("undying", ChatFormatting.LIGHT_PURPLE, new Modifier(MAX_HEALTH, 2.0D), new Modifier(DAMAGE_RESISTANCE, 0.03D), new Modifier(MAGIC_RESIST, 1.0D)));
        return list;
    }

    public record QualityType(String quality, ChatFormatting formatting, Modifier... attributes) {
    }

    public record Modifier(Attribute attribute, double getAmount) {
    }
}
