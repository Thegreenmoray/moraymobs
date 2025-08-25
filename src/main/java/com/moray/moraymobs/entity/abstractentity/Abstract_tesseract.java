package com.moray.moraymobs.entity.abstractentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public abstract class Abstract_tesseract extends Monster {

    protected Abstract_tesseract(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
  this.moveControl =new TesseractMoveControl(this);
    }



    class TesseractMoveControl extends MoveControl {
        public TesseractMoveControl(Abstract_tesseract vex) {
            super(vex);
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - Abstract_tesseract.this.getX(), this.wantedY - Abstract_tesseract.this.getY(), this.wantedZ - Abstract_tesseract.this.getZ());
                double d0 = vec3.length();
                if (d0 < Abstract_tesseract.this.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    Abstract_tesseract.this.setDeltaMovement(Abstract_tesseract.this.getDeltaMovement().scale((double)0.2F));
                } else {
                    Abstract_tesseract.this.setDeltaMovement(Abstract_tesseract.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.02 / d0)));
                    if (Abstract_tesseract.this.getTarget() == null) {
                        Vec3 vec31 = Abstract_tesseract.this.getDeltaMovement();
                        Abstract_tesseract.this.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                        Abstract_tesseract.this.yBodyRot = Abstract_tesseract.this.getYRot();
                    } else {
                        double d2 = Abstract_tesseract.this.getTarget().getX() - Abstract_tesseract.this.getX();
                        double d1 = Abstract_tesseract.this.getTarget().getZ() - Abstract_tesseract.this.getZ();
                        Abstract_tesseract.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                        Abstract_tesseract.this.yBodyRot = Abstract_tesseract.this.getYRot();
                    }
                }
            }

        }
    }


    @Override
    protected void registerGoals() {
       this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new Abstract_tesseractRandomMoveGoal());


    }





    class Abstract_tesseractRandomMoveGoal extends Goal {
        public Abstract_tesseractRandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !Abstract_tesseract.this.getMoveControl().hasWanted() && Abstract_tesseract.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = Abstract_tesseract.this.blockPosition();


            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(Abstract_tesseract.this.random.nextInt(15) - 7, Abstract_tesseract.this.random.nextInt(11) - 5, Abstract_tesseract.this.random.nextInt(15) - 7);
                if (Abstract_tesseract.this.level().isEmptyBlock(blockpos1)) {
                    Abstract_tesseract.this.moveControl.setWantedPosition((double)blockpos1.getX() + (double)0.5F, (double)blockpos1.getY() + (double)0.5F, (double)blockpos1.getZ() + (double)0.5F, (double)0.25F);
                    if (Abstract_tesseract.this.getTarget() == null) {
                        Abstract_tesseract.this.getLookControl().setLookAt((double)blockpos1.getX() + (double)0.5F, (double)blockpos1.getY() + (double)0.5F, (double)blockpos1.getZ() + (double)0.5F, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }








}
