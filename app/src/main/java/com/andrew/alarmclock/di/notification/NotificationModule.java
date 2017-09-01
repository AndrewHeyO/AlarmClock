package com.andrew.alarmclock.di.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.repository.notification.INotificationRepository;
import com.andrew.alarmclock.data.repository.notification.NotificationRepository;
import com.andrew.alarmclock.data.sourse.notification.INotificationSource;
import com.andrew.alarmclock.data.sourse.notification.NotificationSource;
import com.andrew.alarmclock.pager.presentation.PagerActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NotificationModule {

    @Binds
    @Singleton
    abstract INotificationSource provideNotificationSource(NotificationSource source);

    @Binds
    @Singleton
    abstract INotificationRepository provideNotificationRepository(NotificationRepository repository);

    @Provides
    @Singleton
    static NotificationManager provideNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    static NotificationCompat.Builder provideNotificationBuilder(Context context,
                                                                 TaskStackBuilder stackBuilder) {
        return new NotificationCompat.Builder(context)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(ContextCompat.getColor(context, R.color.accent))
                .setContentIntent(stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    @Provides
    @Singleton
    static TaskStackBuilder provideTaskStackBuilder(Context context) {
        return TaskStackBuilder.create(context)
                .addParentStack(PagerActivity.class)
                .addNextIntent(new Intent(context, PagerActivity.class));
    }
}
