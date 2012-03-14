package name.richardson.james.bukkit.simplemessages;

import java.io.File;
import java.io.FilenameFilter;

public class MessageFileFilter implements FilenameFilter {

  public boolean accept(File dir, String name) {
    if (name.endsWith(".txt")) return true;
    return false;
  }
  
}
