package duelistmod.cards.tempCards;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.CardPoolRelic;
import duelistmod.variables.Strings;

public class CardPoolOptionSaveC extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CardPoolOptionSaveC");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GENERIC_TOKEN);
    public static final String NAME = cardStrings.NAME;
    public static final String[] EXTENDED = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public 	static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private ArrayList<AbstractCard> savedPool = new ArrayList<>();
    // /STAT DECLARATION/

    public CardPoolOptionSaveC() 
    { 
    	super(ID, NAME, IMG, COST, EXTENDED[0], TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.savedPool = setupPool();
    	this.magicNumber = this.baseMagicNumber = this.savedPool.size();
    }
    
    public void resetPool()
    {
    	this.savedPool.clear();
    	this.baseMagicNumber = this.magicNumber = 0;
       	DuelistMod.saveSlotC = "";
    	try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setString(DuelistMod.PROP_SAVE_SLOT_C, DuelistMod.saveSlotC);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
    	setupPool();
    }
    
    public void loadCorrectDesc(boolean saving)
    {
    	if (saving) { this.rawDescription = EXTENDED[0]; }
    	else { this.rawDescription = EXTENDED[1]; }
    	this.initializeDescription();
    }
    
    // Call this when player selects save slot card from Options Relic
    public void loadPool()
    {
    	DuelistMod.coloredCards.clear();
		DuelistMod.toReplacePoolWith.clear();
		DuelistMod.toReplacePoolWith.addAll(this.savedPool);
		if (this.savedPool.size() > 0) { DuelistMod.poolIsCustomized = true; }
		DuelistMod.shouldReplacePool = true;
		if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) { ((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription(); }
		CardCrawlGame.dungeon.initializeCardPools();
    }
    
    // Call this when the player selects the save slot card from Save Relic
    public void setPool(ArrayList<AbstractCard> newPool)
    {
    	this.savedPool = newPool;
    	this.magicNumber = this.baseMagicNumber = this.savedPool.size();
    	DuelistMod.saveSlotC = "";
    	for (AbstractCard c : this.savedPool) { DuelistMod.saveSlotC += c.originalName + "~"; }
    	try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setString(DuelistMod.PROP_SAVE_SLOT_C, DuelistMod.saveSlotC);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
    }
    
    private ArrayList<AbstractCard> setupPool()
	{
		ArrayList<AbstractCard> newPool = new ArrayList<>();
		if (!DuelistMod.saveSlotC.equals(""))
		{
			String[] savedStrings = DuelistMod.saveSlotC.split("~");
			//Map<String, String> map = new HashMap<>();
			List<String> cards = Arrays.asList(savedStrings);
			for (String s : cards) {
				if (DuelistMod.mapForCardPoolSave.containsKey(s))
				{
					newPool.add(DuelistMod.mapForCardPoolSave.get(s).makeStatEquivalentCopy());
					Util.log("SaveSlotCard found, loaded, and added to card pool: " + s);
				}
				else
				{
					Util.log("SaveSlotCard did not find " + s + " in the map!");
				}
			}
		}
		return newPool;
	}
    
    

    /*@Override
    public void update()
    {
    	super.update();
    	if (this.savedPool != null) { this.magicNumber = this.baseMagicNumber = this.savedPool.size(); }
    }*/

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    @Override public AbstractCard makeCopy() { return new CardPoolOptionSaveC(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}