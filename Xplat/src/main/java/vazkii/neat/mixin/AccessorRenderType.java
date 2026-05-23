package vazkii.neat.mixin;

import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface AccessorRenderType {
	@Invoker("create")
	static RenderType neat_create(String name, RenderSetup renderSetup) {
		throw new IllegalStateException("");
	}
}
