package com.jinghan.core.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.internal.util.Predicate;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author William Ferguson, F43nd1r
 * @since 4.6.0
 */
public final class IOUtils {

//    private static final Predicate<String> DEFAULT_FILTER = new Predicate<String>() {
//        @Override
//        public boolean apply(String s) {
//            return true;
//        }
//    };
//    private static final int NO_LIMIT = -1;
//    private static final int READ_TIMEOUT = 3000;
//
//    private IOUtils() {
//    }
//
//
    /**
     * Closes a Closeable.
     *
     * @param closeable Closeable to close. If closeable is null then method just returns.
     */
    public static void safeClose(@Nullable Closeable closeable) {
        if (closeable == null) return;

        try {
            closeable.close();
        } catch (IOException ignored) {
            // We made out best effort to release this resource. Nothing more we can do.
        }
    }
//
//    /**
//     * Reads an InputStream into a string
//     *
//     * @param input InputStream to read.
//     * @return the String that was read.
//     * @throws IOException if the InputStream could not be read.
//     */
////    @NonNull
////    public static String streamToString(@NonNull InputStream input) throws IOException {
////        return streamToString(input, DEFAULT_FILTER, NO_LIMIT);
////    }
//
//    /**
//     * Reads an InputStream into a string
//     *
//     * @param input  InputStream to read.
//     * @param filter should return false for lines which should be excluded
//     * @return the String that was read.
//     * @throws IOException if the InputStream could not be read.
//     */
//    @NonNull
//    public static String streamToString(@NonNull InputStream input, Predicate<String> filter) throws IOException {
//        return streamToString(input, filter, NO_LIMIT);
//    }
//
//    /**
//     * Reads an InputStream into a string
//     *
//     * @param input InputStream to read.
//     * @param limit the maximum number of lines to read (the last x lines are kept)
//     * @return the String that was read.
//     * @throws IOException if the InputStream could not be read.
//     */
//    @NonNull
//    public static String streamToString(@NonNull InputStream input, int limit) throws IOException {
//        return streamToString(input, DEFAULT_FILTER, limit);
//    }
//
//    /**
//     * Reads an InputStream into a string
//     *
//     * @param input  InputStream to read.
//     * @param filter Predicate that should return false for lines which should be excluded.
//     * @param limit  the maximum number of lines to read (the last x lines are kept)
//     * @return the String that was read.
//     * @throws IOException if the InputStream could not be read.
//     */
//    /*@NonNull
//    public static String streamToString(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {
//        final BufferedReader reader = new BufferedReader(new InputStreamReader(input), ACRAConstants.DEFAULT_BUFFER_SIZE_IN_BYTES);
//        try {
//            String line;
//            final List<String> buffer = limit == NO_LIMIT ? new LinkedList<String>() : new BoundedLinkedList<String>(limit);
//            while ((line = reader.readLine()) != null) {
//                if (filter.apply(line)) {
//                    buffer.add(line);
//                }
//            }
//            return TextUtils.join("\n", buffer);
//        } finally {
//            safeClose(reader);
//        }
//    }*/
//
//    /**
//     * Reads an InputStream into a string without blocking the current thread.
//     * It has a default timeout of 3 seconds.
//     *
//     * @param input  InputStream to read.
//     * @param filter Predicate that should return false for lines which should be excluded.
//     * @param limit  the maximum number of lines to read (the last x lines are kept).
//     * @return the String that was read.
//     * @throws IOException if the InputStream could not be read.
//     */
//   /* @NonNull
//    public static String streamToStringNonBlockingRead(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {
//        final BufferedReader reader = new BufferedReader(new InputStreamReader(input), ACRAConstants.DEFAULT_BUFFER_SIZE_IN_BYTES);
//        final NonBlockingBufferedReader nonBlockingReader = new NonBlockingBufferedReader(reader);
//        try {
//            final List<String> buffer = limit == NO_LIMIT ? new LinkedList<String>() : new BoundedLinkedList<String>(limit);
//            final long end = System.currentTimeMillis() + READ_TIMEOUT;
//            try {
//                while ((System.currentTimeMillis() < end)) {
//                    final String line = nonBlockingReader.readLine();
//                    if (line == null) {
//                        break;
//                    }
//                    if (filter.apply(line)) {
//                        buffer.add(line);
//                    }
//                }
//            } catch (InterruptedException e) {
//                Logger.d("Interrupted while reading stream", e);
//            }
//            return TextUtils.join("\n", buffer);
//        } finally {
//            nonBlockingReader.close();
//        }
//    }*/
//
//    public static void deleteReport(@NonNull File file) {
//        final boolean deleted = file.delete();
//        if (!deleted) {
//            Logger.d("Could not delete error report : " + file);
//        }
//    }
//
//   /* public static void writeStringToFile(@NonNull File file, @NonNull String content) throws IOException {
//        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), ACRAConstants.UTF8);
//        try {
//            writer.write(content);
//            writer.flush();
//        } finally {
//            safeClose(writer);
//        }
//    }*/
}