package com.haiqal.excessbaggageestimator;

/** Pure calculation logic (no Android code) so it is easy to test. */
public class BaggageCalculator {

    public static final double ECONOMY_LIMIT_KG = 20.0;
    public static final double BUSINESS_LIMIT_KG = 40.0;
    public static final double SERVICE_TAX_RATE = 0.08;

    public static double allowance(boolean isBusiness) {
        return isBusiness ? BUSINESS_LIMIT_KG : ECONOMY_LIMIT_KG;
    }

    /** Excess weight = weight - X, never negative. */
    public static double excessWeight(double weightKg, boolean isBusiness) {
        return Math.max(0, weightKg - allowance(isBusiness));
    }

    /** Excess charge = excess weight * rate per kg. */
    public static double excessCharge(double excessKg, double ratePerKg) {
        return excessKg * ratePerKg;
    }

    /** Total = charge + (8% * charge). */
    public static double totalPayable(double charge) {
        return charge + (SERVICE_TAX_RATE * charge);
    }
}