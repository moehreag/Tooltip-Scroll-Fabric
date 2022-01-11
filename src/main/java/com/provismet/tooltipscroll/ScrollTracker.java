package com.provismet.tooltipscroll;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;

public class ScrollTracker {
	private static int xOffset = 0;
	private static int yOffset = 0;
	public static TextRenderer renderer; 

	private static List<TooltipComponent> currentItem;
	private static final int scrollSize = 5;

	// Various scroll functions to interact with private fields
	public static void scrollUp() {
		yOffset -= scrollSize;
	}

	public static void scrollDown() {
		yOffset += scrollSize;
	}

	public static void scrollLeft() {
		xOffset -= scrollSize;
	}

	public static void scrollRight() {
		xOffset += scrollSize;
	}

	public static int getXOffset() {
		return xOffset;
	}

	public static int getYOffset() {
		return yOffset;
	}

	private static void resetScroll() {
		xOffset = 0;
		yOffset = 0;
	}

	// isEqual compares the width of each line to determine if a new tooltip is being loaded
	// this method is not as precise as comparing the strings directly but with the new
	// TooltipComponent system I could not find a better way to do it
	public static boolean isEqual(List<TooltipComponent> item1, List<TooltipComponent> item2) {
		if (item1 == null || item2 == null || item1.size() != item2.size()) {
			return false;
		}

		for (int i = 0; i < item1.size(); i++) {
			if (item1.get(i).getWidth(renderer) != item2.get(i).getWidth(renderer)) {
				return false;
			}
		}
		return true;
	}

	// Set currentItem if it is not the same tooltip as the saved one
	public static void setItem(List<TooltipComponent> item) {
		// We need the text renderer to get the width, so might as well check for that here
		if (ScrollTracker.renderer == null) {
			ScrollTracker.renderer = MinecraftClient.getInstance().textRenderer;
		}

		if (isEqual(currentItem, item) == false) {
			resetScroll();
			currentItem = item;
		}
	}
}