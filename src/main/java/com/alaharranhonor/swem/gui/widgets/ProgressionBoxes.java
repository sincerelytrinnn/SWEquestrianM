package com.alaharranhonor.swem.gui.widgets;

public enum ProgressionBoxes {
	ROOT("main/root", 111, 130),
	CANTAZARITE("main/cantazarite", 111, 147),
	CANTAZARITE_DYE("main/cantazarite_dye", 121, 149),
	CANTAZARITE_POTION("main/cantazarite_potion", 132, 147),
	CANTAZARITE_ANVIL("main/cantazarite_anvil", 140, 144),
	TACK_BOX("main/tack_box", 160, 121),
	AMETHYST_HORSE_ARMOR("main/amethyst_horse_armor", 167, 58),
	GLOW_BOOTS("main/glow_boots", 98, 141),
	GOLD_BOOTS("main/gold_boots", 101, 152),
	DIAMOND_BOOTS("main/diamond_boots", 84, 150),
	AMETHYST_BOOTS("main/amethyst_boots", 71, 159),
	SW_DIAMOND_BLOCK("main/sw_diamond_block", 129, 114),
	AMETHYST("main/amethyst", 129, 80),
	ADVENTURE_TACK_SET("main/adventure_tack_set", 148, 68),
	AMETHYST_HELMET("main/amethyst_helmet", 173, 66),
	AMETHYST_CHESTPLATE("main/amethyst_chestplate", 170, 77),
	AMETHYST_LEGGINGS("main/amethyst_leggings", 161, 83),
	AMETHYST_LONGSOWRD("main/amethyst_longsword", 153, 91),
	AMETHYST_SCYTHE("main/amethyst_scythe", 154, 104),
	AMETHYST_BOW("main/amethyst_bow", 148, 115),
	STAR_WORM_GOOP("main/star_worm_goop", 137, 127),
	BANDAGE("main/bandage", 158, 133),
	SLOW_FEEDERS("main/slow_feeders", 154, 149),
	SHAVINGS("main/shavings", 166, 146),
	HORSE_WHISTLE("main/horse_whistle", 172, 159),
	MEASUREMENT_TOOL("main/measurement_tool", 161, 159),
	WESTERN_BARREL("main/western_barrel", 166, 168);



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