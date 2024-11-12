package pl.creazy.miningscore.score;

import lombok.Getter;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigFile;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigVar;

@Getter
@ConfigFile(name = "miningScoreConfig")
class MiningScoreConfig {
  @ConfigVar("pickaxe.wood")
  private Boolean woodPickaxe = false;

  @ConfigVar("pickaxe.stone")
  private Boolean stonePickaxe = false;

  @ConfigVar("pickaxe.iron")
  private Boolean ironPickaxe = true;

  @ConfigVar("pickaxe.golden")
  private Boolean goldenPickaxe = false;

  @ConfigVar("pickaxe.diamond")
  private Boolean diamondPickaxe = false;

  @ConfigVar("pickaxe.netherite")
  private Boolean netheritePickaxe = false;

  @ConfigVar("score-display")
  private String scoreDisplay = "&7Wykopane bloki: ${AMOUNT}";
}
