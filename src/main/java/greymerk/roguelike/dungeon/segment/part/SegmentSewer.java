package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class SegmentSewer extends SegmentBase {

	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		MetaBlock stair = theme.getSecondaryStair();

		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.UP, 2);
		start.add(dir);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true);
		editor.fillRectSolid(rand, start, end, stair, true, true);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		editor.fillRectSolid(rand, start, end, water, true, true);
	}
}
