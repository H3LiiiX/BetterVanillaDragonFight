package com.h3liiix.bettervanilladragonfight;

public class PerchContext {
    public static final ThreadLocal<Boolean> isPerchRegen = ThreadLocal.withInitial(() -> false);
}
