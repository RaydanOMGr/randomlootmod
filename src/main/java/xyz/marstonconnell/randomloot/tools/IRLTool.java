package xyz.marstonconnell.randomloot.tools;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.BasicTag;

/**
 * Determines whether or not an item is an acceptable RandomLoot tool.
 * @author marston connell
 *
 */
public interface IRLTool {
	void setStats(ItemStack stack);
	void updateStats(ItemStack stack);
	void upgradeTool(ItemStack stack, World worldIn);
	List<String> getStatsLore(ItemStack stack);
	String getItemType();
	int getVariants();
	List<BasicTag> getAllowedTags();
}
