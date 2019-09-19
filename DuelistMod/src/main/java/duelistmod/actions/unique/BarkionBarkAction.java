package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;

public class BarkionBarkAction extends AbstractGameAction
{
	int magicNumber = 0;
	
    public BarkionBarkAction(int magic) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.magicNumber = magic;
    }
    
    @Override
    public void update() 
    {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
        	AbstractPlayer p = AbstractDungeon.player;
        	ArrayList<AbstractMonster> strMons = new ArrayList<AbstractMonster>();
        	for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
        	{
        		if (mon.hasPower(StrengthPower.POWER_ID))
        		{
        			strMons.add(mon);
        		}
        	}
        	
        	if (strMons.size() > this.magicNumber)
        	{
        		ArrayList<AbstractMonster> strMonsB = new ArrayList<AbstractMonster>();
        		while (strMonsB.size() < this.magicNumber && strMons.size() > 0)
        		{
        			int highest = 0;
        			int index = -1;
        			for (int i = 0; i < strMons.size(); i++)
        			{
        				if (strMons.get(i).hasPower(StrengthPower.POWER_ID))
        				{
        					if (strMons.get(i).getPower(StrengthPower.POWER_ID).amount > highest)
        					{
        						highest = strMons.get(i).getPower(StrengthPower.POWER_ID).amount;
        						index = i;
        					}
        				}
        			}
        			
        			if (index != -1)
        			{
        				strMonsB.add(strMons.get(index));
        				strMons.remove(index);
        			}
        			else { strMons.clear(); Util.log("Uh-oh. Barkion Bark did a bad. We are gonna infinite loops maybe, so lets not. Clearing strMons list to exit the loop now"); }
        		}
        		
        		if (strMonsB.size() == this.magicNumber && strMonsB.size() > 0)
        		{
        			int totalStr = 0;
        			for (AbstractMonster mon : strMonsB) { if (mon.hasPower(StrengthPower.POWER_ID)) { totalStr += mon.getPower(StrengthPower.POWER_ID).amount; }}
        			DuelistCard.applyPowerToSelf(new StrengthPower(p, totalStr));
        		}
        	}
        	else
        	{
        		int totalStr = 0;
        		for (AbstractMonster mon : strMons) { if (mon.hasPower(StrengthPower.POWER_ID)) { totalStr += mon.getPower(StrengthPower.POWER_ID).amount; }}
        		DuelistCard.applyPowerToSelf(new StrengthPower(p, totalStr));
        	}
        }
        this.tickDuration();
    }
}
