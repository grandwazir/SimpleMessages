package name.richardson.james.bukkit.simplemessages.motd;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import name.richardson.james.bukkit.simplemessages.Message;
import name.richardson.james.bukkit.simplemessages.SimpleMessages;

public class LoginListener implements Listener {

  private Map<String, Message> messages;

  public LoginListener(SimpleMessages plugin) {
    this.messages = plugin.getMessages();
  }
 
  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (messages.containsKey("motd")) {
      messages.get("motd").sendMessage(event.getPlayer(), 1);
    }
  }
  
}
