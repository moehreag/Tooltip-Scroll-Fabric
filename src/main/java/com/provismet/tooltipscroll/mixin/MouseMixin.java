package com.provismet.tooltipscroll.mixin;

import com.provismet.tooltipscroll.ScrollTracker;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
	// This will affect *every* use of the mouse wheel and alter the tracker accordingly.
	// Has no impact from a blackbox perspective though since the tooltip position will be reset when selecting an item.
	@Inject(method = "onMouseScroll(JDD)V", at = @At(value = "FIELD", ordinal = 0, shift = At.Shift.BEFORE))//, locals = LocalCapture.PRINT)
	private void trackWheel(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (vertical > 0) {
			if (InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_LEFT_SHIFT)) {
				ScrollTracker.scrollRight();
			}
			else {
				ScrollTracker.scrollUp();
			}
		}
		else if (vertical < 0) {
			if (InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_LEFT_SHIFT)) {
				ScrollTracker.scrollLeft();
			}
			else {
				ScrollTracker.scrollDown();
			}
		}
	}
}
