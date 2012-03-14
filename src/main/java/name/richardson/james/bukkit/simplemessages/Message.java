/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Message.java is part of SimpleMessages.
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

  public Message(final File file) throws IOException {
    this.name = file.getName().replaceAll(".txt", "");
    logger.debug("Created message: " + file.getName());

    try {
      this.buffer = new BufferedReader(new FileReader(file));
      this.parseLines();

    } finally {
      this.buffer.close();
    }

  }

  public ChatPage getChatPage(final int page) {
    return ChatPaginator.paginate(this.test, page);
  }

  public List<String> getLines() {
    return Collections.unmodifiableList(this.lines);
  }

  public String getName() {
    return this.name;
  }

  public void sendMessage(final Player player, final int pageNumber) {
    logger.debug("Sending message using lines from " + this.name);
    final ChatPage page = ChatPaginator.paginate(this.test, pageNumber);
    if (page.getTotalPages() > 1) {
      player.sendMessage(this.getPageHeader(this.name, page));
    }
    for (String line : page.getLines()) {
      line = this.tokenReplace(player, line);
      player.sendMessage(line);
    }
  }

  private String getPageHeader(final String message, final ChatPage page) {
    final StringBuilder header = new StringBuilder();
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

  private void parseLines() throws IOException {
    /*
     * String line;
     * while ((line = buffer.readLine()) != null) {
     * lines.add(ColourFormatter.replace("&", line));
     * }
     */
    final StringBuilder builder = new StringBuilder();
    String line;
    while ((line = this.buffer.readLine()) != null) {
      builder.append(ColourFormatter.replace("&", line));
      builder.append("\n");
    }
    this.test = builder.toString();
  }

  private String tokenReplace(final Player player, String line) {
    if (line.contains("%name")) {
      line = line.replaceAll("%name", player.getName());
    } else if (line.contains("%display_name")) {
      line = line.replaceAll("%display_name", player.getDisplayName());
    }
    return line;
  }

}
