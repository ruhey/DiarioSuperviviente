package org.aecc.superdiary.presentation.view.activity.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.view.activity.BienvenidaActivity;
import org.aecc.superdiary.presentation.view.activity.CitasActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentosActivity;
import org.aecc.superdiary.presentation.view.activity.RutinasActivity;


/**
 * This service is started when an Alarm has been raised
 * 
 * We pop a notification into the status bar for the user to click on
 * When the user clicks the notification a new activity is opened
 * 
 */
public class NotifyService extends Service {

	public static String TYPE = "D" ;

	/**
	 * Class for clients to access
	 */
	public class ServiceBinder extends Binder {
		NotifyService getService() {
			return NotifyService.this;
		}
	}

	// Unique id to identify the notification.
	private static final int NOTIFICATION = 123;
	// Name of an intent extra we can use to identify if this service was started to create a notification	
	public static final String INTENT_NOTIFY = ".service.INTENT_NOTIFY";
	// The system notification manager
	private NotificationManager mNM;

	@Override
	public void onCreate() {
		Log.i("NotifyService", "onCreate()");
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("LocalService", "Received start id " + startId + ": " + intent);
		
		// If this service was started by out AlarmTask intent then we want to show our notification
		if(intent.getBooleanExtra(INTENT_NOTIFY, false)) {
			showNotification(intent.getStringExtra(TYPE));
		}
		
		// We don't care if this service is stopped as we have already delivered our notification
		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// This is the object that receives interactions from clients
	private final IBinder mBinder = new ServiceBinder();

	/**
	 * Creates a notification and shows it in the OS drag-down status bar
	 */
	private void showNotification(String type) {
		CharSequence title = "Hora de consultar su diario de salud";
		int icon = R.drawable.aecc_corazonb;
		CharSequence text = "Acceda a la aplicación para más información";



		switch (type) {
			case "C":
				title = "Cita médica";
				icon = R.drawable.aecc_lugar;
				text = "La hora de su cita médica está próxima";
		//		contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, CitasActivity.class), 0);
				break;
			case "R":
				title = "Rutina";
				icon = R.drawable.aecc_persona;
				text = "La hora de sus actividades está próxima";
		//		contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, RutinasActivity.class), 0);
				break;
			case "M":
				title = "Medicación";
				icon = R.drawable.aecc_medicamento;
				text = "Es la hora de tomar sus medicinas";
		//		contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MedicamentosActivity.class), 0);
				break;
		}


		// What time to show on the notification
		long time = System.currentTimeMillis();
		
		Notification notification = new Notification(icon, text, time);

		// The PendingIntent to launch our activity if the user selects this notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, BienvenidaActivity.class), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, title, text, contentIntent);
		notification.defaults |= Notification.DEFAULT_SOUND;

		// Clear the notification when it is pressed
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		// Send the notification to the system.
		mNM.notify(NOTIFICATION, notification);

		// Stop the service when we are finished
		stopSelf();
	}
}