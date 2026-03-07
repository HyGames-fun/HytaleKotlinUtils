package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

class MissingTypeProcessorException(name: String): Exception("Type processor with \"$name\" not found! Check type processor registration")