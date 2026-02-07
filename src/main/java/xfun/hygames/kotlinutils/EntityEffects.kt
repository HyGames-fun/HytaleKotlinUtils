package xfun.hygames.kotlinutils

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.RemovalBehavior
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

object EntityEffects {

    operator fun get(effectId : String) : Effect {
        var effect : Effect

        with(EntityEffect.getAssetMap()) {
            effect = Effect(getIndex(effectId), getAsset(effectId)!!)
        }

        return effect
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun EffectControllerComponent.addInfiniteEffect(ownerRef: Ref<EntityStore>, effect: Effect){
    this.addInfiniteEffect(ownerRef, effect.index, effect.effect, ownerRef.store)
}

@Suppress("NOTHING_TO_INLINE")
inline fun EffectControllerComponent.addEffect(ownerRef: Ref<EntityStore>, effect: Effect, duration: Float, overlapBehavior: OverlapBehavior){
    this.addEffect(ownerRef, effect.index, effect.effect, duration, overlapBehavior, ownerRef.store)
}

@Suppress("NOTHING_TO_INLINE")
inline fun EffectControllerComponent.removeEffect(ownerRef: Ref<EntityStore>, effect: Effect){
    this.removeEffect(ownerRef, effect.index, ownerRef.store)
}

@Suppress("NOTHING_TO_INLINE")
inline fun EffectControllerComponent.removeEffect(ownerRef: Ref<EntityStore>, effect: Effect, removalBehavior: RemovalBehavior){
    this.removeEffect(ownerRef, effect.index, removalBehavior, ownerRef.store)
}