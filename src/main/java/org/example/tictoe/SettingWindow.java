package org.example.tictoe;
/*
Задача: На лекции был написан фрейм, содержащий одну кнопку – начать
игру и расположением самого окна настроек автоматически, относительно
игрового окна.
Добавить на экран компоновщик-сетку с одним столбцом и добавить над
существующей кнопкой следующие компоненты в заданном порядке: JLabel
с заголовком «Выберите режим игры», сгруппированные в ButtonGroup
переключаемые JRadioButton с указанием режимов «Человек против
компьютера» и «Человек против человека», JLabel с заголовком «Выберите
размеры поля», JLabel с заголовком «Установленный размер поля:», JSlider
со значениями 3..10, JLabel с заголовком «Выберите длину для победы»,
JLabel с заголовком «Установленная длина:», JSlider со значениями 3..10.
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 360;
    private static final int WINDOW_POSX = 600;
    private static final int WINDOW_POSY = 160;

    private static final String SELECT_FIELD_SIZE = "Выбранный размер поля: ";
    private static final String SELECT_FIELD_SIZE_WIN = "Установленная длина: ";

    JButton btnStart;
    JRadioButton btn1 = new JRadioButton("Человек против компьютера");
    JRadioButton btn2 = new JRadioButton("Человек против человека");
    JSlider winSize = new JSlider(3, 10);
    JSlider sizeF = new JSlider(3, 10);

    GameWindow gameWindow;

    SettingWindow(GameWindow gameWindow){
        this.gameWindow = gameWindow;
        btnStart = new JButton("Start new game");

        setLocationRelativeTo(gameWindow);
        setSize(WIDTH, HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBtnStart();//запускаем новую игру
            }
        });

        JPanel settings = new JPanel(new GridLayout(3, 1));

        //выбор типа игры
        JPanel gameType = new JPanel(new GridLayout(3, 1));
        gameType.add(new JLabel("Выберите тип игры: "));
        ButtonGroup Group1 = new ButtonGroup();
        btn1.setSelected(true);
        Group1.add(btn1);
        Group1.add(btn2);
        gameType.add(btn1);
        gameType.add(btn2);

        //выбор длины повторений для победы
        JPanel sizeWin = new JPanel(new GridLayout(3, 1));
        sizeWin.add(new JLabel("Выберите длину для победы"));
        JLabel currentWinSize = new JLabel(SELECT_FIELD_SIZE_WIN);
        sizeWin.add(currentWinSize);
        winSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int size2 = winSize.getValue();
                currentWinSize.setText(SELECT_FIELD_SIZE_WIN + size2);
            }
        });
        sizeWin.add(winSize);

        //выбор размеров поля
        JPanel fieldSize = new JPanel(new GridLayout(3, 1));
        fieldSize.add(new JLabel("Выберите размер поля"));
        JLabel currentSize = new JLabel(SELECT_FIELD_SIZE);
        fieldSize.add(currentSize);
        sizeF.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int size1 = sizeF.getValue();
                currentSize.setText(SELECT_FIELD_SIZE + size1);
                winSize.setMaximum(size1);
            }
        });
        fieldSize.add(sizeF);


        settings.add(gameType);
        settings.add(fieldSize);
        settings.add(sizeWin);

        add(settings);
        add(btnStart, BorderLayout.SOUTH);
    }

    private void setBtnStart(){
        int mode = 0;
        if(btn1.isSelected()){
            mode = 1;
        } else if(btn2.isSelected()){
            mode = 2;
        }
        int sizeField = sizeF.getValue();
        int sizeWin = winSize.getValue();
        gameWindow.startNewGame(mode, sizeField, sizeField, sizeWin);
        setVisible(false);
    }
}
