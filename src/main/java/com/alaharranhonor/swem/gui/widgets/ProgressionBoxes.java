package com.alaharranhonor.swem.gui.widgets;

public enum ProgressionBoxes {
	ROOT("main/root", 133, 122),
	CANTAZARITE_DYE("main/cantazarite_dye", 133, 135),
	CANTAZARITE_POTION("main/cantazarite_potion", 146, 147),
	CANTAZARITE_ANVIL("main/cantazarite_anvil", 142, 165),
	SADDLE("main/saddle", 100, 93),
	//GLOW_SADDLE("main/glow_saddle", 133, 135), // MISSING DOT ON GUI
	//CLOTH_HORSE_ARMOR("main/cloth_horse_armor", 133, 135), // MISSING DOT ON GUI
	TACK_BOX("main/tack_box", 143, 93),
	RIVETS_AND_PLATES("main/rivets_and_plates", 157, 106),
	IRON_HORSE_ARMOR("main/iron_horse_armor", 160, 125),
	GOLD_HORSE_ARMOR("main/gold_horse_armor", 154, 134),
	DIAMOND_HORSE_ARMOR("main/diamond_horse_armor", 159, 148),
	AMETHYST_HORSE_ARMOR("main/amethyst_horse_armor", 143, 181),
	LEATHER_BOOTS("main/leather_boots", 82, 118),
	GLOW_BOOTS("main/glow_boots", 99, 127),
	IRON_BOOTS("main/iron_boots", 81, 145),
	GOLD_BOOTS("main/gold_boots", 97, 157),
	DIAMOND_BOOTS("main/diamond_boots", 106, 157),
	AMETHYST_BOOTS("main/amethyst_boots", 112, 151),
	AMETHYST_LEGGINGS("main/amethyst_leggings", 83, 164),
	AMETHYST_HELMET("main/amethyst_helmet", 92, 168),
	AMETHYST_CHESTPLATE("main/amethyst_chestplate", 98, 164);


	public static int OFFSET = 3;
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

	public boolean isMouseOver(double mouseX, double mouseY, int guiLeftOffset, int guiTopOffset) {
		return (mouseX >= getX() + guiLeftOffset && mouseX <= getX() + guiLeftOffset + OFFSET) && (mouseY >= getY() + guiTopOffset && mouseY <= getY() + guiTopOffset + OFFSET);
	}
}
