package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class DungeonTreetho extends DungeonBase{

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-11, -1, -11));
		end.add(new Coord(11, 8, 11));
		
		editor.fillRectHollow(rand, start, end, wall, false, true);
		
		MetaBlock birchSlab = new MetaBlock(Blocks.wooden_slab);
		birchSlab.withProperty(BlockWoodSlab.VARIANT_PROP, BlockPlanks.EnumType.BIRCH);
		birchSlab.withProperty(BlockWoodSlab.HALF_PROP, BlockSlab.EnumBlockHalf.TOP);
		MetaBlock pumpkin = new MetaBlock(Blocks.lit_pumpkin);
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-9, 8, -9));
		end.add(new Coord(9, 8, 9));
		editor.fillRectSolid(rand, start, end, birchSlab, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, start, end, pumpkin, true, true);
		
		cursor = new Coord(origin);
		cursor.add(new Coord(0, 8, 0));
		ceiling(editor, rand, settings, cursor);
		
		cursor = new Coord(origin);
		treeFarm(editor, rand, settings, cursor, entrances[0]);
		
		Cardinal[] orth = Cardinal.getOrthogonal(entrances[0]);
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(o, 5);
			treeFarm(editor, rand, settings, cursor, entrances[0]);
		}
		
		
		return true;
	}
	
	private void treeFarm(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir){
		Coord cursor;
		Coord start;
		Coord end;
		
		MetaBlock slab = new MetaBlock(Blocks.stone_slab);
		slab.withProperty(BlockStoneSlab.VARIANT_PROP, BlockStoneSlab.EnumType.SAND);
		MetaBlock light = new MetaBlock(Blocks.lit_pumpkin);
		MetaBlock sapling = new MetaBlock(Blocks.sapling);
		sapling.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.BIRCH);
		MetaBlock glass = ColorBlock.get(Blocks.stained_glass, EnumDyeColor.YELLOW);
		MetaBlock dirt = new MetaBlock(Blocks.dirt);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(orth[0]);
		end.add(orth[1]);
		
		start.add(Cardinal.reverse(dir), 7);
		end.add(dir, 7);
		
		editor.fillRectSolid(rand, start, end, slab, true, true);
		
		cursor = new Coord(origin);
		
		cursor.add(Cardinal.reverse(dir), 6);
		for(int i = 0; i <= 12; ++i ){
			if(i % 2 == 0){
				Coord p = new Coord(cursor);
				if(i % 4 == 0){
					sapling.setBlock(editor, p);
					p.add(Cardinal.DOWN);
					dirt.setBlock(editor, p);
				} else {
					glass.setBlock(editor, p);
					p.add(Cardinal.DOWN);
					light.setBlock(editor, p);
				}
			}
			cursor.add(dir);
		}
		
		
		
	}
	
	private void ceiling(WorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		
		MetaBlock fill = new MetaBlock(Blocks.planks);
		fill.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.DARK_OAK);
		
		MinimumSpanningTree tree = new MinimumSpanningTree(rand, 7, 3);
		tree.generate(editor, rand, fill, origin);
		
		for(Cardinal dir : Cardinal.directions){
			Coord start = new Coord(origin);
			start.add(dir, 9);
			Coord end = new Coord(start);
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start.add(orth[0], 9);
			end.add(orth[1], 9);
			
			editor.fillRectSolid(rand, start, end, fill, true, true);
			
			Coord cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir, 10);
			cursor.add(orth[0], 10);
			for(int i = 0; i < 5; ++i){
				pillar(editor, rand, settings, cursor);
				cursor.add(orth[1], 4);
			}
		}
		
	}
	
	private void pillar(WorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(origin);
		editor.fillDown(rand, cursor, pillar);
		
		for (Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			if(editor.isAirBlock(cursor)){
				WorldEditor.blockOrientation(stair, dir, true).setBlock(editor, cursor);
			}
		}
	}

	@Override
	public int getSize() {
		return 12;
	}

}
