package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.entity.Frozen
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent
import com.hypixel.hytale.server.core.entity.entities.BlockEntity
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent
import com.hypixel.hytale.server.core.entity.entities.player.CameraManager
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager
import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent
import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent
import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight
import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent
import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollision
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent
import com.hypixel.hytale.server.core.modules.entity.item.PickupItemComponent
import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker
import com.hypixel.hytale.server.core.modules.entity.player.KnockbackSimulation
import com.hypixel.hytale.server.core.modules.entity.player.PlayerInput
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes
import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues
import com.hypixel.hytale.server.core.modules.physics.component.Velocity

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
val MODEL
    inline get() = ModelComponent.getComponentType()!!
val BOUNDING_BOX
    inline get() = BoundingBox.getComponentType()!!
val DYNAMIC_LIGHT
    inline get() = DynamicLight.getComponentType()!!
val ENTITY_SCALE
    inline get() = EntityScaleComponent.getComponentType()!!
val PLAYER_SKIN
    inline get() = PlayerSkinComponent.getComponentType()
val PLAYER_SETTINGS
    inline get() = PlayerSettings.getComponentType()
val PLAYER_INPUT
    inline get() = PlayerInput.getComponentType()!!
val KNOCKBACK_SIMULATION
    inline get() = KnockbackSimulation.getComponentType()!!
val CHUNK_TRACKER
    inline get() = ChunkTracker.getComponentType()!!
val ITEM
    inline get() = ItemComponent.getComponentType()
val PICKUP_ITEM
    inline get() = PickupItemComponent.getComponentType()
val HITBOX_COLLISION
    inline get() = HitboxCollision.getComponentType()!!
val UUID_COMPONENT
    inline get() = UUIDComponent.getComponentType()
val HEAD_ROTATION
    inline get() = HeadRotation.getComponentType()!!
val MOVEMENT_MANAGER
    inline get() = MovementManager.getComponentType()!!
val CAMERA_MANAGER
    inline get() = CameraManager.getComponentType()!!
val FROZEN
    inline get() = Frozen.getComponentType()!!
val PROJECTILE
    inline get() = ProjectileComponent.getComponentType()
val BLOCK_ENTITY
    inline get() = BlockEntity.getComponentType()!!
val DISPLAY_NAME
    inline get() = DisplayNameComponent.getComponentType()
val MOVEMENT_STATES
    inline get() = MovementStatesComponent.getComponentType()!!
val KNOCKBACK
    inline get() = KnockbackComponent.getComponentType()!!
val VELOCITY
    inline get() = Velocity.getComponentType()
val PHYSICS_VALUES
    inline get() = PhysicsValues.getComponentType()
val INVULNERABLE
    inline get() = Invulnerable.getComponentType()!!