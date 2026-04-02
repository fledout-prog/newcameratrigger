/*
Copyright 2019 Peter Stoiber

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Please contact the author if you need another license.
This Repository is provided "as is", without warranties of any kind.
*/

package humer.UvcCamera;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Persistent storage for camera settings using SharedPreferences.
 * Saves and restores all UVC camera parameters so the app can auto-connect
 * without manual configuration on every launch.
 */
public class CameraSettingsDB {

    private static final String PREFS_NAME      = "CameraSettings";
    private static final String KEY_CONFIGURED  = "isConfigured";
    private static final String KEY_ALT_SETTING         = "camStreamingAltSetting";
    private static final String KEY_FORMAT_INDEX        = "camFormatIndex";
    private static final String KEY_FRAME_INDEX         = "camFrameIndex";
    private static final String KEY_FRAME_INTERVAL      = "camFrameInterval";
    private static final String KEY_PACKETS_PER_REQUEST = "packetsPerRequest";
    private static final String KEY_MAX_PACKET_SIZE     = "maxPacketSize";
    private static final String KEY_IMAGE_WIDTH         = "imageWidth";
    private static final String KEY_IMAGE_HEIGHT        = "imageHeight";
    private static final String KEY_ACTIVE_URBS         = "activeUrbs";
    private static final String KEY_VIDEOFORMAT         = "videoformat";
    private static final String KEY_DEVICE_NAME         = "deviceName";
    private static final String KEY_B_UNIT_ID           = "bUnitID";
    private static final String KEY_B_TERMINAL_ID       = "bTerminalID";
    private static final String KEY_B_STILL_CAPTURE     = "bStillCaptureMethod";
    private static final String KEY_LIBUSB              = "libUsb";
    private static final String KEY_MOVE_TO_NATIVE      = "moveToNative";
    private static final String KEY_BULK_MODE           = "bulkMode";
    private static final String KEY_PREVIEW_ENABLED     = "previewEnabled";

    private final SharedPreferences prefs;

    public CameraSettingsDB(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /** Returns true if the user has completed at least one camera setup. */
    public boolean isConfigured() {
        return prefs.getBoolean(KEY_CONFIGURED, false);
    }

    /** Returns true if the live preview should be visible. Default: true. */
    public boolean isPreviewEnabled() {
        return prefs.getBoolean(KEY_PREVIEW_ENABLED, true);
    }

    public void setPreviewEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_PREVIEW_ENABLED, enabled).apply();
    }

    /** Persists all UVC camera parameters. Call this after a successful manual setup. */
    public void saveSettings(
            int camStreamingAltSetting,
            int camFormatIndex,
            int camFrameIndex,
            int camFrameInterval,
            int packetsPerRequest,
            int maxPacketSize,
            int imageWidth,
            int imageHeight,
            int activeUrbs,
            String videoformat,
            String deviceName,
            byte bUnitID,
            byte bTerminalID,
            byte bStillCaptureMethod,
            boolean libUsb,
            boolean moveToNative,
            boolean bulkMode) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_CONFIGURED, true);
        editor.putInt(KEY_ALT_SETTING, camStreamingAltSetting);
        editor.putInt(KEY_FORMAT_INDEX, camFormatIndex);
        editor.putInt(KEY_FRAME_INDEX, camFrameIndex);
        editor.putInt(KEY_FRAME_INTERVAL, camFrameInterval);
        editor.putInt(KEY_PACKETS_PER_REQUEST, packetsPerRequest);
        editor.putInt(KEY_MAX_PACKET_SIZE, maxPacketSize);
        editor.putInt(KEY_IMAGE_WIDTH, imageWidth);
        editor.putInt(KEY_IMAGE_HEIGHT, imageHeight);
        editor.putInt(KEY_ACTIVE_URBS, activeUrbs);
        editor.putString(KEY_VIDEOFORMAT, videoformat != null ? videoformat : "MJPEG");
        editor.putString(KEY_DEVICE_NAME, deviceName != null ? deviceName : "");
        editor.putInt(KEY_B_UNIT_ID, bUnitID & 0xFF);
        editor.putInt(KEY_B_TERMINAL_ID, bTerminalID & 0xFF);
        editor.putInt(KEY_B_STILL_CAPTURE, bStillCaptureMethod & 0xFF);
        editor.putBoolean(KEY_LIBUSB, libUsb);
        editor.putBoolean(KEY_MOVE_TO_NATIVE, moveToNative);
        editor.putBoolean(KEY_BULK_MODE, bulkMode);
        editor.apply();
    }

    // ---- Getters ----

    public int getCamStreamingAltSetting()  { return prefs.getInt(KEY_ALT_SETTING, 0); }
    public int getCamFormatIndex()          { return prefs.getInt(KEY_FORMAT_INDEX, 0); }
    public int getCamFrameIndex()           { return prefs.getInt(KEY_FRAME_INDEX, 0); }
    public int getCamFrameInterval()        { return prefs.getInt(KEY_FRAME_INTERVAL, 0); }
    public int getPacketsPerRequest()       { return prefs.getInt(KEY_PACKETS_PER_REQUEST, 0); }
    public int getMaxPacketSize()           { return prefs.getInt(KEY_MAX_PACKET_SIZE, 0); }
    public int getImageWidth()              { return prefs.getInt(KEY_IMAGE_WIDTH, 0); }
    public int getImageHeight()             { return prefs.getInt(KEY_IMAGE_HEIGHT, 0); }
    public int getActiveUrbs()              { return prefs.getInt(KEY_ACTIVE_URBS, 0); }
    public String getVideoformat()          { return prefs.getString(KEY_VIDEOFORMAT, "MJPEG"); }
    public String getDeviceName()           { return prefs.getString(KEY_DEVICE_NAME, ""); }
    public byte getBUnitID()                { return (byte) prefs.getInt(KEY_B_UNIT_ID, 0); }
    public byte getBTerminalID()            { return (byte) prefs.getInt(KEY_B_TERMINAL_ID, 0); }
    public byte getBStillCaptureMethod()    { return (byte) prefs.getInt(KEY_B_STILL_CAPTURE, 0); }
    public boolean isLibUsb()               { return prefs.getBoolean(KEY_LIBUSB, true); }
    public boolean isMoveToNative()         { return prefs.getBoolean(KEY_MOVE_TO_NATIVE, false); }
    public boolean isBulkMode()             { return prefs.getBoolean(KEY_BULK_MODE, false); }
}
