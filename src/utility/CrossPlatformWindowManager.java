package utility;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CrossPlatformWindowManager {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    // Linux state
    private static String xdotoolId;

    // macOS / Windows state
    private static String  cachedName;
    private static Rectangle cachedBounds;

    // -------------------------------------------------------------------------

    public static boolean isWindowAvailable(String name) {
        if (isLinux())   return findLinux(name);
        if (isMac())     return findMac(name);
        if (isWindows()) return findWindows(name);
        return false;
    }

    public static Rectangle getWindowBounds() {
        if (isLinux())   return boundsLinux();
        if (isMac())     return boundsMac();
        if (isWindows()) return boundsWindows();
        return null;
    }

    public static void focusWindow() {
        if (isLinux())   focusLinux();
        if (isMac())     focusMac();
        if (isWindows()) focusWindows();
    }

    // -------------------------------------------------------------------------
    // Linux — xdotool

    private static boolean findLinux(String name) {
        try {
            Process p = new ProcessBuilder("xdotool", "search", "--name", name)
                    .redirectErrorStream(true).start();
            String id = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
            p.waitFor();
            if (id != null && !id.trim().isEmpty()) { xdotoolId = id.trim(); return true; }
        } catch (Exception e) {
            System.err.println("[window] xdotool not found: " + e.getMessage());
        }
        xdotoolId = null;
        return false;
    }

    private static Rectangle boundsLinux() {
        if (xdotoolId == null) return null;
        try {
            Process p = new ProcessBuilder("xdotool", "getwindowgeometry", "--shell", xdotoolId)
                    .redirectErrorStream(true).start();
            int x = 0, y = 0, w = 0, h = 0;
            String line;
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) {
                if      (line.startsWith("X="))      x = Integer.parseInt(line.substring(2).trim());
                else if (line.startsWith("Y="))      y = Integer.parseInt(line.substring(2).trim());
                else if (line.startsWith("WIDTH="))  w = Integer.parseInt(line.substring(6).trim());
                else if (line.startsWith("HEIGHT=")) h = Integer.parseInt(line.substring(7).trim());
            }
            p.waitFor();
            return new Rectangle(x, y, w, h);
        } catch (Exception e) { System.err.println("[window] xdotool geometry failed: " + e.getMessage()); }
        return null;
    }

    private static void focusLinux() {
        if (xdotoolId == null) return;
        try { new ProcessBuilder("xdotool", "windowfocus", "--sync", xdotoolId)
                .redirectErrorStream(true).start().waitFor(); }
        catch (Exception ignored) {}
    }

    // -------------------------------------------------------------------------
    // macOS — osascript
    // Note: requires Accessibility permission (System Settings > Privacy > Accessibility)

    private static boolean findMac(String name) {
        String script =
            "tell application \"System Events\"\n" +
            "  repeat with proc in (every process whose visible is true)\n" +
            "    repeat with win in (every window of proc)\n" +
            "      if name of win contains \"" + name.replace("\"", "\\\"") + "\" then\n" +
            "        set pos to position of win\n" +
            "        set sz to size of win\n" +
            "        return ((item 1 of pos) as string) & \",\" & ((item 2 of pos) as string) & \",\" & ((item 1 of sz) as string) & \",\" & ((item 2 of sz) as string)\n" +
            "      end if\n" +
            "    end repeat\n" +
            "  end repeat\n" +
            "end tell";
        try {
            Process p = new ProcessBuilder("osascript", "-e", script)
                    .redirectErrorStream(true).start();
            String result = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
            p.waitFor();
            if (result != null && result.contains(",")) {
                String[] parts = result.trim().split(",");
                cachedBounds = new Rectangle(
                        Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()),
                        Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()));
                cachedName = name;
                return true;
            }
        } catch (Exception e) { System.err.println("[window] osascript failed: " + e.getMessage()); }
        cachedBounds = null;
        return false;
    }

    private static Rectangle boundsMac() {
        if (cachedName != null) findMac(cachedName); // refresh each call
        return cachedBounds;
    }

    private static void focusMac() {
        if (cachedName == null) return;
        String script =
            "tell application \"System Events\"\n" +
            "  repeat with proc in (every process whose visible is true)\n" +
            "    repeat with win in (every window of proc)\n" +
            "      if name of win contains \"" + cachedName.replace("\"", "\\\"") + "\" then\n" +
            "        set frontmost of proc to true\n" +
            "        perform action \"AXRaise\" of win\n" +
            "        return\n" +
            "      end if\n" +
            "    end repeat\n" +
            "  end repeat\n" +
            "end tell";
        try { new ProcessBuilder("osascript", "-e", script)
                .redirectErrorStream(true).start().waitFor(); }
        catch (Exception ignored) {}
    }

    // -------------------------------------------------------------------------
    // Windows — PowerShell

    private static boolean findWindows(String name) {
        String script =
            "$p = Get-Process | Where-Object { $_.MainWindowTitle -like '*" + name + "*' } | Select-Object -First 1\n" +
            "if ($p -eq $null) { exit 1 }\n" +
            "Add-Type -TypeDefinition @'\n" +
            "using System; using System.Runtime.InteropServices;\n" +
            "public class WinApi {\n" +
            "  [DllImport(\"user32.dll\")] public static extern bool GetWindowRect(IntPtr h, out RECT r);\n" +
            "  public struct RECT { public int Left, Top, Right, Bottom; }\n" +
            "}\n" +
            "'@\n" +
            "$r = New-Object WinApi+RECT\n" +
            "[WinApi]::GetWindowRect($p.MainWindowHandle, [ref]$r) | Out-Null\n" +
            "Write-Output \"$($r.Left),$($r.Top),$($r.Right - $r.Left),$($r.Bottom - $r.Top)\"";
        try {
            Process p = new ProcessBuilder("powershell", "-Command", script)
                    .redirectErrorStream(true).start();
            String result = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
            p.waitFor();
            if (result != null && result.contains(",")) {
                String[] parts = result.trim().split(",");
                cachedBounds = new Rectangle(
                        Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()),
                        Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()));
                cachedName = name;
                return true;
            }
        } catch (Exception e) { System.err.println("[window] PowerShell failed: " + e.getMessage()); }
        cachedBounds = null;
        return false;
    }

    private static Rectangle boundsWindows() {
        if (cachedName != null) findWindows(cachedName);
        return cachedBounds;
    }

    private static void focusWindows() {
        if (cachedName == null) return;
        String script =
            "$p = Get-Process | Where-Object { $_.MainWindowTitle -like '*" + cachedName + "*' } | Select-Object -First 1\n" +
            "if ($p -ne $null) { [void][System.Runtime.InteropServices.Marshal]::GetActiveObject; " +
            "Add-Type -AssemblyName Microsoft.VisualBasic; [Microsoft.VisualBasic.Interaction]::AppActivate($p.Id) }";
        try { new ProcessBuilder("powershell", "-Command", script)
                .redirectErrorStream(true).start().waitFor(); }
        catch (Exception ignored) {}
    }

    // -------------------------------------------------------------------------

    private static boolean isLinux()   { return OS.contains("nux") || OS.contains("nix"); }
    private static boolean isMac()     { return OS.contains("mac"); }
    private static boolean isWindows() { return OS.contains("win"); }
}
