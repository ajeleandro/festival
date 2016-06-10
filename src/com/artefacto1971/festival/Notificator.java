package com.artefacto1971.festival;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.artefacto1971.festival.classes.Lineup;
import com.google.gson.Gson;

public class Notificator {
	
	Context context;
	Lineup lineup;

	public void Schedule(Context context, Lineup lineup){
		this.context = context;
		this.lineup = lineup;
		
		NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Festival")
                        .setContentText("The Show of " + lineup.getArtist() + " is about to start!");
		
		Gson gson = new Gson();
		String json = gson.toJson(lineup);

		Intent resultIntent = new Intent(context, ScheduleNotificationActivity.class);
		resultIntent.putExtra("extraJson",json);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		mBuilder.setOngoing(false);
		mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(lineup.getID(), mBuilder.build());
	}
}
