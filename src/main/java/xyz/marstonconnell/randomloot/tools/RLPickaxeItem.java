package xyz.marstonconnell.randomloot.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.StatBoostTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;
import xyz.marstonconnell.randomloot.utils.Config;

public class RLPickaxeItem extends RLToolItem implements IRLTool {
	private final static Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.FURNACE, Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD);


	   @Override
	public Set<Block> getEffectiveOn() {
		return EFFECTIVE_ON;
	}
	   
	public RLPickaxeItem(String name) {
		super(name, EFFECTIVE_ON, 2.0f, -2.8f);
	}
	
	public String getItemType() {
		return "pickaxe";
	}
	
	@Override
	public Set<ToolType> getToolTypes(ItemStack stack) {
		HashSet<ToolType> hs = new HashSet<ToolType>();
		hs.add(ToolType.PICKAXE);
		return hs;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState) {
		// TODO Auto-generated method stub
		return super.getHarvestLevel(stack, tool, player, blockState);
	}

	public boolean canHarvestBlock(BlockState blockIn) {
		if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
			return true;
		}
		Material material = blockIn.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		float speedBonus = nbt.getFloat("rl_dig_speed");
		
		Material material = state.getMaterial();
		
		float f = (getToolTypes(stack).stream().anyMatch(e -> state.isToolEffective(e)) ? efficiency :
	    (EFFECTIVE_ON.contains(state.getBlock()) ? this.efficiency : 1.0F));
		
		return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK
				? f
				: efficiency + speedBonus - 1;
	}
	
	@Override
	public List<BasicTag> getAllowedTags() {
		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if(tag.forTools) {
				allowedTags.add(tag);
			}

		}

		return allowedTags;
	}

	@Override
	public void setStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		int dmg = Config.BASE_PICKAXE_DAMAGE.get();
		double spd = Config.BASE_PICKAXE_ATTACK_SPEED.get();

		 
		nbt.putInt("rl_damage", dmg);
		nbt.putDouble("rl_speed", spd);
		nbt.putFloat("rl_dig_speed", 1);
		ToolUtilities.setIntNBT(stack, "rl_level", 1);
		
		stack.setTag(nbt);

	}

	@Override
	public void updateStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		CompoundNBT damage = new CompoundNBT();
		damage.put("AttributeName", StringNBT.valueOf("generic.attack_damage"));
		damage.put("Name", StringNBT.valueOf("generic.attack_damage"));
		
		int dmg = nbt.getInt("rl_damage");
		double spd = nbt.getDouble("rl_speed");

		
		damage.put("Amount", IntNBT.valueOf(dmg));
		damage.put("Operation", IntNBT.valueOf(0));
		
		IntArrayNBT UUID = new IntArrayNBT(new int[] {1,2,3,4});
		
		damage.put("UUID", UUID);
		damage.put("Slot", StringNBT.valueOf("mainhand"));

		// speed
		CompoundNBT speed = new CompoundNBT();
		speed.put("AttributeName", StringNBT.valueOf("generic.attack_speed"));
		speed.put("Name", StringNBT.valueOf("generic.attack_speed"));

		
		speed.put("Amount", DoubleNBT.valueOf(spd));
		speed.put("Operation", IntNBT.valueOf(0));

		UUID = new IntArrayNBT(new int[] {5,6,7,8});
		
		speed.put("UUID", UUID);
		
		speed.put("Slot", StringNBT.valueOf("mainhand"));
		

		ListNBT modifiers = new ListNBT();

		modifiers.add(damage);
		modifiers.add(speed);
		
		

		nbt.put("AttributeModifiers", modifiers);
		
		stack.setTag(nbt);

	}

	@Override
	public void upgradeTool(ItemStack stack, World worldIn) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		

		
		nbt.putDouble("rl_speed", nbt.getDouble("rl_speed") * (0.9));
		nbt.putFloat("rl_dig_speed", nbt.getFloat("rl_dig_speed") * (1.1f));
		
		
		stack.setTag(nbt);
		
		updateStats(stack);
		
		ToolUtilities.setLore(stack, worldIn);

	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public List<String> getStatsLore(ItemStack stack) {
DecimalFormat f = new DecimalFormat("#0.00");
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		List<String> s = new ArrayList<String>();
		s.add(TextFormatting.GRAY + "Attack Damage: " + nbt.getInt("rl_damage"));
		s.add(TextFormatting.GRAY + "Attack Speed: "
				+ f.format(4 + nbt.getDouble("rl_speed")));
		
		s.add(TextFormatting.GRAY + "Dig Speed: "
				+ f.format(this.efficiency - 1 + nbt.getFloat("rl_dig_speed")));
		
		return s;
	}

	@Override
	public int getVariants() {
		// TODO Auto-generated method stub
		return 17;
	}
	
	
	
	
	 

}
