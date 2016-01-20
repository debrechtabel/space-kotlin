package com.trevordebrecht.spacekotlin;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by tdebrecht on 1/19/16.
 */
public class ShipUtil {

    public static void processShipDrawable(Drawable d) {
        DrawableCompat.setTint(d, ShipKt.shipColor());
        d.setAlpha(Ship.INSTANCE.getAlpha());
    }

}
