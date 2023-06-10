/**
 * @author: Arsentyeva N.V.
 */

package com.example.database;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;


/**
 * Контроллер для работы с главным окном
 */
public class mainWindow {

    private Database db = new Database();

    /** Создание Table View */
    @FXML
    private TableView<Person> table_database;

    /** Инициализация колонок для каждого поля объекта Person */
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
        table_database.setItems(db.getUserData());
        /**Разрешаем редактирование**/
        table_database.setEditable(true);
        db_second_name.setCellFactory(TextFieldTableCell.forTableColumn());
        db_first_name.setCellFactory(TextFieldTableCell.forTableColumn());
        db_middle_name.setCellFactory(TextFieldTableCell.forTableColumn());
        db_number.setCellFactory(TextFieldTableCell.forTableColumn());
        db_PersonalAccount.setCellFactory(TextFieldTableCell.forTableColumn());
        db_payment.setCellFactory(TextFieldTableCell.forTableColumn((StringConverter<Double>) new DoubleStringConverter()));

    }

    /**
     * Изменение данных в ячейках таблицы, event - параметр метода,
     * представляющий объект события изменения ячейки таблицы
     * Здесь тип значения в ячейке таблицы не определен, так как он может быть различным для разных колонок
     */
    @FXML
    private void onEditCell(TableColumn.CellEditEvent<Person, ?> event) {
        label_error.setText("Строка состояния");
        try {
            // получение выбранного объекта Person из таблицы по индексу выбранной строки
            Person person = table_database.getSelectionModel().getSelectedItem();
            // получение колонки таблицы, которая была отредактирована
            TableColumn<Person, ?> column = event.getTableColumn();
            // получение нового значения из ячейки таблицы
            Object newValue = event.getNewValue();
            // проверка идентификатора колонки и обновление соответствующего поля объекта Person
            switch (column.getId()) {
                case "db_second_name":
                    person.setSecondName((String) newValue);
                    break;
                case "db_first_name":
                    person.setFirstName((String) newValue);
                    break;
                case "db_middle_name":
                    person.setMiddleName((String) newValue);
                    break;
                case "db_number":
                    person.setNumber((String) newValue);
                    break;
                case "db_personalAccount":
                    person.setPersonalAccount((String) newValue);
                    break;
                case "db_payment":
                    person.setPayment((Double) newValue);
                    break;
                default:
                    break;
            }
            // получение индекса выбранной строки в таблице и обновление данных в бд
            int selectedIndex = table_database.getSelectionModel().getSelectedIndex();
            db.getUserData().set(selectedIndex, person);
        } catch (IllegalArgumentException e) {
            // исключения отображаются в строке состояния
            label_error.setText(e.getMessage());
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
            db.getUserData().add(new Person(field_second_name.getText(), field_first_name.getText(), field_middle_name.getText(),
                    field_number.getText(), field_personalAccount.getText(), Double.parseDouble(field_payment.getText())));
        } catch (IllegalArgumentException e) {
            label_error.setText(e.getMessage()); // Сообщение о вброшенных исключениях
            // При схватывании исключения выводится сообщение этого исключения и поле окрашывается в красный цвет
            switch (e.getMessage()) {
                case "Имя введено некорректно":
                    field_first_name.setStyle("-fx-control-inner-background: " + color_error);
                    break;
                case "Фамилия введена некорректно":
                    field_second_name.setStyle("-fx-control-inner-background: " + color_error);
                    break;
                case "Отчество введено некорректно":
                    field_middle_name.setStyle("-fx-control-inner-background: " + color_error);
                    break;
                case "Номер должен содержать 11 цифр и начинаться с 7 или 8":
                    field_number.setStyle("-fx-control-inner-background: " + color_error);
                    break;
                case "Лицевой счёт должен содержать 13 цифр":
                    field_personalAccount.setStyle("-fx-control-inner-background: " + color_error);
                    break;
                case "Сумма оплаты должна быть не пустой и неотрицательной":
                    field_payment.setStyle("-fx-control-inner-background: " + color_error);
                    break;
                default:
                    break;
            }
        }
    }

    /** Удаление из бд */
    @FXML
    private void handleDelUserAction(MouseEvent event) {
        // Получаем выбранного человека из таблицы
        Person selectedPerson = table_database.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            // Удаляем выбранного человека из списка
            db.getUserData().remove(selectedPerson);
        }
    }

    @FXML
    /**Поиск сотрудника в списке */
    private void handleFindPerson() {
        String text = field_search_data.getText();
        if (text.equals("")) label_error.setText("Нет параметра для поиска");
        else if (!text.equals("")) {
            if (db.findPerson(text) == -1) {
                label_error.setText("Абонент не найден");
                // снятия выделения с выбранных элементов в таблице
                table_database.getSelectionModel().clearSelection();
            } else table_database.getSelectionModel().select(db.findPerson(text));
        }
    }


    /** Открыть файл */
    @FXML
    private void handleOpenFileAction() {
        try {
            db.open();
            table_database.setItems(db.getUserData());
        } catch (Exception ex) {
            label_error.setText("Во время открытия файла произошла ошибка");
        }
    }

    /** Сохранить файл */
    @FXML
    private void handleSaveFileAction() {
        try {
            db.saveFile();
        } catch (Exception ex) {
            label_error.setText("Во время сохранения файла произошла ошибка");
        }
    }

    /** Очистить бд  */
    public void handleDelAll() {
        db.clearDatabase();
    }

    /** Окно о разработчике  */
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

    /** Окно о горячих клавишах  */
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

    /** Установка горячих клавиш */
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