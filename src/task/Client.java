package task;

import java.io.*;
import java.net.Socket;

/***
 * Разработать приложение для определения суммы подоходного налога.
 * На клиентской части вводятся заработные платы сотрудников предприятия и передаются северу,
 * а тот в свою очередь возвращает суммы налога.
 * Причем для з/п меньше 100 000 руб. применяется ставка налога 5 %,
 * для з/п от 100 000 до 500 000 – ставка 10 %,
 * для з/п больше 500 000 – ставка 15 %.
 */
public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("server connecting....");
            //установление соединения между локальной машиной и указанным портом узла сети
            Socket clientSocket = new Socket("localhost", 2525);
            System.out.println("connection established....");

            //создание буферизированного символьного потока ввода
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            //создание потока вывода
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            //создание потока ввода
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Enter any string to send to server \n\t('quite' − programme terminate)");

            String clientMessage = stdin.readLine();
            System.out.println("you've entered: " + clientMessage);
            //выполнение цикла, пока строка не будет равна «quite»

            while (!clientMessage.equals("quite")) {
                //потоку вывода присваивается значение строковой переменной (передается серверу)
                coos.writeObject(clientMessage);
                //выводится на экран содержимое потока ввода (переданное сервером)
                System.out.println("~server~: " +
                        cois.readObject());
                System.out.println("---------------------------");
                //ввод текста с клавиатуры
                //вывод в консоль строки и значения строковой переменной
                System.out.println("you've entered: " + clientMessage);
                clientMessage = stdin.readLine();
            }
            coos.close(); //закрытие потока вывода
            cois.close(); //закрытие потока ввода
            clientSocket.close(); // закрытие сокета
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
