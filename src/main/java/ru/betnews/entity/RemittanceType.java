package ru.betnews.entity;

/**
 * Created by Evgeniy.Guzeev on 09.04.2018.
 */
public enum RemittanceType {
    REPLENISHMENT("remittance.type.replenishment"),//Ввод средств
    WITHDRAWAL("remittance.type.withdrawal"),//Вывод средст
    COMMISSION("remittance.type.commission"),//Плата за комиссию
    WINNINGS("remittance.type.winnings"),//Выигрыш
    LOSS("remittance.type.loss"),//Проигрыш
    SALARY("remittance.type.salary"), //Зарплата
    VOTE("remittance.type.vote"); //Ставка

    String remittanceType;
    RemittanceType(String remittanceType){
        this.remittanceType = remittanceType;
    }

    public String getRemittanceType() {
        return remittanceType;
    }
}
