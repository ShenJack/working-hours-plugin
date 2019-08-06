package org.jenkinsci.plugins.workinghours.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 中国农历
 * Created by heqiao on 2019/3/31.
 *
 * @author joel
 */
public final class ChineseLunarCalendar implements Serializable {
    /**
     * 支持的最小年份
     */
    public final static int MINI_YEAR = 1900;
    /**
     * 支持的最大年份
     */
    public final static int MAX_YEAR = 2150;
    /**
     * 10天干
     */
    protected static final char[] LunarGan = {'甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸'};
    /**
     * 12地支
     */
    protected static final char[] LunarZhi = {'子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥'};
    /**
     * 12生肖
     */
    protected static final char[] LunarAnimailName = {'鼠', '牛', '虎', '兔', '龙', '蛇', '马', '羊', '猴', '鸡', '狗', '猪'};
    /**
     * 农历年份名
     */
    protected static final char[] LunarYearName = {'〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
    /**
     * 农历月份名
     */
    protected static final char[] LunarMonthName = {'正', '二', '三', '四', '五', '六', '七', '八', '九', '十', '冬', '腊'};
    /**
     * 农历日期名
     */
    protected static final String[] LunarDayName = {"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一",
        "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "廿十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九",
        "卅十"};
    /**
     * 农历信息.<br>
     * 每个数组的第一个数表示该年闰月月份，为0表示不闰月<br>
     * 数组中其他数表示该月初一对应的公历日期<br>
     */
    protected final static int[][] LUAR_INFO = {
        {8, 131, 301, 331, 429, 528, 627, 726, 825, 924, 1023, 1122, 1222, 1320}, // 1900
        {0, 219, 320, 419, 518, 616, 716, 814, 913, 1012, 1111, 1211, 1310}, // 1901
        {0, 208, 310, 408, 508, 606, 705, 804, 902, 1002, 1031, 1130, 1230}, // 1902
        {5, 129, 227, 329, 427, 527, 625, 724, 823, 921, 1020, 1119, 1219, 1317}, // 1903
        {0, 216, 317, 416, 515, 614, 713, 811, 910, 1009, 1107, 1207, 1306}, // 1904
        {0, 204, 306, 405, 504, 603, 703, 801, 830, 929, 1028, 1127, 1226}, // 1905
        {4, 125, 223, 325, 424, 523, 622, 721, 820, 918, 1018, 1116, 1216, 1314}, // 1906
        {0, 213, 314, 413, 512, 611, 710, 809, 908, 1007, 1106, 1205, 1304}, // 1907
        {0, 202, 303, 401, 430, 530, 629, 728, 827, 925, 1025, 1124, 1223}, // 1908
        {2, 122, 220, 322, 420, 519, 618, 717, 816, 914, 1014, 1113, 1213, 1311}, // 1909
        {0, 210, 311, 410, 509, 607, 707, 805, 904, 1003, 1102, 1202, 1301}, // 1910
        {6, 130, 301, 330, 429, 528, 626, 726, 824, 922, 1022, 1121, 1220, 1319}, // 1911
        {0, 218, 319, 417, 517, 615, 714, 813, 911, 1010, 1109, 1209, 1307}, // 1912
        {0, 206, 308, 407, 506, 605, 704, 802, 901, 930, 1029, 1128, 1227}, // 1913
        {5, 126, 225, 327, 425, 525, 623, 723, 821, 920, 1019, 1117, 1217, 1315}, // 1914
        {0, 214, 316, 414, 514, 613, 712, 811, 909, 1009, 1107, 1207, 1305}, // 1915
        {0, 203, 304, 403, 502, 601, 630, 730, 829, 927, 1027, 1125, 1225}, // 1916
        {2, 123, 222, 323, 421, 521, 619, 719, 818, 916, 1016, 1115, 1214, 1313}, // 1917
        {0, 211, 313, 411, 510, 609, 708, 807, 905, 1005, 1104, 1203, 1302}, // 1918
        {7, 201, 302, 401, 430, 529, 628, 727, 825, 924, 1024, 1122, 1222, 1321}, // 1919
        {0, 220, 320, 419, 518, 616, 716, 814, 912, 1012, 1110, 1210, 1309}, // 1920
        {0, 208, 310, 408, 508, 606, 705, 804, 902, 1001, 1031, 1129, 1229}, // 1921
        {5, 128, 227, 328, 427, 527, 625, 724, 823, 921, 1020, 1119, 1218, 1317}, // 1922
        {0, 216, 317, 416, 516, 614, 714, 812, 911, 1010, 1108, 1208, 1306}, // 1923
        {0, 205, 305, 404, 504, 602, 702, 801, 830, 929, 1028, 1127, 1226}, // 1924
        {4, 124, 223, 324, 423, 522, 621, 721, 819, 918, 1018, 1116, 1216, 1314}, // 1925
        {0, 213, 314, 412, 512, 610, 710, 808, 907, 1007, 1105, 1205, 1304}, // 1926
        {0, 202, 304, 402, 501, 531, 629, 729, 827, 926, 1025, 1124, 1224}, // 1927
        {2, 123, 221, 322, 420, 519, 618, 717, 815, 914, 1013, 1112, 1212, 1311}, // 1928
        {0, 210, 311, 410, 509, 607, 707, 805, 903, 1003, 1101, 1201, 1231}, // 1929
        {6, 130, 228, 330, 429, 528, 626, 726, 824, 922, 1022, 1120, 1220, 1319}, // 1930
        {0, 217, 319, 418, 517, 616, 715, 814, 912, 1011, 1110, 1209, 1308}, // 1931
        {0, 206, 307, 406, 506, 604, 704, 802, 901, 930, 1029, 1128, 1227}, // 1932
        {5, 126, 224, 326, 425, 524, 623, 722, 821, 920, 1019, 1118, 1217, 1315}, // 1933
        {0, 214, 315, 414, 513, 612, 712, 810, 909, 1008, 1107, 1207, 1305}, // 1934
        {0, 204, 305, 403, 503, 601, 701, 730, 829, 928, 1027, 1126, 1226}, // 1935
        {3, 124, 223, 323, 421, 521, 619, 718, 817, 916, 1015, 1114, 1214, 1313}, // 1936
        {0, 211, 313, 411, 510, 609, 708, 806, 905, 1004, 1103, 1203, 1302}, // 1937
        {7, 131, 302, 401, 430, 529, 628, 727, 825, 924, 1023, 1122, 1222, 1320}, // 1938
        {0, 219, 321, 420, 519, 617, 717, 815, 913, 1013, 1111, 1211, 1309}, // 1939
        {0, 208, 309, 408, 507, 606, 705, 804, 902, 1001, 1031, 1129, 1229}, // 1940
        {6, 127, 226, 328, 426, 526, 625, 724, 823, 921, 1020, 1119, 1218, 1317}, // 1941
        {0, 215, 317, 415, 515, 614, 713, 812, 910, 1010, 1108, 1208, 1306}, // 1942
        {0, 205, 306, 405, 504, 603, 702, 801, 831, 929, 1029, 1127, 1227}, // 1943
        {4, 125, 224, 324, 423, 522, 621, 720, 819, 917, 1017, 1116, 1215, 1314}, // 1944
        {0, 213, 314, 412, 512, 610, 709, 808, 906, 1006, 1105, 1205, 1303}, // 1945
        {0, 202, 304, 402, 501, 531, 629, 728, 827, 925, 1025, 1124, 1223}, // 1946
        {2, 122, 221, 323, 421, 520, 619, 718, 816, 915, 1014, 1113, 1212, 1311}, // 1947
        {0, 210, 311, 409, 509, 607, 707, 805, 903, 1003, 1101, 1201, 1230}, // 1948
        {7, 129, 228, 329, 428, 528, 626, 726, 824, 922, 1022, 1120, 1220, 1318}, // 1949
        {0, 217, 318, 417, 517, 615, 715, 814, 912, 1011, 1110, 1209, 1308}, // 1950
        {0, 206, 308, 406, 506, 605, 704, 803, 901, 1001, 1030, 1129, 1228}, // 1951
        {5, 127, 225, 326, 424, 524, 622, 722, 820, 919, 1019, 1117, 1217, 1315}, // 1952
        {0, 214, 315, 414, 513, 611, 711, 810, 908, 1008, 1107, 1206, 1305}, // 1953
        {0, 203, 305, 403, 503, 601, 630, 730, 828, 927, 1027, 1126, 1225}, // 1954
        {3, 124, 222, 324, 422, 522, 620, 719, 818, 916, 1016, 1114, 1214, 1313}, // 1955
        {0, 212, 312, 411, 510, 609, 708, 806, 905, 1004, 1103, 1203, 1301}, // 1956
        {8, 131, 302, 331, 430, 529, 628, 727, 825, 924, 1023, 1122, 1221, 1320}, // 1957
        {0, 218, 320, 419, 519, 617, 717, 815, 913, 1013, 1111, 1211, 1309}, // 1958
        {0, 208, 309, 408, 508, 606, 706, 804, 903, 1002, 1101, 1130, 1230}, // 1959
        {6, 128, 227, 327, 426, 525, 624, 724, 822, 921, 1020, 1119, 1218, 1317}, // 1960
        {0, 215, 317, 415, 515, 613, 713, 811, 910, 1010, 1108, 1208, 1306}, // 1961
        {0, 205, 306, 405, 504, 602, 702, 731, 830, 929, 1028, 1127, 1227}, // 1962
        {4, 125, 224, 325, 424, 523, 621, 721, 819, 918, 1017, 1116, 1216, 1315}, // 1963
        {0, 213, 314, 412, 512, 610, 709, 808, 906, 1006, 1104, 1204, 1303}, // 1964
        {0, 202, 303, 402, 501, 531, 629, 728, 827, 925, 1024, 1123, 1223}, // 1965
        {3, 121, 220, 322, 421, 520, 619, 718, 816, 915, 1014, 1112, 1212, 1311}, // 1966
        {0, 209, 311, 410, 509, 608, 708, 806, 904, 1004, 1102, 1202, 1231}, // 1967
        {7, 130, 228, 329, 427, 527, 626, 725, 824, 922, 1022, 1120, 1220, 1318}, // 1968
        {0, 217, 318, 417, 516, 615, 714, 813, 912, 1011, 1110, 1209, 1308}, // 1969
        {0, 206, 308, 406, 505, 604, 703, 802, 901, 930, 1030, 1129, 1228}, // 1970
        {5, 127, 225, 327, 425, 524, 623, 722, 821, 919, 1019, 1118, 1218, 1316}, // 1971
        {0, 215, 315, 414, 513, 611, 711, 809, 908, 1007, 1106, 1206, 1304}, // 1972
        {0, 203, 305, 403, 503, 601, 630, 730, 828, 926, 1026, 1125, 1224}, // 1973
        {4, 123, 222, 324, 422, 522, 620, 719, 818, 916, 1015, 1114, 1214, 1312}, // 1974
        {0, 211, 313, 412, 511, 610, 709, 807, 906, 1005, 1103, 1203, 1301}, // 1975
        {8, 131, 301, 331, 429, 529, 627, 727, 825, 924, 1023, 1121, 1221, 1319}, // 1976
        {0, 218, 320, 418, 518, 617, 716, 815, 913, 1013, 1111, 1211, 1309}, // 1977
        {0, 207, 309, 407, 507, 606, 705, 804, 902, 1002, 1101, 1130, 1230}, // 1978
        {6, 128, 227, 328, 426, 526, 624, 724, 823, 921, 1021, 1120, 1219, 1318}, // 1979
        {0, 216, 317, 415, 514, 613, 712, 811, 909, 1009, 1108, 1207, 1306}, // 1980
        {0, 205, 306, 405, 504, 602, 702, 731, 829, 928, 1028, 1126, 1226}, // 1981
        {4, 125, 224, 325, 424, 523, 621, 721, 819, 917, 1017, 1115, 1215, 1314}, // 1982
        {0, 213, 315, 413, 513, 611, 710, 809, 907, 1006, 1105, 1204, 1303}, // 1983
        {10, 202, 303, 401, 501, 531, 629, 728, 827, 925, 1024, 1123, 1222, 1321}, // 1984
        {0, 220, 321, 420, 520, 618, 718, 816, 915, 1014, 1112, 1212, 1310}, // 1985
        {0, 209, 310, 409, 509, 607, 707, 806, 904, 1004, 1102, 1202, 1231}, // 1986
        {6, 129, 228, 329, 428, 527, 626, 726, 824, 923, 1023, 1121, 1221, 1319}, // 1987
        {0, 217, 318, 416, 516, 614, 714, 812, 911, 1011, 1109, 1209, 1308}, // 1988
        {0, 206, 308, 406, 505, 604, 703, 802, 831, 930, 1029, 1128, 1228}, // 1989
        {5, 127, 225, 327, 425, 524, 623, 722, 820, 919, 1018, 1117, 1217, 1316}, // 1990
        {0, 215, 316, 415, 514, 612, 712, 810, 908, 1008, 1106, 1206, 1305}, // 1991
        {0, 204, 304, 403, 503, 601, 630, 730, 828, 926, 1026, 1124, 1224}, // 1992
        {3, 123, 221, 323, 422, 521, 620, 719, 818, 916, 1015, 1114, 1213, 1312}, // 1993
        {0, 210, 312, 411, 511, 609, 709, 807, 906, 1005, 1103, 1203, 1301}, // 1994
        {8, 131, 301, 331, 430, 529, 628, 727, 826, 925, 1024, 1122, 1222, 1320}, // 1995
        {0, 219, 319, 418, 517, 616, 715, 814, 912, 1012, 1111, 1211, 1309}, // 1996
        {0, 207, 309, 407, 507, 605, 705, 803, 902, 1002, 1031, 1130, 1230}, // 1997
        {5, 128, 227, 328, 426, 526, 624, 723, 822, 921, 1020, 1119, 1219, 1317}, // 1998
        {0, 216, 318, 416, 515, 614, 713, 811, 910, 1009, 1108, 1208, 1307}, // 1999
        {0, 205, 306, 405, 504, 602, 702, 731, 829, 928, 1027, 1126, 1226}, // 2000
        {4, 124, 223, 325, 423, 523, 621, 721, 819, 917, 1017, 1115, 1215, 1313}, // 2001
        {0, 212, 314, 413, 512, 611, 710, 809, 907, 1006, 1105, 1204, 1303}, // 2002
        {0, 201, 303, 402, 501, 531, 630, 729, 828, 926, 1025, 1124, 1223}, // 2003
        {2, 122, 220, 321, 419, 519, 618, 717, 816, 914, 1014, 1112, 1212, 1310}, // 2004
        {0, 209, 310, 409, 508, 607, 706, 805, 904, 1003, 1102, 1201, 1231}, // 2005
        {7, 129, 228, 329, 428, 527, 626, 725, 824, 922, 1022, 1121, 1220, 1319}, // 2006
        {0, 218, 319, 417, 517, 615, 714, 813, 911, 1011, 1110, 1210, 1308}, // 2007
        {0, 207, 308, 406, 505, 604, 703, 801, 831, 929, 1029, 1128, 1227}, // 2008
        {5, 126, 225, 327, 425, 524, 623, 722, 820, 919, 1018, 1117, 1216, 1315}, // 2009
        {0, 214, 316, 414, 514, 612, 712, 810, 908, 1008, 1106, 1206, 1304}, // 2010
        {0, 203, 305, 403, 503, 602, 701, 731, 829, 927, 1027, 1125, 1225}, // 2011
        {4, 123, 222, 322, 421, 521, 619, 719, 817, 916, 1015, 1114, 1213, 1312}, // 2012
        {0, 210, 312, 410, 510, 608, 708, 807, 905, 1005, 1103, 1203, 1301}, // 2013
        {9, 131, 301, 331, 429, 529, 627, 727, 825, 924, 1024, 1122, 1222, 1320}, // 2014
        {0, 219, 320, 419, 518, 616, 716, 814, 913, 1013, 1112, 1211, 1310}, // 2015
        {0, 208, 309, 407, 507, 605, 704, 803, 901, 1001, 1031, 1129, 1229}, // 2016
        {6, 128, 226, 328, 426, 526, 624, 723, 822, 920, 1020, 1118, 1218, 1317}, // 2017
        {0, 216, 317, 416, 515, 614, 713, 811, 910, 1009, 1108, 1207, 1306}, // 2018
        {0, 205, 307, 405, 505, 603, 703, 801, 830, 929, 1028, 1126, 1226}, // 2019
        {4, 125, 223, 324, 423, 523, 621, 721, 819, 917, 1017, 1115, 1215, 1313}, // 2020
        {0, 212, 313, 412, 512, 610, 710, 808, 907, 1006, 1105, 1204, 1303}, // 2021
        {0, 201, 303, 401, 501, 530, 629, 729, 827, 926, 1025, 1124, 1223}, // 2022
        {2, 122, 220, 322, 420, 519, 618, 718, 816, 915, 1015, 1113, 1213, 1311}, // 2023
        {0, 210, 310, 409, 508, 606, 706, 804, 903, 1003, 1101, 1201, 1231}, // 2024
        {6, 129, 228, 329, 428, 527, 625, 725, 823, 922, 1021, 1120, 1220, 1319}, // 2025
        {0, 217, 319, 417, 517, 615, 714, 813, 911, 1010, 1109, 1209, 1308}, // 2026
        {0, 206, 308, 407, 506, 605, 704, 802, 901, 930, 1029, 1128, 1228}, // 2027
        {5, 126, 225, 326, 425, 524, 623, 722, 820, 919, 1018, 1116, 1216, 1315}, // 2028
        {0, 213, 315, 414, 513, 612, 711, 810, 908, 1008, 1106, 1205, 1304}, // 2029
        {0, 203, 304, 403, 502, 601, 701, 730, 829, 927, 1027, 1125, 1225}, // 2030
        {3, 123, 221, 323, 422, 521, 620, 719, 818, 917, 1016, 1115, 1214, 1313}, // 2031
        {0, 211, 312, 410, 509, 608, 707, 806, 905, 1004, 1103, 1203, 1301}, // 2032
        {7, 131, 301, 331, 429, 528, 627, 726, 825, 923, 1023, 1122, 1222, 1320}, // 2033
        {0, 219, 320, 419, 518, 616, 716, 814, 913, 1012, 1111, 1211, 1309}, // 2034
        {0, 208, 310, 408, 508, 606, 705, 804, 902, 1001, 1031, 1130, 1229}, // 2035
        {6, 128, 227, 328, 426, 526, 624, 723, 822, 920, 1019, 1118, 1217, 1316}, // 2036
        {0, 215, 317, 416, 515, 614, 713, 811, 910, 1009, 1107, 1207, 1305}, // 2037
        {0, 204, 306, 405, 504, 603, 702, 801, 830, 929, 1028, 1126, 1226}, // 2038
        {5, 124, 223, 325, 423, 523, 622, 721, 820, 918, 1018, 1116, 1216, 1314}, // 2039
        {0, 212, 313, 411, 511, 610, 709, 808, 906, 1006, 1105, 1204, 1303}, // 2040
        {0, 201, 302, 401, 430, 530, 628, 728, 827, 925, 1025, 1124, 1223}, // 2041
        {2, 122, 220, 322, 420, 519, 618, 717, 816, 914, 1014, 1113, 1212, 1311}, // 2042
        {0, 210, 311, 410, 509, 607, 707, 805, 903, 1003, 1102, 1201, 1231}, // 2043
        {7, 130, 229, 329, 428, 527, 625, 725, 823, 921, 1021, 1119, 1219, 1318}, // 2044
        {0, 217, 319, 417, 517, 615, 714, 813, 911, 1010, 1109, 1208, 1307}, // 2045
        {0, 206, 308, 406, 506, 604, 704, 802, 901, 930, 1029, 1128, 1227}, // 2046
        {5, 126, 225, 326, 425, 525, 623, 723, 821, 920, 1019, 1117, 1217, 1315}, // 2047
        {0, 214, 314, 413, 513, 611, 711, 810, 908, 1008, 1106, 1205, 1304}, // 2048
        {0, 202, 304, 402, 502, 531, 630, 730, 828, 927, 1027, 1125, 1225}, // 2049
        {3, 123, 221, 323, 421, 521, 619, 719, 817, 916, 1016, 1114, 1214, 1313}, // 2050
        {0, 211, 313, 411, 510, 609, 708, 806, 905, 1005, 1103, 1203, 1302}, // 2051
        {8, 201, 301, 331, 429, 528, 627, 726, 824, 923, 1022, 1121, 1221, 1320}, // 2052
        {0, 219, 320, 419, 518, 616, 716, 814, 912, 1012, 1110, 1210, 1309}, // 2053
        {0, 208, 309, 408, 508, 606, 705, 804, 902, 1001, 1031, 1129, 1229}, // 2054
        {6, 128, 226, 328, 427, 526, 625, 724, 823, 921, 1020, 1119, 1218, 1317}, // 2055
        {0, 215, 316, 415, 515, 613, 713, 811, 910, 1009, 1107, 1207, 1305}, // 2056
        {0, 204, 305, 404, 504, 602, 702, 731, 830, 929, 1028, 1126, 1226}, // 2057
        {4, 124, 223, 324, 423, 522, 621, 720, 819, 918, 1017, 1116, 1216, 1314}, // 2058
        {0, 212, 314, 412, 512, 610, 710, 808, 907, 1006, 1105, 1205, 1304}, // 2059
        {0, 202, 303, 401, 501, 530, 628, 727, 826, 924, 1024, 1123, 1223}, // 2060
        {3, 121, 220, 322, 420, 519, 618, 717, 815, 914, 1013, 1112, 1212, 1311}, // 2061
        {0, 209, 311, 410, 509, 607, 707, 805, 903, 1003, 1101, 1201, 1231}, // 2062
        {7, 129, 228, 330, 428, 528, 626, 726, 824, 922, 1022, 1120, 1220, 1318}, // 2063
        {0, 217, 318, 417, 516, 615, 714, 813, 911, 1010, 1109, 1208, 1307}, // 2064
        {0, 205, 307, 406, 505, 604, 704, 802, 901, 930, 1029, 1128, 1227}, // 2065
        {5, 126, 224, 326, 424, 524, 623, 722, 821, 919, 1019, 1117, 1217, 1315}, // 2066
        {0, 214, 315, 414, 513, 612, 711, 810, 909, 1008, 1107, 1206, 1305}, // 2067
        {0, 203, 304, 402, 502, 531, 629, 729, 828, 926, 1026, 1125, 1224}, // 2068
        {4, 123, 221, 323, 421, 521, 619, 718, 817, 915, 1015, 1114, 1214, 1312}, // 2069
        {0, 211, 312, 411, 510, 609, 708, 806, 905, 1004, 1103, 1203, 1301}, // 2070
        {8, 131, 302, 331, 430, 529, 628, 727, 825, 924, 1023, 1122, 1221, 1320}, // 2071
        {0, 219, 320, 418, 518, 616, 716, 814, 912, 1012, 1110, 1210, 1308}, // 2072
        {0, 207, 309, 407, 507, 606, 705, 804, 902, 1001, 1031, 1129, 1229}, // 2073
        {6, 127, 226, 327, 426, 526, 624, 724, 822, 921, 1020, 1119, 1218, 1317}, // 2074
        {0, 215, 317, 415, 515, 613, 713, 812, 910, 1010, 1108, 1208, 1306}, // 2075
        {0, 205, 305, 404, 503, 602, 701, 731, 829, 928, 1028, 1126, 1226}, // 2076
        {4, 124, 223, 324, 423, 522, 620, 720, 818, 917, 1017, 1116, 1215, 1314}, // 2077
        {0, 212, 314, 412, 512, 610, 709, 808, 906, 1006, 1105, 1204, 1303}, // 2078
        {0, 202, 303, 402, 501, 531, 629, 728, 827, 925, 1025, 1123, 1223}, // 2079
        {3, 122, 221, 321, 420, 519, 618, 717, 815, 914, 1013, 1111, 1211, 1310}, // 2080
        {0, 209, 310, 409, 509, 607, 707, 805, 903, 1003, 1101, 1130, 1230}, // 2081
        {7, 129, 227, 329, 428, 528, 626, 725, 824, 922, 1022, 1120, 1219, 1318}, // 2082
        {0, 217, 318, 417, 517, 615, 715, 813, 912, 1011, 1110, 1209, 1308}, // 2083
        {0, 206, 307, 405, 505, 603, 703, 802, 831, 930, 1029, 1128, 1227}, // 2084
        {5, 126, 224, 326, 424, 523, 622, 722, 820, 919, 1019, 1117, 1217, 1315}, // 2085
        {0, 214, 315, 414, 513, 611, 711, 809, 908, 1008, 1106, 1206, 1305}, // 2086
        {0, 203, 305, 403, 503, 601, 630, 730, 828, 927, 1026, 1125, 1225}, // 2087
        {4, 124, 222, 323, 421, 521, 619, 718, 817, 915, 1014, 1113, 1213, 1312}, // 2088
        {0, 210, 312, 411, 510, 609, 708, 806, 904, 1004, 1102, 1202, 1301}, // 2089
        {8, 130, 301, 331, 430, 529, 628, 727, 825, 924, 1023, 1121, 1221, 1320}, // 2090
        {0, 218, 320, 419, 518, 617, 716, 815, 913, 1013, 1111, 1210, 1309}, // 2091
        {0, 207, 308, 407, 506, 605, 705, 803, 902, 1001, 1031, 1129, 1229}, // 2092
        {6, 127, 225, 327, 426, 525, 624, 723, 822, 921, 1020, 1119, 1218, 1317}, // 2093
        {0, 215, 316, 415, 514, 613, 712, 811, 910, 1009, 1108, 1208, 1306}, // 2094
        {0, 205, 306, 405, 504, 602, 702, 731, 830, 928, 1028, 1127, 1227}, // 2095
        {4, 125, 224, 324, 423, 522, 620, 720, 818, 916, 1016, 1115, 1215, 1313}, // 2096
        {0, 212, 314, 412, 512, 610, 709, 808, 906, 1005, 1104, 1204, 1302}, // 2097
        {0, 201, 303, 402, 501, 531, 629, 728, 826, 925, 1024, 1123, 1222}, // 2098
        {2, 121, 220, 322, 420, 520, 619, 718, 816, 915, 1014, 1112, 1212, 1310}, // 2099
        {0, 209, 311, 410, 509, 608, 707, 806, 904, 1004, 1102, 1201, 1231}, //2100
        {7, 129, 228, 330, 428, 528, 626, 726, 825, 923, 1023, 1121, 1220, 1319}, //2101
        {0, 217, 319, 417, 517, 616, 715, 814, 912, 1012, 1111, 1210, 1309}, //2102
        {0, 207, 308, 407, 506, 605, 704, 803, 901, 1001, 1031, 1130, 1229}, //2103
        {5, 128, 226, 327, 425, 524, 623, 722, 821, 919, 1019, 1118, 1217, 1316}, //2104
        {0, 215, 316, 415, 514, 612, 712, 810, 908, 1008, 1107, 1206, 1305}, //2105
        {0, 204, 306, 404, 504, 602, 701, 731, 829, 927, 1027, 1125, 1225}, //2106
        {4, 124, 223, 325, 423, 523, 621, 720, 819, 917, 1016, 1115, 1214, 1313}, //2107
        {0, 212, 313, 411, 511, 609, 709, 807, 906, 1005, 1103, 1203, 1301}, //2108
        {9, 131, 302, 331, 430, 530, 628, 728, 826, 925, 1024, 1122, 1222, 1320}, //2109
        {0, 219, 320, 419, 519, 617, 717, 816, 914, 1014, 1112, 1211, 1310}, //2110
        {0, 208, 310, 408, 508, 606, 706, 805, 903, 1003, 1101, 1201, 1231}, //2111
        {6, 129, 227, 328, 426, 526, 624, 724, 822, 921, 1021, 1119, 1219, 1318}, //2112
        {0, 216, 318, 416, 515, 614, 713, 811, 910, 1010, 1108, 1208, 1307}, //2113
        {0, 206, 307, 406, 505, 603, 703, 801, 830, 929, 1028, 1127, 1227}, //2114
        {4, 126, 225, 326, 425, 524, 622, 722, 820, 918, 1018, 1116, 1216, 1315}, //2115
        {0, 214, 314, 413, 512, 611, 710, 809, 907, 1006, 1105, 1204, 1303}, //2116
        {0, 202, 303, 402, 502, 531, 630, 729, 828, 926, 1025, 1124, 1223}, //2117
        {3, 122, 220, 322, 421, 520, 619, 719, 817, 916, 1015, 1113, 1213, 1311}, //2118
        {0, 210, 311, 410, 509, 608, 708, 806, 905, 1004, 1103, 1202, 1301}, //2119
        {7, 130, 229, 329, 428, 527, 626, 725, 824, 923, 1022, 1121, 1220, 1319}, //2120
        {0, 217, 319, 417, 517, 615, 715, 813, 912, 1011, 1110, 1210, 1309}, //2121
        {0, 207, 308, 407, 506, 605, 704, 802, 901, 930, 1030, 1129, 1229}, //2122
        {5, 127, 226, 328, 426, 525, 624, 723, 821, 920, 1019, 1118, 1218, 1317}, //2123
        {0, 215, 316, 414, 514, 612, 712, 810, 908, 1008, 1106, 1206, 1305}, //2124
        {0, 203, 305, 404, 503, 602, 701, 731, 829, 927, 1027, 1125, 1225}, //2125
        {4, 123, 222, 324, 423, 522, 621, 720, 819, 917, 1016, 1115, 1214, 1313}, //2126
        {0, 211, 313, 412, 511, 610, 710, 808, 906, 1006, 1104, 1204, 1302}, //2127
        {11, 201, 301, 331, 429, 529, 628, 727, 826, 924, 1024, 1122, 1222, 1320}, //2128
        {0, 219, 320, 419, 518, 617, 716, 815, 914, 1013, 1112, 1211, 1310}, //2129
        {0, 208, 310, 408, 508, 606, 705, 804, 903, 1002, 1101, 1201, 1230}, //2130
        {6, 129, 227, 329, 427, 527, 625, 724, 823, 921, 1021, 1120, 1220, 1318}, //2131
        {0, 217, 317, 416, 515, 614, 713, 811, 910, 1009, 1108, 1208, 1306}, //2132
        {0, 205, 307, 405, 505, 603, 703, 801, 830, 928, 1028, 1127, 1226}, //2133
        {5, 125, 224, 326, 424, 524, 622, 722, 820, 918, 1018, 1116, 1216, 1314}, //2134
        {0, 213, 315, 413, 513, 612, 711, 809, 908, 1007, 1106, 1205, 1304}, //2135
        {0, 202, 303, 401, 501, 531, 629, 729, 827, 926, 1025, 1124, 1223}, //2136
        {2, 122, 220, 322, 420, 520, 618, 718, 817, 915, 1015, 1113, 1213, 1311}, //2137
        {0, 210, 311, 410, 509, 608, 707, 806, 904, 1004, 1103, 1202, 1301}, //2138
        {7, 130, 301, 330, 429, 528, 626, 726, 824, 923, 1023, 1122, 1221, 1320}, //2139
        {0, 218, 319, 417, 517, 615, 714, 813, 911, 1011, 1110, 1209, 1308}, //2140
        {0, 207, 308, 407, 506, 605, 704, 802, 901, 930, 1030, 1128, 1228}, //2141
        {5, 127, 226, 327, 426, 525, 624, 723, 821, 919, 1019, 1117, 1217, 1316}, //2142
        {0, 215, 316, 415, 515, 613, 713, 811, 909, 1008, 1107, 1206, 1305}, //2143
        {0, 204, 304, 403, 503, 602, 701, 730, 829, 927, 1027, 1125, 1224}, //2144
        {4, 123, 222, 323, 422, 522, 620, 720, 818, 917, 1016, 1115, 1214, 1313}, //2145
        {0, 211, 312, 411, 511, 609, 709, 807, 906, 1006, 1104, 1204, 1302}, //2146
        {0, 201, 302, 331, 430, 529, 628, 728, 826, 925, 1025, 1123, 1223}, //2147
        {1, 121, 220, 320, 419, 518, 616, 716, 814, 913, 1013, 1111, 1211, 1310}, //2148
        {0, 208, 310, 408, 508, 606, 705, 804, 902, 1002, 1031, 1130, 1230}, //2149
        {6, 129, 227, 329, 427, 527, 625, 724, 822, 921, 1020, 1119, 1219, 1318}, //2150
    };
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7241031233810655166L;
    // 农历年，和公历是一样的
    private int lyear;
    // 农历月，范围1-12
    private int lmonth;
    // 农历日期
    private int ldate;
    // 是否为闰月日期
    private boolean isLeapMonth = false;
    // 农历这年闰月，如果不闰月，默认为0
    private int leapMonth = 0;
    // 公历日期
    private GregorianCalendar solar = new GregorianCalendar();

    /**
     * 默认构造
     */
    public ChineseLunarCalendar() {
        computeBySolarDate(solar.get(Calendar.YEAR), solar.get(Calendar.MONTH), solar.get(Calendar.DATE));
    }

    /**
     * 通过农历年、月、日构造
     *
     * @param lyear       农历年
     * @param lmonth      农历月份,范围1-12
     * @param ldate       农历日
     * @param isleapMonth 是否闰月
     */
    public ChineseLunarCalendar(int lyear, int lmonth, int ldate, boolean isleapMonth) {
        computeByLunarDate(lyear, lmonth, ldate, isleapMonth);
    }

    /**
     * 通过公历构造
     *
     * @param calendar 　公历日期
     */
    public ChineseLunarCalendar(Calendar calendar) {
        computeBySolarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
    }

    /**
     * 计算两个农历日期之差
     *
     * @param lc1   　农历１
     * @param lc2   　农历２
     * @param field 　计算的维度，比如按月,天等
     * @return 具体的差值
     */
    public static long luanrDiff(ChineseLunarCalendar lc1, ChineseLunarCalendar lc2, int field) {
        return solarDiff(lc1.getSolar(), lc2.getSolar(), field);
    }

    /**
     * 公历转农历
     *
     * @param solar 　公历日期
     * @return 农历日期
     */
    public static ChineseLunarCalendar solar2Lunar(Calendar solar) {
        ChineseLunarCalendar ret = new ChineseLunarCalendar();
        ret.computeBySolarDate(solar.get(Calendar.YEAR), solar.get(Calendar.MONTH), solar.get(Calendar.DATE));
        return ret;
    }

    /**
     * 农历转公历
     *
     * @param lunarYear   　农历年
     * @param lunarMonth  　农历月，从１开始
     * @param LunarDate   　农历日
     * @param isLeapMonth 　是否润月
     * @return 公历日期
     */
    public static Calendar lunar2Solar(int lunarYear, int lunarMonth, int LunarDate, boolean isLeapMonth) {
        ChineseLunarCalendar ret = new ChineseLunarCalendar();
        ret.computeByLunarDate(lunarYear, lunarMonth, LunarDate, isLeapMonth);
        return ret.getSolar();
    }

    /**
     * 获取农历日的表示
     *
     * @param lunarDay 　农历日数值表示
     * @return 农历日传统字符表示
     */
    public static String getDayName(int lunarDay) {
        return LunarDayName[lunarDay - 1];
    }

    /**
     * 获取农历月份
     *
     * @param lunarMonth 　农历月数值表示
     * @return 农历月传统字符表示
     */
    public static char getMonthName(int lunarMonth) {
        return LunarMonthName[lunarMonth - 1];
    }

    /**
     * 获取农历年份
     *
     * @param lunarYear 　农历年数值表示
     * @return 农历年传统字符表示
     */
    public static String getYearName(int lunarYear) {
        StringBuilder sb = new StringBuilder();
        sb.append(LunarYearName[lunarYear / 1000]);
        sb.append(LunarYearName[lunarYear % 1000 / 100]);
        sb.append(LunarYearName[lunarYear % 100 / 10]);
        sb.append(LunarYearName[lunarYear % 10]);
        return sb.toString();
    }

    /**
     * 判断两个整数所代表公历日期的差值<br>
     * 一年按365天计算，一个月按30天计算<br>
     *
     * @param solarCode1 　农历日期代码
     * @param solarCode2 　农历日期代码
     * @param field      　差值单位
     * @return 差值
     */
    public static long solarDateCodesDiff(int solarCode1, int solarCode2, int field) {
        GregorianCalendar c1 = new GregorianCalendar(solarCode1 / 10000, solarCode1 % 10000 / 100 - 1,
            solarCode1 % 10000 % 100);
        GregorianCalendar c2 = new GregorianCalendar(solarCode2 / 10000, solarCode2 % 10000 / 100 - 1,
            solarCode2 % 10000 % 100);
        return solarDiff(c1, c2, field);
    }

    /**
     * 求两个公历日期之差，field可以为年月日，时分秒<br>
     * 一年按365天计算，一个月按30天计算<br>
     *
     * @param solar1 　历日期
     * @param solar2 　历日期
     * @param field  差值单位
     * @return 差值
     */
    public static long solarDiff(Calendar solar1, Calendar solar2, int field) {
        long t1 = solar1.getTimeInMillis();
        long t2 = solar2.getTimeInMillis();
        switch (field) {
            case Calendar.SECOND:
                return (long) Math.rint(Double.valueOf(t1 - t2) / Double.valueOf(1000));
            case Calendar.MINUTE:
                return (long) Math.rint(Double.valueOf(t1 - t2) / Double.valueOf(60 * 1000));
            case Calendar.HOUR:
                return (long) Math.rint(Double.valueOf(t1 - t2) / Double.valueOf(3600 * 1000));
            case Calendar.DATE:
                return (long) Math.rint(Double.valueOf(t1 - t2) / Double.valueOf(24 * 3600 * 1000));
            case Calendar.MONTH:
                return (long) Math.rint(Double.valueOf(t1 - t2) / Double.valueOf(30 * 24 * 3600 * 1000));
            case Calendar.YEAR:
                return (long) Math.rint(Double.valueOf(t1 - t2) / Double.valueOf(365 * 24 * 3600 * 1000));
            default:
                return -1;
        }
    }

    /**
     * 一个简单的二分查找，返回查找到的元素坐标，用于查找农历二维数组信息
     *
     * @param array 　数组
     * @param n     　待查询数字
     * @return 查到的坐标
     */
    private static int binSearch(int[] array, int n) {
        if (null == array || array.length == 0) {
            return -1;
        }
        int min = 0, max = array.length - 1;
        if (n <= array[min]) {
            return min;
        } else if (n >= array[max]) {
            return max;
        }
        while (max - min > 1) {
            int newIndex = (max + min) / 2; // 二分
            if (array[newIndex] > n) { // 取小区间
                max = newIndex;
            } else if (array[newIndex] < n) {// 取大区间
                min = newIndex;
            } else { // 相等，直接返回下标
                return newIndex;
            }
        }
        if (array[max] == n) {
            return max;
        } else if (array[min] == n) {
            return min;
        } else {
            return min; // 返回 较小的一个
        }
    }

    /**
     * 返回传统天干地支年名称
     *
     * @param y 农历年
     * @return 传统农历年份的表示
     */
    public static String getTraditionalYearName(int y) {
        y = y - MINI_YEAR + 36;
        return ("" + LunarGan[y % 10] + LunarZhi[y % 12] + "年");
    }

    /**
     * 获取生肖名
     *
     * @param y 　农历年
     * @return 生肖名
     */
    public static char getAnimalYearName(int y) {
        return LunarAnimailName[(y - 4) % 12];
    }

    /**
     * 创建LunarInfo中某一年的一列公历日历编码<br>
     * 公历日历编码：年份+月份+天，用于查询某个公历日期在某个LunarInfo列的哪一个区间<br>
     *
     * @param solarYear 年份
     * @return 公历日历编码
     */
    private int[] builderSolarCodes(int solarYear) {
        if (solarYear < MINI_YEAR && solarYear > MAX_YEAR) {
            throw new LunarException("Illegal solar year: " + solarYear);
        }
        int lunarIndex = solarYear - MINI_YEAR;
        int[] solarCodes = new int[LUAR_INFO[lunarIndex].length];
        for (int i = 0; i < solarCodes.length; i++) {
            if (0 == i) { // 第一个数表示闰月，不用更改
                solarCodes[i] = LUAR_INFO[lunarIndex][i];
            } else if (1 == i) {
                if (LUAR_INFO[lunarIndex][1] > 999) {
                    // 这年农历一月一日对应的公历实际是上一年的
                    solarCodes[i] = (solarYear - 1) * 10000 + LUAR_INFO[lunarIndex][i];
                } else {
                    solarCodes[i] = solarYear * 10000 + LUAR_INFO[lunarIndex][i];
                }
            } else {
                solarCodes[i] = solarYear * 10000 + LUAR_INFO[lunarIndex][i];
            }
        }
        return solarCodes;
    }

    /**
     * 日期增加,和<code>GregorianCalendar.add</code>类似
     *
     * @param field  　单位
     * @param amount 数值
     * @see GregorianCalendar
     */
    public void add(int field, int amount) {
        this.getSolar().add(field, amount);
        this.computeBySolarDate(this.getSolar().get(Calendar.YEAR), this.getSolar().get(Calendar.MONTH),
            this.getSolar().get(Calendar.DATE));
    }

    /**
     * 通过给定的农历日期，计算公历日期
     *
     * @param lunarYear   　农历年
     * @param lunarMonth  　农历月，从１开始
     * @param lunarDate   　农历日期
     * @param isleapMonth 　是否为闰月
     */
    private void computeByLunarDate(final int lunarYear, final int lunarMonth, final int lunarDate, final boolean isleapMonth) {
        if (lunarYear < MINI_YEAR && lunarYear > MAX_YEAR) {
            throw new LunarException("LunarYear must in (" + MINI_YEAR + "," + MAX_YEAR + ")");
        }
        this.lyear = lunarYear;
        this.lmonth = lunarMonth;
        this.ldate = lunarDate;
        int solarMontDate = LUAR_INFO[lunarYear - MINI_YEAR][lunarMonth];
        leapMonth = LUAR_INFO[lunarYear - MINI_YEAR][0];
        if (leapMonth != 0 && (lunarMonth > leapMonth || (lunarMonth == leapMonth && isleapMonth))) {
            // 闰月，且当前农历月大于闰月月份，取下一个月的LunarInfo码
            // 闰月，且当前农历月等于闰月月份，并且此农历月为闰月，取下一个月的LunarInfo码
            solarMontDate = LUAR_INFO[lunarYear - MINI_YEAR][lunarMonth + 1];
        }
        this.getSolar().set(Calendar.YEAR, lunarYear);
        this.getSolar().set(Calendar.MONTH, (solarMontDate / 100) - 1);
        this.getSolar().set(Calendar.DATE, solarMontDate % 100);
        this.add(Calendar.DATE, lunarDate - 1);
    }

    /**
     * 通过给定公历日期，计算农历日期
     *
     * @param solarYear  公历年
     * @param solarMonth 公历月，0-11
     * @param solarDate  公历日
     * @return void
     */
    private void computeBySolarDate(final int solarYear, final int solarMonth, final int solarDate) {
        if (solarYear < MINI_YEAR && solarYear > MAX_YEAR) {
            throw new LunarException("Illegal solar year: " + solarYear);
        }
        int solarCode = solarYear * 10000 + 100 * (1 + solarMonth) + solarDate; // 公历码
        leapMonth = LUAR_INFO[solarYear - MINI_YEAR][0];
        int[] solarCodes = builderSolarCodes(solarYear);
        int newMonth = binSearch(solarCodes, solarCode);
        if (-1 == newMonth) {
            throw new LunarException("No lunarInfo found by solarCode: " + solarCode);
        }
        int xdate = Long.valueOf(solarDateCodesDiff(solarCode, solarCodes[newMonth], Calendar.DATE)).intValue();
        if (0 == newMonth) {// 在上一年
            int preYear = solarYear - 1;
            int[] preSolarCodes = LUAR_INFO[preYear - MINI_YEAR];
            // 取上年农历12月1号公历日期码
            int nearSolarCode = preSolarCodes[preSolarCodes.length - 1]; // 上一年12月1号
            // 下一年公历1月表示为了13月，这里做翻译，并计算出日期码
            nearSolarCode = (nearSolarCode / 100 == 13 ? preYear + 1 : preYear) * 10000
                + (nearSolarCode / 100 == 13 ? nearSolarCode - 1200 : nearSolarCode);
            if (nearSolarCode > solarCode) {// 此公历日期在上一年农历12月1号，之前，即在上年农历11月内
                newMonth = 11;
                // 取农历11月的公历码
                nearSolarCode = preYear * 10000 + preSolarCodes[preSolarCodes.length - 2];
            } else {// 此公历日期在上一年农历12月内
                newMonth = 12;
            }
            xdate = Long.valueOf(solarDateCodesDiff(solarCode, nearSolarCode, Calendar.DATE)).intValue();
            if (xdate < 0) {
                throw new LunarException("Wrong solarCode: " + solarCode);
            }
            this.ldate = 1 + xdate;
            this.lyear = preYear;
            this.lmonth = newMonth;
            this.isLeapMonth = false; // 农历12月不可能为闰月
        } else if (solarCodes.length == newMonth + 1 && xdate >= 30) {// 在下一年(公历12月只有30天)
            newMonth = 1; // 农历肯定是1月
            // 取下一年的公历日期码
            int[] nextSolarCodes = LUAR_INFO[solarYear + 1 - MINI_YEAR];
            // 取下一年农历1月1号公历日期码
            int nearSolarCode = solarYear * 10000 + nextSolarCodes[1]; // 下一年农历1月1号公历日期码
            xdate = Long.valueOf(solarDateCodesDiff(solarCode, nearSolarCode, Calendar.DATE)).intValue();
            if (xdate < 0) {
                throw new LunarException("Wrong solarCode: " + solarCode);
            }
            this.ldate = 1 + xdate;
            this.lyear = solarYear + 1; // 农历年到了下一年
            this.lmonth = newMonth;
            this.isLeapMonth = false; // 农历1月不可能为闰月
        } else {
            if (xdate < 0) {
                throw new LunarException("Wrong solarCode: " + solarCode);
            }
            this.ldate = 1 + xdate;
            this.lyear = solarYear;
            this.isLeapMonth = 0 != leapMonth && (leapMonth + 1 == newMonth);
            if (0 != leapMonth && leapMonth < newMonth) {
                this.lmonth = newMonth - 1;
            } else {
                this.lmonth = newMonth;
            }
        }
        this.getSolar().set(Calendar.YEAR, solarYear);
        this.getSolar().set(Calendar.MONTH, solarMonth);
        this.getSolar().set(Calendar.DATE, solarDate);
    }

    /**
     * 增加公历日期
     *
     * @param field 　单位
     * @param n     数值
     * @see GregorianCalendar
     */
    public void solarAdd(int field, int n) {
        getSolar().add(field, n);
        computeBySolarDate(getSolar().get(Calendar.YEAR), getSolar().get(Calendar.MONTH), getSolar().get(Calendar.DATE));
    }

    /**
     * 返回农历日期，不包含年份
     *
     * @param showLeap 　是否显示闰月的闰字
     * @return 农历日期
     */
    public String getLunar(boolean showLeap) {
        if (this.lmonth < 1 || this.lmonth > 12 || this.ldate < 1
            || this.ldate > 30) {
            throw new LunarException("Wrong lunar ldate: " + lmonth + " " + ldate);
        }
        if (showLeap) {
            return (this.isLeapMonth() ? "闰" : "") + getMonthName(this.lmonth) + "月"
                + getDayName(this.ldate);
        } else {
            return getMonthName(this.lmonth) + "月" + getDayName(this.ldate);
        }
    }

    // getter and setter
    public int getLyear() {
        return lyear;
    }

    public void setLyear(int lyear) {
        this.lyear = lyear;
    }

    public int getLmonth() {
        return lmonth;
    }

    public void setLmonth(int lmonth) {
        this.lmonth = lmonth;
    }

    public int getLdate() {
        return ldate;
    }

    public void setLdate(int ldate) {
        this.ldate = ldate;
    }

    public int getLeapMonth() {
        return leapMonth;
    }

    public GregorianCalendar getSolar() {
        return solar;
    }

    public void setSolar(GregorianCalendar solar) {
        this.solar = solar;
    }

    public boolean isLeapMonth() {
        return isLeapMonth;
    }

    public void setLeapMonth(int leapMonth) {
        this.leapMonth = leapMonth;
    }

    public void setLeapMonth(boolean isLeapMonth) {
        this.isLeapMonth = isLeapMonth;
    }

    @Override
    public String toString() {
        if (this.lyear < MINI_YEAR || this.lyear > MAX_YEAR || this.lmonth < 1 || this.lmonth > 12 || this.ldate < 1
            || this.ldate > 30) {
            return "Wrong lunar date: " + lyear + " " + lmonth + " " + ldate;
        }
        return getYearName(this.lyear) + "年" + (this.isLeapMonth() ? "闰" : "") + getMonthName(this.lmonth) + "月"
            + getDayName(this.ldate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChineseLunarCalendar that = (ChineseLunarCalendar) o;

        if (lyear != that.lyear) return false;
        if (lmonth != that.lmonth) return false;
        if (ldate != that.ldate) return false;
        return isLeapMonth == that.isLeapMonth;
    }

    @Override
    public int hashCode() {
        int result = lyear;
        result = 31 * result + lmonth;
        result = 31 * result + ldate;
        result = 31 * result + (isLeapMonth ? 1 : 0);
        return result;
    }

    /**
     * 返回中国农历的全名
     *
     * @return String
     */
    public String getFullLunarName() {
        return this.toString() + " " + getTraditionalYearName(this.lyear) + " " + getAnimalYearName(this.lyear);
    }

    /**
     * 农历日期异常
     *
     * @author joel
     */
    public static class LunarException extends RuntimeException {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 3274596943314243191L;

        private String message;

        // constructor
        public LunarException(String message) {
            super(message);
            this.message = message;
        }

        public LunarException() {
            super();
        }

        public LunarException(String message, Throwable t) {
            super(message, t);
            this.message = message;
        }

        public LunarException(Throwable t) {
            super(t);
        }

        // getter and setter
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
