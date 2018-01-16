package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

class MainFrame extends JFrame {

    /*
     * private поля:
     *
     * title - надпись "Find a pair!"
     * newGameButton - кнопка для начала новой игры
     * exitButton - кнопка для выхода из игры
     * currentlyOpened - переменная хранит ссылку на текущую открытую карту
     * listOfImages - лист содержащий все игровые карты
     *
     *
     * private fields:
     *
     * title - title "Find a pair!"
     * newGameButton - a button for starting a new game
     * exitButton - a button for shutting down the program
     * currentlyOpened - this variable holds a reference to the currently opened card
     * listOfImages - a list containing all playing cards
     */
    private JLabel title;
    private NewGameButton newGameButton;
    private ExitButton exitButton;
    private ImagePanel currentlyOpened;
    private List<ImagePanel> listOfImages;

    MainFrame() {
        super("Find a pair!");
        /*
         * инициализируем private поля главной рамки
         *
         * initializing MainFrame private fields
         */
        title = new JLabel("Find a pair!");
        title.setFont(new Font(Font.SERIF, Font.ITALIC, 64));
        newGameButton = new NewGameButton();
        exitButton = new ExitButton();
        /*
         * получаем лист игровых карт
         *
         * getting a list of playing cards
         */
        listOfImages = ImagePanels.getListOfImages();
        /*
         * проходим итерацией по листу с картами и для каждой карты добавляем ActionListener
         *
         * iterating through the list of playing cards and adding ActionListener to each card
         */
        for (final ImagePanel i : listOfImages) {
            i.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*
                     * описываем что должно происходить при нажатии на карту
                     *
                     * describing the main logic what should happen when a card is clicked
                     */
                    if (i.isClosed()) {
                        try {
                            i.open();
                        } catch (IOException e1) {
                            emergencyExit();
                        }
                        if (currentlyOpened == null) currentlyOpened = i;
                        else {
                            if (currentlyOpened.equals(i)) {
                                currentlyOpened = null;
                            } else {
                                suspend(i);
                            }
                        }
                    }
                    /*
                     * когда количество открытых карт достигает числа 12 (все карты открыты) появляется диалоговое окно
                     * оповещающее о победе и предлагающее пользователю либо сыграть в новую игру заново либо выйти из
                     * игры
                     *
                     * when the number of opened cards reaches 12 (i.e. all cards are opened) a message dialog pops up
                     * telling that you won and offering two options: whether to play a new game again or to exit the
                     * game
                     */
                    if(ImagePanels.countOpenedImages ==12) {
                        int action = JOptionPane.showOptionDialog(MainFrame.this, "You won!",
                                "Congratulations!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, new String[]{"Try again", "Exit game"}, "default");
                        if(action==JOptionPane.OK_OPTION) {
                            try {
                                for(ImagePanel ip: listOfImages) ip.close();
                                drawPlayBoard();
                            } catch (IOException e1) {
                                emergencyExit();
                            }
                        }
                        else if(action==JOptionPane.YES_NO_CANCEL_OPTION) {
                            System.exit(0);
                        }
                    }
                }
            });
        }

        /*
         * добавляем ActionListener к newGameButton. Определяем что при нажатии кнопки создается новое игровое поле
         *
         * adding ActionListener to newGameButton. When the button is clicked a new game board is created
         */
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    drawPlayBoard();
                } catch (IOException e1) {
                    emergencyExit();
                }
            }
        });

        /*
         * добавляем ActionListener к exitButton. Определяем что при нажатии кнопки игра завершается
         *
         * adding ActionListener to exitButton. When the button is clicked the game shuts down
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLayouts();

        setVisible(true);

        setSize(600, 500);

        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /*
     * метод вызывается когда две карты не одинаковы. Создается SwingWorker объект и в его отдельном фоновом потоке
     * с интервалом в 1 секунду сначала блокируется главная рамка (чтобы не было возможности открывать новые карты),
     * затем закрываются карты и в конце рамка снова становится рабочей
     *
     * this method is invoked when two cards are not the same. SwingWorker object is created and all code flows in
     * it's background thread. Firstly, the MainFrame is blocked for 1 second. It disables a user to open new cards.
     * Then two initial cards close back and MainFrame is set to be enable again
     */
    private void suspend(final ImagePanel i) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                MainFrame.super.setEnabled(false);
                Thread.sleep(1000);
                i.close();
                currentlyOpened.close();
                currentlyOpened = null;
                MainFrame.super.setEnabled(true);
                return null;
            }
        };
        worker.execute();
    }

    /*
     * метод для создания игрового поля с 12 закрытыми картами
     *
     * method for creating a new game board with 12 closed cards
     */
    private void drawPlayBoard() throws IOException {
        ImagePanels.shuffle(listOfImages);

        getContentPane().removeAll();

        setTitle("Game");

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;

        //////////////image1////////////////
        gc.anchor = GridBagConstraints.LINE_START;
        add(listOfImages.get(0), gc);

        //////////////image2////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        add(listOfImages.get(1), gc);

        //////////////image3////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(listOfImages.get(2), gc);

        //////////////image4////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(listOfImages.get(3), gc);

        //////////////image5////////////////
        gc.gridx = 0;
        gc.gridy++;
        gc.anchor = GridBagConstraints.LINE_START;
        add(listOfImages.get(4), gc);

        //////////////image6////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        add(listOfImages.get(5), gc);

        //////////////image7////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(listOfImages.get(6), gc);

        //////////////image8////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(listOfImages.get(7), gc);

        //////////////image9////////////////
        gc.gridx = 0;
        gc.gridy++;
        gc.anchor = GridBagConstraints.LINE_START;
        add(listOfImages.get(8), gc);

        //////////////image10////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_START;
        add(listOfImages.get(9), gc);

        //////////////image11////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(listOfImages.get(10), gc);

        //////////////image12////////////////
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(listOfImages.get(11), gc);

        validate();
        repaint();
    }

    /*
     * метод размещает кнопки и надпись на главной рамке
     *
     * this method places the buttons and the label on the MainFrame
     */
    private void setLayouts() {
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //////////////title////////////////
        gc.weightx = 1;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.NORTH;
        add(title, gc);

        //////////////newGameButton////////////////
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(newGameButton, gc);

        //////////////exitButton////////////////
        gc.gridx = 0;
        gc.gridy = 3;
        add(exitButton, gc);
    }

    /*
     * метод выводящий диалоговое окно с сообщением о неизвестной ошибке. Вызывается при любых ошибках в работе
     * программы
     *
     * a method making and showing a message dialog telling about an unknown error. It is invoked when any flaws or
     * exceptions occur
     */
    private void emergencyExit() {
        int action = JOptionPane.showConfirmDialog(MainFrame.this,
                "Unknown error", "Error occurred", JOptionPane.DEFAULT_OPTION);
        if(action==JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
}
