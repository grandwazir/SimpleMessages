/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * LoginListener.java is part of SimpleMessages.
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
package name.richardson.james.bukkit.simplemessages.motd;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import name.richardson.james.bukkit.simplemessages.Message;
import name.richardson.james.bukkit.simplemessages.SimpleMessages;

public class LoginListener implements Listener {

  private final Map<String, Message> messages;

  public LoginListener(final SimpleMessages plugin) {
    this.messages = plugin.getMessages();
  }

  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerJoin(final PlayerJoinEvent event) {
    if (this.messages.containsKey("motd")) {
      this.messages.get("motd").sendMessage(event.getPlayer(), 1);
    }
  }

}
