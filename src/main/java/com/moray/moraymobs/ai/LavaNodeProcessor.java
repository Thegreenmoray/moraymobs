package com.moray.moraymobs.ai;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.*;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class LavaNodeProcessor extends NodeEvaluator {
//borrowed from SwimNodeEvaluator
private final boolean allowBreaching;
    private final Long2ObjectMap<PathType> pathTypesByPosCache = new Long2ObjectOpenHashMap();

    public LavaNodeProcessor(boolean allowBreaching) {
        this.allowBreaching = allowBreaching;
    }

    public void prepare(PathNavigationRegion level, Mob mob) {
        super.prepare(level, mob);
        this.pathTypesByPosCache.clear();
    }

    public void done() {
        super.done();
        this.pathTypesByPosCache.clear();
    }

    public Node getStart() {
        return this.getNode(Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY + 0.5), Mth.floor(this.mob.getBoundingBox().minZ));
    }

    public Target getTarget(double x, double y, double z) {
        return this.getTargetNodeAt(x, y, z);
    }

    public int getNeighbors(Node[] outputArray, Node p_node) {
        int i = 0;
        Map<Direction, Node> map = Maps.newEnumMap(Direction.class);
        Direction[] var5 = Direction.values();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Direction direction = var5[var7];
            Node node = this.findAcceptedNode(p_node.x + direction.getStepX(), p_node.y + direction.getStepY(), p_node.z + direction.getStepZ());
            map.put(direction, node);
            if (this.isNodeValid(node)) {
                outputArray[i++] = node;
            }
        }

        Iterator var10 = Direction.Plane.HORIZONTAL.iterator();

        while(var10.hasNext()) {
            Direction direction1 = (Direction)var10.next();
            Direction direction2 = direction1.getClockWise();
            if (hasMalus((Node)map.get(direction1)) && hasMalus((Node)map.get(direction2))) {
                Node node1 = this.findAcceptedNode(p_node.x + direction1.getStepX() + direction2.getStepX(), p_node.y, p_node.z + direction1.getStepZ() + direction2.getStepZ());
                if (this.isNodeValid(node1)) {
                    outputArray[i++] = node1;
                }
            }
        }

        return i;
    }

    protected boolean isNodeValid(@Nullable Node node) {
        return node != null && !node.closed;
    }

    private static boolean hasMalus(@Nullable Node node) {
        return node != null && node.costMalus >= 0.0F;
    }

    @Nullable
    protected Node findAcceptedNode(int x, int y, int z) {
        Node node = null;
        PathType pathtype = this.getCachedBlockType(x, y, z);
        if (this.allowBreaching && pathtype == PathType.BREACH || pathtype == PathType.LAVA) {
            float f = this.mob.getPathfindingMalus(pathtype);
            if (f >= 0.0F) {
                node = this.getNode(x, y, z);
                node.type = pathtype;
                node.costMalus = Math.max(node.costMalus, f);
                if (this.currentContext.level().getFluidState(new BlockPos(x, y, z)).isEmpty()) {
                    node.costMalus += 8.0F;
                }
            }
        }

        return node;
    }

    protected PathType getCachedBlockType(int x, int y, int z) {
        return (PathType)this.pathTypesByPosCache.computeIfAbsent(BlockPos.asLong(x, y, z), (p_330157_) -> {
            return this.getPathType(this.currentContext, x, y, z);
        });
    }

    public PathType getPathType(PathfindingContext context, int x, int y, int z) {
        return this.getPathTypeOfMob(context, x, y, z, this.mob);
    }

    public PathType getPathTypeOfMob(PathfindingContext context, int x, int y, int z, Mob mob) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int i = x; i < x + this.entityWidth; ++i) {
            for(int j = y; j < y + this.entityHeight; ++j) {
                for(int k = z; k < z + this.entityDepth; ++k) {
                    BlockState blockstate = context.getBlockState(blockpos$mutableblockpos.set(i, j, k));
                    FluidState fluidstate = blockstate.getFluidState();
                    if (fluidstate.isEmpty() && blockstate.isPathfindable(PathComputationType.WATER) && blockstate.isAir()) {
                        return PathType.BREACH;
                    }

                    if (!fluidstate.is(FluidTags.LAVA)) {
                        return PathType.BLOCKED;
                    }
                }
            }
        }

        BlockState blockstate1 = context.getBlockState(blockpos$mutableblockpos);
        return blockstate1.isPathfindable(PathComputationType.WATER) ? PathType.LAVA : PathType.BLOCKED;
    }
}