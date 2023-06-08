/**
 * @author: Arsentyeva N.V.
 */
package com.example.database;


import java.io.Serializable;


/**
 * Класс человек для абонента
 */
public class Person implements Serializable {

    private String secondName; // Фамилия

    private String firstName; // Имя

    private String middleName; // Отчество

    private String number; // Номер абонента

    private String personalAccount; // Лицевой счёт

    private Double payment; // Оплата за год


    public Person(String secondName, String firstName, String middleName, String number,
                  String personalAccount, Double payment) {
        setSecondName(secondName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setNumber(number);
        setPersonalAccount(personalAccount);
        setPayment(payment);
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        if (secondName.equals("")) throw new IllegalArgumentException("Фамилия не может быть пустой");
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.equals("")) throw new IllegalArgumentException("Имя не может быть пустым");
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (!number.matches("[78]\\d{10}")) {
            throw new IllegalArgumentException("Номер должен содержать 11 цифр и начинаться с 7 или 8");
        }
        this.number = number;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        if (!personalAccount.matches("\\d{13}")) {
            throw new IllegalArgumentException("Лицевой счёт должен содержать 13 цифр");
        }
        this.personalAccount = personalAccount;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        if (payment == null || payment < 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть не пустой и неотрицательной");
        }
        this.payment = payment;
    }

    public String toString() {
        return "Фамилия : " + getSecondName() + " | " + "Имя : " + getFirstName() +
                " | " + "Отчество : " + getMiddleName() + " | " + "Лицевой счёт :" + getPersonalAccount()
                + " | " + "Номер телефона :" + getNumber() + " | " + "Оплата за год :" + getPayment().toString();
    }
}