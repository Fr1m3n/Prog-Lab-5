package com.p3112.roman.commands;
// Writed by Roman Devyatilov (Fr1m3n) in 9:06 07.02.2020

import com.p3112.roman.collection.StorageService;
import com.p3112.roman.exceptions.NoSuchCommandException;
import com.p3112.roman.utils.UserInterfaceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Синглтон, который управляет командами. Хранит в себе реестр всех команд. Через него происходит выполнение команды по строке пользователя.
 */
@Slf4j
public class CommandsManager {
    private static CommandsManager instance;

    public static CommandsManager getInstance() {
        if (instance == null) {
            instance = new CommandsManager();
        }
        return instance;
    }
    private Map<String, AbstractCommand> commands = new HashMap<>();

    /**
     * Стандартный конструктор, в котором добавляются все команды.
     */
    private CommandsManager() {
        // init commands
        addCommand(new Help());
        addCommand(new Info());
        addCommand(new Add());
        addCommand(new AddIfMin());
        addCommand(new Clear());
        addCommand(new CountGreaterThanHouse());
        addCommand(new ExecuteScript());
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


    /**
     * Добавляет команду в реестр
     * @param cmd объект команды
     */
    private void addCommand(AbstractCommand cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    /**
     * Получает команду из реестра.
     * @param s строка, соответсвующая команде
     * @return объект команды
     * @throws NoSuchCommandException в случае, если команды соответсвующей s нет в реестре
     */
    public AbstractCommand getCommand(String s) throws NoSuchCommandException {
        if (!commands.containsKey(s)) {
            throw new NoSuchCommandException();
        }
        return commands.getOrDefault(s, null);
    }

    /**
     * Выполняет команду, по строке юзера
     * @param userInterface интерфейс обмена данными между юзером и программой
     * @param storageService сервис, управляющий коллекцие
     * @param s строка, введённая юзером
     * @throws IOException Пробрасывается от команды, в случае если команда работает с I/O и произошла ошибка.
     */
    public void executeCommand(UserInterfaceImpl userInterface, StorageService storageService, String s) throws IOException {
        String[] parsedCommand = parseCommand(s);
        String commandName = parsedCommand[0];
        log.info("Выполняется коммадна {}", commandName);
        AbstractCommand command = getCommand(parsedCommand[0]);
        String[] args = Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length);
        command.execute(userInterface, storageService, args);
        log.info("Комманда {} успешно выполнена.", commandName);
    }

    /**
     * Возвращает все команды из реестра.
     * @return Список всех команд.
     */
    public List<AbstractCommand> getAllCommands() {
        return commands.keySet().stream().map(x -> (commands.get(x))).collect(Collectors.toList());
    }

    /**
     * Разбивает команду на лексемы.
     * @param line строка, введёная юзером
     * @return Массив лексем
     */
    public String[] parseCommand(String line) {
        return line.split(" ");
    }
}
