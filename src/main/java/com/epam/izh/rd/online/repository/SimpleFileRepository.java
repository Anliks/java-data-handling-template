package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.*;


public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        int count = 0;
        String str = new File("").getAbsolutePath();
        File file = new File(str + "/src/main/resources/" + path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                if (file1.isFile()) {
                    count++;
                } else {
                    String string = file1.getName();
                    count += countFilesInDirectory(path + "/" + string);
                }
            }
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        int x = 0;
        File file = new File("src/main/resources/" + path);
        if (file.isDirectory()) {
            x++;
            for (File fil : file.listFiles()) {
                x += countDirsInDirectory(path + "/" + fil.getName());
            }
        }
        return x;
    }


    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File root = new File(from);
        File toCopy = new File(to);
        if (root.exists()) {
            if (root.isFile() && root.getName().endsWith(".txt")) {
                try {
                    if (!toCopy.exists()) {
                        toCopy.mkdirs();
                    }
                    Files.copy(root.toPath(), toCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (root.isDirectory()) {
                for (File file : root.listFiles()) {
                    copyTXTFiles(file.getAbsolutePath(), toCopy + "/" + root.toPath().relativize(file.toPath()));
                }
            }
        }
    }
    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File newFile = new File("target/classes/" + path + "/" + name);
        try {
            return newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + fileName));
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();}

        return builder.toString();
    }
}
