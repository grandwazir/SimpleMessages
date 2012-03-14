package name.richardson.james.bukkit.simplemessages;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import name.richardson.james.bukkit.simplemessages.commands.CommandListener;
import name.richardson.james.bukkit.simplemessages.motd.LoginListener;
import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public class SimpleMessages extends SimplePlugin {
  
  private final Map<String, Message> messages = new HashMap<String, Message>();
  
  private SimpleMessagesConfiguration configuration;

  public void onEnable() {
    this.logger.setPrefix("[SimpleMessages] ");

    try {
      this.loadConfiguration();
      this.setResourceBundle();
      this.loadMessages();
      this.registerListeners();
    } catch (final IOException exception) {
      this.logger.severe(this.getMessage("unable-to-read-configuration"));
      this.setEnabled(false);
    } finally {
      if (!this.isEnabled()) {
        this.logger.severe(this.getMessage("panic"));
        return;
      }
    }

    this.logger.info(this.getSimpleFormattedMessage("plugin-enabled", this.getDescription().getFullName()));
  }
  
  private void registerListeners() {
    this.getServer().getPluginManager().registerEvents(new CommandListener(this), this);
    this.getServer().getPluginManager().registerEvents(new LoginListener(this), this);
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new SimpleMessagesConfiguration(this);
    if (this.configuration.getDebugging()) {
      Logger.setDebugging(this, true);
    }
  }

  public void loadMessages() {
    FilenameFilter filter = new MessageFileFilter();
    File[] files = this.getDataFolder().listFiles(filter);
    for (File file : files) {
      Message message;
      try {
        message = new Message(file);
        messages.put(message.getName(), message);
      } catch (IOException e) {
        logger.warning("Unable to read file!");
      } 
    }
    this.logger.info(getFormattedMessageCount(this.messages.size()));
  }

  public Map<String, Message> getMessages() {
    return Collections.unmodifiableMap(messages);
  }
  
  private String getFormattedMessageCount(int count) {
    Object[] arguments = {count};
    double[] limits = {0, 1, 2};
    String[] formats = {this.getMessage("no-messages"), this.getMessage("one-message"), this.getMessage("many-messages")};
    return this.getChoiceFormattedMessage("messages-loaded", arguments, formats, limits);
  }
  
}
