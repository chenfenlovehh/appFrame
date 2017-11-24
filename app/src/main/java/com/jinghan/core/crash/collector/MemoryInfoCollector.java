package com.jinghan.core.crash.collector;

import android.support.annotation.NonNull;

import com.jinghan.core.crash.ReportBuilder;
import com.jinghan.core.crash.ReportField;
import com.jinghan.core.crash.model.Element;
import com.jinghan.core.crash.model.NumberElement;
import com.jinghan.core.crash.model.StringElement;
import com.jinghan.core.helper.IOUtils;
import com.jinghan.core.helper.MemoryHelper;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Collects results of the <code>dumpsys</code> command.
 *
 * @author Kevin Gaudin & F43nd1r
 */
final class MemoryInfoCollector extends Collector {
    MemoryInfoCollector() {
        super(ReportField.DUMPSYS_MEMINFO, ReportField.TOTAL_MEM_SIZE, ReportField.AVAILABLE_MEM_SIZE);
    }

    @Override
    boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return super.shouldCollect(crashReportFields, collect, reportBuilder) && !(reportBuilder.getException() instanceof OutOfMemoryError);
    }

    @NonNull
    @Override
    Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        switch (reportField) {
            case DUMPSYS_MEMINFO:
                return collectMemInfo();
            case TOTAL_MEM_SIZE:
                return new NumberElement(MemoryHelper.getTotalInternalMemorySize());
            case AVAILABLE_MEM_SIZE:
                return new NumberElement(MemoryHelper.getAvailableInternalMemorySize());
            default:
                //will not happen if used correctly
                throw new IllegalArgumentException();
        }
    }

    /**
     * Collect results of the <code>dumpsys meminfo</code> command restricted to
     * this application process.
     *
     * @return The execution result.
     */
    @NonNull
    private static Element collectMemInfo() {

        try {
            final List<String> commandLine = new ArrayList<String>();
            commandLine.add("dumpsys");
            commandLine.add("meminfo");
            commandLine.add(Integer.toString(android.os.Process.myPid()));

            final Process process = Runtime.getRuntime().exec(commandLine.toArray(new String[commandLine.size()]));
            return null;
        } catch (IOException e) {
            Logger.d("MemoryInfoCollector.meminfo could not retrieve data", e);
            return null;
        }
    }
}