package com.artefacto1971.festival.logic;

import java.util.ArrayList;
import java.util.List;

import com.artefacto1971.festival.classes.Lineup;
import com.artefacto1971.festival.classes.ScheduleHeader;
import com.artefacto1971.festival.classes.ScheduleItemInterface;

public class ScheduleListHandler {
	
	private int i;
	private ArrayList<Lineup> lineuplist;
	
	public List<ScheduleItemInterface> GetArrangedLineup(ArrayList<Lineup> list){
		
		lineuplist = list;
		List<ScheduleItemInterface> Schedule = new ArrayList<ScheduleItemInterface>();
	    
	    
		for (i = 0; i < lineuplist.size(); i++) {
			if(i > 0){
				if(!lineuplist.get(i).getDate().equals(lineuplist.get(i - 1).getDate())){
					Schedule.add(new ScheduleHeader(getDay(),false));
					Schedule.add(new ScheduleHeader(lineuplist.get(i).getTime(),true));
				}
				else if(!lineuplist.get(i).getTime().equals(lineuplist.get(i - 1).getTime()))
					Schedule.add(new ScheduleHeader(lineuplist.get(i).getTime(),true));
			}
			else{
				Schedule.add(new ScheduleHeader(getDay(),false));
				Schedule.add(new ScheduleHeader(lineuplist.get(i).getTime(),true));
			}
			
			Schedule.add(lineuplist.get(i));
		}
		return Schedule;
	}
	
	public String getDay(){
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
	    
	    day = day +  + lineuplist.get(i).getDate().getDay() + " ,";
	    
	    switch (lineuplist.get(i).getDate().getMonth()) {
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
