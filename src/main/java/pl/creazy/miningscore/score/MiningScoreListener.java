package pl.creazy.miningscore.score;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.creazy.creazylib.data.persistence.nbt.NbtEditor;
import pl.creazy.creazylib.listener.constraints.EventHandlers;
import pl.creazy.creazylib.log.Logger;
import pl.creazy.creazylib.part.constraints.Injected;
import pl.creazy.creazylib.util.mc.Mc;
import pl.creazy.creazylib.util.message.Message;
import pl.creazy.creazylib.util.message.Placeholder;
import pl.creazy.creazylib.util.text.Text;
import pl.creazy.miningscore.constans.Keys;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@EventHandlers
class MiningScoreListener implements Listener {
  @Injected
  private MiningScoreConfig config;

  @Injected
  private Logger logger;

  @EventHandler
  void onBlockBreak(BlockBreakEvent event) {
    var player = event.getPlayer();
    var item = player.getInventory().getItemInMainHand();

    if (Mc.isEmpty(item) || !shouldSaveMiningScore(item)) {
      return;
    }

    var nbtEditor = NbtEditor.of(item);
    var newMiningScore = nbtEditor.find(Keys.MINING_SCORE, Integer.class).orElse(0) + 1;

    nbtEditor.set(Keys.MINING_SCORE, newMiningScore);
    nbtEditor.save();

    var meta = item.getItemMeta();

    if (meta == null) {
      logger.error("Failed to set item lore. Item meta is null");
      return;
    }

    var lore = Objects.requireNonNullElse(meta.getLore(), new ArrayList<String>());
    var newLore = lore.stream()
        .filter(line -> !ChatColor.stripColor(line).startsWith("Wykopane bloki: "))
        .collect(Collectors.toList());

    newLore.add(Message.create(config.getScoreDisplay(), Placeholder.amount(newMiningScore)).getContent());
    meta.setLore(newLore);

    item.setItemMeta(meta);
  }

  private boolean shouldSaveMiningScore(ItemStack item) {
    return switch (item.getType()) {
      case WOODEN_PICKAXE -> config.getWoodPickaxe();
      case STONE_PICKAXE -> config.getStonePickaxe();
      case IRON_PICKAXE -> config.getIronPickaxe();
      case GOLDEN_PICKAXE -> config.getGoldenPickaxe();
      case DIAMOND_PICKAXE -> config.getDiamondPickaxe();
      case NETHERITE_PICKAXE -> config.getNetheritePickaxe();
      default -> false;
    };
  }
}
