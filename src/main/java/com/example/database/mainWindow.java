/**
 * @author: Arsentyeva N.V.
 */

package com.example.database;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.*;
import java.util.ArrayList;

/**
 * Контроллер для работы с главным окном
 */
public class mainWindow {

    /**
     * Создание Table View
     */
    @FXML
    private TableView<Person> table_database;

    /**
     * Создание списка, который оповещает об изменениях
     */
    private ObservableList<Person> usersData = FXCollections.observableArrayList();

    /**
     * Инициализация колонок для каждого поля объекта Person
     */
    @FXML
    private TableColumn<Person, String> db_PersonalAccount;
    @FXML
    private TableColumn<Person, String> db_first_name;
    @FXML
    private TableColumn<Person, String> db_middle_name;
    @FXML
    private TableColumn<Person, String> db_number;
    @FXML
    private TableColumn<Person, String> db_second_name;
    @FXML
    private TableColumn<Person, Double> db_payment;
    @FXML
    private TextField field_first_name;
    @FXML
    private TextField field_middle_name;
    @FXML
    private TextField field_number;
    @FXML
    private TextField field_personalAccount;
    @FXML
    private TextField field_payment;
    @FXML
    private TextField field_search_data;
    @FXML
    private TextField field_second_name;
    @FXML
    private Label label_error;      // поле состояния
    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * Инициализация таблицы
     */
    private void initTableView() {
        /**Устанавливаем тип и значение которое должно хранится в колонке**/
        db_second_name.setCellValueFactory(new PropertyValueFactory<Person, String>("secondName"));
        db_first_name.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        db_middle_name.setCellValueFactory(new PropertyValueFactory<Person, String>("middleName"));
        db_number.setCellValueFactory(new PropertyValueFactory<Person, String>("number"));
        db_PersonalAccount.setCellValueFactory(new PropertyValueFactory<Person, String>("personalAccount"));
        db_payment.setCellValueFactory(new PropertyValueFactory<Person, Double>("payment"));
        /**Заполняем таблицу данными из коллекции UsersData**/
        table_database.setItems(usersData);
        /**Разрешаем редактирование**/
        table_database.setEditable(true);
        db_second_name.setCellFactory(TextFieldTableCell.forTableColumn());
        db_first_name.setCellFactory(TextFieldTableCell.forTableColumn());
        db_middle_name.setCellFactory(TextFieldTableCell.forTableColumn());
        db_number.setCellFactory(TextFieldTableCell.forTableColumn());
        db_PersonalAccount.setCellFactory(TextFieldTableCell.forTableColumn());
        db_payment.setCellFactory(TextFieldTableCell.forTableColumn((StringConverter<Double>) new DoubleStringConverter()));

    }


    @FXML
    /**Редактирование ячеек Фамилии */
    private void onEditSecondName(TableColumn.CellEditEvent<Person, String> personStringCellEditEvent) {
        try {
            Person person = table_database.getSelectionModel().getSelectedItem();
            person.setSecondName(personStringCellEditEvent.getNewValue());
            int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
            // обновляем данные в списке usersData
            usersData.set(selectedIndex, person);
        } catch (IllegalArgumentException e) {
            label_error.setText("Фамилия введена некорректно");
        }
    }

    @FXML
/**Редактирование ячеек Имени */
    private void onEditFirstName(TableColumn.CellEditEvent<Person, String> personStringCellEditEvent) {
        try {
            Person person = table_database.getSelectionModel().getSelectedItem();
            person.setFirstName(personStringCellEditEvent.getNewValue());
            int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
            usersData.set(selectedIndex, person);
        } catch (IllegalArgumentException e) {
            label_error.setText("Имя введено некорректно");
        }
    }

    @FXML
/**Редактирование ячеек Отчества */
    private void onEditMiddleName(TableColumn.CellEditEvent<Person, String> personStringCellEditEvent) {
        Person person = table_database.getSelectionModel().getSelectedItem();
        person.setMiddleName(personStringCellEditEvent.getNewValue());
        int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
        usersData.set(selectedIndex, person);

    }

    @FXML
/**Редактирование ячеек Номера */
    private void onEditNumber(TableColumn.CellEditEvent<Person, String> personStringCellEditEvent) {
        try {
            Person person = table_database.getSelectionModel().getSelectedItem();
            person.setNumber(personStringCellEditEvent.getNewValue());
            int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
            usersData.set(selectedIndex, person);
        } catch (IllegalArgumentException e) {
            label_error.setText("Номер должен начинаться с 7 или 8 и содержать 11 символов");
        }

    }

    @FXML
/**Редактирование ячеек Лицевого счёта */
    private void onEditPersonalAccount(TableColumn.CellEditEvent<Person, String> personStringCellEditEvent) {
        try {
            Person person = table_database.getSelectionModel().getSelectedItem();
            person.setPersonalAccount(personStringCellEditEvent.getNewValue());
            int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
            usersData.set(selectedIndex, person);
        } catch (IllegalArgumentException e) {
            label_error.setText("Лицевой символ состоит из 13 числовых символов");
        }
    }

    @FXML
/**Редактирование ячеек Оплата за год */
    private void onEditPayment(TableColumn.CellEditEvent<Person, Double> personStringCellEditEvent) {
        try {
            Person person = table_database.getSelectionModel().getSelectedItem();
            person.setPayment(personStringCellEditEvent.getNewValue());
            int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
            usersData.set(selectedIndex, person);
        } catch (IllegalArgumentException e) {
            label_error.setText("Сумма оплаты должна быть не пустой и неотрицательной");
        }
    }

    final String color_error = "#ffc3bf";

    @FXML
    /**Добавление в абонента бд */
    void handleAddUserAction() {
        try {
            // Окрас полей в белый цвет
            field_payment.setStyle("-fx-control-inner-background: #ffffff");
            field_number.setStyle("-fx-control-inner-background: #ffffff");
            field_second_name.setStyle("-fx-control-inner-background: #ffffff");
            field_personalAccount.setStyle("-fx-control-inner-background: #ffffff");
            field_first_name.setStyle("-fx-control-inner-background: #ffffff");
            field_middle_name.setStyle("-fx-control-inner-background: #ffffff");
            label_error.setText("Строка состояния");

            if (field_payment.getText().equals(""))
                throw new IllegalArgumentException("Сумма оплаты должна быть не пустой и неотрицательной");

            // добавление нового объекта в ObservableList
            usersData.add(new Person(field_second_name.getText(), field_first_name.getText(), field_middle_name.getText(),
                    field_number.getText(), field_personalAccount.getText(), Double.parseDouble(field_payment.getText())));
        } catch (IllegalArgumentException e) {
            // При схватывании исключения выводится сообщение этого исключения и поле окрасывается в красный цвет
            if (e.getMessage() == "Имя введено некорректно") {
                label_error.setText(e.getMessage());
                field_first_name.setStyle("-fx-control-inner-background: " + color_error);
            }
            if (e.getMessage() == "Фамилия введена некорректно") {
                label_error.setText(e.getMessage());
                field_second_name.setStyle("-fx-control-inner-background: " + color_error);
            }
            if (e.getMessage() == "Отчество введено некорректно") {
                label_error.setText(e.getMessage());
                field_middle_name.setStyle("-fx-control-inner-background: " + color_error);
            }
            if (e.getMessage() == "Номер должен содержать 11 цифр и начинаться с 7 или 8") {
                label_error.setText(e.getMessage());
                field_number.setStyle("-fx-control-inner-background: " + color_error);
            }
            if (e.getMessage() == "Лицевой счёт должен содержать 13 цифр") {
                label_error.setText(e.getMessage());
                field_personalAccount.setStyle("-fx-control-inner-background: " + color_error);
            }
            if (e.getMessage() == "Сумма оплаты должна быть не пустой и неотрицательной") {
                label_error.setText(e.getMessage());
                field_payment.setStyle("-fx-control-inner-background: " + color_error);
            }
        }
    }

    /**
     * Удаление из бд
     **/
    @FXML
    private void handleDelUserAction(MouseEvent event) {
        // Получаем выбранного человека из таблицы
        Person selectedPerson = table_database.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            // Удаляем выбранного человека из списка
            usersData.remove(selectedPerson);
        }
    }

    @FXML
    /**Поиск сотрудника в списке */
    private void handleFindPerson() {
        String text = field_search_data.getText();
        if (text.equals("")) label_error.setText("Нет параметра для поиска");
        else if (!text.equals("")) {
            for (int i = 0; i < usersData.size(); i++) {
                if (usersData.get(i).getSecondName().equals(text)) {
                    table_database.getSelectionModel().select(i);
                    break;
                }
                if (usersData.get(i).getFirstName().equals(text)) {
                    table_database.getSelectionModel().select(i);
                    break;
                }
                if (usersData.get(i).getMiddleName().equals(text)) {
                    table_database.getSelectionModel().select(i);
                    break;
                }
                if (usersData.get(i).getPersonalAccount().equals(text)) {
                    table_database.getSelectionModel().select(i);
                    break;
                }
                if (usersData.get(i).getNumber().equals(text)) {
                    table_database.getSelectionModel().select(i);
                    break;
                }
            }
        } else {
            label_error.setText("Абонент не найден");
            // снятия выделения с выбранных элементов в таблице
            table_database.getSelectionModel().clearSelection();
        }
    }


    /**
     * ObservableList convert to ArrayList
     **/
    private ArrayList<Person> toArrayList(ObservableList<Person> usersData) {
        ArrayList<Person> usersDataArray = new ArrayList<Person>();
        for (int i = 0; i < usersData.size(); i++) {
            usersDataArray.add(usersData.get(i));
        }
        return usersDataArray;
    }

    /**
     * ArrayList convert to ObservableList
     **/
    private ObservableList<Person> toObservableList(ArrayList<Person> usersData) {
        ObservableList<Person> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < usersData.size(); i++) {
            observableList.add(usersData.get(i));
        }
        return observableList;
    }

    /**
     * Открыть файл
     */
    @FXML
    private void handleOpenFileAction() {
        FileChooser fileChooser = new FileChooser();
        /**Устанавливаем ограничения на TXT Format*/
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        /**Открываем openDialog*/
        File filename = fileChooser.showOpenDialog(null);
        try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(filename))) {
            usersData = toObservableList((ArrayList<Person>) output.readObject());
            table_database.setItems(usersData);

        } catch (Exception ex) {
            label_error.setText("Во время открытия файла произошла ошибка");
        }
    }

    /**
     * Сохранить файл
     */
    @FXML
    private void handleSaveFileAction() {
        FileChooser fileChooser = new FileChooser();
        /**Устанавливаем ограничения на TXT Format */
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        /**Открываем saveDialog**/
        File filename = fileChooser.showSaveDialog(null);
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(filename))) {
            input.writeObject(toArrayList(usersData));
        } catch (Exception ex) {
            label_error.setText("Во время сохранения файла произошла ошибка");
        }
    }

    /**
     * Очистить бд
     */
    public void handleDelAll() {
        usersData.clear();
    }

    /**
     * Окно о разработчике
     */
    @FXML
    protected void onAboutButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // установка заголовка
        alert.setTitle("О разработчике");
        // удаляется подзаголовок
        alert.setHeaderText(null);
        alert.setContentText("Arsentyeva N. V. \nemail: huachensan@gmail.com");
        alert.showAndWait();
    }

    @FXML
    protected void onHotKeyButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // установка заголовка
        alert.setTitle("Горячие клавиши");
        // удаляется подзаголовок
        alert.setHeaderText(null);
        alert.setContentText("Добавить -> ENTER\nОткрыть -> CTRL + O\nСохранить -> CTRL + S\nСправка -> F1");
        alert.showAndWait();
    }

    /**
     * Установка горячих клавиш
     */
    @FXML
    private void setHotKeys() {
        // данный метод привязан к mainAnchorPane с помощью fx:id
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.O) {
                handleOpenFileAction();
            } else if (event.isControlDown() && event.getCode() == KeyCode.S) {
                handleSaveFileAction();
            } else if (event.getCode() == KeyCode.F1) {
                onHotKeyButton();
            } else if (event.getCode() == KeyCode.ENTER) {
                handleAddUserAction();
            }
        });

    }

    @FXML
    void initialize() {
        initTableView();
    }
}