package in.nitkkr.placements.notifications;

import in.nitkkr.placements.activities.MainActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.placementapplication.R;

public class NotificationsGenerator {
	private final NotificationManager notificationManager;
	private final Context context;
	private final int MORE_ELIGIBLE_JOBS_ID = 1;
	private final int NEW_NOTIFICATIONS_ID = 2;

	public NotificationsGenerator(Context context) {
		this.notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		this.context = context;
	}

	public void moreEligibleJobsNotification() {
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
				context);
		notificationBuilder.setSmallIcon(R.drawable.ic_notifications_company);
		notificationBuilder.setContentTitle("New jobs you are eligible for");
		notificationBuilder.setContentText("Click here to view.");
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notificationBuilder.setSound(soundUri);
		notificationBuilder.setAutoCancel(true);
		Intent resultIntent = new Intent(context, MainActivity.class);
		resultIntent.putExtra(MainActivity.FRAGMENT_TO_OPEN,
				MainActivity.JOB_FRAGMENT);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
				MORE_ELIGIBLE_JOBS_ID, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		notificationBuilder.setContentIntent(resultPendingIntent);
		notificationManager.notify(MORE_ELIGIBLE_JOBS_ID,
				notificationBuilder.build());
	}

	public void moreNotifications() {
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
				context);
		notificationBuilder
				.setSmallIcon(R.drawable.ic_notifications_notification);
		notificationBuilder.setContentTitle("New notifications");
		notificationBuilder.setContentText("Click here to view.");
		notificationBuilder.setAutoCancel(true);
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notificationBuilder.setSound(soundUri);
		Intent resultIntent = new Intent(context, MainActivity.class);
		resultIntent.putExtra(MainActivity.FRAGMENT_TO_OPEN,
				MainActivity.NOTIFICATIONS_FRAGMENT);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
				NEW_NOTIFICATIONS_ID, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		notificationBuilder.setContentIntent(resultPendingIntent);
		notificationManager.notify(NEW_NOTIFICATIONS_ID,
				notificationBuilder.build());
	}
}
