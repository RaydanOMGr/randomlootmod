package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;
import xyz.marstonconnell.randomloot.tools.BaseTool;

public class CriticalStrikeEvent extends WorldInteractEvent{

	@Override
	public void effect(ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, LivingEntity target) {
		
		
		float damage = BaseTool.getFloatNBT(stack, "rl_damage");
		
		
		target.setHealth(target.getHealth() - damage / 2f);
		
		
		((ServerWorld) target.getEntityWorld()).spawnParticle(ParticleTypes.DAMAGE_INDICATOR, target.getPosX(), target.getPosYHeight(0.5D), target.getPosZ(), (int)target.getHealth(), 0.1D, 0.0D, 0.1D, 0.2D);

		
	}

}
