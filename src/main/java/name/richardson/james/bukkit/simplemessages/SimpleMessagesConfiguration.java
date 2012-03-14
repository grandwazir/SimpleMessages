package name.richardson.james.bukkit.simplemessages;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.configuration.AbstractConfiguration;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public class SimpleMessagesConfiguration extends AbstractConfiguration {

  public SimpleMessagesConfiguration(final SimplePlugin plugin) throws IOException {
    super(plugin, "config.yml");
  }

  public boolean getDebugging() {
    return this.configuration.getBoolean("debugging");
  }
  
}
