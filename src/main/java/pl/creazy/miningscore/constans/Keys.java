package pl.creazy.miningscore.constans;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import pl.creazy.creazylib.util.key.Key;
import pl.creazy.miningscore.MiningScore;

@UtilityClass
public class Keys {
  public static final NamespacedKey MINING_SCORE = createKey("mining_score");

  private static NamespacedKey createKey(String key) {
    return Key.create(key, MiningScore.class);
  }
}
