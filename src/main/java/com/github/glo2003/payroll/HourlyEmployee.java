package com.github.glo2003.payroll;

public class HourlyEmployee extends Employee {
    private float rate;
    private float amount;

    public HourlyEmployee(String name, String role, int vacationDays, float rate, float amount) {
        super(name, role, vacationDays);
        this.rate = rate;
        this.amount = amount;
    }

    public float getRate() {
        return rate;
    }

    public float getAmount() {
        return amount;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "name='" + this.getName() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", vacationDays=" + this.getVacationDays() +
                ", hourlyRate=" + rate +
                ", amount=" + amount +
                '}';
    }
}
