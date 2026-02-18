package `fun`.hygames.kotlinutils

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.ExtraInfo
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.codec.builder.BuilderField
import com.hypixel.hytale.function.consumer.TriConsumer
import kotlinx.serialization.Required
import java.util.function.BiConsumer
import java.util.function.BiFunction
import java.util.function.Function

fun <T, FieldType, S : BuilderCodec.BuilderBase<T, S>> BuilderCodec.BuilderBase<T, S>.append(
    key : String, codec : Codec<FieldType>, setter : BiConsumer<T, FieldType>, getter : Function<T, FieldType>
) : BuilderField.FieldBuilder<T, FieldType, S>{
    return append(KeyedCodec(key, codec), setter, getter)
}

fun <T, FieldType, S : BuilderCodec.BuilderBase<T, S>> BuilderCodec.BuilderBase<T, S>.appendRequired(
    key : String, codec : Codec<FieldType>, setter : BiConsumer<T, FieldType>, getter : Function<T, FieldType>
) : BuilderField.FieldBuilder<T, FieldType, S>{
    return append(KeyedCodec(key, codec, true), setter, getter)
}

fun <T, FieldType, S : BuilderCodec.BuilderBase<T, S>> BuilderCodec.BuilderBase<T, S>.append(
    key : String, codec : Codec<FieldType>, setter : TriConsumer<T, FieldType, ExtraInfo>, getter : BiFunction<T, ExtraInfo, FieldType>
) : BuilderField.FieldBuilder<T, FieldType, S>{
    return append(KeyedCodec(key, codec), setter, getter)
}

fun <T, FieldType, S : BuilderCodec.BuilderBase<T, S>> BuilderCodec.BuilderBase<T, S>.appendRequired(
    key : String, codec : Codec<FieldType>, setter : TriConsumer<T, FieldType, ExtraInfo>, getter : BiFunction<T, ExtraInfo, FieldType>
) : BuilderField.FieldBuilder<T, FieldType, S>{
    return append(KeyedCodec(key, codec, true), setter, getter)
}