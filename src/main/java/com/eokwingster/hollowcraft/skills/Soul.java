package com.eokwingster.hollowcraft.skills;

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
        int oldVessel = getVessel();
        vessel = Math.clamp(num, 0, 3);
        correctValues();
        if (vessel > oldVessel) {
            addSoul((vessel - oldVessel) * getSoulVesselCapacity());
        }
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
        setSoul(getSoul());
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return writeNBT();
    }

    public CompoundTag writeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("soul", getSoul());
        tag.putInt("vessel", getVessel());
        tag.putBoolean("broken", isBroken());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.readNBT(nbt);
    }

    public void readNBT(CompoundTag nbt) {
        soul = nbt.getInt("soul");
        vessel = nbt.getInt("vessel");
        broken = nbt.getBoolean("broken");
        correctValues();
    }
}
