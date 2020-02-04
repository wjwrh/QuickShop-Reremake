package org.maxgamer.quickshop.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class InteractionManager {
  private Map<Class<? extends Event>, Set<InteractionRegisterEntry>> registered = new HashMap<>();

  /**
   * Register a listener to interaction manager for player actions
   * @param event The event class
   * @param uuid The uuid
   * @param consumer The callback
   * @throws IllegalArgumentException Throw when you register this event twice
   */
  public void register(@NotNull Class<? extends Event> event,@NotNull UUID uuid,@NotNull Consumer<InteractionCallback> consumer) throws IllegalArgumentException{
    Set<InteractionRegisterEntry> register = this.registered.getOrDefault(event, Collections.emptySet());
    register.add(new InteractionRegisterEntry(event,uuid,consumer));
    this.registered.put(event, register);
  }
  public void unregister(@NotNull Class<? extends Event> event,@NotNull UUID uuid,@NotNull Consumer<InteractionCallback> consumer){
    Set<InteractionRegisterEntry> register = this.registered.getOrDefault(event, Collections.emptySet());
    register.remove(new InteractionRegisterEntry(event,uuid,consumer));
    this.registered.put(event, register);
  }
  public void fireEvent(@NotNull Event event){
    Set<InteractionRegisterEntry> register = this.registered.getOrDefault(event.getClass(), Collections.emptySet());
    register.forEach(e-> e.getConsumer().accept(new InteractionCallback(event, e.getUniqueID())));
  }

}
@AllArgsConstructor
@Data
class InteractionRegisterEntry{
  private @NotNull Class<? extends Event> event;
  private @NotNull UUID uniqueID;
  private @NotNull Consumer<InteractionCallback> consumer;
}
@AllArgsConstructor
@Data
class InteractionCallback{
  private @NotNull Event event;
  private @NotNull UUID uniqueID;
}
