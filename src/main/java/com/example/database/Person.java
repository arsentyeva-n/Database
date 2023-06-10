/**
 * @author: Arsentyeva N.V.
 */
package com.example.database;


import java.io.Serializable;


/**
 * Класс человек для абонента
 * Интерфейс Serializable в Java используется для того, чтобы указать,
 * что объект может быть сериализован - то есть преобразован в последовательность
 * байтов и сохранен в файле.
 */
public class Person implements Serializable {

    private String secondName; // Фамилия

    private String firstName; // Имя

    private String middleName; // Отчество

    private String number; // Номер абонента

    private String personalAccount; // Лицевой счёт

    private Double payment; // Оплата за год

    /**
     * @param secondName Фамилия
     * @param firstName Имя
     * @param middleName Отчество
     * @param number Номер телефона
     * @param personalAccount Лицевой счет
     * @param payment Оплата за год
     */
    public Person(String secondName, String firstName, String middleName, String number, String personalAccount, Double payment) {
        setSecondName(secondName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setNumber(number);
        setPersonalAccount(personalAccount);
        setPayment(payment);
    }

    /** Возвращает Фамилию*/
    public String getSecondName() {
        return secondName;
    }

    /** Новое значение в поле Фамилия*/
    public void setSecondName(String secondName) {
        if (!secondName.matches("^[a-zA-Zа-яА-ЯёЁ]+$"))
            throw new IllegalArgumentException("Фамилия введена некорректно");
        this.secondName = secondName;
    }

    /** Возвращает Имя*/
    public String getFirstName() {
        return firstName;
    }

    /** Новое значение в поле Имя*/
    public void setFirstName(String firstName) {
        if (!firstName.matches("^[a-zA-Zа-яА-ЯёЁ]+$"))
            throw new IllegalArgumentException("Имя введено некорректно");
        this.firstName = firstName;
    }

    /** Возвращает Отчество*/
    public String getMiddleName() {
        return middleName;
    }

    /** Новое значение в поле Отчество*/
    public void setMiddleName(String middleName) {
        if (!middleName.matches("^[a-zA-Zа-яА-ЯёЁ]+$"))
            throw new IllegalArgumentException("Отчество введено некорректно");
        this.middleName = middleName;
    }

    /** Возвращает Номер телефона*/
    public String getNumber() {
        return number;
    }

    /** Новое значение в поле Номер телефона*/
    public void setNumber(String number) {
        if (!number.matches("[78]\\d{10}")) {
            throw new IllegalArgumentException("Номер должен содержать 11 цифр и начинаться с 7 или 8");
        }
        this.number = number;
    }

    /** Возвращает Лицевой счёт*/
    public String getPersonalAccount() {
        return personalAccount;
    }

    /** Новое значение в поле Лицевой счёт*/
    public void setPersonalAccount(String personalAccount) {
        if (!personalAccount.matches("\\d{13}")) {
            throw new IllegalArgumentException("Лицевой счёт должен содержать 13 цифр");
        }
        this.personalAccount = personalAccount;
    }

    /** Возвращает Оплату за год*/
    public Double getPayment() {
        return payment;
    }

    /** Новое значение в поле Оплата за год*/
    public void setPayment(Double payment) {
        if (payment == null || payment < 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть не пустой и неотрицательной");
        }
        this.payment = payment;
    }

    /** Вывод всех данных об абоненте в строку*/
    public String toString() {
        return "Фамилия : " + getSecondName() + " | " + "Имя : " + getFirstName() +
                " | " + "Отчество : " + getMiddleName() + " | " + "Лицевой счёт :" + getPersonalAccount()
                + " | " + "Номер телефона :" + getNumber() + " | " + "Оплата за год :" + getPayment().toString();
    }
}