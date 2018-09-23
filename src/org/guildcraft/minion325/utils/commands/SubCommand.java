package org.guildcraft.minion325.utils.commands;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class SubCommand extends Command {

    private Command parent;

    public SubCommand(JavaPlugin owner, String label, Command parent){
        super(owner, label);
        this.parent = parent;
    }

    public Command getParent() {
        return parent;
    }
}
