package vazkii.neat;

import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import vazkii.neat.mixin.AccessorRenderType;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class NeatRenderType {

	//https://github.com/UpcraftLP/Orderly/blob/master/src/main/resources/assets/orderly/textures/ui/default_health_bar.png
	public static final Identifier HEALTH_BAR_TEXTURE = Identifier.fromNamespaceAndPath(NeatConfig.MOD_ID, "textures/ui/health_bar_texture.png");
	public static final RenderType BAR_TEXTURE_TYPE = getHealthBarType();

	private static RenderType getHealthBarType() {
		RenderSetup renderSetup = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT)
				.withTexture("Sampler0", NeatRenderType.HEALTH_BAR_TEXTURE)
				.useLightmap()
				.bufferSize(256)
				.createRenderSetup();
		return AccessorRenderType.neat_create("neat_health_bar", renderSetup);
	}
}
