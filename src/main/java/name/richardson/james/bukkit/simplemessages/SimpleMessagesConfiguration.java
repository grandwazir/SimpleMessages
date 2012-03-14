/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SimpleMessagesConfiguration.java is part of SimpleMessages.
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
