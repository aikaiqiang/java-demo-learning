package com.kaywall.springevent.observer.spring;

public class PaymentInfo {

    private int id;
    private String stauts;

    public PaymentInfo(int id, String stauts) {
        this.id = id;
        this.stauts = stauts;
    }
    // 省略setter getter ....
    @Override
    public String toString() {
        return "PaymentInfo{" +
                "id=" + id +
                ", stauts='" + stauts + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
}