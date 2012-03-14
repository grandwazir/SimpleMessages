package name.richardson.james.bukkit.simplemessages.commands;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.util.ChatPaginator.ChatPage;

import name.richardson.james.bukkit.simplemessages.Message;
import name.richardson.james.bukkit.simplemessages.SimpleMessages;
import name.richardson.james.bukkit.utilities.formatters.TimeFormatter;
import name.richardson.james.bukkit.utilities.internals.Logger;

public class CommandListener implements Listener {

  private final static Logger logger = new Logger(CommandListener.class);
  
  private final Map<String, Message> messages;



  public CommandListener(SimpleMessages plugin) {
    this.messages = plugin.getMessages();
  }
  
  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
    final String command = event.getMessage();
    if (event.isCancelled()) return;
    logger.debug("Processing command: " + command);
    for (String key : messages.keySet()) {
      logger.debug(key);
      if (command.startsWith(key, 1)) {
        int pageNumber = 1;
        event.setCancelled(true);
        // check to see if we have a page number
        String[] arguments = command.split(" ");
        if (arguments.length != 1) {
          try {
            pageNumber = Integer.parseInt(arguments[1]);
          } catch (final NumberFormatException exception) {
            pageNumber = 1;
          }
        }
        messages.get(key).sendMessage(event.getPlayer(), pageNumber);
        return;
      }
    }
  }

  
}
