package com.andrew.alarmclock.data.sourse.notification;

public interface INotificationSource {
    void createNotification(String description, boolean shouldDisturb, boolean isDeleted);
    void removeNotification();
}
