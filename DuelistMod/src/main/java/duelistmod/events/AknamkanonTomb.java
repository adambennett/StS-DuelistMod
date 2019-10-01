package duelistmod.events;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.helpers.Util;
import duelistmod.relics.*;
import duelistmod.variables.Tags;

public class AknamkanonTomb extends DuelistEvent {


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
        		if (AbstractDungeon.player.potionSlots < 9) { imageEventText.setDialogOption(OPTIONS[7]); }
            	else { imageEventText.setDialogOption(OPTIONS[6], true); }
        		imageEventText.setDialogOption(OPTIONS[8]);
                imageEventText.setDialogOption(OPTIONS[9]);
                imageEventText.setDialogOption(OPTIONS[10]);
                imageEventText.setDialogOption(OPTIONS[4]);
        		
        	}
        	else
        	{
        		if (AbstractDungeon.player.potionSlots < 9) { imageEventText.setDialogOption(OPTIONS[0]); }
            	else { imageEventText.setDialogOption(OPTIONS[6], true); }
                
            	imageEventText.setDialogOption(OPTIONS[1]);
                imageEventText.setDialogOption(OPTIONS[2]);
                imageEventText.setDialogOption(OPTIONS[3]);
                imageEventText.setDialogOption(OPTIONS[4]);
        	}
        }
        else
        {
        	imageEventText.setDialogOption(OPTIONS[5], true);
        	imageEventText.setDialogOption(OPTIONS[5], true);
        	imageEventText.setDialogOption(OPTIONS[5], true);
        	imageEventText.setDialogOption(OPTIONS[5], true);
            imageEventText.setDialogOption(OPTIONS[4]);
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
	            	// Brew - 2x potion slots - Lose 4(7) max hp
	            	case 0:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[4]);
	            		this.imageEventText.clearRemainingOptions();
	            		int initSlots = AbstractDungeon.player.potionSlots;
	            		AbstractDungeon.player.potionSlots = AbstractDungeon.player.potionSlots * 2;
	            		for (int j = 0; j < initSlots; j++) { AbstractDungeon.player.potions.add(new PotionSlot(initSlots + j)); }
	            		if (a15) { AbstractDungeon.player.decreaseMaxHealth(7); }
	            		else { AbstractDungeon.player.decreaseMaxHealth(4); }
	            		logMetric(NAME, "Brew - 2x Potion Slots");
	            		screenNum = 1;
	            		break;
	
	            	// Enrich - 2x Gold - get 2(3) random duelist curses
	            	case 1:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[4]);
	            		this.imageEventText.clearRemainingOptions();
	            		AbstractDungeon.player.gainGold(AbstractDungeon.player.gold);
	            		AbstractCard c = DuelistCardLibrary.getRandomDuelistCurse();	
	            		AbstractCard c2 = DuelistCardLibrary.getRandomDuelistCurse();
	            		while (c2.name.equals(c.name)) { c2 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		if (a15) 
	            		{
	            			AbstractCard c3 = DuelistCardLibrary.getRandomDuelistCurse(); 
	            			while (c3.name.equals(c.name) || c3.name.equals(c2.name)) { c3 = DuelistCardLibrary.getRandomDuelistCurse(); }
	            			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c3, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		}
	            		logMetric(NAME, "Enrich - 2x gold");
	            		screenNum = 1;
	            		break;
	
	            	// Intellect - dupe all spells - get 2 random curses
	            	case 2:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[4]);
	            		this.imageEventText.clearRemainingOptions();                        
	            		for (AbstractCard card : AbstractDungeon.player.masterDeck.group)  { if (card.hasTag(Tags.SPELL)) { AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2))); }}
	            		AbstractCard ca = CardLibrary.getCurse();	
	            		AbstractCard ca2 = CardLibrary.getCurse();
	            		while (ca2.name.equals(ca.name)) { ca2 = CardLibrary.getCurse(); }
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(ca, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(ca2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
	            		logMetric(NAME, "Intellect - dupe all spells");
	            		screenNum = 1;
	            		break;
	
	            	// Dig - random Duelist relic - lose 6(8) HP
	            	case 3:
	            		this.imageEventText.updateDialogOption(0, OPTIONS[4]);
	            		this.imageEventText.clearRemainingOptions();      
	            		boolean hasEveryDuelistRelic = true;
	            		for (AbstractRelic t : DuelistMod.duelistRelicsForTombEvent) { if (!AbstractDungeon.player.hasRelic(t.relicId)) { hasEveryDuelistRelic = false; break; }}
	            		if (!hasEveryDuelistRelic) 
	            		{
	            			AbstractRelic r = DuelistMod.duelistRelicsForTombEvent.get(AbstractDungeon.eventRng.random(DuelistMod.duelistRelicsForTombEvent.size() - 1));
	            			while (AbstractDungeon.player.hasRelic(r.relicId) || !r.canSpawn()) { r = DuelistMod.duelistRelicsForTombEvent.get(AbstractDungeon.eventRng.random(DuelistMod.duelistRelicsForTombEvent.size() - 1)); }
	            			AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
	            			Util.removeRelicFromPools(r);
	            			if (!a15) { AbstractDungeon.player.damage(new DamageInfo(null, 6, DamageInfo.DamageType.HP_LOSS)); }
	            			else { AbstractDungeon.player.damage(new DamageInfo(null, 8, DamageInfo.DamageType.HP_LOSS)); }
	            		}
	            		else if (DuelistMod.debug)
	            		{
	            			DuelistMod.logger.info("Triggered hasEveryDuelistRelic boolean, so do you have them all? DuelistMod.duelistRelics.size() == " + DuelistMod.duelistRelicsForTombEvent.size());
	            		}
	            		logMetric(NAME, "Dig - random Duelist relic");
	            		screenNum = 1;
	            		break;

	            	
	            	// Leave
	            	case 4:
	            		this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
	            		this.imageEventText.updateDialogOption(0, OPTIONS[4]);
	            		this.imageEventText.clearRemainingOptions();
	            		logMetric(NAME, "Leave");
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

