package com.alexstyl.specialdates.upcoming.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import com.alexstyl.specialdates.events.peopleevents.PeopleEventsProvider
import com.alexstyl.specialdates.images.ImageLoader
import com.alexstyl.specialdates.permissions.MementoPermissions
import com.alexstyl.specialdates.upcoming.widget.today.LuminanceAnalyzer
import com.alexstyl.specialdates.upcoming.widget.today.RecentPeopleEventsPresenter
import com.alexstyl.specialdates.upcoming.widget.today.TodayUpcomingEventsView
import com.alexstyl.specialdates.upcoming.widget.today.UpcomingWidgetPreferences
import com.alexstyl.specialdates.upcoming.widget.today.WidgetImageLoader
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class RecentUpcomingPeopleEventsModule {

    @Provides
    fun preferences(context: Context): UpcomingWidgetPreferences {
        return UpcomingWidgetPreferences(context)
    }

    @Provides
    fun widgetImageLoader(appWidgetManager: AppWidgetManager, imageLoader: ImageLoader)
            : WidgetImageLoader {
        return WidgetImageLoader(appWidgetManager, imageLoader)
    }


    @Provides
    fun luminanceAnalyzer(): LuminanceAnalyzer {
        return LuminanceAnalyzer(Schedulers.io(), AndroidSchedulers.mainThread())
    }

    @Provides
    fun presenter(eventsProvider: PeopleEventsProvider, permissions: MementoPermissions): RecentPeopleEventsPresenter {
        return RecentPeopleEventsPresenter(eventsProvider, permissions, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Provides
    fun todayUpcomingEventsView(context: Context, appWidgetManager: AppWidgetManager): TodayUpcomingEventsView {
        return TodayUpcomingEventsView(context, appWidgetManager)
    }
}
