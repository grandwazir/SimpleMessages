/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * CommandListener.java is part of SimpleMessages.
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
package name.richardson.james.bukkit.simplemessages.commands;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import name.richardson.james.bukkit.simplemessages.Message;
import name.richardson.james.bukkit.simplemessages.SimpleMessages;

public class CommandListener implements Listener {

  private final Map<String, Message> messages;

  public CommandListener(final SimpleMessages plugin) {
    this.messages = plugin.getMessages();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
    final String command = event.getMessage();
    for (final String key : this.messages.keySet()) {
      if (command.startsWith(key, 1)) {
        int pageNumber = 1;
        event.setCancelled(true);
        // check to see if we have a page number
        final String[] arguments = command.split(" ");
        if (arguments.length != 1) {
          try {
            pageNumber = Integer.parseInt(arguments[1]);
          } catch (final NumberFormatException exception) {
            pageNumber = 1;
          }
        }
        this.messages.get(key).sendMessage(event.getPlayer(), pageNumber);
        return;
      }
    }
  }

}
