package duelistmod.dungeons;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.random.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import duelistmod.helpers.Util;

@SuppressWarnings("unused")
public class ActFour extends AbstractDungeon 
{
	private static final UIStrings uiStrings;
	public static final String[] TEXT;
	public static final String NAME;
	public static final String ID = "theDuelist:DuelistKingdom";

	public ActFour(final AbstractPlayer p, final ArrayList<String> newSpecialOneTimeEventList) 
	{
		super(NAME, ID, p, newSpecialOneTimeEventList);
		if (scene != null) { scene.dispose(); }
        //scene = new FactoryScene(); // TODO
        fadeColor = Color.valueOf("0f220aff");
		initializeLevelSpecificChances();
		mapRng = new Random(Settings.seed + AbstractDungeon.actNum * 100);
		generateMap();
		//CardCrawlGame.music.changeBGM("FACTORYMAIN");
        AbstractDungeon.currMapNode = new MapRoomNode(0, -1);
        AbstractDungeon.currMapNode.room = new EmptyRoom();
	}
	
	public ActFour(AbstractPlayer p, SaveFile saveFile) 
	{
		super(NAME, p, saveFile);
		initializeLevelSpecificChances();
		CardCrawlGame.dungeon = this;
		if (scene != null) { scene.dispose(); }
		//scene = new FactoryScene(); // TODO
		fadeColor = Color.valueOf("0f220aff");
		miscRng = new Random(Settings.seed + saveFile.floor_num);
		//CardCrawlGame.music.changeBGM("FACTORYMAIN");
		mapRng = new Random(Settings.seed + AbstractDungeon.actNum * 100);
		generateMap();
        firstRoomChosen = true;
        populatePathTaken(saveFile);
	}

	private void setBoss(final String key)
	{
		AbstractDungeon.bossKey = key;
		if (DungeonMap.boss != null && DungeonMap.bossOutline != null)
		{
			DungeonMap.boss.dispose();
			DungeonMap.bossOutline.dispose();
		}
		
		// Add bosses here
		if (key.equals("")) 
		{
			DungeonMap.boss = ImageMaster.loadImage("");
			DungeonMap.bossOutline = ImageMaster.loadImage("");
		}
		
		else 
		{
			Util.log("WARNING: UNKNOWN BOSS ICON: " + key);
			DungeonMap.boss = null;
		}
		Util.log("[BOSS] " + key);
	}

	private static void generateRoomTypes(final ArrayList<AbstractRoom> roomList, final int availableRoomCount) 
	{
		Util.log("Generating Room Types! There are " + availableRoomCount + " rooms:");
        final int shopCount = Math.round(availableRoomCount * AbstractDungeon.shopRoomChance);
        Util.log(" SHOP (" + toPercentage(AbstractDungeon.shopRoomChance) + "): " + shopCount);
        final int restCount = Math.round(availableRoomCount * AbstractDungeon.restRoomChance);
        Util.log(" REST (" + toPercentage(AbstractDungeon.restRoomChance) + "): " + restCount);
        final int treasureCount = Math.round(availableRoomCount * AbstractDungeon.treasureRoomChance);
        Util.log(" TRSRE (" + toPercentage(AbstractDungeon.treasureRoomChance) + "): " + treasureCount);
        int eliteCount;
        if (ModHelper.isModEnabled("Elite Swarm")) {
            eliteCount = Math.round(availableRoomCount * (AbstractDungeon.eliteRoomChance * 2.5f));
            Util.log(" ELITE (" + toPercentage(AbstractDungeon.eliteRoomChance) + "): " + eliteCount);
        }
        else if (AbstractDungeon.ascensionLevel >= 1) {
            eliteCount = Math.round(availableRoomCount * AbstractDungeon.eliteRoomChance * 1.6f);
            Util.log(" ELITE (" + toPercentage(AbstractDungeon.eliteRoomChance) + "): " + eliteCount);
        }
        else {
            eliteCount = Math.round(availableRoomCount * AbstractDungeon.eliteRoomChance);
            Util.log(" ELITE (" + toPercentage(AbstractDungeon.eliteRoomChance) + "): " + eliteCount);
        }
        final int eventCount = Math.round(availableRoomCount * AbstractDungeon.eventRoomChance);
        Util.log(" EVNT (" + toPercentage(AbstractDungeon.eventRoomChance) + "): " + eventCount);
        final int monsterCount = availableRoomCount - shopCount - restCount - treasureCount - eliteCount - eventCount;
        Util.log(" MSTR (" + toPercentage(1.0f - AbstractDungeon.shopRoomChance - AbstractDungeon.restRoomChance - AbstractDungeon.treasureRoomChance - AbstractDungeon.eliteRoomChance - AbstractDungeon.eventRoomChance) + "): " + monsterCount);
        
        for (int i = 0; i < shopCount; ++i) {
            roomList.add(new ShopRoom());
        }
        for (int i = 0; i < restCount; ++i) {
            roomList.add(new RestRoom());
        }
        for (int i = 0; i < eliteCount; ++i) {
            roomList.add(new MonsterRoomElite());
        }
        for (int i = 0; i < eventCount; ++i) {
            roomList.add(new EventRoom());
        }
	}
	
	private static String toPercentage(final float n) {
        return String.format("%.0f", n * 100.0f) + "%";
    }
    
    private static void firstRoomLogic() {
        initializeFirstRoom();
        AbstractDungeon.leftRoomAvailable = AbstractDungeon.currMapNode.leftNodeAvailable();
        AbstractDungeon.centerRoomAvailable = AbstractDungeon.currMapNode.centerNodeAvailable();
        AbstractDungeon.rightRoomAvailable = AbstractDungeon.currMapNode.rightNodeAvailable();
    }

    private void addColorlessCards() {
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            final AbstractCard card = c.getValue();
            if (card.color == AbstractCard.CardColor.COLORLESS && card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.SPECIAL && card.type != AbstractCard.CardType.STATUS) {
                AbstractDungeon.colorlessCardPool.addToTop(card);
            }
        }
        Util.log("COLORLESS CARDS: " + AbstractDungeon.colorlessCardPool.size());
    }
    
    private void addCurseCards() {
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            final AbstractCard card = c.getValue();
            if (card.type == AbstractCard.CardType.CURSE && !Objects.equals(card.cardID, "Necronomicurse") && !Objects.equals(card.cardID, "AscendersBane") && !Objects.equals(card.cardID, "Pride")) {
                AbstractDungeon.curseCardPool.addToTop(card);
            }
        }
        Util.log("CURSE CARDS: " + AbstractDungeon.curseCardPool.size());
    }
    
    private boolean isNoteForYourselfAvailable() 
    {
        Util.log("Note For Yourself is disabled in: " + NAME);
        return false;
    }
    
    private static AbstractCard.CardRarity getCardRarityFallback(final int roll) {
        final int rareRate = 3;
        if (roll < rareRate) {
            return AbstractCard.CardRarity.RARE;
        }
        if (roll < 40) {
            return AbstractCard.CardRarity.UNCOMMON;
        }
        return AbstractCard.CardRarity.COMMON;
    }
    
    private AbstractRoom generateRoom(final EventHelper.RoomResult roomType) {
        Util.log("GENERATING ROOM: " + roomType.name());
        switch (roomType) {
            case ELITE: {
                return new MonsterRoomElite();
            }
            case MONSTER: {
                return new MonsterRoom();
            }
            case SHOP: {
                return new ShopRoom();
            }
            case TREASURE: {
                return new TreasureRoom();
            }
            default: {
                return new EventRoom();
            }
        }
    }
    
	

	@Override
	protected void generateMonsters() 
	{
		// TODO: This is copied from TheCity
        generateWeakEnemies(2);
        generateStrongEnemies(12);
        generateElites(10);
	}

	@Override
	protected void generateWeakEnemies(int arg0) {
		
		
	}
	
	@Override
	protected void generateStrongEnemies(int arg0) {
		
		
	}

	
	@Override
	protected void generateElites(int arg0) {
		
		
	}

	@Override
	protected ArrayList<String> generateExclusions() {
		
		return null;
	}

	@Override
	protected void initializeBoss() 
	{
		 bossList.clear();
	     // Bosses are added via BaseMod in DuelistMod.receivePostInitialize()
	}

	@Override
	protected void initializeEventImg() 
	{
        if (eventBackgroundImg != null) {
            eventBackgroundImg.dispose();
           eventBackgroundImg = null;
        }
        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");
	}

	@Override
	protected void initializeEventList() 
	{
		// Events are added via BaseMod in DuelistMod.receivePostInitialize()
	}

	@Override
	protected void initializeLevelSpecificChances() 
	{
		shopRoomChance = 0.05F;
        restRoomChance = 0.12F;
        treasureRoomChance = 0.0F;
        eventRoomChance = 0.22F;
        eliteRoomChance = 0.08F;
        smallChestChance = 50;
        mediumChestChance = 33;
        largeChestChance = 17;
        commonRelicChance = 50;
        uncommonRelicChance = 33;
        rareRelicChance = 17;
        colorlessRareChance = 0.3F;
        if (AbstractDungeon.ascensionLevel >= 12) {
            cardUpgradedChance = 0.25F;
        } else {
            cardUpgradedChance = 0.5F;
        }
	}

	@Override
	protected void initializeShrineList() 
	{
		// TODO: This is copied from TheCity
        shrineList.add("Match and Keep!");
        shrineList.add("Wheel of Change");
        shrineList.add("Golden Shrine");
        shrineList.add("Transmorgrifier");
        shrineList.add("Purifier");
        shrineList.add("Upgrade Shrine");
	}
	
	static {
        uiStrings = CardCrawlGame.languagePack.getUIString("theDuelist:ActFourText");
        TEXT = ActFour.uiStrings.TEXT;
        NAME = ActFour.TEXT[0];
    }

}
