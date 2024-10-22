package org.example.Server.server;

import org.example.Server.client.ClientWinGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerWin extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    JButton btnStart, btnStop;
    private static final String SERVER_WORKING = "Сервер работает";
    private static final String SERVER_NOT_WORKING = "Cервер не запущен!";

    JTextArea textAreaServer = new JTextArea();
    private boolean isServerWorking;
    private final List<ClientWinGUI> connectedClients;
    //private final List<String> historyChat;

    String message;
    String textMessage;

    public ServerWin() {
        isServerWorking = false;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Server");

        connectedClients = new ArrayList<>();
        //historyChat = new ArrayList<>();

        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = true;
                textAreaServer.setText(SERVER_WORKING);
                getHistoryChatFromFile();
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = false;
                textAreaServer.setText(SERVER_NOT_WORKING);
                //connectedClients.clear();
                //System.exit(0);
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.add(btnStart);
        btnPanel.add(btnStop);
        add(btnPanel, BorderLayout.SOUTH);

        add(new JScrollPane(textAreaServer), BorderLayout.CENTER);

        setVisible(true);
    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    public String getTextMessage() {
        return textMessage;
    }

    // Метод для регистрации клиента
    public void addClient(ClientWinGUI client) {
        connectedClients.add(client); // Добавляем объект клиента
        //textAreaServer.append("\n" + client.getLogin() + " подключился к беседе"); // Используем метод получения логина
        broadcastMessage(client.getLogin() + " присоединился к чату."); // Уведомляем всех клиентов
    }

    public void sendMessage(String text, String clientName) {
        message = clientName + ": " + text;
        broadcastMessage(message); // Отправляем сообщение всем клиентам
    }

    // Метод для отправки сообщения всем клиентам
    public void broadcastMessage(String message) {
        for (ClientWinGUI client : connectedClients) {
            client.outChatClient(message); // Отправляем сообщение каждому клиенту
        }
        textAreaServer.append("\n" + message); // Также показываем сообщение на сервере
        writeHistoryChatToFile(message); // Сохраняем сообщение в файл
    }

    public void writeHistoryChatToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/history.txt", true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getHistoryChatFromFile() {
        StringBuilder textMessageBuilder = new StringBuilder(); // Для накопления сообщений
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/history.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                textAreaServer.append("\n" + line);
                textMessageBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textMessage = textMessageBuilder.toString();
    }
}
