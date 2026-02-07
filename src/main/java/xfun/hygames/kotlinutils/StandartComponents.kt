package xfun.hygames.kotlinutils

import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes

val TRANSFORM
    inline get() = TransformComponent.getComponentType()

var TransformComponent.x : Double
    inline get() = position.x
    inline set(value) { position.x = value }

var TransformComponent.y : Double
    inline get() = position.y
    inline set(value) { position.y = value }


var TransformComponent.z : Double
    inline get() = position.z
    inline set(value) { position.z = value }


var TransformComponent.pitch : Float
    inline get() = rotation.pitch
    inline set(value) { rotation.pitch = value }

var TransformComponent.yaw : Float
    inline get() = rotation.yaw
    inline set(value) { rotation.yaw = value }

var TransformComponent.roll : Float
    inline get() = rotation.roll
    inline set(value) { rotation.roll = value }

val ENTITY_STAT_MAP
    inline get() = EntityStatMap.getComponentType()!!

var EntityStatMap.health : Float
    inline get() = get(DefaultEntityStatTypes.getHealth())!!.get()
    inline set(value) { setStatValue(DefaultEntityStatTypes.getHealth(), value) }

var EntityStatMap.oxygen : Float
    inline get() = get(DefaultEntityStatTypes.getOxygen())!!.get()
    inline set(value) { setStatValue(DefaultEntityStatTypes.getOxygen(), value) }

var EntityStatMap.stamina : Float
    inline get() = get(DefaultEntityStatTypes.getStamina())!!.get()
    inline set(value) { setStatValue(DefaultEntityStatTypes.getStamina(), value) }

var EntityStatMap.mana : Float
    inline get() = get(DefaultEntityStatTypes.getMana())!!.get()
    inline set(value) { setStatValue(DefaultEntityStatTypes.getMana(), value) }

var EntityStatMap.signatureEnergy : Float
    inline get() = get(DefaultEntityStatTypes.getSignatureEnergy())!!.get()
    inline set(value) { setStatValue(DefaultEntityStatTypes.getSignatureEnergy(), value) }

var EntityStatMap.ammo : Float
    inline get() = get(DefaultEntityStatTypes.getAmmo())!!.get()
    inline set(value) { setStatValue(DefaultEntityStatTypes.getAmmo(), value) }

val PLAYER
    inline get() = Player.getComponentType()
val TELEPORT
    inline get() = Teleport.getComponentType()
val EFFECT_CONTROLLER
    inline get() = EffectControllerComponent.getComponentType()