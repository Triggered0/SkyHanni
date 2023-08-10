package at.hannibal2.skyhanni.utils

import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard
import java.awt.Desktop
import java.io.IOException
import java.net.URI

object OSUtils {

    @JvmStatic
    fun openBrowser(url: String) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI(url))
            } catch (e: IOException) {
                e.printStackTrace()
                LorenzUtils.error("[SkyHanni] Error opening website: $url!")
            }
        } else {
            copyToClipboard(url)
            LorenzUtils.warning("[SkyHanni] Web browser is not supported! Copied url to clipboard.")
        }
    }

    fun copyToClipboard(text: String) {
        ClipboardUtils.copyToClipboard(text)
    }

    suspend fun readFromClipboard() = ClipboardUtils.readFromClipboard()

    fun KeyBinding.isActive(): Boolean {
        if (!Keyboard.isCreated()) return false
        try {
            if (Keyboard.isKeyDown(this.keyCode)) return true
        } catch (e: IndexOutOfBoundsException) {
            println("KeyBinding isActive caused an IndexOutOfBoundsException with keyCode: $keyCode")
            e.printStackTrace()
            return false
        }
        return this.isKeyDown || this.isPressed
    }
}
