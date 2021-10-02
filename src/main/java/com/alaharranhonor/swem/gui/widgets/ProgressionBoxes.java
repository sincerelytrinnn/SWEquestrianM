package com.alaharranhonor.swem.gui.widgets;

public enum ProgressionBoxes {
	ROOT("main/root", 150, 120);


	public static int OFFSET = 30;
	private String path;
	private int x;
	private int y;

	ProgressionBoxes(String path, int x, int y) {
		this.path = path;
		this.x = x;
		this.y = y;
	}

	public String getPath() {
		return path;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isMouseOver(double mouseX, double mouseY) {
		return (mouseX >= getX() && mouseX <= getX() + OFFSET) && (mouseY >= getY() && mouseY <= getY() + OFFSET);
	}
}
