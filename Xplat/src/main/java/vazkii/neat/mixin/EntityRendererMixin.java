package vazkii.neat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import vazkii.neat.HealthBarRenderer;
import vazkii.neat.NeatConfig;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

	@Inject(method = "submitNameTag", at = @At("HEAD"), cancellable = true)
	private void neat_disableNameTag(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector buffers, CameraRenderState cameraRenderState, CallbackInfo ci) {
		Entity entity = HealthBarRenderer.getEntity(renderState);
		if (entity == null) {
			return;
		}
		NeatConfig.NameTagRenderBehavior renderBehavior = NeatConfig.instance.nameTagRenderBehavior();
		if (renderBehavior != NeatConfig.NameTagRenderBehavior.ALWAYS &&
				(renderBehavior == NeatConfig.NameTagRenderBehavior.WHEN_NO_HEALTHBAR && neat$entityHasHealthbar(entity)) ||
				renderBehavior == NeatConfig.NameTagRenderBehavior.NEVER) {
			ci.cancel();
		}
	}

	@Unique
	public boolean neat$entityHasHealthbar(Entity entity) {
		if (!(entity instanceof LivingEntity))
			return false;
		if (entity instanceof Player && !NeatConfig.instance.showOnPlayers())
			return false;
		if (HealthBarRenderer.isBoss(entity) && !NeatConfig.instance.showOnBosses())
			return false;
		if (entity.getType().getCategory().isFriendly() && !NeatConfig.instance.showOnPassive())
			return false;
		if ((!entity.getType().getCategory().isFriendly() && !HealthBarRenderer.isBoss(entity)) && !NeatConfig.instance.showOnHostile())
			return false;

		var id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
		return !NeatConfig.instance.blacklist().contains(id.toString()) && NeatConfig.draw;
	}
}
