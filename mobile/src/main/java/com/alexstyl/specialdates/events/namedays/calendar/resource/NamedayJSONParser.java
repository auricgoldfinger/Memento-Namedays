package com.alexstyl.specialdates.events.namedays.calendar.resource;

import com.alexstyl.specialdates.ErrorTracker;
import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.namedays.NamedayBundle;
import com.alexstyl.specialdates.events.namedays.NamedaysList;
import com.novoda.notils.exception.DeveloperError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class NamedayJSONParser {

    private NamedayJSONParser() {
        // hide this
    }

    static NamedayBundle getNamedaysFromJSONasSounds(NamedayJSON json) {
        return createBundleWith(json, new SoundNode());
    }

    static NamedayBundle getNamedaysFrom(NamedayJSON json) {
        return createBundleWith(json, new CharacterNode());
    }

    private static NamedayBundle createBundleWith(NamedayJSON locale, Node namesToDate) {
        NamedaysList dateToNames = new NamedaysList();

        JSONArray data = locale.getData();
        int size = data.length();
        for (int i = 0; i < size; i++) {
            try {
                JSONObject nameday;

                nameday = (JSONObject) data.get(i);
                String dateString = nameday.getString("date");
                Date theDate = getNamedaysFrom(dateString);

                JSONArray variations = nameday.getJSONArray("names");
                int numberOfVariations = variations.length();
                for (int varCount = 0; varCount < numberOfVariations; varCount++) {
                    String variation = variations.getString(varCount);

                    namesToDate.addDate(variation, theDate);
                    dateToNames.addNameday(theDate, variation);

                }
            } catch (JSONException e) {
                ErrorTracker.track(e);
            }
        }

        return new NamedayBundle(namesToDate, dateToNames);
    }

    private static Date getNamedaysFrom(String date) {
        int slashIndex = date.indexOf("/");
        if (slashIndex == -1) {
            throw new DeveloperError("Unable to getNamedaysFrom " + date);
        }
        int dayOfMonth = Integer.valueOf(date.substring(0, slashIndex));
        int month = Integer.valueOf(date.substring(slashIndex + 1));
        return Date.on(dayOfMonth, month);
    }
}
