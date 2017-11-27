/**
 * (주)오픈잇 | http://www.openit.co.kr
 * Copyright (c)2006-2015, openit Inc.
 * All rights reserved.
 */
package com.skhynix.hydesign.portal.common.util;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * json 공통화 클래스
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.0
 * @created 2015. 4. 7.
 */
public class DataFormatUtil {

    /**
     * 4200 seconds -> 00d:01h:10m:00s -> 01h:10m
     * 
     * @param timeInSecond int - 입력 초
     * @return
     */
    public static String reformatDate(int timeInSecond) {
        String timeString = String.format("%02dd:%02dh:%02dm:%02ds",
                                          TimeUnit.SECONDS.toDays(timeInSecond),
                                          TimeUnit.SECONDS.toHours(timeInSecond) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(timeInSecond)),
                                          TimeUnit.SECONDS.toMinutes(timeInSecond) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(timeInSecond)), // The change is in this line
                                          TimeUnit.SECONDS.toSeconds(timeInSecond) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(timeInSecond)));
        //^((?!hede).)*$
        timeString = timeString.replaceAll("00d:00h:", "")
                               .replaceAll("00d:", "")
                               .replaceAll("00h:00m:", "")
                               .replaceAll("^00m:", "");
        if (timeString.split(":").length > 1) {
            timeString = timeString.split(":")[0] + ":" + timeString.split(":")[1];
        } else {
            timeString = timeString;
        }
        return timeString;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[] {"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int)(Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 4700 Kbytes -> 4.7MB
     * 
     * @param appendMaps Map - 파싱할 정보
     * @return
     */
    public static String reformatBytes(String kbyteString) {
        if (kbyteString == null) {
            kbyteString = "";
        }
        if (kbyteString.isEmpty()) {
            return "-";
        }
        if (kbyteString.contains("*")) {
            kbyteString = kbyteString.replaceAll("\\*", "");
        }
        int multiplyBytes = kbyteString.contains("M") ? 1024 : (kbyteString.contains("G") ? 1024 * 1024 : 1);
        if (!kbyteString.toLowerCase().contains("byte")) {
            kbyteString = kbyteString + "Bytes";
        }
        long bytes = kbyteString.replaceAll(".[b|B]yte[s|]", "").trim().isEmpty() ? 0
                                                                                 : Integer.parseInt(kbyteString.replaceAll(".[b|B]yte[s|]",
                                                                                                                           "")
                                                                                                               .trim()) * multiplyBytes;
        String byteUnit = multiplyBytes == 1024 ? "MB" : (multiplyBytes == (1024 * 1024) ? "GB" : "KB");

        for (int unit = multiplyBytes; unit <= 1024 * 1024; unit = unit * 1024) {
            if ((bytes / (1024.0D * unit)) > 1.0D) {
                multiplyBytes = multiplyBytes * 1024;
                if (byteUnit.equals("KB")) {
                    byteUnit = "MB";
                } else if (byteUnit.equals("MB")) {
                    byteUnit = "GB";
                }
                break;
            }
        }

        String byteStr = (Math.round((bytes * 10) / multiplyBytes) / 10.0D) + " " + byteUnit;
        byteStr = byteStr.replaceAll("\\.0", "");
        return byteStr;
    }

    /**
     * 4700 Kbytes -> 4.7MB
     * 
     * @param appendMaps Map - 파싱할 정보
     * @return
     */
    public static String reformatBytes(long kbyteLong) {
        String kbyteString = kbyteLong + "KBytes";
        if (kbyteString == null) {
            kbyteString = "";
        }
        if (kbyteString.isEmpty()) {
            return "-";
        }
        if (kbyteString.contains("*")) {
            kbyteString = kbyteString.replaceAll("\\*", "");
        }
        int multiplyBytes = kbyteString.contains("M") ? 1024 : (kbyteString.contains("G") ? 1024 * 1024 : 1);
        if (!kbyteString.toLowerCase().contains("byte")) {
            kbyteString = kbyteString + "Bytes";
        }
        long bytes = kbyteString.replaceAll(".[b|B]yte[s|]", "").trim().isEmpty() ? 0
                                                                                 : Integer.parseInt(kbyteString.replaceAll(".[b|B]yte[s|]",
                                                                                                                           "")
                                                                                                               .trim()) * multiplyBytes;
        String byteUnit = multiplyBytes == 1024 ? "MB" : (multiplyBytes == (1024 * 1024) ? "GB" : "KB");

        for (int unit = multiplyBytes; unit <= 1024 * 1024; unit = unit * 1024) {
            if ((bytes / (1024.0D * unit)) > 1.0D) {
                multiplyBytes = multiplyBytes * 1024;
                if (byteUnit.equals("KB")) {
                    byteUnit = "MB";
                } else if (byteUnit.equals("MB")) {
                    byteUnit = "GB";
                }
                break;
            }
        }

        String byteStr = (Math.round((bytes * 10) / multiplyBytes) / 10.0D) + " " + byteUnit;
        byteStr = byteStr.replaceAll(".0", "");
        if (byteStr.endsWith("MB")) {
            byteStr = reformatBytes(byteStr + "ytes");
        }
        return byteStr;
    }

    public static long toKBytes(String kbyteString) {
        //System.out.println("##" + kbyteString);
        if (kbyteString == null) {
            kbyteString = "";
        }
        if (kbyteString.isEmpty()) {
            return 0;
        }
        if (kbyteString.contains("*")) {
            kbyteString = kbyteString.replaceAll("\\*", "");
        }
        int multiplyBytes = kbyteString.contains("M") ? 1024 : (kbyteString.contains("G") ? 1024 * 1024 : 1);
        if (!kbyteString.toLowerCase().contains("byte")) {
            kbyteString = kbyteString + "Bytes";
        }
        long bytes = kbyteString.replaceAll(".[b|B]yte[s|]", "").trim().isEmpty() ? 0
                                                                                 : Integer.parseInt(kbyteString.replaceAll(".[b|B]yte[s|]",
                                                                                                                           "")
                                                                                                               .trim()) * multiplyBytes;

        return bytes;
    }

    public static String getStringXSS(String value) {
        if(value == null) {
            return null;
        }
        return cleanXSS(value);
    }
    
    private static String cleanXSS(String value) {
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}
