package com.company;

public class Server {

    private int file;       // файл сервера

    public int getFile() {
        return file;
    }

    public void uploadFile (int file) {           //метод
        this.file = this.file + file;         // где принимаем файл с Uploader
    }
}
