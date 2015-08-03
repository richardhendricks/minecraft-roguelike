package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.init.Blocks;

public class DungeonsMusic extends DungeonBase {
	WorldEditor editor;
	Random rand;
	int originX;
	int originY;
	int originZ;
	
	public DungeonsMusic() {
	}

	public boolean generate(WorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		
		this.editor = editor;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();

		MetaBlock air = new MetaBlock(Blocks.air);
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory deco = theme.getSecondaryWall();
		MetaBlock rug1 = ColorBlock.get(Blocks.carpet, rand);
		MetaBlock rug2 = ColorBlock.get(Blocks.carpet, rand);
		MetaBlock rug3 = ColorBlock.get(Blocks.carpet, rand);
		
		// fill air
		editor.fillRectSolid(inRandom, originX - 5, originY, originZ - 5, originX + 5, originY + 3, originZ + 5, air);
		
		// shell
		editor.fillRectHollow(rand, new Coord(originX - 6, originY - 2, originZ - 6), new Coord(originX + 6, originY + 5, originZ + 6), wall, false, true);
		
		// floor
		editor.fillRectSolid(rand, originX - 5, originY - 1, originZ - 5, originX + 5, originY - 1, originZ + 5, deco, true, true);
		editor.fillRectSolid(rand, originX - 3, originY, originZ - 3, originX + 3, originY, originZ + 3, rug1, true, true);
		editor.fillRectSolid(rand, originX - 2, originY, originZ - 2, originX + 2, originY, originZ + 2, rug2, true, true);
		editor.fillRectSolid(rand, originX - 1, originY, originZ - 1, originX + 1, originY, originZ + 1, rug3, true, true);
		
		//WALLS
		MetaBlock log = Log.getLog(Log.OAK);
		
		// vertical beams
		editor.fillRectSolid(inRandom, originX - 2, originY, originZ - 5, originX - 2, originY + 2, originZ - 5, log, true, true);
		editor.fillRectSolid(inRandom, originX + 2, originY, originZ - 5, originX + 2, originY + 2, originZ - 5, log, true, true);

		editor.fillRectSolid(inRandom, originX - 2, originY, originZ + 5, originX - 2, originY + 2, originZ + 5, log, true, true);
		editor.fillRectSolid(inRandom, originX + 2, originY, originZ + 5, originX + 2, originY + 2, originZ + 5, log, true, true);

		editor.fillRectSolid(rand, originX - 5, originY, originZ - 2, originX - 5, originY + 2, originZ - 2, log, true, true);
		editor.fillRectSolid(rand, originX - 5, originY, originZ + 2, originX - 5, originY + 2, originZ + 2, log, true, true);

		editor.fillRectSolid(rand, originX + 5, originY, originZ - 2, originX + 5, originY + 2, originZ - 2, log, true, true);
		editor.fillRectSolid(rand, originX + 5, originY, originZ + 2, originX + 5, originY + 2, originZ + 2, log, true, true);

		editor.fillRectSolid(rand, originX - 5, originY, originZ - 5, originX - 5, originY + 2, originZ - 5, log, true, true);
		editor.fillRectSolid(rand, originX - 5, originY, originZ + 5, originX - 5, originY + 2, originZ + 5, log, true, true);
		editor.fillRectSolid(rand, originX + 5, originY, originZ - 5, originX + 5, originY + 2, originZ - 5, log, true, true);
		editor.fillRectSolid(rand, originX + 5, originY, originZ + 5, originX + 5, originY + 2, originZ + 5, log, true, true);

		// shelves
		editor.fillRectSolid(rand, originX - 4, originY, originZ - 5, originX - 3, originY, originZ - 5, deco, true, true);
		editor.fillRectSolid(rand, originX + 3, originY, originZ - 5, originX + 4, originY, originZ - 5, deco, true, true);
		
		editor.fillRectSolid(rand, originX - 4, originY, originZ + 5, originX - 3, originY, originZ + 5, deco, true, true);
		editor.fillRectSolid(rand, originX + 3, originY, originZ + 5, originX + 4, originY, originZ + 5, deco, true, true);
		
		editor.fillRectSolid(rand, originX - 5, originY, originZ - 4, originX - 5, originY, originZ - 3, deco, true, true);
		editor.fillRectSolid(rand, originX - 5, originY, originZ + 3, originX - 5, originY, originZ + 4, deco, true, true);
		
		editor.fillRectSolid(rand, originX + 5, originY, originZ - 4, originX + 5, originY, originZ - 3, deco, true, true);
		editor.fillRectSolid(rand, originX + 5, originY, originZ + 3, originX + 5, originY, originZ + 4, deco, true, true);
		
		HashSet<Coord> chestSpace = new HashSet<Coord>();
		chestSpace.addAll(WorldEditor.getRectSolid(originX - 4, originY + 1, originZ - 5, originX - 3, originY + 1, originZ - 5));
		chestSpace.addAll(WorldEditor.getRectSolid(originX + 3, originY + 1, originZ - 5, originX + 4, originY + 1, originZ - 5));
		
		chestSpace.addAll(WorldEditor.getRectSolid(originX - 4, originY + 1, originZ + 5, originX - 3, originY + 1, originZ + 5));
		chestSpace.addAll(WorldEditor.getRectSolid(originX + 3, originY + 1, originZ + 5, originX + 4, originY + 1, originZ + 5));
		
		chestSpace.addAll(WorldEditor.getRectSolid(originX - 5, originY + 1, originZ - 4, originX - 5, originY + 1, originZ - 3));
		chestSpace.addAll(WorldEditor.getRectSolid(originX - 5, originY + 1, originZ + 3, originX - 5, originY + 1, originZ + 4));
		
		chestSpace.addAll(WorldEditor.getRectSolid(originX + 5, originY + 1, originZ - 4, originX + 5, originY + 1, originZ - 3));
		chestSpace.addAll(WorldEditor.getRectSolid(originX + 5, originY + 1, originZ + 3, originX + 5, originY + 1, originZ + 4));

		TreasureChest.generate(editor, rand, settings, new ArrayList<Coord>(chestSpace), TreasureChest.MUSIC);
		
		// horizontal beams
		editor.fillRectSolid(rand, originX - 5, originY + 3, originZ - 5, originX - 5, originY + 3, originZ + 5, log, true, true);
		editor.fillRectSolid(rand, originX - 5, originY + 3, originZ - 5, originX + 5, originY + 3, originZ - 5, log, true, true);
		editor.fillRectSolid(rand, originX - 5, originY + 3, originZ + 5, originX + 5, originY + 3, originZ + 5, log, true, true);
		editor.fillRectSolid(rand, originX + 5, originY + 3, originZ - 5, originX + 5, originY + 3, originZ + 5, log, true, true);
		
		// ceiling cross beams
		editor.fillRectSolid(rand, originX - 2, originY + 4, originZ - 5, originX - 2, originY + 4, originZ + 5, log, true, true);
		editor.fillRectSolid(rand, originX + 2, originY + 4, originZ - 5, originX + 2, originY + 4, originZ + 5, log, true, true);
		editor.fillRectSolid(rand, originX - 5, originY + 4, originZ - 2, originX + 5, originY + 4, originZ - 2, log, true, true);
		editor.fillRectSolid(rand, originX - 5, originY + 4, originZ + 2, originX + 5, originY + 4, originZ + 2, log, true, true);
		
		// ceiling lamp
		editor.setBlock(originX, originY + 4, originZ, Blocks.redstone_block);
		editor.setBlock(originX - 1, originY + 4, originZ, Blocks.redstone_lamp);
		editor.setBlock(originX + 1, originY + 4, originZ, Blocks.redstone_lamp);
		editor.setBlock(originX, originY + 4, originZ - 1, Blocks.redstone_lamp);
		editor.setBlock(originX, originY + 4, originZ + 1, Blocks.redstone_lamp);
		
		// ceiling fill
		editor.fillRectSolid(rand, originX - 5, originY + 4, originZ - 5, originX + 5, originY + 4, originZ + 5, deco, true, false);
		
		// music box
		editor.setBlock(originX, originY, originZ, Blocks.jukebox);
		
		return true;
	}
	
	public boolean isValidDungeonLocation(WorldEditor editor, int originX, int originY, int originZ) {
		return false;
	}

	public int getSize(){
		return 7;
	}
	
}
