package com.daungochuyen.utils;

import java.util.UUID;

public class GenerateOrderCodeUtils {
	public static String generateOrderCode() {
        UUID uuid = UUID.randomUUID();
        String orderCode = uuid.toString().substring(0, 8);
        return "#" + orderCode;
    }
}
