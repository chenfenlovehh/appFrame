package com.jinghan.core.crash.collector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import com.jinghan.core.crash.ReportBuilder;
import com.jinghan.core.crash.ReportField;
import com.jinghan.core.crash.model.Element;
import com.jinghan.core.crash.model.StringElement;
import com.jinghan.core.helper.DeviceId;

import java.util.Set;

/**
 * Collects the device ID
 *
 * @author F43nd1r
 * @since 4.9.1
 */
final class DeviceIdCollector extends Collector {
    private final Context context;
    private final SharedPreferences prefs;

    DeviceIdCollector(Context context, SharedPreferences prefs) {
        super(ReportField.DEVICE_ID);
        this.context = context;
        this.prefs = prefs;
    }

    @Override
    boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return super.shouldCollect(crashReportFields, collect, reportBuilder);
    }

    @NonNull
    @Override
    Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        final String result = DeviceId.getDeviceID(context);
        return result != null ? new StringElement(result) : null;
    }

}

