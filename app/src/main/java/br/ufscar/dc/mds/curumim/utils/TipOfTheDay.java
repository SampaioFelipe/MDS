package br.ufscar.dc.mds.curumim.utils;

import android.content.Context;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.ufscar.dc.mds.curumim.R;

public abstract class TipOfTheDay {

    public static String getTipOfTheDay(Context ctx) {

        return getTipOfTheDayOfMonthOffline(ctx);
    }

    public static String getTipOfTheDayOfMonthOffline(Context ctx) {
//        List<String> tipsOfTheDay = Arrays.asList();

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return ctx.getResources().getStringArray(R.array.tips_of_day)[day-1];

//        return tipsOfTheDay.get(day - 1);
    }
}
