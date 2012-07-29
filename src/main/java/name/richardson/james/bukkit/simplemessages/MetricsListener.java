package name.richardson.james.bukkit.simplemessages;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.metrics.AbstractMetricsListener;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Graph;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Plotter;

public class MetricsListener extends AbstractMetricsListener {

  private SimpleMessages plugin;

  public MetricsListener(SimpleMessages plugin) throws IOException {
    super(plugin);
    this.plugin = plugin;
    this.setupUsageStatistics();
    metrics.start();
  }

  private void setupUsageStatistics() {
    // Create a graph to show the total amount of kits issued.
    Graph graph = this.metrics.createGraph("Usage Statistics");
    graph.addPlotter(new Plotter("Total messages configured") {
      @Override
      public int getValue() {
        int i = plugin.getMessages().size();
        return i;
      }
    }); 
  }
  
  

}
