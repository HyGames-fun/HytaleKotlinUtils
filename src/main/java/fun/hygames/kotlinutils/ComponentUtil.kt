package `fun`.hygames.kotlinutils

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.component.Holder
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> Holder<ECS_TYPE>.get(componentType : ComponentType<ECS_TYPE, T>): T? {
    return this.getComponent(componentType)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> Holder<ECS_TYPE>.set(componentType : ComponentType<ECS_TYPE, T>, component: T) {
    this.putComponent(componentType, component)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> Ref<ECS_TYPE>.get(componentType : ComponentType<ECS_TYPE, T>): T? {
    return this.store.getComponent(this, componentType)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> Ref<ECS_TYPE>.set(componentType : ComponentType<ECS_TYPE, T>, component: T) {
    this.store.putComponent(this, componentType, component)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T : Component<EntityStore>> PlayerRef.get(componentType : ComponentType<EntityStore, T>): T? {
    val ref = this.reference ?: throw NullPointerException("Reference of PlayerRef is null!")

    return ref.store.getComponent(ref, componentType)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T : Component<EntityStore>> PlayerRef.set(componentType : ComponentType<EntityStore, T>, component: T) {
    val ref = this.reference ?: throw NullPointerException("Reference of PlayerRef is null!")

    ref.store.putComponent(ref, componentType, component)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE> ArchetypeChunk<ECS_TYPE>.get(index : Int): Ref<ECS_TYPE> {
    return this.getReferenceTo(index)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> ArchetypeChunk<ECS_TYPE>.get(
    index: Int,
    componentType: ComponentType<ECS_TYPE, T>
): T? = this.getComponent(index, componentType)

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> CommandBuffer<ECS_TYPE>.get(
    ref: Ref<ECS_TYPE>,
    componentType: ComponentType<ECS_TYPE, T>
): T? = this.getComponent(ref, componentType)

@Suppress("NOTHING_TO_INLINE")
inline operator fun <ECS_TYPE, T : Component<ECS_TYPE>> CommandBuffer<ECS_TYPE>.set(
    ref: Ref<ECS_TYPE>,
    componentType: ComponentType<ECS_TYPE, T>,
    component: T
) = this.putComponent(ref, componentType, component)
