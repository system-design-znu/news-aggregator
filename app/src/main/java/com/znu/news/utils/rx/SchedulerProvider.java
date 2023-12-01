package com.znu.news.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler computation();

    Scheduler mainThread();

    Scheduler io();

    Scheduler ui();
}
