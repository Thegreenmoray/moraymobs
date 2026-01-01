package com.moray.moraymobs.entity.projectiles;

import com.moray.moraymobs.registries.Mobregistries;
import com.moray.moraymobs.registries.Particlesregistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Treebeam extends Entity implements GeoEntity {
    private final AnimatableInstanceCache Cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(Treebeam.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> THETA = SynchedEntityData.defineId(Treebeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> PHI = SynchedEntityData.defineId(Treebeam.class, EntityDataSerializers.FLOAT);
    public LivingEntity spriggan;
  final  public float rho=15;
final public float distancebig=rho*Mth.sin(getPhi());
    private static final EntityDataAccessor<Integer> TIMER= SynchedEntityData.defineId(Treebeam.class, EntityDataSerializers.INT);
    float x;
    float y;
    float z;

    private void calculateEndPos() {
        double radius = 15;
        if (level().isClientSide()) {
             x = (float) (getX() + radius * Math.cos(0) * Math.cos(0));
            z = (float) (getZ() + radius * Math.sin(0) * Math.cos(0));
            y = (float) (getY() + radius * Math.sin(0));
        }
        else {
            x = (float) (getX() + radius * Math.cos(getTheta()) * Math.cos(getPhi()));
            z = (float) (getZ() + radius * Math.sin(getTheta()) * Math.cos(getPhi()));
            y = (float) (getY() + radius * Math.sin(getPhi()));
        }
    }


    public int gettimer(){
        return this.entityData.get(TIMER);
    }
    public void settimer(int timer){
        this.entityData.set(TIMER,timer);
    }

    public float getTheta() {
        return this.entityData.get(THETA);
    }

    public void setTheta(float theta) {
        this.entityData.set(THETA, theta);
    }

    public float getPhi() {
        return this.entityData.get(PHI);
    }

    public void setPhi(float phi) {
        this.entityData.set(PHI, phi);
    }
    public Treebeam(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

   public Treebeam(Level level, float theta, float phi, LivingEntity spriggan, float x, float y, float z){
       super(Mobregistries.TREE_BEAM.get(),level);
      this.setPos(x,y,z);
      this.setPhi(phi);
      this.setTheta(theta);
      this.spriggan=spriggan;
       if (!level.isClientSide) {
           this.setCasterID(spriggan.getId());
       }
        this.calculateEndPos();
    }

    public int getCasterID() {
        return this.entityData.get(CASTER);
    }

    public void setCasterID(int id) {
        this.entityData.set(CASTER, id);
    }

    @Override
    public void tick() {
        super.tick();

        settimer(gettimer()+1);

        if (gettimer()==1 && level().isClientSide) {
            spriggan = (LivingEntity) level().getEntity(getCasterID());
        }

        if(!level().isClientSide&&spriggan!=null){

            int new_rho= Mth.ceil(rho);
            for (int i=0;i<=new_rho;i++){
                float distance=i*Mth.sin(getPhi());
                Vec3 w=spriggan.position();

                Vec3 more_distance=new Vec3(w.x()+(distance*Mth.cos(getTheta())),
                        w.y()+(i*Mth.cos(getPhi()))+1,
                        w.z()+(distance*Mth.sin(getTheta())));
                ((ServerLevel) this.spriggan.level()).sendParticles(Particlesregistries.BEAM_PARTICLES.get(),
                        more_distance.x(),more_distance.y(),more_distance.z(),1,0,0,0,0);

                if(this.gettimer()>10){
                    List<Entity> entities=this.spriggan.level().getEntities(this.spriggan,new AABB(spriggan.position().x(),spriggan.position().y(),spriggan.position().z(),Mth.floor(distancebig*Mth.cos(getTheta())),Mth.floor(rho*Mth.cos(getPhi())),Mth.floor(distancebig*Mth.sin(getTheta()))).inflate(1, 1, 1));


                    for (Entity entity:entities){

                        entity.hurt(this.spriggan.damageSources().campfire(),2);
                    }


                }
            }
        }




        if(gettimer()>=170){
            this.remove(RemovalReason.DISCARDED);

        }



    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return Cache;
    }
    public void readAdditionalSaveData(CompoundTag compound) {
        this.settimer(compound.getInt("timer"));
        this.setCasterID(compound.getInt("caster"));
        this.setTheta(compound.getFloat("theta"));
        this.setPhi(compound.getFloat("phi"));


    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("timer",this.gettimer());
        compound.putInt("caster", this.getCasterID());
        compound.putFloat("phi",this.getPhi());
        compound.putFloat("theta",this.getTheta());




    }


    protected void defineSynchedData (SynchedEntityData.Builder builder) {
        builder.define(TIMER,0);
        builder.define(THETA, 0F);
        builder.define(PHI, 0F);
        builder.define(CASTER, 0);
    }

}
