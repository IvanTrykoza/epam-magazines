package com.my.command.commandContainer;

import com.my.command.commandContainer.impl.ChangeLocaleCommand;
import com.my.command.commandContainer.impl.admin.*;
import com.my.command.commandContainer.impl.magazine.*;
import com.my.command.commandContainer.impl.user.*;

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

        commands.put("subscribeMagazine", new SubscribeMagazine());
        commands.put("getUsersSubscriptions", new ShowSubscriptionsCommand());


        commands.put("changeLocale", new ChangeLocaleCommand());

    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
