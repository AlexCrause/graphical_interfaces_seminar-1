package org.example.Server.client;

import org.example.Server.server.ServerWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*

Задание 5
Задача: Создать окно клиента чата. Окно должно содержать JTextField
для ввода логина, пароля, IP-адреса сервера, порта подключения
к серверу, область ввода сообщений, JTextArea область просмотра
сообщений чата и JButton подключения к серверу и отправки сообщения
в чат. Желательно сразу сгруппировать компоненты, относящиеся
к серверу сгруппировать на JPanel сверху экрана, а компоненты,
относящиеся к отправке сообщения – на JPanel снизу

Задание 5*
Задача: Добавить на экран компонент JList – имитацию списка пользователей, заполнить этот список
несколькими выдуманными именами пользователей чата. Подсказка: компонент не может добавлять
или убирать элементы списка, он работает с методом setListData(), изучите его аргументы
 */
public class ClientWinGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int WINDOW_POSITION_X = 50;
    private static final int WINDOW_POSITION_Y = 250;

    private static final String CONNECTED = "Вы успешно подключились!";
    private static final String NOT_CONNECTED = "Сервер не работает!";
    private static final String NOT_LOGGED_IN = "Вы не авторизованы!";

    JTextField login;
    JTextArea text;

    private boolean isConnected;
    private final ServerWin serverWin;

    public ClientWinGUI(ServerWin serverWin) {
        isConnected = false;
        this.serverWin = serverWin;
        setSize(WIDTH, HEIGHT);
        setLocation(WINDOW_POSITION_X, WINDOW_POSITION_Y);
        setTitle("Client");

        createComponentsNorth();
        createComponentsCenter();
        createComponentsSouth();

        setVisible(true);
    }

    // метод для создания поля ввода логина и пароля...и отправки на сервер
    private void createComponentsNorth() {
        JPanel panel = new JPanel(new GridLayout(2, 3));
        JTextField ip = new JTextField("127.0.0.1", 10);
        JTextField port = new JTextField("8189", 10);
        login = new JTextField("Name", 10);
        JPasswordField password = new JPasswordField("12345", 10);

        JButton button = new JButton("Login");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverWin.isServerWorking()) {
                    isConnected = true;
                    createChatComponents(); // Вызов метода для создания компонентов чата
                    serverWin.addClient(ClientWinGUI.this); // Добавляем клиента на сервер
                    text.setText(CONNECTED);
                    outChatClient(serverWin.getTextMessage()); //вывод логов
                } else if (!serverWin.isServerWorking() && !isConnected) {
                    text.setText(NOT_CONNECTED);
                }
            }
        });

        panel.add(ip);
        panel.add(port);
        panel.add(login);
        panel.add(password);
        panel.add(button);

        add(panel, BorderLayout.NORTH);
    }

    // Метод для создания текстового поля
    private void createComponentsCenter() {
        text = new JTextArea();
        add(new JScrollPane(text), BorderLayout.CENTER);
    }

    // метод для создания поля ввода сообщения и отправки
    private void createComponentsSouth() {
        JPanel panelMessage = new JPanel();
        JTextField message = new JTextField(25);
        JButton send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverWin.isServerWorking() && isConnected) {
                    String msg = message.getText();
                    serverWin.sendMessage(msg, login.getText()); // Отправляем сообщение на сервер
                    message.setText(""); // Очищаем поле ввода
                } else if (!serverWin.isServerWorking() && !isConnected) {
                    text.setText(NOT_CONNECTED);
                } else if (!serverWin.isServerWorking() && isConnected) {
                    text.setText(NOT_CONNECTED);
                } else if (serverWin.isServerWorking() && !isConnected) {
                    text.setText(NOT_LOGGED_IN);
                }
            }
        });

        panelMessage.add(message);
        panelMessage.add(send);

        add(panelMessage, BorderLayout.SOUTH);
    }

    // Метод для создания компонентов чата
    private void createChatComponents() {
        getContentPane().removeAll(); // Удаляем все компоненты текущей панели
        // Создаем и добавляем новую панель в центр с компонентами чата
        createComponentsCenter();
        createComponentsSouth();
        revalidate(); // Обновляем интерфейс
        repaint();
    }

    // Добавляем содержимое textMessage в JTextArea клиента
    public void outChatClient(String textMessage) {
        text.append("\n" + textMessage);
    }

    public String getLogin() {
        return login.getText();
    }
}
