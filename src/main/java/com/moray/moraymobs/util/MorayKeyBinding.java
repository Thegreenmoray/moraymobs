package com.moray.moraymobs.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.windows.MOUSEINPUT;

public class MorayKeyBinding {
    public static final String KEY_CATEGORY_MORAY = "key.category.moraymobs.moray";
    public static final String KEY_ROAR = "key.moraymobs.roar";
    public static final KeyMapping ROARING_KEY = new KeyMapping(KEY_ROAR, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_MORAY);


}
