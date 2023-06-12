package model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TuTaoKey {
	 public static String createKey(String tiento) {
	        String key = tiento;
	        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
	        Date currentDate = new Date();
	        String d = dateFormat.format(currentDate);
	        key = key + d;

	        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
	        String[] partsTime = timeFormat.format(currentDate).split(":");
	            if (partsTime[2].substring(3, 5).equals("PM")) {
	                partsTime[0] = convertTimeTo24(partsTime[0]);
	            }
	            if (partsTime[2].substring(3, 5).equals("AM")) {
	                if (partsTime[0].length() == 1) {
	                    partsTime[0] = "0" + partsTime[0];
	                }
	            }
	        partsTime[2] = partsTime[2].substring(0, 2);
	        String t = "_" + partsTime[0] + partsTime[1] + partsTime[2];
	        key = key + t;
	        return key;
	    }

    public static String convertTimeTo24(String hour) {
        String h = "";
        switch (hour) {
            case "1":
                h = "13";
                break;
            case "2":
                h = "14";
                break;
            case "3":
                h = "15";
                break;
            case "4":
                h = "16";
                break;
            case "5":
                h = "17";
                break;
            case "6":
                h = "18";
                break;
            case "7":
                h = "19";
                break;
            case "8":
                h = "20";
                break;
            case "9":
                h = "21";
                break;
            case "10":
                h = "22";
                break;
            case "11":
                h = "23";
                break;
            case "12":
                h = "0";
                break;
        }
        return h;
    }

}
