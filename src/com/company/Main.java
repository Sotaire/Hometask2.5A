package com.company;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        Semaphore semaphore = new Semaphore(3,true);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Uploader uploader = new Uploader(server,500,20,countDownLatch);
        uploader.start();

        for (int i = 1; i <= 11 ; i++) {  // сделал 11 Downloader'ов , чтоб последнему не пришлось одному скачивать файл xD
            Downloaders downloaders = new Downloaders(server.getFile(),100,countDownLatch,semaphore,server);
            downloaders.setName("Downloader" + i);
            downloaders.start();
        }
    }
}
