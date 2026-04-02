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

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Extends the original camera stream activity to add:
 *  1. Q-key trigger: pressing Q on a USB keyboard captures a photo.
 *  2. Preview toggle: if the user disabled preview, a black overlay hides the
 *     SurfaceView while keeping the native surface alive for the camera engine.
 *  3. FLAG_KEEP_SCREEN_ON: keeps the display on while the stream is active.
 *
 * The parent class (StartIsoStreamActivityUvc) is NOT modified.
 */
public class PhotoTriggerStreamActivity extends StartIsoStreamActivityUvc {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keep the screen on while the camera stream is active
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // If the user turned off preview, overlay a black view so the stream
        // surface (required by the native camera engine) is still created but
        // nothing is visible on screen.
        CameraSettingsDB db = new CameraSettingsDB(this);
        if (!db.isPreviewEnabled()) {
            applyBlackOverlay();
        }
    }

    /**
     * Intercepts key events before they reach the default handler.
     * A single press of the Q key triggers one photo capture.
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Q
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (photoButton != null && photoButton.isEnabled()) {
                photoButton.performClick();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    // ---- Private helpers ----

    /** Adds a full-screen black view on top of the layout to hide the preview. */
    private void applyBlackOverlay() {
        RelativeLayout root = (RelativeLayout) findViewById(R.id.rootView);
        if (root == null) return;
        View overlay = new View(this);
        overlay.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        root.addView(overlay, params);
    }
}
