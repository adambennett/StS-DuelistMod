package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class LoseXEnergyExceptSomeAction extends AbstractGameAction {

	private AbstractPlayer player;
	private boolean freeToPlayOnce;
	private int keep = 0;

	public LoseXEnergyExceptSomeAction(AbstractPlayer player, boolean freeToPlayOnce, int keepEnergyAmount) {
		super();
		this.player = player;
		this.freeToPlayOnce = freeToPlayOnce;
		this.keep = keepEnergyAmount;
		duration = Settings.ACTION_DUR_XFAST;
		actionType = AbstractGameAction.ActionType.SPECIAL;
	}

	@Override
	public void update() {
		if (!freeToPlayOnce) {
			player.energy.use(EnergyPanel.totalCount - this.keep);
		}
		isDone = true;
	}

}