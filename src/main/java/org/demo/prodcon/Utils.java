package org.demo.prodcon;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utils {
    public static String getCurrentTime() {
        return "[" + ZonedDateTime.now(ZoneId.of("Europe/Paris")) + "] - ";
    }
}
