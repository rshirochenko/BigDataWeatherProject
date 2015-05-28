
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ishJava {
    static String sProgramName = "ishJava.java";
    static String sDebugName = "ishJava";
    static String sInFileName = "";
    static String sOutFileName = "";
    static FileOutputStream fDebug = null;
    static boolean bVerbose = false;
    static boolean bOnce = false;
    static boolean bStdErr = false;
    static String sOutputRecord = "";
    static String sControlSection = "";
    static String sMandatorySection = "";
    static int iCounter = 0;
    static int iLength = 0;
    static int iOffset = 25;
    static int iObsKey = 0;
    static int iWork = 0;
    static String[] sWW1234;
    static String[] sAW1234;
    static float fWork = 0.0F;
    static float fWorkSave = 0.0F;
    static String sConcat = "";
    static String sConcatDate = "";
    static String sConcatMonth = "";
    static String sMessage = "";
    static final int iPROD = 0;
    static final int iDEBUG = 1;
    static int iLogLevel = 0;
    static String p_sFilter1 = "None";
    static String p_sFilter2 = "None";
    static NumberFormat fmt03 = new DecimalFormat("000");
    static NumberFormat fmt4_1 = new DecimalFormat("#0.0");
    static NumberFormat fmt6_1 = new DecimalFormat("###0.0");
    static NumberFormat fmt5_2 = new DecimalFormat("##0.00");
    static NumberFormat fmt02 = new DecimalFormat("#0");
    static String sCDS = "";
    static String sCDS_Fill1 = "";
    static String sCDS_ID = "";
    static String sCDS_Wban = "";
    static String sCDS_Year = "";
    static String sCDS_Month = "";
    static String sCDS_Day = "";
    static String sCDS_Hour = "";
    static String sCDS_Minute = "";
    static String sCDS_Fill2 = "";
    static String sMDS = "";
    static String sMDS_Dir = "";
    static String sMDS_DirQ = "";
    static String sMDS_DirType = "";
    static String sMDS_Spd = "";
    static String sMDS_Fill2 = "";
    static String sMDS_Clg = "";
    static String sMDS_Fill3 = "";
    static String sMDS_Vsb = "";
    static String sMDS_Fill4 = "";
    static String sMDS_TempSign = "";
    static String sMDS_Temp = "";
    static String sMDS_Fill5 = "";
    static String sMDS_DewpSign = "";
    static String sMDS_Dewp = "";
    static String sMDS_Fill6 = "";
    static String sMDS_Slp = "";
    static String sMDS_Fill7 = "";
    static int iREM_IndexOf = 0;
    static int iOC1_IndexOf = 0;
    static int iOC1_Length = 8;
    static String sOC1 = "";
    static String sOC1_Fill1 = "";
    static String sOC1_Gus = "";
    static String sOC1_Fill2 = "";
    static int iGF1_IndexOf = 0;
    static int iGF1_Length = 26;
    static String sGF1 = "";
    static String sGF1_Fill1 = "";
    static String sGF1_Skc = "";
    static String sGF1_Fill2 = "";
    static String sGF1_Low = "";
    static String sGF1_Fill3 = "";
    static String sGF1_Med = "";
    static String sGF1_Fill4 = "";
    static String sGF1_Hi = "";
    static String sGF1_Fill5 = "";
    static int iMW1_IndexOf = 0;
    static int iMW1_Length = 6;
    static String sMW1 = "";
    static String sMW1_Fill1 = "";
    static String sMW1_Ww = "";
    static String sMW1_Fill2 = "";
    static int iMW2_IndexOf = 0;
    static int iMW2_Length = 6;
    static String sMW2 = "";
    static String sMW2_Fill1 = "";
    static String sMW2_Ww = "";
    static String sMW2_Fill2 = "";
    static int iMW3_IndexOf = 0;
    static int iMW3_Length = 6;
    static String sMW3 = "";
    static String sMW3_Fill1 = "";
    static String sMW3_Ww = "";
    static String sMW3_Fill2 = "";
    static int iMW4_IndexOf = 0;
    static int iMW4_Length = 6;
    static String sMW4 = "";
    static String sMW4_Fill1 = "";
    static String sMW4_Ww = "";
    static String sMW4_Fill2 = "";
    static int iAY1_IndexOf = 0;
    static int iAY1_Length = 8;
    static String sAY1 = "";
    static String sAY1_Fill1 = "";
    static String sAY1_Pw = "";
    static String sAY1_Fill2 = "";
    static int iMA1_IndexOf = 0;
    static int iMA1_Length = 15;
    static String sMA1 = "";
    static String sMA1_Fill1 = "";
    static String sMA1_Alt = "";
    static String sMA1_Fill2 = "";
    static String sMA1_Stp = "";
    static String sMA1_Fill3 = "";
    static String sMaxTemp = "";
    static String sMinTemp = "";
    static int iKA1_IndexOf = 0;
    static int iKA1_Length = 13;
    static String sKA1 = "";
    static String sKA1_Fill1 = "";
    static String sKA1_Code = "";
    static String sKA1_Temp = "";
    static String sKA1_Fill2 = "";
    static int iKA2_IndexOf = 0;
    static int iKA2_Length = 13;
    static String sKA2 = "";
    static String sKA2_Fill1 = "";
    static String sKA2_Code = "";
    static String sKA2_Temp = "";
    static String sKA2_Fill2 = "";
    static String sPcp01 = "*****";
    static String sPcp01t = " ";
    static String sPcp06 = "*****";
    static String sPcp06t = " ";
    static String sPcp24 = "0.0";
    static String sPcp24t = " ";
    static String sPcp12 = "*****";
    static String sPcp12t = " ";
    static int iAA1_IndexOf = 0;
    static int iAA1_Length = 11;
    static String sAA1 = "";
    static String sAA1_Fill1 = "";
    static String sAA1_Hours = "";
    static String sAA1_Pcp = "";
    static String sAA1_Trace = "";
    static String sAA1_Fill2 = "";
    static int iAA2_IndexOf = 0;
    static int iAA2_Length = 11;
    static String sAA2 = "";
    static String sAA2_Fill1 = "";
    static String sAA2_Hours = "";
    static String sAA2_Pcp = "";
    static String sAA2_Trace = "";
    static String sAA2_Fill2 = "";
    static int iAA3_IndexOf = 0;
    static int iAA3_Length = 11;
    static String sAA3 = "";
    static String sAA3_Fill1 = "";
    static String sAA3_Hours = "";
    static String sAA3_Pcp = "";
    static String sAA3_Trace = "";
    static String sAA3_Fill2 = "";
    static int iAA4_IndexOf = 0;
    static int iAA4_Length = 11;
    static String sAA4 = "";
    static String sAA4_Fill1 = "";
    static String sAA4_Hours = "";
    static String sAA4_Pcp = "";
    static String sAA4_Trace = "";
    static String sAA4_Fill2 = "";
    static int iAJ1_IndexOf = 0;
    static int iAJ1_Length = 17;
    static String sAJ1 = "";
    static String sAJ1_Fill1 = "";
    static String sAJ1_Sd = "";
    static String sAJ1_Fill2 = "";
    static int iAW1_IndexOf = 0;
    static int iAW1_Length = 6;
    static String sAW1 = "";
    static String sAW1_Fill1 = "";
    static String sAW1_Zz = "";
    static String sAW1_Fill2 = "";
    static int iAW2_IndexOf = 0;
    static int iAW2_Length = 6;
    static String sAW2 = "";
    static String sAW2_Fill1 = "";
    static String sAW2_Zz = "";
    static String sAW2_Fill2 = "";
    static int iAW3_IndexOf = 0;
    static int iAW3_Length = 6;
    static String sAW3 = "";
    static String sAW3_Fill1 = "";
    static String sAW3_Zz = "";
    static String sAW3_Fill2 = "";
    static int iAW4_IndexOf = 0;
    static int iAW4_Length = 6;
    static String sAW4 = "";
    static String sAW4_Fill1 = "";
    static String sAW4_Zz = "";
    static String sAW4_Fill2 = "";
    static String sHeader = "";
    //static String sHeader = "  USAF  WBAN YR--MODAHRMN DIR SPD GUS CLG SKC L M H  VSB MW MW MW MW AW AW AW AW W TEMP DEWP    SLP   ALT    STP MAX MIN PCP01 PCP06 PCP24 PCPXX SD\n";

    public ishJava() {
    }

    public static void main(String[] var0) {
        if(var0.length <= 1) {
            bStdErr = true;
            logIt(fDebug, 0, false, "Error. Input and Output filenames required.");
            System.exit(77);
        }

        if(var0.length >= 2) {
            sInFileName = var0[0];
            sOutFileName = var0[1];
        }

        if(var0.length >= 3) {
            if(!var0[2].equals("0") && !var0[2].equals("1")) {
                logIt(fDebug, 0, false, "Invalid log message level parameter=[" + var0[2] + "].  Must be 0 or 1.  Defaulting to [" + iLogLevel + "]");
            } else {
                iLogLevel = Integer.parseInt(var0[2]);
            }
        }

        if(var0.length >= 4) {
            p_sFilter1 = var0[3];
        }

        if(var0.length >= 5) {
            p_sFilter2 = var0[4];
        }

        logIt(fDebug, 1, false, "        Input Filename=[" + sInFileName + "]");
        logIt(fDebug, 1, false, "       Output Filename=[" + sOutFileName + "]");
        logIt(fDebug, 1, false, "         Logging Level=[" + iLogLevel + "]");
        logIt(fDebug, 1, false, "1st Log Message Filter=[" + p_sFilter1 + "]");
        logIt(fDebug, 1, false, "2nd Log Message Filter=[" + p_sFilter2 + "]");

        try {
            BufferedReader var1 = new BufferedReader(new FileReader(sInFileName));
            FileWriter var2 = new FileWriter(sOutFileName);
            BufferedWriter var3 = new BufferedWriter(var2);
            var3.write(sHeader);

            try {
                String var4 = null;

                while((var4 = var1.readLine()) != null) {
                    ++iCounter;
                    iLength = var4.length();
                    iREM_IndexOf = var4.indexOf("REM");
                    if(iREM_IndexOf == -1) {
                        iREM_IndexOf = 9999;
                    }

                    getCDS(var4);
                    sConcat = sCDS_ID + "-" + sCDS_Wban + "-" + sCDS_Year + "-" + sCDS_Month + "-" + sCDS_Day + " " + sCDS_Hour + ":" + sCDS_Minute;
                    sConcatDate = sCDS_Year + "-" + sCDS_Month + "-" + sCDS_Day;
                    sConcatMonth = sCDS_Year + "-" + sCDS_Month;
                    getMDS(var4);
                    getOC1(var4);
                    getGF1(var4);
                    getMW1(var4);
                    getMW2(var4);
                    getMW3(var4);
                    getMW4(var4);
                    getAY1(var4);
                    getMA1(var4);
                    sMaxTemp = "***";
                    sMinTemp = "***";
                    getKA1(var4);
                    getKA2(var4);
                    sPcp01 = "*****";
                    sPcp01t = " ";
                    sPcp06 = "*****";
                    sPcp06t = " ";
                    sPcp24 = "0.0";
                    sPcp24t = " ";
                    sPcp12 = "*****";
                    sPcp12t = " ";
                    getAA1(var4);
                    getAA2(var4);
                    getAA3(var4);
                    getAA4(var4);
                    getAJ1(var4);
                    getAW1(var4);
                    getAW2(var4);
                    getAW3(var4);
                    getAW4(var4);
                    if(sCDS_Wban.equals("99999")) {
                        sCDS_Wban = "*****";
                    }

                    sControlSection = sCDS_ID + " " + sCDS_Year + sCDS_Month + sCDS_Day + sCDS_Hour + sCDS_Minute;
                    sWW1234 = new String[]{sMW1_Ww, sMW2_Ww, sMW3_Ww, sMW4_Ww};
                    Arrays.sort(sWW1234);
                    sAW1234 = new String[]{sAW1_Zz, sAW2_Zz, sAW3_Zz, sAW4_Zz};
                    Arrays.sort(sAW1234);
                    // sMandatorySection = sMDS_Dir + " " + sMDS_Spd + " " + sOC1_Gus + " " + sMDS_Clg + " " + sGF1_Skc + " " + sGF1_Low + " " + sGF1_Med + " " + sGF1_Hi + " " + sMDS_Vsb + " " + sWW1234[3] + " " + sWW1234[2] + " " + sWW1234[1] + " " + sWW1234[0] + " " + sAW1234[3] + " " + sAW1234[2] + " " + sAW1234[1] + " " + sAW1234[0] + " " + sAY1_Pw + " " + sMDS_Temp + " " + sMDS_Dewp + " " + sMDS_Slp + " " + sMA1_Alt + " " + sMA1_Stp + " " + sMaxTemp + " " + sMinTemp + " " + sPcp01 + sPcp01t + sPcp06 + sPcp06t + sPcp24 + sPcp24t + sPcp12 + sPcp12t + sAJ1_Sd;
                    sMandatorySection =  sMDS_Slp+ " "+sPcp24;
                    sOutputRecord = sControlSection + " " + sMandatorySection;
                    var3.write(sOutputRecord + "\n");
                }
            } catch (IOException var5) {
                System.err.println(sProgramName + ": IOException 2. Error=[" + var5.getMessage() + "]");
                System.err.println(sProgramName + ": Stack trace follows:");
                var5.printStackTrace();
                System.exit(2);
            }

            var1.close();
            var3.flush();
            var3.close();
        } catch (Exception var6) {
            sMessage = sProgramName + ": Unspecified Exception 1. Error=[" + var6.getMessage() + "]";
            bStdErr = true;
            logIt(fDebug, 0, false, sMessage);
            System.err.println(sProgramName + ": Stack trace follows:");
            var6.printStackTrace();
            System.exit(1);
        }

        logIt(fDebug, 1, false, "Processed " + iCounter + " records");
        logIt(fDebug, 1, false, "Done.");
    }

    public static String formatInt(int var0, int var1) {
        String var3 = Integer.toString(var0);
        if(var3.length() < var1) {
            var3 = "                 ".substring(0, var1 - var3.length()) + var3;
        }

        return var3;
    }

    public static String formatFloat(float var0, int var1) {
        String var3 = Float.toString(var0);
        if(var3.length() < var1) {
            var3 = "                 ".substring(0, var1 - var3.length()) + var3;
        }

        return var3;
    }

    public static void getCDS(String var0) {
        sCDS = var0.substring(0, 60);
        sCDS_Fill1 = var0.substring(0, 4);
        sCDS_ID = var0.substring(4, 10);
        sCDS_Wban = var0.substring(10, 15);
        sCDS_Year = var0.substring(15, 19);
        sCDS_Month = var0.substring(19, 21);
        sCDS_Day = var0.substring(21, 23);
        sCDS_Hour = var0.substring(23, 25);
        sCDS_Minute = var0.substring(25, 27);
        sCDS_Fill2 = var0.substring(27, 60);
    }

    public static void getMDS(String var0) {
        sMDS = var0.substring(60, 105);
        sMDS_Dir = var0.substring(60, 63);
        sMDS_DirQ = var0.substring(63, 64);
        sMDS_DirType = var0.substring(64, 65);
        sMDS_Spd = var0.substring(65, 69);
        sMDS_Fill2 = var0.substring(69, 70);
        sMDS_Clg = var0.substring(70, 75);
        sMDS_Fill3 = var0.substring(75, 78);
        sMDS_Vsb = var0.substring(78, 84);
        sMDS_Fill4 = var0.substring(84, 87);
        sMDS_TempSign = var0.substring(87, 88);
        sMDS_Temp = var0.substring(88, 92);
        sMDS_Fill5 = var0.substring(92, 93);
        sMDS_DewpSign = var0.substring(93, 94);
        sMDS_Dewp = var0.substring(94, 98);
        sMDS_Fill6 = var0.substring(98, 99);
        sMDS_Slp = var0.substring(99, 104);
        sMDS_Fill7 = var0.substring(104, 105);
        if(sMDS_Dir.equals("999")) {
            sMDS_Dir = "***";
        }

        if(sMDS_DirType.equals("V")) {
            sMDS_Dir = "990";
        }

        if(sMDS_Spd.equals("9999")) {
            sMDS_Spd = "***";
        } else {
            iWork = Integer.parseInt(sMDS_Spd);
            iWork = (int)((double)((float)iWork) / 10.0D * 2.237D + 0.5D);
            sMDS_Spd = formatInt(iWork, 3);
        }

        if(sMDS_Clg.equals("99999")) {
            sMDS_Clg = "***";
        } else {
            try {
                iWork = Integer.parseInt(sMDS_Clg);
            } catch (Exception var2) {
                logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sMDS_Clg value could not be converted to integer=[" + sMDS_Clg + "]");
                sMDS_Clg = "***";
            }

            if(!sMDS_Clg.equals("***")) {
                iWork = (int)((double)((float)iWork) * 3.281D / 100.0D + 0.5D);
                sMDS_Clg = formatInt(iWork, 3);
            }
        }

        if(sMDS_Vsb.equals("999999")) {
            sMDS_Vsb = "****";
        } else {
            fWork = Float.parseFloat(sMDS_Vsb);
            fWork *= 6.25E-4F;
            fWorkSave = fWork;
            if((double)fWork > 99.9D) {
                fWork = 99.0F;
            }

            if(fWork == 10.058125F) {
                logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sMDS_Vsb value rounded to 10 miles");
                fWork = 10.0F;
            }

            sMDS_Vsb = fmt4_1.format((double)fWork);
            sMDS_Vsb = String.format("%4s", new Object[]{sMDS_Vsb});
        }

        if(sMDS_Temp.equals("9999")) {
            sMDS_Temp = "****";
        } else {
            iWork = Integer.parseInt(sMDS_Temp);
            if(sMDS_TempSign.equals("-")) {
                iWork *= -1;
            }

            if(iWork < -178) {
                iWork = (int)((double)((float)iWork) / 10.0D * 1.8D + 32.0D - 0.5D);
            } else {
                iWork = (int)((double)((float)iWork) / 10.0D * 1.8D + 32.0D + 0.5D);
            }

            sMDS_Temp = formatInt(iWork, 4);
        }

        if(sMDS_Dewp.equals("9999")) {
            sMDS_Dewp = "****";
        } else {
            iWork = Integer.parseInt(sMDS_Dewp);
            if(sMDS_DewpSign.equals("-")) {
                iWork *= -1;
            }

            if(iWork < -178) {
                iWork = (int)((double)((float)iWork) / 10.0D * 1.8D + 32.0D - 0.5D);
            } else {
                iWork = (int)((double)((float)iWork) / 10.0D * 1.8D + 32.0D + 0.5D);
            }

            sMDS_Dewp = formatInt(iWork, 4);
        }

        if(sMDS_Slp.equals("99999")) {
            sMDS_Slp = "0.0";
        } else {
            fWork = Float.parseFloat(sMDS_Slp);
            fWork = (float)((double)fWork / 10.0D);
            sMDS_Slp = fmt6_1.format((double)fWork);
            sMDS_Slp = String.format("%6s", new Object[]{sMDS_Slp});
        }

    }

    public static void getOC1(String var0) {
        sOC1 = "";
        sOC1_Fill1 = "";
        sOC1_Gus = "***";
        sOC1_Fill2 = "";
        iOC1_IndexOf = var0.indexOf("OC1");
        if(iOC1_IndexOf >= 0 && iOC1_IndexOf < iREM_IndexOf) {
            sOC1 = var0.substring(iOC1_IndexOf, iOC1_IndexOf + iOC1_Length);
            sOC1_Fill1 = sOC1.substring(1, 3);
            sOC1_Gus = sOC1.substring(3, 7);
            sOC1_Fill2 = sOC1.substring(7, 8);
            if(sOC1_Gus.equals("9999")) {
                sOC1_Gus = "***";
            } else {
                try {
                    iWork = Integer.parseInt(sOC1_Gus);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sOC1_Gus value could not be converted to integer=[" + sOC1_Gus + "]");
                    sOC1_Gus = "***";
                }

                if(!sOC1_Gus.equals("***")) {
                    iWork = (int)((double)((float)iWork) / 10.0D * 2.237D + 0.5D);
                    sOC1_Gus = formatInt(iWork, 3);
                }
            }
        }

    }

    public static void getGF1(String var0) {
        sGF1 = "";
        sGF1_Fill1 = "";
        sGF1_Skc = "***";
        sGF1_Fill2 = "";
        sGF1_Low = "*";
        sGF1_Fill3 = "";
        sGF1_Med = "*";
        sGF1_Fill4 = "";
        sGF1_Hi = "*";
        sGF1_Fill5 = "";
        iGF1_IndexOf = var0.indexOf("GF1");
        if(iGF1_IndexOf >= 0 && iGF1_IndexOf < iREM_IndexOf) {
            sGF1 = var0.substring(iGF1_IndexOf, iGF1_IndexOf + iGF1_Length);
            sGF1_Fill1 = sGF1.substring(1, 3);
            sGF1_Skc = sGF1.substring(3, 5);
            sGF1_Fill2 = sGF1.substring(5, 11);
            sGF1_Low = sGF1.substring(11, 13);
            sGF1_Fill3 = sGF1.substring(13, 20);
            sGF1_Med = sGF1.substring(20, 22);
            sGF1_Fill4 = sGF1.substring(22, 23);
            sGF1_Hi = sGF1.substring(23, 25);
            sGF1_Fill5 = sGF1.substring(25, 26);
        }

        if(iGF1_IndexOf >= 0 && iGF1_IndexOf < iREM_IndexOf) {
            if(sGF1_Skc.equals("99")) {
                sGF1_Skc = "***";
            } else {
                try {
                    iWork = Integer.parseInt(sGF1_Skc);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sGF1_Skc value could not be converted to integer=[" + sGF1_Skc + "]");
                    sGF1_Skc = "***";
                }

                if(!sGF1_Skc.equals("***")) {
                    if(iWork == 0) {
                        sGF1_Skc = "CLR";
                    } else if(iWork >= 1 && iWork <= 4) {
                        sGF1_Skc = "SCT";
                    } else if(iWork >= 5 && iWork <= 7) {
                        sGF1_Skc = "BKN";
                    } else if(iWork == 8) {
                        sGF1_Skc = "OVC";
                    } else if(iWork == 9) {
                        sGF1_Skc = "OBS";
                    } else if(iWork == 10) {
                        sGF1_Skc = "POB";
                    }
                }
            }

            if(sGF1_Low.equals("99")) {
                sGF1_Low = "*";
            } else {
                sGF1_Low = sGF1_Low.substring(1, 2);
            }

            if(sGF1_Med.equals("99")) {
                sGF1_Med = "*";
            } else {
                sGF1_Med = sGF1_Med.substring(1, 2);
            }

            if(sGF1_Hi.equals("99")) {
                sGF1_Hi = "*";
            } else {
                sGF1_Hi = sGF1_Hi.substring(1, 2);
            }
        }

    }

    public static void getMW1(String var0) {
        sMW1 = "";
        sMW1_Fill1 = "";
        sMW1_Ww = "**";
        sMW1_Fill2 = "";
        iMW1_IndexOf = var0.indexOf("MW1");
        if(iMW1_IndexOf >= 0 && iMW1_IndexOf < iREM_IndexOf) {
            sMW1 = var0.substring(iMW1_IndexOf, iMW1_IndexOf + iMW1_Length);
            sMW1_Fill1 = sMW1.substring(1, 3);
            sMW1_Ww = sMW1.substring(3, 5);
            sMW1_Fill2 = sMW1.substring(5, 6);
        }

    }

    public static void getMW2(String var0) {
        sMW2 = "";
        sMW2_Fill1 = "";
        sMW2_Ww = "**";
        sMW2_Fill2 = "";
        iMW2_IndexOf = var0.indexOf("MW2");
        if(iMW2_IndexOf >= 0 && iMW2_IndexOf < iREM_IndexOf) {
            sMW2 = var0.substring(iMW2_IndexOf, iMW2_IndexOf + iMW2_Length);
            sMW2_Fill1 = sMW2.substring(1, 3);
            sMW2_Ww = sMW2.substring(3, 5);
            sMW2_Fill2 = sMW2.substring(5, 6);
        }

    }

    public static void getMW3(String var0) {
        sMW3 = "";
        sMW3_Fill1 = "";
        sMW3_Ww = "**";
        sMW3_Fill2 = "";
        iMW3_IndexOf = var0.indexOf("MW3");
        if(iMW3_IndexOf >= 0 && iMW3_IndexOf < iREM_IndexOf) {
            sMW3 = var0.substring(iMW3_IndexOf, iMW3_IndexOf + iMW3_Length);
            sMW3_Fill1 = sMW3.substring(1, 3);
            sMW3_Ww = sMW3.substring(3, 5);
            sMW3_Fill2 = sMW3.substring(5, 6);
        }

    }

    public static void getMW4(String var0) {
        sMW4 = "";
        sMW4_Fill1 = "";
        sMW4_Ww = "**";
        sMW4_Fill2 = "";
        iMW4_IndexOf = var0.indexOf("MW4");
        if(iMW4_IndexOf >= 0 && iMW4_IndexOf < iREM_IndexOf) {
            sMW4 = var0.substring(iMW4_IndexOf, iMW4_IndexOf + iMW4_Length);
            sMW4_Fill1 = sMW4.substring(1, 3);
            sMW4_Ww = sMW4.substring(3, 5);
            sMW4_Fill2 = sMW4.substring(5, 6);
        }

    }

    public static void getAY1(String var0) {
        sAY1 = "";
        sAY1_Fill1 = "";
        sAY1_Pw = "*";
        sAY1_Fill2 = "";
        iAY1_IndexOf = var0.indexOf("AY1");
        if(iAY1_IndexOf >= 0 && iAY1_IndexOf < iREM_IndexOf) {
            sAY1 = var0.substring(iAY1_IndexOf, iAY1_IndexOf + iAY1_Length);
            sAY1_Fill1 = sAY1.substring(1, 3);
            sAY1_Pw = sAY1.substring(3, 4);
            sAY1_Fill2 = sAY1.substring(4, 8);
        }

    }

    public static void getMA1(String var0) {
        sMA1 = "";
        sMA1_Fill1 = "";
        sMA1_Alt = "*****";
        sMA1_Fill2 = "";
        sMA1_Stp = "******";
        sMA1_Fill3 = "";
        iMA1_IndexOf = var0.indexOf("MA1");
        if(iMA1_IndexOf >= 0 && iMA1_IndexOf < iREM_IndexOf) {
            sMA1 = var0.substring(iMA1_IndexOf, iMA1_IndexOf + iMA1_Length);
            sMA1_Fill1 = sMA1.substring(1, 3);
            sMA1_Alt = sMA1.substring(3, 8);
            sMA1_Fill2 = sMA1.substring(8, 9);
            sMA1_Stp = sMA1.substring(9, 14);
            sMA1_Fill3 = sMA1.substring(14, 15);
            if(sMA1_Alt.equals("99999")) {
                sMA1_Alt = "*****";
            } else {
                try {
                    fWork = Float.parseFloat(sMA1_Alt);
                } catch (Exception var3) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sMA1_Alt value could not be converted to floating point=[" + sMA1_Alt + "]");
                    sMA1_Alt = "*****";
                }

                if(!sMA1_Alt.equals("*****")) {
                    fWork = (float)((double)fWork / 10.0D * 100.0D) / 3386.39F;
                    sMA1_Alt = fmt5_2.format((double)fWork);
                    sMA1_Alt = String.format("%5s", new Object[]{sMA1_Alt});
                }
            }

            if(sMA1_Stp.equals("99999")) {
                sMA1_Stp = "******";
            } else {
                try {
                    fWork = Float.parseFloat(sMA1_Stp);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sMA1_Stp value could not be converted to floating point=[" + sMA1_Stp + "]");
                    sMA1_Stp = "******";
                }

                if(!sMA1_Stp.equals("******")) {
                    fWork = (float)((double)fWork / 10.0D);
                    sMA1_Stp = fmt6_1.format((double)fWork);
                    sMA1_Stp = String.format("%6s", new Object[]{sMA1_Stp});
                }
            }
        }

    }

    public static void getKA1(String var0) {
        sKA1 = "";
        sKA1_Fill1 = "";
        sKA1_Code = "*";
        sKA1_Temp = "***";
        sKA1_Fill2 = "";
        iKA1_IndexOf = var0.indexOf("KA1");
        if(iKA1_IndexOf >= 0 && iKA1_IndexOf < iREM_IndexOf) {
            sKA1 = var0.substring(iKA1_IndexOf, iKA1_IndexOf + iKA1_Length);
            sKA1_Fill1 = sKA1.substring(1, 6);
            sKA1_Code = sKA1.substring(6, 7);
            sKA1_Temp = sKA1.substring(7, 12);
            sKA1_Fill2 = sKA1.substring(12, 13);
            if(sKA1_Temp.equals("+9999")) {
                sKA1_Temp = "***";
            } else {
                try {
                    fWork = Float.parseFloat(sKA1_Temp);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sKA1_Temp value could not be converted to floating point=[" + sKA1_Temp + "]");
                    sKA1_Temp = "***";
                }

                if(!sKA1_Temp.equals("***")) {
                    if(fWork < -178.0F) {
                        fWork = (float)((int)((double)fWork / 10.0D * 1.8D + 32.0D - 0.5D));
                    } else {
                        fWork = (float)((int)((double)fWork / 10.0D * 1.8D + 32.0D + 0.5D));
                    }

                    if(sKA1_Code.equals("N")) {
                        sMinTemp = formatInt((int)fWork, 3);
                    } else if(sKA1_Code.equals("M")) {
                        sMaxTemp = formatInt((int)fWork, 3);
                    }
                }
            }
        }

    }

    public static void getKA2(String var0) {
        sKA2 = "";
        sKA2_Fill1 = "";
        sKA2_Code = "*";
        sKA2_Temp = "***";
        sKA2_Fill2 = "";
        iKA2_IndexOf = var0.indexOf("KA2");
        if(iKA2_IndexOf >= 0 && iKA2_IndexOf < iREM_IndexOf) {
            sKA2 = var0.substring(iKA2_IndexOf, iKA2_IndexOf + iKA2_Length);
            sKA2_Fill1 = sKA2.substring(1, 6);
            sKA2_Code = sKA2.substring(6, 7);
            sKA2_Temp = sKA2.substring(7, 12);
            sKA2_Fill2 = sKA2.substring(12, 13);
            if(sKA2_Temp.equals("+9999")) {
                sKA2_Temp = "***";
            } else {
                try {
                    fWork = Float.parseFloat(sKA2_Temp);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sKA2_Temp value could not be converted to floating point=[" + sKA2_Temp + "]");
                    sKA2_Temp = "***";
                }

                if(!sKA2_Temp.equals("***")) {
                    if(fWork < -178.0F) {
                        fWork = (float)((int)((double)fWork / 10.0D * 1.8D + 32.0D - 0.5D));
                    } else {
                        fWork = (float)((int)((double)fWork / 10.0D * 1.8D + 32.0D + 0.5D));
                    }

                    if(sKA2_Code.equals("N")) {
                        sMinTemp = formatInt((int)fWork, 3);
                    } else if(sKA2_Code.equals("M")) {
                        sMaxTemp = formatInt((int)fWork, 3);
                    }
                }
            }
        }

    }

    public static void getAA1(String var0) {
        sAA1 = "";
        sAA1_Fill1 = "";
        sAA1_Hours = "";
        sAA1_Pcp = "";
        sAA1_Trace = "";
        sAA1_Fill2 = "";
        iAA1_IndexOf = var0.indexOf("AA1");
        if(iAA1_IndexOf >= 0 && iAA1_IndexOf < iREM_IndexOf) {
            sAA1 = var0.substring(iAA1_IndexOf, iAA1_IndexOf + iAA1_Length);
            sAA1_Fill1 = sAA1.substring(1, 3);
            sAA1_Hours = sAA1.substring(3, 5);
            sAA1_Pcp = sAA1.substring(5, 9);
            sAA1_Trace = sAA1.substring(9, 10);
            sAA1_Fill2 = sAA1.substring(10, 11);
            if(sAA1_Pcp.equals("9999")) {
                sAA1_Pcp = "*****";
            } else {
                try {
                    fWork = Float.parseFloat(sAA1_Pcp);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] AA1_Pcp value could not be converted to floating point=[" + sAA1_Pcp + "]");
                    sAA1_Pcp = "*****";
                }

                if(!sAA1_Pcp.equals("*****")) {
                    setPcp(sAA1_Hours, sAA1_Trace);
                }
            }
        }

    }

    public static void getAA2(String var0) {
        sAA2 = "";
        sAA2_Fill1 = "";
        sAA2_Hours = "";
        sAA2_Pcp = "";
        sAA2_Trace = "";
        sAA2_Fill2 = "";
        iAA2_IndexOf = var0.indexOf("AA2");
        if(iAA2_IndexOf >= 0 && iAA2_IndexOf < iREM_IndexOf) {
            sAA2 = var0.substring(iAA2_IndexOf, iAA2_IndexOf + iAA2_Length);
            sAA2_Fill1 = sAA2.substring(1, 3);
            sAA2_Hours = sAA2.substring(3, 5);
            sAA2_Pcp = sAA2.substring(5, 9);
            sAA2_Trace = sAA2.substring(9, 10);
            sAA2_Fill2 = sAA2.substring(10, 11);
            if(sAA2_Pcp.equals("9999")) {
                sAA2_Pcp = "*****";
            } else {
                try {
                    fWork = Float.parseFloat(sAA2_Pcp);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] AA2_Pcp value could not be converted to floating point=[" + sAA2_Pcp + "]");
                    sAA2_Pcp = "*****";
                }

                if(!sAA2_Pcp.equals("*****")) {
                    setPcp(sAA2_Hours, sAA2_Trace);
                }
            }
        }

    }

    public static void getAA3(String var0) {
        sAA3 = "";
        sAA3_Fill1 = "";
        sAA3_Hours = "";
        sAA3_Pcp = "";
        sAA3_Trace = "";
        sAA3_Fill2 = "";
        iAA3_IndexOf = var0.indexOf("AA3");
        if(iAA3_IndexOf >= 0 && iAA3_IndexOf < iREM_IndexOf) {
            sAA3 = var0.substring(iAA3_IndexOf, iAA3_IndexOf + iAA3_Length);
            sAA3_Fill1 = sAA3.substring(1, 3);
            sAA3_Hours = sAA3.substring(3, 5);
            sAA3_Pcp = sAA3.substring(5, 9);
            sAA3_Trace = sAA3.substring(9, 10);
            sAA3_Fill2 = sAA3.substring(10, 11);
            if(sAA3_Pcp.equals("9999")) {
                sAA3_Pcp = "*****";
            } else {
                try {
                    fWork = Float.parseFloat(sAA3_Pcp);
                } catch (Exception var2) {
                    logIt(fDebug, 0, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] AA3_Pcp value could not be converted to floating point=[" + sAA3_Pcp + "]");
                    sAA3_Pcp = "*****";
                }

                if(!sAA3_Pcp.equals("*****")) {
                    setPcp(sAA3_Hours, sAA3_Trace);
                }
            }
        }

    }

    public static void getAA4(String var0) {
        sAA4 = "";
        sAA4_Fill1 = "";
        sAA4_Hours = "";
        sAA4_Pcp = "";
        sAA4_Trace = "";
        sAA4_Fill2 = "";
        iAA4_IndexOf = var0.indexOf("AA4");
        if(iAA4_IndexOf >= 0 && iAA4_IndexOf < iREM_IndexOf) {
            sAA4 = var0.substring(iAA4_IndexOf, iAA4_IndexOf + iAA4_Length);
            sAA4_Fill1 = sAA4.substring(1, 3);
            sAA4_Hours = sAA4.substring(3, 5);
            sAA4_Pcp = sAA4.substring(5, 9);
            sAA4_Trace = sAA4.substring(9, 10);
            sAA4_Fill2 = sAA4.substring(10, 11);
            if(sAA4_Pcp.equals("9999")) {
                sAA4_Pcp = "*****";
            } else {
                try {
                    fWork = Float.parseFloat(sAA4_Pcp);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] AA4_Pcp value could not be converted to floating point=[" + sAA4_Pcp + "]");
                    sAA4_Pcp = "*****";
                }

                if(!sAA4_Pcp.equals("*****")) {
                    setPcp(sAA4_Hours, sAA4_Trace);
                }
            }
        }

    }

    public static void setPcp(String var0, String var1) {
        fWork = fWork / 10.0F * 0.03937008F;
        if(var0.equals("01")) {
            sPcp01 = fmt5_2.format((double)fWork);
            sPcp01 = String.format("%5s", new Object[]{sPcp01});
            if(var1.equals("2")) {
                sPcp01t = "T";
            }
        } else if(var0.equals("06")) {
            sPcp06 = fmt5_2.format((double)fWork);
            sPcp06 = String.format("%5s", new Object[]{sPcp06});
            if(var1.equals("2")) {
                sPcp06t = "T";
            }
        } else if(var0.equals("24")) {
            sPcp24 = fmt5_2.format((double)fWork);
            sPcp24 = String.format("%5s", new Object[]{sPcp24});
            if(var1.equals("2")) {
                sPcp24t = "T";
            }
        } else {
            sPcp12 = fmt5_2.format((double)fWork);
            sPcp12 = String.format("%5s", new Object[]{sPcp12});
            if(var1.equals("2")) {
                sPcp12t = "T";
            }
        }

    }

    public static void getAJ1(String var0) {
        sAJ1 = "";
        sAJ1_Fill1 = "";
        sAJ1_Sd = "**";
        sAJ1_Fill2 = "";
        iAJ1_IndexOf = var0.indexOf("AJ1");
        if(iAJ1_IndexOf >= 0 && iAJ1_IndexOf < iREM_IndexOf) {
            sAJ1 = var0.substring(iAJ1_IndexOf, iAJ1_IndexOf + iAJ1_Length);
            sAJ1_Fill1 = sAJ1.substring(1, 3);
            sAJ1_Sd = sAJ1.substring(3, 7);
            sAJ1_Fill2 = sAJ1.substring(7, 17);
            if(sAJ1_Sd.equals("9999")) {
                sAJ1_Sd = "**";
            } else {
                try {
                    fWork = Float.parseFloat(sAJ1_Sd);
                } catch (Exception var2) {
                    logIt(fDebug, 1, false, "sInFileName=[" + sInFileName + "] DateTime=[" + sConcat + "] sAJ1_Sd value could not be converted to floating point=[" + sAJ1_Sd + "]");
                    sAJ1_Sd = "**";
                }

                if(!sAJ1_Sd.equals("**")) {
                    iWork = (int)((double)(fWork * 0.3937008F) + 0.5D);
                    sAJ1_Sd = fmt02.format((long)iWork);
                    sAJ1_Sd = String.format("%2s", new Object[]{sAJ1_Sd});
                }
            }
        }

    }

    public static void getAW1(String var0) {
        sAW1 = "";
        sAW1_Fill1 = "";
        sAW1_Zz = "**";
        sAW1_Fill2 = "";
        iAW1_IndexOf = var0.indexOf("AW1");
        if(iAW1_IndexOf >= 0 && iAW1_IndexOf < iREM_IndexOf) {
            sAW1 = var0.substring(iAW1_IndexOf, iAW1_IndexOf + iAW1_Length);
            sAW1_Fill1 = sAW1.substring(1, 3);
            sAW1_Zz = sAW1.substring(3, 5);
            sAW1_Fill2 = sAW1.substring(5, 6);
        }

    }

    public static void getAW2(String var0) {
        sAW2 = "";
        sAW2_Fill1 = "";
        sAW2_Zz = "**";
        sAW2_Fill2 = "";
        iAW2_IndexOf = var0.indexOf("AW2");
        if(iAW2_IndexOf >= 0 && iAW2_IndexOf < iREM_IndexOf) {
            sAW2 = var0.substring(iAW2_IndexOf, iAW2_IndexOf + iAW2_Length);
            sAW2_Fill1 = sAW2.substring(1, 3);
            sAW2_Zz = sAW2.substring(3, 5);
            sAW2_Fill2 = sAW2.substring(5, 6);
        }

    }

    public static void getAW3(String var0) {
        sAW3 = "";
        sAW3_Fill1 = "";
        sAW3_Zz = "**";
        sAW3_Fill2 = "";
        iAW3_IndexOf = var0.indexOf("AW3");
        if(iAW3_IndexOf >= 0 && iAW3_IndexOf < iREM_IndexOf) {
            sAW3 = var0.substring(iAW3_IndexOf, iAW3_IndexOf + iAW3_Length);
            sAW3_Fill1 = sAW3.substring(1, 3);
            sAW3_Zz = sAW3.substring(3, 5);
            sAW3_Fill2 = sAW3.substring(5, 6);
        }

    }

    public static void getAW4(String var0) {
        sAW4 = "";
        sAW4_Fill1 = "";
        sAW4_Zz = "**";
        sAW4_Fill2 = "";
        iAW4_IndexOf = var0.indexOf("AW4");
        if(iAW4_IndexOf >= 0 && iAW4_IndexOf < iREM_IndexOf) {
            sAW4 = var0.substring(iAW4_IndexOf, iAW4_IndexOf + iAW4_Length);
            sAW4_Fill1 = sAW4.substring(1, 3);
            sAW4_Zz = sAW4.substring(3, 5);
            sAW4_Fill2 = sAW4.substring(5, 6);
        }

    }

    public static int logIt(FileOutputStream var0, int var1, boolean var2, String var3) {
        byte var4 = 99;
        String var5 = "";
        SimpleDateFormat var6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date var7 = new Date();
        var5 = sProgramName + ": " + var6.format(var7) + "_" + var3;
        if(bStdErr) {
            System.err.println(var5);
        }

        if(bVerbose) {
            System.out.println(var5);
        }

        if(iLogLevel < var1) {
            return 0;
        } else if(var2 && !p_sFilter1.equals("None") && !sConcat.equals(p_sFilter1) && !sConcat.equals(p_sFilter2)) {
            return 0;
        } else {
            try {
                var0 = new FileOutputStream(sDebugName + ".debug", true);
                (new PrintStream(var0)).println(var6.format(var7) + "_" + var3);
                var4 = 0;
                var0.close();
            } catch (IOException var9) {
                System.out.println("5. Unable to open debug log");
                System.err.println(sProgramName + ": Stack trace follows:");
                var9.printStackTrace();
                System.exit(5);
            } catch (Exception var10) {
                var4 = 6;
                System.err.println(sProgramName + ": Unspecified Exception in logIt. Error=[" + var10.getMessage() + "]");
                System.err.println(sProgramName + ": Stack trace follows:");
                var10.printStackTrace();
                System.exit(6);
            }

            return var4;
        }
    }
}
