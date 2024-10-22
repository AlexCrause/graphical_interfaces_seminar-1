package org.example.Server;
/*
Задание 4.
Задача: Создать простейшее окно управления сервером (по сути, любым),
содержащее две кнопки (JButton) – запустить сервер и остановить сервер.
Кнопки должны просто логировать нажатие (имитировать запуск и
остановку сервера, соответственно) и выставлять внутри интерфейса
соответствующее булево isServerWorking.
 */

/*
Задание 4*
Задача: Если сервер не запущен, кнопка остановки должна сообщить, что
сервер не запущен и более ничего не делать. Если сервер запущен, кнопка
старта должна сообщить, что сервер работает и более ничего не делать.
 */

/*
Задание 4**
Задача: Добавить на окно компонент JTextArea и выводить
сообщения сервера в него, а не в терминал.
 */
import org.example.Server.client.ClientWinGUI;
import org.example.Server.server.ServerWin;

public class Main {
    public static void main(String[] args) {
        ServerWin serverWin = new ServerWin();
        new ClientWinGUI(serverWin);
        new ClientWinGUI(serverWin);


    }

}
