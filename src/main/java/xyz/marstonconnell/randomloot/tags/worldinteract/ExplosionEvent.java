package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class ExplosionEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn,  LivingEntity entityLiving, BlockState state, BlockPos pos, Entity target) {
		if (!worldIn.isRemote) {
			float f = 1.0f + 1.0F * level;
			worldIn.createExplosion(entityLiving, pos.getX(), pos.getY(), pos.getZ(), f, false, Mode.BREAK);

		}		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

	

}
