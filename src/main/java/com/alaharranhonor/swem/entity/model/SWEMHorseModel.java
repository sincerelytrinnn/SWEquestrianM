
package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class SWEMHorseModel extends AnimatedEntityModel<SWEMHorseEntity> {

    private final AnimatedModelRenderer main;
    private final AnimatedModelRenderer body;
    private final AnimatedModelRenderer front;
    private final AnimatedModelRenderer base2;
    private final AnimatedModelRenderer breastright;
    private final AnimatedModelRenderer breastleft;
    private final AnimatedModelRenderer frontleft;
    private final AnimatedModelRenderer bone;
    private final AnimatedModelRenderer bone2;
    private final AnimatedModelRenderer bone3;
    private final AnimatedModelRenderer hoof;
    private final AnimatedModelRenderer frontright;
    private final AnimatedModelRenderer bone8;
    private final AnimatedModelRenderer bone9;
    private final AnimatedModelRenderer bone10;
    private final AnimatedModelRenderer hoof4;
    private final AnimatedModelRenderer neck;
    private final AnimatedModelRenderer neckjoint1;
    private final AnimatedModelRenderer mainneck1;
    private final AnimatedModelRenderer mane;
    private final AnimatedModelRenderer forelock;
    private final AnimatedModelRenderer lock1;
    private final AnimatedModelRenderer lock1seg2;
    private final AnimatedModelRenderer lock1seg3;
    private final AnimatedModelRenderer lock2;
    private final AnimatedModelRenderer lock1seg4;
    private final AnimatedModelRenderer lock1seg5;
    private final AnimatedModelRenderer lock3;
    private final AnimatedModelRenderer lock1seg6;
    private final AnimatedModelRenderer lock1seg7;
    private final AnimatedModelRenderer lock4;
    private final AnimatedModelRenderer lock1seg8;
    private final AnimatedModelRenderer lock1seg9;
    private final AnimatedModelRenderer lock5;
    private final AnimatedModelRenderer lock1seg10;
    private final AnimatedModelRenderer lock1seg11;
    private final AnimatedModelRenderer lock6;
    private final AnimatedModelRenderer lock1seg12;
    private final AnimatedModelRenderer lock1seg13;
    private final AnimatedModelRenderer lock7;
    private final AnimatedModelRenderer lock1seg14;
    private final AnimatedModelRenderer bone5;
    private final AnimatedModelRenderer thiccneck1;
    private final AnimatedModelRenderer head;
    private final AnimatedModelRenderer skull;
    private final AnimatedModelRenderer bridgeofnose;
    private final AnimatedModelRenderer bone14;
    private final AnimatedModelRenderer bang;
    private final AnimatedModelRenderer ears;
    private final AnimatedModelRenderer mouth;
    private final AnimatedModelRenderer downlip;
    private final AnimatedModelRenderer middle;
    private final AnimatedModelRenderer belly;
    private final AnimatedModelRenderer back;
    private final AnimatedModelRenderer base;
    private final AnimatedModelRenderer tail;
    private final AnimatedModelRenderer bone11;
    private final AnimatedModelRenderer tailhairgroup;
    private final AnimatedModelRenderer tail7;
    private final AnimatedModelRenderer tail6;
    private final AnimatedModelRenderer tail5;
    private final AnimatedModelRenderer tail4;
    private final AnimatedModelRenderer tail3;
    private final AnimatedModelRenderer tail2;
    private final AnimatedModelRenderer tail1;
    private final AnimatedModelRenderer tail8;
    private final AnimatedModelRenderer backleft;
    private final AnimatedModelRenderer bone4;
    private final AnimatedModelRenderer thigh;
    private final AnimatedModelRenderer knee2;
    private final AnimatedModelRenderer bone6;
    private final AnimatedModelRenderer hoof2;
    private final AnimatedModelRenderer backright;
    private final AnimatedModelRenderer bone7;
    private final AnimatedModelRenderer thigh2;
    private final AnimatedModelRenderer knee;
    private final AnimatedModelRenderer bone13;
    private final AnimatedModelRenderer hoof3;
    private final AnimatedModelRenderer bone12;

    public SWEMHorseModel()
    {
        textureWidth = 128;
        textureHeight = 128;
        main = new AnimatedModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(main, 0.0F, -1.5708F, 0.0F);

        main.setModelRendererName("main");
        this.registerModelRenderer(main);

        body = new AnimatedModelRenderer(this);
        body.setRotationPoint(0.0F, -12.0F, 0.0F);
        main.addChild(body);

        body.setModelRendererName("body");
        this.registerModelRenderer(body);

        front = new AnimatedModelRenderer(this);
        front.setRotationPoint(-8.0F, -13.0F, 0.0F);
        body.addChild(front);

        front.setModelRendererName("front");
        this.registerModelRenderer(front);

        base2 = new AnimatedModelRenderer(this);
        base2.setRotationPoint(0.0F, 0.0F, 0.0F);
        front.addChild(base2);
        base2.setTextureOffset(42, 65).addBox(-4.0F, -7.0F, -6.0F, 7.0F, 14.0F, 12.0F, -0.0625F, false);
        base2.setTextureOffset(0, 23).addBox(-6.0F, -4.0F, -3.0F, 2.0F, 8.0F, 6.0F, -0.0625F, false);
        base2.setTextureOffset(106, 76).addBox(-6.0F, -4.0F, -6.0F, 2.0F, 8.0F, 3.0F, 0.0F, false);
        base2.setTextureOffset(106, 43).addBox(-6.0F, -4.0F, 3.0F, 2.0F, 8.0F, 3.0F, 0.0F, false);
        base2.setModelRendererName("base2");
        this.registerModelRenderer(base2);

        breastright = new AnimatedModelRenderer(this);
        breastright.setRotationPoint(-5.0F, 5.0F, 2.0F);
        front.addChild(breastright);
        setRotationAngle(breastright, 0.0F, 0.0F, -0.5672F);
        breastright.setTextureOffset(46, 101).addBox(-1.1716F, -1.5857F, -2.0F, 3.0F, 3.0F, 5.0F, 0.002F, false);
        breastright.setModelRendererName("breastright");
        this.registerModelRenderer(breastright);

        breastleft = new AnimatedModelRenderer(this);
        breastleft.setRotationPoint(-5.0F, 5.0F, 2.0F);
        front.addChild(breastleft);
        setRotationAngle(breastleft, 0.0F, 0.0F, -0.5672F);
        breastleft.setTextureOffset(50, 0).addBox(-1.1716F, -1.5857F, -7.0F, 5.0F, 3.0F, 5.0F, 0.002F, false);
        breastleft.setModelRendererName("breastleft");
        this.registerModelRenderer(breastleft);

        frontleft = new AnimatedModelRenderer(this);
        frontleft.setRotationPoint(-1.0F, -5.0F, -4.0F);
        front.addChild(frontleft);
        frontleft.setTextureOffset(0, 77).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 13.0F, 6.0F, 0.0625F, false);
        frontleft.setTextureOffset(28, 77).addBox(-2.0F, 1.0F, -4.0F, 5.0F, 8.0F, 1.0F, 0.0F, false);
        frontleft.setModelRendererName("frontleft");
        this.registerModelRenderer(frontleft);

        bone = new AnimatedModelRenderer(this);
        bone.setRotationPoint(1.0F, 11.0F, 0.0F);
        frontleft.addChild(bone);
        bone.setTextureOffset(0, 96).addBox(-3.75F, -1.0F, -2.0F, 5.0F, 8.0F, 4.0F, 0.125F, false);
        bone.setModelRendererName("bone");
        this.registerModelRenderer(bone);

        bone2 = new AnimatedModelRenderer(this);
        bone2.setRotationPoint(-1.0F, 8.0F, 0.0F);
        bone.addChild(bone2);
        bone2.setTextureOffset(100, 67).addBox(-1.75F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        bone2.setModelRendererName("bone2");
        this.registerModelRenderer(bone2);

        bone3 = new AnimatedModelRenderer(this);
        bone3.setRotationPoint(-1.0F, 1.0F, 0.5F);
        bone2.addChild(bone3);
        bone3.setTextureOffset(98, 104).addBox(0.0F, 0.0F, -1.75F, 3.0F, 8.0F, 3.0F, 0.0F, false);
        bone3.setModelRendererName("bone3");
        this.registerModelRenderer(bone3);

        hoof = new AnimatedModelRenderer(this);
        hoof.setRotationPoint(0.0F, 8.0F, 0.0F);
        bone3.addChild(hoof);
        hoof.setTextureOffset(107, 90).addBox(-1.0F, -1.0F, -1.75F, 3.0F, 3.0F, 3.0F, 0.25F, false);
        hoof.setModelRendererName("hoof");
        this.registerModelRenderer(hoof);

        frontright = new AnimatedModelRenderer(this);
        frontright.setRotationPoint(-1.0F, -5.0F, 4.0F);
        front.addChild(frontright);
        frontright.setTextureOffset(72, 30).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 13.0F, 6.0F, 0.0625F, false);
        frontright.setTextureOffset(26, 52).addBox(-2.0F, 1.0F, 3.0F, 5.0F, 8.0F, 1.0F, 0.0F, false);
        frontright.setModelRendererName("frontright");
        this.registerModelRenderer(frontright);

        bone8 = new AnimatedModelRenderer(this);
        bone8.setRotationPoint(1.0F, 11.0F, 0.0F);
        frontright.addChild(bone8);
        bone8.setTextureOffset(94, 23).addBox(-3.75F, -1.0F, -2.0F, 5.0F, 8.0F, 4.0F, 0.125F, false);
        bone8.setModelRendererName("bone8");
        this.registerModelRenderer(bone8);

        bone9 = new AnimatedModelRenderer(this);
        bone9.setRotationPoint(-1.0F, 8.0F, 0.0F);
        bone8.addChild(bone9);
        bone9.setTextureOffset(100, 59).addBox(-1.75F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        bone9.setModelRendererName("bone9");
        this.registerModelRenderer(bone9);

        bone10 = new AnimatedModelRenderer(this);
        bone10.setRotationPoint(-1.0F, 1.0F, -0.5F);
        bone9.addChild(bone10);
        bone10.setTextureOffset(18, 104).addBox(0.0F, 0.0F, -1.25F, 3.0F, 8.0F, 3.0F, 0.0F, false);
        bone10.setModelRendererName("bone10");
        this.registerModelRenderer(bone10);

        hoof4 = new AnimatedModelRenderer(this);
        hoof4.setRotationPoint(0.0F, 8.0F, 0.0F);
        bone10.addChild(hoof4);
        hoof4.setTextureOffset(86, 59).addBox(-1.0F, -1.0F, -1.25F, 3.0F, 3.0F, 3.0F, 0.25F, false);
        hoof4.setModelRendererName("hoof4");
        this.registerModelRenderer(hoof4);

        neck = new AnimatedModelRenderer(this);
        neck.setRotationPoint(-4.0F, -1.0F, 0.0F);
        front.addChild(neck);

        neck.setModelRendererName("neck");
        this.registerModelRenderer(neck);

        neckjoint1 = new AnimatedModelRenderer(this);
        neckjoint1.setRotationPoint(0.0F, 0.0F, 0.0F);
        neck.addChild(neckjoint1);
        setRotationAngle(neckjoint1, 0.0F, 0.0F, -0.2618F);

        neckjoint1.setModelRendererName("neckjoint1");
        this.registerModelRenderer(neckjoint1);

        mainneck1 = new AnimatedModelRenderer(this);
        mainneck1.setRotationPoint(-4.5F, -0.5F, 0.0F);
        neckjoint1.addChild(mainneck1);
        setRotationAngle(mainneck1, 0.0F, 0.0F, 0.7854F);
        mainneck1.setTextureOffset(0, 65).addBox(-12.6337F, -10.1798F, -3.0F, 21.0F, 6.0F, 6.0F, 0.0F, false);
        mainneck1.setModelRendererName("mainneck1");
        this.registerModelRenderer(mainneck1);

        mane = new AnimatedModelRenderer(this);
        mane.setRotationPoint(0.0F, -14.0F, 0.0F);
        mainneck1.addChild(mane);

        mane.setModelRendererName("mane");
        this.registerModelRenderer(mane);

        forelock = new AnimatedModelRenderer(this);
        forelock.setRotationPoint(-3.616F, -5.2631F, -1.0F);
        mane.addChild(forelock);
        setRotationAngle(forelock, 0.0F, 0.0F, -0.3927F);

        forelock.setModelRendererName("forelock");
        this.registerModelRenderer(forelock);

        lock1 = new AnimatedModelRenderer(this);
        lock1.setRotationPoint(-11.6415F, 3.8085F, 0.05F);
        mane.addChild(lock1);
        setRotationAngle(lock1, 0.2182F, 0.0F, 0.0F);
        lock1.setTextureOffset(55, 8).addBox(-0.9922F, 0.0016F, -0.0513F, 2.0F, 0.0F, 3.0F, 0.0F, false);
        lock1.setModelRendererName("lock1");
        this.registerModelRenderer(lock1);

        lock1seg2 = new AnimatedModelRenderer(this);
        lock1seg2.setRotationPoint(-0.1F, 0.0532F, 2.8381F);
        lock1.addChild(lock1seg2);
        setRotationAngle(lock1seg2, -0.8727F, 0.0F, 0.0F);
        lock1seg2.setTextureOffset(61, 8).addBox(-0.8922F, -0.1168F, 0.0315F, 2.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg2.setModelRendererName("lock1seg2");
        this.registerModelRenderer(lock1seg2);

        lock1seg3 = new AnimatedModelRenderer(this);
        lock1seg3.setRotationPoint(0.1F, -0.0273F, 1.0077F);
        lock1seg2.addChild(lock1seg3);
        setRotationAngle(lock1seg3, -0.8727F, 0.0F, 0.0F);
        lock1seg3.setTextureOffset(59, 37).addBox(-0.9922F, -0.0748F, -0.0533F, 2.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg3.setModelRendererName("lock1seg3");
        this.registerModelRenderer(lock1seg3);

        lock2 = new AnimatedModelRenderer(this);
        lock2.setRotationPoint(-10.1095F, 4.0745F, 0.05F);
        mane.addChild(lock2);
        setRotationAngle(lock2, 0.2182F, 0.0F, 0.0F);
        lock2.setTextureOffset(49, 33).addBox(-0.5242F, -0.2581F, 0.0062F, 2.0F, 0.0F, 3.0F, 0.0F, false);
        lock2.setModelRendererName("lock2");
        this.registerModelRenderer(lock2);

        lock1seg4 = new AnimatedModelRenderer(this);
        lock1seg4.setRotationPoint(0.35F, -0.4196F, 2.909F);
        lock2.addChild(lock1seg4);
        setRotationAngle(lock1seg4, -0.8727F, 0.0F, 0.0F);
        lock1seg4.setTextureOffset(57, 11).addBox(-0.8742F, 0.0296F, 0.1855F, 2.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg4.setModelRendererName("lock1seg4");
        this.registerModelRenderer(lock1seg4);

        lock1seg5 = new AnimatedModelRenderer(this);
        lock1seg5.setRotationPoint(0.0258F, 0.1786F, 1.046F);
        lock1seg4.addChild(lock1seg5);
        setRotationAngle(lock1seg5, -0.8727F, 0.0F, 0.0F);
        lock1seg5.setTextureOffset(46, 49).addBox(-0.9F, -0.2022F, -0.0252F, 2.0F, 0.0F, 2.0F, 0.0F, false);
        lock1seg5.setModelRendererName("lock1seg5");
        this.registerModelRenderer(lock1seg5);

        lock3 = new AnimatedModelRenderer(this);
        lock3.setRotationPoint(-7.3114F, 3.8745F, 0.05F);
        mane.addChild(lock3);
        setRotationAngle(lock3, 0.2182F, 0.0F, 0.0F);
        lock3.setTextureOffset(7, 26).addBox(-1.3223F, -0.0628F, -0.0371F, 3.0F, 0.0F, 3.0F, 0.0F, false);
        lock3.setModelRendererName("lock3");
        this.registerModelRenderer(lock3);

        lock1seg6 = new AnimatedModelRenderer(this);
        lock1seg6.setRotationPoint(0.2F, -0.0444F, 2.7867F);
        lock3.addChild(lock1seg6);
        setRotationAngle(lock1seg6, -0.8727F, 0.0F, 0.0F);
        lock1seg6.setTextureOffset(43, 38).addBox(-1.5223F, -0.1465F, 0.0984F, 3.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg6.setModelRendererName("lock1seg6");
        this.registerModelRenderer(lock1seg6);

        lock1seg7 = new AnimatedModelRenderer(this);
        lock1seg7.setRotationPoint(-0.3F, 0.079F, 1.1161F);
        lock1seg6.addChild(lock1seg7);
        setRotationAngle(lock1seg7, -0.8727F, 0.0F, 0.0F);
        lock1seg7.setTextureOffset(7, 23).addBox(-1.2223F, -0.1311F, -0.1849F, 3.0F, 0.0F, 3.0F, 0.0F, false);
        lock1seg7.setModelRendererName("lock1seg7");
        this.registerModelRenderer(lock1seg7);

        lock4 = new AnimatedModelRenderer(this);
        lock4.setRotationPoint(-4.0152F, 3.8745F, -0.05F);
        mane.addChild(lock4);
        setRotationAngle(lock4, 0.2182F, 0.0F, 0.0F);
        lock4.setTextureOffset(33, 23).addBox(-1.6185F, -0.0412F, 0.0606F, 4.0F, 0.0F, 3.0F, 0.0F, false);
        lock4.setModelRendererName("lock4");
        this.registerModelRenderer(lock4);

        lock1seg8 = new AnimatedModelRenderer(this);
        lock1seg8.setRotationPoint(0.6F, -0.0787F, 3.1601F);
        lock4.addChild(lock1seg8);
        setRotationAngle(lock1seg8, -0.8727F, 0.0F, 0.0F);
        lock1seg8.setTextureOffset(35, 38).addBox(-2.2185F, 0.1007F, -0.036F, 4.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg8.setModelRendererName("lock1seg8");
        this.registerModelRenderer(lock1seg8);

        lock1seg9 = new AnimatedModelRenderer(this);
        lock1seg9.setRotationPoint(-0.3F, 0.1551F, 0.6807F);
        lock1seg8.addChild(lock1seg9);
        setRotationAngle(lock1seg9, -0.8727F, 0.0F, 0.0F);
        lock1seg9.setTextureOffset(46, 8).addBox(-1.9185F, -0.2003F, 0.1206F, 4.0F, 0.0F, 4.0F, 0.0F, false);
        lock1seg9.setModelRendererName("lock1seg9");
        this.registerModelRenderer(lock1seg9);

        lock5 = new AnimatedModelRenderer(this);
        lock5.setRotationPoint(-0.9531F, 3.8745F, -0.05F);
        mane.addChild(lock5);
        setRotationAngle(lock5, 0.2182F, 0.0F, 0.0F);
        lock5.setTextureOffset(49, 30).addBox(-0.6806F, -0.0412F, 0.0606F, 2.0F, 0.0F, 3.0F, 0.0F, false);
        lock5.setModelRendererName("lock5");
        this.registerModelRenderer(lock5);

        lock1seg10 = new AnimatedModelRenderer(this);
        lock1seg10.setRotationPoint(0.0F, 0.0692F, 3.1105F);
        lock5.addChild(lock1seg10);
        setRotationAngle(lock1seg10, -0.8727F, 0.0F, 0.0F);
        lock1seg10.setTextureOffset(55, 37).addBox(-0.6806F, -0.0317F, -0.1167F, 2.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg10.setModelRendererName("lock1seg10");
        this.registerModelRenderer(lock1seg10);

        lock1seg11 = new AnimatedModelRenderer(this);
        lock1seg11.setRotationPoint(0.1F, 0.1409F, 0.9579F);
        lock1seg10.addChild(lock1seg11);
        setRotationAngle(lock1seg11, -0.8727F, 0.0F, 0.0F);
        lock1seg11.setTextureOffset(47, 0).addBox(-0.7806F, -0.0534F, -0.1809F, 2.0F, 0.0F, 3.0F, 0.0F, false);
        lock1seg11.setModelRendererName("lock1seg11");
        this.registerModelRenderer(lock1seg11);

        lock6 = new AnimatedModelRenderer(this);
        lock6.setRotationPoint(1.311F, 3.8745F, -0.05F);
        mane.addChild(lock6);
        setRotationAngle(lock6, 0.2182F, 0.0F, 0.0F);
        lock6.setTextureOffset(41, 23).addBox(-0.9447F, -0.0412F, 0.0606F, 2.0F, 0.0F, 3.0F, 0.0F, false);
        lock6.setModelRendererName("lock6");
        this.registerModelRenderer(lock6);

        lock1seg12 = new AnimatedModelRenderer(this);
        lock1seg12.setRotationPoint(0.0F, 0.0166F, 2.9434F);
        lock6.addChild(lock1seg12);
        setRotationAngle(lock1seg12, -0.8727F, 0.0F, 0.0F);
        lock1seg12.setTextureOffset(51, 37).addBox(-0.9447F, -0.1259F, 0.031F, 2.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg12.setModelRendererName("lock1seg12");
        this.registerModelRenderer(lock1seg12);

        lock1seg13 = new AnimatedModelRenderer(this);
        lock1seg13.setRotationPoint(0.0F, 0.0238F, 0.838F);
        lock1seg12.addChild(lock1seg13);
        setRotationAngle(lock1seg13, -0.8727F, 0.0F, 0.0F);
        lock1seg13.setTextureOffset(48, 3).addBox(-0.9447F, -0.243F, 0.0094F, 2.0F, 0.0F, 2.0F, 0.0F, false);
        lock1seg13.setModelRendererName("lock1seg13");
        this.registerModelRenderer(lock1seg13);

        lock7 = new AnimatedModelRenderer(this);
        lock7.setRotationPoint(3.4771F, 3.8745F, -0.05F);
        mane.addChild(lock7);
        setRotationAngle(lock7, 0.2182F, 0.0F, 0.0F);
        lock7.setTextureOffset(33, 26).addBox(-1.1108F, -0.0422F, 0.0606F, 2.0F, 0.0F, 3.0F, 0.0F, false);
        lock7.setModelRendererName("lock7");
        this.registerModelRenderer(lock7);

        lock1seg14 = new AnimatedModelRenderer(this);
        lock1seg14.setRotationPoint(-0.3F, 0.0285F, 2.9516F);
        lock7.addChild(lock1seg14);
        setRotationAngle(lock1seg14, -0.8727F, 0.0F, 0.0F);
        lock1seg14.setTextureOffset(35, 29).addBox(-0.8108F, -0.1279F, 0.0159F, 2.0F, 0.0F, 1.0F, 0.0F, false);
        lock1seg14.setModelRendererName("lock1seg14");
        this.registerModelRenderer(lock1seg14);

        bone5 = new AnimatedModelRenderer(this);
        bone5.setRotationPoint(-10.5865F, -19.0278F, -1.0F);
        neckjoint1.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, -0.3927F);

        bone5.setModelRendererName("bone5");
        this.registerModelRenderer(bone5);

        thiccneck1 = new AnimatedModelRenderer(this);
        thiccneck1.setRotationPoint(-1.7112F, -1.6461F, 0.0F);
        neckjoint1.addChild(thiccneck1);
        setRotationAngle(thiccneck1, 0.0F, 0.0F, -0.48F);
        thiccneck1.setTextureOffset(28, 87).addBox(-2.6464F, -10.5857F, -2.0F, 5.0F, 16.0F, 4.0F, 0.002F, false);
        thiccneck1.setModelRendererName("thiccneck1");
        this.registerModelRenderer(thiccneck1);

        head = new AnimatedModelRenderer(this);
        head.setRotationPoint(-6.9913F, -11.9996F, -0.125F);
        neckjoint1.addChild(head);

        head.setModelRendererName("head");
        this.registerModelRenderer(head);

        skull = new AnimatedModelRenderer(this);
        skull.setRotationPoint(-5.0F, 3.0F, 0.0F);
        head.addChild(skull);
        skull.setTextureOffset(80, 10).addBox(0.9332F, -3.5184F, -3.875F, 5.0F, 5.0F, 8.0F, 0.0F, false);
        skull.setTextureOffset(82, 100).addBox(3.6311F, -7.507F, -2.875F, 2.0F, 5.0F, 6.0F, 0.125F, false);
        skull.setTextureOffset(93, 94).addBox(-7.032F, -2.2541F, -2.875F, 4.0F, 4.0F, 6.0F, 0.125F, false);
        skull.setModelRendererName("skull");
        this.registerModelRenderer(skull);

        bridgeofnose = new AnimatedModelRenderer(this);
        bridgeofnose.setRotationPoint(-4.0F, -0.5F, -0.5F);
        skull.addChild(bridgeofnose);
        setRotationAngle(bridgeofnose, 0.0F, 0.0F, -0.4974F);
        bridgeofnose.setTextureOffset(80, 0).addBox(-1.0629F, -2.6236F, -2.375F, 11.0F, 4.0F, 6.0F, 0.0625F, false);
        bridgeofnose.setModelRendererName("bridgeofnose");
        this.registerModelRenderer(bridgeofnose);

        bone14 = new AnimatedModelRenderer(this);
        bone14.setRotationPoint(12.1454F, -1.5234F, 0.825F);
        bridgeofnose.addChild(bone14);
        setRotationAngle(bone14, 0.0F, 0.0F, 0.6545F);
        bone14.setTextureOffset(10, 37).addBox(-2.1031F, -0.0168F, -1.2F, 2.0F, 0.0F, 2.0F, 0.0F, false);
        bone14.setModelRendererName("bone14");
        this.registerModelRenderer(bone14);

        bang = new AnimatedModelRenderer(this);
        bang.setRotationPoint(3.9048F, -7.9282F, 0.225F);
        skull.addChild(bang);
        setRotationAngle(bang, 0.0F, 0.0F, -0.2618F);
        bang.setTextureOffset(0, 37).addBox(-4.9848F, -0.0477F, -1.1F, 5.0F, 0.0F, 2.0F, 0.0F, false);
        bang.setModelRendererName("bang");
        this.registerModelRenderer(bang);

        ears = new AnimatedModelRenderer(this);
        ears.setRotationPoint(-0.9523F, -4.1121F, 0.0F);
        head.addChild(ears);
        setRotationAngle(ears, 0.0F, 0.0F, -0.3054F);
        ears.setTextureOffset(0, 62).addBox(0.5298F, -3.5758F, -2.875F, 1.0F, 4.0F, 2.0F, 0.0F, false);
        ears.setTextureOffset(0, 23).addBox(0.5298F, -3.5758F, 1.125F, 1.0F, 4.0F, 2.0F, 0.0F, false);
        ears.setModelRendererName("ears");
        this.registerModelRenderer(ears);

        mouth = new AnimatedModelRenderer(this);
        mouth.setRotationPoint(-3.0F, 3.0F, 0.0F);
        head.addChild(mouth);
        mouth.setTextureOffset(86, 49).addBox(-6.5466F, -2.9352F, -2.875F, 7.0F, 4.0F, 6.0F, 0.001F, false);
        mouth.setModelRendererName("mouth");
        this.registerModelRenderer(mouth);

        downlip = new AnimatedModelRenderer(this);
        downlip.setRotationPoint(-6.521F, 1.7192F, 0.125F);
        mouth.addChild(downlip);
        setRotationAngle(downlip, 0.0F, 0.0F, -0.3927F);
        downlip.setTextureOffset(52, 30).addBox(-1.7008F, -0.5812F, -3.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        downlip.setModelRendererName("downlip");
        this.registerModelRenderer(downlip);

        middle = new AnimatedModelRenderer(this);
        middle.setRotationPoint(0.0F, -14.0F, 0.0F);
        body.addChild(middle);
        middle.setTextureOffset(0, 0).addBox(-12.0F, -5.0F, -6.0F, 19.0F, 11.0F, 12.0F, 0.002F, false);
        middle.setTextureOffset(48, 9).addBox(-6.0F, -3.0F, -7.0F, 9.0F, 7.0F, 14.0F, 0.0F, false);
        middle.setModelRendererName("middle");
        this.registerModelRenderer(middle);

        belly = new AnimatedModelRenderer(this);
        belly.setRotationPoint(-1.0F, 7.0F, 0.0F);
        middle.addChild(belly);
        belly.setTextureOffset(68, 65).addBox(-5.0F, -1.0F, -5.0F, 11.0F, 1.0F, 10.0F, 0.0F, false);
        belly.setTextureOffset(80, 80).addBox(-5.0F, -1.0F, -4.0F, 9.0F, 2.0F, 8.0F, 0.002F, false);
        belly.setModelRendererName("belly");
        this.registerModelRenderer(belly);

        back = new AnimatedModelRenderer(this);
        back.setRotationPoint(7.0F, -14.0F, 0.0F);
        body.addChild(back);

        back.setModelRendererName("back");
        this.registerModelRenderer(back);

        base = new AnimatedModelRenderer(this);
        base.setRotationPoint(4.0F, -5.0F, 0.0F);
        back.addChild(base);
        base.setTextureOffset(38, 38).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 13.0F, 14.0F, 0.0625F, false);
        base.setTextureOffset(0, 23).addBox(-4.0F, 1.0F, -8.0F, 10.0F, 10.0F, 16.0F, 0.0F, false);
        base.setTextureOffset(0, 49).addBox(-3.0F, -1.0F, -6.0F, 7.0F, 1.0F, 12.0F, 0.0F, false);
        base.setModelRendererName("base");
        this.registerModelRenderer(base);

        tail = new AnimatedModelRenderer(this);
        tail.setRotationPoint(7.0F, -3.0F, -1.0F);
        back.addChild(tail);

        tail.setModelRendererName("tail");
        this.registerModelRenderer(tail);

        bone11 = new AnimatedModelRenderer(this);
        bone11.setRotationPoint(2.0F, -2.0F, 1.1F);
        tail.addChild(bone11);
        bone11.setTextureOffset(26, 49).addBox(0.0F, 0.0F, -1.1F, 10.0F, 1.0F, 2.0F, 0.125F, false);
        bone11.setModelRendererName("bone11");
        this.registerModelRenderer(bone11);

        tailhairgroup = new AnimatedModelRenderer(this);
        tailhairgroup.setRotationPoint(3.5F, -1.5F, 1.0F);
        tail.addChild(tailhairgroup);

        tailhairgroup.setModelRendererName("tailhairgroup");
        this.registerModelRenderer(tailhairgroup);

        tail7 = new AnimatedModelRenderer(this);
        tail7.setRotationPoint(1.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail7);
        tail7.setTextureOffset(84, 76).addBox(0.5F, -0.5F, -1.0F, 1.0F, 11.0F, 1.0F, 0.0F, false);
        tail7.setModelRendererName("tail7");
        this.registerModelRenderer(tail7);

        tail6 = new AnimatedModelRenderer(this);
        tail6.setRotationPoint(2.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail6);
        tail6.setTextureOffset(30, 107).addBox(0.5F, -0.5F, 0.0F, 1.0F, 17.0F, 1.0F, 0.0F, false);
        tail6.setModelRendererName("tail6");
        this.registerModelRenderer(tail6);

        tail5 = new AnimatedModelRenderer(this);
        tail5.setRotationPoint(3.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail5);
        tail5.setTextureOffset(62, 101).addBox(0.5F, -0.5F, -1.0F, 1.0F, 17.0F, 1.0F, 0.0F, false);
        tail5.setModelRendererName("tail5");
        this.registerModelRenderer(tail5);

        tail4 = new AnimatedModelRenderer(this);
        tail4.setRotationPoint(4.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail4);
        tail4.setTextureOffset(38, 107).addBox(0.5F, -0.5F, 0.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
        tail4.setModelRendererName("tail4");
        this.registerModelRenderer(tail4);

        tail3 = new AnimatedModelRenderer(this);
        tail3.setRotationPoint(5.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail3);
        tail3.setTextureOffset(34, 107).addBox(0.5F, -0.5F, -1.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
        tail3.setModelRendererName("tail3");
        this.registerModelRenderer(tail3);

        tail2 = new AnimatedModelRenderer(this);
        tail2.setRotationPoint(6.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail2);
        tail2.setTextureOffset(72, 65).addBox(0.5F, -0.5F, 0.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
        tail2.setModelRendererName("tail2");
        this.registerModelRenderer(tail2);

        tail1 = new AnimatedModelRenderer(this);
        tail1.setRotationPoint(7.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail1);
        tail1.setTextureOffset(68, 65).addBox(0.5F, -0.5F, -1.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
        tail1.setModelRendererName("tail1");
        this.registerModelRenderer(tail1);

        tail8 = new AnimatedModelRenderer(this);
        tail8.setRotationPoint(0.0F, 0.0F, 0.0F);
        tailhairgroup.addChild(tail8);
        tail8.setTextureOffset(80, 76).addBox(0.5F, -0.5F, 0.0F, 1.0F, 11.0F, 1.0F, 0.0F, false);
        tail8.setModelRendererName("tail8");
        this.registerModelRenderer(tail8);

        backleft = new AnimatedModelRenderer(this);
        backleft.setRotationPoint(2.0F, 2.0F, -4.0F);
        back.addChild(backleft);
        backleft.setTextureOffset(46, 91).addBox(-1.0F, 3.0F, -2.5F, 7.0F, 5.0F, 5.0F, 0.1875F, false);
        backleft.setModelRendererName("backleft");
        this.registerModelRenderer(backleft);

        bone4 = new AnimatedModelRenderer(this);
        bone4.setRotationPoint(0.0F, 7.0F, 0.0F);
        backleft.addChild(bone4);

        bone4.setModelRendererName("bone4");
        this.registerModelRenderer(bone4);

        thigh = new AnimatedModelRenderer(this);
        thigh.setRotationPoint(4.0F, 7.0F, 1.0F);
        bone4.addChild(thigh);
        setRotationAngle(thigh, 0.0F, 0.0F, -0.6109F);
        thigh.setTextureOffset(66, 100).addBox(0.6268F, -9.6809F, -2.75F, 4.0F, 8.0F, 4.0F, 0.125F, false);
        thigh.setModelRendererName("thigh");
        this.registerModelRenderer(thigh);

        knee2 = new AnimatedModelRenderer(this);
        knee2.setRotationPoint(5.0F, 5.0F, 0.0F);
        bone4.addChild(knee2);
        knee2.setTextureOffset(100, 35).addBox(-2.0F, -2.0F, -1.75F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        knee2.setModelRendererName("knee2");
        this.registerModelRenderer(knee2);

        bone6 = new AnimatedModelRenderer(this);
        bone6.setRotationPoint(0.75F, 1.75F, 0.5F);
        knee2.addChild(bone6);
        bone6.setTextureOffset(0, 49).addBox(-2.25F, -0.75F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);
        bone6.setModelRendererName("bone6");
        this.registerModelRenderer(bone6);

        hoof2 = new AnimatedModelRenderer(this);
        hoof2.setRotationPoint(0.25F, 8.25F, 0.0F);
        bone6.addChild(hoof2);
        hoof2.setTextureOffset(70, 0).addBox(-3.5F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.25F, false);
        hoof2.setModelRendererName("hoof2");
        this.registerModelRenderer(hoof2);

        backright = new AnimatedModelRenderer(this);
        backright.setRotationPoint(2.0F, 2.0F, 4.0F);
        back.addChild(backright);
        backright.setTextureOffset(75, 90).addBox(-1.0F, 3.0F, -2.5F, 7.0F, 5.0F, 5.0F, 0.1875F, false);
        backright.setModelRendererName("backright");
        this.registerModelRenderer(backright);

        bone7 = new AnimatedModelRenderer(this);
        bone7.setRotationPoint(0.0F, 7.0F, 0.0F);
        backright.addChild(bone7);

        bone7.setModelRendererName("bone7");
        this.registerModelRenderer(bone7);

        thigh2 = new AnimatedModelRenderer(this);
        thigh2.setRotationPoint(4.0F, 7.0F, -7.0F);
        bone7.addChild(thigh2);
        setRotationAngle(thigh2, 0.0F, 0.0F, -0.6109F);
        thigh2.setTextureOffset(36, 26).addBox(0.6268F, -9.6809F, 4.75F, 4.0F, 8.0F, 4.0F, 0.125F, false);
        thigh2.setModelRendererName("thigh2");
        this.registerModelRenderer(thigh2);

        knee = new AnimatedModelRenderer(this);
        knee.setRotationPoint(5.0F, 5.0F, 0.0F);
        bone7.addChild(knee);
        knee.setTextureOffset(98, 10).addBox(-2.0F, -2.0F, -2.25F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        knee.setModelRendererName("knee");
        this.registerModelRenderer(knee);

        bone13 = new AnimatedModelRenderer(this);
        bone13.setRotationPoint(0.75F, 1.75F, -0.5F);
        knee.addChild(bone13);

        bone13.setModelRendererName("bone13");
        this.registerModelRenderer(bone13);

        hoof3 = new AnimatedModelRenderer(this);
        hoof3.setRotationPoint(0.25F, 8.25F, 0.0F);
        bone13.addChild(hoof3);
        hoof3.setTextureOffset(64, 30).addBox(-3.5F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.25F, false);
        hoof3.setModelRendererName("hoof3");
        this.registerModelRenderer(hoof3);

        bone12 = new AnimatedModelRenderer(this);
        bone12.setRotationPoint(-14.75F, 10.25F, -3.5F);
        bone13.addChild(bone12);
        bone12.setTextureOffset(0, 0).addBox(12.5F, -11.0F, 2.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
        bone12.setModelRendererName("bone12");
        this.registerModelRenderer(bone12);

        this.rootBones.add(main);
    }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("swem", "animations/swem_horse.json");
    }
}