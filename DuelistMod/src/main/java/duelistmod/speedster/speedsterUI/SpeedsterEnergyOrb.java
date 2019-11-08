package duelistmod.speedster.speedsterUI;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.abstracts.CustomEnergyOrb;
import duelistmod.DuelistMod;
import duelistmod.speedster.SpeedsterVFX.general.*;

public class SpeedsterEnergyOrb extends CustomEnergyOrb {
    public static final String[] orbTextures = {
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png"),
            DuelistMod.makeSpeedsterPath("orb/layerFake.png")};

    private static final String VFXTexture = DuelistMod.makeSpeedsterPath("EnergyRecharge.png");

    private static final float[] layerSpeeds = new float[]{10.0F, 30.0F, 15.0F, -20.0F, 0.0F};

    private static final float CD = 0.05f;
    private static final float CALM_FIRE_OFFSET = -35.0F * Settings.scale;

    private ArrayList<AbstractGameEffect> flameEffects = new ArrayList<>();
    private ArrayList<AbstractGameEffect> baseEffects = new ArrayList<>();
    private int targetCount;
    private float maxOffset;
    private float calmFireCooldown = CD;

    public SpeedsterEnergyOrb() {
        super(orbTextures, VFXTexture, layerSpeeds);
        targetCount = 40;
        maxOffset = 75 * Settings.scale;
    }

    @Override
    public void updateOrb(int energyCount) {
        for (AbstractGameEffect e : baseEffects) {
            e.update();
        }
        for (AbstractGameEffect e : flameEffects) {
            e.update();
        }
        targetCount = energyCount * 20;
        maxOffset = Math.min(120.0f * Settings.scale, energyCount * 30.0f * Settings.scale);
        baseEffects.removeIf((e) -> e.isDone);
        flameEffects.removeIf((e) -> e.isDone);
        calmFireCooldown -= Gdx.graphics.getRawDeltaTime();
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean active, float current_x, float current_y) {
        super.renderOrb(sb, active, current_x, current_y);
        for (AbstractGameEffect e : baseEffects) {
            e.render(sb);
        }
        for (AbstractGameEffect e : flameEffects) {
            e.render(sb);
        }

        int max = Math.min(targetCount / 4, targetCount - flameEffects.size());
        for (int i = 0; i < max; i++) {

            float distance = MathUtils.random(maxOffset);
            float direction = MathUtils.random(MathUtils.PI2);
            float xOffset = MathUtils.cos(direction) * distance;
            float yOffset = MathUtils.sin(direction) * distance;
            flameEffects.add(new CasualFlameParticleEffect(current_x + xOffset, current_y + yOffset));
        }
        if (calmFireCooldown < 0) {
            calmFireCooldown += CD;
            baseEffects.add(new CalmFireEffect(current_x, current_y + CALM_FIRE_OFFSET));
        }
    }
}