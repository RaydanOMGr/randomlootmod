package xyz.marstonconnell.randomloot.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.StatBoostTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;
import xyz.marstonconnell.randomloot.utils.Config;

public class RandomArmor extends ArmorItem implements IRLTool{

	public RandomArmor(String name, BaseArmorMaterial mat, EquipmentSlotType slot) {
		super(mat, slot, new Properties());
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));
		
		RLItems.ITEMS.add(this);

	}

	@Override
	public void setStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		int armor = Config.BASE_ARMOR.get();
		double toughness = Config.BASE_TOUGHNESS.get();

		
		nbt.putDouble("rl_armor", armor);
		nbt.putDouble("rl_tough", toughness);
		
		stack.setTag(nbt);
		
		
		ToolUtilities.setIntNBT(stack, "rl_level", 1);

		
	}
	
	
	public boolean isRepairItem(ItemStack stack) {
		return stack.getItem() == RLItems.best_shard;
	}
	
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return repair.getItem().equals(RLItems.best_shard);

	}

	@Override
	public void updateStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		CompoundNBT armorPoints = new CompoundNBT();
		armorPoints.put("AttributeName", StringNBT.valueOf("generic.armor"));
		armorPoints.put("Name", StringNBT.valueOf("generic.armor"));
		
		double armor = nbt.getDouble("rl_armor");
		double tough = nbt.getDouble("rl_tough");

		
		armorPoints.put("Amount", DoubleNBT.valueOf(armor));
		armorPoints.put("Operation", IntNBT.valueOf(0));
		
		IntArrayNBT UUID = new IntArrayNBT(new int[] {RandomLootMod.rand.nextInt(Integer.MAX_VALUE),RandomLootMod.rand.nextInt(Integer.MAX_VALUE),RandomLootMod.rand.nextInt(Integer.MAX_VALUE),RandomLootMod.rand.nextInt(Integer.MAX_VALUE)});
		
		armorPoints.put("UUID", UUID);
		armorPoints.put("Slot", StringNBT.valueOf(getItemType()));

		// speed
		CompoundNBT toughness = new CompoundNBT();
		toughness.put("AttributeName", StringNBT.valueOf("generic.armor_toughness"));
		toughness.put("Name", StringNBT.valueOf("generic.armor_toughness"));

		
		toughness.put("Amount", DoubleNBT.valueOf(tough));
		toughness.put("Operation", IntNBT.valueOf(0));

		UUID = new IntArrayNBT(new int[] {RandomLootMod.rand.nextInt(Integer.MAX_VALUE),RandomLootMod.rand.nextInt(Integer.MAX_VALUE),RandomLootMod.rand.nextInt(Integer.MAX_VALUE),RandomLootMod.rand.nextInt(Integer.MAX_VALUE)});
		
		toughness.put("UUID", UUID);
				
		toughness.put("Slot", StringNBT.valueOf(getItemType()));

		ListNBT modifiers = new ListNBT();

		modifiers.add(armorPoints);
		modifiers.add(toughness);
		
		

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
		
		
		
		nbt.putDouble("rl_armor", nbt.getDouble("rl_armor")* 1.05);
		nbt.putDouble("rl_tough", nbt.getDouble("rl_tough") * (1.05));
		
		stack.setTag(nbt);
		
		updateStats(stack);
		
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
		s.add(TextFormatting.GRAY + "Armor: " + f.format(nbt.getDouble("rl_armor")));
		s.add(TextFormatting.GRAY + "Toughness: "
				+ f.format(nbt.getDouble("rl_tough")));
		
		return s;
	}
	
	@Override
	public boolean isRepairable(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		List<BasicTag> tags = TagHelper.getTagList(stack);
		
		if(stack.getItem().equals(Items.AIR)) {
			return;
		}
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		int dm = nbt.getInt("rl_armor_damage");
		
		
		if(this.getDamage(stack) != dm) {
			
			ToolUtilities.changeXP(stack, 1, world, player.getPosition());
			
			ToolUtilities.setLore(stack, world);
			
			dm = this.getDamage(stack);
			nbt.putInt("rl_armor_damage", dm);
			stack.setTag(nbt);
		}
		
		
		for (int i = 0; i < tags.size(); i++) {
			if(tags.get(i).active) {
				continue;
			}
			
			if (tags.get(i) instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tags.get(i);
				eTag.runEffect(stack, world, player);
			}
			else if (tags.get(i) instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tags.get(i);
				eTag.runEffect(stack, world, player, world.getBlockState(new BlockPos(player.getPositionVec())), new BlockPos(player.getPositionVec()), null);
			}

		}
		
		
		
		super.onArmorTick(stack, world, player);
	}
	

	@Override
	public String getItemType() {
		// TODO Auto-generated method stub
		return this.getEquipmentSlot().getName();
	}

	@Override
	public int getVariants() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public List<BasicTag> getAllowedTags() {
		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if (tag instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tag;
				if (!eTag.offensive) {
					allowedTags.add(eTag);
				}
			} else if (tag instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tag;
				if (eTag.forArmor) {
					allowedTags.add(eTag);
				}
			}else if (tag instanceof StatBoostTag) {
				StatBoostTag eTag = (StatBoostTag) tag;
				if (eTag.forArmor) {
					allowedTags.add(eTag);
				}
			}
		}
		
				
		return allowedTags;
	}
	
	
	
	
	

}
