# Hytale Kotlin Utils

HKU (Hytale Kotlin Utils) adding Kotlin-like API to Hytale API

### Examples

Default Hytale API

```
override fun tick(
  dt: Float,
  index: Int,
  @Nonnull chunk: ArchetypeChunk<EntityStore?>,
  @Nonnull store: Store<EntityStore?>,
  @Nonnull commandBuffer: CommandBuffer<EntityStore?>
) {
  if (Util.worldCheck(commandBuffer, "lobby")) return

  val player = chunk.getComponent(index, Player.getComponentType()) ?: return

  if (player.gameMode.value != 0) return

  val transform = chunk.getComponent(index, TransformComponent.getComponentType()) ?: return

  if (transform.position.y > 205) return

  val teleport =
      if (transform.position.z < 34) Teleport(PARKOUR_SPAWN_POINT, Vector3f.ZERO)
      else Teleport(LOBBY_SPAWN_POINT, Vector3f.ZERO)

  val ref = chunk.getReferenceTo(index)

  commandBuffer.addComponent(ref, Teleport.getComponentType(), teleport)

  val statMap = commandBuffer.getComponent(ref, EntityStatMap.getComponentType()) ?: return

  statMap.setStatValue(DefaultEntityStatTypes.getStamina(), 100f)
}
```

HKU Hytale API
```
override fun tick(
    dt: Float,
    index: Int,
    @Nonnull chunk: ArchetypeChunk<EntityStore?>,
    @Nonnull store: Store<EntityStore?>,
    @Nonnull commandBuffer: CommandBuffer<EntityStore?>
) {
    if (Util.worldCheck(commandBuffer, "lobby")) return

    val player = chunk[index, PLAYER] ?: return

    if (player.gameMode.value != 0) return

    val transform = chunk[index, TRANSFORM] ?: return

    if (transform.y > 205) return

    val teleport =
        if (transform.z < 34) Teleport(PARKOUR_SPAWN_POINT, Vector3f.ZERO)
        else Teleport(LOBBY_SPAWN_POINT, Vector3f.ZERO)

    val ref = chunk[index]

    commandBuffer[ref, TELEPORT] = teleport

    val statMap = commandBuffer[ref, ENTITY_STAT_MAP] ?: return

    statMap.stamina = 100f
}
```
