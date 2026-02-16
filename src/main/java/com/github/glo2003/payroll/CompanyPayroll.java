package com.github.glo2003.payroll;

import java.util.ArrayList;
import java.util.List;

public class CompanyPayroll {
final private List<Employee> employeeList;
private List<Paycheck> paycheckList;
private List<Boolean> employeeHasHoliday;

    public CompanyPayroll() {
        this.employeeList = new ArrayList<>();
        this.paycheckList = new ArrayList<>();
        this.employeeHasHoliday = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        this.employeeHasHoliday.add(false);
    }

    public List<Employee> findSoftwareEngineers() {
        return findEmployeesByRole("engineer");
    }

    public List<Employee> findManagers() {
        return findEmployeesByRole("manager");
    }

    public List<Employee> findVicePresidents() {
        return findEmployeesByRole("vp");
    }

    public List<Employee> findInterns() {
        return findEmployeesByRole("intern");
    }

    public List<Employee> findEmployeesByRole(String role) {
        List<Employee> employeesWithRole = new ArrayList<>();
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getRole().equals(role)) {
                employeesWithRole.add(employeeList.get(i));
            }
        }
        return employeesWithRole;
    }

    public void preparePaychecks() {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee e = employeeList.get(i);
            paycheckList.add(new Paycheck(e.getName(), e.getSalary()));
        }
    }

    public void processPaychecks() {
        for (int i = 0; i < this.paycheckList.size(); i++) {
            Paycheck p = this.paycheckList.get((i));
            System.out.println("Sending " + p.getAmount() + "$ to " + p.getRecipient());
        }
        this.paycheckList.clear();
    }

    public void raiseSalary(Employee e, float raise) {
        if (raise < 0) {
            throw new RuntimeException("Salary raise cannot be negative");
        }
        if (!this.employeeList.contains(e)) {
            throw new RuntimeException("Employee " + e.getName() + " is not in company payroll");
        }
        e.raiseSalary(raise);
    }

    public float getAveragePaycheck() {
        if (this.paycheckList.size() == 0) {
            return -1f;
        }
        return getTotalPayout() / this.paycheckList.size();
    }

    public float getTotalPayout() {
        float total = 0;
        for (int i = 0; i < this.paycheckList.size(); i++) {
            Paycheck p = this.paycheckList.get(i);
            total += p.getAmount();
        }
        return total;
    }

    public List<Paycheck> getPaycheckList() {
        return this.paycheckList;
    }
}
