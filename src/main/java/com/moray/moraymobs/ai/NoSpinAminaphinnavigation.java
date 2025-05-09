package com.moray.moraymobs.ai;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NoSpinAminaphinnavigation extends AmphibiousPathNavigation {
    public NoSpinAminaphinnavigation(Mob p_217788_, Level p_217789_) {
        super(p_217788_, p_217789_);
    }


    protected void followThePath() {
        Vec3 vec3 = this.getTempMobPos();
        this.maxDistanceToWaypoint = (float) (this.mob.getBbWidth()*0.5);
        Vec3i vec3i = this.path.getNextNodePos();
        double d0 = Math.abs(this.mob.getX() - ((double)vec3i.getX() + (double)((int)(this.mob.getBbWidth() + 1.0F)) / (double)2.0F));
        double d1 = Math.abs(this.mob.getY() - (double)vec3i.getY());
        double d2 = Math.abs(this.mob.getZ() - ((double)vec3i.getZ() + (double)((int)(this.mob.getBbWidth() + 1.0F)) / (double)2.0F));
        boolean flag = d0 <= (double)this.maxDistanceToWaypoint && d2 <= (double)this.maxDistanceToWaypoint && d1 < (double)1.0F;
        if (flag || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vec3)) {
            this.path.advance();
        }

        this.doStuckDetection(vec3);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 vec) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vec3 = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!vec.closerThan(vec3, (double)2.0F)) {
                return false;
            } else {
                Vec3 vec31 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vec32 = vec3.subtract(vec);
                Vec3 vec33 = vec31.subtract(vec);

                    Vec3 vec34 = vec32.normalize();
                    Vec3 vec35 = vec33.normalize();
                    return vec35.dot(vec34) < (double)0.0F;
                }
            }
        }
    }




