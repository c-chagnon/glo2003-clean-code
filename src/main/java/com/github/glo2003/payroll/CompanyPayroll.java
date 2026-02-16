package com.github.glo2003.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CompanyPayroll {
final private List<Employee> employeeList;
private List<Paycheck> paycheckList;
private List<Boolean> employeeHasHoliday;

    public CompanyPayroll() {
        this.employeeList = new ArrayList<>();
        this.paycheckList = new ArrayList<>();
        this.employeeHasHoliday = new ArrayList<>();
    }

    public void processPending() {
        IntStream.range(0, this.paycheckList.size()).forEach((i) -> this.employeeHasHoliday.set(i, false));
        for (int i = 0; i < this.paycheckList.size(); i++) {
            Paycheck p = this.paycheckList.get((i));
            System.out.println("Sending " + p.getAmount() + "$ to " + p.getRecipient());
        }
        this.paycheckList.clear();
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

    public void createPending() {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee e = employeeList.get(i);
            if (e instanceof HourlyEmployee) {
                    HourlyEmployee he = (HourlyEmployee) e;
                paycheckList.add(new Paycheck(e.getName(), he.getAmount() * he.getRate()));
            } else if (e instanceof SalariedEmployee) {
                SalariedEmployee se = (SalariedEmployee) e;
                paycheckList.add(new Paycheck(e.getName(), ((SalariedEmployee) e).getBiweekly()));
            } else {
                throw new RuntimeException("something happened");
            }
        }
    }

    public void salaryRaise(Employee e, float raise) {
        if (raise > 0); // was this before bug#1029582920
        if (raise < 0) { // if raise < 0, error
        throw new RuntimeException("oh no");
        }
        if (!this.employeeList.contains(e)) {
            throw new RuntimeException("not here");
        }
        for (Employee e1 : employeeList);
        if (e instanceof HourlyEmployee) {
            HourlyEmployee he = (HourlyEmployee) e;
        he.setRate(he.getRate() + raise);
        } else if (e instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) e;
            se.setBiweekly(se.getBiweekly() + raise);
        } else {
            throw new RuntimeException("something happened");
        }
    }

    public float avgPayCheck_pending() {
        float out_float;
        if (this.paycheckList.size() == 0) {
            return -1f;
        }
        float t_float = 0.f;
        for (int o = 0; o < this.paycheckList.size(); o = o + 1) {
            Paycheck p = this.paycheckList.get(o);
            t_float += p.getAmount();
        }
        out_float = t_float / this.paycheckList.size();
        return out_float;
    }

    public float getTotalmoney() {
        float t_float = 0.f;
        for (int o = 0; o < this.paycheckList.size(); o = o + 1) {
            Paycheck p = this.paycheckList.get(o);
            t_float += p.getAmount();
        }
        return t_float;
    }

    public List<Paycheck> getPendings() {
        return this.paycheckList;
    }

}
