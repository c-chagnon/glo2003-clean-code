package com.github.glo2003.payroll;

public class SalariedEmployee extends Employee {
    private float biweekly;

    public SalariedEmployee(String name, String role, int vacationDays, float biweekly) {
        super(name, role, vacationDays);
        this.biweekly = biweekly;
    }

    public float getSalary() {
        return biweekly;
    }

    public void raiseSalary(float raise) {
        this.biweekly += raise;
    }

    @Override
    public String toString() {
        return "SalariedEmployee{" +
                "name='" + this.getName() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", vacationDays=" + this.getVacationDays() +
                ", monthly=" + biweekly +
                '}';
    }
}
