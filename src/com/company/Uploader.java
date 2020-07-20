package com.company;

import java.util.concurrent.CountDownLatch;

public class Uploader extends Thread {

    Server server;
    private int fileUpload;          // файл,который мы хотим загрузить на сервер
    private int dSpeedUploader;
    private int precent = 0;
    CountDownLatch countDownLatch;

    public Uploader(Server server, int file, int dSpeedUploader, CountDownLatch countDownLatch) {
        this.server = server;
        fileUpload = file;
        this.dSpeedUploader = dSpeedUploader;
        this.countDownLatch = countDownLatch;
    }

    public void run (){
        System.out.println("Загружаем файл в сервер");
        for (int i = 0; i < fileUpload/dSpeedUploader ; i++) {
            server.uploadFile(dSpeedUploader);               //загружаем наш файл на сервер
            precent = 100 *server.getFile()/fileUpload;
            System.out.println("Загружено: " + precent  + " %");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("файл загружен; " + "размер: " + server.getFile());
        countDownLatch.countDown(); // оповещаем Downloader'ов, что файл готов к скачиванию
    }

}
