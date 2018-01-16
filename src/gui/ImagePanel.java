package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class ImagePanel extends JButton {

    /*
     * private поля:
     *
     * image - переменная хранит ссылку на картинку
     * resource - путь где лежит картинка
     * shirtImageResource - путь где лежит "рубашка" карты
     * isClosed - карта открыта или нет?
     *
     * private fields:
     *
     * image - this variable holds a reference to an image
     * resource - path to the image
     * shirtImageResource - path to the back side of a card
     * isClosed - is a card opened or not?
     */
    private BufferedImage image;
    private String resource;
    private static final String shirtImageResource = "/images/shirt.jpg";
    private boolean isClosed;

    ImagePanel(String resource) {
        try {
            /*
             * создаем карту
             *
             * creating a card
             */
            isClosed = true;
            this.resource = resource;
            image = ImageIO.read(getClass().getResource(shirtImageResource));
            this.setIcon(new ImageIcon(image));
            setPreferredSize(new Dimension(160, 160));
        } catch (IOException ex) {
            emergencyExit();
        }
    }

    /*
     * метод открывающий карту
     *
     * a method aiming to open a card
     */
    void open() throws IOException {
        image = ImageIO.read(getClass().getResource(resource));
        this.setIcon(new ImageIcon(image));
        isClosed = false;
        ImagePanels.countOpenedImages++;
    }

    /*
     * метод закрывающий карту
     *
     * a method aiming to close a card
     */
    void close() throws IOException {
        image = ImageIO.read(getClass().getResource(shirtImageResource));
        this.setIcon(new ImageIcon(image));
        isClosed = true;
        ImagePanels.countOpenedImages--;
    }

    /*
     * метод для проверки открыта ли карта или нет
     *
     * a method telling whether the card is opened or not
     */
    boolean isClosed() {
        return isClosed;
    }

    /*
     * переопределяем метод equals(). Карты одинаковы если одинаковы их имена путей где они лежат
     *
     * overriding the equals() method. Two cards are equal when their path names are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagePanel that = (ImagePanel) o;
        return Objects.equals(resource, that.resource);
    }

    /*
     * метод выводящий диалоговое окно с сообщением о неизвестной ошибке. Вызывается при любых ошибках в работе
     * программы
     *
     * a method making and showing a message dialog telling about an unknown error. It is invoked when any flaws or
     * exceptions occur
     */
    private void emergencyExit() {
        int action = JOptionPane.showConfirmDialog(null,
                "Unknown error", "Error occurred", JOptionPane.DEFAULT_OPTION);
        if (action == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
}
