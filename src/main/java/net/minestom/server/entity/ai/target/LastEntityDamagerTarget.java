package net.minestom.server.entity.ai.target;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.ai.TargetSelector;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.entity.damage.EntityDamage;

/**
 * Target the last damager of this entity
 */
public class LastEntityDamagerTarget extends TargetSelector {

    private float range;

    public LastEntityDamagerTarget(EntityCreature entityCreature, float range) {
        super(entityCreature);
        this.range = range;
    }

    @Override
    public Entity findTarget() {
        final DamageType damageType = entityCreature.getLastDamageType();

        if (!(damageType instanceof EntityDamage)) {
            // No damager recorded, return null
            return null;
        }

        final EntityDamage entityDamage = (EntityDamage) damageType;
        final Entity entity = entityDamage.getSource();

        if (entity.isRemoved()) {
            // Entity not valid
            return null;
        }

        // Check range
        return entityCreature.getDistance(entity) < range ? entity : null;
    }
}
