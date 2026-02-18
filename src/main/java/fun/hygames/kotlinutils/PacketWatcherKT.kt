package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.io.adapter.PacketAdapters
import com.hypixel.hytale.server.core.io.adapter.PacketFilter
import com.hypixel.hytale.server.core.io.adapter.PacketWatcher

/*
    Hytale API example usage:
        PacketAdapters.registerInbound(PacketWatcher { packetHandler, packet ->
            YOUR CODE
        })

    HKU example usage:
        PacketWatcher { packetHandler, packet ->
            YOUR CODE
        }.inbound()
 */

fun PacketWatcher.inbound(): PacketFilter = PacketAdapters.registerInbound(this)

fun PacketWatcher.toServer(): PacketFilter = inbound()


fun PacketWatcher.outbound(): PacketFilter = PacketAdapters.registerOutbound(this)

fun PacketWatcher.toClient(): PacketFilter = outbound()