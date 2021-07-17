package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.entities.RiderEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.model.RiderModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RiderGeoRenderer<T extends RiderEntity> implements IGeoRenderer<T>, IResourceManager {

	public static final RiderGeoRenderer<RiderEntity> INSTANCE = new RiderGeoRenderer<>();

	private static final RiderModel riderModel = new RiderModel();


	@Override
	public GeoModelProvider getGeoModelProvider() {
		return riderModel;
	}

	@Override
	public ResourceLocation getTextureLocation(T t) {
		return riderModel.getTextureLocation(t);
	}

	@Override
	public void render(GeoModel model, T animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		RiderEntity rider = (RiderEntity) animatable;
		Entity entity = rider.getPlayer().getVehicle();
		if (entity instanceof SWEMHorseEntityBase) {

			float limbSwing = 0.0f;
			float limbSwingAmount = 0.0f;
			AnimationEvent<T> predicate = new AnimationEvent((IAnimatable)entity, limbSwing, limbSwingAmount, partialTicks, limbSwingAmount <= -0.15F || limbSwingAmount >= 0.15F, Collections.singletonList(new EntityModelData()));
			this.riderModel.setLivingAnimations(animatable, this.getUniqueID(animatable), predicate);


			this.renderEarly(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			if (renderTypeBuffer != null) {
				vertexBuilder = renderTypeBuffer.getBuffer(type);
			}

			this.renderLate(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			Iterator var14 = model.topLevelBones.iterator();

			while(var14.hasNext()) {
				GeoBone group = (GeoBone)var14.next();
				this.renderRecursively(group, matrixStackIn, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			}
		}
	}

	@Override
	public Set<String> getNamespaces() {
		return ImmutableSet.of("swem");
	}

	@Override
	public IResource getResource(ResourceLocation p_199002_1_) throws IOException {
		return new IResource() {
			ArrayList<InputStream> streams = new ArrayList<>();

			@Override
			public ResourceLocation getLocation() {
				return p_199002_1_;
			}

			@Override
			public InputStream getInputStream() {
				try {
					for (InputStream stream : streams) stream.close();
					streams.clear();
					System.out.println(p_199002_1_);
					System.out.println("Working dir: " + System.getProperty("user.dir"));

					File f1 = new File(p_199002_1_.getPath());
					System.out.println(f1.getAbsolutePath());
					String anim;
					if (f1.exists()) {
						InputStream stream1 = new FileInputStream(f1);
						byte[] bytes1 = new byte[stream1.available()];
						stream1.read(bytes1);
						stream1.close();
						anim = new String(bytes1);
					} else {
						anim = "{\"format_version\":\"1.8.0\",\"animations\":{\"animation.idle\":{\"loop\":true}}}";
					}
					anim = anim.replace(" ", "").replace("\t", "").replace("\n", "");

					InputStream stream = new ByteArrayInputStream(anim.getBytes());
					streams.add(stream);
					return stream;
				} catch (IOException ignored) {
				}

				return null;

			}

			@Nullable
			@Override
			public <T> T getMetadata(IMetadataSectionSerializer<T> p_199028_1_) {
				return null;
			}

			@Override
			public String getSourceName() {
				return "null";
			}

			@Override
			public void close() throws IOException {
				for(InputStream stream : streams) stream.close();
			}
		};
	}

	@Override
	public boolean hasResource(ResourceLocation p_219533_1_) {
		return true;
	}

	@Override
	public List<IResource> getResources(ResourceLocation p_199004_1_) throws IOException {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> listResources(String p_199003_1_, Predicate<String> p_199003_2_) {
		return new ArrayList<>();
	}

	@Override
	public Stream<IResourcePack> listPacks() {
		return null;
	}
}
