package com.company;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Downloaders extends Thread {

    private int precent = 0;           // Показываем процент загруженного файла
    private int fileDownload;          // скачанный файл
    private int dSpeedDownload;         // скорость загрузки
    CountDownLatch countDownLatch;
    Semaphore semaphore;
    Server server;

    public Downloaders(int file, int dSpeedDownload, CountDownLatch countDownLatch, Semaphore semaphore, Server server) {
        fileDownload = file;
        this.dSpeedDownload = dSpeedDownload;
        this.countDownLatch = countDownLatch;
        this.semaphore = semaphore;
        this.server = server;
    }

    public void run () {
        try {
            countDownLatch.await();           // ждем, пока файл загрузфт на сервер
            semaphore.acquire();            // делаем запрос на скачивание файла
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " начал загрузку файла");
        for (int i = 0; i < server.getFile()/dSpeedDownload ; i++) {
            fileDownload = fileDownload + dSpeedDownload; // скачиваем файл...
            precent = 100 * fileDownload/server.getFile(); // вычисляем процент загрузки
            System.out.println(this.getName() + " Загрузил: " + precent + " %");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.getName() + " закончил загрузку файла");
        System.out.println("______________________________");
        semaphore.release();                        // освобождаем место для хагрузки файла другому Downloader'у, когда скачаем файл
    }

}
