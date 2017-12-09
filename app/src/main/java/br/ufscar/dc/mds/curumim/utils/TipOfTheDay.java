package br.ufscar.dc.mds.curumim.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class TipOfTheDay {

    public static String getTipOfTheDay() {

        return getTipOfTheDayOfMonthOffline();
    }


    public static String getTipOfTheDayOfMonthOffline() {
        List<String> tipsOfTheDay = Arrays.asList(
                "Tip 1",
                "Tip 2",
                "Tip 3",
                "Tip 4",
                "Tip 5",
                "Tip 6",
                "Tip 7",
                "Tip 8",
                "Tip 9",
                "Tip 10",
                "Tip 11",
                "Tip 12",
                "Tip 13",
                "Tip 14",
                "Tip 15",
                "Tip 16",
                "Tip 17",
                "Tip 18",
                "Tip 19",
                "Tip 20",
                "Tip 21",
                "Tip 22",
                "Tip 23",
                "Tip 24",
                "Tip 25",
                "Tip 26",
                "Tip 27",
                "Tip 28",
                "Tip 29",
                "Tip 30",
                "Tip 31"
        );

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return tipsOfTheDay.get(day - 1);
    }
}
