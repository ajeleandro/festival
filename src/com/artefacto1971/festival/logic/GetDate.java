package com.artefacto1971.festival.logic;

import com.artefacto1971.festival.classes.Lineup;

public class GetDate {
	
	public String getLineUpDay(Lineup lineup){
		java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
		String day;
		
		switch  (calendar.get(java.util.Calendar.DAY_OF_WEEK)){
    	
	    case 1:
	    	day = "Sunday ";
	    	break;
	    case 2:
	    	day = "Monday ";
	    	break;
	    case 3:
	    	day = "Tuesday ";
	    	break;
	    case 4:
	    	day = "Wednesday ";
	    	break;
	    case 5:
	    	day = "Thursday ";
	    	break;	
	    case 6:
	    	day = "Friday ";
	    	break;
	    case 7:
	    	day = "Saturday ";
	    	break;
    	default:
    		day = "";
    		break;
	    }
	    
	    day = day +  + lineup.getDate().getDay() + " ,";
	    
	    switch (lineup.getDate().getMonth()) {
        case 1:  day = day + " January";
                 break;
        case 2:  day = day + " February";
                 break;
        case 3:  day = day + " March";
                 break;
        case 4:  day = day + " April";
                 break;
        case 5:  day = day + " May";
                 break;
        case 6:  day = day + " June";
                 break;
        case 7:  day = day + " July";
                 break;
        case 8:  day = day + " August";
                 break;
        case 9:  day = day + " September";
                 break;
        case 10: day = day + " October";
                 break;
        case 11: day = day + " November";
                 break;
        case 12: day = day + " December";
                 break;
        default: day = day + "";
                 break;
	    }
	    return day;
	}
	
}
