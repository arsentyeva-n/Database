package com.example.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс для работы с данными
 */
public class Database {
    /**
     * Создание списка, который оповещает об изменениях
     */
    private ObservableList<Person> usersData = FXCollections.observableArrayList();


    /**
     * Возвращает список ObservableList<Person> userData
     */
    public ObservableList<Person> getUserData() {
        return usersData;
    }


    /**
     * ObservableList конвертируется в ArrayList
     **/
    private ArrayList<Person> toArrayList(ObservableList<Person> usersData) {
        ArrayList<Person> usersDataArray = new ArrayList<Person>();
        for (int i = 0; i < usersData.size(); i++) {
            usersDataArray.add(usersData.get(i));
        }
        return usersDataArray;
    }

    /**
     * ArrayList конвертируется в ObservableList
     **/
    private ObservableList<Person> toObservableList(ArrayList<Person> usersData) {
        ObservableList<Person> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < usersData.size(); i++) {
            observableList.add(usersData.get(i));
        }
        return observableList;
    }

    /**
     * Поиск сотрудника в списке
     */
    public Integer findPerson(String text) {        // принимает искомое значение
        int index = -1;
        for (int i = 0; i < getUserData().size(); i++) {
            if (getUserData().get(i).getPersonalAccount().equals(text)) {   // проверка совпадения
                index = i;
                break;          // прерывается на первом найденом элементе
            }
        }
        return index;           // возвращает адрес строки
    }

    /**
     * Сохранение в файл
     */
    public void saveFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем ограничения на TXT Format
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        // Открываем saveDialog
        File filename = fileChooser.showSaveDialog(null);
        ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(filename));
        // записываем список
        input.writeObject(toArrayList(usersData));
    }

    /**
     * Открытие списка из файла, возвращает нвоый открытый список для отображения в table view
     */
    public ObservableList<Person> open() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        // Устанавливаем ограничения на TXT Format
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        // Открываем openDialog
        File filename = fileChooser.showOpenDialog(null);
        ObjectInputStream output = new ObjectInputStream(new FileInputStream(filename));
        usersData = toObservableList((ArrayList<Person>) output.readObject());
        return usersData;
    }

    /**
     * Очистка списка
     */
    public void clearDatabase() {
        usersData.clear();
    }

}
