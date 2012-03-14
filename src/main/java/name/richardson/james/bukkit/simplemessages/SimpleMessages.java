/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SimpleMessages.java is part of SimpleMessages.
 * 
 * SimpleMessages is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * SimpleMessages is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimpleMessages. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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

  public Map<String, Message> getMessages() {
    return Collections.unmodifiableMap(this.messages);
  }

  public void loadMessages() {
    final FilenameFilter filter = new MessageFileFilter();
    final File[] files = this.getDataFolder().listFiles(filter);
    for (final File file : files) {
      Message message;
      try {
        message = new Message(file);
        this.messages.put(message.getName(), message);
      } catch (final IOException e) {
        this.logger.warning("Unable to read file!");
      }
    }
    this.logger.info(this.getFormattedMessageCount(this.messages.size()));
  }

  @Override
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

  private String getFormattedMessageCount(final int count) {
    final Object[] arguments = { count };
    final double[] limits = { 0, 1, 2 };
    final String[] formats = { this.getMessage("no-messages"), this.getMessage("one-message"), this.getMessage("many-messages") };
    return this.getChoiceFormattedMessage("messages-loaded", arguments, formats, limits);
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new SimpleMessagesConfiguration(this);
    if (this.configuration.getDebugging()) {
      Logger.setDebugging(this, true);
    }
  }

  private void registerListeners() {
    this.getServer().getPluginManager().registerEvents(new CommandListener(this), this);
    this.getServer().getPluginManager().registerEvents(new LoginListener(this), this);
  }

}
