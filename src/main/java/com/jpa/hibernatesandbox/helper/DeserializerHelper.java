package com.jpa.hibernatesandbox.helper;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigInteger;

public final class DeserializerHelper {
    public DeserializerHelper() {}

    public static BigInteger getBigInteger(JsonNode node, String fieldName) {
        BigInteger returnBigInt = null;
        if(node != null) {
            returnBigInt = node.isBigInteger() ? BigInteger.valueOf(node.findValue(fieldName).asLong()) : null;
        }
        return returnBigInt;
    }

    public static Integer getInteger(JsonNode node, String fieldName) {
        Integer returnInt = null;
        if(node != null) {
            returnInt = node.canConvertToInt() ? node.findValue(fieldName).asInt() : null;
        }
        return returnInt;
    }

    public static Float getFloat(JsonNode node, String fieldName) {
        Float returnFloat = null;
        if(node != null) {
            returnFloat = node.isFloat() ? (float) node.asDouble() : null;
        }
        return returnFloat;
    }
}
