package com.onlinemadrasa.utils;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.onlinemadrasa.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static KProgressHUD hud_progress = null;
    public static KProgressHUD hud_status = null;

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, Integer id) {
        String message = context.getResources().getString(id);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showIosDialog(Context context, String title, String message) {
        new iOSDialogBuilder(context)
                .setTitle(title)
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener(context.getResources().getString(R.string.ok), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

    public static void showIosDialog(Context context, String message) {
        new iOSDialogBuilder(context)
                .setTitle(context.getResources().getString(R.string.alert))
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener(context.getResources().getString(R.string.ok), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

    public static void showIosDialog(Context context, String title, String message, final OnAlertHide onAlertHide) {
        new iOSDialogBuilder(context)
                .setTitle(title)
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener(context.getResources().getString(R.string.ok), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                        onAlertHide.onAlertHide();
                    }
                })
                .build().show();
    }

    public static String getDisplayFormattedDate(String inputDate) {
        String formattedDate = "";
        DateFormat formatter = new SimpleDateFormat("EEEE , MMM dd yyyy HH:mm a", Locale.ENGLISH);
        DateFormat toFormat = new SimpleDateFormat("EEEE, MMM dd yyyy", Locale.ENGLISH);
        try {
            Date date = formatter.parse(inputDate);
            formattedDate = toFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String getDisplayFormattedTime(String inputDate) {
        String formattedDate = "";
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        DateFormat toFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date date = formatter.parse(inputDate);
            formattedDate = toFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static void setLoading(Context context) {
        hideProgress();
        if (isValid(hud_progress))
            hud_progress = null;
        hud_progress = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.2f).setBackgroundColor(Color.TRANSPARENT);

    }

    public static void setLoadingWait(Context context) {
        hideProgress();
        if (isValid(hud_progress))
            hud_progress = null;
        hud_progress = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("Please wait").setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }

    public static void setLoading(Context context, String message) {
        hideProgress();
        if (isValid(hud_progress))
            hud_progress = null;
        hud_progress = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("" + message).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f).setBackgroundColor(R.color.black_transparent);

    }

    public static void hideProgress() {
        try {
            if (isValid(hud_progress)) if (hud_progress.isShowing()) hud_progress.dismiss();
        } catch (Exception e) {

        }
    }

    public static void showProgress() {
        try {
            hideProgress();
            if (isValid(hud_progress)) if (!hud_progress.isShowing()) hud_progress.show();
        } catch (Exception e) {
        }
    }


    public static Boolean isValid(Object object) {
        return object != null;
    }

    public static String getDeviceId(Context context) {
        try {
            String uuid;
            String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            uuid = androidID;// + deviceID ;//+ simID;
            uuid = String.format("%32s", uuid).replace(' ', '0');
            uuid = uuid.substring(0, 32);
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            System.out.println(" Device Id" + uuid);
            return uuid;
        } catch (Exception e) {
            return "No Device Id";
        }
    }


    public static String findDiffrence(String time1, String time2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date1 = simpleDateFormat.parse(time1);
        Date date2 = simpleDateFormat.parse(time2);

        long difference = date2.getTime() - date1.getTime();
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        return "" + hours + ":" + min;
    }

    public static long toMilliSecond(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date mDate = sdf.parse(time);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String addTime(String myTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(myTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 1);
        String newTime = df.format(cal.getTime());
        return newTime;
    }

    public static String subtractTime(String myTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(myTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.SECOND, 1);
        String newTime = df.format(cal.getTime());
        return newTime;
    }

    public static String subtractTime2(String myTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        Date d = df.parse(myTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.SECOND, -1);
        String newTime = df.format(cal.getTime());
        return newTime;
    }

    public static long minuteToMilliSecond(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        try {
            Date mDate = sdf.parse(time);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}