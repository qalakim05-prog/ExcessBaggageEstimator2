package com.haiqal.excessbaggageestimator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaggageCalculatorTest {

    @Test
    public void testAllowance() {
        assertEquals(20.0, BaggageCalculator.allowance(false), 0.001);
        assertEquals(40.0, BaggageCalculator.allowance(true), 0.001);
    }

    @Test
    public void testExcessWeightEconomy() {
        // Under allowance
        assertEquals(0.0, BaggageCalculator.excessWeight(15.0, false), 0.001);
        // Exact allowance
        assertEquals(0.0, BaggageCalculator.excessWeight(20.0, false), 0.001);
        // Over allowance
        assertEquals(5.0, BaggageCalculator.excessWeight(25.0, false), 0.001);
    }

    @Test
    public void testExcessWeightBusiness() {
        // Under allowance
        assertEquals(0.0, BaggageCalculator.excessWeight(35.0, true), 0.001);
        // Exact allowance
        assertEquals(0.0, BaggageCalculator.excessWeight(40.0, true), 0.001);
        // Over allowance
        assertEquals(2.5, BaggageCalculator.excessWeight(42.5, true), 0.001);
    }

    @Test
    public void testExcessCharge() {
        // 5 kg * RM 15 = RM 75.00
        assertEquals(75.0, BaggageCalculator.excessCharge(5.0, 15.0), 0.001);
    }

    @Test
    public void testTotalPayable() {
        // RM 75 charge + 8% tax (RM 6) = RM 81.00
        assertEquals(81.0, BaggageCalculator.totalPayable(75.0), 0.001);
    }
}