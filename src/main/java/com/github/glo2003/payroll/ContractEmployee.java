package com.github.glo2003.payroll;

public class ContractEmployee extends Employee {
    private float[] scheduledPayouts;
    private int currentMilestone = 0;

    public ContractEmployee(String name, String role, float[] scheduledPayouts) {
        super(name, role, 0);
        this.scheduledPayouts = scheduledPayouts;
    }

    public float getSalary() {
        if (areMilestonesLeft()) {
            float salary = scheduledPayouts[currentMilestone];
            currentMilestone++;
            return salary;
        } else {
            throwNoPayoutLeftException();
            return 0;
        }
    }

    public void raiseSalary(float raise) {
        if (areMilestonesLeft()) {
            this.scheduledPayouts[currentMilestone] += raise;
        } else {
            throwNoPayoutLeftException();
        }
    }

    private Boolean areMilestonesLeft() {
        return currentMilestone < scheduledPayouts.length;
    }

    private void throwNoPayoutLeftException() {
        throw new RuntimeException("Employee " + getName() + " has no payouts left");
    }
}
