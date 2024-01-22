package ua.mykolamurza.chatullo.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.chatullo.configuration.Config;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1)
            return true;

        if (!args[0].equalsIgnoreCase("reload"))
            return true;

        if (sender instanceof ConsoleCommandSender)
            Config.reload();
        else if (sender.isOp() || sender.hasPermission("chatullo.admin"))
            Config.reload();

        return true;
    }
}
