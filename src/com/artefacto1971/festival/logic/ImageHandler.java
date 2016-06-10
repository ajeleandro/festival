package com.artefacto1971.festival.logic;

import com.artefacto1971.festival.R;

public class ImageHandler {

	
	public int getFestivalDrawable(int fk_festival){
		
		switch (fk_festival) {
		case 1:
			return R.drawable.icon_festival_tomorrowland;
		case 2:
			return R.drawable.icon_festival_ultra;
		case 3:
			return R.drawable.icon_festival_bonnaroo;
		case 4:
			return R.drawable.icon_festival_rockinrio;
		case 5:
			return R.drawable.icon_festival_lolapalooza;
		case 6:
			return R.drawable.icon_festival_sonar;
		case 7:
			return R.drawable.icon_festival_creamfields;
		case 8:
			return R.drawable.icon_festival_hellfest;
		default:
			return R.drawable.ic_launcher;
		}
	}
	
public int getMenuItemDrawable(String item_title){
	
		if (item_title.equals("Choose Your Festival"))
			return R.drawable.icono_whatshot;
		
		else if (item_title.equals("Shows And Line Up"))
			return R.drawable.icono_live;
		
		else if (item_title.equals("News And Promos"))
			return R.drawable.icono_festnews;
		
		else if (item_title.equals("Festivals List"))
			return R.drawable.icono_live;
		
		else if (item_title.equals("Your Pics And Fashion"))
			return R.drawable.instagram_festival;
		
		else if (item_title.equals("Log In"))
			return R.drawable.facebook_icon;
		
		else if (item_title.equals("Your Voice"))
			return R.drawable.festival_tweet;
		
		else if (item_title.equals("Shop"))
			return R.drawable.icono_backpack;
		else
			return R.drawable.ic_launcher;
	}
	
public int getCategoryDrawable(int categoryID){
		
		
		switch (categoryID) {
		case 1:
			return R.drawable.icono_live;
		case 2:
			return R.drawable.icono_whatshot;
		case 3:
			return R.drawable.icono_festcamp;
		case 4:
			return R.drawable.icono_snackfest;
		case 5:
			return R.drawable.icono_festnews;
		case 6:
			return R.drawable.icono_backpack;
		case 7:
			return R.drawable.icono_glamcrowd;
		case 8:
			return R.drawable.icono_gotcha;
		case 9:
			return R.drawable.icono_shop;

		default:
			return R.drawable.ic_launcher;
		}
	}
	
	
}
