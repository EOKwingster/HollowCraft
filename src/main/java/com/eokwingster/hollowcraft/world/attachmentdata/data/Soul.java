package com.eokwingster.hollowcraft.world.attachmentdata.data;

import com.eokwingster.hollowcraft.HCConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class Soul implements INBTSerializable<CompoundTag> {
    private int soul;
    private int vessel;
    private boolean broken;

    private int maxSoul;

    public Soul() {
        correctValues();
    }

    public int getSoulMeterCapacity() {
        int soulMeterCapacity = HCConfig.soulMeterCapacity;
        return isBroken() ? soulMeterCapacity * 2 / 3 : soulMeterCapacity;
    }

    public int getSoulVesselCapacity() {
        return HCConfig.soulVesselCapacity;
    }

    public int getSoul() {
        return soul;
    }

    public void addSoul(int value) {
        setSoul(getSoul() + value);
    }

    public void subSoul(int value) {
        setSoul(getSoul() - value);
    }

    public void setSoul(int value) {
        soul = Math.clamp(value, 0, getMaxSoul());
    }

    public int getVessel() {
        return vessel;
    }

    public void addVessel(int num) {
        setVessel(getVessel() + num);
    }

    public void subVessel(int num) {
        setVessel(getVessel() - num);
    }

    public void setVessel(int num) {
        vessel = Math.clamp(num, 0, 3);
        correctValues();
    }

    public int getMaxSoul() {
        return maxSoul;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
        correctValues();
    }

    private void correctValues() {
        this.maxSoul =  getSoulMeterCapacity() + getVessel() * getSoulVesselCapacity();
        if (getSoul() > getMaxSoul()) {
            setSoul(getMaxSoul());
        }
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return writeNBT();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.readNBT(nbt);
    }

    public CompoundTag writeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("soul", getSoul());
        tag.putInt("vessel", getVessel());
        tag.putBoolean("broken", isBroken());
        return tag;
    }

    public void readNBT(CompoundTag nbt) {
        soul = nbt.getInt("soul");
        vessel = nbt.getInt("vessel");
        broken = nbt.getBoolean("broken");
        correctValues();
    }

    private Soul copy() {
        Soul soulCopy = new Soul();
        soulCopy.soul = this.soul;
        soulCopy.vessel = this.vessel;
        soulCopy.broken = this.broken;
        soulCopy.maxSoul = this.maxSoul;
        return soulCopy;
    }

    public Soul getPlayerCloneSoul() {
        Soul cloneSoul = this.copy();
        cloneSoul.setSoul(0);
        return cloneSoul;
    }
}
