package xyz.marstonconnell.randomloot.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.utils.RomanNumber;

public class BasicTag {
	public String name;
	public TextFormatting color;
	public int level;
	public int maxLevel;
	public List<String> incompatibleTags;
	
	public boolean offensive = false;
	public boolean forWeapons = false;
	public boolean forTools = false;
	public boolean forArmor = false;
	
	Map<String, Float> extraValues;

	public boolean onTagAdded(ItemStack s, World worldIn, PlayerEntity player) {
		if(worldIn == null) {
			return false;
		}
		
		return true;
	}
	
	
	public BasicTag addValue(String s, float f) {
		extraValues.put(s, f);
		return this;
	}
	
	public float getValue(String s) {
		return extraValues.get(s);
	}
	
	public BasicTag(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
		this.level = 0;
		this.maxLevel = 0;
		this.incompatibleTags = new ArrayList<String>();
		
		TagHelper.allTags.add(this);
		TagHelper.tagNames.add(name);
		TagHelper.tagMap.put(name, this);
		
		extraValues = new HashMap<String, Float>();

	}
	
	
	public BasicTag addBlackTags(String ... tags) {
		
		for(String t : tags) {
			
			this.incompatibleTags.add(t);
			
		}
		
		return this;
	}
	
	public BasicTag(BasicTag clone) {
		this.name = clone.name;
		this.color = clone.color;
		this.level = clone.level;
		this.maxLevel = clone.maxLevel;
		this.incompatibleTags = clone.incompatibleTags;
		extraValues = clone.extraValues;

	}
	
	public boolean sameTag(BasicTag tag) {
		return this.name.equals(tag.name);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof BasicTag) {
			
			BasicTag compareTo = (BasicTag) obj;
			
			return compareTo.level == this.level && sameTag(compareTo);
			
		}
		
		return false;
	}
	
	public BasicTag setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
		return this;
	}

	public BasicTag setLevel(int lvlIn) {
		this.level  = lvlIn;
		return this;
	}

	
	
	@Override
	public String toString() {

		
		String newName = this.name.replaceAll("_", " ");
		newName = TagHelper.convertToTitleCaseIteratingChars(newName);
		
		if(this.level >= 1) {
			String roman = RomanNumber.toRoman(this.level + 1);
			newName = newName + " " + roman;
		}
		return newName;
	}


	public BasicTag get() {
		return this;
	}


	public boolean onTagAdded(ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos,
			LivingEntity target) {
		// TODO Auto-generated method stub
		return false;
	}
}
