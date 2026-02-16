package com.github.glo2003.payroll;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class CompanyPayrollTest {

    public static final float HOURLY_EMPLOYEE_RATE = 10;
    public static final float HOURLY_EMPLOYEE_AMOUNT = 25;
    public static final String HOURLY_EMPLOYEE_NAME = "William";
    public static final String SALARIED_EMPLOYEE_NAME = "Xavier";
    public static final float BIWEEKLY_EMPLOYEE_AMOUNT = 10_000;
    public static final float RAISE = 10;
    public static final float ANOTHER_MONTHLY_AMOUNT = 20_000;
    public static final int VACATION_DAYS = 12;
    CompanyPayroll company;
    Employee vp;
    Employee eng;
    Employee manager;
    Employee intern1;
    Employee intern2;
    HourlyEmployee hourlyEmployee;
    SalariedEmployee salariedEmployee;
    SalariedEmployee anotherSalariedEmployee;

    @BeforeEach
    void setUp() {
        company = new CompanyPayroll();
        vp = new HourlyEmployee("Alice", "vp", 25, 100, 35.5f * 2);
        eng = new SalariedEmployee("Bob", "engineer", 4, 1500);
        manager = new SalariedEmployee("Charlie", "manager", 10, 2000);
        intern1 = new HourlyEmployee("Ernest", "intern", 10, 5, 50 * 2);
        intern2 = new HourlyEmployee("Fred", "intern", 10, 5, 50 * 2);

        hourlyEmployee = new HourlyEmployee(HOURLY_EMPLOYEE_NAME, "engineer", VACATION_DAYS, HOURLY_EMPLOYEE_RATE, HOURLY_EMPLOYEE_AMOUNT);
        salariedEmployee = new SalariedEmployee(SALARIED_EMPLOYEE_NAME, "engineer", VACATION_DAYS, BIWEEKLY_EMPLOYEE_AMOUNT);
        anotherSalariedEmployee = new SalariedEmployee("Yan", "manager", VACATION_DAYS, ANOTHER_MONTHLY_AMOUNT);
    }

    @Test
    void hourlyEmployee_preparePaychecks_createsCorrectHourlyPaycheck() {
        company.addEmployee(hourlyEmployee);

        company.preparePaychecks();

        Paycheck paycheck = company.getPaycheckList().get(0);
        assertThat(paycheck.getRecipient()).isEqualTo(HOURLY_EMPLOYEE_NAME);
        assertThat(paycheck.getAmount()).isEqualTo(HOURLY_EMPLOYEE_RATE * HOURLY_EMPLOYEE_AMOUNT);
    }

    @Test
    void salariedEmployee_preparePaychecks_createsCorrectSalariedPaycheck() {
        company.addEmployee(salariedEmployee);

        company.preparePaychecks();

        Paycheck paycheck = company.getPaycheckList().get(0);
        assertThat(paycheck.getRecipient()).isEqualTo(SALARIED_EMPLOYEE_NAME);
        assertThat(paycheck.getAmount()).isEqualTo(BIWEEKLY_EMPLOYEE_AMOUNT);
    }

    @Test
    void notZeroEmployees_processPaychecks_noPendingPaychecks() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);
        company.preparePaychecks();

        company.processPaychecks();

        assertThat(company.getPaycheckList().size()).isEqualTo(0);
    }

    @Test
    void oneSoftwareEngineer_findSoftwareEngineers_returnsListWithCorrectEmployee() {
        company.addEmployee(eng);

        List<Employee> es = company.findSoftwareEngineers();
        assertThat(es).containsExactly(eng);
    }

    @Test
    void oneManager_findManagers_returnsListWithCorrectEmployee() {
        company.addEmployee(manager);

        List<Employee> es = company.findManagers();
        assertThat(es).containsExactly(manager);
    }

    @Test
    void oneVicePresident_findVicePresidents_returnsListWithCorrectEmployee() {
        company.addEmployee(vp);

        List<Employee> es = company.findVicePresidents();
        assertThat(es).containsExactly(vp);
    }

    @Test
    void twoInterns_findInterns_returnsListWithCorrectEmployees() {
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        List<Employee> es = company.findInterns();
        assertThat(es).containsExactly(intern1, intern2);
    }

    @Test
    void fiveEmployees_preparePaychecks_fivePaychecksInList() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.preparePaychecks();

        assertThat(company.getPaycheckList().size()).isEqualTo(5);
    }

    @Test
    void hourlyEmployee_raiseSalary_hourlySalaryIsRaised() {
        company.addEmployee(hourlyEmployee);

        company.raiseSalary(hourlyEmployee, RAISE);

        company.preparePaychecks();
        Paycheck paycheck = company.getPaycheckList().get(0);
        assertThat(paycheck.getAmount()).isEqualTo((HOURLY_EMPLOYEE_RATE + RAISE) * HOURLY_EMPLOYEE_AMOUNT);
    }

    @Test
    void salariedEmployee_raiseSalary_monthlySalaryIsRaised() {
        company.addEmployee(salariedEmployee);

        company.raiseSalary(salariedEmployee, RAISE);

        company.preparePaychecks();
        Paycheck paycheck = company.getPaycheckList().get(0);
        assertThat(paycheck.getAmount()).isEqualTo(BIWEEKLY_EMPLOYEE_AMOUNT + RAISE);
    }

    @Test
    void negativeRaise_raiseSalary_throwsRuntimeException() {
        company.addEmployee(eng);

        Assert.assertThrows(RuntimeException.class, () -> company.raiseSalary(eng, -1));
    }

    @Test
    void employeeNotInCompany_raiseSalary_throwsRuntimeException() {
        Assert.assertThrows(RuntimeException.class, () -> company.raiseSalary(eng, 10));
    }

    @Test
    void twoEmployees_getAveragePaycheck_isEqualToAverageOfTwoPaychecks() {
        company.addEmployee(salariedEmployee);
        company.addEmployee(anotherSalariedEmployee);
        company.preparePaychecks();

        float avg = company.getAveragePaycheck();

        assertThat(avg).isEqualTo((BIWEEKLY_EMPLOYEE_AMOUNT + ANOTHER_MONTHLY_AMOUNT) / 2);
    }

    @Test
    void twoEmployees_getTotalPayout_isEqualToSumOfTwoPaychecks() {
        company.addEmployee(salariedEmployee);
        company.addEmployee(anotherSalariedEmployee);
        company.preparePaychecks();

        float t = company.getTotalPayout();

        assertThat(t).isEqualTo(BIWEEKLY_EMPLOYEE_AMOUNT + ANOTHER_MONTHLY_AMOUNT);
    }
}
