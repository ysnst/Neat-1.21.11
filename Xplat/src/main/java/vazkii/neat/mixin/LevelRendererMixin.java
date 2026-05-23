package vazkii.neat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import vazkii.neat.HealthBarRenderer;

@Mixin(EntityRenderDispatcher.class)
public class LevelRendererMixin {

	@Inject(method = "extractEntity", at = @At("RETURN"))
	private void neat_storeEntity(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
		HealthBarRenderer.registerRenderState(cir.getReturnValue(), entity);
	}

	@Inject(method = "submit", at = @At("TAIL"))
	private void neat_renderHealthBar(EntityRenderState renderState, CameraRenderState cameraRenderState,
			double x, double y, double z, PoseStack poseStack, SubmitNodeCollector buffers, CallbackInfo ci) {
		Entity entity = HealthBarRenderer.getEntity(renderState);
		if (entity != null) {
			HealthBarRenderer.hookRender(entity, renderState, poseStack, buffers, cameraRenderState, x, y, z);
		}
	}
}
