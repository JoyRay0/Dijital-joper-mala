package com.mala.digital_joper_mala.Utils;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class My_worker extends Worker {

    public My_worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        SharedPreferences notification_pre = getApplicationContext().getSharedPreferences("notification", Context.MODE_PRIVATE);

        notification_pre.edit().remove("show_notification").apply();

        return Result.success();
    }
}
