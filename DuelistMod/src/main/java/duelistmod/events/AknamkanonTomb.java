package duelistmod.events;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.relics.*;
import duelistmod.variables.Tags;

public class AknamkanonTomb extends AbstractImageEvent {


    public static final String ID = DuelistMod.makeID("AknamkanonTomb");
    public static final String IMG = DuelistMod.makeEventPath("AknamkanonTomb.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
   // private boolean relicSelected = true;
   // private RelicSelectScreen relicSelectScreen;

    public AknamkanonTomb() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        if (AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID) || AbstractDungeon.player.hasRelic(MillenniumPuzzleShared.ID))
        {
        	if (AbstractDungeon.ascensionLevel >= 15)
        	{
        		if (AbstractDungeon.player.potionSlots < 9) { imageEventText.setDialogOption(OPTIONS[8]); }
            	else { imageEventText.setDialogOption(OPTIONS[7], true); }
        		imageEventText.setDialogOption(OPTIONS[8]);
                imageEventText.setDialogOption(OPTIONS[9]);
                imageEventText.setDialogOption(OPTIONS[10]);
                imageEventText.setDialogOption(OPTIONS[11]);
                imageEventText.setDialogOption(OPTIONS[12]);
        		
        	}
        	else
        	{
        		if (AbstractDungeon.player.potionSlots < 9) { imageEventText.setDialogOption(OPTIONS[0]); }
            	else { imageEventText.setDialogOption(OPTIONS[7], true); }
                
            	imageEventText.setDialogOption(OPTIONS[1]);
                imageEventText.setDialogOption(OPTIONS[2]);
                imageEventText.setDialogOption(OPTIONS[3]);
                imageEventText.setDialogOption(OPTIONS[4]);
                imageEventText.setDialogOption(OPTIONS[5]);
        	}
        }
        else
        {
        	imageEventText.setDialogOption(OPTIONS[6], true);
        	imageEventText.setDialogOption(OPTIONS[6], true);
        	imageEventText.setDialogOption(OPTIONS[6], true);
        	imageEventText.setDialogOption(OPTIONS[6], true);
        	imageEventText.setDialogOption(OPTIONS[6], true);
            imageEventText.setDialogOption(OPTIONS[5]);
        }
    }

    @Override
    protected void buttonEffect(int i) 
    {
    	boolean a15 = AbstractDungeon.ascensionLevel >= 15;
        switch (screenNum) 
        {
            case 0:
            	switch (i) 
            	{
	            	// Brew - 2x potion slots - 2x random Duelist curse
            		// 3 random on A15+
	            	case 0:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		int initSlots = AbstractDungeon.player.potionSlots;
	            		AbstractDungeon.player.potionSlots = AbstractDungeon.player.potionSlots * 2;
	            		for (int j = 0; j < initSlots; j++) { AbstractDungeon.player.potions.add(new PotionSlot(initSlots + j)); }
	            		AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractCard b2 = DuelistCardLibrary.getRandomDuelistCurse();
	            		while (b2.name.equals(b.name)) { b2 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		if (a15) 
	            		{
	            			AbstractCard b3 = DuelistCardLibrary.getRandomDuelistCurse();	 
	            			while (b3.name.equals(b.name) || b3.name.equals(b2.name)) { b3 = CardLibrary.getCurse(); }
	            			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b3, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		}
	            		screenNum = 1;
	            		break;
	
	            	// Enrich - 2x Gold - 2x random curse
	            	// 3 random on A15+
	            	case 1:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		AbstractDungeon.player.gainGold(AbstractDungeon.player.gold);
	            		AbstractCard c = CardLibrary.getCurse();	
	            		AbstractCard c2 = CardLibrary.getCurse();
	            		while (c2.name.equals(c.name)) { c2 = CardLibrary.getCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		if (a15) 
	            		{
	            			AbstractCard c3 = CardLibrary.getCurse(); 
	            			while (c3.name.equals(c.name) || c3.name.equals(c2.name)) { c3 = CardLibrary.getCurse(); }
	            			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c3, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		}
	            		screenNum = 1;
	            		break;
	
	            	// Intellect - dupe all spells - lose 8 max hp
	            	// 11 MaxHP on A15+
	            	case 2:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();                        
	            		for (AbstractCard card : AbstractDungeon.player.masterDeck.group)  { if (card.hasTag(Tags.SPELL)) { AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2))); }}
	            		if (a15)
	            		{
	            			if (AbstractDungeon.player.maxHealth > 11) 
		            		{ 
		            			AbstractDungeon.player.maxHealth -= 11; 
		            			if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth)
		            			{
		            				AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth; 
		            			}
		            		}
		            		else { AbstractDungeon.player.maxHealth = 1; AbstractDungeon.player.currentHealth = 1;}
	            		}
	            		else
	            		{
	            			if (AbstractDungeon.player.maxHealth > 8) 
		            		{ 
		            			AbstractDungeon.player.maxHealth -= 8; 
		            			if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth)
		            			{
		            				AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth; 
		            			}
		            		}
		            		else { AbstractDungeon.player.maxHealth = 1; AbstractDungeon.player.currentHealth = 1;}
	            		}
	            		
	            		screenNum = 1;
	            		break;
	
	            	// Reduce - all cards in deck cost 1 - 3 random curses (1 duelist, 2 totally random)
	            	// 4 random (2 duelist, 2 totally random) on A15+
	            	case 3:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();                        
	            		for (AbstractCard card : AbstractDungeon.player.masterDeck.group) { if (card.cost != 1) { card.updateCost(-card.cost + 1); card.isCostModified = true; }}
	            		AbstractCard d = CardLibrary.getCurse(); 
	            		AbstractCard d2 = DuelistCardLibrary.getRandomDuelistCurse();
	            		AbstractCard d3 = CardLibrary.getCurse();
	            		while (d3.name.equals(d2.name)) { d3 = CardLibrary.getCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));                       
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d2, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d3, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
	            		if (a15)
	            		{
	            			AbstractCard d4 = CardLibrary.getCurse(); 
	            			while (d4.name.equals(d.name)) { d4 = CardLibrary.getCurse(); }
	            			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d4, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));   
	            		}
	            		screenNum = 1;
	            		break;

	            	// Dig - random Duelist relic - lose 6 HP
	            	// -8 HP on A15+
	            	case 4:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();      
	            		boolean hasEveryDuelistRelic = true;
	            		for (AbstractRelic t : DuelistMod.duelistRelicsForTombEvent) { if (!AbstractDungeon.player.hasRelic(t.relicId)) { hasEveryDuelistRelic = false; break; }}
	            		if (!hasEveryDuelistRelic) 
	            		{
	            			AbstractRelic r = DuelistMod.duelistRelicsForTombEvent.get(AbstractDungeon.eventRng.random(DuelistMod.duelistRelicsForTombEvent.size() - 1));
	            			while (AbstractDungeon.player.hasRelic(r.relicId) || !r.canSpawn()) { r = DuelistMod.duelistRelicsForTombEvent.get(AbstractDungeon.eventRng.random(DuelistMod.duelistRelicsForTombEvent.size() - 1)); }
	            			AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
	            			if (!a15) { AbstractDungeon.player.damage(new DamageInfo(null, 6, DamageInfo.DamageType.HP_LOSS)); }
	            			else { AbstractDungeon.player.damage(new DamageInfo(null, 8, DamageInfo.DamageType.HP_LOSS)); }
	            		}
	            		else if (DuelistMod.debug)
	            		{
	            			DuelistMod.logger.info("Triggered hasEveryDuelistRelic boolean, so do you have them all? DuelistMod.duelistRelics.size() == " + DuelistMod.duelistRelicsForTombEvent.size());
	            		}
	            		screenNum = 1;
	            		break;

	            	// Leave
	            	case 5:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[5]);
	            		this.imageEventText.clearRemainingOptions();
	            		screenNum = 1;
	            		break;
            	}
            	break;
            case 1:
                openMap();
                break;
        }
    }
}

