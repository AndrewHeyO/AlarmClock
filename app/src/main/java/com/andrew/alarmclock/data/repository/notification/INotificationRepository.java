package com.andrew.alarmclock.data.repository.notification;

import io.reactivex.Single;

public interface INotificationRepository {
    Single<Object> disturbNotificationHandle(boolean shouldDisturb);
}
