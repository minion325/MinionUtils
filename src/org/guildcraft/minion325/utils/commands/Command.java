package org.guildcraft.minion325.utils.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Command {

    private Set<SubCommand> subCommands = new HashSet<>();
    private Plugin owner;
    private String label;

    public Command(JavaPlugin owner, String label) {
        this.owner = owner;
        this.label = label;
    }

    public abstract void execute(CommandSender player);

    public abstract String getPermission();

    public  abstract String getPermissionMessage();

    public final void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            for (SubCommand command : this.subCommands) {
                if (command.getLabel().equalsIgnoreCase(args[0])) {
                    command.execute(sender, Arrays.copyOfRange(args, 1, args.length));
                    return;
                }
            }
        }
        if (!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(this.getPermissionMessage());
            return;
        }
        this.execute(sender);
    }

    public String getLabel() {
        return label;
    }

    public Plugin getOwner() {
        return owner;
    }

    public void addSubCommand(SubCommand command) {
        this.subCommands.add(command);
    }

    public final Set<SubCommand> getSubCommands() {
        return Collections.unmodifiableSet(this.subCommands);
    }
}
