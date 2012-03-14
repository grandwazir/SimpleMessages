package name.richardson.james.bukkit.simplemessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;

import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.internals.Logger;

public class Message {

  private static final Logger logger = new Logger(Message.class);
  
  private final List<String> lines = new ArrayList<String>();
  private final String name;
  private String test;
  private BufferedReader buffer;

  public Message(File file) throws IOException {
    this.name = file.getName().replaceAll(".txt", "");
    logger.debug("Created message: " + file.getName());
    
    try {
      buffer = new BufferedReader(new FileReader(file));
      this.parseLines();
      
    } finally {
      buffer.close();
    }
    
  }
  
  public String getName() {
    return this.name; 
  }
  
  public ChatPage getChatPage(int page) {
    return ChatPaginator.paginate(test, page);
  }
  
  public List<String> getLines() {
    return Collections.unmodifiableList(this.lines);
  }
  
  private void parseLines() throws IOException {
    /* String line;
    while ((line = buffer.readLine()) != null) {
      lines.add(ColourFormatter.replace("&", line));
    }
    */
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = buffer.readLine()) != null) {
      builder.append(ColourFormatter.replace("&", line));
      builder.append("\n");
    }
    this.test = builder.toString();
  }

  
  public void sendMessage(Player player, int pageNumber) {
    logger.debug("Sending message using lines from " + this.name);
    final ChatPage page = ChatPaginator.paginate(test, pageNumber);
    if (page.getTotalPages() > 1) player.sendMessage(getPageHeader(this.name, page));
    for (String line : page.getLines()) {
      line = tokenReplace(player, line);
      player.sendMessage(line);
    }
  }
  
  private String getPageHeader(String message, ChatPage page) {
    StringBuilder header = new StringBuilder();
    header.append(ChatColor.RED);
    header.append("== /");
    header.append(message);
    header.append(ChatColor.YELLOW);
    header.append(" (Page ");
    header.append(page.getPageNumber());
    header.append(" of ");
    header.append(page.getTotalPages());
    header.append(")");
    return header.toString();
  }
  
  private String tokenReplace(Player player, String line) {
    if (line.contains("%name")) {
      line = line.replaceAll("%name", player.getName());
    } else if (line.contains("%display_name")) {
      line = line.replaceAll("%display_name", player.getDisplayName());
    } 
    return line;
  }
  
}
