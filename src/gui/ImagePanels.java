package gui;

import java.util.*;

/*
 * класс хранящий статические поля со ссылками на 12 игровых карт, создающий лист и добавляющий в него карты
 *
 * class storing static fields that determine playing cards, creating a list and adding these cards to it
 */
class ImagePanels {

    private static final ImagePanel IMAGE1 = new ImagePanel("/images/image1.jpg");
    private static final ImagePanel IMAGE2 = new ImagePanel("/images/image2.jpg");
    private static final ImagePanel IMAGE3 = new ImagePanel("/images/image3.jpg");
    private static final ImagePanel IMAGE4 = new ImagePanel("/images/image4.jpg");
    private static final ImagePanel IMAGE5 = new ImagePanel("/images/image5.jpg");
    private static final ImagePanel IMAGE6 = new ImagePanel("/images/image6.jpg");
    private static final ImagePanel IMAGE7 = new ImagePanel("/images/image1.jpg");
    private static final ImagePanel IMAGE8 = new ImagePanel("/images/image2.jpg");
    private static final ImagePanel IMAGE9 = new ImagePanel("/images/image3.jpg");
    private static final ImagePanel IMAGE10 = new ImagePanel("/images/image4.jpg");
    private static final ImagePanel IMAGE11 = new ImagePanel("/images/image5.jpg");
    private static final ImagePanel IMAGE12 = new ImagePanel("/images/image6.jpg");

    /*
     * переменная класса хранящая текущее количество открытых карт
     *
     * class variable which is used to store current number of opened cards
     */
    static byte countOpenedImages = 0;

    private static final List<ImagePanel> listOfImages = new ArrayList<>();

    static {
        listOfImages.add(IMAGE1);
        listOfImages.add(IMAGE2);
        listOfImages.add(IMAGE3);
        listOfImages.add(IMAGE4);
        listOfImages.add(IMAGE5);
        listOfImages.add(IMAGE6);
        listOfImages.add(IMAGE7);
        listOfImages.add(IMAGE8);
        listOfImages.add(IMAGE9);
        listOfImages.add(IMAGE10);
        listOfImages.add(IMAGE11);
        listOfImages.add(IMAGE12);
    }

    /*
     * метод возвращающий лист с картами
     *
     * method returning a list of cards
     */
    static List<ImagePanel> getListOfImages() {
        return listOfImages;
    }

    /*
     * метод тасующий карты в листе
     *
     * method shuffling cards in a list
     */
    static void shuffle(List<ImagePanel> listOfImages) {
        Collections.shuffle(listOfImages);
    }
}
