package xyz.marstonconnell.randomloot.tags.StatBoost;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.StatBoostEvent;
import xyz.marstonconnell.randomloot.tools.ToolUtilities;

public class WeatherBoostEvent extends StatBoostEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockPos pos,
			LivingEntity target) {
		
		
		
		
		if(worldIn.isRaining()) {

			ToolUtilities.setFloatNBT(stack, ToolUtilities.TAG_BONUS_SPEED, ToolUtilities.getFloatNBT(stack, ToolUtilities.TAG_BONUS_SPEED) + level * 6);
		}
		
		
	}

	@Override
	public void undoEffect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockPos pos,
			LivingEntity target) {

		
		ToolUtilities.setFloatNBT(stack, ToolUtilities.TAG_BONUS_SPEED, 0);

		
	}
	
	

}
