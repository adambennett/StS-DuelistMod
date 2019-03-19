package defaultmod.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import defaultmod.DefaultMod;

public class DuelistOrb extends AbstractOrb {

	protected static int originalPassive = 0;
	protected static int originalEvoke = 0;
	
	@Override
	public AbstractOrb makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onEvoke() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playChannelSFX() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDescription() {
		// TODO Auto-generated method stub

	}
	
	public void setOriginalValues(int passive, int evoke)
	{
		originalPassive = passive;
		originalEvoke = evoke;
	}
	
	public int getOriginalEvoke()
	{
		return originalEvoke;
	}

	public void checkFocus() 
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			this.basePassiveAmount = originalPassive + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
		else
		{
			this.basePassiveAmount = originalPassive;
		}
		if (DefaultMod.debug)
		{
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalPassive: " + originalPassive + " :: new passive amount: " + this.basePassiveAmount);
		}
		applyFocus();
		updateDescription();
	}

}
