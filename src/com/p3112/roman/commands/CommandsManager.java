package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:06 07.02.2020

import com.p3112.roman.exceptions.NoSuchCommandException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandsManager {
    private static CommandsManager instance;

    public static CommandsManager getInstance() {
        if (instance == null) {
            instance = new CommandsManager();
        }
        return instance;
    }
    private Map<String, AbstractCommand> commands = new HashMap<>();

    private CommandsManager() {
        // init commands
        addCommand(new Help());
        addCommand(new Info());
        addCommand(new Add());
        addCommand(new AddIfMin());
        addCommand(new Clear());
        addCommand(new CountGreaterThanHouse());
        addCommand(new ExecuteSript());
        addCommand(new Exit());
        addCommand(new FilterLessThanNumberOfRooms());
        addCommand(new InsertAt());
        addCommand(new PrintFieldAscendingView());
        addCommand(new RemoveAt());
        addCommand(new RemoveById());
        addCommand(new Save());
        addCommand(new Show());
        addCommand(new Update());
    }



    private void addCommand(AbstractCommand cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    public AbstractCommand getCommand(String s) throws NoSuchCommandException {
        if (!commands.containsKey(s)) {
            throw new NoSuchCommandException();
        }
        return commands.getOrDefault(s, null);
    }

    public List<AbstractCommand> getAllCommands() {
        return commands.keySet().stream().map(x -> (commands.get(x))).collect(Collectors.toList());
    }
}
