package team._0mods.go

import com.sun.jna.Pointer
import com.sun.jna.Structure

class GoLangAdapter {
    open class GoSlice: Structure() {
        open class ByValue: GoSlice(), Structure.ByValue

        // Invoked by native
        @JvmField var data: Pointer = null!! // cast null to non-null? yeah, it is a crutch that will change anyway thanks to the JNI
        @JvmField var len: Long = null!!
        @JvmField var cap: Long = null!!

        override fun getFieldOrder(): List<String> {
            return listOf("data", "len", "cap")
        }
    }

    open class GoString: Structure() {
        open class ByValue: GoString(), Structure.ByValue

        // Invoked by native
        @JvmField var p: String = null!!
        @JvmField var n: Long = null!!

        override fun getFieldOrder(): List<String> {
            return listOf("p", "n")
        }
    }
}
