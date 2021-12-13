package com.alaharranhonor.swem.armor;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class GenericModel extends ArmorBaseModel{
	private ModelRenderer Head;
	private ModelRenderer myArmorHead;
	private ModelRenderer bone;
	private ModelRenderer bone4;
	private ModelRenderer bone2;
	private ModelRenderer bone5;
	private ModelRenderer bone6;
	private ModelRenderer bone3;
	private ModelRenderer bone8;
	private ModelRenderer bone7;
	private ModelRenderer Body;
	private ModelRenderer myArmorBody;
	private ModelRenderer bone9;
	private ModelRenderer bone10;
	private ModelRenderer bone14;
	private ModelRenderer bone15;
	private ModelRenderer bone16;
	private ModelRenderer bone17;
	private ModelRenderer bone18;
	private ModelRenderer bone19;
	private ModelRenderer bone20;
	private ModelRenderer bone13;
	private ModelRenderer bone25;
	private ModelRenderer bone12;
	private ModelRenderer bone11;
	private ModelRenderer RightArm;
	private ModelRenderer myArmorRightArm;
	private ModelRenderer bone23;
	private ModelRenderer bone24;
	private ModelRenderer LeftArm;
	private ModelRenderer myArmorLeftArm;
	private ModelRenderer bone21;
	private ModelRenderer bone22;
	private ModelRenderer RightLeg;
	private ModelRenderer myArmorRightLeg;
	private ModelRenderer bone33;
	private ModelRenderer bone34;
	private ModelRenderer bone36;
	private ModelRenderer RightBoot;
	private ModelRenderer myArmorRightBoot;
	private ModelRenderer bone29;
	private ModelRenderer LeftLeg;
	private ModelRenderer myArmorLeftLeg;
	private ModelRenderer bone32;
	private ModelRenderer bone35;
	private ModelRenderer LeftBoot;
	private ModelRenderer myArmorLeftBoot;
	private ModelRenderer bone26;

	public GenericModel(int textureWidth, int textureHeight, ResourceLocation texture) {
		super(textureWidth, textureHeight, texture);
	}

	// LEFT BOOT IS ACTUALLY THE RIGHT BOOT
	// RIGHT BOOT IS ACTUALLY THE LEFT BOOT
	// RIGHT LEG IS ACTUALLY THE LEFT LEG
	// LEFT LEG IS ACTUALLY THE RIGHT LEG
	@Override
	public void setupArmorParts() {
		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Head.setTexSize(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		myArmorHead = new ModelRenderer(this);
		myArmorHead.setPos(0.0556F, -12.5556F, 2.6111F);
		armorHead.addChild(myArmorHead);
		myArmorHead.setTexSize(0, 16).addBox(-4.0556F, 3.5556F, -6.6111F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		myArmorHead.setTexSize(51, 52).addBox(-4.0556F, 4.5556F, 1.3889F, 8.0F, 8.0F, 1.0F, 0.0F, false);
		myArmorHead.setTexSize(18, 41).addBox(-5.0556F, 4.5556F, -6.6111F, 1.0F, 5.0F, 8.0F, 0.0F, false);
		myArmorHead.setTexSize(23, 62).addBox(-6.0556F, 4.5556F, -5.6111F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		myArmorHead.setTexSize(18, 59).addBox(4.9444F, 4.5556F, -5.6111F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		myArmorHead.setTexSize(36, 24).addBox(4.9444F, 5.5556F, -2.6111F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		myArmorHead.setTexSize(0, 25).addBox(-6.0556F, 5.5556F, -2.6111F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		myArmorHead.setTexSize(66, 66).addBox(-5.0556F, 9.5556F, -6.6111F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		myArmorHead.setTexSize(15, 65).addBox(3.9444F, 9.5556F, -6.6111F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		myArmorHead.setTexSize(61, 62).addBox(-5.0556F, 9.5556F, -1.6111F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		myArmorHead.setTexSize(42, 62).addBox(3.9444F, 9.5556F, -1.6111F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		myArmorHead.setTexSize(0, 41).addBox(3.9444F, 4.5556F, -6.6111F, 1.0F, 5.0F, 8.0F, 0.0F, false);
		myArmorHead.setTexSize(33, 72).addBox(-0.5556F, 7.5556F, -8.6111F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		myArmorHead.setTexSize(29, 72).addBox(-0.5556F, 1.5556F, -8.6111F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setPos(-0.0556F, 5.5556F, -12.6111F);
		myArmorHead.addChild(bone);
		setRotationAngle(bone, 0.0F, 0.3927F, 0.0F);
		bone.setTexSize(56, 30).addBox(-7.9927F, -1.0F, 3.5042F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		bone.setTexSize(66, 50).addBox(-5.9927F, -2.0F, 3.5042F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		bone.setTexSize(66, 11).addBox(-4.9927F, -3.0F, 3.5042F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		bone.setTexSize(12, 54).addBox(-7.9927F, 2.0F, 3.5042F, 6.0F, 2.0F, 1.0F, 0.0F, false);
		bone.setTexSize(63, 45).addBox(-6.9927F, 4.0F, 3.5042F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		bone.setTexSize(30, 67).addBox(-5.9927F, 5.0F, 3.5042F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		bone.setTexSize(0, 73).addBox(-4.9927F, 6.0F, 3.5042F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		bone.setTexSize(16, 69).addBox(-7.9927F, -1.0F, 3.5042F, 2.0F, 4.0F, 1.0F, -0.0625F, false);

		bone4 = new ModelRenderer(this);
		bone4.setPos(-1.0556F, 5.5556F, -12.6111F);
		myArmorHead.addChild(bone4);
		setRotationAngle(bone4, 0.0F, -0.3927F, 0.0F);
		bone4.setTexSize(56, 28).addBox(2.9166F, -1.0F, 3.1215F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTexSize(66, 43).addBox(2.9166F, -2.0F, 3.1215F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTexSize(65, 47).addBox(2.9166F, -3.0F, 3.1215F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		bone4.setTexSize(52, 49).addBox(2.9166F, 2.0F, 3.1215F, 6.0F, 2.0F, 1.0F, 0.0F, false);
		bone4.setTexSize(63, 41).addBox(2.9166F, 4.0F, 3.1215F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTexSize(66, 24).addBox(2.9166F, 5.0F, 3.1215F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTexSize(7, 72).addBox(2.9166F, 6.0F, 3.1215F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTexSize(24, 16).addBox(6.9166F, -1.0F, 3.1215F, 2.0F, 4.0F, 1.0F, -0.0625F, false);

		bone2 = new ModelRenderer(this);
		bone2.setPos(-0.0556F, 12.5556F, 3.3889F);
		myArmorHead.addChild(bone2);
		setRotationAngle(bone2, 0.0F, -0.3927F, 0.0F);
		bone2.setTexSize(71, 67).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setPos(-0.0556F, 11.5556F, 3.3889F);
		myArmorHead.addChild(bone5);
		setRotationAngle(bone5, 0.5236F, -0.3927F, 0.0F);
		bone5.setTexSize(69, 58).addBox(-3.2325F, -1.5845F, -1.2803F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone5.setTexSize(66, 61).addBox(-3.2325F, -3.3165F, -0.2803F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone5.setTexSize(66, 36).addBox(-3.2325F, -5.0486F, 0.7197F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone5.setTexSize(69, 55).addBox(-3.2325F, -5.7806F, 1.7197F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone5.setTexSize(71, 65).addBox(-3.2325F, -6.5127F, 2.7197F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone6 = new ModelRenderer(this);
		bone6.setPos(-0.0556F, 11.5556F, 3.3889F);
		myArmorHead.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.3927F, 0.0F);
		bone6.setTexSize(53, 71).addBox(0.0F, 0.0F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setPos(-1.0556F, 15.5556F, -0.6111F);
		myArmorHead.addChild(bone3);
		setRotationAngle(bone3, 0.5236F, 0.3927F, 0.0F);
		bone3.setTexSize(45, 71).addBox(-0.4229F, -2.0195F, 4.2341F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		bone3.setTexSize(66, 32).addBox(-0.4877F, -4.765F, 5.2108F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone3.setTexSize(66, 20).addBox(-0.4877F, -6.4971F, 6.2108F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone3.setTexSize(69, 52).addBox(-0.4877F, -7.2291F, 7.2108F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone3.setTexSize(37, 71).addBox(-0.4877F, -7.9612F, 8.2108F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone8 = new ModelRenderer(this);
		bone8.setPos(1.0F, 3.0F, -5.0F);
		myArmorHead.addChild(bone8);
		bone8.setTexSize(24, 16).addBox(-1.5556F, -0.4444F, -1.6111F, 1.0F, 1.0F, 7.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setPos(0.0F, 0.0F, 2.0F);
		myArmorHead.addChild(bone7);
		setRotationAngle(bone7, -0.3927F, 0.0F, 0.0F);
		bone7.setTexSize(45, 57).addBox(-0.5556F, 3.6058F, -0.2472F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		bone7.setTexSize(73, 34).addBox(-0.5556F, 5.4536F, 3.5182F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		bone7.setTexSize(55, 73).addBox(-0.5556F, 7.3013F, 4.2836F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		bone7.setTexSize(44, 0).addBox(-0.5556F, 9.1491F, 5.049F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Body.setTexSize(0, 25).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		myArmorBody = new ModelRenderer(this);
		myArmorBody.setPos(0.0F, 0.0F, 0.0F);
		armorBody.addChild(myArmorBody);
		myArmorBody.setTexSize(48, 0).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 9.0F, 1.0F, 0.0F, false);
		myArmorBody.setTexSize(12, 57).addBox(-1.0F, 0.0F, 3.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);

		bone9 = new ModelRenderer(this);
		bone9.setPos(0.0F, 1.5F, -3.5F);
		myArmorBody.addChild(bone9);
		setRotationAngle(bone9, 0.3927F, 0.0F, 0.0F);
		bone9.setTexSize(52, 14).addBox(-4.0F, 9.0F, 2.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		bone9.setTexSize(74, 22).addBox(-3.0F, 13.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bone9.setTexSize(74, 20).addBox(1.0F, 13.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bone9.setTexSize(0, 71).addBox(1.0F, 12.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		bone9.setTexSize(22, 71).addBox(-4.0F, 12.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone10 = new ModelRenderer(this);
		bone10.setPos(0.0F, 2.25F, 3.75F);
		myArmorBody.addChild(bone10);
		setRotationAngle(bone10, -0.48F, 0.0F, 0.0F);
		bone10.setTexSize(48, 10).addBox(-4.0F, 8.4045F, -2.7055F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		bone10.setTexSize(67, 74).addBox(-3.0F, 12.4045F, -2.7055F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bone10.setTexSize(74, 10).addBox(1.0F, 12.4045F, -2.7055F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bone10.setTexSize(70, 14).addBox(-4.0F, 11.4045F, -2.7055F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		bone10.setTexSize(70, 16).addBox(1.0F, 11.4045F, -2.7055F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone14 = new ModelRenderer(this);
		bone14.setPos(1.0F, 0.0F, -2.0F);
		myArmorBody.addChild(bone14);
		setRotationAngle(bone14, 0.0F, 0.3927F, 0.0F);
		bone14.setTexSize(35, 53).addBox(-4.8258F, 2.0F, -2.9989F, 5.0F, 5.0F, 1.0F, 0.0F, false);
		bone14.setTexSize(26, 54).addBox(-2.8258F, 1.0F, -2.9989F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone15 = new ModelRenderer(this);
		bone15.setPos(0.0F, 6.0F, 2.0F);
		myArmorBody.addChild(bone15);
		setRotationAngle(bone15, 0.0F, 0.3927F, 0.0F);
		bone15.setTexSize(28, 47).addBox(0.3128F, 2.0F, 1.4546F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setPos(-1.0F, 3.0F, -2.0F);
		myArmorBody.addChild(bone16);
		setRotationAngle(bone16, 0.5236F, 0.3927F, 0.0F);
		bone16.setTexSize(69, 26).addBox(-0.294F, 5.5965F, 2.1576F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone16.setTexSize(30, 69).addBox(-0.294F, 3.8645F, 3.1576F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone16.setTexSize(8, 69).addBox(-0.294F, 2.1324F, 4.1576F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone16.setTexSize(55, 68).addBox(-0.294F, 0.4004F, 5.1576F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		bone17 = new ModelRenderer(this);
		bone17.setPos(-3.0F, 5.0F, 7.0F);
		myArmorBody.addChild(bone17);
		setRotationAngle(bone17, 0.0F, -0.3927F, 0.0F);
		bone17.setTexSize(0, 47).addBox(-2.4546F, 3.0F, -4.3128F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone18 = new ModelRenderer(this);
		bone18.setPos(-4.0F, 6.0F, 10.0F);
		myArmorBody.addChild(bone18);
		setRotationAngle(bone18, 0.5236F, -0.3927F, 0.0F);
		bone18.setTexSize(47, 68).addBox(-2.6788F, -3.5015F, -7.6007F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone18.setTexSize(39, 68).addBox(-2.6788F, -5.2336F, -6.6007F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone18.setTexSize(22, 68).addBox(-2.6788F, -6.9656F, -5.6007F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		bone18.setTexSize(0, 68).addBox(-2.6788F, -8.6977F, -4.6007F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		bone19 = new ModelRenderer(this);
		bone19.setPos(0.0F, -2.0F, 1.0F);
		myArmorBody.addChild(bone19);
		setRotationAngle(bone19, -0.7854F, 0.0F, 0.0F);
		bone19.setTexSize(7, 65).addBox(-1.5F, 1.0F, 4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone19.setTexSize(24, 4).addBox(0.5F, 1.0F, 4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone19.setTexSize(40, 32).addBox(-1.5F, 2.3813F, 5.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone19.setTexSize(20, 25).addBox(0.5F, 2.3813F, 5.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone19.setTexSize(43, 73).addBox(-1.5F, 3.8284F, 6.8284F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		bone19.setTexSize(37, 73).addBox(0.5F, 3.8284F, 6.8284F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone20 = new ModelRenderer(this);
		bone20.setPos(0.0F, -4.0F, 1.0F);
		myArmorBody.addChild(bone20);
		setRotationAngle(bone20, -0.7854F, 0.0F, 0.0F);
		bone20.setTexSize(49, 73).addBox(-1.5F, 1.0F, 4.1768F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		bone20.setTexSize(20, 73).addBox(0.5F, 1.0F, 4.1768F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		bone13 = new ModelRenderer(this);
		bone13.setPos(0.0F, 3.5F, -8.5F);
		myArmorBody.addChild(bone13);
		setRotationAngle(bone13, 0.0F, 0.1963F, 0.0F);
		bone13.setTexSize(66, 18).addBox(-4.902F, 3.5F, 4.3548F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		bone25 = new ModelRenderer(this);
		bone25.setPos(0.0F, 8.0F, -4.0F);
		myArmorBody.addChild(bone25);
		setRotationAngle(bone25, 0.0F, -0.1963F, 0.0F);
		bone25.setTexSize(66, 6).addBox(-0.0447F, -1.0F, -0.045F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		bone12 = new ModelRenderer(this);
		bone12.setPos(4.0F, 2.0F, -2.0F);
		myArmorBody.addChild(bone12);
		setRotationAngle(bone12, 0.0F, -0.3927F, 0.0F);
		bone12.setTexSize(0, 54).addBox(-4.769F, 0.0F, -1.0957F, 5.0F, 5.0F, 1.0F, 0.0F, false);
		bone12.setTexSize(69, 29).addBox(-4.769F, -1.0F, -1.0957F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		bone11 = new ModelRenderer(this);
		bone11.setPos(0.0F, 0.0F, 0.0F);
		myArmorBody.addChild(bone11);
		bone11.setTexSize(48, 32).addBox(-4.0F, 1.0F, -3.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-4.0F, 2.0F, 0.0F);
		RightArm.setTexSize(40, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		myArmorRightArm = new ModelRenderer(this);
		myArmorRightArm.setPos(0.0F, 0.0F, 0.0F);
		armorRightArm.addChild(myArmorRightArm);
		myArmorRightArm.setTexSize(50, 65).addBox(-4.0F, 3.0F, 2.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		myArmorRightArm.setTexSize(0, 65).addBox(-3.0F, -1.0F, -3.0F, 4.0F, 2.0F, 1.0F, 0.0625F, false);
		myArmorRightArm.setTexSize(73, 47).addBox(-3.0F, 5.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorRightArm.setTexSize(73, 31).addBox(-2.0F, 1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorRightArm.setTexSize(73, 8).addBox(-3.0F, 2.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorRightArm.setTexSize(28, 62).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorRightArm.setTexSize(31, 64).addBox(-3.0F, -3.0F, -1.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		myArmorRightArm.setTexSize(0, 41).addBox(-5.0F, 4.0F, -2.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		myArmorRightArm.setTexSize(62, 70).addBox(-5.0F, 4.0F, -3.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		bone23 = new ModelRenderer(this);
		bone23.setPos(-8.0F, -1.0F, 0.0F);
		myArmorRightArm.addChild(bone23);
		setRotationAngle(bone23, 0.3927F, 0.0F, -0.3927F);
		bone23.setTexSize(10, 41).addBox(3.3818F, -0.3869F, -2.9524F, 4.0F, 1.0F, 3.0F, 0.0F, false);

		bone24 = new ModelRenderer(this);
		bone24.setPos(-8.0F, -1.0F, 0.0F);
		myArmorRightArm.addChild(bone24);
		setRotationAngle(bone24, -0.3927F, 0.0F, -0.3927F);
		bone24.setTexSize(10, 45).addBox(3.3818F, -0.3072F, -0.2399F, 4.0F, 1.0F, 3.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(4.0F, 2.0F, 0.0F);
		LeftArm.setTexSize(36, 37).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		myArmorLeftArm = new ModelRenderer(this);
		myArmorLeftArm.setPos(0.0F, 0.0F, 0.0F);
		armorLeftArm.addChild(myArmorLeftArm);
		myArmorLeftArm.setTexSize(66, 3).addBox(0.0F, 3.0F, 2.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(66, 0).addBox(-1.0F, -1.0F, -3.0F, 4.0F, 2.0F, 1.0F, 0.0625F, false);
		myArmorLeftArm.setTexSize(61, 74).addBox(1.0F, 5.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(73, 69).addBox(0.0F, 1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(13, 74).addBox(1.0F, 2.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(7, 74).addBox(0.0F, -2.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(65, 8).addBox(0.0F, -3.0F, -1.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(28, 41).addBox(4.0F, 4.0F, -2.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		myArmorLeftArm.setTexSize(68, 70).addBox(3.0F, 4.0F, -3.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		bone21 = new ModelRenderer(this);
		bone21.setPos(6.0F, -2.0F, 0.0F);
		myArmorLeftArm.addChild(bone21);
		setRotationAngle(bone21, -0.3927F, 0.0F, 0.3927F);
		bone21.setTexSize(52, 45).addBox(-5.0718F, -0.2318F, -0.065F, 4.0F, 1.0F, 3.0F, 0.0F, false);

		bone22 = new ModelRenderer(this);
		bone22.setPos(6.0F, -2.0F, 0.0F);
		myArmorLeftArm.addChild(bone22);
		setRotationAngle(bone22, 0.3927F, 0.0F, 0.3927F);
		bone22.setTexSize(52, 41).addBox(-5.0718F, -0.2099F, -2.8821F, 4.0F, 1.0F, 3.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-2.0F, 12.0F, 0.0F);
		RightLeg.setTexSize(32, 0).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		myArmorRightLeg = new ModelRenderer(this);
		myArmorRightLeg.setPos(0.0F, 0.0F, 0.0F);
		armorRightLeg.addChild(myArmorRightLeg);


		bone33 = new ModelRenderer(this);
		bone33.setPos(0.0F, 0.0F, 0.0F);
		myArmorRightLeg.addChild(bone33);
		bone33.setTexSize(35, 59).addBox(2.0F, 0.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);

		bone34 = new ModelRenderer(this);
		bone34.setPos(0.0F, -1.6875F, -2.125F);
		myArmorRightLeg.addChild(bone34);
		setRotationAngle(bone34, 0.3927F, 0.0F, 0.0F);
		bone34.setTexSize(33, 16).addBox(2.0F, 3.9761F, -3.0577F, 4.0F, 3.0F, 1.0F, -0.0625F, false);

		bone36 = new ModelRenderer(this);
		bone36.setPos(3.0F, 0.0F, 0.0F);
		myArmorRightLeg.addChild(bone36);
		setRotationAngle(bone36, 0.0F, 0.3927F, 0.0F);
		bone36.setTexSize(0, 0).addBox(2.9197F, 0.0F, -1.6236F, 1.0F, 4.0F, 3.0F, 0.0F, false);

		RightBoot = new ModelRenderer(this);
		RightBoot.setPos(-2.0F, 12.0F, 0.0F);


		myArmorRightBoot = new ModelRenderer(this);
		myArmorRightBoot.setPos(-2.0F, 12.0F, 0.0F);
		armorRightBoot.addChild(myArmorRightBoot);
		myArmorRightBoot.setTexSize(24, 0).addBox(2.0F, 10.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
		myArmorRightBoot.setTexSize(24, 21).addBox(3.0F, 9.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		bone29 = new ModelRenderer(this);
		bone29.setPos(6.0F, 6.0F, 3.0F);
		myArmorRightBoot.addChild(bone29);
		setRotationAngle(bone29, -0.1963F, 0.0F, 0.0F);
		bone29.setTexSize(25, 56).addBox(-4.0F, 0.8582F, 0.1707F, 4.0F, 5.0F, 1.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(2.0F, 12.0F, 0.0F);
		LeftLeg.setTexSize(24, 25).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		myArmorLeftLeg = new ModelRenderer(this);
		myArmorLeftLeg.setPos(-7.0F, 1.0F, -2.0F);
		armorLeftLeg.addChild(myArmorLeftLeg);
		myArmorLeftLeg.setTexSize(0, 60).addBox(1.0F, -1.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);

		bone32 = new ModelRenderer(this);
		bone32.setPos(3.0F, 1.0F, -4.0F);
		myArmorLeftLeg.addChild(bone32);
		setRotationAngle(bone32, 0.3927F, 0.0F, 0.0F);
		bone32.setTexSize(54, 61).addBox(-2.0F, 2.0681F, 1.9253F, 4.0F, 3.0F, 1.0F, -0.0625F, false);

		bone35 = new ModelRenderer(this);
		bone35.setPos(-3.0F, 0.0F, 0.0F);
		myArmorLeftLeg.addChild(bone35);
		setRotationAngle(bone35, 0.0F, -0.3927F, 0.0F);
		bone35.setTexSize(0, 16).addBox(3.3128F, -1.0F, -2.4546F, 1.0F, 4.0F, 3.0F, 0.0F, false);

		LeftBoot = new ModelRenderer(this);
		LeftBoot.setPos(2.0F, 12.0F, 0.0F);


		myArmorLeftBoot = new ModelRenderer(this);
		myArmorLeftBoot.setPos(2.0F, 12.0F, 0.0F);
		armorLeftBoot.addChild(myArmorLeftBoot);
		myArmorLeftBoot.setTexSize(56, 24).addBox(-6.0F, 10.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
		myArmorLeftBoot.setTexSize(18, 57).addBox(-5.0F, 9.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		bone26 = new ModelRenderer(this);
		bone26.setPos(-2.0F, 6.0F, 3.0F);
		myArmorLeftBoot.addChild(bone26);
		setRotationAngle(bone26, -0.1963F, 0.0F, 0.0F);
		bone26.setTexSize(56, 18).addBox(-4.0F, 0.8582F, 0.1707F, 4.0F, 5.0F, 1.0F, 0.0F, false);
	}
}
