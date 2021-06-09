package com.my.command.container;

import com.my.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put("login", new AuthorizationCommand());
        commands.put("register", new RegisterCommand());
        commands.put("logOut", new LogOutCommand());

        commands.put("accountInfo", new AccountInfoCommand());
        commands.put("topUpBalance", new ToUpBalanceCommand());

        commands.put("adminUserSetting", new EditUserCommand());
        commands.put("adminMagazineSetting", new EditMagazineCommand());

        commands.put("showAllUsers", new ShowAllUsersCommand());

        commands.put("showAllMagazine", new ShowAllMagazinesCommand());
        commands.put("findMagazineByName", new FindMagazinesByNameCommand());
        commands.put("sortMagazineByCategory", new ShowMagazinesByCategoryCommand());

        commands.put("sortMagazineByName", new SortMagazineByNameCommand());
        commands.put("sortMagazineByPriceHL", new SortMagazineByPriceHLCommand());
        commands.put("sortMagazineByPriceLH", new SortMagazineByPriceLHCommand());

    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
