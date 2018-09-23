package org.guildcraft.minion325.utils.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseCommand extends Command implements CommandExecutor {

    public BaseCommand(JavaPlugin owner, String label) {
        super(owner, label);
        owner.getCommand(label).setExecutor(this);
    }

    @Override
    public final boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        this.execute(commandSender, strings);
        return true;
    }

}
