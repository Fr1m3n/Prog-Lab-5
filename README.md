# Prog-Lab-6
6 лабораторная работа по программированию.  

 Инструкция по запуску
- 
- Режим работы определяется переменной окружения mode
- Для работы в режиме удалённого сервера -Dmode=server
- Для работы в режиме клиента, для подключения к серверу -Dmode=client
- По дефолту (если mode не присвоена, или имеет не корректное значение) запускается client.

# Клиент
Для определения адреса сервера, к которому клиент должен подключиться - необходимо ввести хост и порт сервера в аргумента
Первый аргумент - хост (домен или ip), второй аргумент - порт. \
Или же первый аргумент - адрес до сервера в формате <домен или ip>:<порт>
```
java -jar Prog_Lab_6.jar localhost 5858
или
java -jar Prog_Lab_6.jar localhost:5858
или
java -jar Prog_Lab_6.jar 127.0.0.1:5858
или
java -jar Prog_Lab_6.jar -Dmode=client 127.0.0.1 5858
```
Если аргументов нет, то подключение происходит по адресу: 
```
127.0.0.1:5858
```
# Сервер
- Первый аргумент - путь до файла .json из которого сервер должен инициализировать коллекцию. \
- Второй аргумент - порт, который будет слушать сервер (если второй аргумент отсутствует или не входит в диапозон допустимых портов - будет использован порт 5858).
```
java -jar Prog_Lab_6.jar -Dmode=server in.json 5858
или
java -jar Prog_Lab_6.jar -Dmode=server in.json
```
- Если будут проблемы с файлом из аргументов - коллекция инициализируется пустой.